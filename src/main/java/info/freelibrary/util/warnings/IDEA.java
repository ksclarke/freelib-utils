
package info.freelibrary.util.warnings;

/**
 * Warnings specific to the IDEA IDE.
 */
public final class IDEA {

    /** Suppresses copy constructor warnings. */
    public static final String COPY_CONSTRUCTOR_MISSES_FIELD = "CopyConstructorMissesField";

    /** Constructors in constant classes should be private. */
    private IDEA() {
        // This is intentionally left empty
    }

}
