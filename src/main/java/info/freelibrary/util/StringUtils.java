
package info.freelibrary.util;

import static info.freelibrary.util.Constants.BUNDLE_NAME;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Provides a few convenience methods for working with strings.
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class StringUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class, BUNDLE_NAME);

    private static final String EOL = System.getProperty("line.separator");

    private static final String RANGE_DELIMETER = "-";

    private static final String DOUBLE_SPACE = "  ";

    private StringUtils() {
    }

    /**
     * Trims a string; if there is nothing left after the trimming, returns null. If the passed in object is not a
     * string, a cast exception will be thrown.
     *
     * @param aString The string to be trimmed
     * @return The trimmed string or null if string is empty
     */
    public static String trimToNull(final String aString) {
        return trimTo(aString, null);
    }

    /**
     * Trims a string object down into a boolean and has the ability to define what the default value should be. We
     * only offer the method with the default value because most times a boolean with either exist or not (and in the
     * case of not a default should be specified).
     *
     * @param aString A boolean in string form
     * @param aBool A default boolean value
     * @return The boolean representation of the string value or the default value
     */
    public static boolean trimToBool(final String aString, final boolean aBool) {
        final String boolString = trimTo(aString, Boolean.toString(aBool));
        return Boolean.parseBoolean(boolString);
    }

    /**
     * Trims a string; if there is nothing left after the trimming, returns whatever the default value passed in is.
     *
     * @param aString The string to be trimmed
     * @param aDefault A default string to return if a null string is passed in
     * @return The trimmed string or the default value if string is empty
     */
    public static String trimTo(final String aString, final String aDefault) {
        if (aString == null) {
            return aDefault;
        }

        final String trimmed = aString.trim();
        return trimmed.length() == 0 ? aDefault : trimmed;
    }

    /**
     * Formats the supplied message using the supplied details. The string form of the detail comes from the object's
     * <code>toString()</code> method.
     *
     * @param aMessage A message to format
     * @param aDetail Additional details to integrate into the message
     * @return The formatted message
     */
    public static String format(final String aMessage, final Object... aDetail) {
        return format(aMessage, StringUtils.toStrings(aDetail));
    }

    /**
     * Takes a <code>String</code> in the form "This is {} text {}" and replaces the <code>{}</code>s with values from
     * the supplied <code>String[]</code>. The number of curly braces should be the same as the number of strings in
     * the string array.
     *
     * @param aMessage A string that contains curly braces in the form <code>{}</code>
     * @param aDetails Strings that should be put in place of the curly braces in the message string.
     * @return The formatted string
     */
    public static String format(final String aMessage, final String... aDetails) {
        int position = 0;
        int count = 0;

        while ((position = aMessage.indexOf("{}", position)) != -1) {
            position += 1;
            count += 1;
        }

        if (count != aDetails.length) {
            throw new IndexOutOfBoundsException(LOGGER.getI18n(MessageCodes.UTIL_043, count, aDetails.length));
        }

        final String[] parts = aMessage.split("\\{\\}");
        final StringBuilder builder = new StringBuilder();

        if (count == 1 && parts.length == 0) {
            builder.append(aDetails[0]);
        } else {
            for (int index = 0; index < parts.length; index++) {
                builder.append(parts[index]);

                if (index < aDetails.length) {
                    builder.append(aDetails[index]);
                }
            }
        }

        return builder.length() == 0 ? aMessage : builder.toString();
    }

    /**
     * Converts a varargs into an array of strings by calling <code>toString()</code> on each object.
     *
     * @param aVarargs A varargs of objects
     * @return An array of strings
     */
    public static String[] toStrings(final Object... aVarargs) {
        final String[] strings = new String[aVarargs.length];

        for (int index = 0; index < strings.length; index++) {
            strings[index] = aVarargs[index].toString();
        }

        return strings;
    }

    /**
     * Normalizes white space in the message value.
     *
     * @param aMessage A message
     * @return The message with white space normalized
     */
    public static String normalizeWS(final String aMessage) {
        return aMessage.replaceAll("\\s+", " ");
    }

    /**
     * Pads the beginning of a supplied string with the repetition of a supplied value.
     *
     * @param aString The string to pad
     * @param aPadding The string to be repeated as the padding
     * @param aRepeatCount How many times to repeat the padding
     * @return The front padded string
     */
    public static String padStart(final String aString, final String aPadding, final int aRepeatCount) {
        if (aRepeatCount != 0) {
            final StringBuilder buffer = new StringBuilder();

            for (int index = 0; index < aRepeatCount; index++) {
                buffer.append(aPadding);
            }

            return buffer.append(aString).toString();
        }

        return aString;
    }

    /**
     * Pads the end of a supplied string with the repetition of a supplied value.
     *
     * @param aString The string to pad
     * @param aPadding The string to be repeated as the padding
     * @param aRepeatCount How many times to repeat the padding
     * @return The end padded string
     */
    public static String padEnd(final String aString, final String aPadding, final int aRepeatCount) {
        if (aRepeatCount != 0) {
            final StringBuilder buffer = new StringBuilder(aString);

            for (int index = 0; index < aRepeatCount; index++) {
                buffer.append(aPadding);
            }

            return buffer.toString();
        }

        return aString;
    }

    /**
     * Formats a string with or without line breaks into a string with lines with less than a supplied number of
     * characters per line.
     *
     * @param aString A string to format
     * @param aCount A number of characters to allow per line
     * @return A string formatted using the supplied count
     */
    public static String toCharCount(final String aString, final int aCount) {
        final StringBuilder builder = new StringBuilder();
        final String[] words = aString.split("\\s");
        int count = 0;

        for (final String word : words) {
            count += word.length();

            if (count < aCount) {
                builder.append(word);

                if ((count += 1) < aCount) {
                    builder.append(' ');
                } else {
                    builder.append(EOL).append(DOUBLE_SPACE);
                    count = 2;
                }
            } else {
                builder.append(EOL).append(DOUBLE_SPACE).append(word);
                count = word.length() + 2; // two spaces at start of line
            }
        }

        return builder.toString();
    }

    /**
     * Creates a new string from the repetition of a supplied value.
     *
     * @param aValue The string to repeat, creating a new string
     * @param aRepeatCount The number of times to repeat the supplied value
     * @return The new string containing the supplied value repeated the specified number of times
     */
    public static String repeat(final String aValue, final int aRepeatCount) {
        final StringBuilder buffer = new StringBuilder();

        for (int index = 0; index < aRepeatCount; index++) {
            buffer.append(aValue);
        }

        return buffer.toString();
    }

    /**
     * Creates a new string from the repetition of a supplied char.
     *
     * @param aChar The char to repeat, creating a new string
     * @param aRepeatCount The number of times to repeat the supplied value
     * @return The new string containing the supplied value repeated the specified number of times
     */
    public static String repeat(final char aChar, final int aRepeatCount) {
        final StringBuilder buffer = new StringBuilder();

        for (int index = 0; index < aRepeatCount; index++) {
            buffer.append(aChar);
        }

        return buffer.toString();
    }

    /**
     * Reads the contents of a file using the supplied {@link Charset}; the default system charset may vary across
     * systems so can't be trusted.
     *
     * @param aFile The file from which to read
     * @param aCharsetName The name of the character set of the file to be read
     * @return The information read from the file
     * @throws IOException If the supplied file could not be read
     */
    public static String read(final File aFile, final String aCharsetName) throws IOException {
        return read(aFile, Charset.forName(aCharsetName));
    }

    /**
     * Reads the contents of a file using the supplied {@link Charset}; the default system charset may vary across
     * systems so can't be trusted.
     *
     * @param aFile The file from which to read
     * @param aCharset The character set of the file to be read
     * @return The information read from the file
     * @throws IOException If the supplied file could not be read
     */
    public static String read(final File aFile, final Charset aCharset) throws IOException {
        String string = new String(readBytes(aFile), aCharset);

        if (string.endsWith(EOL)) {
            string = string.substring(0, string.length() - 1);
        }

        return string;
    }

    /**
     * Reads the contents of a file into a string using the UTF-8 character set encoding.
     *
     * @param aFile The file from which to read
     * @return The information read from the file
     * @throws IOException If the supplied file could not be read
     */
    public static String read(final File aFile) throws IOException {
        String string = new String(readBytes(aFile), StandardCharsets.UTF_8.name());

        if (string.endsWith(EOL)) {
            string = string.substring(0, string.length() - 1);
        }

        return string;
    }

    /**
     * Removes empty and null strings from a string array.
     *
     * @param aStringArray A varargs that may contain empty or null strings
     * @return A string array without empty or null strings
     */
    public static String[] trim(final String... aStringArray) {
        final ArrayList<String> list = new ArrayList<>();

        for (final String string : aStringArray) {
            if (string != null && !"".equals(string)) {
                list.add(string);
            }
        }

        return list.toArray(new String[0]);
    }

    /**
     * Returns true if the supplied string is null, empty, or contains nothing but whitespace.
     *
     * @param aString A string to test to see if it is null, empty or contains nothing but whitespace
     * @return True if the supplied string is empty; else, false
     */
    public static boolean isEmpty(final String aString) {
        boolean result = true;

        if (aString != null) {
            for (int index = 0; index < aString.length(); index++) {
                if (!Character.isWhitespace(aString.charAt(index))) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * A convenience method for toString(Object[], char) to add varargs support.
     *
     * @param aPadChar A padding character
     * @param aVarargs A varargs into which to insert the padding character
     * @return A string form of the varargs with padding added
     */
    public static String toString(final char aPadChar, final Object... aVarargs) {
        return toString(aVarargs, aPadChar);
    }

    /**
     * Concatenates the string representations of a series of objects (by calling their <code>Object.toString()</code>
     * method). Concatenated strings are separated using the supplied 'padding' character.
     *
     * @param aObjArray An array of objects whose <code>toString()</code> representations should be concatenated
     * @param aPadChar The character used to separate concatenated strings
     * @return A concatenation of the supplied objects' string representations
     */
    public static String toString(final Object[] aObjArray, final char aPadChar) {
        if (aObjArray == null || aObjArray.length == 0) {
            return "";
        }

        if (aObjArray.length == 1) {
            return aObjArray[0].toString();
        }

        final StringBuilder buffer = new StringBuilder();

        for (final Object obj : aObjArray) {
            buffer.append(obj).append(aPadChar);
        }

        return buffer.deleteCharAt(buffer.length() - 1).toString();
    }

    /**
     * Turns the keys in a map into a character delimited string. The order is only consistent if the map is sorted.
     *
     * @param aMap The map from which to pull the keys
     * @param aSeparator The character separator for the construction of the string
     * @return A string constructed from the keys in the map
     */
    public static String joinKeys(final Map<String, ?> aMap, final char aSeparator) {
        if (aMap.isEmpty()) {
            return "";
        }

        final Iterator<String> iterator = aMap.keySet().iterator();
        final StringBuilder buffer = new StringBuilder();

        while (iterator.hasNext()) {
            buffer.append(iterator.next()).append(aSeparator);
        }

        final int length = buffer.length() - 1;

        return buffer.charAt(length) == aSeparator ? buffer.substring(0, length) : buffer.toString();
    }

    /**
     * Provides a toString() method for maps that have string keys and string array values. The regular map toString()
     * works fine for string keys and string values but, since a string array doesn't have a toString(), the map's
     * toString() method doesn't produce a useful output. This fixes that.
     *
     * @param aMap A map of string keys and string array values to turn into a single string representation of the map
     * @return A concatenation of the supplied map's string values
     */
    public static String toString(final Map<String, String[]> aMap) {
        final Set<Entry<String, String[]>> set = aMap.entrySet();
        final Iterator<Entry<String, String[]>> setIter = set.iterator();
        final StringBuilder buffer = new StringBuilder();

        while (setIter.hasNext()) {
            final Entry<String, String[]> entry = setIter.next();
            final Object[] values = entry.getValue();

            buffer.append(entry.getKey()).append('=');

            for (final Object value : values) {
                buffer.append('{').append(value).append('}');
            }

            buffer.append('&');
        }

        if (buffer.length() > 0 && buffer.charAt(buffer.length() - 1) == '&') {
            buffer.deleteCharAt(buffer.length() - 1);
        }

        return buffer.toString();
    }

    /**
     * Adds line numbers to any string. This is useful when I need to debug an XQuery outside the context of an IDE
     * (i.e., in debugging output).
     *
     * @param aMessage Text to which line numbers should be added
     * @return The supplied text with line numbers at the first of each line
     */
    public static String addLineNumbers(final String aMessage) {
        final StringBuilder buffer = new StringBuilder();
        final String[] lines = aMessage.split(EOL);

        int lineCount = 1; // Error messages start with line 1

        for (final String line : lines) {
            buffer.append(lineCount++).append(' ').append(line);
            buffer.append(EOL);
        }

        final int length = buffer.length();

        buffer.delete(length - EOL.length(), length);

        return buffer.toString();
    }

    /**
     * Reads the contents of a file into a byte array.
     *
     * @param aFile The file from which to read
     * @return The bytes read from the file
     * @throws IOException If the supplied file could not be read in its entirety
     */
    private static byte[] readBytes(final File aFile) throws IOException {
        final FileInputStream fileStream = new FileInputStream(aFile);
        final ByteBuffer buf = ByteBuffer.allocate((int) aFile.length());
        final int read = fileStream.getChannel().read(buf);

        if (read != aFile.length()) {
            fileStream.close();
            throw new IOException(LOGGER.getI18n(MessageCodes.UTIL_044, aFile));
        }

        fileStream.close();
        return buf.array();
    }

    /**
     * Parses strings with an integer range (e.g., 2-5) and returns an expanded integer array {2, 3, 4, 5} with those
     * values.
     *
     * @param aIntRange A string representation of a range of integers
     * @return An int array with the expanded values of the string representation
     */
    public static int[] parseIntRange(final String aIntRange) {
        final String[] range = aIntRange.split(RANGE_DELIMETER);
        final int[] ints;

        if (range.length == 1) {
            ints = new int[range.length];
            ints[0] = Integer.parseInt(aIntRange);
        } else {
            final int start = Integer.parseInt(range[0]);
            final int end = Integer.parseInt(range[1]);

            if (end >= start) {
                int position = 0;
                final int size = end - start;
                ints = new int[size + 1]; // because we're zero-based

                for (int index = start; index <= end; index++) {
                    ints[position++] = index;
                }
            } else {
                throw new NumberFormatException(LOGGER.getI18n(MessageCodes.UTIL_045, start, RANGE_DELIMETER, end));
            }
        }

        return ints;
    }

    /**
     * Returns a human-friendly, locale dependent, string representation of the supplied int; for instance, "1"
     * becomes "first", "2" becomes "second", etc.
     *
     * @param aInt An int to convert into a string
     * @return The string form of the supplied int
     */
    public static String toString(final int aInt) {
        return toUpcaseString(aInt).toLowerCase(Locale.getDefault());
    }

    /**
     * Reverses the characters in a string.
     *
     * @param aString A string whose characters are to be reversed
     * @return A string with the supplied string reversed
     */
    public static String reverse(final String aString) {
        return new StringBuffer(aString).reverse().toString();
    }

    /**
     * Upcases a string.
     *
     * @param aString A string to upcase
     * @return The upcased string
     */
    public static String upcase(final String aString) {
        return aString.substring(0, 1).toUpperCase(Locale.getDefault()) + aString.substring(1);
    }

    /**
     * Returns an up-cased human-friendly string representation for the supplied int; for instance, "1" becomes
     * "First", "2" becomes "Second", etc.
     *
     * @param aInt An int to convert into a string
     * @return The string form of the supplied int
     */
    public static String toUpcaseString(final int aInt) {
        switch (aInt) {
            case 1:
                return "First";
            case 2:
                return "Second";
            case 3:
                return "Third";
            case 4:
                return "Fourth";
            case 5:
                return "Fifth";
            case 6:
                return "Sixth";
            case 7:
                return "Seventh";
            case 8:
                return "Eighth";
            case 9:
                return "Ninth";
            case 10:
                return "Tenth";
            case 11:
                return "Eleventh";
            case 12:
                return "Twelveth";
            case 13:
                return "Thirteenth";
            case 14:
                return "Fourteenth";
            case 15:
                return "Fifthteenth";
            case 16:
                return "Sixteenth";
            case 17:
                return "Seventeenth";
            case 18:
                return "Eighteenth";
            case 19:
                return "Nineteenth";
            case 20:
                return "Twentieth";
            default:
                throw new UnsupportedOperationException(LOGGER.getI18n(MessageCodes.UTIL_046, aInt));
        }
    }

}
