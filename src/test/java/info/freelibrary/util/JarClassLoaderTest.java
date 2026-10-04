package info.freelibrary.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

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
            new JarClassLoader(I18nRuntimeException.class.getCanonicalName()).close();
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
            new JarClassLoader(JarUtils.getJarURLs(), I18nRuntimeException.class.getCanonicalName()).close();
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
            new JarClassLoader(Arrays.asList(JarUtils.getJarURLs()),
              I18nRuntimeException.class.getCanonicalName()).close();
        } catch (final Exception details) {
            Assert.fail(details.getMessage());
        }
    }

}
