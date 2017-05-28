
package info.freelibrary.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class JarClassLoaderTest {

    @Test
    public void testJarClassLoaderString() {
        try {
            new JarClassLoader("org.apache.maven.model.Build").close();
        } catch (final Exception details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testJarClassLoaderURLArrayString() {
        try {
            new JarClassLoader(JarUtils.getJarURLs(), "org.apache.maven.model.Build").close();
        } catch (final Exception details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testJarClassLoaderListOfURLString() {
        try {
            new JarClassLoader(Arrays.asList(JarUtils.getJarURLs()), "org.apache.maven.model.Build").close();
        } catch (final Exception details) {
            Assert.fail(details.getMessage());
        }
    }

}
