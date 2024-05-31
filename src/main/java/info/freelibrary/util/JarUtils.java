
package info.freelibrary.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import info.freelibrary.util.warnings.PMD;

/**
 * A small collection of Jar file utilities.
 */
public final class JarUtils {

    /**
     * The delimiter between the Jar file and its path.
     */
    public static final String JAR_URL_DELIMITER = "!/";

    /**
     * A constant for the protocol of a Jar file.
     */
    public static final String JAR_URL_PROTOCOL = "jar:file://";

    /**
     * The logger used by the Jar utilities.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JarUtils.class, MessageCodes.BUNDLE);

    /**
     * The number of components in a Jar URL.
     */
    private static final int URL_COMPONENT_COUNT = 2;

    /**
     * Creates a new Jar utilities instance.
     */
    private JarUtils() {
        // This is intentionally left empty
    }

    /**
     * Tests whether the supplied file path exists in the supplied Jar file. This method does not close the JarFile.
     *
     * @param aJarFile A jar file to search
     * @param aFilePath A path for which to search
     * @return True if the file path is found; else, false
     * @throws IOException If the jar file cannot be read
     */
    public static boolean contains(final JarFile aJarFile, final String aFilePath) throws IOException {
        final Enumeration<JarEntry> entries = aJarFile.entries();

        while (entries.hasMoreElements()) {
            if (entries.nextElement().getName().equals(aFilePath)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Extract the supplied path from the supplied Jar file to the supplied {@link java.io.File} location. This method
     * closes the JarFile.
     *
     * @param aJarFile A Jar file from which to extract
     * @param aFilePath The Jar's file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     * @throws NoSuchFileException If the Jar file cannot be found at the supplied path
     */
    public static void extract(final File aJarFile, final String aFilePath, final File aDestDir) throws IOException {
        extract(new JarFile(aJarFile), aFilePath, aDestDir);
    }

    /**
     * Extract a particular path from the supplied Jar file to a supplied {@link java.io.File} location. This method
     * closes the JarFile.
     *
     * @param aJarFile A Jar file from which to extract the supplied file path
     * @param aFilePath The Jar's file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     * @throws NoSuchFileException If the Jar file cannot be found at the supplied path
     */
    public static void extract(final JarFile aJarFile, final String aFilePath, final File aDestDir) throws IOException {
        try (aJarFile) {
            final Enumeration<JarEntry> entries = aJarFile.entries();

            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                final String entryName = entry.getName();
                final File file = new File(aDestDir + File.separator + entryName);

                if (entryName.equals(aFilePath)) {
                    final File parent = file.getParentFile();

                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IOException(LOGGER.getI18n(MessageCodes.UTIL_038, file));
                    }

                    try (InputStream inputStream = aJarFile.getInputStream(entry);
                            OutputStream outputStream = Files.newOutputStream(Paths.get(file.getAbsolutePath()))) {
                        IOUtils.copyStream(inputStream, outputStream);
                    }
                }
            }
        } // Close JarFile when done with it
    }

    /**
     * Extract the supplied path from the supplied Jar file to a supplied {@link java.io.File} location. This method
     * closes the JarFile.
     *
     * @param aJarFilePath The path to a Jar file from which to extract
     * @param aFilePath The Jar's file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     * @throws NoSuchFileException If the Jar file cannot be found at the supplied path
     */
    public static void extract(final String aJarFilePath, final String aFilePath, final File aDestDir)
            throws IOException {
        if (aJarFilePath.startsWith(JAR_URL_PROTOCOL)) {
            extract(new URL(aJarFilePath + JAR_URL_DELIMITER + aFilePath), aDestDir);
        } else {
            extract(new File(aJarFilePath), aFilePath, aDestDir);
        }
    }

    /**
     * Extracts the supplied path from the supplied Jar file's URL to the supplied {@link java.io.File} location. This
     * method closes the JarFile.
     *
     * @param aJarURL A Jar URL that contains the system file path of a Jar file and a subfile path
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     * @throws NoSuchFileException If the Jar file cannot be found at the supplied path
     * @throws MalformedUrlException If the supplied URL does not also contain a subfile path
     */
    public static void extract(final URL aJarURL, final File aDestDir) throws IOException {
        final String[] jarURLParts = aJarURL.getFile().split(JAR_URL_DELIMITER);
        final File jarFile;

        if (jarURLParts.length != URL_COMPONENT_COUNT) {
            throw new MalformedUrlException(LOGGER.getMessage(MessageCodes.UTIL_072, aJarURL));
        }

        jarFile = new File(jarURLParts[0].substring(aJarURL.getProtocol().length() + 4)); // Remove protocol
        extract(jarFile, jarURLParts[1], aDestDir);
    }

    /**
     * Gets a list of jar files in the classpath (this includes jars in jars, one level deep).
     *
     * @return An array of {@link URL}s found in the classpath
     * @throws IOException If there is trouble reading the classpath
     */
    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS, PMD.COGNITIVE_COMPLEXITY })
    public static URL[] getJarURLs() throws IOException {
        final List<URL> urlList = new LinkedList<>();

        for (final JarFile jarFile : ClasspathUtils.getJarFiles()) {
            try (jarFile) {
                final Manifest manifest = jarFile.getManifest();
                final URL jarURL = new URL(JAR_URL_PROTOCOL + jarFile.getName() + JAR_URL_DELIMITER);

                urlList.add(jarURL);

                if (manifest != null) {
                    @SuppressWarnings({ PMD.LOOSE_COUPLING })
                    final Attributes attributes = manifest.getMainAttributes();

                    if (attributes != null) {
                        final String classpath = attributes.getValue("Class-Path");

                        if (classpath != null) {
                            final StringTokenizer tokenizer = new StringTokenizer(classpath);

                            while (tokenizer.hasMoreTokens()) {
                                final String jarPath = tokenizer.nextToken();

                                if (jarPath.endsWith(".jar")) {
                                    urlList.add(new URL(jarURL.toExternalForm() + jarPath));
                                }
                            }
                        }
                    }
                }
            }
        }

        return urlList.toArray(new URL[0]);
    }
}
