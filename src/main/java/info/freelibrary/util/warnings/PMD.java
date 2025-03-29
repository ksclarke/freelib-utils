
package info.freelibrary.util.warnings;

/**
 * Constants related to PMD validation rules. These don't actually work in the SuppressWarnings annotation, but they can
 * still be used to give an indication of what the <code>// NOPMD</code> comment is instructing PMD to ignore.
 */
@SuppressWarnings({ PMD.LONG_VARIABLE })
public final class PMD {

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#abstractclasswithoutabstractmethod */
    public static final String ABSTRACT_CLASS_WITHOUT_ABSTRACT_METHOD = "PMD.AbstractClassWithoutAbstractMethod";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#avoidcatchinggenericexception */
    public static final String AVOID_CATCHING_GENERIC_EXCEPTION = "PMD.AvoidCatchingGenericException";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_errorprone.html#avoidcatchingnpe */
    public static final String AVOID_CATCHING_NPE = "PMD.AvoidCatchingNPE";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#avoiddeeplynestedifstmts */
    public static final String AVOID_DEEPLY_NESTED_IF_STMTS = "PMD.AvoidDeeplyNestedIfStmts";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_errorprone.html#avoidduplicateliterals */
    public static final String AVOID_DUPLICATE_LITERALS = "PMD.AvoidDuplicateLiterals";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_performance.html#avoidfilestream */
    public static final String AVOID_FILE_STREAM = "PMD.AvoidFileStream";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_errorprone.html#avoidliteralsinifcondition */
    public static final String AVOID_LITERALS_IN_IF_CONDITION = "PMD.AvoidLiteralsInIfCondition";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#avoidreassigningloopvariables */
    public static final String AVOID_REASSIGNING_LOOP_VARIABLES = "PMD.AvoidReassigningLoopVariables";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#avoidrethrowingexception */
    public static final String AVOID_RETHROWING_EXCEPTION = "PMD.AvoidRethrowingException";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#avoidthrowingnullpointerexception */
    public static final String AVOID_THROWING_NULLPOINTEREXCEPTION = "PMD.AvoidThrowingNullPointerException";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#avoidthrowingrawexceptiontypes */
    public static final String AVOID_THROWING_RAW_EXCEPTION_TYPES = "PMD.AvoidThrowingRawExceptionTypes";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_codestyle.html#classnamingconventions */
    public static final String CLASS_NAMING_CONVENTIONS = "PMD.ClassNamingConventions";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#cognitivecomplexity */
    public static final String COGNITIVE_COMPLEXITY = "PMD.CognitiveComplexity";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_performance.html#consecutiveliteralappends */
    public static final String CONSECUTIVE_LITERAL_APPENDS = "PMD.ConsecutiveLiteralAppends";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_errorprone.html#constructorcallsoverridablemethod */
    public static final String CONSTRUCTOR_CALLS_OVERRIDABLE_METHOD = "PMD.ConstructorCallsOverridableMethod";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#couplingbetweenobjects */
    public static final String COUPLING_BETWEEN_OBJECTS = "PMD.CouplingBetweenObjects";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#cyclomaticcomplexity */
    public static final String CYCLOMATIC_COMPLEXITY = "PMD.CyclomaticComplexity";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#dataclass */
    public static final String DATA_CLASS = "PMD.DataClass";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_codestyle.html#emptymethodinabstractclassshouldbeabstract */
    public static final String EMPTY_METHOD_IN_ABSTRACT_CLASS_SHOULD_BE_ABSTRACT =
            "PMD.EmptyMethodInAbstractClassShouldBeAbstract";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_apex_design.html#excessiveclasslength */
    public static final String EXCESSIVE_CLASS_LENGTH = "PMD.ExcessiveClassLength";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#excessiveimports */
    public static final String EXCESSIVE_IMPORTS = "PMD.ExcessiveImports";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_apex_design.html#excessivemethodlength */
    public static final String EXCESSIVE_METHOD_LENGTH = "PMD.ExcessiveMethodLength";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#excessivepubliccount */
    public static final String EXCESSIVE_PUBLIC_COUNT = "PMD.ExcessivePublicCount";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#forloopcanbeforeach */
    public static final String FOR_LOOP_CAN_BE_FOR_EACH = "PMD.ForLoopCanBeForeach";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#godclass */
    public static final String GOD_CLASS = "PMD.GodClass";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#implicitfunctionalinterface */
    public static final String IMPLICIT_FUNCTIONAL_INTERFACE = "PMD.ImplicitFunctionalInterface";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_errorprone.html#invalidlogmessageformat */
    public static final String INVALID_LOG_MESSAGE_FORMAT = "PMD.InvalidLogMessageFormat";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_codestyle.html#longvariable */
    public static final String LONG_VARIABLE = "PMD.LongVariable";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#loosecoupling */
    public static final String LOOSE_COUPLING = "PMD.LooseCoupling";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#missingoverride */
    public static final String MISSING_OVERRIDE = "PMD.MissingOverride";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_errorprone.html#morethanonelogger */
    public static final String MORE_THAN_ONE_LOGGER = "PMD.MoreThanOneLogger";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#npathcomplexity */
    public static final String N_PATH_COMPLEXITY = "PMD.NPathComplexity";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#ncsscount */
    public static final String NCSS_COUNT = "PMD.NcssCount";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_errorprone.html#nullassignment */
    public static final String NULL_ASSIGNMENT = "PMD.NullAssignment";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#preservestacktrace */
    public static final String PRESERVE_STACK_TRACE = "PMD.PreserveStackTrace";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#signaturedeclarethrowsexception */
    public static final String SIGNATURE_DECLARE_THROWS_EXCEPTION = "PMD.SignatureDeclareThrowsException";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#simplifybooleanreturns */
    public static final String SIMPLIFY_BOOLEAN_RETURNS = "PMD.SimplifyBooleanReturns";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_errorprone.html#suspiciousequalsmethodname */
    public static final String SUSPICIOUS_EQUALS_METHOD_NAME = "PMD.SuspiciousEqualsMethodName";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#switchdensity */
    public static final String SWITCH_DENSITY = "PMD.SwitchDensity";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#systemprintln */
    public static final String SYSTEM_PRINTLN = "PMD.SystemPrintln";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#toomanyfields */
    public static final String TOO_MANY_FIELDS = "PMD.TooManyFields";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_design.html#toomanymethods */
    public static final String TOO_MANY_METHODS = "PMD.TooManyMethods";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_codestyle.html#toomanystaticimports */
    public static final String TOO_MANY_STATIC_IMPORTS = "PMD.TooManyStaticImports";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_codestyle.html#unnecessaryboxing */
    public static final String UNNECESSARY_BOXING = "PMD.UnnecessaryBoxing";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#unusedassignment */
    public static final String UNUSED_ASSIGNMENT = "PMD.UnusedAssignment";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#unusedformalparameter */
    public static final String UNUSED_FORMAL_PARAMETER = "PMD.UnusedFormalParameter";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#unusedlocalvariable */
    public static final String UNUSED_LOCAL_VARIABLE = "PMD.UnusedLocalVariable";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#unusedprivatefield */
    public static final String UNUSED_PRIVATE_FIELD = "PMD.UnusedPrivateField";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#unusedprivatemethod */
    public static final String UNUSED_PRIVATE_METHOD = "PMD.UnusedPrivateMethod";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_codestyle.html#usediamondoperator */
    public static final String USE_DIAMOND_OPERATOR = "PMD.UseDiamondOperator";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#usetrywithresources */
    public static final String USE_TRY_WITH_RESOURCES = "PMD.UseTryWithResources";

    /** Cf. https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html#usevarargs */
    public static final String USE_VARARGS = "PMD.UseVarargs";

    /**
     * Constant classes have private constructors.
     */
    private PMD() {
        // This is intentionally left empty
    }

}
