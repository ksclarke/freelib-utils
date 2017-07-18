
package info.freelibrary.maven;

import static info.freelibrary.util.Constants.MESSAGES;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ListIterator;
import java.util.logging.LogManager;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import info.freelibrary.util.FileExtFileFilter;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A helper class that configures the default Java log manager if there is a <code>logging.properties</code> file in
 * <code>src/main/resources</code>.
 * 
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@Mojo(name = "read-logging-properties", defaultPhase = LifecyclePhase.INITIALIZE)
public class JavaLoggingHelperMojo extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaLoggingHelperMojo.class, MESSAGES);

    private static final String LOGGING_CONF_FILE = "logging.properties";

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final ListIterator<Resource> resources = myProject.getResources().listIterator();
        final FileExtFileFilter filter = new FileExtFileFilter(FileUtils.getExt(LOGGING_CONF_FILE));

        while (resources.hasNext()) {
            for (final File file : new File(resources.next().getDirectory()).listFiles(filter)) {
                if (file.getName().equals(LOGGING_CONF_FILE)) {
                    final LogManager logManager = LogManager.getLogManager();

                    try (final FileInputStream in = new FileInputStream(file)) {
                        logManager.readConfiguration(in);
                    } catch (IOException details) {
                        getLog().error(details);
                    }
                }
            }
        }
    }

}
