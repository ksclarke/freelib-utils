/**
 *
 */

package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.test.I18nExceptionWrapper;

/**
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class I18nExceptionTest {

    private static final String BUNDLE_NAME = "test_freelib-utils_messages";

    private static final Locale LOCALE = Locale.getDefault();

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Locale.setDefault(Locale.US);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        Locale.setDefault(LOCALE);
    }

    /**
     * Test method for {@link info.freelibrary.util.I18nException#I18nException()}.
     */
    @Test
    public void testI18nException() {
        assertNull(new I18nExceptionWrapper().getMessage());
    }

    /**
     * Test method for {@link info.freelibrary.util.I18nException#I18nException(String, String)}.
     */
    @Test
    public void testI18nExceptionStringString() {
        assertEquals("one", new I18nExceptionWrapper(BUNDLE_NAME, "test.one").getMessage());
    }

    /**
     * Test method for {@link info.freelibrary.util.I18nException#I18nException(Locale, String, String)}.
     */
    @Test
    public void testI18nExceptionLocaleStringString() {
        assertEquals("one", new I18nExceptionWrapper(Locale.US, BUNDLE_NAME, "test.one").getMessage());
    }

    /**
     * Test method for {@link info.freelibrary.util.I18nException#I18nException(String, String, Object[])} .
     */
    @Test
    public void testI18nExceptionStringStringObjectArray() {
        /* Test single values passed into object varargs */

        assertEquals("one", new I18nExceptionWrapper(BUNDLE_NAME, "test.value.one", "one").getMessage());

        assertEquals("one", new I18nExceptionWrapper(BUNDLE_NAME, "test.value.one", new Object[] {
            "one"
        }).getMessage());

        assertEquals("1", new I18nExceptionWrapper(BUNDLE_NAME, "test.value.one", 1).getMessage());

        try {
            final File file = File.createTempFile(UUID.randomUUID().toString(), ".txt");

            file.deleteOnExit();
            assertEquals(file.getAbsolutePath(), new I18nExceptionWrapper(BUNDLE_NAME, "test.value.one", file)
                    .getMessage());
        } catch (final IOException details) {
            fail(details.getMessage());
        }

        /* Test multiple values passed into object varargs */
        assertEquals("one and two", new I18nExceptionWrapper(BUNDLE_NAME, "test.value.two", "one", "two").getMessage());

        assertEquals("one and 2", new I18nExceptionWrapper(BUNDLE_NAME, "test.value.two", new Object[] {
            "one", 2
        }).getMessage());

        assertEquals("one and 2", new I18nExceptionWrapper(BUNDLE_NAME, "test.value.two", "one", 2).getMessage());

        assertEquals("1 and 2", new I18nExceptionWrapper(BUNDLE_NAME, "test.value.two", 1, 2).getMessage());

        try {
            final File file = File.createTempFile(UUID.randomUUID().toString(), ".txt");

            file.deleteOnExit();

            assertEquals("one and " + file.getAbsolutePath(), new I18nExceptionWrapper(BUNDLE_NAME, "test.value.two",
                    new Object[] {
                        "one", file
                    }).getMessage());
        } catch (final IOException details) {
            fail(details.getMessage());
        }

        try {
            final File file = File.createTempFile(UUID.randomUUID().toString(), ".txt");

            file.deleteOnExit();

            assertEquals("one and " + file.getAbsolutePath(), new I18nExceptionWrapper(BUNDLE_NAME, "test.value.two",
                    "one", file).getMessage());
        } catch (final IOException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link I18nException#I18nException(Locale, String, String, Object[])}.
     */
    @Test
    public void testI18nExceptionLocaleStringStringObjectArray() {

    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable)}.
     */
    @Test
    public void testI18nExceptionThrowable() {

    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable, String, String)}.
     */
    @Test
    public void testI18nExceptionThrowableStringString() {

    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable, Locale, String, String)}.
     */
    @Test
    public void testI18nExceptionThrowableLocaleStringString() {

    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable, String, String, Object[])} .
     */
    @Test
    public void testI18nExceptionThrowableStringStringObjectArray() {

    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable, Locale, String, String, Object[])} .
     */
    @Test
    public void testI18nExceptionThrowableLocaleStringStringObjectArray() {

    }

}