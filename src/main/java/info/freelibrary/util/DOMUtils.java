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
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class DOMUtils {

    private DOMUtils() {
    }

    /**
     * Returns an XML string representation of the supplied node.
     * 
     * @param aNode A W3C node
     * @return An XML string representation of the supplied node
     */
    public static String toXML(Node aNode) throws TransformerException {
        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            StringWriter buffer = new StringWriter();
            DOMSource domSource = new DOMSource(aNode);
            StreamResult streamResult = new StreamResult(buffer);
            String omitDeclaration = OutputKeys.OMIT_XML_DECLARATION;

            transformer.setOutputProperty(omitDeclaration, "yes");
            transformer.transform(domSource, streamResult);

            return buffer.toString();
        } catch (TransformerConfigurationException details) {
            throw new RuntimeException(details);
        }
    }

}
