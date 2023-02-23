
package info.freelibrary.util;

import java.util.ResourceBundle;

import org.slf4j.ILoggerFactory;

/**
 * A facade for SLF4J's {@link org.slf4j.LoggerFactory}.
 */
public final class LoggerFactory {

    /**
     * Creates a new logger factory.
     */
    private LoggerFactory() {
        // This is intentionally left empty
    }

    /**
     * Gets a {@link ResourceBundle} wrapped SLF4J {@link org.slf4j.Logger}. By default, the resource bundle is
     * "messages".
     *
     * @param aClass A class to use for the logger name
     * @return A resource bundle aware logger
     */
    public static Logger getLogger(final Class<?> aClass) {
        return getLogger(aClass.getName(), null);
    }

    /**
     * Gets a {@link ResourceBundle} wrapped SLF4J {@link org.slf4j.Logger}. By default, the resource bundle is
     * "messages".
     *
     * @param aName A class to use for the logger name
     * @return A resource bundle aware logger
     */
    public static Logger getLogger(final String aName) {
        return getLogger(aName, null);
    }

    /**
     * Gets a {@link ResourceBundle} wrapped SLF4J {@link org.slf4j.Logger}.
     *
     * @param aClass A class to use for the logger name
     * @param aBundleName The name of the resource bundle to use
     * @return A resource bundle aware logger
     */
    public static Logger getLogger(final Class<?> aClass, final String aBundleName) {
        return getLogger(aClass.getName(), aBundleName);
    }

    /**
     * Gets a {@link ResourceBundle} wrapped SLF4J {@link org.slf4j.Logger}.
     *
     * @param aName A class to use for the logger name
     * @param aBundleName The name of the resource bundle to use
     * @return A resource bundle aware logger
     */
    public static Logger getLogger(final String aName, final String aBundleName) {
        final ILoggerFactory factory = org.slf4j.LoggerFactory.getILoggerFactory();
        final Logger logger;

        if (aBundleName != null) {
            logger = new Logger(factory.getLogger(aName), aBundleName);
        } else {
            logger = new Logger(factory.getLogger(aName));
        }

        return logger;
    }

}
