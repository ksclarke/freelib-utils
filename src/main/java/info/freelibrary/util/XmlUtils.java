
package info.freelibrary.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Utilities for working with XML.
 */
public final class XmlUtils {

    private static final int DEFAULT_INDENTATION = 2;

    private static final String YES = "yes";

    /**
     * Utility classes have private constructors.
     */
    private XmlUtils() {
    }

    /**
     * Pretty prints the supplied XML string using a supplied indentation.
     *
     * @param aUnformattedXmlString An unformatted XML string
     * @param aIndentation An indentation count
     * @return A pretty printed XML string
     * @throws TransformerConfigurationException If there is trouble configuring the JDK XML transformer
     * @throws TransformerException If there is trouble transforming the supplied XML
     */
    public static String format(final String aUnformattedXmlString, final int aIndentation)
            throws TransformerConfigurationException, TransformerException {
        final Source xmlInput = new StreamSource(new StringReader(aUnformattedXmlString));
        final StringWriter stringWriter = new StringWriter();
        final StreamResult xmlOutput = new StreamResult(stringWriter);
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer;

        transformerFactory.setAttribute("indent-number", aIndentation);
        transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
        transformer.setOutputProperty(OutputKeys.INDENT, YES);
        transformer.transform(xmlInput, xmlOutput);
        xmlOutput.getWriter().toString();

        return xmlOutput.getWriter().toString();
    }

    /**
     * Pretty prints the supplied XML string using a default indentation of two spaces.
     *
     * @param aUnformattedXmlString An unformatted XML string
     * @return A pretty printed XML string
     * @throws TransformerConfigurationException If there is trouble configuring the JDK XML transformer
     * @throws TransformerException If there is trouble transforming the supplied XML
     */
    public static String format(final String aUnformattedXmlString)
            throws TransformerConfigurationException, TransformerException {
        return format(aUnformattedXmlString, DEFAULT_INDENTATION);
    }
}
