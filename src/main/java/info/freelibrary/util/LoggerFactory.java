
package info.freelibrary.util;

import org.slf4j.ILoggerFactory;

public class LoggerFactory {

    private LoggerFactory() {
    }

    /**
     * Gets an {@link XMLResourceBundle} wrapped SLF4J {@link org.slf4j.Logger}. By default, the resource bundle is
     * "messages".
     * 
     * @param aClass A class to use for the logger name
     * @return A resource bundle aware logger
     */
    public static final Logger getLogger(Class<?> aClass) {
        return getLogger(aClass.getName(), null);
    }

    /**
     * Gets an {@link XMLResourceBundle} wrapped SLF4J {@link org.slf4j.Logger}. By default, the resource bundle is
     * "messages".
     * 
     * @param aName A class to use for the logger name
     * @return A resource bundle aware logger
     */
    public static final Logger getLogger(String aName) {
        return getLogger(aName, null);
    }

    /**
     * Gets an {@link XMLResourceBundle} wrapped SLF4J {@link org.slf4j.Logger}.
     * 
     * @param aClass A class to use for the logger name
     * @param aBundleName The name of the resource bundle to use
     * @return A resource bundle aware logger
     */
    public static final Logger getLogger(Class<?> aClass, String aBundleName) {
        return getLogger(aClass.getName(), aBundleName);
    }

    /**
     * Gets an {@link XMLResourceBundle} wrapped SLF4J {@link org.slf4j.Logger}.
     * 
     * @param aName A class to use for the logger name
     * @param aBundleName The name of the resource bundle to use
     * @return A resource bundle aware logger
     */
    public static final Logger getLogger(String aName, String aBundleName) {
        ILoggerFactory factory = org.slf4j.LoggerFactory.getILoggerFactory();
        Logger logger;

        if (aBundleName != null) {
            logger = new Logger(factory.getLogger(aName), aBundleName);
        } else {
            logger = new Logger(factory.getLogger(aName));
        }

        return logger;
    }
}
