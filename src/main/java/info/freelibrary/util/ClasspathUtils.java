/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for working with the Java classpath.
 *
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class ClasspathUtils {

    private static final String CLASSPATH = "java.class.path";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathUtils.class);

    private ClasspathUtils() {
    }

    /**
     * Returns an String array of all the directory names in the system classpath
     *
     * @return The names of directories from the system classpath
     */
    public static String[] getDirs() {
        final ArrayList<String> list = new ArrayList<String>();

        for (final String filename : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(filename);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Checking to see if {} is a dir ({})", file.getAbsolutePath(), file.isDirectory() ? "yes"
                        : "no");
            }

            if (file.isDirectory()) {
                list.add(file.getAbsolutePath());
            }
        }

        return list.toArray(new String[list.size()]);
    }

    /**
     * Returns an array of all the directories in the system classpath
     *
     * @return The directories from the system classpath
     */
    public static File[] getDirFiles() {
        final ArrayList<File> list = new ArrayList<File>();

        for (final String filename : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(filename);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Checking to see if {} is a dir ({})", file.getAbsolutePath(), file.isDirectory() ? "yes"
                        : "no");
            }

            if (file.isDirectory()) {
                list.add(file);
            }
        }

        return list.toArray(new File[list.size()]);
    }

    /**
     * Returns an String array of all the directory names in the system classpath that match the supplied
     * <code>FilenameFilter</code>
     *
     * @return The names of directories from the system classpath that match the supplied <code>FilenameFilter</code>
     */
    public static String[] getDirs(final FilenameFilter aFilter) {
        final ArrayList<String> list = new ArrayList<String>();

        for (final String filename : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(filename);

            if (aFilter.accept(file.getParentFile(), file.getName()) && file.isDirectory()) {
                list.add(file.getAbsolutePath());
            }
        }

        return list.toArray(new String[list.size()]);
    }

    /**
     * Returns an array of all the directories in the system classpath that match the supplied
     * <code>FilenameFilter</code>
     *
     * @return The directories from the system classpath that match the supplied <code>FilenameFilter</code>
     */
    public static File[] getDirFiles(final FilenameFilter aFilter) {
        final ArrayList<File> list = new ArrayList<File>();

        for (final String filename : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(filename);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Checking to see if {} is a dir ({})", file.getAbsolutePath(), file.isDirectory() ? "yes"
                        : "no");
            }

            if (aFilter.accept(file.getParentFile(), file.getName()) && file.isDirectory()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("{} is a directory", file.getAbsolutePath());
                }

                list.add(file);
            }
        }

        return list.toArray(new File[list.size()]);
    }

    /**
     * Returns an String array of all the names of the jars in the system classpath
     *
     * @return The names of jars from the system classpath
     */
    public static String[] getJars() {
        final ArrayList<String> list = new ArrayList<String>();
        final FileExtFileFilter filter = new FileExtFileFilter("jar");

        for (final String part : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(part);

            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(file.getAbsolutePath());
            }
        }

        return list.toArray(new String[list.size()]);
    }

    /**
     * Returns an String array of all the names of the jars in the system classpath that match the supplied
     * <code>FilenameFilter</code>
     *
     * @return The names of jars from the system classpath that match the supplied <code>FilenameFilter</code>
     */
    public static String[] getJars(final FilenameFilter aFilter) {
        final ArrayList<String> list = new ArrayList<String>();
        final FileExtFileFilter filter = new FileExtFileFilter("jar");

        for (final String part : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(part);
            final File parent = file.getParentFile();
            final String name = file.getName();

            if (filter.accept(parent, name) && aFilter.accept(parent, name)) {
                list.add(file.getAbsolutePath());
            }
        }

        return list.toArray(new String[list.size()]);
    }

    /**
     * Returns an array of all the jar files in the system classpath
     *
     * @return The jar files from the system classpath
     */
    public static JarFile[] getJarFiles() throws IOException {
        final ArrayList<JarFile> list = new ArrayList<JarFile>();
        final FileExtFileFilter filter = new FileExtFileFilter("jar");

        for (final String part : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(part);

            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(new JarFile(file));
            }
        }

        return list.toArray(new JarFile[list.size()]);
    }

    /**
     * Returns an array of all the jar files in the system classpath that match the supplied <code>FilenameFilter</code>
     *
     * @return The jar files from the system classpath that match the supplied <code>FilenameFilter</code>
     */
    public static JarFile[] getJarFiles(final FilenameFilter aFilter) throws IOException {
        final ArrayList<JarFile> list = new ArrayList<JarFile>();
        final FileExtFileFilter filter = new FileExtFileFilter("jar");

        for (final String part : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(part);
            final File parent = file.getParentFile();
            final String name = file.getName();

            if (filter.accept(parent, name) && aFilter.accept(parent, name)) {
                list.add(new JarFile(file));
            }
        }

        return list.toArray(new JarFile[list.size()]);
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
        final FileExtFileFilter filter = new FileExtFileFilter("jar");

        for (final String cpEntry : System.getProperty(CLASSPATH).split(":")) {
            final File file = new File(cpEntry);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Checking {} for {}", cpEntry, aFileName);
            }

            if (file.isDirectory()) {
                final File target = new File(file, aFileName);

                if (target.exists()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Found {} in {}", aFileName, cpEntry);
                    }

                    return target.toURI().toURL();
                }
            } else if (filter.accept(file.getParentFile(), file.getName())) {
                final JarFile jarFile = new JarFile(file);
                final JarEntry jarEntry = jarFile.getJarEntry(aFileName);

                if (jarEntry != null && jarEntry.getSize() > 0) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Found {} in {}", aFileName, cpEntry);
                    }

                    jarFile.close();
                    return file.toURI().toURL();
                } else if (LOGGER.isDebugEnabled()) {
                    if (jarEntry != null) {
                        LOGGER.debug("Jar entry {} did not match search pattern", jarEntry.getName());
                    } else {
                        LOGGER.debug("Could not get {} from jar file", aFileName);
                    }
                }

                jarFile.close();
            } else if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Did not check {} because not a directory or jar file", file);
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
