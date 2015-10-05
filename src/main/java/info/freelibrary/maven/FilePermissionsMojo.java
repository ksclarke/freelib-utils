
package info.freelibrary.maven;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.freelibrary.util.FileUtils;

@Mojo(name = "set-file-perms")
public class FilePermissionsMojo extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilePermissionsMojo.class);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * The permissions to set.
     */
    @Parameter(alias = "perms")
    private int myPerms;

    /**
     * The file or directory to set permissions.
     */
    @Parameter(alias = "file")
    private File myFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Set<PosixFilePermission> perms = FileUtils.convertToPermissionsSet(myPerms);

        try {
            Files.setPosixFilePermissions(myFile.toPath(), perms);
        } catch (final IOException details) {
            throw new MojoExecutionException(details.getMessage(), details);
        }
    }

}
