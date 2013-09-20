/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * A {@link ResourceBundle} that uses a XML file as its source.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class XMLResourceBundle extends ResourceBundle {

    /**
     * The properties set by the XML file.
     */
    private Properties myProperties;

    /**
     * Constructor that allows {@link ResourceBundle}s to be backed by XML
     * files.
     * 
     * @param aInStream An XML {@link InputStream}
     * @throws IOException If there is trouble reading from the XML file
     */
    XMLResourceBundle(InputStream aInStream) throws IOException {
        myProperties = new Properties();
        myProperties.loadFromXML(aInStream);
    }

    /**
     * Handles getting the value of the supplied key.
     * 
     * @param aKey The key from which to lookup the value
     * @return The value of the supplied property key
     */
    protected Object handleGetObject(String aKey) {
        return myProperties.getProperty(aKey);
    }

    /**
     * Returns an {@link Enumeration} of property keys.
     * 
     * @return An {@link Enumeration} of property keys.
     */
    public Enumeration<String> getKeys() {
        final Enumeration<?> enumeration = myProperties.elements();

        return new Enumeration<String>() {

            /**
             * Returns whether the {@link Enumeration} has more elements.
             * 
             * @return True if the {@link Enumeration} has more elements; else,
             *         false.
             */
            public boolean hasMoreElements() {
                return enumeration.hasMoreElements();
            }

            /**
             * Returns the next element in the {@link Enumeration}
             * 
             * @return The next element in the {@link Enumeration}
             */
            public String nextElement() {
                return (String) enumeration.nextElement();
            }
        };
    }

    /**
     * Return a message value with the supplied values integrated into it.
     * 
     * @param aMessage A message in which to include the supplied string values
     * @param aArray The string values to insert into the supplied message
     * @return A message with the supplied values integrated into it
     */
    public String get(String aMessage, String... aArray) {
        return StringUtils.format(super.getString(aMessage), aArray);
    }

    /**
     * Return a message value with the supplied string integrated into it.
     * 
     * @param aMessage A message in which to include the supplied string value
     * @param aDetail A string value to be included into the supplied message
     * @return A message with the supplied string value integrated into it
     */
    public String get(String aMessage, String aDetail) {
        return StringUtils.format(super.getString(aMessage), aDetail);
    }

    /**
     * Returns the string form of the requested message.
     * 
     * @param aMessage The key of the message to be returned
     * @return The value of the requested message
     */
    public String get(String aMessage) {
        return getString(aMessage);
    }

    /**
     * Returns the string form of the requested message with the file values
     * integrated into it.
     * 
     * @param aMessage A message key to use to return a message value
     * @param aFileArray An array of files, whose names should be integrated
     *        into the message value
     * @return The message value with the supplied file names integrated
     */
    public String get(String aMessage, File... aFileArray) {
        String[] strings = new String[aFileArray.length];

        for (int index = 0; index < strings.length; index++) {
            strings[index] = aFileArray[index].getAbsolutePath();
        }

        return StringUtils.format(super.getString(aMessage), strings);
    }

    /**
     * Returns the string form of the requested message with the file value
     * integrated into it.
     * 
     * @param aMessage A message key to use to return a message value
     * @param aFile A file whose name should be integrated into the message
     *        value
     * @return The message value with the supplied file name integrated
     */
    public String get(String aMessage, File aFile) {
        return StringUtils.format(super.getString(aMessage),
                new String[] {aFile.getAbsolutePath()});
    }

    /**
     * Returns the supplied file name integrated into a message value.
     * 
     * @param aFile A file whose name to be returned in the message
     * @return The message form of the supplied file name
     */
    public String get(File aFile) {
        return getString(aFile.getAbsolutePath());
    }

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message
     * key and additional details.
     * 
     * @param aMessage A message key
     * @param aDetailsArray Additional details for the message
     * @return The value of the bundle message
     */
    public String get(String aMessage, Object... aDetailsArray) {
        String[] strings = new String[aDetailsArray.length];

        for (int index = 0; index < strings.length; index++) {
            strings[index] = aDetailsArray[index].toString();
        }

        return StringUtils.format(super.getString(aMessage), strings);
    }

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message
     * key and additional details.
     * 
     * @param aMessage A message key
     * @param aDetail Additional details for the message
     * @return The value of the bundle message
     */
    public String get(String aMessage, Object aDetail) {
        return StringUtils.format(super.getString(aMessage),
                new String[] {aDetail.toString()});
    }

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message
     * key.
     * 
     * @param aObject A message key
     * @return The value of the bundle message
     */
    public String get(Object aObject) {
        return getString(aObject.toString());
    }
}
