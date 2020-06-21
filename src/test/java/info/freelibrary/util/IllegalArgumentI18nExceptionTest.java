
package info.freelibrary.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class IllegalArgumentI18nExceptionTest {

    private static final String DETAIL = "bad";

    private static final String MESSAGE = "Illegal argument [java.lang.String]: bad";

    @Test
    public final void testI18nIllegalArgumentExceptionObject() {
        try {
            throw new IllegalArgumentI18nException(DETAIL);
        } catch (final IllegalArgumentI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    @Test
    public final void testI18nIllegalArgumentExceptionThrowableObject() {
        try {
            throw new IllegalArgumentI18nException(new Throwable(), DETAIL);
        } catch (final IllegalArgumentI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNotNull(details.getCause());
        }
    }

    @Test
    public final void testI18nIllegalArgumentExceptionStringStringObjectArray() {
        try {
            throw new IllegalArgumentI18nException(Constants.BUNDLE_NAME, MessageCodes.UTIL_065, String.class
                    .getName(), DETAIL);
        } catch (final IllegalArgumentI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    @Test
    public final void testI18nIllegalArgumentExceptionThrowableStringStringObjectArray() {
        try {
            throw new IllegalArgumentI18nException(new Throwable(), Constants.BUNDLE_NAME, MessageCodes.UTIL_065,
                    String.class.getName(), DETAIL);
        } catch (final IllegalArgumentI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNotNull(details.getCause());
        }
    }

}
