
package info.freelibrary.util;

/**
 * Creates a new utilities class for working with doubles.
 */
public final class DoubleUtils {

    /**
     * Creates a new utilities class.
     */
    private DoubleUtils() {
        // This is intentionally left empty.
    }

    /**
     * Checks that the supplied double is valid.
     *
     * @param aDouble A double to check
     * @return The double if it's valid
     * @throws IllegalArgumentException If the supplied double isn't valid
     */
    public static double requireValid(final double aDouble) {
        if (Double.isNaN(aDouble)) {
            throw new IllegalArgumentException("Value cannot be NaN");
        }

        if (Double.isInfinite(aDouble)) {
            throw new IllegalArgumentException("Value cannot be infinite");
        }

        return aDouble;
    }

    /**
     * Checks that the supplied double is valid and a positive number.
     *
     * @param aDouble A double to check
     * @return The double if it's valid
     * @throws IllegalArgumentException If the supplied double isn't valid
     */
    public static double requireValidPositive(final double aDouble) {
        if (requireValid(aDouble) >= 0) {
            return aDouble;
        }

        throw new IllegalArgumentException("'{}' isn't a positive double");
    }
}
