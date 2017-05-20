
package info.freelibrary.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

/**
 * Utilities to assist with working with URLs.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public final class URLUtils {

    private URLUtils() {
    }

    /**
     * Takes a URL and converts it to a File. The attempts to deal with Windows UNC format specific problems,
     * specifically files located on network shares and different drives. If the URL.getAuthority() returns null or is
     * empty, then only the url's path property is used to construct the file. Otherwise, the authority is prefixed
     * before the path. It is assumed that url.getProtocol returns "file". Authority is the drive or network share the
     * file is located on. Such as "C:", "E:", "\\fooServer"
     *
     * @param url a URL object that uses protocol "file"
     * @return a File that corresponds to the URL's location
     */
    public static File urlToFile(final URL url) {
        if (!"file".equals(url.getProtocol())) {
            return null; // not a File URL
        }
        String string = url.toExternalForm();
        if (string.contains("+")) {
            // this represents an invalid URL created using either
            // file.toURL(); or
            // file.toURI().toURL() on a specific version of Java 5 on Mac
            string = string.replace("+", "%2B");
        }
        try {
            string = URLDecoder.decode(string, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Could not decode the URL to UTF-8 format", e);
        }

        final String path3;

        final String simplePrefix = "file:/";
        final String standardPrefix = "file://";
        final String os = System.getProperty("os.name");

        if (os.toUpperCase().contains("WINDOWS") && string.startsWith(standardPrefix)) {
            // win32: host/share reference
            path3 = string.substring(standardPrefix.length() - 2);
        } else if (string.startsWith(standardPrefix)) {
            path3 = string.substring(standardPrefix.length());
        } else if (string.startsWith(simplePrefix)) {
            path3 = string.substring(simplePrefix.length() - 1);
        } else {
            final String auth = url.getAuthority();
            final String path2 = url.getPath().replace("%20", " ");
            if (auth != null && !auth.equals("")) {
                path3 = "//" + auth + path2;
            } else {
                path3 = path2;
            }
        }

        return new File(path3);
    }

    /**
     * Returns the contents of the supplied URL as a string.
     *
     * @param aURL A URL
     * @return The contents of the supplied URL as a string
     * @throws MalformedURLException If the supplied URL is malformed
     * @throws IOException If there is a problem reading the URL's contents
     */
    public static String urlToString(final URL aURL) throws MalformedURLException, IOException {
        final Scanner scanner = new Scanner(aURL.openStream(), "UTF-8");
        final String string = scanner.useDelimiter("\\A").next();

        scanner.close();
        return string;
    }
}
