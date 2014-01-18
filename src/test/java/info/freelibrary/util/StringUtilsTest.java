/**
 * 
 */

package info.freelibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests of the {@link StringUtils} class.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class StringUtilsTest {

    private static final String CHARSET = "UTF-8";

    /**
     * Tests {@link StringUtils#trimTo(Object, String)}.
     */
    @Test
    public void trimToStringString() {
        String assertion = StringUtils.trimTo(" original ", "default");

        assertEquals("default", StringUtils.trimTo(null, "default"));
        assertEquals("default", StringUtils.trimTo("", "default"));
        assertEquals("original", assertion);
    }

    /**
     * Tests {@link StringUtils#formatMessage()}.
     */
    @Test
    public void formatMessageStringStringArray() {
        String message = "This is the {} and the {}";
        String[] values = new String[] {"first", "second"};
        String result = StringUtils.format(message, values);

        assertEquals(result, "This is the first and the second");

        try {
            StringUtils.format(message, new String[] {"first"});
            fail("Failed to notice more slots than values");
        } catch (IndexOutOfBoundsException details) {
        }

        try {
            String[] array = new String[] {"first", "second", "third"};
            StringUtils.format(message, array);
            fail("Failed to notice more values than slots");
        } catch (IndexOutOfBoundsException details) {
        }
    }

    /**
     * Tests {@link StringUtils#formatTo80Chars(String)}.
     */
    @Test
    public void testFormatTo80CharsString() {
        File testFile1 = new File("src/test/resources/80_char_test_1.txt");

        try {
            String test1 = StringUtils.read(testFile1, CHARSET);
            String formattedTest1 = StringUtils.toCharCount(test1, 80);
            StringReader stringReader = new StringReader(formattedTest1);
            BufferedReader reader = new BufferedReader(stringReader);
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 79) {
                    fail("Line <[ '" + line + "' ]> has a length of " +
                            line.length() + " chars");
                }
            }
        } catch (IOException details) {
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
     * Tests {@link StringUtils#read(File, String)}.
     */
    @Test
    public void testReadFileString() {
        File tmpFile = new File(getClass().getName());
        String original = "This is my content?\nYes!";

        try {
            FileWriter fileWriter = new FileWriter(tmpFile);
            fileWriter.write(original);
            fileWriter.close();

            assertEquals(original, StringUtils.read(tmpFile, CHARSET));
        } catch (IOException details) {
            fail(details.getMessage());
        } finally {
            tmpFile.delete();
        }
    }

    /**
     * Tests {@link StringUtils#toString(Object[], Char)}.
     */
    @Test
    public void toStringObjectArrayChar() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(21);
        Integer i3 = new Integer(3);

        Integer[] array = new Integer[] {i1, i2, i3};
        assertEquals("1~21~3", StringUtils.toString(array, '~'));
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
        String result = StringUtils.padStart("source", "!@", 3);
        assertEquals("!@!@!@source", result);
    }

    /**
     * Tests {@link StringUtils#padEnd(String, String, Int)}.
     */
    @Test
    public void testPadEndStringStringInt() {
        String result = StringUtils.padEnd("source", "!@", 3);
        assertEquals("source!@!@!@", result);
    }

    /**
     * Tests {@link StringUtils#addLineNumbers(String)}.
     */
    @Test
    public void testAddLineNumbersString() {
        String original = "This is my content?\nYes!\n\nThis is my content?";
        String expected =
                "1 This is my content?\n2 Yes!\n3 \n4 This is my content?";

        assertEquals(expected, StringUtils.addLineNumbers(original));
    }

    /**
     * Tests {@link StringUtils#parseIntRange(String)}.
     */
    @Test
    public void testParseIntRangeString() {
        int[] iArray1 = new int[] {1111};
        int[] iArray2 = new int[] {1000, 1001, 1002, 1003, 1004, 1005};

        assertArrayEquals(iArray1, StringUtils.parseIntRange("1111"));
        assertArrayEquals(iArray2, StringUtils.parseIntRange("1000-1005"));

        try {
            StringUtils.parseIntRange("1001-1000");
            fail("Failed to catch inverted number range");
        } catch (NumberFormatException details) {
        }

        try {
            StringUtils.parseIntRange("1000-");
            fail("Failed to catch single number range value");
        } catch (NumberFormatException details) {
        }
    }
}
