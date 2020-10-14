
package info.freelibrary.util;

/**
 * An I18n wrapper around <code>IllegalArgumentException</code>.
 */
public class IllegalArgumentI18nException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code> for IllegalArgumentI18nException.
     */
    private static final long serialVersionUID = -1524376662801564253L;

    /**
     * Creates a new <code>IllegalArgumentI18nException</code>.
     *
     * @param aObject The object that was an illegal argument
     */
    public IllegalArgumentI18nException(final Object aObject) {
        super(Constants.BUNDLE_NAME, MessageCodes.UTIL_065, aObject.getClass().getName(), aObject);
    }

    /**
     * Creates a new <code>IllegalArgumentI18nException</code> from the supplied root cause.
     *
     * @param aCause The root cause of the exception
     * @param aObject The object that was an illegal argument
     */
    public IllegalArgumentI18nException(final Throwable aCause, final Object aObject) {
        super(aCause, Constants.BUNDLE_NAME, MessageCodes.UTIL_065, aObject.getClass().getName(), aObject);
    }

    /**
     * Creates a new <code>IllegalArgumentI18nException</code> using a message bundle and key that are different from
     * the default ones.
     *
     * @param aBundleName The name of a different message bundle
     * @param aMessageKey A message (or message key) for the exception message
     * @param aVarargs Additional details about the illegal argument
     */
    public IllegalArgumentI18nException(final String aBundleName, final String aMessageKey, final Object... aVarargs) {
        super(aBundleName, aMessageKey, aVarargs);
    }

    /**
     * Creates a new <code>IllegalArgumentI18nException</code> from the supplied root cause using a message bundle and
     * key that are different from the default ones.
     *
     * @param aCause The root cause of the exception
     * @param aBundleName The name of a different message bundle
     * @param aMessageKey A message (or message key) for the exception message
     * @param aVarargs Additional details about the illegal argument
     */
    public IllegalArgumentI18nException(final Throwable aCause, final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        super(aCause, aBundleName, aMessageKey, aVarargs);
    }
}
