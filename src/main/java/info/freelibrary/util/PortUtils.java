
package info.freelibrary.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Utilities related to ports.
 */
public final class PortUtils {

    /**
     * Create a new instance of the port utilities.
     */
    private PortUtils() {
    }

    /**
     * Gets an open port.
     *
     * @return An open port
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public static int getPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (final IOException details) {
            throw new RuntimeException(details);
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
    private static class PortIterator implements Iterator<Integer> {

        /**
         * The next available port.
         */
        private int myNextPort;

        @Override
        @SuppressWarnings("PMD.AvoidCatchingGenericException")
        public boolean hasNext() {
            try {
                myNextPort = getPort();
                return myNextPort != 0;
            } catch (final RuntimeException details) {
                return false;
            }
        }

        @Override
        @SuppressWarnings("PMD.AvoidCatchingGenericException")
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
