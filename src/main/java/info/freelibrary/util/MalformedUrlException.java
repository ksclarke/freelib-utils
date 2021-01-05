
package info.freelibrary.util;

import java.net.MalformedURLException;
import java.util.Locale;

/**
 * A malformed URL exception that is a runtime, instead of checked, exception.
 */
public class MalformedUrlException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code> of MalformedUrlException.
     */
    private static final long serialVersionUID = 1977819328378028389L;

    /**
     * Creates a new malformed URL exception from the supplied URL string.
     *
     * @param aInvalidURLString An invalid URL string
     */
    public MalformedUrlException(final String aInvalidURLString) {
        super(MessageCodes.BUNDLE, MessageCodes.UTIL_068, aInvalidURLString);
    }

    /**
     * Creates a new malformed URL exception from the supplied URL string.
     *
     * @param aLocale A locale to use when rendering the exception message
     * @param aInvalidURLString An invalid URL string
     */
    public MalformedUrlException(final Locale aLocale, final String aInvalidURLString) {
        super(aLocale, MessageCodes.BUNDLE, MessageCodes.UTIL_068, aInvalidURLString);
    }

    /**
     * Creates a new malformed URL exception from the supplied MalformedURLException.
     *
     * @param aParentException An underlying MalformedURLException
     */
    public MalformedUrlException(final MalformedURLException aParentException) {
        super(aParentException);
    }

}
