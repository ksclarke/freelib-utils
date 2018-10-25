
package info.freelibrary.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.jar.JarFile;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for working with IO streams.
 */
public final class IOUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

    private IOUtils() {
    }

    /**
     * Closes a reader, catching and logging any exceptions.
     *
     * @param aReader A supplied reader to close
     */
    public static void closeQuietly(final Reader aReader) {
        if (aReader != null) {
            try {
                aReader.close();
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
            }
        }
    }

    /**
     * Closes a writer, catching and logging any exceptions.
     *
     * @param aWriter A supplied writer to close
     */
    public static void closeQuietly(final Writer aWriter) {
        if (aWriter != null) {
            try {
                aWriter.close();
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
            }
        }
    }

    /**
     * Closes an input stream, catching and logging any exceptions.
     *
     * @param aInputStream A supplied input stream to close
     */
    public static void closeQuietly(final InputStream aInputStream) {
        if (aInputStream != null) {
            try {
                aInputStream.close();
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
            }
        }
    }

    /**
     * Closes an image input stream, catching and logging any exceptions
     *
     * @param aImageInputStream A supplied image input stream to close
     */
    public static void closeQuietly(final ImageInputStream aImageInputStream) {
        if (aImageInputStream != null) {
            try {
                aImageInputStream.close();
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
            }
        }
    }

    /**
     * Closes an image output stream, catching and logging any exceptions.
     *
     * @param aImageOutputStream A supplied image output stream to close
     */
    public static void closeQuietly(final ImageOutputStream aImageOutputStream) {
        if (aImageOutputStream != null) {
            try {
                aImageOutputStream.close();
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
            }
        }
    }

    /**
     * Closes an output stream, catching and logging any exceptions.
     *
     * @param aOutputStream A supplied output stream to close
     */
    public static void closeQuietly(final OutputStream aOutputStream) {
        if (aOutputStream != null) {
            try {
                aOutputStream.close();
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
            }
        }
    }

    /**
     * Closes a {@link JarFile}, catching and logging any exceptions.
     *
     * @param aJarFile A supplied {@link JarFile} to close
     */
    public static void closeQuietly(final JarFile aJarFile) {
        if (aJarFile != null) {
            try {
                aJarFile.close();
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
            }
        }
    }

    /**
     * Writes from an input stream to an output stream; you're responsible for closing the <code>InputStream</code>
     * and <code>OutputStream</code>.
     *
     * @param aInStream The stream from which to read
     * @param aOutStream The stream from which to write
     * @throws IOException If there is trouble reading or writing
     */
    public static void copyStream(final InputStream aInStream, final OutputStream aOutStream) throws IOException {
        final BufferedOutputStream outStream = new BufferedOutputStream(aOutStream);
        final BufferedInputStream inStream = new BufferedInputStream(aInStream);
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int bytesRead = 0;

        while (true) {
            bytesRead = inStream.read(buffer);

            if (bytesRead == -1) {
                break;
            }

            byteStream.write(buffer, 0, bytesRead);
        }

        outStream.write(byteStream.toByteArray());
        outStream.flush();
    }

    /**
     * Writes a file to an output stream. You're responsible for closing the <code>OutputStream</code>; the input
     * stream is closed for you since just a <code>File</code> was passed in.
     *
     * @param aFile A file from which to read
     * @param aOutStream An output stream to which to write
     * @throws IOException If there is a problem reading or writing
     */
    public static void copyStream(final File aFile, final OutputStream aOutStream) throws IOException {
        final FileInputStream input = new FileInputStream(aFile);
        final FileChannel channel = input.getChannel();
        final byte[] buffer = new byte[256 * 1024];
        final ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

        try {
            for (int length = 0; (length = channel.read(byteBuffer)) != -1;) {
                aOutStream.write(buffer, 0, length);
                byteBuffer.clear();
            }
        } finally {
            input.close();
        }
    }

    /**
     * Reads an InputStream into a byte array.
     *
     * @param aInputStream The input stream from which to read
     * @return The array of bytes
     * @throws IOException If there is trouble reading from the input stream
     */
    public static byte[] readBytes(final InputStream aInputStream) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] buffer = new byte[4096];
        int read = 0;

        while (read != -1) {
            read = aInputStream.read(buffer);

            if (read > 0) {
                baos.write(buffer, 0, read);
            }
        }

        closeQuietly(aInputStream);
        return baos.toByteArray();
    }

}
