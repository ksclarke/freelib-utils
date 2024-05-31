
package info.freelibrary.util;

/**
 * Creates a malformed URL runtime exception. The exception is intended to be used with configuration URLs rather than
 * URLs that are supplied by users. Validation of configuration input should be done at an earlier point.
 */
public class MalformedUrlException extends RuntimeException {

    /**
     * The <code>serialVersionUID</code> of a MalformedUrlException.
     */
    private static final long serialVersionUID = -4345212901234152387L;

    /**
     * Constructs a MalformedUrlException with the specified detail message.
     *
     * @param aMessage A detail message.
     */
    public MalformedUrlException(final String aMessage) {
        super(aMessage);
    }

}
