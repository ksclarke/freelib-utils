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
 */
public class XMLResourceBundle extends ResourceBundle {

    /* Use the original loggers here, not the info.freelib.util.Logger/LoggerFactory wrappers */
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLResourceBundle.class, MessageCodes.BUNDLE);

    /**
     * The properties set by the XML file.
     */
    private final Properties myProperties;

    /**
     * Constructor that allows {@link ResourceBundle}s to be backed by XML files.
     *
     * @param aInStream An XML {@link InputStream}
     * @throws IOException If there is trouble reading from the XML file
     */
    XMLResourceBundle(final InputStream aInStream) throws IOException {
        myProperties = new Properties();
        myProperties.loadFromXML(aInStream);
    }

    /**
     * Handles getting the value of the supplied key.
     *
     * @param aKey The key from which to lookup the value
     * @return The value of the supplied property key
     */
    @Override
    protected Object handleGetObject(final String aKey) {
        return myProperties.getProperty(aKey);
    }

    /**
     * Returns an {@link Enumeration} of property keys.
     *
     * @return An {@link Enumeration} of property keys.
     */
    @Override
    public Enumeration<String> getKeys() {
        final Enumeration<?> enumeration = myProperties.elements();

        return new Enumeration<>() {

            /**
             * Returns whether the {@link Enumeration} has more elements.
             *
             * @return True if the {@link Enumeration} has more elements; else, false.
             */
            @Override
            public boolean hasMoreElements() {
                return enumeration.hasMoreElements();
            }

            /**
             * Returns the next element in the {@link Enumeration}
             *
             * @return The next element in the {@link Enumeration}
             */
            @Override
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
    public String get(final String aMessage, final String... aArray) {
        return StringUtils.format(super.getString(aMessage), aArray);
    }

    /**
     * Return a message value with the supplied string integrated into it.
     *
     * @param aMessage A message in which to include the supplied string value
     * @param aDetail A string value to be included into the supplied message
     * @return A message with the supplied string value integrated into it
     */
    public String get(final String aMessage, final String aDetail) {
        return StringUtils.format(super.getString(aMessage), aDetail);
    }

    /**
     * Returns the string form of the requested message.
     *
     * @param aMessage The key of the message to be returned
     * @return The value of the requested message
     */
    public String get(final String aMessage) {
        return getString(aMessage);
    }

    /**
     * Returns the string form of the requested message with the file values integrated into it.
     *
     * @param aMessage A message key to use to return a message value
     * @param aFileArray An array of files, whose names should be integrated into the message value
     * @return The message value with the supplied file names integrated
     */
    public String get(final String aMessage, final File... aFileArray) {
        final String[] details = new String[aFileArray.length];

        for (int index = 0; index < details.length; index++) {
            details[index] = aFileArray[index].getAbsolutePath();
        }

        LOGGER.debug(MessageCodes.UTIL_026, aMessage, details);
        return StringUtils.format(super.getString(aMessage), details);
    }

    /**
     * Returns the string form of the requested message with the file value integrated into it.
     *
     * @param aMessage A message key to use to return a message value
     * @param aFile A file whose name should be integrated into the message value
     * @return The message value with the supplied file name integrated
     */
    public String get(final String aMessage, final File aFile) {
        return StringUtils.format(super.getString(aMessage), aFile.getAbsolutePath());
    }

    /**
     * Returns the supplied file name integrated into a message value.
     *
     * @param aFile A file whose name to be returned in the message
     * @return The message form of the supplied file name
     */
    public String get(final File aFile) {
        return getString(aFile.getAbsolutePath());
    }

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message key and additional details.
     *
     * @param aMessage A message key
     * @param aDetailsArray Additional details for the message
     * @return The value of the bundle message
     */
    public String get(final String aMessage, final Object... aDetailsArray) {
        final String[] details = new String[aDetailsArray.length];

        for (int index = 0; index < details.length; index++) {
            details[index] = aDetailsArray[index].toString();
        }

        LOGGER.debug(MessageCodes.UTIL_026, aMessage, details);
        return StringUtils.format(super.getString(aMessage), details);
    }

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message key and additional details.
     *
     * @param aMessage A message key
     * @param aDetail Additional details for the message
     * @return The value of the bundle message
     */
    public String get(final String aMessage, final Object aDetail) {
        return StringUtils.format(super.getString(aMessage), aDetail.toString());
    }

    /**
     * Gets the value from the {@link ResourceBundle} for the supplied message key.
     *
     * @param aObject A message key
     * @return The value of the bundle message
     */
    public String get(final Object aObject) {
        return getString(aObject.toString());
    }

    /**
     * Returns true if the supplied key is found in the internal bundle; else, false.
     *
     * @param aKey The resource key for which to check in the bundle
     * @return True if key exists in the bundle; else, false
     */
    @Override
    public boolean containsKey(final String aKey) {
        return myProperties.containsKey(aKey);
    }
}
