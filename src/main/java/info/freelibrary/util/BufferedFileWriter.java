
package info.freelibrary.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * A {@link BufferedWriter} that writes to a file.
 */
public final class BufferedFileWriter extends BufferedWriter {

    /**
     * Constructs a writer to the supplied file using a UTF-8 charset.
     *
     * @param aFile A file to which to write
     * @throws FileNotFoundException If the supplied file couldn't be found
     */
    public BufferedFileWriter(final File aFile) throws NoSuchFileException, IOException {
        super(getWriter(aFile));
    }

    /**
     * Constructs a writer to the supplied file using the supplied character encoding.
     *
     * @param aFile A file to which to write
     * @param aEncoding A character encoding to use to write to the supplied file
     * @throws FileNotFoundException If the supplied file cannot be found
     * @throws java.io.UnsupportedEncodingException If the supplied character encoding isn't supported by the JVM
     */
    public BufferedFileWriter(final File aFile, final String aEncoding)
        throws NoSuchFileException, IOException, UnsupportedEncodingException {
        super(new OutputStreamWriter(Files.newOutputStream(Paths.get(aFile.getAbsolutePath())), aEncoding));
    }

    /**
     * Gets a writer that can write to the supplied file using the UTF-8 charset.
     *
     * @param aFile A file to which to write
     * @return A writer that writes to the supplied file
     * @throws FileNotFoundException If the supplied file cannot be found
     */
    private static Writer getWriter(final File aFile) throws NoSuchFileException, IOException {
        try {
            return new OutputStreamWriter(Files.newOutputStream(Paths.get(aFile.getAbsolutePath())),
                StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException details) {
            throw new UnsupportedEncodingI18nException(details, StandardCharsets.UTF_8);
        }
    }

}
