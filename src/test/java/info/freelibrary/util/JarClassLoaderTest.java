
package info.freelibrary.util;

import java.util.Arrays;

import org.junit.Test;

public class JarClassLoaderTest {

    @Test
    public void testJarClassLoaderString() throws Exception {
        new JarClassLoader("org.apache.maven.model.Build").close();
    }

    @Test
    public void testJarClassLoaderURLArrayString() throws Exception {
        new JarClassLoader(JarUtils.getJarURLs(), "org.apache.maven.model.Build").close();
    }

    @Test
    public void testJarClassLoaderListOfURLString() throws Exception {
        new JarClassLoader(Arrays.asList(JarUtils.getJarURLs()), "org.apache.maven.model.Build").close();
    }

}
