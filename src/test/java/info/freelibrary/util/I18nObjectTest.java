
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

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
     * Test method for {@link I18nObject#getI18N(String, Exception)}.
     */
    @Test
    public void testGetI18StringException() {
        String expectedMessage = "MY EXCEPTION";
        Exception exception = new IOException(expectedMessage);
        I18nObjectWrapper test = new I18nObjectWrapper();
        String foundMessage = test.getI18N("test.my.exception", exception);

        assertEquals(expectedMessage, foundMessage);
    }

    /**
     * Test method for {@link I18nObject#getI18N(String, Long)}.
     */
    @Test
    public void testGetI18nStringLong() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18N(String, Int)}.
     */
    @Test
    public void testGetI18nStringInt() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18N(String, String)}.
     */
    @Test
    public void testGetI18nStringString() {
        // fail("Not yet implemented");
    }

    /**
     *  Test method for {@link I18nObject#getI18N(String, Array)}.
     */
    @Test
    public void testGetI18nStringStringArray() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18N(String, File)}.
     */
    @Test
    public void testGetI18nStringFile() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18N(String, FileArray)}.
     */
    @Test
    public void testGetI18nStringFileArray() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link I18nObject#getI18N(String, ObjectArray)}.
     */
    @Test
    public void testGetI18nStringObjectArray() {
        // fail("Not yet implemented");
    }

}
