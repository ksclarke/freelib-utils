
package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utilities for working with the Java classpath.
 */
public final class ClasspathUtils {

    /**
     * The <code>java.class.path</code> variable.
     */
    private static final String CLASSPATH = "java.class.path";

    /**
     * A constant for YES.
     */
    private static final String YES = "yes";

    /**
     * A constant for NO.
     */
    private static final String NO = "no";

    /**
     * The classpath delimiter.
     */
    private static final String DELIMETER = ":";

    /**
     * A constant for the Jar extension.
     */
    private static final String JAR_EXT = "jar";

    /**
     * The logger used by ClasspathUtils.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathUtils.class, MessageCodes.BUNDLE);

    /**
     * Creates a new classpath utilities class.
     */
    private ClasspathUtils() {
    }

    /**
     * Returns an String array of all the directory names in the system classpath
     *
     * @return The names of directories from the system classpath
     */
    public static String[] getDirs() {
        final ArrayList<String> list = new ArrayList<>();

        for (final String filename : System.getProperty(CLASSPATH).split(DELIMETER)) {
            final File file = new File(filename);

            LOGGER.debug(MessageCodes.UTIL_003, file, file.isDirectory() ? YES : NO);

            if (file.isDirectory()) {
                list.add(file.getAbsolutePath());
            }
        }

        return list.toArray(new String[0]);
    }

    /**
     * Returns an array of all the directories in the system classpath
     *
     * @return The directories from the system classpath
     */
    public static File[] getDirFiles() {
        final ArrayList<File> list = new ArrayList<>();

        for (final String filename : System.getProperty(CLASSPATH).split(DELIMETER)) {
            final File file = new File(filename);

            LOGGER.debug(MessageCodes.UTIL_003, file, file.isDirectory() ? YES : NO);

            if (file.isDirectory()) {
                list.add(file);
            }
        }

        return list.toArray(new File[0]);
    }

    /**
     * Returns an String array of all the directory names in the system classpath that match the supplied
     * <code>FilenameFilter</code>
     *
     * @param aFilter A filter to use while retrieving directories
     * @return The names of directories from the system classpath that match the supplied <code>FilenameFilter</code>
     */
    public static String[] getDirs(final FilenameFilter aFilter) {
        final ArrayList<String> list = new ArrayList<>();

        for (final String filename : System.getProperty(CLASSPATH).split(DELIMETER)) {
            final File file = new File(filename);

            if (aFilter.accept(file.getParentFile(), file.getName()) && file.isDirectory()) {
                list.add(file.getAbsolutePath());
            }
        }

        return list.toArray(new String[0]);
    }

    /**
     * Returns an array of all the directories in the system classpath that match the supplied
     * <code>FilenameFilter</code>
     *
     * @param aFilter A filter to use while retrieving directories
     * @return The directories from the system classpath that match the supplied <code>FilenameFilter</code>
     */
    public static File[] getDirFiles(final FilenameFilter aFilter) {
        final ArrayList<File> list = new ArrayList<>();

        for (final String filename : System.getProperty(CLASSPATH).split(DELIMETER)) {
            final File file = new File(filename);

            LOGGER.debug(MessageCodes.UTIL_003, file, file.isDirectory() ? YES : NO);

            if (aFilter.accept(file.getParentFile(), file.getName()) && file.isDirectory()) {
                LOGGER.debug(MessageCodes.UTIL_004, file.getAbsolutePath());
                list.add(file);
            }
        }

        return list.toArray(new File[0]);
    }

    /**
     * Returns an String array of all the names of the jars in the system classpath
     *
     * @return The names of jars from the system classpath
     */
    public static String[] getJars() {
        final ArrayList<String> list = new ArrayList<>();
        final FileExtFileFilter filter = new FileExtFileFilter(JAR_EXT);

        for (final String part : System.getProperty(CLASSPATH).split(File.pathSeparator)) {
            final File file = new File(part);

            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(file.getAbsolutePath());
            }
        }

        return list.toArray(new String[0]);
    }

    /**
     * Returns an String array of all the names of the jars in the system classpath that match the supplied
     * <code>FilenameFilter</code>
     *
     * @param aFilter A file name filter to use while retrieving Jar files
     * @return The names of jars from the system classpath that match the supplied <code>FilenameFilter</code>
     */
    public static String[] getJars(final FilenameFilter aFilter) {
        final ArrayList<String> list = new ArrayList<>();
        final FileExtFileFilter filter = new FileExtFileFilter(JAR_EXT);

        for (final String part : System.getProperty(CLASSPATH).split(File.pathSeparator)) {
            final File file = new File(part);
            final File parent = file.getParentFile();
            final String name = file.getName();

            if (filter.accept(parent, name) && aFilter.accept(parent, name)) {
                list.add(file.getAbsolutePath());
            }
        }

        return list.toArray(new String[0]);
    }

    /**
     * Returns an array of all the jar files in the system classpath
     *
     * @return The jar files from the system classpath
     * @throws IOException If there is trouble reading the file system while looking for Jar files
     */
    public static JarFile[] getJarFiles() throws IOException {
        final ArrayList<JarFile> list = new ArrayList<>();
        final FileExtFileFilter filter = new FileExtFileFilter(JAR_EXT);

        for (final String part : System.getProperty(CLASSPATH).split(File.pathSeparator)) {
            final File file = new File(part);

            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(new JarFile(file));
            }
        }

        return list.toArray(new JarFile[0]);
    }

    /**
     * Returns an array of all the jar files in the system classpath that match the supplied <code>FilenameFilter</code>
     *
     * @param aFilter A file name filter to use while retrieving Jar files
     * @return The jar files from the system classpath that match the supplied <code>FilenameFilter</code>
     * @throws IOException If there is trouble reading the file system while looking for Jar files
     */
    public static JarFile[] getJarFiles(final FilenameFilter aFilter) throws IOException {
        final ArrayList<JarFile> list = new ArrayList<>();
        final FileExtFileFilter filter = new FileExtFileFilter(JAR_EXT);

        for (final String part : System.getProperty(CLASSPATH).split(File.pathSeparator)) {
            final File file = new File(part);
            final File parent = file.getParentFile();
            final String name = file.getName();

            if (filter.accept(parent, name) && aFilter.accept(parent, name)) {
                list.add(new JarFile(file));
            }
        }

        return list.toArray(new JarFile[0]);
    }

    /**
     * Finds the first instance of the supplied file name in the classpath (in either a directory or a jar file) and
     * returns a {@link URL} for it.
     *
     * @param aFileName The name of the file we want to read
     * @return The {@link URL} of the file we want to read
     * @throws IOException If there is trouble reading from the file system or jars
     */
    public static URL findFirst(final String aFileName) throws IOException {
        final FileExtFileFilter filter = new FileExtFileFilter(JAR_EXT);

        for (final String cpEntry : System.getProperty(CLASSPATH).split(File.pathSeparator)) {
            final File file = new File(cpEntry);

            LOGGER.debug(MessageCodes.UTIL_005, cpEntry, aFileName);

            if (file.isDirectory()) {
                final File target = new File(file, aFileName);

                if (target.exists()) {
                    LOGGER.debug(MessageCodes.UTIL_006, aFileName, cpEntry);
                    return target.toURI().toURL();
                }
            } else if (filter.accept(file.getParentFile(), file.getName())) {
                try (JarFile jarFile = new JarFile(file)) {
                    final JarEntry jarEntry = jarFile.getJarEntry(aFileName);

                    if (jarEntry != null && jarEntry.getSize() > 0) {
                        LOGGER.debug(MessageCodes.UTIL_006, aFileName, cpEntry);
                        return file.toURI().toURL();
                    } else {
                        if (jarEntry != null) {
                            LOGGER.debug(MessageCodes.UTIL_007, jarEntry.getName());
                        } else {
                            LOGGER.debug(MessageCodes.UTIL_008, aFileName);
                        }
                    }
                }
            } else {
                LOGGER.debug(MessageCodes.UTIL_009, file);
            }
        }

        return null;
    }

    /**
     * Finds the first instance of the supplied file name in the classpath (in either a directory or jar file) and
     * returns a URL for it.
     *
     * @param aFileName The name of the file we want to read
     * @return True if the file is found in the classpath; else, false
     * @throws IOException If a directory or jar file can't be read
     */
    public static boolean find(final String aFileName) throws IOException {
        return findFirst(aFileName) != null;
    }
}
