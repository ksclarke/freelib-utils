
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests of UnsupportedOperationI18nException.
 */
public class UnsupportedOperationI18nExceptionTest {

    private static final String DETAIL = "soup";

    private static final String MESSAGE = "Unsupported operation: soup";

    /**
     * Tests UnsupportedOperationI18nException's default constructor.
     */
    @Test(expected = UnsupportedOperationI18nException.class)
    public final void testI18nUnsupportedOperationException() throws UnsupportedOperationI18nException {
        throw new UnsupportedOperationI18nException();
    }

    /**
     * Tests UnsupportedOperationI18nException's constructor that takes an UnsupportedOperationException.
     */
    @Test
    public final void testI18nUnsupportedOperationExceptionString() {
        try {
            throw new UnsupportedOperationI18nException(DETAIL);
        } catch (final UnsupportedOperationI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    /**
     * Tests UnsupportedOperationI18nException constructor that takes an exception and additional details.
     */
    @Test
    public final void testI18nUnsupportedOperationExceptionThrowableString() {
        try {
            throw new UnsupportedOperationI18nException(new Throwable(DETAIL), DETAIL);
        } catch (final UnsupportedOperationI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertEquals(DETAIL, details.getCause().getMessage());
        }
    }

    /**
     * Tests UnsupportedOperationI18nException's constructor that takes a bundle name, key, and additional details.
     */
    @Test
    public final void testI18nUnsupportedOperationExceptionStringStringObjectArray() {
        try {
            throw new UnsupportedOperationI18nException(MessageCodes.BUNDLE, MessageCodes.UTIL_067, DETAIL);
        } catch (final UnsupportedOperationI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertNull(details.getCause());
        }
    }

    /**
     * Tests UnsupportedOperationI18nException's constructor that takes an exception, bundle name, and key, and
     * additional details.
     */
    @Test
    public final void testI18nUnsupportedOperationExceptionThrowableStringStringObjectArray() {
        try {
            throw new UnsupportedOperationI18nException(new Throwable(DETAIL), MessageCodes.BUNDLE,
                MessageCodes.UTIL_067, DETAIL);
        } catch (final UnsupportedOperationI18nException details) {
            assertEquals(MESSAGE, details.getMessage());
            assertEquals(DETAIL, details.getCause().getMessage());
        }
    }

}
