/**
 *
 */

package info.freelibrary.util;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.Test;

/**
 *
 */
public class UnsupportedEncodingI18nExceptionTest {

    private static final String MESSAGE = "Unsupported encoding: UTF-8";

    /**
     * Test method for {@link UnsupportedEncodingI18nException#I18nUnsupportedEncodingException(String)}.
     */
    @Test
    public final void testI18nUnsupportedEncodingExceptionString() {
        try {
            throw new UnsupportedEncodingI18nException(StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    /**
     * Test method for {@link UnsupportedEncodingI18nException#I18nUnsupportedEncodingException(Charset)}.
     */
    @Test
    public final void testI18nUnsupportedEncodingExceptionCharset() {
        try {
            Locale.setDefault(Locale.US);
            throw new UnsupportedEncodingI18nException(StandardCharsets.UTF_8);
        } catch (final UnsupportedEncodingI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    /**
     * Test method for {@link UnsupportedEncodingI18nException#I18nUnsupportedEncodingException(Charset, Locale)}.
     */
    @Test
    public final void testI18nUnsupportedEncodingExceptionCharsetLocale() {
        try {
            throw new UnsupportedEncodingI18nException(StandardCharsets.UTF_8, Locale.US);
        } catch (final UnsupportedEncodingI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    /**
     * Test method for {@link UnsupportedEncodingI18nException#I18nUnsupportedEncodingException(Throwable, String)}.
     */
    @Test
    public final void testI18nUnsupportedEncodingExceptionThrowableString() {
        try {
            throw new UnsupportedEncodingI18nException(new Throwable(), StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNotNull(details.getCause());
        }
    }

    /**
     * Test method for {@link UnsupportedEncodingI18nException#I18nUnsupportedEncodingException(Throwable, Charset)}.
     */
    @Test
    public final void testI18nUnsupportedEncodingExceptionThrowableCharset() {
        try {
            Locale.setDefault(Locale.US);
            throw new UnsupportedEncodingI18nException(new Throwable(), StandardCharsets.UTF_8);
        } catch (final UnsupportedEncodingI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNotNull(details.getCause());
        }
    }

    /**
     * Test method for
     * {@link UnsupportedEncodingI18nException#I18nUnsupportedEncodingException(Throwable, Charset, Locale)}.
     */
    @Test
    public final void testI18nUnsupportedEncodingExceptionThrowableCharsetLocale() {
        try {
            throw new UnsupportedEncodingI18nException(new Throwable(), StandardCharsets.UTF_8, Locale.US);
        } catch (final UnsupportedEncodingI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNotNull(details.getCause());
        }
    }

    /**
     * Test method for
     * {@link UnsupportedEncodingI18nException#I18nUnsupportedEncodingException(String, String, Object[])}.
     */
    @Test
    public final void testI18nUnsupportedEncodingExceptionStringStringObjectArray() {
        try {
            throw new UnsupportedEncodingI18nException(MessageCodes.BUNDLE, MessageCodes.UTIL_028,
                StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    /**
     * Test method for
     * {@link UnsupportedEncodingI18nException#I18nUnsupportedEncodingException(Throwable, String, String, Object[])}.
     */
    @Test
    public final void testI18nUnsupportedEncodingExceptionThrowableStringStringObjectArray() {
        try {
            throw new UnsupportedEncodingI18nException(new Throwable(), MessageCodes.BUNDLE, MessageCodes.UTIL_028,
                StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNotNull(details.getCause());
        }
    }

}
