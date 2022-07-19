
package info.freelibrary.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * A resource bundle control that supports the {@link XMLResourceBundle}.
 */
public class CustomBundleControl extends ResourceBundle.Control {

    /**
     * A constant for the XML format.
     */
    private static final String XML = "xml";

    /**
     * A constant for the properties format.
     */
    private static final String PROPERTIES = "properties";

    /**
     * An array of expected resource file extensions.
     */
    private static final String[] FORMATS = { XML, PROPERTIES };

    /**
     * Returns a list of formats supported for the supplied base name.
     *
     * @param aBaseName for which to get formats
     * @return A {@link List} of strings
     */
    @Override
    public List<String> getFormats(final String aBaseName) {
        checkForNull(aBaseName);
        return Arrays.asList(FORMATS);
    }

    /**
     * Creates a new {@link ResourceBundle}.
     *
     * @param aBaseName A base name for the bundle
     * @param aLocale A locale for the bundle
     * @param aFormat A format for the bundle
     * @param aClassLoader A {@link ClassLoader} for the bundle
     * @param aReload Whether the bundle is to be reloaded
     */
    @Override
    public ResourceBundle newBundle(final String aBaseName, final Locale aLocale, final String aFormat,
            final ClassLoader aClassLoader, final boolean aReload)
            throws IllegalAccessException, InstantiationException, IOException {
        checkForNull(aBaseName, aLocale, aFormat, aClassLoader);

        if (canRead(aFormat)) {
            final String bundleName = toBundleName(aBaseName, aLocale);
            final String resourceName = toResourceName(bundleName, aFormat);
            final URL url;

            if (!aReload) {
                try (InputStream bundleStream = aClassLoader.getResourceAsStream(resourceName)) {
                    return makeBundle(bundleStream, aFormat);
                }
            }

            if ((url = aClassLoader.getResource(resourceName)) != null) {
                final URLConnection connection = url.openConnection();

                connection.setUseCaches(false);

                try (InputStream bundleStream = connection.getInputStream()) {
                    return makeBundle(bundleStream, aFormat);
                }
            }
        }

        return null;
    }

    /**
     * Detects if the supplied format can be read by this bundle control.
     *
     * @param aFormat A bundle file format
     * @return True if the bundle can be read; else, false
     */
    private boolean canRead(final String aFormat) {
        boolean result = false;

        for (final String format : FORMATS) {
            if (format.equals(aFormat)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Makes a {@link ResourceBundle} from the supplied {@link InputStream}.
     *
     * @param aInputStream An {@link InputStream} from which to build a {@link ResourceBundle}
     * @param aFormat The format of the bundle's resources
     * @return A {@link ResourceBundle}
     * @throws IOException If there is trouble building the {@link ResourceBundle} from the supplied {@link InputStream}
     */
    private ResourceBundle makeBundle(final InputStream aInputStream, final String aFormat) throws IOException {
        final ResourceBundle bundle;

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(aInputStream)) {
            if (XML.equalsIgnoreCase(aFormat)) {
                bundle = new XMLResourceBundle(bufferedInputStream);
            } else { // The default is a properties file
                bundle = new PropertiesResourceBundle(bufferedInputStream);
            }
        }

        return bundle;
    }

    /**
     * Checks a varargs for nulls.
     *
     * @param aVarargs A varargs to check for nulls
     */
    private void checkForNull(final Object... aVarargs) {
        for (final Object arg : aVarargs) {
            Objects.requireNonNull(arg);
        }
    }
}
