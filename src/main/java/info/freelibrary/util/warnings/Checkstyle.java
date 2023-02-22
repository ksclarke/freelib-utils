
package info.freelibrary.util.warnings;

/**
 * Checkstyle warnings.
 */
public final class Checkstyle {

    /**
     * Cf. https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport
     */
    public static final String AVOID_STAR_IMPORT = "checkstyle:AvoidStarImport";

    /**
     * Cf. https://checkstyle.sourceforge.io/config_coding.html#MultipleStringLiterals
     */
    public static final String MULTIPLE_STRING_LITERALS = "checkstyle:MultipleStringLiterals";

    /**
     * Cf. https://checkstyle.sourceforge.io/config_metrics.html#BooleanExpressionComplexity
     */
    public static final String BOOLEAN_EXPR_COMPLEXITY = "checkstyle:BooleanExpressionComplexity";

    /**
     * Cf. https://checkstyle.sourceforge.io/config_naming.html#MemberName
     */
    public static final String MEMBER_NAME = "checkstyle:MemberName";

    /**
     * Creates a new Checkstyle warnings class.
     */
    private Checkstyle() {
        // This is intentionally left empty.
    }

}
