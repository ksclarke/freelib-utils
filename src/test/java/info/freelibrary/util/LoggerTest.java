
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.impl.SimpleLogger;

import info.freelibrary.util.test.TestUtils;

public class LoggerTest {

    private static final String LOG_LEVEL = "org.slf4j.simpleLogger.defaultLogLevel";

    private static final String BUNDLE_NAME = "test_freelib-utils_messages";

    private Logger myLogger;

    @Before
    public void setUp() throws Exception {
        System.setProperty(LOG_LEVEL, "trace");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(UUID.randomUUID().toString()), BUNDLE_NAME);
    }

    @Before
    public void tearDown() throws Exception {
        System.clearProperty(LOG_LEVEL);
    }

    @Test
    public void testSimpleConstructor() {
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(LoggerTest.class));
    }

    @Test
    public void testDebugMessage() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageKey() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageKeyDetails() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageDetails() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("{}", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageKeyVarargDetails() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageVarargDetails() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("{} and {}", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageKeyThrowable() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testDebugMessageKeyThrowableNull() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageThrowable() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testDebugMessageThrowableNull() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageObjectObject() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMessageKeyObjectObject() throws IOException {
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug("test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "{}", "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - asdf and sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "{} and {}", new String[] { "asdf", "sadf" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testDebugMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testDebugMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testDebugMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] DEBUG " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.debug(notifyAdmin, "test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    // End of debug tests

    @Test
    public void testErrorMessage() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageKey() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageKeyDetails() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageDetails() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("{}", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageKeyVarargDetails() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageVarargDetails() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("{} and {}", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageKeyThrowable() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorMessageKeyThrowableNull() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageThrowable() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorMessageThrowableNull() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorThrowableMessageKey() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException("bad"), "test.one");
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorThrowableNullMessageKey() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, "test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorThrowableMessage() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException("bad"), "one");
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorThrowableNullMessage() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageKeyThrowableVarargs() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException("bad"), "test.value.two", new String[] { "one", "two" });
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorMessageKeyThrowableNullVarargs() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, "test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorThrowableMessageVarargs() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException("bad"), "{} and {}", new String[] { "one", "two" });
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorThrowableNullMessageVarargs() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, "{} and {}", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorThrowableMessageVarargs2() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(new IOException("bad"), "one and two", new String[] {});
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorThrowableNullMessageVarargs2() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error((Throwable) null, "one and two", new String[] {});
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageObjectObject() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMessageKeyObjectObject() throws IOException {
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error("test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "{}", "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - asdf and sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "{} and {}", new String[] { "asdf", "sadf" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testErrorMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testErrorMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] ERROR " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.error(notifyAdmin, "test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    // End of error tests

    @Test
    public void testInfoMessage() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageKey() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageKeyDetails() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageDetails() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("{}", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageKeyVarargDetails() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageVarargDetails() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("{} and {}", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageKeyThrowable() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testInfoMessageKeyThrowableNull() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageThrowable() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testInfoMessageThrowableNull() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageObjectObject() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMessageKeyObjectObject() throws IOException {
        final String expected = "[main] INFO " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info("test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "{}", "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - asdf and sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "{} and {}", new String[] { "asdf", "sadf" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testInfoMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testInfoMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testInfoMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] INFO " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.info(notifyAdmin, "test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    // End of info tests

    @Test
    public void testTraceMessage() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageKey() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageKeyDetails() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageDetails() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("{}", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageKeyVarargDetails() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageVarargDetails() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("{} and {}", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageKeyThrowable() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testTraceMessageKeyThrowableNull() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageThrowable() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testTraceMessageThrowableNull() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageObjectObject() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMessageKeyObjectObject() throws IOException {
        final String expected = "[main] TRACE " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace("test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "{}", "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - asdf and sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "{} and {}", new String[] { "asdf", "sadf" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testTraceMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testTraceMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testTraceMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] TRACE " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.trace(notifyAdmin, "test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    // End of trace tests

    @Test
    public void testWarnMessage() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageKey() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageKeyDetails() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageDetails() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("{}", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageKeyVarargDetails() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageVarargDetails() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("{} and {}", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageKeyThrowable() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testWarnMessageKeyThrowableNull() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageThrowable() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testWarnMessageThrowableNull() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessage() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageKey() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "test.one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageObjectObject() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMessageKeyObjectObject() throws IOException {
        final String expected = "[main] WARN " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn("test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - asdf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "{}", "asdf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageKeyObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "test.value.one", "one");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - asdf and sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "{} and {}", new String[] { "asdf", "sadf" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageKeyVarargs() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "test.value.two", new String[] { "one", "two" });
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageKeyThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "test.one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testWarnMarkerMessageKeyThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "test.one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageThrowable() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "one", new IOException("bad"));
        });
        final String[] parts = found.split("\\R");

        assertEquals(expected, parts[0]);
        assertEquals("java.io.IOException: bad", parts[1]);
    }

    @Test
    public void testWarnMarkerMessageThrowableNull() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - one";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "one", (Throwable) null);
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - asdf | sadf";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "{} | {}", "asdf", "sadf");
        });

        assertEquals(expected, found);
    }

    @Test
    public void testWarnMarkerMessageKeyObjectObject() throws IOException {
        final Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        final String expected = "[main] WARN " + myLogger.getName() + " - one and two";
        final String found = TestUtils.captureStdOut(() -> {
            myLogger.warn(notifyAdmin, "test.value.two", "one", "two");
        });

        assertEquals(expected, found);
    }

    // End warn tests

    @Test
    public void testGetLoggerImpl() {
        assertEquals("org.slf4j.impl.SimpleLogger", myLogger.getLoggerImpl().getClass().getName());
    }

    @Test
    public void testGetMessage() {
        final String found = myLogger.getMessage("one");

        assertEquals("one", found);
    }

    @Test
    public void testGetMessageVarargs() {
        final String found = myLogger.getMessage("{} and {}", new String[] { "one", "two" });

        assertEquals("one and two", found);
    }

    @Test
    public void testGetMessageKey() {
        final String found = myLogger.getMessage("test.value.two", "one", "two");

        assertEquals("one and two", found);
    }

    @Test
    public void testIsWarnEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "WARN");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isWarnEnabled());
    }

    @Test
    public void testIsWarnEnabledBad() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "ERROR");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertFalse(myLogger.isWarnEnabled());
    }

    @Test
    public void testIsTraceEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "TRACE");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isTraceEnabled());
    }

    @Test
    public void testIsTraceEnabledBad() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "ERROR");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertFalse(myLogger.isTraceEnabled());
    }

    @Test
    public void testIsInfoEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "INFO");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isInfoEnabled());
    }

    @Test
    public void testIsInfoEnabledBad() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "ERROR");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertFalse(myLogger.isInfoEnabled());
    }

    @Test
    public void testIsErrorEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "ERROR");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isErrorEnabled());
    }

    @Test
    public void testIsDebugEnabledGood() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "DEBUG");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isDebugEnabled());
    }

    @Test
    public void testIsDebugEnabledBad() {
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "WARN");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertFalse(myLogger.isDebugEnabled());
    }

    @Test
    public void testIsWarnMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "WARN");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isWarnEnabled(marker));
    }

    @Test
    public void testIsWarnMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "ERROR");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertFalse(myLogger.isWarnEnabled(marker));
    }

    @Test
    public void testIsTraceMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "TRACE");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isTraceEnabled(marker));
    }

    @Test
    public void testIsTraceMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "ERROR");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertFalse(myLogger.isTraceEnabled(marker));
    }

    @Test
    public void testIsInfoMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "INFO");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isInfoEnabled(marker));
    }

    @Test
    public void testIsInfoMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "ERROR");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertFalse(myLogger.isInfoEnabled(marker));
    }

    @Test
    public void testIsErrorMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "ERROR");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isErrorEnabled(marker));
    }

    @Test
    public void testIsDebugMarkerEnabledGood() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "DEBUG");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertTrue(myLogger.isDebugEnabled(marker));
    }

    @Test
    public void testIsDebugMarkerEnabledBad() {
        final Marker marker = MarkerFactory.getMarker("TEST");
        final String uuid = UUID.randomUUID().toString();

        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + uuid, "WARN");
        myLogger = new Logger(org.slf4j.LoggerFactory.getLogger(uuid), BUNDLE_NAME);

        assertFalse(myLogger.isDebugEnabled(marker));
    }

}
