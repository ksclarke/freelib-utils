
package info.freelibrary.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * Tests of the {@link StringUtils} class.
 */
public class StringUtilsTest {

    private static final String CHARSET = "UTF-8";

    /**
     * Tests {@link StringUtils#trimTo(Object, String)}.
     */
    @Test
    public void trimToStringString() {
        final String assertion = StringUtils.trimTo(" original ", "default");

        assertEquals("default", StringUtils.trimTo(null, "default"));
        assertEquals("default", StringUtils.trimTo("", "default"));
        assertEquals("original", assertion);
    }

    /**
     * Tests {@link StringUtils#formatMessage()}.
     */
    @Test
    public void formatMessageStringStringArray() {
        final String message = "This is the {} and the {}";
        final String[] values = new String[] { "first", "second" };
        final String result = StringUtils.format(message, values);

        assertEquals(result, "This is the first and the second");

        try {
            StringUtils.format(message, new String[] { "first" });
            fail("Failed to notice more slots than values");
        } catch (final IndexOutOfBoundsException details) {
        }

        try {
            final String[] array = new String[] { "first", "second", "third" };
            StringUtils.format(message, array);
            fail("Failed to notice more values than slots");
        } catch (final IndexOutOfBoundsException details) {
        }
    }

    /**
     * Tests {@link StringUtils#formatTo80Chars(String)}.
     */
    @Test
    public void testFormatTo80CharsString() {
        final File testFile1 = new File("src/test/resources/80_char_test_1.txt");

        try {
            final String test1 = StringUtils.read(testFile1, CHARSET);
            final String formattedTest1 = StringUtils.toCharCount(test1, 80);
            final StringReader stringReader = new StringReader(formattedTest1);
            final BufferedReader reader = new BufferedReader(stringReader);
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 79) {
                    fail("Line <[ '" + line + "' ]> has a length of " + line.length() + " chars");
                }
            }
        } catch (final IOException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Tests {@link StringUtils#trimToNull(String)}.
     */
    @Test
    public void testTrimToNullString() {
        assertEquals(null, StringUtils.trimToNull(""));
        assertEquals("a", StringUtils.trimToNull(" a "));
        assertEquals(null, StringUtils.trimToNull(null));
    }

    /**
     * Tests {@link StringUtils#isEmpty(String)}.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty("   "));
        assertFalse(StringUtils.isEmpty(" a "));
    }

    /**
     * Tests {@link StringUtils#read(File, String)}.
     */
    @Test
    public void testReadFileString() {
        final File tmpFile = new File(getClass().getName());
        final String original = "This is my content?\nYes!";

        try {
            final FileWriter fileWriter = new FileWriter(tmpFile);
            fileWriter.write(original);
            fileWriter.close();

            assertEquals(original, StringUtils.read(tmpFile, CHARSET));
        } catch (final IOException details) {
            fail(details.getMessage());
        } finally {
            tmpFile.delete();
        }
    }

    /**
     * Tests {@link StringUtils#joinKeys(Map, char)}.
     */
    @Test
    public void toKeysString() {
        final Map<String, String> map = new TreeMap<>();

        map.put("one", "two");
        map.put("three", "four");
        map.put("five", "six");

        // This is only consistent because we're using a sorted map
        assertEquals("five one three", StringUtils.joinKeys(map, ' '));
    }

    /**
     * Tests {@link StringUtils#toString(Object[], Char)}.
     */
    @Test
    public void toStringObjectArrayChar() {
        final Integer i1 = Integer.valueOf(1);
        final Integer i2 = Integer.valueOf(21);
        final Integer i3 = Integer.valueOf(3);

        final Integer[] array = new Integer[] { i1, i2, i3 };
        assertEquals("1~21~3", StringUtils.toString(array, '~'));
    }

    /**
     * Tests {@link StringUtils#toString(char, Object...)}.
     */
    @Test
    public void toStringCharVarargs() {
        final Integer i1 = Integer.valueOf(1);
        final Integer i2 = Integer.valueOf(21);
        final Integer i3 = Integer.valueOf(3);

        final Object[] array = new Object[] { i1, i2, i3 };

        assertEquals("1~21~3", StringUtils.toString('~', array));
    }

    /**
     * Tests {@link StringUtils#repeat(String, Int)}.
     */
    @Test
    public void repeatStringInt() {
        assertEquals("!@!@!@", StringUtils.repeat("!@", 3));
    }

    /**
     * Tests {@link StringUtils#repeat(Char, Int)}.
     */
    @Test
    public void repeatCharInt() {
        assertEquals("@@@", StringUtils.repeat("@", 3));
    }

    /**
     * Tests {@link StringUtils#padStart(String, String, Int)}.
     */
    @Test
    public void testPadStartStringStringInt() {
        final String result = StringUtils.padStart("source", "!@", 3);
        assertEquals("!@!@!@source", result);
    }

    /**
     * Tests {@link StringUtils#padEnd(String, String, Int)}.
     */
    @Test
    public void testPadEndStringStringInt() {
        final String result = StringUtils.padEnd("source", "!@", 3);
        assertEquals("source!@!@!@", result);
    }

    /**
     * Tests {@link StringUtils#addLineNumbers(String)}.
     */
    @Test
    public void testAddLineNumbersString() {
        final String original = "This is my content?\nYes!\n\nThis is my content?";
        final String expected = "1 This is my content?\n2 Yes!\n3 \n4 This is my content?";

        assertEquals(expected, StringUtils.addLineNumbers(original));
    }

    /**
     * Tests {@link StringUtils#parseIntRange(String)}.
     */
    @Test
    public void testParseIntRangeString() {
        final int[] iArray1 = new int[] { 1111 };
        final int[] iArray2 = new int[] { 1000, 1001, 1002, 1003, 1004, 1005 };

        assertArrayEquals(iArray1, StringUtils.parseIntRange("1111"));
        assertArrayEquals(iArray2, StringUtils.parseIntRange("1000-1005"));

        try {
            StringUtils.parseIntRange("1001-1000");
            fail("Failed to catch inverted number range");
        } catch (final NumberFormatException details) {
        }

        try {
            StringUtils.parseIntRange("1000-");
            fail("Failed to catch single number range value");
        } catch (final NumberFormatException details) {
        }
    }
}
