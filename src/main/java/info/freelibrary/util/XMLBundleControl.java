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

public class XMLBundleControl extends ResourceBundle.Control {

	private static final String FORMAT = "xml";

	public List<String> getFormats(String aBaseName) {
		if (aBaseName == null) {
			throw new NullPointerException();
		}

		return Arrays.asList(FORMAT);
	}

	public ResourceBundle newBundle(String aBaseName, Locale aLocale,
			String aFormat, ClassLoader aLoader, boolean aReload)
			throws IllegalAccessException, InstantiationException, IOException {
		ResourceBundle bundle = null;

		checkForNull(aBaseName, aLocale, aFormat, aLoader);

		if (aFormat.equals(FORMAT)) {
			String bundleName = toBundleName(aBaseName, aLocale);
			String resourceName = toResourceName(bundleName, aFormat);

			if (aReload) {
				URL url = aLoader.getResource(resourceName);

				if (url != null) {
					URLConnection connection = url.openConnection();

					if (connection != null) {
						// Disable caches to get fresh data for reloading.
						connection.setUseCaches(false);
						bundle = makeBundle(connection.getInputStream());
					}
				}
			}
			else {
				bundle = makeBundle(aLoader.getResourceAsStream(resourceName));
			}
		}

		return bundle;
	}

	private ResourceBundle makeBundle(InputStream aInputStream)
			throws IOException {
		BufferedInputStream bufferedInputStream;
		ResourceBundle bundle;

		bufferedInputStream = new BufferedInputStream(aInputStream);
		bundle = new XMLResourceBundle(bufferedInputStream);
		bufferedInputStream.close();

		return bundle;
	}

	private void checkForNull(Object... aVarargs) {
		for (Object arg : aVarargs) {
			if (arg == null) {
				throw new NullPointerException();
			}
		}
	}
}
