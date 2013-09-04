/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.File;
import java.util.ResourceBundle;

/**
 * A generic object with baked-in I18N support. It wraps an
 * {@link XMLResourceBundle} and provides an easy way to get access to
 * internationalized strings.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public abstract class I18nObject {

    private final XMLResourceBundle BUNDLE;

    /**
     * Generic constructor for the I18NObject.
     */
    public I18nObject() {
        BUNDLE =
                (XMLResourceBundle) ResourceBundle.getBundle("Messages",
                        new XMLBundleControl());
    }

    /**
     * Constructor for an I18NObject that takes a {@link ResourceBundle} as an
     * argument.
     * 
     * @param aBundleName The name of a {@link ResourceBundle}
     */
    public I18nObject(String aBundleName) {
        BUNDLE =
                (XMLResourceBundle) ResourceBundle.getBundle(aBundleName,
                        new XMLBundleControl());
    }

    /**
     * Gets the internationalized value for the supplied message key.
     * 
     * @param aMessage A message key
     * @return An internationalized value
     */
    protected String getI18n(String aMessage) {
        return normalizeWS(BUNDLE.get(aMessage));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a
     * long as additional information.
     * 
     * @param aMessage A message key
     * @param aLongDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(String aMessage, long aLongDetail) {
        return normalizeWS(BUNDLE.get(aMessage, Long.toString(aLongDetail)));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an
     * int as additional information.
     * 
     * @param aMessage A message key
     * @param aIntDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(String aMessage, int aIntDetail) {
        return normalizeWS(BUNDLE.get(aMessage, Integer.toString(aIntDetail)));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a
     * string as additional information.
     * 
     * @param aMessage A message key
     * @param aDetail Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(String aMessage, String aDetail) {
        return normalizeWS(BUNDLE.get(aMessage, aDetail));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a
     * string array as additional information.
     * 
     * @param aMessage A message key
     * @param aDetailsArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(String aMessage, String[] aDetailsArray) {
        return normalizeWS(BUNDLE.get(aMessage, aDetailsArray));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an
     * exception as additional information.
     * 
     * @param aMessage A message key
     * @param aException Additional details for the message
     * @return The internationalized message
     */
    protected String getI18N(String aMessage, Exception aException) {
        return normalizeWS(BUNDLE.get(aMessage, aException.getMessage()));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a
     * file as additional information.
     * 
     * @param aMessage A message key
     * @param aFile Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(String aMessage, File aFile) {
        return normalizeWS(BUNDLE.get(aMessage, aFile.getAbsolutePath()));
    }

    /**
     * Gets the internationalized value for the supplied message key, using a
     * file array as additional information.
     * 
     * @param aMessage A message key
     * @param aFileArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(String aMessage, File[] aFileArray) {
        String[] fileNames = new String[aFileArray.length];

        for (int index = 0; index < fileNames.length; index++) {
            fileNames[index] = aFileArray[index].getAbsolutePath();
        }

        return normalizeWS(BUNDLE.get(aMessage, fileNames));
    }

    /**
     * Gets the internationalized value for the supplied message key, using an
     * object array as additional information.
     * 
     * @param aMessage A message key
     * @param aObjArray Additional details for the message
     * @return The internationalized message
     */
    protected String getI18n(String aMessage, Object[] aObjArray) {
        String[] strings = new String[aObjArray.length];

        for (int index = 0; index < aObjArray.length; index++) {
            if (aObjArray[index] instanceof File) {
                strings[index] = ((File) aObjArray[index]).getAbsolutePath();
            } else {
                strings[index] = aObjArray[index].toString();
            }
        }

        return normalizeWS(BUNDLE.get(aMessage, strings));
    }

    /**
     * Normalizes white space in the message value.
     * 
     * @param aMessage A message
     * @return The message with white space normalized
     */
    private String normalizeWS(String aMessage) {
        return aMessage.replaceAll("\\s+", " ");
    }
}
