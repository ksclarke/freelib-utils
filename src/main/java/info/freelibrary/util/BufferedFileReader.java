
package info.freelibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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
    public BufferedFileReader(final File aFile) throws FileNotFoundException {
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
    public BufferedFileReader(final File aFile, final String aEncoding) throws FileNotFoundException,
            UnsupportedEncodingException {
        super(new InputStreamReader(new FileInputStream(aFile), aEncoding));
    }

    /**
     * Gets a {@link Reader} for the supplied file using the UTF-8 encoding.
     *
     * @param aFile The file for which to get a {#link Reader}
     * @return A {#link Reader} that will read using the UTF-8 charset
     * @throws FileNotFoundException If the supplied file couldn't be found
     * @throws UnsupportedEncodingI18nException If the supplied encoding isn't supported by the JVM
     */
    private static Reader getReader(final File aFile) throws FileNotFoundException {
        try {
            return new InputStreamReader(new FileInputStream(aFile), StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException details) {
            throw new UnsupportedEncodingI18nException(details, StandardCharsets.UTF_8);
        }
    }

}
