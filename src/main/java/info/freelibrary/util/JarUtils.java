
package info.freelibrary.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A small collection of {@link java.util.Jar} file utilities.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class JarUtils {

    private JarUtils() {
    }

    /**
     * Extract a particular path from a supplied {@link java.util.Jar} file to a supplied {@link java.io.File} location.
     *
     * @param aJarFile A Jar file from which to extract
     * @param aFilePath The Jar file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     */
    public static final void extract(final File aJarFile, final String aFilePath, final File aDestDir)
            throws IOException {
        final JarFile jarFile = new JarFile(aJarFile);
        final Enumeration<JarEntry> entries = jarFile.entries();

        try {
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                final String entryName = entry.getName();
                final File file = new File(aDestDir + File.separator + entryName);

                if (entryName.equals(aFilePath)) {
                    final File parent = file.getParentFile();

                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IOException("Unable to create directory structure for: " + file);
                    }

                    final InputStream inputStream = jarFile.getInputStream(entry);
                    final FileOutputStream outputStream = new FileOutputStream(file);

                    IOUtils.copyStream(inputStream, outputStream);
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(outputStream);
                }
            }
        } finally {
            jarFile.close();
        }
    }
}
