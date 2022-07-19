
package info.freelibrary.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * A {@link ResourceBundle} that uses a properties file as its source.
 */
class PropertiesResourceBundle extends AbstractResourceBundle {

    /**
     * Constructor that allows {@link ResourceBundle}s to be backed by property files with Logback's style of string
     * replacement.
     *
     * @param aInStream A property file {@link InputStream}
     * @throws IOException If there is trouble reading from the properties file
     */
    PropertiesResourceBundle(final InputStream aInStream) throws IOException {
        super(new Properties());
        myProperties.load(aInStream);
    }
}
