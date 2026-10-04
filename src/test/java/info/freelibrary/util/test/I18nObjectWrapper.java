
package info.freelibrary.util.test;

import java.util.Locale;

import info.freelibrary.util.I18nObject;

/**
 * A wrapper to make testing I18nObject easier.
 */
public class I18nObjectWrapper extends I18nObject {

    /**
     * A resource bundle base name.
     */
    private static final String BUNDLE_NAME = "test_freelib-utils_messages";

    /**
     * Generic constructor for the I18NObject.
     */
    public I18nObjectWrapper() {
        super(BUNDLE_NAME);
    }

    /**
     * Generic constructor for the I18nObject.
     *
     * @param aName A resource bundle name
     */
    public I18nObjectWrapper(final String aName) {
        super(aName);
    }

    /**
     * Constructor for the I18nObject that takes a resource bundle name and locale.
     *
     * @param aName A resource bundle name
     * @param aLocale A locale
     */
    public I18nObjectWrapper(final String aName, final Locale aLocale) {
        super(aName, aLocale);
    }

    /**
     * Constructor for the I18nObject that takes a locale.
     *
     * @param aLocale A locale
     */
    public I18nObjectWrapper(final Locale aLocale) {
        super(BUNDLE_NAME, aLocale);
    }

    /**
     * Gets the number of keys in the bundle.
     */
    @Override
    public int countKeys() {
        return super.countKeys();
    }

    /**
     * Gets the internationalized message for the supplied locale and message key with details.
     *
     * @param aLocale A locale
     * @param aMessageKey A message key
     * @param aDetails Details for formatting
     * @return The formatted message
     */
    @Override
    public String getI18n(final Locale aLocale, final String aMessageKey, final Object... aDetails) {
        return super.getI18n(aLocale, aMessageKey, aDetails);
    }
}
