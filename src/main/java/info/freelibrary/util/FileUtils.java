
package info.freelibrary.util;

import static info.freelibrary.util.Constants.EMPTY;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utilities for working with files.
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CyclomaticComplexity", "PMD.GodClass", "PMD.AvoidDuplicateLiterals" })
public final class FileUtils {

    /**
     * A date format pattern.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

    /**
     * A constant for file type.
     */
    private static final String FILE_TYPE = "file";

    /**
     * A regex expression for file extensions.
     */
    private static final String WILDCARD = ".*";

    /**
     * A constant for the file extension delimiter.
     */
    private static final char DOT = '.';

    /**
     * A logger for FileUtils.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class, MessageCodes.BUNDLE);

    /**
     * Constructor for the contained file utilities.
     */
    private FileUtils() {
    }

    /**
     * Returns a Map representation of the supplied directory's structure. The map contains the file name as the key and
     * its path as the value. If a file with a name occurs more than once, multiple path values are returned for that
     * file name key. The map that is returned is unmodifiable.
     *
     * @param aFilePath The directory of which you'd like a file listing
     * @return An unmodifiable map representing the files in the file structure
     * @throws FileNotFoundException If the directory for the supplied file path does not exist
     */
    public static Map<String, List<String>> toHashMap(final String aFilePath) throws FileNotFoundException {
        return toHashMap(aFilePath, null, (String[]) null);
    }

    /**
     * Returns a Map representation of the supplied directory's structure. The map contains the file name as the key and
     * its path as the value. If a file with a name occurs more than once, multiple path values are returned for that
     * file name key. The map that is returned is unmodifiable.
     *
     * @param aFilePath The directory of which you'd like a file listing
     * @param aPattern A regular expression pattern which the files must match to be returned
     * @return An unmodifiable map representing the files in the file structure
     * @throws FileNotFoundException If the directory for the supplied file path does not exist
     */
    public static Map<String, List<String>> toHashMap(final String aFilePath, final String aPattern)
            throws FileNotFoundException {
        return toHashMap(aFilePath, aPattern, (String[]) null);
    }

    /**
     * Returns a Map representation of the supplied directory's structure. The map contains the file name as the key and
     * its path as the value. If a file with a name occurs more than once, multiple path values are returned for that
     * file name key. The map that is returned is unmodifiable.
     *
     * @param aFilePath The directory of which you'd like a file listing
     * @param aPattern A regular expression pattern which the files must match to be returned
     * @param aIgnoreList A list of directories into which we shouldn't descend
     * @return An unmodifiable map representing the files in the file structure
     * @throws FileNotFoundException If the directory for the supplied file path does not exist
     * @throws RuntimeException If a duplicate file path name is discovered
     */
    public static Map<String, List<String>> toHashMap(final String aFilePath, final String aPattern,
            final String... aIgnoreList) throws FileNotFoundException {
        final String filePattern = aPattern != null ? aPattern : WILDCARD;
        final RegexFileFilter filter = new RegexFileFilter(filePattern);
        final Map<String, List<String>> fileMap = new HashMap<>();
        final File source = new File(aFilePath);

        for (final File file : listFiles(source, filter, true, aIgnoreList)) {
            final String fileName = file.getName();
            final String filePath = file.getAbsolutePath();

            if (fileMap.containsKey(fileName)) {
                final List<String> paths = fileMap.get(fileName);

                if (!paths.contains(filePath)) {
                    paths.add(filePath);
                } else {
                    throw new I18nRuntimeException(MessageCodes.BUNDLE, MessageCodes.UTIL_034);
                }
            } else {
                final ArrayList<String> pathList = new ArrayList<>();
                pathList.add(filePath);
                fileMap.put(fileName, pathList);
            }
        }

        return Collections.unmodifiableMap(fileMap);
    }

    /**
     * Returns a Java <code>File</code> for the supplied file-based URL.
     *
     * @param aURL A URL that has a file protocol
     * @return A Java <code>File</code> for the supplied URL
     * @throws MalformedURLException If the supplied URL doesn't have a file protocol
     */
    public static File toFile(final URL aURL) throws MalformedURLException {
        if (aURL.getProtocol().equals(FILE_TYPE)) {
            return new File(aURL.toString().replace("file:", ""));
        }

        throw new MalformedURLException(LOGGER.getI18n(MessageCodes.UTIL_036, aURL));
    }

    /**
     * An array of all the files in the supplied directory that match the supplied <code>FilenameFilter</code>.
     *
     * @param aDir A directory from which a file listing should be returned
     * @param aFilter A file name filter which returned files should match
     * @return An array of matching files
     * @throws FileNotFoundException If the supplied directory doesn't exist
     */
    public static File[] listFiles(final File aDir, final FilenameFilter aFilter) throws FileNotFoundException {
        return listFiles(aDir, aFilter, false, (String[]) null);
    }

    /**
     * An array of all the files in the supplied directory that match the supplied <code>FilenameFilter</code>.
     *
     * @param aDir A directory from which a file listing should be returned
     * @param aFilter A file name filter which returned files should match
     * @param aDeepListing Whether we should descend through subdirectories
     * @return An array of matching files
     * @throws FileNotFoundException If the supplied directory doesn't exist
     */
    public static File[] listFiles(final File aDir, final FilenameFilter aFilter, final boolean aDeepListing)
            throws FileNotFoundException {
        return listFiles(aDir, aFilter, aDeepListing, (String[]) null);
    }

    /**
     * An array of all the files in the supplied directory that match the supplied <code>FilenameFilter</code>.
     * Directories that match the supplied ignore list will not be included in the result.
     *
     * @param aDir A directory from which a file listing should be returned
     * @param aFilter A file name filter which returned files should match
     * @param aDeepListing Whether we should descend through subdirectories
     * @param aIgnoreList Directory names that should be ignored in the list.
     * @return An array of matching files
     * @throws FileNotFoundException If the supplied directory doesn't exist
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
    public static File[] listFiles(final File aDir, final FilenameFilter aFilter, final boolean aDeepListing,
            final String... aIgnoreList) throws FileNotFoundException {
        if (!aDir.exists()) {
            throw new FileNotFoundException(aDir.getAbsolutePath());
        }

        if (aDir.isFile()) {
            if (aFilter.accept(aDir.getParentFile(), aDir.getName())) {
                return new File[] { aDir };
            } else {
                return new File[0];
            }
        }

        if (!aDeepListing) {
            return aDir.listFiles(aFilter);
        } else {
            final ArrayList<File> fileList = new ArrayList<>();
            final String[] ignoreList;

            if (aIgnoreList == null) {
                ignoreList = new String[0];
            } else {
                ignoreList = aIgnoreList;
            }

            for (final File file : aDir.listFiles()) {
                final String fileName = file.getName();

                if (aFilter.accept(aDir, fileName)) {
                    LOGGER.debug(MessageCodes.UTIL_010, file);
                    fileList.add(file);
                }

                if (file.isDirectory() && Arrays.binarySearch(ignoreList, fileName) < 0) {
                    final File[] files;

                    LOGGER.debug(MessageCodes.UTIL_011, file);
                    files = listFiles(file, aFilter, aDeepListing);
                    fileList.addAll(Arrays.asList(files));
                }

            }

            return fileList.toArray(new File[0]);
        }
    }

    /**
     * Return a file name without the dot extension.
     *
     * @param aFile The file name
     * @return The file name without the extension
     */
    public static String stripExt(final File aFile) {
        return stripExt(aFile.getName());
    }

    /**
     * Return a file name without the dot extension.
     *
     * @param aFileName The file name from which we want to strip the extension
     * @return The file name without the extension
     */
    public static String stripExt(final String aFileName) {
        final int index = aFileName.lastIndexOf(DOT);

        if (index != -1) {
            return aFileName.substring(0, index);
        }

        return aFileName;
    }

    /**
     * Returns a file extension (as delimited by a period).
     *
     * @param aFileName A file name from which to get the extension
     * @return The extension or an empty string if the file doesn't have an extension
     */
    public static String getExt(final String aFileName) {
        final int index = aFileName.lastIndexOf(DOT);

        if (index != -1) {
            return aFileName.substring(index + 1, aFileName.length());
        }

        return EMPTY;
    }

    /**
     * Gets the calculated size of a directory of files.
     *
     * @param aFile A file or directory from which to calculate size
     * @return The calculated size of the supplied directory or file
     */
    public static long getSize(final File aFile) {
        long size = 0;

        if (aFile != null && aFile.exists()) {
            if (aFile.isDirectory()) {
                for (final File file : aFile.listFiles()) {
                    size += getSize(file);
                }
            } else {
                size += aFile.length();
            }
        }

        return size;
    }

    /**
     * Deletes a directory and all its children.
     *
     * @param aDir A directory to delete
     * @return True if file was successfully deleted; else, false
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
    public static boolean delete(final File aDir) {
        if (aDir.exists() && aDir.listFiles() != null) {
            for (final File file : aDir.listFiles()) {
                if (file.isDirectory()) {
                    if (!delete(file)) {
                        LOGGER.error(MessageCodes.UTIL_012, file);
                    }
                } else {
                    if (file.exists() && !file.delete()) {
                        LOGGER.error(MessageCodes.UTIL_012, file);
                    }
                }
            }
        } else if (LOGGER.isDebugEnabled() && aDir.listFiles() == null) {
            LOGGER.debug(MessageCodes.UTIL_013, aDir);
        }

        return aDir.delete();
    }

    /**
     * Copies a file or directory from one place to another. This copies a file to a file or a directory to a directory.
     * It does not copy a file to a directory.
     *
     * @param aFromFile A file or directory source
     * @param aToFile A file or directory destination
     * @throws IOException If there is an exception copying the files or directories
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
    public static void copy(final File aFromFile, final File aToFile) throws IOException {
        if (aFromFile.isDirectory() && aToFile.isFile() || aFromFile.isFile() && aToFile.isDirectory()) {
            throw new IOException(LOGGER.getI18n(MessageCodes.UTIL_037, aFromFile, aToFile));
        }

        if (aFromFile.isDirectory()) {
            if (!aToFile.exists() && !aToFile.mkdirs()) {
                throw new IOException(LOGGER.getI18n(MessageCodes.UTIL_035, aToFile.getAbsolutePath()));
            }

            for (final File file : aFromFile.listFiles()) {
                copy(file, new File(aToFile, file.getName()));
            }
        } else {
            copyFile(aFromFile, aToFile);
        }
    }

    /**
     * Returns a human readable size from a large number of bytes.
     *
     * @param aByteCount A large number of bytes
     * @return A human readable size
     */
    public static String sizeFromBytes(final long aByteCount) {
        return sizeFromBytes(aByteCount, false);
    }

    /**
     * Returns a human readable size from a large number of bytes. You can specify that the human readable size use an
     * abbreviated label (e.g., GB or MB).
     *
     * @param aByteCount A large number of bytes
     * @param aAbbreviatedLabel Whether the label should be abbreviated
     * @return A human readable size
     */
    public static String sizeFromBytes(final long aByteCount, final boolean aAbbreviatedLabel) {
        long count;

        if ((count = aByteCount / 1_073_741_824) > 0) {
            return count + (aAbbreviatedLabel ? " GB" : " gigabytes");
        } else if ((count = aByteCount / 1_048_576) > 0) {
            return count + (aAbbreviatedLabel ? " MB" : " megabytes");
        } else if ((count = aByteCount / 1024) > 0) {
            return count + (aAbbreviatedLabel ? " KB" : " kilobytes");
        }

        return count + (aAbbreviatedLabel ? " B" : " bytes");
    }

    /**
     * Get a hash for a supplied file.
     *
     * @param aFile A file to get a hash for
     * @param aAlgorithm A hash algorithm supported by <code>MessageDigest</code>
     * @return A hash string
     * @throws NoSuchAlgorithmException If the supplied algorithm isn't supported
     * @throws IOException If there is trouble reading the file
     */
    public static String hash(final File aFile, final String aAlgorithm) throws NoSuchAlgorithmException, IOException {
        final MessageDigest md = MessageDigest.getInstance(aAlgorithm);
        final Path filePath = Paths.get(aFile.getAbsolutePath());

        try (InputStream inStream = Files.newInputStream(filePath); Formatter formatter = new Formatter();
                DigestInputStream mdStream = new DigestInputStream(inStream, md)) {
            final byte[] bytes = new byte[8192];
            int bytesRead = 0;

            while (bytesRead != -1) {
                bytesRead = mdStream.read(bytes);
            }

            for (final byte bite : md.digest()) {
                formatter.format("%02x", bite);
            }

            return formatter.toString();
        }
    }

    /**
     * Gets the MIME type of the supplied file URL. It gets MIME type from {@link URLConnection}'s file name map.
     *
     * @param aFileUrl A file-based URL
     * @return The MIME-type name for the supplied file
     */
    public static String getMimeType(final String aFileUrl) {
        return URLConnection.getFileNameMap().getContentTypeFor(aFileUrl);
    }

    /**
     * Tests that the supplied directory exists and can be used according to the supplied permissions string (e.g.,
     * 'rwx').
     *
     * @param aDirName A name of a directory on the file system
     * @param aPermString A string representing the desired permissions of the directory
     * @return True if the directory is okay to be used; else, false
     */
    public static boolean dirIsUseable(final String aDirName, final String aPermString) {
        final File dir = new File(aDirName);

        if (!dir.exists() && !dir.mkdirs()) {
            return false;
        } else if ("r".contains(aPermString)) {
            return dir.canRead();
        } else if ("w".contains(aPermString)) {
            return dir.canWrite();
        } else if ("x".contains(aPermString)) {
            return dir.canExecute();
        } else {
            return true;
        }
    }

    /**
     * Converts an file permissions mode integer to a PosixFilePermission set.
     *
     * @param aMode An integer permissions mode.
     * @return A PosixFilePermission set
     */
    @SuppressWarnings("PMD.NPathComplexity")
    public static Set<PosixFilePermission> convertToPermissionsSet(final int aMode) {
        final Set<PosixFilePermission> result = EnumSet.noneOf(PosixFilePermission.class);

        if (isSet(aMode, 400)) {
            result.add(PosixFilePermission.OWNER_READ);
        }

        if (isSet(aMode, 200)) {
            result.add(PosixFilePermission.OWNER_WRITE);
        }

        if (isSet(aMode, 100)) {
            result.add(PosixFilePermission.OWNER_EXECUTE);
        }

        if (isSet(aMode, 40)) {
            result.add(PosixFilePermission.GROUP_READ);
        }

        if (isSet(aMode, 20)) {
            result.add(PosixFilePermission.GROUP_WRITE);
        }

        if (isSet(aMode, 10)) {
            result.add(PosixFilePermission.GROUP_EXECUTE);
        }

        if (isSet(aMode, 4)) {
            result.add(PosixFilePermission.OTHERS_READ);
        }

        if (isSet(aMode, 2)) {
            result.add(PosixFilePermission.OTHERS_WRITE);
        }

        if (isSet(aMode, 1)) {
            result.add(PosixFilePermission.OTHERS_EXECUTE);
        }

        return result;
    }

    /**
     * Convert a PosixFilePermission set to an integer permissions mode.
     *
     * @param aPermSet A PosixFilePermission set
     * @return A permissions mode integer
     */
    @SuppressWarnings("PMD.NPathComplexity")
    public static int convertToInt(final Set<PosixFilePermission> aPermSet) {
        int result = 0;

        if (aPermSet.contains(PosixFilePermission.OWNER_READ)) {
            result = result | 400;
        }

        if (aPermSet.contains(PosixFilePermission.OWNER_WRITE)) {
            result = result | 200;
        }

        if (aPermSet.contains(PosixFilePermission.OWNER_EXECUTE)) {
            result = result | 100;
        }

        if (aPermSet.contains(PosixFilePermission.GROUP_READ)) {
            result = result | 40;
        }

        if (aPermSet.contains(PosixFilePermission.GROUP_WRITE)) {
            result = result | 20;
        }

        if (aPermSet.contains(PosixFilePermission.GROUP_EXECUTE)) {
            result = result | 10;
        }

        if (aPermSet.contains(PosixFilePermission.OTHERS_READ)) {
            result = result | 4;
        }

        if (aPermSet.contains(PosixFilePermission.OTHERS_WRITE)) {
            result = result | 2;
        }

        if (aPermSet.contains(PosixFilePermission.OTHERS_EXECUTE)) {
            result = result | 1;
        }

        return result;
    }

    private static boolean isSet(final int aMode, final int aTestbit) {
        return (aMode & aTestbit) == aTestbit;
    }

    /**
     * Copies a non-directory file from one location to another.
     *
     * @param aSourceFile A file to copy
     * @param aDestFile A destination location for the copied file
     * @return True if the copy was successful; else, false
     * @throws IOException If there is a problem copying the file
     */
    @SuppressWarnings({ "PMD.NPathComplexity", "PMD.AvoidFileStream" })
    private static boolean copyFile(final File aSourceFile, final File aDestFile) throws IOException {
        boolean success = true;

        // destructive copy
        if (aDestFile.exists() && !aDestFile.delete()) {
            success = false;
        }

        if (success && !aDestFile.createNewFile()) {
            LOGGER.warn(MessageCodes.UTIL_014, aDestFile.getAbsolutePath());
            success = false;
        }

        if (success) {
            try (FileOutputStream outputStream = new FileOutputStream(aDestFile);
                    FileInputStream inputStream = new FileInputStream(aSourceFile);
                    FileChannel source = inputStream.getChannel()) {
                outputStream.getChannel().transferFrom(source, 0, source.size());
            }
        }

        if (success && aDestFile.exists() && aSourceFile.canRead()) {
            success = aDestFile.setReadable(true, true);
        }

        if (success && aDestFile.exists() && aSourceFile.canWrite()) {
            success = aDestFile.setWritable(true, true);
        }

        if (success && aDestFile.exists() && aSourceFile.canExecute()) {
            success = aDestFile.setExecutable(true, true);
        }

        if (!success && !aDestFile.delete() && LOGGER.isWarnEnabled()) {
            LOGGER.warn(MessageCodes.UTIL_015, aDestFile);
        }

        return success;
    }

}
