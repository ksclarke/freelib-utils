/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import static info.freelibrary.util.Constants.BUNDLE_NAME;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * A {@link URLClassLoader} for Jar files.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class JarClassLoader extends URLClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JarClassLoader.class, BUNDLE_NAME);

    /**
     * Constructor for a Jar ClassLoader.
     *
     * @param aMainClassName A main class name to locate
     * @throws IOException If there is trouble reading the main class
     * @throws InstantiationException If there is a problem instantiating the main class
     * @throws IllegalAccessException If there is trouble accessing the main class
     * @throws ClassNotFoundException If the main class cannot be found
     */
    public JarClassLoader(final String aMainClassName) throws IOException, IllegalAccessException,
            InstantiationException, ClassNotFoundException {
        super(JarUtils.getJarURLs());

        LOGGER.debug(MessageCodes.UTIL_001, aMainClassName);
        loadClass(aMainClassName).newInstance();
    }

    /**
     * Constructor for a {@link ClassLoader} for Jar files.
     *
     * @param aURLs An array of URLs to search
     * @param aMainClassName A main class name to locate
     * @throws IOException If there is trouble reading the main class
     * @throws IllegalAccessException If there is a problem accessing the main class
     * @throws InstantiationException If there is trouble instantiating the main class
     * @throws ClassNotFoundException If the main class cannot be found
     */
    public JarClassLoader(final URL[] aURLs, final String aMainClassName) throws IOException, IllegalAccessException,
            InstantiationException, ClassNotFoundException {
        super(aURLs);

        loadClass(aMainClassName).newInstance();
    }

    /**
     * Constructor for a {@link ClassLoader} for Jar files.
     *
     * @param aListOfURLs A {@link List} of URLs to search
     * @param aMainClassName A main class name to locate
     * @throws IOException If there is trouble reading the main class
     * @throws IllegalAccessException If there is a problem accessing the main class
     * @throws InstantiationException If there is trouble instantiating the main class
     * @throws ClassNotFoundException If the main class cannot be found
     */
    public JarClassLoader(final List<URL> aListOfURLs, final String aMainClassName) throws IOException,
            IllegalAccessException, InstantiationException, ClassNotFoundException {
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
                LOGGER.trace("Class for {} not found... trying super class", aName, details);
                loadedClass = super.loadClass(aName);
            }
        }

        return loadedClass;
    }
}
