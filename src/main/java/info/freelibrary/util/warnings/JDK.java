
package info.freelibrary.util.warnings;

/**
 * Constants for suppressing checks.
 */
public final class JDK {

    /**
     * Warn about an auxiliary class that is hidden in a source file, and is used from other files.
     */
    public static final String AUXILIARY_CLASS = "auxiliaryclass";

    /**
     * Warn about use of unnecessary casts.
     */
    public static final String CAST = "cast";

    /**
     * Warn about issues related to classfile contents.
     */
    public static final String CLASSFILE = "classfile";

    /**
     * Warn about items marked as deprecated in JavaDoc but not using the @Deprecated annotation.
     */
    public static final String DEPRECATED_ANNO = "dep-ann";

    /**
     * Warn about use of deprecated items.
     */
    public static final String DEPRECATION = "deprecation";

    /**
     * Warn about division by constant integer 0.
     */
    public static final String DIV_BY_ZERO = "divzero";

    /**
     * Warn about empty statement after if.
     */
    public static final String EMPTY = "empty";

    /**
     * Warn about issues regarding module exports.
     */
    public static final String EXPORTS = "exports";

    /**
     * Warn about falling through from one case of a switch statement to the next.
     */
    public static final String FALLTHROUGH = "fallthrough";

    /**
     * Warn about finally clauses that do not terminate normally.
     */
    public static final String FINALLY = "finally";

    /**
     * Warn about module system related issues.
     */
    public static final String MODULE = "module";

    /**
     * Warn about issues regarding module opens.
     */
    public static final String OPENS = "opens";

    /**
     * Warn about issues relating to use of command line options.
     */
    public static final String OPTIONS = "options";

    /**
     * Warn about issues regarding method overloads.
     */
    public static final String OVERLOADS = "overloads";

    /**
     * Warn about issues regarding method overrides.
     */
    public static final String OVERRIDES = "overrides";

    /**
     * Warn about invalid path elements on the command line.
     */
    public static final String PATH = "path";

    /**
     * Warn about use of preview language features.
     */
    public static final String PREVIEW = "preview";

    /**
     * Warn about issues regarding annotation processing.
     */
    public static final String PROCESSING = "processing";

    /**
     * Warn about use of raw types.
     */
    public static final String RAW_TYPES = "rawtypes";

    /**
     * Warn about use of API that has been marked for removal.
     */
    public static final String REMOVAL = "removal";

    /**
     * Warn about use of automatic modules in the requires clauses.
     */
    public static final String REQUIRES_AUTOMATIC = "requires-automatic";

    /**
     * Warn about automatic modules in requires transitive.
     */
    public static final String REQUIRES_TRANSITIVE_AUTO = "requires-transitive-automatic";

    /**
     * Warn about Serializable classes that do not provide a serial version ID. Also warn about access to non-public
     * members from a serializable element.
     */
    public static final String SERIAL = "serial";

    /**
     * Warn about accessing a static member using an instance.
     */
    public static final String STATIC = "static";

    /**
     * Warn about issues relating to use of try blocks (i.e. try-with-resources).
     */
    public static final String TRY = "try";

    /**
     * Suppresses unchecked conversions.
     */
    public static final String UNCHECKED = "unchecked";

    /**
     * Warn about potentially unsafe varargs methods.
     */
    public static final String VARARGS = "varargs";

    /**
     * A private constructor for a constants class.
     */
    private JDK() {
        // This is intentionally left empty
    }

}
