
package info.freelibrary.util;

public class UnsupportedOperationI18nException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code>.
     */
    private static final long serialVersionUID = 6127985353515098008L;

    /**
     * Creates a new <code>UnsupportedOperationI18nException</code>.
     */
    public UnsupportedOperationI18nException() {
        super();
    }

    /**
     * Creates a new <code>UnsupportedOperationI18nException</code>.
     *
     * @param aMessage Details about the unsupported operation
     */
    public UnsupportedOperationI18nException(final String aMessage) {
        super(Constants.BUNDLE_NAME, MessageCodes.UTIL_067, aMessage);
    }

    /**
     * Creates a new <code>UnsupportedOperationI18nException</code> from the supplied root cause.
     *
     * @param aCause The root cause of the exception
     * @param aMessage Details about the unsupported operation
     */
    public UnsupportedOperationI18nException(final Throwable aCause, final String aMessage) {
        super(aCause, Constants.BUNDLE_NAME, MessageCodes.UTIL_067, aMessage);
    }

    /**
     * Creates a new <code>I18nUnsupportedOperationsException</code> from the supplied root cause.
     *
     * @param aBundleName The name of a different message bundle
     * @param aMessageKey A message key for the exception message
     * @param aVarargs Additional details to be inserted into the exception message
     */
    public UnsupportedOperationI18nException(final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        super(aBundleName, aMessageKey, aVarargs);
    }

    /**
     * Creates a new <code>UnsupportedOperationI18nException</code> from the supplied root cause.
     *
     * @param aCause The root cause of the exception
     * @param aBundleName The name of a different message bundle
     * @param aMessageKey A message key for the exception message
     * @param aVarargs Additional details to be inserted into the exception message
     */
    public UnsupportedOperationI18nException(final Throwable aCause, final String aBundleName,
            final String aMessageKey, final Object... aVarargs) {
        super(aCause, aBundleName, aMessageKey, aVarargs);
    }

}
