
package info.freelibrary.util;

import static info.freelibrary.util.Constants.EMPTY;

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
 */
public final class ZipUtils {

    /**
     * The logger used by ZipUtils.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class, MessageCodes.BUNDLE);

    /**
     * A wildcard for file extensions.
     */
    private static final String WILDCARD = ".*";

    /**
     * Creates a new zip utilities class.
     */
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
    public static void zip(final File aFileSystemDir, final File aOutputZipFile)
            throws FileNotFoundException, IOException {
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
    @SuppressWarnings("PMD.AvoidFileStream")
    public static void zip(final File aFileSystemDir, final File aOutputZipFile, final FilenameFilter aIncludesFilter,
            final File... aIncludesFileList) throws FileNotFoundException, IOException {
        try (ZipOutputStream zipFileStream = new ZipOutputStream(new FileOutputStream(aOutputZipFile))) {
            final String parentDirPath = aFileSystemDir.getParentFile().getAbsolutePath();

            dirToZip(aFileSystemDir, parentDirPath, aIncludesFilter, zipFileStream);

            // Supplied additional files are just written into the root folder of the package
            for (final File file : aIncludesFileList) {
                try (FileInputStream inFileStream = new FileInputStream(file)) {
                    final String path = File.separator + aFileSystemDir.getName() + File.separator + file.getName();

                    zipFileStream.putNextEntry(new ZipEntry(path));
                    IOUtils.copyStream(inFileStream, zipFileStream);
                }
            }
        }
    }

    /**
     * Zips up a directory.
     *
     * @param aSourceDir A source directory
     * @param aRootDir The directory to use as the root for the zipped file system
     * @param aFilter A filter to determine which files should be included
     * @param aZipStream An output stream for the zipped directory
     * @throws IOException If there is trouble zipping up the directory
     */
    @SuppressWarnings("PMD.AvoidFileStream")
    private static void dirToZip(final File aSourceDir, final String aRootDir, final FilenameFilter aFilter,
            final ZipOutputStream aZipStream) throws IOException {
        for (final File inFile : aSourceDir.listFiles()) {
            if (inFile.isDirectory()) {
                LOGGER.debug(MessageCodes.UTIL_027, inFile);
                dirToZip(inFile, aRootDir, aFilter, aZipStream);
            } else if (aFilter.accept(inFile.getParentFile(), inFile.getName())) {
                try (FileInputStream inFileStream = new FileInputStream(inFile)) {
                    final String relativePath = aSourceDir.getAbsolutePath().replace(aRootDir, EMPTY);

                    aZipStream.putNextEntry(new ZipEntry(relativePath + File.separator + inFile.getName()));
                    IOUtils.copyStream(inFileStream, aZipStream);
                }
            }
        }
    }
}
