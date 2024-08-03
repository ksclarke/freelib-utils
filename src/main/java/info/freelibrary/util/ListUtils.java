
package info.freelibrary.util;

import java.util.List;
import java.util.Objects;

/**
 * A utilities class for working with lists.
 */
public final class ListUtils {

    /**
     * Creates a new utilities class.
     */
    private ListUtils() {
        // This is intentionally left empty.
    }

    /**
     * Checks equality between two supplied lists, considering a null and empty list to be equal.
     *
     * @param aFirstList A first list to check
     * @param aSecondList A second list to check
     * @return True if they are considered to be equal
     */
    public static boolean equals(final List<?> aFirstList, final List<?> aSecondList) {
        if (aFirstList == null && aSecondList != null && aSecondList.isEmpty()) {
            return true;
        }

        if (aSecondList == null && aFirstList != null && aFirstList.isEmpty()) {
            return true;
        }

        return Objects.equals(aFirstList, aSecondList);
    }
}
