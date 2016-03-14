/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import static info.freelibrary.util.MessageCodes.UTIL_MESSAGE_001;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link URLClassLoader} for Jar files.
 *
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class JarClassLoader extends URLClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JarClassLoader.class);

    private static final ResourceBundle RB = ResourceBundle.getBundle(Constants.FREELIB_UTIL_MESSAGES,
            new XMLBundleControl());

    /**
     * Constructor for a Jar ClassLoader.
     *
     * @param aMainClassName A main class name to locate
     * @throws Exception If there is trouble locating the main class
     */
    public JarClassLoader(final String aMainClassName) throws Exception {
        super(JarUtils.getJarURLs());

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(RB.getString(UTIL_MESSAGE_001), aMainClassName);
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
                LOGGER.trace("Class for {} not found... trying super class", aName, details);
                loadedClass = super.loadClass(aName);
            }
        }

        return loadedClass;
    }
}
