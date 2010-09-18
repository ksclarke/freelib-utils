/**
 * 
 */
package info.freelibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

import org.junit.Assert;

/**
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class StringUtilTest extends TestCase {

	public void testTrimTo() {
		String defValue = "default".intern();
		String finalAssertion = StringUtils.trimTo(" original ", defValue);

		Assert.assertEquals(defValue, StringUtils.trimTo(null, defValue));
		Assert.assertEquals(defValue, StringUtils.trimTo("", defValue));
		Assert.assertEquals("original", finalAssertion);
	}

	public void testFormatTo80Chars() {
		File testFile1 = new File("src/test/resources/80_char_test_1.txt");

		try {
			String test1 = StringUtils.read(testFile1);

			String formattedTest1 = StringUtils.formatTo80Chars(test1);
			StringReader stringReader = new StringReader(formattedTest1);
			BufferedReader reader = new BufferedReader(stringReader);
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.length() > 79) {
					Assert.fail("Line <[ '" + line + "' ]> has a length of "
							+ line.length() + " chars");
				}
			}
		}
		catch (IOException details) {
			Assert.fail(details.getMessage());
		}
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.trimToNull(String)'
	 */
	public void testTrimToNull() {
		Assert.assertEquals(null, StringUtils.trimToNull(""));
		Assert.assertEquals("a", StringUtils.trimToNull(" a "));
		Assert.assertEquals(null, StringUtils.trimToNull(null));
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.read(File)'
	 */
	public void testRead() {
		File tmpFile = new File(getClass().getName());
		String original = "This is my content?\nYes!";

		try {
			FileWriter fileWriter = new FileWriter(tmpFile);
			fileWriter.write(original);
			fileWriter.close();

			Assert.assertEquals(original, StringUtils.read(tmpFile));
		}
		catch (IOException details) {
			Assert.fail(details.getMessage());
		}
		finally {
			tmpFile.delete();
		}
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.toString(Object[],
	 * char)'
	 */
	public void testToString() {
		Integer[] array = new Integer[] { new Integer(1), new Integer(21),
				new Integer(3) };

		Assert.assertEquals("1~21~3", StringUtils.toString(array, '~'));
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.repeat(String, int)
	 */
	public void testRepeatStringInt() {
		Assert.assertEquals("!@!@!@", StringUtils.repeat("!@", 3));
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.repeat(char, int)
	 */
	public void testRepeatCharInt() {
		Assert.assertEquals("@@@", StringUtils.repeat("@", 3));
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.padStart(String,
	 * String, int)
	 */
	public void testPadStart() {
		String result = StringUtils.padStart("source", "!@", 3);
		Assert.assertEquals("!@!@!@source", result);
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.padEnd(String, String,
	 * int)
	 */
	public void testPadEnd() {
		String result = StringUtils.padEnd("source", "!@", 3);
		Assert.assertEquals("source!@!@!@", result);
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.addLineNumbers(String)'
	 */
	public void testAddLineNumbers() {
		String original = "This is my content?\nYes!\n\nThis is my content?\nNo!";
		String desired = "1 This is my content?\n2 Yes!\n3 \n4 This is my content?\n5 No!";

		Assert.assertEquals(desired, StringUtils.addLineNumbers(original));
	}

	/*
	 * Test method for 'info.freelibrary.util.StringUtil.parseIntRange(String)'
	 */
	public void testParseIntRange() {
		Assert.assertArrayEquals(new int[] { 1111 }, StringUtils
				.parseIntRange("1111"));
		Assert.assertArrayEquals(
				new int[] { 1000, 1001, 1002, 1003, 1004, 1005 }, StringUtils
						.parseIntRange("1000-1005"));

		try {
			StringUtils.parseIntRange("1001-1000");
			fail("Failed to catch inverted number range");
		}
		catch (NumberFormatException details) {}

		try {
			StringUtils.parseIntRange("1000-");
			fail("Failed to catch single number range value");
		}
		catch (NumberFormatException details) {}
	}
}
