
package info.freelibrary.util.warnings;

/**
 * Sonar warnings.
 */
public final class Sonar {

    /** The Sonar warning for cognitive complexity. */
    public static final String COGNITIVE_COMPLEXITY = "java:S3776";

    /** The Sonar warning for too many inheritance levels. */
    public static final String INHERITANCE_TREE = "java:S110";

    /** The Sonar warning for a monster class. */
    public static final String MONSTER_CLASS = "java:S6539";

    /** The Sonar warning for tests that should be parameterized. */
    public static final String PARAMETERIZE_TEST = "java:S5976";

    /** The Sonar warning for the use of a singleton. */
    public static final String SINGLETON_USE = "java:S6548";

    /**
     * Creates a new Sonar constants class.
     */
    private Sonar() {
        // This is intentionally left empty
    }

}
