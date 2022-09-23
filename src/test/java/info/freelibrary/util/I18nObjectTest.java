
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.test.I18nObjectWrapper;

/**
 * Tests of I18nObject.
 */
public class I18nObjectTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nObjectTest.class, MessageCodes.BUNDLE);

    private static final Locale LOCALE = Locale.getDefault();

    private static final String VALUE_ONE = "test.value.one";

    private static final String TEST_ONE = "test.one";

    private static final String ONE = "one";

    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    /**
     * Sets up the testing environment.
     */
    @Before
    public void beforeTests() {
        Locale.setDefault(Locale.US);
    }

    /**
     * Cleans up the testing environment.
     */
    @After
    public void afterTests() {
        Locale.setDefault(LOCALE);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String)}.
     */
    @Test
    public void testGetI18nString() {
        assertEquals(ONE, new I18nObjectWrapper().getI18n(TEST_ONE));
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Exception)}.
     */
    @Test
    public void testGetI18nStringException() {
        final String expected = ONE;
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, new IOException(expected));

        assertEquals(expected, found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Long)}.
     */
    @Test
    public void testGetI18nStringLong() {
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, 100L);

        assertEquals(100L, Long.parseLong(found));
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Int)}.
     */
    @Test
    public void testGetI18nStringInt() {
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, 100);

        assertEquals(100, Integer.parseInt(found));
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, String)}.
     */
    @Test
    public void testGetI18nStringString() {
        final String expected = ONE;
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, expected);

        assertEquals(expected, found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Array)}.
     */
    @Test
    public void testGetI18nStringStringArray() {
        final String expected = ONE;
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, new String[] { expected });

        assertEquals(expected, found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, java.io.File)}.
     */
    @Test
    public void testGetI18nStringFile() {
        final File expected = new File(TMP_DIR, UUID.randomUUID().toString());
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, expected);

        assertEquals(expected.getAbsolutePath(), found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, FileArray)}.
     */
    @Test
    public void testGetI18nStringFileArray() {
        final File expected = new File(TMP_DIR, UUID.randomUUID().toString());
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, new File[] { expected });

        assertEquals(expected.getAbsolutePath(), found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, java.lang.ObjectArray)}.
     */
    @Test
    public void testGetI18nStringObjectArray() {
        final File expected = new File(TMP_DIR, UUID.randomUUID().toString());
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, new Object[] { expected });

        assertEquals(expected.getAbsolutePath(), found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String)}.
     */
    @Test
    public void testGetI18nBounceBack() {
        try {
            new I18nObjectWrapper().getI18n("something.not.found");

            fail(LOGGER.getMessage(MessageCodes.UTIL_062));
        } catch (final MissingResourceException details) {
            // this is expected
        }
    }

    /**
     * Test method for {@link I18nObject#hasI18nKey()}.
     */
    @Test
    public void testHasI18nKey() {
        final I18nObjectWrapper i18nObj = new I18nObjectWrapper();
        assertTrue(i18nObj.hasI18nKey(TEST_ONE));
        assertEquals(ONE, i18nObj.getI18n(TEST_ONE));
    }

    /**
     * Tests the use of an I18n properties file.
     */
    @Test
    public void testPropertiesFile() {
        assertEquals(1, new I18nObjectWrapper("test_messages").countKeys());
    }
}
