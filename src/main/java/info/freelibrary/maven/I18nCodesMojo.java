
package info.freelibrary.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import info.freelibrary.util.Constants;
import info.freelibrary.util.IOUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.MessageCodes;

@Mojo(name = "generate-codes", defaultPhase = LifecyclePhase.VALIDATE)
public class I18nCodesMojo extends AbstractMojo {

    private static final String MESSAGE_CLASS_NAME = "message-class-name";

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nCodesMojo.class, Constants.FREELIB_UTIL_MESSAGES);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    @Parameter(alias = "message-files", property = "message-files")
    private List<String> myPropertyFiles;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (myPropertyFiles != null) {
            final Iterator<?> iterator = myPropertyFiles.iterator();
            final Properties properties = new Properties();

            while (iterator.hasNext()) {
                FileInputStream inStream = null;

                try {
                    inStream = new FileInputStream((String) iterator.next());
                    properties.loadFromXML(inStream);

                    final String fullClassName = properties.getProperty(MESSAGE_CLASS_NAME);
                    final String srcFolderName = myProject.getBuild().getSourceDirectory();

                    if (fullClassName != null) {
                        final Iterator<String> messageIterator = properties.stringPropertyNames().iterator();
                        final String[] nameParts = fullClassName.split("\\.");
                        final int classNameIndex = nameParts.length - 1;
                        final String className = nameParts[classNameIndex];
                        final String[] packageParts = Arrays.copyOfRange(nameParts, 0, classNameIndex);
                        final String packageName = StringUtils.join(packageParts, ".");
                        final JavaInterfaceSource java = Roaster.create(JavaInterfaceSource.class);
                        final File packageDirectory =
                                new File(srcFolderName + File.separatorChar +
                                        packageName.replace('.', File.separatorChar));

                        // Make sure the package directory already exists
                        if (!packageDirectory.exists() && !packageDirectory.mkdirs()) {
                            throw new MojoExecutionException(LOGGER.getMessage(MessageCodes.MVN_MSG_003,
                                    packageDirectory, className));
                        }

                        // Cycle through all the entries in the supplied messages file, creating fields
                        while (messageIterator.hasNext()) {
                            final String key = messageIterator.next();

                            // Create a field in our new message codes class for the message
                            if (!key.equals(MESSAGE_CLASS_NAME)) {
                                final String normalizedKey = key.replaceAll("[\\.-]", "_");
                                final String value = properties.getProperty(key);
                                final FieldSource<JavaInterfaceSource> field = java.addField();

                                field.setName(normalizedKey).setStringInitializer(key);
                                field.setType("String").setPublic().setStatic(true).setFinal(true);
                                field.getJavaDoc().setFullText("Message: " + value);
                            }
                        }

                        // Create our new message codes class in the requested package directory
                        final File javaFile = new File(packageDirectory, className + ".java");
                        final FileWriter javaWriter = new FileWriter(javaFile);

                        // Let's tell Checkstyle to ignore the generated code (if it's so configured)
                        java.getJavaDoc().setFullText("BEGIN GENERATED CODE");

                        // Name our Java file and add a constructor
                        java.setPackage(packageName).setName(className);
                        // java.addMethod().setPrivate().setConstructor(true).setBody("super();");

                        // Lastly, write our generated Java class out to the file system
                        javaWriter.write(java.toString());
                        javaWriter.close();
                    } else if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn(MessageCodes.MVN_MSG_002, MESSAGE_CLASS_NAME);
                    }
                } catch (final IOException details) {
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error(details.getMessage(), details);
                    }

                    IOUtils.closeQuietly(inStream);
                }
            }
        } else if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(MessageCodes.MVN_MSG_001);
        }
    }

}
