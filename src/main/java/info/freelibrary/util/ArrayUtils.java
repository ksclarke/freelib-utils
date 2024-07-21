
package info.freelibrary.util;

import java.util.Objects;

import info.freelibrary.util.warnings.PMD;

/**
 * A utilities class for working with arrays.
 */
public final class ArrayUtils {

    /**
     * Creates a new utilities class.
     */
    private ArrayUtils() {
        // This is intentionally left empty.
    }

    /**
     * Checks equality between two supplied arrays, considering a null and empty array to be equal.
     *
     * @param aFirstArray A first array to check
     * @param aSecondArray A second array to check
     * @return True if they are considered to be equal
     */
    @SuppressWarnings(PMD.USE_VARARGS)
    public static boolean equals(final Object[] aFirstArray, final Object[] aSecondArray) {
        if (aFirstArray == null && aSecondArray != null && aSecondArray.length == 0) {
            return true;
        }

        if (aSecondArray == null && aFirstArray != null && aFirstArray.length == 0) {
            return true;
        }

        return Objects.equals(aFirstArray, aSecondArray);
    }
}
