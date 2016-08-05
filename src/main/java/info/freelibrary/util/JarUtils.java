
package info.freelibrary.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * A small collection of {@link java.util.Jar} file utilities.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class JarUtils {

    private static final String JAR_URL_PROTOCOL = "jar:file://";

    private JarUtils() {
    }

    /**
     * Gets a list of jar files in the classpath (this includes jars in jars, one level deep).
     *
     * @return An array of {@link URL}s found in the classpath
     * @throws IOException If there is trouble reading the classpath
     */
    public static URL[] getJarURLs() throws IOException {
        final List<URL> urlList = new LinkedList<URL>();

        for (final JarFile jarFile : ClasspathUtils.getJarFiles()) {
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

            jarFile.close();
        }

        return urlList.toArray(new URL[urlList.size()]);
    }

    /**
     * Extract a particular path from a supplied {@link java.util.Jar} file to a supplied {@link java.io.File} location.
     *
     * @param aJarFilePath A path to a Jar file from which to extract
     * @param aFilePath The Jar file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     */
    public static final void extract(final String aJarFilePath, final String aFilePath, final File aDestDir)
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
     * Extract a particular path from a supplied {@link java.util.Jar} file to a supplied {@link java.io.File} location.
     *
     * @param aJarFile A Jar file from which to extract
     * @param aFilePath The Jar file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     */
    public static final void extract(final File aJarFile, final String aFilePath, final File aDestDir)
            throws IOException {
        extract(new JarFile(aJarFile), aFilePath, aDestDir);
    }

    /**
     * Extract a particular path from a supplied {@link java.util.Jar} file to a supplied {@link java.io.File} location.
     *
     * @param aJarFile A Jar file from which to extract
     * @param aFilePath The Jar file path of the file to extract
     * @param aDestDir The destination directory into which the file should be extracted
     * @throws IOException If there is an exception thrown while reading or writing the file
     */
    public static final void extract(final JarFile aJarFile, final String aFilePath, final File aDestDir)
            throws IOException {
        final Enumeration<JarEntry> entries = aJarFile.entries();

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

                    final InputStream inputStream = aJarFile.getInputStream(entry);
                    final FileOutputStream outputStream = new FileOutputStream(file);

                    IOUtils.copyStream(inputStream, outputStream);
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(outputStream);
                }
            }
        } finally {
            aJarFile.close();
        }
    }
}
