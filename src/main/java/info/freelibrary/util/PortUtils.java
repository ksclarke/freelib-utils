
package info.freelibrary.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.NoSuchElementException;

import info.freelibrary.util.warnings.PMD;

/**
 * Utilities related to ports.
 */
public final class PortUtils {

    /**
     * Create a new instance of the port utilities.
     */
    private PortUtils() {
        // This is intentionally left empty
    }

    /**
     * Gets an open port.
     *
     * @return An open port
     * @throws I18nRuntimeException If a local port cannot be found
     */
    @SuppressWarnings(PMD.AVOID_THROWING_RAW_EXCEPTION_TYPES)
    public static int getPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (final IOException details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * Returns an iterator over open ports.
     *
     * @return An iterator for open ports
     */
    public static Iterator<Integer> getPortIterator() {
        return new PortIterator();
    }

    /**
     * Gets an open port iterator.
     */
    private static final class PortIterator implements Iterator<Integer> {

        /**
         * The next available port.
         */
        private int myNextPort;

        @Override
        @SuppressWarnings(PMD.AVOID_CATCHING_GENERIC_EXCEPTION)
        public boolean hasNext() {
            try {
                myNextPort = getPort();
                return myNextPort != 0;
            } catch (final RuntimeException details) {
                return false;
            }
        }

        @Override
        @SuppressWarnings(PMD.AVOID_CATCHING_GENERIC_EXCEPTION)
        public Integer next() {
            final Integer port;

            try {
                port = myNextPort == 0 ? getPort() : myNextPort;
                myNextPort = 0;

                return port;
            } catch (final RuntimeException details) {
                throw (NoSuchElementException) new NoSuchElementException(details.getMessage()).initCause(details);
            }
        }

    }
}
