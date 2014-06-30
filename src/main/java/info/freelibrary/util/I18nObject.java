/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.File;
import java.util.ResourceBundle;

/**
 * A generic object with baked-in I18N support. It wraps an {@link XMLResourceBundle} and provides an easy way to get
 * access to internationalized strings.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public abstract class I18nObject {

    private final XMLResourceBundle BUNDLE;

    /**
     * Generic constructor for the I18NObject.
     */
    public I18nObject() {
        BUNDLE = (XMLResourceBundle) ResourceBundle.getBundle("messages", new XMLBundleControl());
    }

    /**
     * Constructor for an I18NObject that takes a {@link ResourceBundle} as an argument.
     * 
     * @param aBundleName The name of a {@link ResourceBundle} that gets lower cased automatically
     */
    public I18nObject(final String aBundleName) {
        BUNDLE = (XMLResourceBundle) ResourceBundle.getBundle(aBundleName.toLowerCase(), new XMLBundleControl());
    }

    /**
     * Gets the internationalized value for the supplied message key.
     * 
     * @param aMessage A message key
     * @return An internationalized value
     */
    protected String getI18n(final String aMessage) {
        return StringUtils.normalizeWS(BUNDLE.get(aMessage));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a long as additional information.
     * 
     * @param aMessage A message key
     * @param aLongDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessage, final long aLongDetail) {
        return StringUtils.normalizeWS(BUNDLE.get(aMessage, Long.toString(aLongDetail)));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an int as additional information.
     * 
     * @param aMessage A message key
     * @param aIntDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessage, final int aIntDetail) {
        return StringUtils.normalizeWS(BUNDLE.get(aMessage, Integer.toString(aIntDetail)));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a string as additional information.
     * 
     * @param aMessage A message key
     * @param aDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessage, final String aDetail) {
        return StringUtils.normalizeWS(BUNDLE.get(aMessage, aDetail));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a string array as additional information.
     * 
     * @param aMessage A message key
     * @param aDetailsArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessage, final String... aDetailsArray) {
        return StringUtils.normalizeWS(BUNDLE.get(aMessage, aDetailsArray));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an exception as additional information.
     * 
     * @param aMessage A message key
     * @param aException Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessage, final Exception aException) {
        return StringUtils.normalizeWS(BUNDLE.get(aMessage, aException.getMessage()));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a file as additional information.
     * 
     * @param aMessage A message key
     * @param aFile Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessage, final File aFile) {
        return StringUtils.normalizeWS(BUNDLE.get(aMessage, aFile.getAbsolutePath()));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a file array as additional information.
     * 
     * @param aMessage A message key
     * @param aFileArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessage, final File... aFileArray) {
        final String[] fileNames = new String[aFileArray.length];

        for (int index = 0; index < fileNames.length; index++) {
            fileNames[index] = aFileArray[index].getAbsolutePath();
        }

        return StringUtils.normalizeWS(BUNDLE.get(aMessage, fileNames));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an object array as additional information.
     * 
     * @param aMessage A message key
     * @param aObjArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(final String aMessage, final Object... aObjArray) {
        final String[] strings = new String[aObjArray.length];

        for (int index = 0; index < aObjArray.length; index++) {
            if (aObjArray[index] instanceof File) {
                strings[index] = ((File) aObjArray[index]).getAbsolutePath();
            } else {
                strings[index] = aObjArray[index].toString();
            }
        }

        return StringUtils.normalizeWS(BUNDLE.get(aMessage, strings));
    }

    /**
     * Returns true if this I18N object contains the requested I18N key; else, false.
     * 
     * @param aKey A key to check to see if it exists
     * @return True if the key exists; else, false
     */
    protected boolean hasI18nKey(final String aKey) {
        return BUNDLE.containsKey(aKey);
    }
}
