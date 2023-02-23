
package info.freelibrary.util;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;

/**
 * Tests FreeLibrary Logger facade.
 */
public class LoggerTest {

    /**
     * An end of line regular expression.
     */
    private static final String EOL_REGEXP = "\\r\\n|\\n|\\r";

    /**
     * A fake line number used in testing.
     */
    private static final String TEST_LINE_NUM = ":999";

    /**
     * The stream from which we get the logging output.
     */
    private ByteArrayOutputStream myLogStream;

    /**
     * The logger being tested.
     */
    private Logger myLogger;

    /**
     * Sets up the test by configuring the logger to be tested.
     *
     * @throws Exception If there is trouble setting up a test.
     */
    @Before
    public void setUp() throws Exception {
        final ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(UUID.randomUUID().toString());
        final LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
        final PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        final OutputStreamAppender<ILoggingEvent> appender = new OutputStreamAppender<>();

        myLogStream = new ByteArrayOutputStream();

        encoder.setPattern("[%thread] %level %logger%X{line} - %msg%n");
        encoder.setContext(loggerContext);
        encoder.start();

        appender.setName("OutputStream Appender");
        appender.setContext(loggerContext);
        appender.setEncoder(encoder);
        appender.setOutputStream(myLogStream);
        appender.start();

        logger.setAdditive(false);
        logger.setLevel(Level.TRACE);
        logger.addAppender(appender);

        Locale.setDefault(Locale.US);
        myLogger = new Logger(logger, TestConstants.BUNDLE_NAME);
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessage() throws IOException {
        myLogger.debug(TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests debug message key.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKey() throws IOException {
        myLogger.debug(TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyDetails() throws IOException {
        myLogger.debug(TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageDetails() throws IOException {
        myLogger.debug(TestConstants.MESSAGE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyVarargDetails() throws IOException {
        myLogger.debug(TestConstants.TEST_VALUE_TWO, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageVarargDetails() throws IOException {
        myLogger.debug(TestConstants.THIS_AND_THAT, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyThrowable() throws IOException {
        myLogger.debug(TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyThrowableNull() throws IOException {
        myLogger.debug(TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageThrowable() throws IOException {
        myLogger.debug(TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageThrowableNull() throws IOException {
        myLogger.debug(TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageObjectObject() throws IOException {
        myLogger.debug(MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMessageKeyObjectObject() throws IOException {
        myLogger.debug(TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.MESSAGE, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.THIS_AND_THAT,
                new Object[] { TestConstants.ASDF, TestConstants.SADF });
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ASDF_SADF, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.TEST_VALUE_TWO,
                new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests debug message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testDebugMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.debug(notifyAdmin, TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_DEBUG + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    // End of debug tests

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessage() throws IOException {
        myLogger.error(TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKey() throws IOException {
        myLogger.error(TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyDetails() throws IOException {
        myLogger.error(TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageDetails() throws IOException {
        myLogger.error(TestConstants.MESSAGE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyVarargDetails() throws IOException {
        myLogger.error(TestConstants.TEST_VALUE_TWO, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageVarargDetails() throws IOException {
        myLogger.error(TestConstants.THIS_AND_THAT, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyThrowable() throws IOException {
        myLogger.error(TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyThrowableNull() throws IOException {
        myLogger.error(TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageThrowable() throws IOException {
        myLogger.error(TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageThrowableNull() throws IOException {
        myLogger.error(TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableMessageKey() throws IOException {
        myLogger.error(new IOException(TestConstants.BAD), TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableNullMessageKey() throws IOException {
        myLogger.error((Throwable) null, TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableMessage() throws IOException {
        myLogger.error(new IOException(TestConstants.BAD), TestConstants.ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableNullMessage() throws IOException {
        myLogger.error((Throwable) null, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyThrowableVarargs() throws IOException {
        myLogger.error(new IOException(TestConstants.BAD), TestConstants.TEST_VALUE_TWO, TestConstants.ONE,
                TestConstants.TWO);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyThrowableNullVarargs() throws IOException {
        myLogger.error((Throwable) null, TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableMessageVarargs() throws IOException {
        myLogger.error(new IOException(TestConstants.BAD), TestConstants.THIS_AND_THAT, TestConstants.ONE,
                TestConstants.TWO);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableNullMessageVarargs() throws IOException {
        myLogger.error((Throwable) null, TestConstants.THIS_AND_THAT, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableMessageVarargs2() throws IOException {
        myLogger.error(new IOException(TestConstants.BAD), TestConstants.ONE_AND_TWO, new Object[] {});
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorThrowableNullMessageVarargs2() throws IOException {
        myLogger.error((Throwable) null, TestConstants.ONE_AND_TWO, new Object[] {});
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageObjectObject() throws IOException {
        myLogger.error(MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMessageKeyObjectObject() throws IOException {
        myLogger.error(TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.MESSAGE, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.THIS_AND_THAT,
                new Object[] { TestConstants.ASDF, TestConstants.SADF });
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ASDF_SADF, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.TEST_VALUE_TWO,
                new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests error message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testErrorMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.error(notifyAdmin, TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_ERROR + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    // End of error tests

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessage() throws IOException {
        myLogger.info(TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKey() throws IOException {
        myLogger.info(TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyDetails() throws IOException {
        myLogger.info(TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageDetails() throws IOException {
        myLogger.info(TestConstants.MESSAGE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyVarargDetails() throws IOException {
        myLogger.info(TestConstants.TEST_VALUE_TWO, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageVarargDetails() throws IOException {
        myLogger.info(TestConstants.THIS_AND_THAT, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyThrowable() throws IOException {
        myLogger.info(TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyThrowableNull() throws IOException {
        myLogger.info(TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageThrowable() throws IOException {
        myLogger.info(TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageThrowableNull() throws IOException {
        myLogger.info(TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageObjectObject() throws IOException {
        myLogger.info(MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMessageKeyObjectObject() throws IOException {
        myLogger.info(TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.MESSAGE, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.THIS_AND_THAT,
                new Object[] { TestConstants.ASDF, TestConstants.SADF });
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ASDF_SADF, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.TEST_VALUE_TWO, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests info message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testInfoMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.info(notifyAdmin, TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_INFO + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    // End of info tests

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessage() throws IOException {
        myLogger.trace(TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKey() throws IOException {
        myLogger.trace(TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyDetails() throws IOException {
        myLogger.trace(TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageDetails() throws IOException {
        myLogger.trace(TestConstants.MESSAGE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyVarargDetails() throws IOException {
        myLogger.trace(TestConstants.TEST_VALUE_TWO, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageVarargDetails() throws IOException {
        myLogger.trace(TestConstants.THIS_AND_THAT, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyThrowable() throws IOException {
        myLogger.trace(TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyThrowableNull() throws IOException {
        myLogger.trace(TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageThrowable() throws IOException {
        myLogger.trace(TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageThrowableNull() throws IOException {
        myLogger.trace(TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageObjectObject() throws IOException {
        myLogger.trace(MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMessageKeyObjectObject() throws IOException {
        myLogger.trace(TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.MESSAGE, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.THIS_AND_THAT,
                new Object[] { TestConstants.ASDF, TestConstants.SADF });
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ASDF_SADF, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.TEST_VALUE_TWO,
                new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests trace message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testTraceMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.trace(notifyAdmin, TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_TRACE + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    // End of trace tests

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessage() throws IOException {
        myLogger.warn(TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKey() throws IOException {
        myLogger.warn(TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyDetails() throws IOException {
        myLogger.warn(TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageDetails() throws IOException {
        myLogger.warn(TestConstants.MESSAGE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyVarargDetails() throws IOException {
        myLogger.warn(TestConstants.TEST_VALUE_TWO, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageVarargDetails() throws IOException {
        myLogger.warn(TestConstants.THIS_AND_THAT, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyThrowable() throws IOException {
        myLogger.warn(TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyThrowableNull() throws IOException {
        myLogger.warn(TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageThrowable() throws IOException {
        myLogger.warn(TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageThrowableNull() throws IOException {
        myLogger.warn(TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.TEST_ONE);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageObjectObject() throws IOException {
        myLogger.warn(MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMessageKeyObjectObject() throws IOException {
        myLogger.warn(TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.MESSAGE, TestConstants.ASDF);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ASDF, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.TEST_VALUE_ONE, TestConstants.ONE);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.THIS_AND_THAT,
                new Object[] { TestConstants.ASDF, TestConstants.SADF });
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ASDF_SADF, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.TEST_VALUE_TWO, new Object[] { TestConstants.ONE, TestConstants.TWO });
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.TEST_ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.TEST_ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.ONE, new IOException(TestConstants.BAD));
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
        assertTrue(getStackTrace().startsWith(IOException.class.getName()));
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.ONE, (Throwable) null);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, MessageCodes.UTIL_060, TestConstants.ASDF, TestConstants.SADF);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.ASDF_SADF, getLog());
    }

    /**
     * Tests warn message.
     *
     * @throws IOException If there is trouble capturing StdOut
     */
    @Test
    public void testWarnMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker(TestConstants.NOTIFY_ADMIN);

        myLogger.warn(notifyAdmin, TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO);
        assertEquals(TestConstants.MAIN_WARN + getLoggerInfo() + TestConstants.DASH_ONE_TWO, getLog());
    }

    // End warn tests

    /**
     * Test getMessage().
     */
    @Test
    public void testGetMessage() {
        assertEquals(TestConstants.ONE, myLogger.getMessage(TestConstants.ONE));
    }

    /**
     * Test getMessage(Varargs).
     */
    @Test
    public void testGetMessageVarargs() {
        assertEquals(TestConstants.ONE_AND_TWO,
                myLogger.getMessage(TestConstants.THIS_AND_THAT, TestConstants.ONE, TestConstants.TWO));
    }

    /**
     * Tests getMessage(String).
     */
    @Test
    public void testGetMessageKey() {
        assertEquals(TestConstants.ONE_AND_TWO,
                myLogger.getMessage(TestConstants.TEST_VALUE_TWO, TestConstants.ONE, TestConstants.TWO));
    }

    /**
     * Tests isWarnEnabled().
     */
    @Test
    public void testIsWarnEnabledGood() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        assertTrue(myLogger.isWarnEnabled());
    }

    /**
     * Tests isWarnEnabled() doesn't include a line number.
     */
    @Test
    public void testIsWarnEnabledLogWithoutLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        myLogger.warn(UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isWarnEnabled() includes a line number.
     */
    @Test
    public void testIsWarnEnabledLogWithLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        myLogger.warn(UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isWarnEnabled().
     */
    @Test
    public void testIsWarnEnabledBad() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.ERROR);
        assertFalse(myLogger.isWarnEnabled());
    }

    /**
     * Tests isTraceEnabled().
     */
    @Test
    public void testIsTraceEnabledGood() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.TRACE);
        assertTrue(myLogger.isTraceEnabled());
    }

    /**
     * Tests isTraceEnabled() doesn't include a line number.
     */
    @Test
    public void testIsTraceEnabledLogWithoutLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        myLogger.trace(UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isTraceEnabled() includes a line number.
     */
    @Test
    public void testIsTraceEnabledLogWithLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.TRACE);
        myLogger.trace(UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isTraceEnabled().
     */
    @Test
    public void testIsTraceEnabledBad() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        assertFalse(myLogger.isTraceEnabled());
    }

    /**
     * Tests isInfoEnabled().
     */
    @Test
    public void testIsInfoEnabledGood() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.INFO);
        assertTrue(myLogger.isInfoEnabled());
    }

    /**
     * Tests isInfoEnabled() doesn't include a line number.
     */
    @Test
    public void testIsInfoEnabledLogWithoutLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.INFO);
        myLogger.info(UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isInfoEnabled() includes a line number.
     */
    @Test
    public void testIsInfoEnabledLogWithLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        myLogger.info(UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isInfoEnabled().
     */
    @Test
    public void testIsInfoEnabledBad() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        assertFalse(myLogger.isInfoEnabled());
    }

    /**
     * Tests isErrorEnabled().
     */
    @Test
    public void testIsErrorEnabledGood() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.ERROR);
        assertTrue(myLogger.isErrorEnabled());
    }

    /**
     * Tests isErrorEnabled() doesn't include a line number.
     */
    @Test
    public void testIsErrorEnabledLogWithoutLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.ERROR);
        myLogger.error(UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isErrorEnabled() includes a line number.
     */
    @Test
    public void testIsErrorEnabledLogWithLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        myLogger.error(UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isDebugEnabled().
     */
    @Test
    public void testIsDebugEnabledGood() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        assertTrue(myLogger.isDebugEnabled());
    }

    /**
     * Tests isDebugEnabled() doesn't include a line number.
     */
    @Test
    public void testIsDebugEnabledLogWithoutLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        myLogger.debug(UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isDebugEnabled() includes a line number.
     */
    @Test
    public void testIsDebugEnabledLogWithLineNumber() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        myLogger.debug(UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isDebugEnabled().
     */
    @Test
    public void testIsDebugEnabledBad() {
        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.INFO);
        assertFalse(myLogger.isDebugEnabled());
    }

    /**
     * Tests isWarnEnabled(Marker).
     */
    @Test
    public void testIsWarnMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        assertTrue(myLogger.isWarnEnabled(marker));
    }

    /**
     * Tests isWarnEnabled() doesn't include a line number.
     */
    @Test
    public void testIsWarnEnabledMarkerLogWithoutLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        myLogger.warn(marker, UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isWarnEnabled() includes a line number.
     */
    @Test
    public void testIsWarnEnabledMarkerLogWithLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        myLogger.warn(marker, UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isWarnEnabled(Marker).
     */
    @Test
    public void testIsWarnMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.ERROR);
        assertFalse(myLogger.isWarnEnabled(marker));
    }

    /**
     * Tests isTraceEnabled(Marker).
     */
    @Test
    public void testIsTraceMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.TRACE);
        assertTrue(myLogger.isTraceEnabled(marker));
    }

    /**
     * Tests isTraceEnabled() doesn't include a line number.
     */
    @Test
    public void testIsTraceEnabledMarkerLogWithoutLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        myLogger.trace(marker, UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isTraceEnabled() includes a line number.
     */
    @Test
    public void testIsTraceEnabledMarkerLogWithLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.TRACE);
        myLogger.trace(marker, UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isTraceEnabled(Marker).
     */
    @Test
    public void testIsTraceMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        assertFalse(myLogger.isTraceEnabled(marker));
    }

    /**
     * Tests isInfoEnabled(Marker).
     */
    @Test
    public void testIsInfoMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.INFO);
        assertTrue(myLogger.isInfoEnabled(marker));
    }

    /**
     * Tests isInfoEnabled() doesn't include a line number.
     */
    @Test
    public void testIsInfoEnabledMarkerLogWithoutLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.INFO);
        myLogger.info(marker, UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isInfoEnabled() includes a line number.
     */
    @Test
    public void testIsInfoEnabledMarkerLogWithLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        myLogger.info(marker, UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isInfoEnabled(Marker).
     */
    @Test
    public void testIsInfoMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        assertFalse(myLogger.isInfoEnabled(marker));
    }

    /**
     * Tests isErrorEnabled(Marker).
     */
    @Test
    public void testIsErrorMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.ERROR);
        assertTrue(myLogger.isErrorEnabled(marker));
    }

    /**
     * Tests isErrorEnabled() doesn't include a line number.
     */
    @Test
    public void testIsErrorEnabledMarkerLogWithoutLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.ERROR);
        myLogger.error(marker, UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isErrorEnabled() includes a line number.
     */
    @Test
    public void testIsErrorEnabledMarkerLogWithLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        myLogger.error(marker, UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isDebugEnabled(Marker).
     */
    @Test
    public void testIsDebugMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        assertTrue(myLogger.isDebugEnabled(marker));
    }

    /**
     * Tests isDebugEnabled() doesn't include a line number.
     */
    @Test
    public void testIsDebugEnabledMarkerLogWithoutLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.WARN);
        myLogger.debug(marker, UUID.randomUUID().toString());
        assertFalse(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isDebugEnabled() includes a line number.
     */
    @Test
    public void testIsDebugEnabledMarkerLogWithLineNumber() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.DEBUG);
        myLogger.debug(marker, UUID.randomUUID().toString());
        assertTrue(getLog().contains(TEST_LINE_NUM));
    }

    /**
     * Tests isDebugEnabled(Marker).
     */
    @Test
    public void testIsDebugMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker(TestConstants.TEST);

        ((ch.qos.logback.classic.Logger) myLogger.getLoggerImpl()).setLevel(Level.INFO);
        assertFalse(myLogger.isDebugEnabled(marker));
    }

    /**
     * Gets the test logger's name with a fake line number. We're testing presence not actual line number.
     *
     * @return The logger's name a line number
     */
    private String getLoggerInfo() {
        return myLogger.getName() + TEST_LINE_NUM;
    }

    /**
     * Gets the current log message.
     *
     * @return The current log message as a string.
     */
    private String getLog() {
        return myLogStream.toString(StandardCharsets.UTF_8).split(EOL_REGEXP)[0].replaceAll(":[0-9]+", TEST_LINE_NUM);
    }

    /**
     * Gets the current log output split into separate lines.
     *
     * @return The current log output
     */
    private String getStackTrace() {
        final String[] logOutput = myLogStream.toString(StandardCharsets.UTF_8).split(EOL_REGEXP);
        return logOutput.length > 1 ? logOutput[1] : EMPTY;
    }

    /**
     * Constants used in testing.
     */
    private static final class TestConstants {

        /** A test bundle name. */
        private static final String BUNDLE_NAME = "test_freelib-utils_messages";

        /** A test value. */
        private static final String DASH_ASDF = " - asdf";

        /** A test value. */
        private static final String DASH_ONE = " - one";

        /** A test value. */
        private static final String DASH_ONE_TWO = " - one and two";

        /** A test value. */
        private static final String DASH_ASDF_SADF = " - asdf and sadf";

        /** A test variable. */
        private static final String ONE = "one";

        /** A test variable. */
        private static final String TWO = "two";

        /** A test variable. */
        private static final String ASDF = "asdf";

        /** A test variable. */
        private static final String SADF = "sadf";

        /** A test variable. */
        private static final String TEST = "TEST";

        /** A test result. */
        private static final String MAIN_DEBUG = "[main] DEBUG ";

        /** A test result. */
        private static final String MAIN_WARN = "[main] WARN ";

        /** A test result. */
        private static final String MAIN_TRACE = "[main] TRACE ";

        /** A test result. */
        private static final String MAIN_INFO = "[main] INFO ";

        /** A test result. */
        private static final String MAIN_ERROR = "[main] ERROR ";

        /** A test value. */
        private static final String ONE_AND_TWO = "one and two";

        /** A test value. */
        private static final String NOTIFY_ADMIN = "NOTIFY_ADMIN";

        /** A test value. */
        private static final String BAD = "bad";

        /** A test value. */
        private static final String TEST_ONE = "test.one";

        /** A test key. */
        private static final String TEST_VALUE_ONE = "test.value.one";

        /** A test key. */
        private static final String TEST_VALUE_TWO = "test.value.two";

        /** A test message pattern. */
        private static final String MESSAGE = "{}";

        /** A second test message pattern. */
        private static final String THIS_AND_THAT = "{} and {}";

        /** A partial test string. */
        private static final String ASDF_SADF = " - asdf | sadf";

        /**
         * Creates a new constants class used in the logger tests.
         */
        private TestConstants() {
            // This is intentionally left empty.
        }
    }
}
