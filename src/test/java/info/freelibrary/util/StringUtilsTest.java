
package info.freelibrary.util;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * Tests of the {@link StringUtils} class.
 */
public class StringUtilsTest {

    /** A constant for 'default'. */
    private static final String DEFAULT = "default";

    /** A constant test value. */
    private static final String EMPTY_A = " a ";

    /** A symbol constant. */
    private static final String EXCLAMATION_AT = "!@";

    /** A constant for 'first'. */
    private static final String FIRST = "first";

    /** A constant test value. */
    private static final String ONE_21_3 = "1~21~3";

    /** A constant for 'second'. */
    private static final String SECOND = "second";

    /** A constant for 'source'. */
    private static final String SOURCE = "source";

    /** A constant for 'third'. */
    private static final String THIRD = "third";

    /**
     * Tests {@link StringUtils#formatMessage()}.
     */
    @Test
    public void formatMessageStringStringArray() {
        final String message = "This is the {} and the {}";
        final String[] values = { FIRST, SECOND };
        final String result = StringUtils.format(message, values);

        assertEquals(result, "This is the first and the second");

        try {
            StringUtils.format(message, FIRST);
            fail("Failed to notice more slots than values");
        } catch (final IndexOutOfBoundsException details) {
            // This is expected
        }

        try {
            final String[] array = { FIRST, SECOND, THIRD };
            StringUtils.format(message, array);
            fail("Failed to notice more values than slots");
        } catch (final IndexOutOfBoundsException details) {
            // This is expected
        }
    }

    /**
     * Tests {@link StringUtils#repeat(Char, Int)}.
     */
    @Test
    public void repeatCharInt() {
        assertEquals("@@@", StringUtils.repeat("@", 3));
    }

    /**
     * Tests {@link StringUtils#repeat(String, Int)}.
     */
    @Test
    public void repeatStringInt() {
        assertEquals("!@!@!@", StringUtils.repeat(EXCLAMATION_AT, 3));
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
     * Tests {@code info.freelibrary.util.StringUtils#format(String, Object...)}.
     */
    @Test
    public void testFormatPassThrough() {
        assertEquals(FIRST, StringUtils.format(FIRST));
    }

    /**
     * Tests {@link StringUtils#formatTo80Chars(String)}.
     */
    @Test
    public void testFormatTo80CharsString() {
        final File testFile1 = new File("src/test/resources/80_char_test_1.txt");

        try {
            final String test1 = StringUtils.read(testFile1, StandardCharsets.UTF_8.toString());
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
     * Tests {@code info.freelibrary.util.StringUtils#format(String, Object...)}.
     */
    @Test
    public void testFormatTwoCurlyBraces() {
        assertEquals("12", StringUtils.format("{}{}", 1, 2));
    }

    /**
     * Tests {@code info.freelibrary.util.StringUtils#format(String, Object...)}.
     */
    public void testForSiblingCurlyBrances() {
        assertEquals("first://secondthird", StringUtils.format("{}://{}{}", FIRST, SECOND, THIRD));
    }

    /**
     * Tests {@link StringUtils#isEmpty(String)}.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(StringUtils.isEmpty(EMPTY));
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty("   "));
        assertFalse(StringUtils.isEmpty(EMPTY_A));
    }

    /**
     * Tests {@link StringUtils#padEnd(String, String, Int)}.
     */
    @Test
    public void testPadEndStringStringInt() {
        final String result = StringUtils.padEnd(SOURCE, EXCLAMATION_AT, 3);
        assertEquals("source!@!@!@", result);
    }

    /**
     * Tests {@link StringUtils#padStart(String, String, Int)}.
     */
    @Test
    public void testPadStartStringStringInt() {
        final String result = StringUtils.padStart(SOURCE, EXCLAMATION_AT, 3);
        assertEquals("!@!@!@source", result);
    }

    /**
     * Tests {@link StringUtils#parseIntRange(String)}.
     */
    @Test
    public void testParseIntRangeString() {
        final int[] iArray1 = { 1111 };
        final int[] iArray2 = { 1000, 1001, 1002, 1003, 1004, 1005 };

        assertArrayEquals(iArray1, StringUtils.parseIntRange("1111"));
        assertArrayEquals(iArray2, StringUtils.parseIntRange("1000-1005"));

        try {
            StringUtils.parseIntRange("1001-1000");
            fail("Failed to catch inverted number range");
        } catch (final NumberFormatException details) {
            // This is expected
        }

        try {
            StringUtils.parseIntRange("1000-");
            fail("Failed to catch single number range value");
        } catch (final NumberFormatException details) {
            // This is expected
        }
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

            assertEquals(original, StringUtils.read(tmpFile, StandardCharsets.UTF_8.toString()));
        } catch (final IOException details) {
            fail(details.getMessage());
        } finally {
            tmpFile.delete();
        }
    }

    /**
     * Tests {@link StringUtils#trimToNull(String)}.
     */
    @Test
    public void testTrimToNullString() {
        assertEquals(null, StringUtils.trimToNull(EMPTY));
        assertEquals("a", StringUtils.trimToNull(EMPTY_A));
        assertEquals(null, StringUtils.trimToNull(null));
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
     * Tests {@link StringUtils#toString(char, Object...)}.
     */
    @Test
    public void toStringCharVarargs() {
        final Integer i1 = 1;
        final Integer i2 = 21;
        final Integer i3 = 3;

        final Object[] array = { i1, i2, i3 };

        assertEquals(ONE_21_3, StringUtils.toString('~', array));
    }

    /**
     * Tests {@link StringUtils#toString(Object[], Char)}.
     */
    @Test
    public void toStringObjectArrayChar() {
        final Integer i1 = 1;
        final Integer i2 = 21;
        final Integer i3 = 3;

        final Integer[] array = { i1, i2, i3 };
        assertEquals(ONE_21_3, StringUtils.toString(array, '~'));
    }

    /**
     * Tests {@link StringUtils#trimTo(Object, String)}.
     */
    @Test
    public void trimToStringString() {
        final String assertion = StringUtils.trimTo(" original ", DEFAULT);

        assertEquals(DEFAULT, StringUtils.trimTo(null, DEFAULT));
        assertEquals(DEFAULT, StringUtils.trimTo(EMPTY, DEFAULT));
        assertEquals("original", assertion);
    }
}
