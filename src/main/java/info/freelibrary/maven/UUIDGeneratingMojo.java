
package info.freelibrary.maven;

import java.util.UUID;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "set-uuid-property", defaultPhase = LifecyclePhase.VALIDATE)
public class UUIDGeneratingMojo extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(UUIDGeneratingMojo.class);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * An optional String value from which to construct the UUID.
     */
    @Parameter(alias = "string")
    private String myString;

    /**
     * An optional build property name for the requested UUID.
     */
    @Parameter(alias = "name", defaultValue = "uuid")
    private String myName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final String uuid = myString == null ? UUID.randomUUID().toString() : UUID.fromString(myString).toString();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Setting a UUID property ({} = {}) for use in the Maven build", myName, uuid);
        }

        myProject.getProperties().setProperty(myName, uuid);
    }

}
