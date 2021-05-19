
package info.freelibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * A {@link BufferedReader} that reads from a file.
 */
public class BufferedFileReader extends BufferedReader {

    /**
     * Constructs a reader from the supplied file using a UTF-8 charset.
     *
     * @param aFile A file from which to read
     * @throws FileNotFoundException If the supplied file couldn't be found
     */
    public BufferedFileReader(final File aFile) throws NoSuchFileException, IOException {
        super(getReader(aFile));
    }

    /**
     * Constructs a reader from the supplied file using the supplied character encoding.
     *
     * @param aFile A file from which to read
     * @param aEncoding A character encoding to use while reading from the file
     * @throws FileNotFoundException If the supplied file couldn't be found
     * @throws java.io.UnsupportedEncodingException If the supplied encoding isn't supported by the JVM
     */
    public BufferedFileReader(final File aFile, final String aEncoding)
            throws NoSuchFileException, IOException, UnsupportedEncodingException {
        super(new InputStreamReader(Files.newInputStream(Paths.get(aFile.getAbsolutePath())), aEncoding));
    }

    /**
     * Gets a {@link Reader} for the supplied file using the UTF-8 encoding.
     *
     * @param aFile The file for which to get a {#link Reader}
     * @return A {#link Reader} that will read using the UTF-8 charset
     * @throws FileNotFoundException If the supplied file couldn't be found
     * @throws UnsupportedEncodingI18nException If the supplied encoding isn't supported by the JVM
     */
    private static Reader getReader(final File aFile) throws NoSuchFileException, IOException {
        try {
            return new InputStreamReader(Files.newInputStream(Paths.get(aFile.getAbsolutePath())),
                    StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException details) {
            throw new UnsupportedEncodingI18nException(details, StandardCharsets.UTF_8);
        }
    }

}
