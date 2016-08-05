
package info.freelibrary.maven;

import static info.freelibrary.util.FileUtils.sizeFromBytes;

import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * Sets Maven project properties with values for system.cores, system.free.memory, and system.total.memory; memory
 * values are set with unit of measurement appended (e.g., 200m, 3g, 5000k).
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@Mojo(name = "set-cpumem-properties", defaultPhase = LifecyclePhase.VALIDATE)
public class CPUandMemoryMojo extends AbstractMojo {

    public static final String SYSTEM_CORES = "system.cores";

    public static final String SYSTEM_FREE_MEMORY = "system.free.memory";

    public static final String SYSTEM_TOTAL_MEMORY = "system.total.memory";

    private static final Logger LOGGER = LoggerFactory.getLogger(CPUandMemoryMojo.class);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Properties properties = myProject.getProperties();
        final int cores = Runtime.getRuntime().availableProcessors();
        final SystemInfo si = new SystemInfo();
        final HardwareAbstractionLayer hal = si.getHardware();
        final GlobalMemory memory = hal.getMemory();

        properties.setProperty(SYSTEM_CORES, Integer.toString(cores));

        properties.setProperty(SYSTEM_FREE_MEMORY, sizeToString(memory.getAvailable()));
        properties.setProperty(SYSTEM_TOTAL_MEMORY, sizeToString(memory.getTotal()));
    }

    /**
     * Convert the size string returned by {@link FileUtils#sizeFromBytes} into the format we need for the JVM.
     *
     * @param aSizeInBytes
     * @return A formatted string representation of the supplied amount of memory
     */
    private String sizeToString(final long aSizeInBytes) {
        final String size = sizeFromBytes(aSizeInBytes, true).replace(" ", "");
        return size.substring(0, size.length() - 1);
    }
}
