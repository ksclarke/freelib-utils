
package info.freelibrary.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * A {@link ResourceBundle} that uses a XML file as its source.
 */
public class XMLResourceBundle extends AbstractResourceBundle {

    /**
     * Constructor that allows {@link ResourceBundle}s to be backed by XML files.
     *
     * @param aInStream An XML {@link InputStream}
     * @throws IOException If there is trouble reading from the XML file
     */
    XMLResourceBundle(final InputStream aInStream) throws IOException {
        super(new Properties());
        myProperties.loadFromXML(aInStream);
    }
}
