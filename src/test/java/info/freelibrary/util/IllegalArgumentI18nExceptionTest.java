
package info.freelibrary.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests of IllegalArgumentI18nException.
 */
public class IllegalArgumentI18nExceptionTest {

    private static final String DETAIL = "bad";

    private static final String MESSAGE = "Illegal argument [java.lang.String]: bad";

    /**
     * Tests IllegalArgumentI18nException constructor that takes an exception.
     */
    @Test
    public final void testIllegalArgumentI18nExceptionObject() {
        try {
            throw new IllegalArgumentI18nException(DETAIL);
        } catch (final IllegalArgumentI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    /**
     * Tests IllegalArgumentI18nException constructor that takes an exception and additional details.
     */
    @Test
    public final void testIllegalArgumentI18nExceptionThrowableObject() {
        try {
            throw new IllegalArgumentI18nException(new Throwable(), DETAIL);
        } catch (final IllegalArgumentI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNotNull(details.getCause());
        }
    }

    /**
     * Tests IllegalArgumentI18nException constructor that takes a bundle, key, and message.
     */
    @Test
    public final void testIllegalArgumentI18nExceptionStringStringObjectArray() {
        try {
            throw new IllegalArgumentI18nException(Constants.BUNDLE_NAME, MessageCodes.UTIL_065, String.class.getName(),
                    DETAIL);
        } catch (final IllegalArgumentI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    /**
     * Tests IllegalArgumentI18Exception constructor that takes an exception, a bundle name, and a key.
     */
    @Test
    public final void testIllegalArgumentI18nExceptionThrowableStringStringObjectArray() {
        try {
            throw new IllegalArgumentI18nException(new Throwable(), Constants.BUNDLE_NAME, MessageCodes.UTIL_065,
                    String.class.getName(), DETAIL);
        } catch (final IllegalArgumentI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNotNull(details.getCause());
        }
    }

}
