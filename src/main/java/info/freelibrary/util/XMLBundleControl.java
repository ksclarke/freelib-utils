
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
public class XMLBundleControl extends ResourceBundle.Control {

    /**
     * An expected resource file extension.
     */
    private static final String FORMAT = "xml";

    /**
     * Returns a list of formats supported for the supplied base name.
     *
     * @param aBaseName for which to get formats
     * @return A {@link List} of strings
     */
    @Override
    public List<String> getFormats(final String aBaseName) {
        Objects.requireNonNull(aBaseName);
        return Arrays.asList(FORMAT);
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

        if (FORMAT.equals(aFormat)) {
            final String bundleName = toBundleName(aBaseName, aLocale);
            final String resourceName = toResourceName(bundleName, aFormat);

            if (aReload) {
                final URL url = aClassLoader.getResource(resourceName);

                if (url != null) {
                    final URLConnection connection = url.openConnection();

                    connection.setUseCaches(false);

                    try (InputStream bundleStream = connection.getInputStream()) {
                        return makeBundle(bundleStream);
                    }
                }
            } else {
                try (InputStream bundleStream = aClassLoader.getResourceAsStream(resourceName)) {
                    return makeBundle(bundleStream);
                }
            }
        }

        return null;
    }

    /**
     * Makes a {@link ResourceBundle} from the supplied {@link InputStream}.
     *
     * @param aInputStream An {@link InputStream} from which to build a {@link ResourceBundle}
     * @return A {@link ResourceBundle}
     * @throws IOException If there is trouble building the {@link ResourceBundle} from the supplied {@link InputStream}
     */
    private ResourceBundle makeBundle(final InputStream aInputStream) throws IOException {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(aInputStream);
        final ResourceBundle bundle = new XMLResourceBundle(bufferedInputStream);

        bufferedInputStream.close();

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
