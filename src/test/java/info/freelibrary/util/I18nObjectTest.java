package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import info.freelibrary.util.test.I18nObjectWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.UUID;

/**
 * Tests of I18nObject.
 */
public class I18nObjectTest {

    /** A logger for the tests. */
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nObjectTest.class, MessageCodes.BUNDLE);

    /** A default locale. */
    private static final Locale LOCALE = Locale.getDefault();

    /** A test value. */
    private static final String VALUE_ONE = "test.value.one";

    /** A test value. */
    private static final String VALUE_TWO = "test.value.two";

    /** A test value. */
    private static final String TEST_ONE = "test.one";

    /** A test key. */
    private static final String ONE = "one";

    /** French for one. */
    private static final String UN = "un";

    /** A test detail. */
    private static final String DETAIL_A = "A";

    /** A test detail. */
    private static final String DETAIL_B = "B";

    /** A test bundle name. */
    private static final String BUNDLE_NAME = "test_freelib-utils_messages";

    /** A temporary directory used in testing. */
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
     * Test method for {@link I18nObject#getI18n(String, long)}.
     */
    @Test
    public void testGetI18nStringLong() {
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, 100L);

        assertEquals(100L, Long.parseLong(found));
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, int)}.
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
     * Test method for {@link I18nObject#getI18n(String, Object...)}.
     */
    @Test
    public void testGetI18nStringStringArray() {
        final String expected = ONE;
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, new String[]{expected});

        assertEquals(expected, found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, File)}.
     */
    @Test
    public void testGetI18nStringFile() {
        final File expected = new File(TMP_DIR, UUID.randomUUID().toString());
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, expected);

        assertEquals(expected.getAbsolutePath(), found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, File...)}.
     */
    @Test
    public void testGetI18nStringFileArray() {
        final File expected = new File(TMP_DIR, UUID.randomUUID().toString());
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, new File[]{expected});

        assertEquals(expected.getAbsolutePath(), found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Object...)}.
     */
    @Test
    public void testGetI18nStringObjectArray() {
        final File expected = new File(TMP_DIR, UUID.randomUUID().toString());
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, new Object[]{expected});

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
     * Test method for {@link I18nObject#hasI18nKey(String)}.
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

    /**
     * Test method for {@link I18nObject#getI18n(Locale, String)}.
     */
    @Test
    public void testGetStringLocale() {
        final I18nObjectWrapper i18n = new I18nObjectWrapper();

        assertEquals(ONE, i18n.getI18n(Locale.US, TEST_ONE));
        assertEquals(UN, i18n.getI18n(Locale.FRENCH, TEST_ONE));
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Locale, String)}.
     */
    @Test
    public void testStaticGetStringLocale() {
        assertEquals(ONE, I18nObject.getI18n(BUNDLE_NAME, Locale.US, TEST_ONE));
        assertEquals(UN, I18nObject.getI18n(BUNDLE_NAME, Locale.FRENCH, TEST_ONE));
    }

    /**
     * Test method for {@link I18nObject#getI18n(Locale, String, Object...)}.
     */
    @Test
    public void testGetI18nWithLocaleAndDetails() {
        final I18nObjectWrapper i18n = new I18nObjectWrapper();

        assertEquals("A and B", i18n.getI18n(Locale.US, VALUE_TWO, DETAIL_A, DETAIL_B));
        assertEquals("A et B", i18n.getI18n(Locale.FRENCH, VALUE_TWO, DETAIL_A, DETAIL_B));
    }

    /**
     * Tests constructor locale binding.
     */
    @Test
    public void testConstructorLocale() {
        final I18nObjectWrapper i18nFr = new I18nObjectWrapper(Locale.FRENCH);
        final I18nObjectWrapper i18nUs = new I18nObjectWrapper(Locale.US);

        assertEquals(UN, i18nFr.getI18n(TEST_ONE));
        assertEquals(ONE, i18nUs.getI18n(TEST_ONE));
    }

    /**
     * Tests null checks on getI18n methods.
     */
    @Test
    public void testGetNullArguments() {
        final I18nObjectWrapper i18n = new I18nObjectWrapper();

        try {
            i18n.getI18n((Locale) null, TEST_ONE);
            fail();
        } catch (final NullPointerException details) {
            // expected
        }

        try {
            i18n.getI18n(Locale.US, null);
            fail();
        } catch (final NullPointerException details) {
            // expected
        }

        try {
            I18nObject.getI18n(null, Locale.US, TEST_ONE);
            fail();
        } catch (final NullPointerException details) {
            // expected
        }

        try {
            I18nObject.getI18n(BUNDLE_NAME, null, TEST_ONE);
            fail();
        } catch (final NullPointerException details) {
            // expected
        }

        try {
            I18nObject.getI18n(BUNDLE_NAME, Locale.US, null);
            fail();
        } catch (final NullPointerException details) {
            // expected
        }
    }
}
