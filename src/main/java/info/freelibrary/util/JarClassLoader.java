
package info.freelibrary.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import info.freelibrary.util.warnings.PMD;

/**
 * A {@link URLClassLoader} for Jar files.
 */
@SuppressWarnings(PMD.CONSTRUCTOR_CALLS_OVERRIDABLE_METHOD)
public class JarClassLoader extends URLClassLoader {

    /**
     * The logger used by the JarClassLoader.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JarClassLoader.class, MessageCodes.BUNDLE);

    /**
     * Constructor for a {@link ClassLoader} for Jar files.
     *
     * @param aListOfURLs A {@link List} of URLs to search
     * @param aMainClassName A main class name to locate
     * @throws IOException If there is trouble reading the main class
     * @throws IllegalAccessException If there is a problem accessing the main class
     * @throws InstantiationException If there is trouble instantiating the main class
     * @throws ClassNotFoundException If the main class cannot be found
     * @throws InvocationTargetException If the main class' constructor cannot be invoked
     * @throws NoSuchMethodException If the class does not have the declared constructor
     */
    public JarClassLoader(final List<URL> aListOfURLs, final String aMainClassName)
            throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException,
            InvocationTargetException, ClassNotFoundException {
        super(aListOfURLs.toArray(new URL[0]));

        loadClass(aMainClassName).getDeclaredConstructor().newInstance();
    }

    /**
     * Constructor for a Jar ClassLoader.
     *
     * @param aMainClassName A main class name to locate
     * @throws IOException If there is trouble reading the main class
     * @throws InstantiationException If there is a problem instantiating the main class
     * @throws IllegalAccessException If there is trouble accessing the main class
     * @throws ClassNotFoundException If the main class cannot be found
     * @throws InvocationTargetException If the main class' constructor cannot be invoked
     * @throws NoSuchMethodException If the class does not have the declared constructor
     */
    public JarClassLoader(final String aMainClassName) throws IOException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        super(JarUtils.getJarURLs());

        LOGGER.debug(MessageCodes.UTIL_001, aMainClassName);
        loadClass(aMainClassName).getDeclaredConstructor().newInstance();
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
     * @throws InvocationTargetException If the main class' constructor cannot be invoked
     * @throws NoSuchMethodException If the class does not have the declared constructor
     */
    public JarClassLoader(final URL[] aURLs, final String aMainClassName) throws IOException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        super(aURLs);

        loadClass(aMainClassName).getDeclaredConstructor().newInstance();
    }

    /**
     * Loads the {@link Class} for the supplied class name.
     *
     * @param aName A class name
     * @return The loaded class
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
