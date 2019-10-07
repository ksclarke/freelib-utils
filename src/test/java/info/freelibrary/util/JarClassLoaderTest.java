
package info.freelibrary.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for JarClassLoader.
 */
public class JarClassLoaderTest {

    private static final String BUILD = "org.apache.maven.model.Build";

    /**
     * Tests JarClassLoader constructor.
     */
    @Test
    public void testJarClassLoaderString() {
        try {
            new JarClassLoader(BUILD).close();
        } catch (final Exception details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests JarClassLoader constructor.
     */
    @Test
    public void testJarClassLoaderURLArrayString() {
        try {
            new JarClassLoader(JarUtils.getJarURLs(), BUILD).close();
        } catch (final Exception details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests JarClassLoader constructor.
     */
    @Test
    public void testJarClassLoaderListOfURLString() {
        try {
            new JarClassLoader(Arrays.asList(JarUtils.getJarURLs()), BUILD).close();
        } catch (final Exception details) {
            Assert.fail(details.getMessage());
        }
    }

}
