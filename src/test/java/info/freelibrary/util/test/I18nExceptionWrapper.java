
package info.freelibrary.util.test;

import java.util.Locale;

import info.freelibrary.util.I18nException;

/**
 * A wrapper around I18nException that makes it easier to test.
 */
public class I18nExceptionWrapper extends I18nException {

    /**
     * The <code>serialVersionUID</code> for the <code>I18nExceptionWrapper</code>.
     */
    private static final long serialVersionUID = 2426773631767080860L;

    /**
     * Constructs a new <code>I18nException</code>.
     */
    public I18nExceptionWrapper() {
        super();
    }

    /**
     * Constructs a new <code>I18nException</code> using the supplied bundle details.
     *
     * @param aBundleName The name of a resource bundle to use
     * @param aMessageKey The message key to retrieve from the supplied bundle
     */
    public I18nExceptionWrapper(final String aBundleName, final String aMessageKey) {
        super(aBundleName, aMessageKey);
    }

    /**
     * Constructs a new <code>I18nException</code> using the supplied {@link Locale} and bundle details.
     *
     * @param aLocale The {@link Locale} to use when looking up the message key
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The message key whose value should be retrieved from the supplied bundle
     */
    public I18nExceptionWrapper(final Locale aLocale, final String aBundleName, final String aMessageKey) {
        super(aLocale, aBundleName, aMessageKey);
    }

    /**
     * Constructs a new <code>I18nException</code> with the supplied string as the message key and the supplied string
     * varargs as the message details.
     *
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The message key whose value should be retrieved from the supplied bundle
     * @param aVarargs The additional details to pass into the exception
     */
    public I18nExceptionWrapper(final String aBundleName, final String aMessageKey, final Object... aVarargs) {
        super(aBundleName, aMessageKey, aVarargs);
    }

    /**
     * Constructs a new <code>I18nException</code> with the supplied {@link Locale} as the locale, the supplied int as
     * the message key, and the supplied string varargs as the message details.
     *
     * @param aLocale The locale to use when constructing the exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     * @param aVarargs The additional details to pass into the exception
     */
    public I18nExceptionWrapper(final Locale aLocale, final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        super(aLocale, aBundleName, aMessageKey, aVarargs);
    }

    /**
     * Constructs a new <code>I18nException</code> with the supplied underlying cause.
     *
     * @param aCause The underlying cause of the current exception
     */
    public I18nExceptionWrapper(final Throwable aCause) {
        super(aCause);
    }

    /**
     * Constructs a new <code>I18nException</code> with the supplied cause and message.
     *
     * @param aCause The underlying cause of the current exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     */
    public I18nExceptionWrapper(final Throwable aCause, final String aBundleName, final String aMessageKey) {
        super(aCause, aBundleName, aMessageKey, new Object[] {});
    }

    /**
     * Constructs a new <code>I18nException</code> using the {@link Locale} with the supplied cause and message.
     *
     * @param aCause The underlying cause of the current exception
     * @param aLocale The locale to use when constructing the exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     */
    public I18nExceptionWrapper(final Throwable aCause, final Locale aLocale, final String aBundleName,
            final String aMessageKey) {
        super(aCause, aLocale, aBundleName, aMessageKey, new Object[] {});
    }

    /**
     * Constructs a new <code>I18nException</code> with the supplied cause, message, and additional details.
     *
     * @param aCause The underlying cause of the current exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     * @param aVarargs The additional details to add to the exception message
     */
    public I18nExceptionWrapper(final Throwable aCause, final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        super(aCause, aBundleName, aMessageKey, aVarargs);
    }

    /**
     * Constructs a new <code>I18nException</code> from the supplied {@link Locale} with the supplied cause, message,
     * and additional details.
     *
     * @param aCause The underlying cause of the current exception
     * @param aLocale The locale to use when constructing the exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     * @param aVarargs The additional details to add to the exception message
     */
    public I18nExceptionWrapper(final Throwable aCause, final Locale aLocale, final String aBundleName,
            final String aMessageKey, final Object... aVarargs) {
        super(aCause, aBundleName, aMessageKey, aVarargs);
    }

}
