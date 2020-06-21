
package info.freelibrary.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests UnsupportedOperationI18nException.
 */
public class UnsupportedOperationI18nExceptionTest {

    private static final String DETAIL = "soup";

    private static final String MESSAGE = "Unsupported operation: soup";

    @Test(expected = UnsupportedOperationI18nException.class)
    public final void testI18nUnsupportedOperationException() throws UnsupportedOperationI18nException {
        throw new UnsupportedOperationI18nException();
    }

    @Test
    public final void testI18nUnsupportedOperationExceptionString() {
        try {
            throw new UnsupportedOperationI18nException(DETAIL);
        } catch (final UnsupportedOperationI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    @Test
    public final void testI18nUnsupportedOperationExceptionThrowableString() {
        try {
            throw new UnsupportedOperationI18nException(new Throwable(DETAIL), DETAIL);
        } catch (final UnsupportedOperationI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertEquals(DETAIL, details.getCause().getMessage());
        }
    }

    @Test
    public final void testI18nUnsupportedOperationExceptionStringStringObjectArray() {
        try {
            throw new UnsupportedOperationI18nException(Constants.BUNDLE_NAME, MessageCodes.UTIL_067, DETAIL);
        } catch (final UnsupportedOperationI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    @Test
    public final void testI18nUnsupportedOperationExceptionThrowableStringStringObjectArray() {
        try {
            throw new UnsupportedOperationI18nException(new Throwable(DETAIL), Constants.BUNDLE_NAME,
                    MessageCodes.UTIL_067, DETAIL);
        } catch (final UnsupportedOperationI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertEquals(DETAIL, details.getCause().getMessage());
        }
    }

}
