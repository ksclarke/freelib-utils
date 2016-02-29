
package info.freelibrary.maven;

import static info.freelibrary.util.Constants.FREELIB_UTIL_MESSAGES;
import static info.freelibrary.util.MessageCodes.MVN_MESSAGE_001;
import static info.freelibrary.util.MessageCodes.MVN_MESSAGE_002;
import static info.freelibrary.util.MessageCodes.MVN_MESSAGE_003;

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

import info.freelibrary.util.IOUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * I18nCodesMojo is a Maven mojo that can generate a <code>MessageCodes</code> class from which I18N message codes can
 * be referenced. The codes are then used to retrieve textual messages from resource bundles. The benefit of this is the
 * code can be generic, but the actual text from the pre-configured message file will be displayed in the IDE.
 * <p>
 * To manually run the plugin: `mvn info.freelibrary:freelib-utils:0.6.0-SNAPSHOT:generate-codes
 * -Dmessage-files=src/main/resources/freelib-utils_messages.xml` (supplying whatever version and message file is
 * appropriate). Usually, though, the plugin would just be configured to run with the resources Maven lifecycle.
 * </p>
 *
 * @author Kevin S. Clarke <a href="mailto:ksclarke@ksclarke.io">ksclarke@ksclarke.io</a>
 */
@Mojo(name = "generate-codes", defaultPhase = LifecyclePhase.VALIDATE)
public class I18nCodesMojo extends AbstractMojo {

    private static final String MESSAGE_CLASS_NAME = "message-class-name";

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nCodesMojo.class, FREELIB_UTIL_MESSAGES);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    @Parameter(alias = "messageFiles", property = "messageFiles")
    private List<String> myPropertyFiles;

    @Parameter(alias = "generatedSourcesDirectory", property = "generatedSourcesDirectory")
    private File myGeneratedSrcDir;

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
                    final String srcFolderName = myGeneratedSrcDir == null ? myProject.getBuild().getSourceDirectory()
                            : myGeneratedSrcDir.getAbsolutePath();

                    if (fullClassName != null) {
                        final Iterator<String> messageIterator = properties.stringPropertyNames().iterator();
                        final String[] nameParts = fullClassName.split("\\.");
                        final int classNameIndex = nameParts.length - 1;
                        final String className = nameParts[classNameIndex];
                        final String[] packageParts = Arrays.copyOfRange(nameParts, 0, classNameIndex);
                        final String packageName = StringUtils.join(packageParts, ".");
                        final JavaInterfaceSource java = Roaster.create(JavaInterfaceSource.class);
                        final File packageDirectory = new File(srcFolderName + File.separatorChar + packageName.replace(
                                '.', File.separatorChar));

                        // Make sure the package directory already exists
                        if (!packageDirectory.exists() && !packageDirectory.mkdirs()) {
                            throw new MojoExecutionException(LOGGER.getMessage(MVN_MESSAGE_003, packageDirectory,
                                    className));
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

                        // Lastly, write our generated Java class out to the file system
                        javaWriter.write(java.toString());
                        javaWriter.close();
                    } else if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn(MVN_MESSAGE_002, MESSAGE_CLASS_NAME);
                    }
                } catch (final IOException details) {
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error(details.getMessage(), details);
                    }

                    IOUtils.closeQuietly(inStream);
                }
            }
        } else if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(MVN_MESSAGE_001);
        }
    }

}
