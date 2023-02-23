
package info.freelibrary.util;

/**
 * A Marker class that contains markers that can influence the behavior of the Logger.
 */
public final class LoggerMarker {

    /** A constant that when supplied to MDC can control the formatting of messages. */
    public static final String EOL_TO_SPACE = "eol-to-space";

    /** A constant that when supplied to MDC can control the formatting of messages. */
    public static final String EOL_TO_CRLF = "eol-to-crlf";

    /** A constant that when supplied to MDC can control the formatting of messages. */
    public static final String EOL_TO_CR = "eol-to-cr";

    /** A constant that when supplied to MDC can control the formatting of messages. */
    public static final String EOL_TO_LF = "eol-to-lf";

    /** A constant that when supplied to MDC can condense whitespace in messages. */
    public static final String CONDENSE_WHITESPACE = "condense-whitespace";

    /**
     * Private constructor for the LoggerMarker utilities class.
     */
    private LoggerMarker() {
        // This is intentionally left empty.
    }
}
