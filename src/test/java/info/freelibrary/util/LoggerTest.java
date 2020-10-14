
package info.freelibrary.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.impl.SimpleLogger;

import info.freelibrary.util.test.TestUtils;

/**
 * Tests FreeLibrary Logger facade.
 */
public class LoggerTest {

    private Logger myLogger;

    /**
     * Test set-up.
     *
     * @throws Exception If there is trouble setting up a test.
     */
    @Before
    public void setUp() throws Exception {
        System.setProperty(LoggerTestConstants.LOG_LEVEL, "trace");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(UUID.randomUUID().toString()),
                LoggerTestConstants.BUNDLE_NAME);
    }

    /**
     * Test clean up.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        System.clearProperty(LoggerTestConstants.LOG_LEVEL);
    }

    /**
     * Tests constructor.
     */
    @Test
    public void testSimpleConstructor() {
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(LoggerTest.class));
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessage() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message key.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKey() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.MESSAGE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE, LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.MESSAGE, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected =
                LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ASDF, LoggerTestConstants.SADF });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_DEBUG + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE,
                    LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    // End of debug tests

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessage() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKey() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.MESSAGE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableMessageKey() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException(LoggerTestConstants.BAD), LoggerTestConstants.TEST_ONE);
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableNullMessageKey() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableMessage() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException(LoggerTestConstants.BAD), LoggerTestConstants.ONE);
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableNullMessage() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyThrowableVarargs() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException(LoggerTestConstants.BAD), LoggerTestConstants.TEST_VALUE_TWO,
                    LoggerTestConstants.ONE, LoggerTestConstants.TWO);
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyThrowableNullVarargs() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE,
                    LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableMessageVarargs() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException(LoggerTestConstants.BAD), LoggerTestConstants.THIS_AND_THAT,
                    LoggerTestConstants.ONE, LoggerTestConstants.TWO);
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableNullMessageVarargs() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, LoggerTestConstants.THIS_AND_THAT, LoggerTestConstants.ONE,
                    LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableMessageVarargs2() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException(LoggerTestConstants.BAD), LoggerTestConstants.ONE_AND_TWO, new String[] {});
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableNullMessageVarargs2() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, LoggerTestConstants.ONE_AND_TWO, new String[] {});
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE, LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.MESSAGE, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected =
                LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ASDF, LoggerTestConstants.SADF });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_ERROR + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE,
                    LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    // End of error tests

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessage() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKey() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.MESSAGE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE, LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.MESSAGE, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ASDF, LoggerTestConstants.SADF });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_INFO + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE,
                    LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    // End of info tests

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessage() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKey() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.MESSAGE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE, LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.MESSAGE, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected =
                LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ASDF, LoggerTestConstants.SADF });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_TRACE + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE,
                    LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    // End of trace tests

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessage() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKey() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.MESSAGE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageVarargDetails() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageThrowable() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageThrowableNull() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.TEST_ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyObjectObject() throws IOException {
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE, LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ASDF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.MESSAGE, LoggerTestConstants.ASDF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.TEST_VALUE_ONE, LoggerTestConstants.ONE);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.THIS_AND_THAT,
                    new String[] { LoggerTestConstants.ASDF, LoggerTestConstants.SADF });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO,
                    new String[] { LoggerTestConstants.ONE, LoggerTestConstants.TWO });
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.TEST_ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.TEST_ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.ONE, new IOException(LoggerTestConstants.BAD));
        });
        final String[] parts = found.split(LoggerTestConstants.BACKSLASH_R);

        assertEquals(expected, parts[0]);
        assertEquals(LoggerTestConstants.BAD_EXCEPTION, parts[1]);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.ONE, (Throwable) null);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.ASDF_SADF;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, MessageCodes.UTIL_060, LoggerTestConstants.ASDF, LoggerTestConstants.SADF);
        });

        assertEquals(expected, found);
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(LoggerTestConstants.NOTIFY_ADMIN);
        final String expected = LoggerTestConstants.MAIN_WARN + myLogger.getName() + LoggerTestConstants.DASH_ONE_TWO;
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE,
                    LoggerTestConstants.TWO);
        });

        assertEquals(expected, found);
    }

    // End warn tests

    /**
     * Tests getLoggerImpl().
     */
    @Test
    public void testGetLoggerImpl() {
        assertEquals(LoggerTestConstants.SIMPLE_LOGGER, myLogger.getLoggerImpl().getClass().getName());
    }

    /**
     * Test getMessage().
     */
    @Test
    public void testGetMessage() {
        final String found = myLogger.getMessage(LoggerTestConstants.ONE);

        assertEquals(LoggerTestConstants.ONE, found);
    }

    /**
     * Test getMessage(Varargs).
     */
    @Test
    public void testGetMessageVarargs() {
        final String found = myLogger.getMessage(LoggerTestConstants.THIS_AND_THAT, LoggerTestConstants.ONE,
                LoggerTestConstants.TWO);

        assertEquals(LoggerTestConstants.ONE_AND_TWO, found);
    }

    /**
     * Tests getMessage(String)
     */
    @Test
    public void testGetMessageKey() {
        final String found = myLogger.getMessage(LoggerTestConstants.TEST_VALUE_TWO, LoggerTestConstants.ONE,
                LoggerTestConstants.TWO);

        assertEquals(LoggerTestConstants.ONE_AND_TWO, found);
    }

    /**
     * Tests isWarnEnabled().
     */
    @Test
    public void testIsWarnEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.WARN);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isWarnEnabled());
    }

    /**
     * Tests isWarnEnabled().
     */
    @Test
    public void testIsWarnEnabledBad() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.ERROR);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertFalse(myLogger.isWarnEnabled());
    }

    /**
     * Tests isTraceEnabled().
     */
    @Test
    public void testIsTraceEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.TRACE);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isTraceEnabled());
    }

    /**
     * Tests isTraceEnabled().
     */
    @Test
    public void testIsTraceEnabledBad() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.ERROR);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertFalse(myLogger.isTraceEnabled());
    }

    /**
     * Tests isInfoEnabled().
     */
    @Test
    public void testIsInfoEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.INFO);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isInfoEnabled());
    }

    /**
     * Tests isInfoEnabled().
     */
    @Test
    public void testIsInfoEnabledBad() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.ERROR);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertFalse(myLogger.isInfoEnabled());
    }

    /**
     * Tests isErrorEnabled().
     */
    @Test
    public void testIsErrorEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.ERROR);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isErrorEnabled());
    }

    /**
     * Tests isDebugEnabled().
     */
    @Test
    public void testIsDebugEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.DEBUG);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isDebugEnabled());
    }

    /**
     * Tests isDebugEnabled().
     */
    @Test
    public void testIsDebugEnabledBad() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.WARN);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertFalse(myLogger.isDebugEnabled());
    }

    /**
     * Tests isWarnEnabled(Marker).
     */
    @Test
    public void testIsWarnMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.WARN);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isWarnEnabled(marker));
    }

    /**
     * Tests isWarnEnabled(Marker).
     */
    @Test
    public void testIsWarnMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.ERROR);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertFalse(myLogger.isWarnEnabled(marker));
    }

    /**
     * Tests isTraceEnabled(Marker).
     */
    @Test
    public void testIsTraceMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.TRACE);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isTraceEnabled(marker));
    }

    /**
     * Tests isTraceEnabled(Marker).
     */
    @Test
    public void testIsTraceMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.ERROR);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertFalse(myLogger.isTraceEnabled(marker));
    }

    /**
     * Tests isInfoEnabled(Marker).
     */
    @Test
    public void testIsInfoMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.INFO);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isInfoEnabled(marker));
    }

    /**
     * Tests isInfoEnabled(Marker).
     */
    @Test
    public void testIsInfoMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.ERROR);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertFalse(myLogger.isInfoEnabled(marker));
    }

    /**
     * Tests isErrorEnabled(Marker).
     */
    @Test
    public void testIsErrorMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.ERROR);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isErrorEnabled(marker));
    }

    /**
     * Tests isDebugEnabled(Marker).
     */
    @Test
    public void testIsDebugMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.DEBUG);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertTrue(myLogger.isDebugEnabled(marker));
    }

    /**
     * Tests isDebugEnabled(Marker).
     */
    @Test
    public void testIsDebugMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker(LoggerTestConstants.TEST);
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, LoggerTestConstants.WARN);
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), LoggerTestConstants.BUNDLE_NAME);

        assertFalse(myLogger.isDebugEnabled(marker));
    }

    private static final class LoggerTestConstants {

        private static final String LOG_LEVEL = "org.slf4j.simpleLogger.defaultLogLevel";

        private static final String SIMPLE_LOGGER = "org.slf4j.impl.SimpleLogger";

        private static final String BUNDLE_NAME = "test_freelib-utils_messages";

        private static final String DASH_ASDF = " - asdf";

        private static final String DASH_ONE = " - one";

        private static final String DASH_ONE_TWO = " - one and two";

        private static final String DASH_ASDF_SADF = " - asdf and sadf";

        private static final String ONE = "one";

        private static final String TWO = "two";

        private static final String ASDF = "asdf";

        private static final String SADF = "sadf";

        private static final String ERROR = "ERROR";

        private static final String TRACE = "TRACE";

        private static final String INFO = "INFO";

        private static final String TEST = "TEST";

        private static final String DEBUG = "DEBUG";

        private static final String WARN = "WARN";

        private static final String MAIN_DEBUG = "[main] DEBUG ";

        private static final String MAIN_WARN = "[main] WARN ";

        private static final String MAIN_TRACE = "[main] TRACE ";

        private static final String MAIN_INFO = "[main] INFO ";

        private static final String MAIN_ERROR = "[main] ERROR ";

        private static final String ONE_AND_TWO = "one and two";

        private static final String NOTIFY_ADMIN = "NOTIFY_ADMIN";

        private static final String BACKSLASH_R = "\\R";

        private static final String BAD = "bad";

        private static final String TEST_ONE = "test.one";

        private static final String BAD_EXCEPTION = "java.io.IOException: bad";

        private static final String TEST_VALUE_ONE = "test.value.one";

        private static final String TEST_VALUE_TWO = "test.value.two";

        private static final String MESSAGE = "{}";

        private static final String THIS_AND_THAT = "{} and {}";

        private static final String ASDF_SADF = " - asdf | sadf";

        private LoggerTestConstants() {
        }
    }
}
