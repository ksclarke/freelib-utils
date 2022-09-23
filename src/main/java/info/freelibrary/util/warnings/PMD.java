
package info.freelibrary.util.warnings;

/**
 * Constants related to PMD validation rules. These don't actually work in the SuppressWarnings annotation, but they can
 * still be used to give an indication of what the <code>// NOPMD</code> comment is instructing PMD to ignore.
 */
public final class PMD {

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_bestpractices.html#avoidreassigningloopvariables
     */
    public static final String AVOID_REASSIGNING_LOOP_VARIABLES = "PMD.AvoidReassigningLoopVariables";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_bestpractices.html#abstractclasswithoutabstractmethod
     */
    @SuppressWarnings("PMD.LongVariable")
    public static final String ABSTRACT_CLASS_WITHOUT_ABSTRACT_METHOD = "PMD.AbstractClassWithoutAbstractMethod";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_design.html#avoiddeeplynestedifstmts
     */
    public static final String AVOID_DEEPLY_NESTED_IF_STMTS = "PMD.AvoidDeeplyNestedIfStmts";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_codestyle.html#classnamingconventions
     */
    public static final String CLASS_NAMING_CONVENTIONS = "PMD.ClassNamingConventions";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_design.html#cyclomaticcomplexity
     */
    public static final String CYCLOMATIC_COMPLEXITY = "PMD.CyclomaticComplexity";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_design.html#excessiveimports
     */
    public static final String EXCESSIVE_IMPORTS = "PMD.ExcessiveImports";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_design.html#excessivepubliccount
     */
    public static final String EXCESSIVE_PUBLIC_COUNT = "PMD.ExcessivePublicCount";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_design.html#godclass
     */
    public static final String GOD_CLASS = "PMD.GodClass";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_codestyle.html#longvariable
     */
    public static final String LONG_VARIABLE = "PMD.LongVariable";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_bestpractices.html#missingoverride
     */
    public static final String MISSING_OVERRIDE = "PMD.MissingOverride";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_design.html#npathcomplexity
     */
    public static final String N_PATH_COMPLEXITY = "PMD.NPathComplexity";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_errorprone.html#nullassignment
     */
    public static final String NULL_ASSIGNMENT = "PMD.NullAssignment";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_bestpractices.html#preservestacktrace
     */
    public static final String PRESERVE_STACK_TRACE = "PMD.PreserveStackTrace";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_design.html#toomanymethods
     */
    public static final String TOO_MANY_METHODS = "PMD.TooManyMethods";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_bestpractices.html#unusedformalparameter
     */
    public static final String UNUSED_FORMAL_PARAMETER = "PMD.UnusedFormalParameter";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_bestpractices.html#unusedprivatemethod
     */
    public static final String UNUSED_PRIVATE_METHOD = "PMD.UnusedPrivateMethod";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_design.html#cognitivecomplexity
     */
    public static final String COGNITIVE_COMPLEXITY = "PMD.CognitiveComplexity";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_performance.html#avoidfilestream
     */
    public static final String AVOID_FILE_STREAM = "PMD.AvoidFileStream";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_errorprone.html#avoidliteralsinifcondition
     */
    public static final String AVOID_LITERALS_IN_IF_CONDITION = "PMD.AvoidLiteralsInIfCondition";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_errorprone.html#morethanonelogger
     */
    public static final String MORE_THAN_ONE_LOGGER = "PMD.MoreThanOneLogger";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_performance.html#consecutiveliteralappends
     */
    public static final String CONSECUTIVE_LITERAL_APPENDS = "PMD.ConsecutiveLiteralAppends";

    /**
     * Cf. https://pmd.github.io/latest/pmd_rules_java_errorprone.html#avoidduplicateliterals
     */
    public static final String AVOID_DUPLICATE_LITERALS = "PMD.AvoidDuplicateLiterals";

    /*
     * Constant classes have private constructors.
     */
    private PMD() {
    }

}
