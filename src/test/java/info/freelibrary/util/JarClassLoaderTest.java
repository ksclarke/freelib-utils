
package info.freelibrary.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for JarClassLoader.
 */
public class JarClassLoaderTest {

    /**
     * Tests JarClassLoader constructor.
     */
    @Test
    public void testJarClassLoaderString() {
        try {
            new JarClassLoader(I18nObject.class.getCanonicalName()).close();
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
            new JarClassLoader(JarUtils.getJarURLs(), I18nObject.class.getCanonicalName()).close();
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
            new JarClassLoader(Arrays.asList(JarUtils.getJarURLs()), I18nObject.class.getCanonicalName()).close();
        } catch (final Exception details) {
            Assert.fail(details.getMessage());
        }
    }

}
