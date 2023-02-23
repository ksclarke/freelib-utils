
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
 * Creates a new exception test.
 */
public class I18nExceptionTest {

    /** A test bundle name. */
    private static final String BUNDLE_NAME = "test_freelib-utils_messages";

    /** A default locale. */
    private static final Locale LOCALE = Locale.getDefault();

    /** A test value. */
    private static final String VALUE_ONE = "test.value.one";

    /** A test value. */
    private static final String VALUE_TWO = "test.value.two";

    /** A test key. */
    private static final String ONE = "one";

    /** A test key. */
    private static final String TEST_ONE = "test.one";

    /** A constant for the TXT file extension. */
    private static final String TEXT_EXT = ".txt";

    /** A test value. */
    private static final String ONE_AND_2 = "one and 2";

    /** A test value. */
    private static final String ONE_AND = "one and ";

    /** A message pattern constant. */
    private static final String MESSAGE = "{}";

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        Locale.setDefault(Locale.US);
    }

    /**
     * Tears down the testing environment.
     */
    @After
    public void tearDown() {
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
        assertEquals(ONE, new I18nExceptionWrapper(BUNDLE_NAME, TEST_ONE).getMessage());
    }

    /**
     * Test method for {@link info.freelibrary.util.I18nException#I18nException(Locale, String, String)}.
     */
    @Test
    public void testI18nExceptionLocaleStringString() {
        assertEquals(ONE, new I18nExceptionWrapper(Locale.US, BUNDLE_NAME, TEST_ONE).getMessage());
    }

    /**
     * Test method for {@link info.freelibrary.util.I18nException#I18nException(String, String, Object[])} .
     */
    @Test
    public void testI18nExceptionStringStringObjectArray() {
        /* Test single values passed into object varargs */
        assertEquals(ONE, new I18nExceptionWrapper(BUNDLE_NAME, VALUE_ONE, ONE).getMessage());

        assertEquals(ONE, new I18nExceptionWrapper(BUNDLE_NAME, VALUE_ONE, ONE).getMessage());

        assertEquals("1", new I18nExceptionWrapper(BUNDLE_NAME, VALUE_ONE, 1).getMessage());

        try {
            final File file = File.createTempFile(UUID.randomUUID().toString(), TEXT_EXT);

            file.deleteOnExit();
            assertEquals(file.getAbsolutePath(), new I18nExceptionWrapper(BUNDLE_NAME, VALUE_ONE, file).getMessage());
        } catch (final IOException details) {
            fail(details.getMessage());
        }

        /* Test multiple values passed into object varargs */
        assertEquals("one and two", new I18nExceptionWrapper(BUNDLE_NAME, VALUE_TWO, ONE, "two").getMessage());

        assertEquals(ONE_AND_2, new I18nExceptionWrapper(BUNDLE_NAME, VALUE_TWO, ONE, 2).getMessage());

        assertEquals(ONE_AND_2, new I18nExceptionWrapper(BUNDLE_NAME, VALUE_TWO, ONE, 2).getMessage());

        assertEquals("1 and 2", new I18nExceptionWrapper(BUNDLE_NAME, VALUE_TWO, 1, 2).getMessage());

        try {
            final File file = File.createTempFile(UUID.randomUUID().toString(), TEXT_EXT);

            file.deleteOnExit();

            assertEquals(ONE_AND + file.getAbsolutePath(),
                    new I18nExceptionWrapper(BUNDLE_NAME, VALUE_TWO, ONE, file).getMessage());
        } catch (final IOException details) {
            fail(details.getMessage());
        }

        try {
            final File file = File.createTempFile(UUID.randomUUID().toString(), TEXT_EXT);

            file.deleteOnExit();

            assertEquals(ONE_AND + file.getAbsolutePath(),
                    new I18nExceptionWrapper(BUNDLE_NAME, VALUE_TWO, ONE, file).getMessage());
        } catch (final IOException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link I18nException#I18nException(Locale, String, String, Object[])}.
     */
    @Test
    public void testI18nExceptionLocaleStringStringObjectArray() {
        final Object[] details = { ONE };
        final Exception exception = new I18nExceptionWrapper(Locale.ENGLISH, BUNDLE_NAME, VALUE_ONE, details);

        assertEquals(ONE, exception.getMessage());
    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable)}.
     */
    @Test
    public void testI18nExceptionThrowable() {
        final I18nException exception = new I18nExceptionWrapper(new RuntimeException());

        assertEquals("RuntimeException", exception.getCause().getClass().getSimpleName());
    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable, String, String)}.
     */
    @Test
    public void testI18nExceptionThrowableStringString() {
        final I18nException exception = new I18nExceptionWrapper(new RuntimeException(), BUNDLE_NAME, VALUE_ONE);

        assertEquals(MESSAGE, exception.getMessage());
    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable, Locale, String, String)}.
     */
    @Test
    public void testI18nExceptionThrowableLocaleStringString() {
        final I18nException exception =
                new I18nExceptionWrapper(new RuntimeException(), Locale.ENGLISH, BUNDLE_NAME, VALUE_ONE);

        assertEquals(MESSAGE, exception.getMessage());
    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable, String, String, Object[])} .
     */
    @Test
    public void testI18nExceptionThrowableStringStringObjectArray() {
        final I18nException exception = new I18nExceptionWrapper(new RuntimeException(), BUNDLE_NAME, VALUE_ONE, ONE);

        assertEquals(ONE, exception.getMessage());
    }

    /**
     * Test method for {@link I18nException#I18nException(Throwable, Locale, String, String, Object[])} .
     */
    @Test
    public void testI18nExceptionThrowableLocaleStringStringObjectArray() {
        final I18nException exception =
                new I18nExceptionWrapper(new RuntimeException(), Locale.ENGLISH, BUNDLE_NAME, VALUE_ONE, ONE);

        assertEquals(ONE, exception.getMessage());
    }

}
