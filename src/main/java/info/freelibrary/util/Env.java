
package info.freelibrary.util;

import java.util.Objects;

/**
 * An extension to the Java environment properties interface.
 */
public final class Env {

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
