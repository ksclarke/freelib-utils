
package info.freelibrary.util.warnings;

/**
 * Checkstyle warnings.
 */
public final class Checkstyle {

    /** Cf. <a href="https://checkstyle.sourceforge.io/checks/imports/avoidstarimport.html">Checkstyle</a>. */
    public static final String AVOID_STAR_IMPORT = "checkstyle:AvoidStarImport";

    /**
     * Cf. <a href="https://checkstyle.sourceforge.io/checks/metrics/booleanexpressioncomplexity.html">Checkstyle</a>.
     */
    public static final String BOOLEAN_EXPR_COMPLEXITY = "checkstyle:BooleanExpressionComplexity";

    /** Cf. <a href="https://checkstyle.sourceforge.io/checks/naming/membername.html">Checkstyle</a>. */
    public static final String MEMBER_NAME = "checkstyle:MemberName";

    /** Cf. <a href="https://checkstyle.sourceforge.io/checks/coding/multiplestringliterals.html">Checkstyle</a>. */
    public static final String MULTIPLE_STRING_LITERALS = "checkstyle:MultipleStringLiterals";

    /** Cf. <a href="https://checkstyle.sourceforge.io/checks/misc/uncommentedmain.html">Checkstyle</a>. */
    public static final String UNCOMMENTED_MAIN = "checkstyle:UncommentedMain";

    /**
     * Creates a new Checkstyle warnings class.
     */
    private Checkstyle() {
        // This is intentionally left empty.
    }

}
