
package info.freelibrary.util;

import static info.freelibrary.util.Constants.BUNDLE_NAME;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A utility class for working with Zip files.
 *
 * @author Kevin S. Clarke <a href="mailto:ksclarke@ksclarke.io">ksclarke@ksclarke.io</a>
 */
public final class ZipUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class, BUNDLE_NAME);

    private static final String WILDCARD = ".*";

    private ZipUtils() {
    }

    /**
     * Recursively zip a file system directory.
     *
     * @param aFileSystemDir A directory to be recursively zipped up
     * @param aOutputZipFile An output Zip file
     * @throws FileNotFoundException If a file expected to be there is not found
     * @throws IOException If there is trouble writing to a Zip file
     */
    public static void zip(final File aFileSystemDir, final File aOutputZipFile) throws FileNotFoundException,
            IOException {
        zip(aFileSystemDir, aOutputZipFile, new RegexFileFilter(WILDCARD));
    }

    /**
     * Recursively zip up a file system directory.
     *
     * @param aFileSystemDir A directory to be recursively zipped up
     * @param aOutputZipFile An output Zip file
     * @param aIncludesFileList A varargs of additional files to include in the zipped directory
     * @throws FileNotFoundException If a file expected to be there is not found
     * @throws IOException If there is trouble writing to the Zip file
     */
    public static void zip(final File aFileSystemDir, final File aOutputZipFile, final File... aIncludesFileList)
            throws FileNotFoundException, IOException {
        zip(aFileSystemDir, aOutputZipFile, new RegexFileFilter(WILDCARD), aIncludesFileList);
    }

    /**
     * Recursively zip up a file system directory.
     *
     * @param aFileSystemDir A directory to be recursively zipped up
     * @param aOutputZipFile An output Zip file
     * @param aIncludesFilter A file name filter indicating which files to include
     * @param aIncludesFileList A varargs of additional files to include in the zipped directory
     * @throws FileNotFoundException If a file expected to be there is not found
     * @throws IOException If there is trouble writing to the Zip file
     */
    public static void zip(final File aFileSystemDir, final File aOutputZipFile, final FilenameFilter aIncludesFilter,
            final File... aIncludesFileList) throws FileNotFoundException, IOException {
        final ZipOutputStream zipFileStream = new ZipOutputStream(new FileOutputStream(aOutputZipFile));
        final String parentDirPath = aFileSystemDir.getParentFile().getAbsolutePath();

        dirToZip(aFileSystemDir, parentDirPath, aIncludesFilter, zipFileStream);

        // Supplied additional files are just written into the root folder of the package
        for (final File file : aIncludesFileList) {
            final String path = File.separator + aFileSystemDir.getName() + File.separator + file.getName();
            final ZipEntry entry = new ZipEntry(path);
            final FileInputStream inFileStream = new FileInputStream(file);

            zipFileStream.putNextEntry(entry);
            IOUtils.copyStream(inFileStream, zipFileStream);
            IOUtils.closeQuietly(inFileStream);
        }

        IOUtils.closeQuietly(zipFileStream);
    }

    private static void dirToZip(final File aSourceDir, final String aRootDir, final FilenameFilter aFilter,
            final ZipOutputStream aZipStream) throws IOException {
        for (final File inFile : aSourceDir.listFiles()) {
            if (inFile.isDirectory()) {
                LOGGER.debug(MessageCodes.UTIL_027, inFile);
                dirToZip(inFile, aRootDir, aFilter, aZipStream);
            } else if (aFilter.accept(inFile.getParentFile(), inFile.getName())) {
                final String relativePath = aSourceDir.getAbsolutePath().replace(aRootDir, "");
                final ZipEntry entry = new ZipEntry(relativePath + File.separator + inFile.getName());
                final FileInputStream inFileStream = new FileInputStream(inFile);

                aZipStream.putNextEntry(entry);
                IOUtils.copyStream(inFileStream, aZipStream);
                IOUtils.closeQuietly(inFileStream);
            }
        }
    }
}
