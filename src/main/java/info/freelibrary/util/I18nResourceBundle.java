
package info.freelibrary.util;

import java.io.File;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * An interface for resource bundles that use Logback's style of string replacement.
 */
interface I18nResourceBundle {

    /**
     * Gets an enumeration of resource bundle's keys.
     *
     * @return An enumeration of resource bundle's keys
     */
    Enumeration<String> getKeys();

    /**
     * Counts the number of keys in the bundle.
     *
     * @return The number of keys in the bundle
     */
    int countKeys();

    /**
     * Return a message value with the supplied values integrated into it.
     *
     * @param aMessage A message in which to include the supplied string values
     * @param aArray The string values to insert into the supplied message
     * @return A message with the supplied values integrated into it
     */
    String get(String aMessage, String... aArray);

    /**
     * Return a message value with the supplied string integrated into it.
     *
     * @param aMessage A message in which to include the supplied string value
     * @param aDetail A string value to be included into the supplied message
     * @return A message with the supplied string value integrated into it
     */
    String get(String aMessage, String aDetail);

    /**
     * Returns the string form of the requested message.
     *
     * @param aMessage The key of the message to be returned
     * @return The value of the requested message
     */
    String get(String aMessage);

    /**
     * Returns the string form of the requested message with the file values integrated into it.
     *
     * @param aMessage A message key to use to return a message value
     * @param aFileArray An array of files, whose names should be integrated into the message value
     * @return The message value with the supplied file names integrated
     */
    String get(String aMessage, File... aFileArray);

    /**
     * Returns the string form of the requested message with the file value integrated into it.
     *
     * @param aMessage A message key to use to return a message value
     * @param aFile A file whose name should be integrated into the message value
     * @return The message value with the supplied file name integrated
     */
    String get(String aMessage, File aFile);

    /**
     * Returns the supplied file name integrated into a message value.
     *
     * @param aFile A file whose name to be returned in the message
     * @return The message form of the supplied file name
     */
    String get(File aFile);

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message key and additional details.
     *
     * @param aMessage A message key
     * @param aDetailsArray Additional details for the message
     * @return The value of the bundle message
     */
    String get(String aMessage, Object... aDetailsArray);

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message key and additional details.
     *
     * @param aMessage A message key
     * @param aDetail Additional details for the message
     * @return The value of the bundle message
     */
    String get(String aMessage, Object aDetail);

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message key.
     *
     * @param aObject A message key
     * @return The value of the bundle message
     */
    String get(Object aObject);

    /**
     * Returns true if the supplied key is found in the internal bundle; else, false.
     *
     * @param aKey The resource key for which to check in the bundle
     * @return True if key exists in the bundle; else, false
     */
    boolean containsKey(String aKey);
}
