
package info.freelibrary.util;

import java.util.Objects;

import info.freelibrary.util.warnings.PMD;

/**
 * An extension to the Java environment properties interface.
 */
@SuppressWarnings({ PMD.INVALID_LOG_MESSAGE_FORMAT })
public final class Env {

    /** A logger for the <code>Env</code> convenience class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Env.class, MessageCodes.BUNDLE);

    /**
     * Creates a new environment object.
     */
    private Env() {
        // This is intentionally left empty.
    }

    /**
     * Gets an environmental property value, optionally falling back to the supplied alternative.
     *
     * @param aPropertyName An environmental property name
     * @param aFallbackValue A fallback value used if the required property isn't set
     * @return The environmental property value or its supplied fallback value
     */
    public static String get(final String aPropertyName, final String aFallbackValue) {
        final String envProperty = StringUtils.trimToNull(System.getenv(aPropertyName));
        return envProperty == null ? aFallbackValue : envProperty;
    }

    /**
     * Gets an environmental property value, optionally falling back to the supplied alternative. If the ENV found value
     * isn't an integer, the fallback value will be used instead (and a warning logged).
     *
     * @param aPropertyName An environmental property name
     * @param aFallbackValue A fallback value used if the required property isn't set
     * @return The environmental property value or its supplied fallback value
     */
    public static int get(final String aPropertyName, final int aFallbackValue) {
        final String envProperty = StringUtils.trimToNull(System.getenv(aPropertyName));

        try {
            return envProperty == null ? aFallbackValue : Integer.parseInt(envProperty);
        } catch (final NumberFormatException details) {
            LOGGER.warn(MessageCodes.UTIL_073, envProperty, Integer.class.getSimpleName());
            return aFallbackValue;
        }
    }

    /**
     * Gets an environmental property value, optionally falling back to the supplied alternative. If the ENV found value
     * isn't a float, the fallback value will be used instead (and a warning logged).
     *
     * @param aPropertyName An environmental property name
     * @param aFallbackValue A fallback value used if the required property isn't set
     * @return The environmental property value or its supplied fallback value
     */
    public static float get(final String aPropertyName, final float aFallbackValue) {
        final String envProperty = StringUtils.trimToNull(System.getenv(aPropertyName));

        try {
            return envProperty == null ? aFallbackValue : Float.parseFloat(envProperty);
        } catch (final NumberFormatException details) {
            LOGGER.warn(MessageCodes.UTIL_073, envProperty, Float.class.getSimpleName());
            return aFallbackValue;
        }
    }

    /**
     * Gets an environmental property value or throws a NullPointerException if that property isn't found.
     *
     * @param aPropertyName An environmental property name
     * @param aMessage An exception message used if property is not found
     * @return The environmental property value
     * @throws NullPointerException If the requested property name isn't found in the environment
     */
    public static String getOrFail(final String aPropertyName, final String aMessage) {
        return Objects.requireNonNull(StringUtils.trimToNull(System.getenv(aPropertyName)), aMessage);
    }
}
