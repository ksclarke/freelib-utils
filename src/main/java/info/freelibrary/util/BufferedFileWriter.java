/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * A {@link BufferedWriter} that writes to a file.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public final class BufferedFileWriter extends BufferedWriter {

    /**
     * Constructs a writer to the supplied file using a UTF-8 charset.
     *
     * @param aFile A file to which to write
     * @throws FileNotFoundException If the supplied file couldn't be found
     */
    public BufferedFileWriter(final File aFile) throws FileNotFoundException {
        super(getWriter(aFile));
    }

    /**
     * Constructs a writer to the supplied file using the supplied character encoding.
     *
     * @param aFile A file to which to write
     * @param aEncoding A character encoding to use to write to the supplied file
     * @throws FileNotFoundException If the supplied file cannot be found
     * @throws UnsupportedEncodingException If the supplied character encoding isn't supported by the JVM
     */
    public BufferedFileWriter(final File aFile, final String aEncoding) throws FileNotFoundException,
            java.io.UnsupportedEncodingException {
        super(new OutputStreamWriter(new FileOutputStream(aFile), aEncoding));
    }

    /**
     * Gets a writer that can write to the supplied file using the UTF-8 charset.
     *
     * @param aFile A file to which to write
     * @return A writer that writes to the supplied file
     * @throws FileNotFoundException If the supplied file cannot be found
     */
    private static Writer getWriter(final File aFile) throws FileNotFoundException {
        try {
            return new OutputStreamWriter(new FileOutputStream(aFile), StandardCharsets.UTF_8.name());
        } catch (final java.io.UnsupportedEncodingException details) {
            throw new UnsupportedEncodingException(details, StandardCharsets.UTF_8.name());
        }
    }

}
