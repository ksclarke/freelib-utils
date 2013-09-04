/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * A {@link BufferedWriter} that writes to a file.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class BufferedFileWriter extends BufferedWriter {

    /**
     * Constructs a writer to the supplied file using a UTF-8 charset.
     * 
     * @param aFile A file to which to write
     * @throws FileNotFoundException If the supplied file couldn't be found
     */
    public BufferedFileWriter(File aFile) throws FileNotFoundException {
        super(getWriter(aFile));
    }

    /**
     * Constructs a writer to the supplied file using the supplied character
     * encoding.
     * 
     * @param aFile A file to which to write
     * @param aEncoding A character encoding to use to write to the supplied
     *        file
     * @throws FileNotFoundException If the supplied file cannot be found
     * @throws UnsupportedEncodingException If the supplied character encoding
     *         isn't supported by the JVM
     */
    public BufferedFileWriter(File aFile, String aEncoding)
        throws FileNotFoundException, UnsupportedEncodingException {
        super(new OutputStreamWriter(new FileOutputStream(aFile), aEncoding));
    }

    /**
     * Gets a writer that can write to the supplied file using the UTF-8
     * charset.
     * 
     * @param aFile A file to which to write
     * @return A writer that writes to the supplied file
     * @throws FileNotFoundException If the supplied file cannot be found
     */
    private static final Writer getWriter(File aFile)
        throws FileNotFoundException {
        try {
            return new OutputStreamWriter(new FileOutputStream(aFile), "UTF-8");
        } catch (UnsupportedEncodingException details) {
            throw new RuntimeException(details); // UTF-8 is always supported
        }
    }
}
