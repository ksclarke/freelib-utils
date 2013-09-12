/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * A {@link BufferedReader} that reads from a file.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class BufferedFileReader extends BufferedReader {

    /**
     * Constructs a reader from the supplied file using a UTF-8 charset.
     * 
     * @param aFile A file from which to read
     * @throws FileNotFoundException If the supplied file couldn't be found
     */
    public BufferedFileReader(File aFile) throws FileNotFoundException {
        super(getReader(aFile));
    }

    /**
     * Constructs a reader from the supplied file using the supplied character
     * encoding.
     * 
     * @param aFile A file from which to read
     * @param aEncoding A character encoding to use while reading from the file
     * @throws FileNotFoundException If the supplied file couldn't be found
     */
    public BufferedFileReader(File aFile, String aEncoding)
            throws FileNotFoundException, UnsupportedEncodingException {
        super(new InputStreamReader(new FileInputStream(aFile), aEncoding));
    }

    /**
     * Gets a {@link Reader} for the supplied file using the UTF-8 encoding.
     * 
     * @param aFile The file for which to get a {#link Reader}
     * @return A {#link Reader} that will read using the UTF-8 charset
     * @throws FileNotFoundException If the supplied file couldn't be found
     */
    private static final Reader getReader(File aFile)
            throws FileNotFoundException {
        try {
            return new InputStreamReader(new FileInputStream(aFile), "UTF-8");
        } catch (UnsupportedEncodingException details) {
            throw new RuntimeException(details); // UTF-8 is always supported
        }
    }
}
