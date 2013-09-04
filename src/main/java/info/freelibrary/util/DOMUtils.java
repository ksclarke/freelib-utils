/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.util.regex.Pattern;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utilities for working with W3C DOMs.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class DOMUtils {

    public static final String XML2000_NS_URI = "http://www.w3.org/2000/xmlns/";

    public static final String XML1998_NS_URI =
            "http://www.w3.org/XML/1998/namespace";

    public static final String XML = "xml";

    public static final String START_OPEN_ELEM = "<";

    public static final String END_FULL_ELEM = ">";

    public static final String SPACE = " ";

    public static final String START_ATTR = "=\"";

    public static final String END_ATTR = "\"";

    public static final String START_CLOSE_ELEM = "</";

    public static final String END_EMPTY_ELEM = "/>";

    public static final String START_XML_DECL = "<?xml ";

    public static final String END_XML_DECL = " ?>";

    public static final String START_COMMENT = "<!-- ";

    public static final String END_COMMENT = " -->";

    public static final String DEFAULT_ENCODING = "UTF-8";

    private static Pattern myPattern = Pattern
            .compile("&(lt|gt|amp|quot|apos|#.{3}|#x.{4});.*");

    private static boolean PRETTY_PRINTED;

    private DOMUtils() {
    }

    /**
     * Returns whether pseudo-pretty-printing is used.
     * 
     * @return True if pseudo-pretty-printing is used
     */
    public static boolean brokenUp() {
        return PRETTY_PRINTED;
    }

    /**
     * Sets whether pseudo-pretty-printing should be used.
     * 
     * @param aBool True if pseudo-pretty-printing should be used
     */
    public static void brokenUp(boolean aBool) {
        PRETTY_PRINTED = aBool;
    }

    /**
     * Returns an XML string representation of the supplied node.
     * 
     * @param aNode A W3C node
     * @return An XML string representation of the supplied node
     */
    public static String toXML(Node aNode) {
        return buildXML(new StringBuilder(), aNode, 0).toString();
    }

    /**
     * Constructs an XML document in a {@link StringBuilder}.
     * 
     * @param aBuilder The {@link StringBuilder} in which we're constructing the
     *        XML
     * @param aNode A W3C node we're turning into an XML string
     * @param aLevel The level in the hierarchy we're currently at
     * @return The {@link StringBuilder} containing our markup
     */
    private static StringBuilder buildXML(StringBuilder aBuilder, Node aNode,
            int aLevel) {
        NamedNodeMap attributes = aNode.getAttributes();
        NodeList nodes = aNode.getChildNodes();
        String nodeName = aNode.getNodeName();

        aBuilder.append(START_OPEN_ELEM).append(nodeName);

        // aNode.hasAttributes() returns an incorrect result in Saxon
        if (attributes != null) {
            for (int index = 0; index < attributes.getLength(); index++) {
                Node attribute = attributes.item(index);
                String attrNsURI = attribute.getNamespaceURI();
                String nodeNsURI = aNode.getNamespaceURI();

                if ((!nodeNsURI.equals(XML1998_NS_URI) || attrNsURI == null) &&
                        !XML.equals(attribute.getLocalName())) {
                    String attrValue = attribute.getTextContent();

                    aBuilder.append(SPACE).append(attribute.getNodeName());
                    aBuilder.append(START_ATTR);
                    aBuilder.append(escapeEntities(attrValue));
                    aBuilder.append(END_ATTR);
                }
            }
        }

        // Might as well do the same thing here
        if (nodes != null) {
            aBuilder.append(END_FULL_ELEM);

            for (int index = 0; index < nodes.getLength(); index++) {
                Node node = nodes.item(index);

                if (node.getNodeType() == Node.TEXT_NODE) {
                    aBuilder.append(escapeEntities(node.getTextContent()));
                } else {
                    buildXML(aBuilder, node, aLevel + 1);
                }
            }

            aBuilder.append(START_CLOSE_ELEM).append(nodeName);
            aBuilder.append(END_FULL_ELEM);

            if (brokenUp()) {
                aBuilder.append(System.getProperty("line.separator"));
            }
        } else {
            aBuilder.append(END_EMPTY_ELEM);

            if (brokenUp()) {
                aBuilder.append(System.getProperty("line.separator"));
            }
        }

        return aBuilder;
    }

    private static String escapeEntities(String aText) {
        StringBuilder result = new StringBuilder();
        int textLength = aText.length();

        for (int index = 0; index < textLength; index++) {
            char character = aText.charAt(index);

            switch (character) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '\'':
                    result.append("&apos;");
                    break;
                case '&':
                    int offset =
                            (textLength - index) >= 6 ? 6 : textLength - index;
                    String substring = aText.substring(index, index + offset);

                    // Escape entity if not already an escaped entity
                    if (!myPattern.matcher(substring).matches()) {
                        result.append("&amp;");
                    }

                    break;
                default:
                    result.append(character);
            }
        }

        return result.toString();
    }
}
