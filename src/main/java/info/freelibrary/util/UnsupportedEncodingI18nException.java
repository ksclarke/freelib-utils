
package info.freelibrary.util;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * An I18n wrapper around <code>UnsupportedEncodingException</code>.
 */
public class UnsupportedEncodingI18nException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code>.
     */
    private static final long serialVersionUID = 6127985351515078608L;

    /**
     * Creates a new <code>UnsupportedEncodingI18nException</code>.
     *
     * @param aEncoding An unsupported encoding
     */
    public UnsupportedEncodingI18nException(final String aEncoding) {
        super(MessageCodes.BUNDLE, MessageCodes.UTIL_028, aEncoding);
    }

    /**
     * Creates a new <code>UnsupportedEncodingI18nException</code>.
     *
     * @param aEncoding An unsupported encoding
     */
    public UnsupportedEncodingI18nException(final Charset aEncoding) {
        super(MessageCodes.BUNDLE, MessageCodes.UTIL_028, aEncoding.displayName(Locale.getDefault()));
    }

    /**
     * Creates a new <code>UnsupportedEncodingI18nException</code>.
     *
     * @param aEncoding An unsupported encoding
     * @param aLocale A locale to use when getting the charset's name
     */
    public UnsupportedEncodingI18nException(final Charset aEncoding, final Locale aLocale) {
        super(MessageCodes.BUNDLE, MessageCodes.UTIL_028, aEncoding.displayName(aLocale));
    }

    /**
     * Creates a new <code>UnsupportedEncodingI18nException</code> from the supplied root cause.
     *
     * @param aCause The root cause of the exception
     * @param aEncoding An unsupported encoding
     */
    public UnsupportedEncodingI18nException(final Throwable aCause, final String aEncoding) {
        super(aCause, MessageCodes.BUNDLE, MessageCodes.UTIL_028, aEncoding);
    }

    /**
     * Creates a new <code>UnsupportedEncodingI18nException</code>.
     *
     * @param aCause The root cause of the exception
     * @param aEncoding An unsupported encoding
     */
    public UnsupportedEncodingI18nException(final Throwable aCause, final Charset aEncoding) {
        super(aCause, MessageCodes.BUNDLE, MessageCodes.UTIL_028, aEncoding.displayName(Locale.getDefault()));
    }

    /**
     * Creates a new <code>UnsupportedEncodingI18nException</code>.
     *
     * @param aCause The root cause of the exception
     * @param aEncoding An unsupported encoding
     * @param aLocale A locale to use when getting the charset's name
     */
    public UnsupportedEncodingI18nException(final Throwable aCause, final Charset aEncoding, final Locale aLocale) {
        super(aCause, MessageCodes.BUNDLE, MessageCodes.UTIL_028, aEncoding.displayName(aLocale));
    }

    /**
     * Creates a new <code>UnsupportedEncodingI18nException</code> from the supplied root cause.
     *
     * @param aBundleName The name of a different message bundle
     * @param aMessageKey A message key for the exception message
     * @param aVarargs Additional details to be inserted into the exception message
     */
    public UnsupportedEncodingI18nException(final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        super(aBundleName, aMessageKey, aVarargs);
    }

    /**
     * Creates a new <code>UnsupportedEncodingI18nException</code> from the supplied root cause.
     *
     * @param aCause The root cause of the exception
     * @param aBundleName The name of a different message bundle
     * @param aMessageKey A message key for the exception message
     * @param aVarargs Additional details to be inserted into the exception message
     */
    public UnsupportedEncodingI18nException(final Throwable aCause, final String aBundleName, final String aMessageKey,
            final Object... aVarargs) {
        super(aCause, aBundleName, aMessageKey, aVarargs);
    }

}
