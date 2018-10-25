
package info.freelibrary.util;

import static info.freelibrary.util.Constants.BUNDLE_NAME;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * A runtime exception that wraps an I18n resource bundle.
 */
public class I18nRuntimeException extends RuntimeException {

    /**
     * The <code>serialVersionUID</code> for the <code>I18nException</code>.
     */
    private static final long serialVersionUID = 1137212882896281357L;

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nRuntimeException.class, BUNDLE_NAME);

    /**
     * Constructs a new <code>RuntimeI18nException</code>.
     */
    public I18nRuntimeException() {
        super();
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> using the supplied bundle details.
     *
     * @param aBundleName The name of a resource bundle to use
     * @param aMessageKey The message key to retrieve from the supplied bundle
     */
    public I18nRuntimeException(final String aBundleName, final String aMessageKey) {
        super(format(aBundleName, aMessageKey, (Object[]) new String[] {}));
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> using the supplied {@link Locale} and bundle details.
     *
     * @param aLocale The {@link Locale} to use when looking up the message key
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The message key whose value should be retrieved from the supplied bundle
     */
    public I18nRuntimeException(final Locale aLocale, final String aBundleName, final String aMessageKey) {
        super(format(aLocale, aBundleName, aMessageKey, new Object[] {}));
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> with the supplied string as the message key and the supplied
     * string varargs as the message details.
     *
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The message key whose value should be retrieved from the supplied bundle
     * @param aVarargs The additional details to pass into the exception
     */
    public I18nRuntimeException(final String aBundleName, final String aMessageKey, final Object... aVarargs) {
        super(format(aBundleName, aMessageKey, aVarargs));
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> with the supplied {@link Locale} as the locale, the supplied
     * int as the message key, and the supplied string varargs as the message details.
     *
     * @param aLocale The locale to use when constructing the exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     * @param aVarargs The additional details to pass into the exception
     */
    public I18nRuntimeException(final Locale aLocale, final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        super(format(aLocale, aBundleName, aMessageKey, aVarargs));
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> with the supplied underlying cause.
     *
     * @param aCause The underlying cause of the current exception
     */
    public I18nRuntimeException(final Throwable aCause) {
        super(aCause);
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> with the supplied cause and message.
     *
     * @param aCause The underlying cause of the current exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     */
    public I18nRuntimeException(final Throwable aCause, final String aBundleName, final String aMessageKey) {
        super(format(aBundleName, aMessageKey, new Object[] {}), aCause);
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> using the {@link Locale} with the supplied cause and
     * message.
     *
     * @param aCause The underlying cause of the current exception
     * @param aLocale The locale to use when constructing the exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     */
    public I18nRuntimeException(final Throwable aCause, final Locale aLocale, final String aBundleName,
            final String aMessageKey) {
        super(format(aLocale, aBundleName, aMessageKey, new Object[] {}), aCause);
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> with the supplied cause, message, and additional details.
     *
     * @param aCause The underlying cause of the current exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     * @param aVarargs The additional details to add to the exception message
     */
    public I18nRuntimeException(final Throwable aCause, final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        super(format(aBundleName, aMessageKey, aVarargs), aCause);
    }

    /**
     * Constructs a new <code>RuntimeI18nException</code> from the supplied {@link Locale} with the supplied cause,
     * message, and additional details.
     *
     * @param aCause The underlying cause of the current exception
     * @param aLocale The locale to use when constructing the exception
     * @param aBundleName The name of the resource bundle to use
     * @param aMessageKey The key to use when looking up the message
     * @param aVarargs The additional details to add to the exception message
     */
    public I18nRuntimeException(final Throwable aCause, final Locale aLocale, final String aBundleName,
            final String aMessageKey, final Object... aVarargs) {
        super(format(aLocale, aBundleName, aMessageKey, aVarargs), aCause);
    }

    /**
     * Constructs our I18n exception message using the supplied bundle name.
     *
     * @param aBundleName A name of a bundle in which to look up the supplied key.
     * @param aMessageKey A key value to look up in the <code>ResourceBundle</code>.
     * @param aVarargs Additional details to use in formatting the message.
     * @return A formatted exception message
     */
    private static String format(final String aBundleName, final String aMessageKey, final Object... aVarargs) {
        return format(null, aBundleName, aMessageKey, aVarargs);
    }

    /**
     * Constructs our I18n exception message using the supplied {@link Locale} and bundle name.
     *
     * @param aLocale A locale that should be used for the lookup of the message key.
     * @param aBundleName A name of a bundle in which to look up the supplied key.
     * @param aMessageKey A key value to look up in the <code>ResourceBundle</code>.
     * @param aVarargs Additional details to use in formatting the message.
     * @return A formatted exception message
     */
    private static String format(final Locale aLocale, final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        Objects.requireNonNull(aBundleName, LOGGER.getI18n(MessageCodes.UTIL_016));
        Objects.requireNonNull(aMessageKey, LOGGER.getI18n(MessageCodes.UTIL_018));
        LOGGER.debug(MessageCodes.UTIL_022, aBundleName);

        final XMLResourceBundle bundle;

        if (aLocale != null) {
            LOGGER.debug(MessageCodes.UTIL_019, aLocale.toString());
            bundle = (XMLResourceBundle) ResourceBundle.getBundle(aBundleName, aLocale, new XMLBundleControl());
        } else {
            bundle = (XMLResourceBundle) ResourceBundle.getBundle(aBundleName, new XMLBundleControl());
        }

        if (aVarargs != null && aVarargs.length > 0) {
            LOGGER.debug(MessageCodes.UTIL_020, aMessageKey, aVarargs, aVarargs.getClass().getSimpleName());
            return bundle.get(aMessageKey, aVarargs);
        } else {
            LOGGER.debug(MessageCodes.UTIL_021, aMessageKey);
            return bundle.get(aMessageKey);
        }
    }

}
