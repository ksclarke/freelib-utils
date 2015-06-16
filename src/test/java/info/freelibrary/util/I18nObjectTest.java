
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.test.I18nObjectWrapper;

public class I18nObjectTest {

    private static final Locale myLocale = Locale.getDefault();

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
        final String found = new I18nObjectWrapper().getI18n("test.value.one", new IOException(expected));

        assertEquals(expected, found);
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Long)}.
     */
    @Test
    public void testGetI18nStringLong() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Int)}.
     */
    @Test
    public void testGetI18nStringInt() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, String)}.
     */
    @Test
    public void testGetI18nStringString() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Array)}.
     */
    @Test
    public void testGetI18nStringStringArray() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, java.io.File)}.
     */
    @Test
    public void testGetI18nStringFile() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, FileArray)}.
     */
    @Test
    public void testGetI18nStringFileArray() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, java.lang.ObjectArray)}.
     */
    @Test
    public void testGetI18nStringObjectArray() {
        // fail("Not yet implemented");
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
