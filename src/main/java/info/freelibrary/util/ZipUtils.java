
package info.freelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A utility class for working with Zip files.
 *
 * @author Kevin S. Clarke <a href="mailto:ksclarke@ksclarke.io">ksclarke@ksclarke.io</a>
 */
public class ZipUtils {

    private ZipUtils() {
    }

    /**
     * Recursively zip up a file system directory.
     *
     * @param aToBeZippedDir A directory to be recursively zipped up
     * @param aOutputZipFile An output Zip file
     * @throws FileNotFoundException If the found is not found
     * @throws IOException If there is trouble writing to the Zip file
     */
    public static void zip(final File aToBeZippedDir, final File aOutputZipFile) throws FileNotFoundException,
            IOException {
        final ZipOutputStream zipFileStream = new ZipOutputStream(new FileOutputStream(aOutputZipFile));

        dirToZip(aToBeZippedDir, aToBeZippedDir.getAbsolutePath(), zipFileStream);
        IOUtils.closeQuietly(zipFileStream);
    }

    private static void dirToZip(final File aSourceDir, final String aRootDir, final ZipOutputStream aZipStream)
            throws IOException {
        for (final File inFile : aSourceDir.listFiles()) {
            if (inFile.isDirectory()) {
                dirToZip(inFile, aRootDir, aZipStream);
            } else {
                final String relativePath = aSourceDir.getAbsolutePath().replace(aRootDir, "");
                final ZipEntry entry = new ZipEntry(relativePath + inFile.getName());
                final FileInputStream inFileStream = new FileInputStream(inFile);

                aZipStream.putNextEntry(entry);
                IOUtils.copyStream(inFileStream, aZipStream);
                IOUtils.closeQuietly(inFileStream);
            }
        }
    }
}
