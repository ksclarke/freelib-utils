package info.freelibrary.util;

import java.io.File;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * A generic object with baked-in &quot;default Locale&quot; I18N support. It wraps a {@link ResourceBundle} and
 * provides an easy way to get access to internationalized strings.
 */
public class I18nObject {

    /** The internationalized object's internal resource bundle. */
    private final I18nResourceBundle myBundle;

    /** The base name of the internationalized object's resource bundle. */
    private final String myBundleName;

    /**
     * Constructor for an I18nObject that takes a {@link ResourceBundle} name as an argument. The name should be
     * something specific to the package that's extending the {@code I18nObject}.
     *
     * @param aBundleName The name of a {@link ResourceBundle} that gets lower cased automatically
     */
    public I18nObject(final String aBundleName) {
        myBundleName = Objects.requireNonNull(aBundleName);
        myBundle = (I18nResourceBundle) bundleFor(aBundleName, Locale.getDefault());
    }

    /**
     * Constructor for an I18nObject that takes a {@link ResourceBundle} name as an argument. The name should be
     * something specific to the package that's extending the {@code I18nObject}.
     *
     * @param aBundleName The name of a {@link ResourceBundle} that gets lower cased automatically
     * @param aLocale The locale of the desired bundle.
     */
    public I18nObject(final String aBundleName, final Locale aLocale) {
        myBundleName = Objects.requireNonNull(aBundleName);
        myBundle = (I18nResourceBundle) bundleFor(aBundleName, aLocale);
    }

    /**
     * Gets the internationalized value for the supplied message key.
     *
     * @param aMessageKey A message key
     * @return An internationalized value
     */
    protected String getI18n(final String aMessageKey) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a long as additional information.
     *
     * @param aMessageKey A message key
     * @param aLongDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessageKey, final long aLongDetail) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey, Long.toString(aLongDetail)));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an int as additional information.
     *
     * @param aMessageKey A message key
     * @param aIntDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessageKey, final int aIntDetail) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey, Integer.toString(aIntDetail)));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a string as additional information.
     *
     * @param aMessageKey A message key
     * @param aDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessageKey, final String aDetail) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey, aDetail));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a string array as additional information.
     *
     * @param aMessageKey A message key
     * @param aDetailsArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessageKey, final String... aDetailsArray) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey, aDetailsArray));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an exception as additional information.
     *
     * @param aMessageKey A message key
     * @param aException Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessageKey, final Exception aException) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey, aException.getMessage()));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a file as additional information.
     *
     * @param aMessageKey A message key
     * @param aFile Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessageKey, final File aFile) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey, aFile.getAbsolutePath()));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a file array as additional information.
     *
     * @param aMessageKey A message key
     * @param aFileArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessageKey, final File... aFileArray) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey, detailsToStrings((Object[]) aFileArray)));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an object array as additional information.
     *
     * @param aMessageKey A message key
     * @param aObjArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessageKey, final Object... aObjArray) {
        return StringUtils.normalizeWS(myBundle.get(aMessageKey, detailsToStrings(aObjArray)));
    }

    /**
     * Gets the internationalized value for the supplied message key and locale, using an object array as additional
     * information.
     *
     * @param aLocale A locale for the message
     * @param aMessageKey A message key
     * @param aDetails Additional details for the message
     * @return The internationalized message
     */
    public String getI18n(final Locale aLocale, final String aMessageKey, final Object... aDetails) {
        return getI18n(bundleFor(aLocale), aMessageKey, aDetails);
    }

    /**
     * Gets the internationalized value for the supplied bundle name, locale, and message key, using an object array as
     * additional information.
     *
     * @param aMessageBundle A message bundle name
     * @param aLocale A locale for the message
     * @param aMessageKey A message key
     * @param aDetails Additional details for the message
     * @return The internationalized message
     */
    public String getI18n(final String aMessageBundle, final Locale aLocale, final String aMessageKey,
      final Object... aDetails) {
        return getI18n(bundleFor(aMessageBundle, aLocale), aMessageKey, aDetails);
    }

    /**
     * Gets the internationalized message for the supplied locale and key.
     *
     * @param aLocale A locale for the message
     * @param aKey A message key
     * @return The internationalized message
     */
    public String getI18n(final Locale aLocale, final String aKey) {
        Objects.requireNonNull(aKey);
        return StringUtils.normalizeWS(bundleFor(aLocale).getString(aKey));
    }

    /**
     * Gets the internationalized message for the supplied bundle name, locale, and key.
     *
     * @param aBundleName A bundle name
     * @param aLocale A locale for the message
     * @param aKey A message key
     * @return The internationalized message
     */
    public static String getI18n(final String aBundleName, final Locale aLocale, final String aKey) {
        Objects.requireNonNull(aKey);
        return StringUtils.normalizeWS(bundleFor(aBundleName, aLocale).getString(aKey));
    }

    /**
     * Internal method to get the internationalized value from the supplied bundle, using an object array as additional
     * information.
     *
     * @param aBundle A resource bundle
     * @param aMessageKey A message key
     * @param aDetails Additional details for the message
     * @return The internationalized message
     */
    private static String getI18n(final ResourceBundle aBundle, final String aMessageKey, final Object... aDetails) {
        Objects.requireNonNull(aBundle);
        Objects.requireNonNull(aMessageKey);
        final String[] strings = detailsToStrings(aDetails);

        if (aBundle instanceof I18nResourceBundle) {
            return StringUtils.normalizeWS(((I18nResourceBundle) aBundle).get(aMessageKey, strings));
        }

        return StringUtils.normalizeWS(StringUtils.format(aBundle.getString(aMessageKey), strings));
    }

    /**
     * Converts message details to strings for message formatting.
     *
     * @param aDetails Message details
     * @return String versions of the supplied details
     */
    private static String[] detailsToStrings(final Object... aDetails) {
        Objects.requireNonNull(aDetails);
        final String[] strings = new String[aDetails.length];

        for (int index = 0; index < aDetails.length; index++) {
            if (aDetails[index] instanceof File file) {
                strings[index] = file.getAbsolutePath();
            } else if (aDetails[index] != null) {
                strings[index] = aDetails[index].toString();
            }
        }

        return strings;
    }

    /**
     * Internal static method to get the resource bundle for the supplied bundle name and locale.
     *
     * @param aBundleName A bundle name
     * @param aLocale A locale
     * @return The resource bundle
     */
    private static ResourceBundle bundleFor(final String aBundleName, final Locale aLocale) {
        Objects.requireNonNull(aBundleName);
        Objects.requireNonNull(aLocale);
        return ResourceBundle.getBundle(aBundleName.toLowerCase(Locale.ENGLISH), aLocale, new CustomBundleControl());
    }

    /**
     * Internal method to get the resource bundle for the supplied locale using the instance's bundle name.
     *
     * @param aLocale A locale
     * @return The resource bundle
     * @throws IllegalStateException If the bundle name has not been set
     */
    private ResourceBundle bundleFor(final Locale aLocale) {
        Objects.requireNonNull(aLocale);

        if (myBundleName == null) {
            throw new IllegalStateException();
        }

        return bundleFor(myBundleName, aLocale);
    }

    /**
     * Returns true if this I18N object contains the requested I18N key; else, false.
     *
     * @param aMessageKey A key to check to see if it exists
     * @return True if the key exists; else, false
     */
    protected boolean hasI18nKey(final String aMessageKey) {
        return myBundle != null && aMessageKey != null && myBundle.containsKey(aMessageKey);
    }

    /**
     * Returns the number of keys known to this object.
     *
     * @return The number of keys known to this object
     */
    protected int countKeys() {
        return myBundle.countKeys();
    }

    /**
     * Returns an enumeration of this object's keys.
     *
     * @return An enumeration of I18n keys
     */
    protected Enumeration<String> getKeys() {
        return myBundle.getKeys();
    }
}
