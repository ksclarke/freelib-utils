
package info.freelibrary.util.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import info.freelibrary.util.UnsupportedEncodingI18nException;

/**
 * A utility class that has some test helpers.
 */
public final class TestUtils {

    private TestUtils() {
    }

    /**
     * Capture logging output so it can be tested.
     *
     * @param aRunnable A runnable
     * @return The logging output
     */
    public static String captureStdOut(final Runnable aRunnable) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream stdErr = System.err;
        final String result;

        try (PrintStream logStream = new PrintStream(baos, true, StandardCharsets.UTF_8.name())) {
            System.setErr(logStream);

            aRunnable.run();

            result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        } catch (final java.io.UnsupportedEncodingException details) {
            throw new UnsupportedEncodingI18nException(details, StandardCharsets.UTF_8.name());
        } finally {
            System.setErr(stdErr);
        }

        // Strip the EOL to make matching easier
        return result.length() == 0 ? result : result.substring(0, result.length() - 1);
    }

}
