
package info.freelibrary.util;

import static info.freelibrary.util.Constants.BUNDLE_NAME;

public class UnsupportedEncodingException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code>.
     */
    private static final long serialVersionUID = 6127985351515078608L;

    /**
     * Creates a new <code>UnsupportedEncodingException</code>.
     *
     * @param aVarargs Additional details to be inserted into the exception message
     */
    public UnsupportedEncodingException(final Object... aVarargs) {
        super(BUNDLE_NAME, MessageCodes.UTIL_028, aVarargs);
    }

    /**
     * Creates a new <code>UnsupportedEncodingException</code> from the supplied root cause.
     *
     * @param aCause The root cause of the exception
     * @param aVarargs Additional details to be inserted into the exception message
     */
    public UnsupportedEncodingException(final Throwable aCause, final Object... aVarargs) {
        super(aCause, BUNDLE_NAME, MessageCodes.UTIL_028, aVarargs);
    }

    /**
     * Creates a new <code>UnsupportedEncodingException</code> from the supplied root cause.
     *
     * @param aCause The root cause of the exception
     * @param aMessageKey A message key for the exception message
     * @param aVarargs Additional details to be inserted into the exception message
     */
    public UnsupportedEncodingException(final Throwable aCause, final String aMessageKey, final Object... aVarargs) {
        super(aCause, BUNDLE_NAME, aMessageKey, aVarargs);
    }

}
