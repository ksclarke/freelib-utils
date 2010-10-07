/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Provides a few convenience methods for working with strings.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class StringUtils {

	public static final String UTF_8 = "UTF-8";

	private StringUtils() {}

	/**
	 * Trims a string; if there is nothing left after the trimming, returns
	 * null. If the passed in object is not a string, a cast exception will be
	 * thrown.
	 * 
	 * @param aStringObj The string to be trimmed
	 * @return The trimmed string or null if string is empty
	 * @throws ClassCastException If the supplied object cannot be cast to a
	 *         <code>String</code>
	 */
	public static String trimToNull(Object aStringObj) {
		return trimTo(aStringObj, null);
	}

	/**
	 * Trims a string object down into a boolean and has the ability to define
	 * what the default value should be. We only offer the method with the
	 * default value because most times a boolean with either exist or not (and
	 * in the case of not a default should be specified).
	 * 
	 * @param aStringObj A boolean in string form
	 * @param aBool A default boolean value
	 * @return The boolean representation of the string value or the default
	 *         value
	 * @throws ClassCastException If the supplied object is not a string
	 */
	public static boolean trimToBool(Object aStringObj, boolean aBool) {
		try {
			String boolString = trimTo(aStringObj, Boolean.toString(aBool));
			return Boolean.parseBoolean(boolString);
		}
		catch (ClassCastException details) {
			return aBool; // Report the failed attempt and keep on truckin'
		}
	}

	/**
	 * Trims a string; if there is nothing left after the trimming, returns
	 * whatever the default value passed in is.
	 * 
	 * @param aString The string to be trimmed
	 * @return The trimmed string or the default value if string is empty
	 * @throws ClassCastException If the supplied <code>Object</code> can't be a
	 *         <code>String</code>
	 */
	public static String trimTo(Object aString, String aDefault) {
		if (aString == null) {
			return aDefault;
		}

		// Will throw ClassCastException if Object can't be cast to String
		String trimmed = ((String) aString).trim();
		return trimmed.length() == 0 ? aDefault : trimmed;
	}

	/**
	 * Takes a <code>String</code> in the form "This is {} text {}" and replaces
	 * the <code>{}</code>s with values from the supplied <code>String[]</code>.
	 * The number of curly braces should be the same as the number of strings in
	 * the string array.
	 * 
	 * @param aMessage A string that contains curly braces in the form
	 *        <code>{}</code>
	 * @param aDetails Strings that should be put in place of the curly braces
	 *        in the message string.
	 * @return The formatted string
	 */
	public static String formatMessage(String aMessage, String[] aDetails) {
		int position = 0;
		int count = 0;

		while ((position = aMessage.indexOf("{}", position)) != -1) {
			position += 1;
			count += 1;
		}

		if (count != aDetails.length) {
			throw new IndexOutOfBoundsException(
					"Different number of slots and values: " + count + " and "
							+ aDetails.length);
		}

		String[] parts = aMessage.split("\\{\\}");
		StringBuilder builder = new StringBuilder();
		
		for (int index = 0; index < parts.length; index++) {
			builder.append(parts[index]);

			if (index < aDetails.length) {
				builder.append(aDetails[index]);
			}
		}

		return builder.length() == 0 ? aMessage : builder.toString();
	}

	/**
	 * Pads the beginning of a supplied string with the repetition of a supplied
	 * value.
	 * 
	 * @param aString The string to pad
	 * @param aPadding The string to be repeated as the padding
	 * @param aRepeatCount How many times to repeat the padding
	 * @return The front padded string
	 */
	public static String padStart(String aString, String aPadding,
			int aRepeatCount) {
		if (aRepeatCount != 0) {
			StringBuilder buffer = new StringBuilder();

			for (int index = 0; index < aRepeatCount; index++) {
				buffer.append(aPadding);
			}

			return buffer.append(aString).toString();
		}

		return aString;
	}

	/**
	 * Pads the end of a supplied string with the repetition of a supplied
	 * value.
	 * 
	 * @param aString The string to pad
	 * @param aPadding The string to be repeated as the padding
	 * @param aRepeatCount How many times to repeat the padding
	 * @return The end padded string
	 */
	public static String padEnd(String aString, String aPadding,
			int aRepeatCount) {
		if (aRepeatCount != 0) {
			StringBuilder buffer = new StringBuilder(aString);

			for (int index = 0; index < aRepeatCount; index++) {
				buffer.append(aPadding);
			}

			return buffer.toString();
		}

		return aString;
	}

	/**
	 * Formats a string with or without line breaks into a string with lines
	 * with less than 80 characters per line.
	 * 
	 * @param aString A string to format
	 * @return A string formatted into 80 characters or less lines
	 */
	public static String formatTo80Chars(String aString) {
		StringBuilder builder = new StringBuilder();
		String[] words = aString.split("\\s");
		int count = 0;

		for (String word : words) {
			count += word.length();

			if (count < 80) {
				builder.append(word);

				if ((count += 1) < 80) {
					builder.append(' ');
				}
				else {
					builder.append("\r\n  ");
					count = 2;
				}
			}
			else {
				builder.append("\r\n  ").append(word);
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
	 * @return The new string containing the supplied value repeated the
	 *         specified number of times
	 */
	public static String repeat(String aValue, int aRepeatCount) {
		StringBuilder buffer = new StringBuilder();

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
	 * @return The new string containing the supplied value repeated the
	 *         specified number of times
	 */
	public static String repeat(char aChar, int aRepeatCount) {
		StringBuilder buffer = new StringBuilder();

		for (int index = 0; index < aRepeatCount; index++) {
			buffer.append(aChar);
		}

		return buffer.toString();
	}

	/**
	 * Reads the contents of a file into a string using the character set
	 * encoding of the local system.
	 * 
	 * @param aFile The file from which to read
	 * @return The information read from the file
	 * @throws IOException If the supplied file could not be read
	 */
	public static String read(File aFile) throws IOException {
		String string = new String(readBytes(aFile));

		if (string.endsWith(System.getProperty("line.separator"))) {
			string = string.substring(0, string.length() - 1);
		}

		return string;
	}

	/**
	 * Reads the contents of a file into a string using the UTF-8 character set
	 * encoding.
	 * 
	 * @param aFile The file from which to read
	 * @return The information read from the file
	 * @throws IOException If the supplied file could not be read
	 */
	public static String readAsUTF8(File aFile) throws IOException {
		String string = new String(readBytes(aFile), UTF_8);

		if (string.endsWith(System.getProperty("line.separator"))) {
			string = string.substring(0, string.length() - 1);
		}

		return string;
	}

	/**
	 * Removes empty and null strings from a string array.
	 * 
	 * @param aStringArray An array of strings that may contain empty or null
	 *        strings
	 * @return A string array without empty or null strings
	 */
	public static String[] trim(String[] aStringArray) {
		ArrayList<String> list = new ArrayList<String>();

		for (String string : aStringArray) {
			if (string != null && !string.equals("")) {
				list.add(string);
			}
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * Concatenates the string representations of a series of objects (by
	 * calling their <code>Object.toString()</code> method). Concatenated
	 * strings are separated using the supplied 'padding' character.
	 * 
	 * @param aObjArray An array of objects whose <code>toString()</code>
	 *        representations should be concatenated
	 * @param aPadChar The character used to separate concatenated strings
	 * @return A concatenation of the supplied objects' string representations
	 */
	public static String toString(Object[] aObjArray, char aPadChar) {
		if (aObjArray.length == 0) {
			return "";
		}

		if (aObjArray.length == 1) {
			return aObjArray[0].toString();
		}

		StringBuilder buffer = new StringBuilder();

		for (Object obj : aObjArray) {
			buffer.append(obj.toString()).append(aPadChar);
		}

		return buffer.deleteCharAt(buffer.length() - 1).toString();
	}

	/**
	 * Provides a toString() method for maps that have string keys and string
	 * array values. The regular map toString() works fine for string keys and
	 * string values but, since a string array doesn't have a toString(), the
	 * map's toString() method doesn't produce a useful output. This fixes that.
	 * 
	 * @param aMap A map of string keys and string array values to turn into a
	 *        single string representation of the map
	 * @return A concatenation of the supplied map's string values
	 */
	public static String toString(Map<String, String[]> aMap) {
		Set<Entry<String, String[]>> set = aMap.entrySet();
		Iterator<Entry<String, String[]>> setIter = set.iterator();
		StringBuilder buffer = new StringBuilder();

		while (setIter.hasNext()) {
			Entry<String, String[]> entry = setIter.next();
			String[] values = entry.getValue();

			buffer.append(entry.getKey()).append('=');

			for (String value : values) {
				buffer.append('{').append(value).append('}');
			}

			buffer.append('&');
		}

		if (buffer.length() > 0) {
			if (buffer.charAt(buffer.length() - 1) == '&') {
				buffer.deleteCharAt(buffer.length() - 1);
			}
		}

		return buffer.toString();
	}

	/**
	 * Adds line numbers to any string. This is useful when I need to debug an
	 * XQuery outside the context of an IDE (i.e., in debugging output).
	 * 
	 * @param aMessage Text to which line numbers should be added
	 * @return The supplied text with line numbers at the first of each line
	 */
	public static String addLineNumbers(String aMessage) {
		String eol = System.getProperty("line.separator");
		String[] lines = aMessage.split(eol);
		StringBuilder buffer = new StringBuilder();
		int lineCount = 1; // Error messages start with line 1
		int length;

		for (String line : lines) {
			buffer.append(lineCount++).append(' ').append(line);
			buffer.append(eol);
		}

		length = buffer.length();
		buffer.delete(length - eol.length(), length);

		return buffer.toString();
	}

	/**
	 * Reads the contents of a file into a byte array.
	 * 
	 * @param aFile The file from which to read
	 * @return The bytes read from the file
	 * @throws IOException If the supplied file could not be read in its
	 *         entirety
	 */
	private static byte[] readBytes(File aFile) throws IOException {
		FileInputStream fileStream = new FileInputStream(aFile);
		ByteBuffer buf = ByteBuffer.allocate((int) aFile.length());
		int read = fileStream.getChannel().read(buf);

		if (read != aFile.length()) {
			throw new IOException("Failed to read whole file");
		}

		return buf.array();
	}

	/**
	 * Parses strings with an integer range (e.g., 2-5) and returns an expanded
	 * integer array {2, 3, 4, 5} with those values.
	 * 
	 * @param aIntRange A string representation of a range of integers
	 * @return An int array with the expanded values of the string
	 *         representation
	 */
	public static int[] parseIntRange(String aIntRange) {
		String[] range = aIntRange.split("-");
		int[] ints;

		if (range.length == 1) {
			ints = new int[range.length];
			ints[0] = Integer.parseInt(aIntRange);
		}
		else {
			int start = Integer.parseInt(range[0]);
			int end = Integer.parseInt(range[1]);

			if (end >= start) {
				int position = 0;
				ints = new int[(end - start) + 1];

				for (int index = start; index <= end; index++) {
					ints[position++] = index;
				}
			}
			else {
				throw new NumberFormatException("Inverted number range: "
						+ start + "-" + end);
			}
		}

		return ints;
	}

	public static String toString(int aInt) {
		return toUpcaseString(aInt).toLowerCase();
	}

	public static String toUpcaseString(int aInt) {
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
			throw new UnsupportedOperationException(
					"Don't have a string value for " + aInt);
		}
	}
}
