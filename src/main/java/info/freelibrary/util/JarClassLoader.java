/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link URLClassLoader} for Jar files.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class JarClassLoader extends URLClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JarClassLoader.class);

    private static final ResourceBundle RB = ResourceBundle.getBundle("Messages", new XMLBundleControl());

    private static final String CLASSPATH = "java.class.path";

    private static final String MAIN_JAR = System.getProperty(CLASSPATH);

    private static final String PATH = System.getProperty("user.home") + "/";

    private static final String HOME = System.getProperty("user.dir") + "/";

    private static final String JAR_URL_PROTOCOL = "jar:file://";

    /**
     * Constructor for a Jar ClassLoader.
     * 
     * @param aMainClassName A main class name to locate
     * @throws Exception If there is trouble locating the main class
     */
    public JarClassLoader(final String aMainClassName) throws Exception {
        super(getJarURLs());

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(RB.getString("jarClassLoader.init"), aMainClassName);
        }

        loadClass(aMainClassName).newInstance();
    }

    /**
     * Constructor for a {@link ClassLoader} for Jar files.
     * 
     * @param aURLs An array of URLs to search
     * @param aMainClassName A main class name to locate
     * @throws Exception If there is trouble locating the main class
     */
    public JarClassLoader(final URL[] aURLs, final String aMainClassName) throws Exception {
        super(aURLs);

        loadClass(aMainClassName).newInstance();
    }

    /**
     * Constructor for a {@link ClassLoader} for Jar files.
     * 
     * @param aListOfURLs A {@link List} of URLs to search
     * @param aMainClassName A main class name to locate
     * @throws Exception If there is trouble locating the main class
     */
    public JarClassLoader(final List<URL> aListOfURLs, final String aMainClassName) throws Exception {
        super(aListOfURLs.toArray(new URL[aListOfURLs.size()]));

        loadClass(aMainClassName).newInstance();
    }

    /**
     * Loads the {@link Class} for the supplied class name.
     * 
     * @throws ClassNotFoundException If the class for the supplied name can't be found
     */
    @Override
    public Class<?> loadClass(final String aName) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(aName);

        if (loadedClass == null) {
            try {
                loadedClass = findClass(aName);
            } catch (final ClassNotFoundException details) {
                loadedClass = super.loadClass(aName);
            }
        }

        return loadedClass;
    }

    /**
     * Gets a list of jar files in the classpath.
     * 
     * @return An array of {@link URL}s found in the classpath
     * @throws IOException If there is trouble reading the classpath
     */
    private static URL[] getJarURLs() throws IOException {
        final JarFile jarFile = new JarFile(MAIN_JAR);
        final Manifest manifest = jarFile.getManifest();
        final Attributes attributes = manifest.getMainAttributes();
        final String classpath = attributes.getValue("Class-Path");
        final StringTokenizer tokenizer = new StringTokenizer(classpath);
        final List<URL> urlList = new LinkedList<URL>();

        urlList.add(new URL(JAR_URL_PROTOCOL + HOME + MAIN_JAR + "!/"));

        while (tokenizer.hasMoreTokens()) {
            final String jarPath = tokenizer.nextToken();
            urlList.add(new URL(JAR_URL_PROTOCOL + PATH + jarPath + "!/"));
        }

        jarFile.close();
        return urlList.toArray(new URL[urlList.size()]);
    }
}
