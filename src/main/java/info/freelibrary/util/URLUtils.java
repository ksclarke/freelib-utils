
package info.freelibrary.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

/**
 * Utilities to assist with working with URLs.
 */
public final class URLUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLUtils.class, MessageCodes.BUNDLE);

    private static final String FILE_PROTOCOL = "file";

    private static final String SIMPLE_PREFIX = FILE_PROTOCOL + ":/";

    private static final String STANDARD_PREFIX = SIMPLE_PREFIX + "/";

    private static final String OS = System.getProperty("os.name");

    private static final String PERCENT = "%25";

    private static final String PLUS = "+";

    private URLUtils() {
    }

    /**
     * Takes a URL and converts it to a File. The attempts to deal with Windows UNC format specific problems,
     * specifically files located on network shares and different drives. If the URL.getAuthority() returns null or is
     * empty, then only the url's path property is used to construct the file. Otherwise, the authority is prefixed
     * before the path. It is assumed that url.getProtocol returns "file". Authority is the drive or network share the
     * file is located on. Such as "C:", "E:", "\\fooServer"
     *
     * @param aURL a URL object that uses protocol "file"
     * @return A File that corresponds to the URL's location
     */
    public static File toFile(final URL aURL) {
        Objects.requireNonNull(aURL, LOGGER.getI18n(MessageCodes.UTIL_031));

        if (!FILE_PROTOCOL.equals(aURL.getProtocol())) {
            throw new UnsupportedOperationException(LOGGER.getI18n(MessageCodes.UTIL_030));
        }

        String string = aURL.toExternalForm();

        // An invalid URL created using file.toURL() or file.toURI().toURL() on a specific version of Java 5 on Mac
        if (string.contains(PLUS)) {
            string = string.replace(PLUS, "%2B");
        }

        try {
            string = URLDecoder.decode(string, StandardCharsets.UTF_8.name());
        } catch (final java.io.UnsupportedEncodingException details) {
            throw new UnsupportedEncodingI18nException(details, MessageCodes.UTIL_029);
        }

        final String path3;

        if (OS.toUpperCase(Locale.US).contains("WINDOWS") && string.startsWith(STANDARD_PREFIX)) {
            path3 = string.substring(STANDARD_PREFIX.length() - 2);
        } else if (string.startsWith(STANDARD_PREFIX)) {
            path3 = string.substring(STANDARD_PREFIX.length());
        } else if (string.startsWith(SIMPLE_PREFIX)) {
            path3 = string.substring(SIMPLE_PREFIX.length() - 1);
        } else {
            final String auth = aURL.getAuthority();
            final String path2 = aURL.getPath().replace("%20", " ");

            if (auth != null && !"".equals(auth)) {
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
     * @throws IOException If there is a problem reading the URL's contents
     */
    public static String toString(final URL aURL) throws IOException {
        final Scanner scanner = new Scanner(aURL.openStream(), StandardCharsets.UTF_8.name());
        final String string = scanner.useDelimiter("\\A").next();

        scanner.close();

        return string;
    }

    /**
     * Decodes an encoded path. If it has been doubly encoded, it is doubly decoded.
     *
     * @param aString An encoded path
     * @return A decoded path
     */
    public static String decode(final String aString) {
        return decode(aString, StandardCharsets.UTF_8.name());
    }

    /**
     * Decodes an encoded path. If it has been doubly encoded, it is doubly decoded.
     *
     * @param aURL An encoded URL String
     * @param aEncoding A character encoding to use for the string decoding
     * @return A decoded URL String
     */
    public static String decode(final String aURL, final String aEncoding) {
        String urlString = aURL;
        String decodedString;

        try {
            do {
                decodedString = urlString;

                // Java's URLDecoder needs a little help with occurrences of '%' that aren't percent escaped values
                urlString = URLDecoder.decode(decodedString.replaceAll("%(?![0-9a-fA-F]{2})", PERCENT), aEncoding);
            } while (!urlString.equals(decodedString));
        } catch (final java.io.UnsupportedEncodingException details) {
            throw new UnsupportedEncodingI18nException(details, aEncoding);
        }

        if (LOGGER.isDebugEnabled() && !aURL.equals(decodedString)) {
            LOGGER.debug(MessageCodes.UTIL_048, aURL, decodedString);
        }

        return decodedString;
    }

    /**
     * Percent-encodes supplied string but only after decoding it completely first.
     *
     * @param aString The string to encode
     * @param aIgnoreSlashFlag Whether slashes should be encoded or not
     * @return The percent-encoded string
     */
    public static String encode(final String aString, final boolean aIgnoreSlashFlag) {
        final CharacterIterator iterator = new StringCharacterIterator(decode(aString));
        final StringBuilder builder = new StringBuilder();

        for (char c = iterator.first(); c != CharacterIterator.DONE; c = iterator.next()) {
            switch (c) {
                case '%':
                    builder.append(PERCENT);
                    break;
                case '/':
                    if (aIgnoreSlashFlag) {
                        builder.append(c);
                    } else {
                        builder.append("%2F");
                    }
                    break;
                case '?':
                    builder.append("%3F");
                    break;
                case '#':
                    builder.append("%23");
                    break;
                case '[':
                    builder.append("%5B");
                    break;
                case ']':
                    builder.append("%5D");
                    break;
                case '@':
                    builder.append("%40");
                    break;
                default:
                    builder.append(c);
                    break;
            }
        }

        // Must percent-encode any characters outside the US-ASCII set
        return URI.create(builder.toString()).toASCIIString();
    }
}
