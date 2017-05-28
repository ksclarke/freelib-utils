
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

public class I18nObjectTest {

    private static final Locale myLocale = Locale.getDefault();

    private static final String VALUE_ONE = "test.value.one";

    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    @Before
    public void beforeTests() {
        Locale.setDefault(Locale.US);
    }

    @After
    public void afterTests() {
        Locale.setDefault(myLocale);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String)}.
     */
    @Test
    public void testGetI18nString() {
        assertEquals("one", new I18nObjectWrapper().getI18n("test.one"));
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Exception)}.
     */
    @Test
    public void testGetI18nStringException() {
        final String expected = "one";
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
        final String expected = "one";
        final String found = new I18nObjectWrapper().getI18n(VALUE_ONE, expected);

        assertEquals(expected, found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Array)}.
     */
    @Test
    public void testGetI18nStringStringArray() {
        final String expected = "one";
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

            fail("Failed to throw MissingResourceException");
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
        assertTrue(i18nObj.hasI18nKey("test.one"));
        assertEquals("one", i18nObj.getI18n("test.one"));
    }
}
