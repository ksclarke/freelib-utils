
package info.freelibrary.util;

import info.freelibrary.util.warnings.PMD;

/**
 * An interface for enumerations that standardizes accessing the enum's label.
 */
@SuppressWarnings({ PMD.IMPLICIT_FUNCTIONAL_INTERFACE })
public interface Labeled {

    /**
     * Gets the enumeration's label.
     *
     * @return A label
     */
    String label();
}
