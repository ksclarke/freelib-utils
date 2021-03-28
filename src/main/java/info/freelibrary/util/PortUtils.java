
package info.freelibrary.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Utilities related to ports.
 */
public final class PortUtils {

    private PortUtils() {
    }

    /**
     * Gets an open port.
     *
     * @return An open port
     */
    public static int getPort() {
        final int port;

        try {
            final ServerSocket socket = new ServerSocket(0);

            port = socket.getLocalPort();
            socket.close();
        } catch (final IOException details) {
            throw new RuntimeException(details);
        }

        return port;
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

        private int myNextPort;

        @Override
        public boolean hasNext() {
            try {
                myNextPort = getPort();
                return myNextPort != 0;
            } catch (final RuntimeException details) {
                return false;
            }
        }

        @Override
        public Integer next() {
            final Integer port;

            try {
                port = myNextPort == 0 ? getPort() : myNextPort;
                myNextPort = 0;

                return port;
            } catch (final RuntimeException details) {
                throw new NoSuchElementException(details.getMessage());
            }
        }

    }
}
