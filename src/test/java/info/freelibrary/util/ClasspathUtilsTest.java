
package info.freelibrary.util;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

import org.junit.Test;

/**
 * Tests the {@link ClasspathUtils} class.
 */
public class ClasspathUtilsTest {

    /** A logger for the tests. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathUtilsTest.class, MessageCodes.BUNDLE);

    /** A test pattern for classes. */
    private static final String CLASSES_PATTERN = "(.*)t-classes";

    /** A test pattern for junit classes. */
    private static final String JUNIT_PATTERN = "^junit(.*)$";

    /** A test constant for a Jar extension. */
    private static final String JAR_EXT = ".jar";

    /**
     * Test method for {@link ClasspathUtils#getDirs()}.
     */
    @Test
    public void testGetDirs() {
        for (final String filename : ClasspathUtils.getDirs()) {
            if (!new File(filename).isDirectory()) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_049, filename));
            }
        }
    }

    /**
     * Test method for {@link ClasspathUtils#getDirFiles()}.
     */
    @Test
    public void testGetDirFiles() {
        for (final File file : ClasspathUtils.getDirFiles()) {
            if (!file.isDirectory()) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_049, file.getAbsolutePath()));
            }
        }
    }

    /**
     * Test method for {@link ClasspathUtils#getDirs(java.io.FilenameFilter)} .
     */
    @Test
    public void testGetDirsFilenameFilter() {
        final RegexDirFilter filter = new RegexDirFilter(CLASSES_PATTERN);
        final int count = ClasspathUtils.getDirs(filter).length;

        if (count != 1) {
            fail(LOGGER.getMessage(MessageCodes.UTIL_050, count));
        }
    }

    /**
     * Test method for {@link ClasspathUtils#getDirFiles(java.io.FilenameFilter)} .
     */
    @Test
    public void testGetDirFilesFilenameFilter() {
        final RegexDirFilter filter = new RegexDirFilter(CLASSES_PATTERN);
        final int count = ClasspathUtils.getDirFiles(filter).length;

        if (count != 1) {
            fail(LOGGER.getMessage(MessageCodes.UTIL_050, count));
        }
    }

    /**
     * Test method for {@link ClasspathUtils#getJars()}.
     */
    @Test
    public void testGetJars() {
        for (final String jarName : ClasspathUtils.getJars()) {
            if (!jarName.endsWith(JAR_EXT)) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_051, jarName));
            }
        }
    }

    /**
     * Test method for {@link ClasspathUtils#getJars(java.io.FilenameFilter)} .
     */
    @Test
    public void testGetJarsFilenameFilter() {
        final RegexFileFilter filter = new RegexFileFilter(JUNIT_PATTERN);

        if (ClasspathUtils.getJars(filter).length != 1) {
            fail(LOGGER.getMessage(MessageCodes.UTIL_052));
        }
    }

    /**
     * Test method for {@link ClasspathUtils#getJarFiles()}.
     */
    @Test
    public void testGetJarFiles() {
        try {
            for (final JarFile jarFile : ClasspathUtils.getJarFiles()) {
                if (!jarFile.getName().endsWith(JAR_EXT)) {
                    fail(LOGGER.getMessage(MessageCodes.UTIL_053, jarFile.getName()));
                }
            }
        } catch (final IOException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link ClasspathUtils#getJarFiles(java.io.FilenameFilter)} .
     */
    @Test
    public void testGetJarFilesFilenameFilter() {
        try {
            final RegexFileFilter filter = new RegexFileFilter(JUNIT_PATTERN);
            final JarFile[] jars = ClasspathUtils.getJarFiles(filter);

            if (jars.length < 1) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_054));
            }
        } catch (final IOException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link ClasspathUtils#findFirst(java.lang.String)}.
     */
    @Test
    public void testFindFirst() {
        try {
            // Looking for a file at the root of a dir in the classpath
            if (!ClasspathUtils.find("freelib-utils_messages.xml")) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_055));
            }

            // Looking for a class file that is buried in a dir structure
            if (!ClasspathUtils.find("info/freelibrary/util/StringUtils.class")) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_056));
            }

            // Looking for something that doesn't exist
            if (ClasspathUtils.find("SOMETHING_NOT_FOUND")) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_057));
            }

            // Looking inside a jar file for its manifest
            if (!ClasspathUtils.find("META-INF/MANIFEST.MF")) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_058));
            }
        } catch (final IOException details) {
            fail(details.getMessage());
        }
    }

}
