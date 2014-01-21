
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.MissingResourceException;

import info.freelibrary.util.test.I18nObjectWrapper;

import org.junit.Test;

public class I18nObjectTest {

    /**
     * Test method for {@link I18nObject#getI18n(String)}.
     */
    @Test
    public void testGetI18nString() {
        assertEquals("Retrieving pairtree_root directory at {}",
                new I18nObjectWrapper().getI18n("pt.retrieving_root"));
    }

    /**
     * Test method for {@link I18nObject#getI18n(String, Exception)}.
     */
    @Test
    public void testGetI18nStringException() {
        String expectedMessage = "MY EXCEPTION";
        Exception exception = new IOException(expectedMessage);
        I18nObjectWrapper test = new I18nObjectWrapper();
        String foundMessage = test.getI18n("test.my.exception", exception);

        assertEquals(expectedMessage, foundMessage);
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
     * Test method for {@link I18nObject#getI18n(String, File)}.
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
     * Test method for {@link I18nObject#getI18n(String, ObjectArray)}.
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
        } catch (MissingResourceException details) {
            // this is expected
        }
    }

    /**
     * Test method for {@link I18nObject#hasI18nKey()}.
     */
    @Test
    public void testHasI18nKey() {
        I18nObjectWrapper i18nObj = new I18nObjectWrapper();
        assertTrue(i18nObj.hasI18nKey("test.i18n"));
        assertEquals("test i18n", i18nObj.getI18n("test.i18n"));
    }
}
