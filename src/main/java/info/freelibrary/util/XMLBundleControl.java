/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A resource bundle control that supports the {@link XMLResourceBundle}.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class XMLBundleControl extends ResourceBundle.Control {

    private static final String FORMAT = "xml";

    /**
     * Returns a list of formats supported for the supplied base name.
     * 
     * @param aBaseName for which to get formats
     * @return A {@link List} of strings
     */
    public List<String> getFormats(String aBaseName) {
        if (aBaseName == null) {
            throw new NullPointerException();
        }

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
    public ResourceBundle newBundle(String aBaseName, Locale aLocale, String aFormat, ClassLoader aClassLoader,
            boolean aReload) throws IllegalAccessException, InstantiationException, IOException {
        ResourceBundle bundle = null;

        checkForNull(aBaseName, aLocale, aFormat, aClassLoader);

        if (aFormat.equals(FORMAT)) {
            String bundleName = toBundleName(aBaseName, aLocale);
            String resourceName = toResourceName(bundleName, aFormat);

            if (aReload) {
                URL url = aClassLoader.getResource(resourceName);

                if (url != null) {
                    URLConnection connection = url.openConnection();

                    if (connection != null) {
                        // Disable caches to get fresh data for reloading.
                        connection.setUseCaches(false);
                        bundle = makeBundle(connection.getInputStream());
                    }
                }
            } else {
                InputStream bundleStream = aClassLoader.getResourceAsStream(resourceName);
                bundle = makeBundle(bundleStream);
            }
        }

        return bundle;
    }

    /**
     * Makes a {@link ResourceBundle} from the supplied {@link InputStream}.
     * 
     * @param aInputStream An {@link InputStream} from which to build a {@link ResourceBundle}
     * @return A {@link ResourceBundle}
     * @throws IOException If there is trouble building the {@link ResourceBundle} from the supplied {@link InputStream}
     */
    private ResourceBundle makeBundle(InputStream aInputStream) throws IOException {
        BufferedInputStream bufferedInputStream;
        ResourceBundle bundle;

        bufferedInputStream = new BufferedInputStream(aInputStream);
        bundle = new XMLResourceBundle(bufferedInputStream);
        bufferedInputStream.close();

        return bundle;
    }

    /**
     * Checks a varargs for nulls.
     * 
     * @param aVarargs A varargs to check for nulls
     */
    private void checkForNull(Object... aVarargs) {
        for (Object arg : aVarargs) {
            if (arg == null) {
                throw new NullPointerException();
            }
        }
    }
}
