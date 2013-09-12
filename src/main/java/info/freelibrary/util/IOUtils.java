/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.util.jar.JarFile;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for working with IO streams.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class IOUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

    /**
     * Closes a reader, catching and logging any exceptions.
     * 
     * @param aReader A supplied reader to close
     */
    public static final void closeQuietly(Reader aReader) {
        if (aReader != null) {
            try {
                aReader.close();
            } catch (IOException details) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(details.getMessage(), details);
                }
            }
        }
    }

    /**
     * Closes a writer, catching and logging any exceptions.
     * 
     * @param aWriter A supplied writer to close
     */
    public static final void closeQuietly(Writer aWriter) {
        if (aWriter != null) {
            try {
                aWriter.close();
            } catch (IOException details) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(details.getMessage(), details);
                }
            }
        }
    }

    /**
     * Closes an input stream, catching and logging any exceptions.
     * 
     * @param aInputStream A supplied input stream to close
     */
    public static final void closeQuietly(InputStream aInputStream) {
        if (aInputStream != null) {
            try {
                aInputStream.close();
            } catch (IOException details) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(details.getMessage(), details);
                }
            }
        }
    }

    /**
     * Closes an output stream, catching and logging any exceptions.
     * 
     * @param aOutputStream A supplied output stream to close
     */
    public static final void closeQuietly(OutputStream aOutputStream) {
        if (aOutputStream != null) {
            try {
                aOutputStream.close();
            } catch (IOException details) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(details.getMessage(), details);
                }
            }
        }
    }

    /**
     * Closes a {@link JarFile}, catching and logging any exceptions.
     * 
     * @param aJarFile A supplied {@link JarFile} to close
     */
    public static final void closeQuietly(JarFile aJarFile) {
        if (aJarFile != null) {
            try {
                aJarFile.close();
            } catch (IOException details) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(details.getMessage(), details);
                }
            }
        }
    }

    /**
     * Writes from an input stream to an output stream; you're responsible for
     * closing the <code>InputStream</code> and <code>OutputStream</code>.
     * 
     * @param aInStream The stream from which to read
     * @param aOutStream The stream from which to write
     * @throws IOException If there is trouble reading or writing
     */
    public static final void copyStream(InputStream aInStream,
            OutputStream aOutStream) throws IOException {
        BufferedOutputStream outStream = new BufferedOutputStream(aOutStream);
        BufferedInputStream inStream = new BufferedInputStream(aInStream);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
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
     * Writes a file to an output stream. You're responsible for closing the
     * <code>OutputStream</code>; the input stream is closed for you since just
     * a <code>File</code> was passed in.
     * 
     * @param aFile A file from which to read
     * @param aOutStream An output stream to which to write
     * @throws IOException If there is a problem reading or writing
     */
    public static final void copyStream(File aFile, OutputStream aOutStream)
            throws IOException {
        FileInputStream input = new FileInputStream(aFile);
        FileChannel channel = input.getChannel();
        byte[] buffer = new byte[256 * 1024];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

        try {
            for (int length = 0; (length = channel.read(byteBuffer)) != -1;) {
                aOutStream.write(buffer, 0, length);
                byteBuffer.clear();
            }
        } finally {
            input.close();
        }
    }
}
