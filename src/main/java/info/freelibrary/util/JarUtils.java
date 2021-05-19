
package info.freelibrary.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * A small collection of Jar file utilities.
 */
public final class JarUtils {

    /**
     * The logger used by the Jar utilities.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JarUtils.class, MessageCodes.BUNDLE);

    /**
     * A constant for the protocol of a Jar file.
     */
    private static final String JAR_URL_PROTOCOL = "jar:file://";

    /**
     * Creates a new Jar utilities instance.
     */
    private JarUtils() {
        // This is intentionally left empty
    }

    /**
     * Gets a list of jar files in the classpath (this includes jars in jars, one level deep).
     *
     * @return An array of {@link URL}s found in the classpath
     * @throws IOException If there is trouble reading the classpath
     */
    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
    public static URL[] getJarURLs() throws IOException {
        final List<URL> urlList = new LinkedList<>();

        for (final JarFile jarFile : ClasspathUtils.getJarFiles()) {
            try (jarFile) {
                final Manifest manifest = jarFile.getManifest();
                final URL jarURL = new URL(JAR_URL_PROTOCOL + jarFile.getName() + "!/");

                urlList.add(jarURL);

                if (manifest != null) {
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

    /**
     * Extract a particular path from a supplied Jar file to a supplied {@link java.io.File} location.
     *
     * @param aJarFilePath A path to a Jar file from which to extract
     * @param aFilePath The Jar file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     */
    public static void extract(final String aJarFilePath, final String aFilePath, final File aDestDir)
            throws IOException {
        File file;

        try {
            // Opening the URL connection just parses location, it doesn't really "open" in the I/O sense
            final JarURLConnection connection = (JarURLConnection) new URL(aJarFilePath).openConnection();

            file = new File(connection.getJarFileURL().getFile());
        } catch (final MalformedURLException details) {
            file = new File(aJarFilePath);
        }

        extract(file, aFilePath, aDestDir);
    }

    /**
     * Extract a particular path from a supplied Jar file to a supplied {@link java.io.File} location.
     *
     * @param aJarFile A Jar file from which to extract
     * @param aFilePath The Jar file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     */
    public static void extract(final File aJarFile, final String aFilePath, final File aDestDir) throws IOException {
        extract(new JarFile(aJarFile), aFilePath, aDestDir);
    }

    /**
     * Extract a particular path from a supplied Jar file to a supplied {@link java.io.File} location.
     *
     * @param aJarFile A Jar file from which to extract
     * @param aFilePath The Jar file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     */
    public static void extract(final JarFile aJarFile, final String aFilePath, final File aDestDir) throws IOException {
        final Enumeration<JarEntry> entries = aJarFile.entries();

        try (aJarFile) {
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
        }
    }
}
