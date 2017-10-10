/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

/**
 * Utilities for working with W3C DOMs.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public final class DOMUtils {

    private DOMUtils() {
    }

    /**
     * Returns an XML string representation of the supplied node.
     *
     * @param aNode A W3C node
     * @return An XML string representation of the supplied node
     * @throws TransformerException If there is trouble with the XSL transformation
     */
    public static String toXML(final Node aNode) throws TransformerException {
        try {
            final TransformerFactory transFactory = TransformerFactory.newInstance();
            final Transformer transformer = transFactory.newTransformer();
            final StringWriter buffer = new StringWriter();
            final DOMSource domSource = new DOMSource(aNode);
            final StreamResult streamResult = new StreamResult(buffer);
            final String omitDeclaration = OutputKeys.OMIT_XML_DECLARATION;

            transformer.setOutputProperty(omitDeclaration, "yes");
            transformer.transform(domSource, streamResult);

            return buffer.toString();
        } catch (final TransformerConfigurationException details) {
            throw new I18nRuntimeException(details);
        }
    }

}
