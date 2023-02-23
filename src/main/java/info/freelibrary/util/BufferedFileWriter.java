
package info.freelibrary.util;

import java.io.BufferedWriter;
import java.io.File;
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
     * @throws NoSuchFileException If the supplied file couldn't be found
     * @throws IOException If there is trouble getting the writer
     */
    public BufferedFileWriter(final File aFile) throws NoSuchFileException, IOException {
        super(getWriter(aFile));
    }

    /**
     * Constructs a writer to the supplied file using the supplied character encoding.
     *
     * @param aFile A file to which to write
     * @param aEncoding A character encoding to use to write to the supplied file
     * @throws NoSuchFileException If the supplied file cannot be found
     * @throws UnsupportedEncodingException If the supplied character encoding isn't supported by the JVM
     * @throws IOException If the writer cannot be created
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
     * @throws NoSuchFileException If the supplied file cannot be found
     * @throws IOException If a writer for the supplied file
     * @throws UnsupportedEncodingI18nException If the JVM doesn't support UTF-8
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
