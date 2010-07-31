package info.freelibrary.util;

import java.util.regex.Pattern;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMUtils implements XMLConstants {

	private static Pattern myPattern = Pattern
			.compile("&(lt|gt|amp|quot|apos|#.{3}|#x.{4});.*");

	private static boolean PRETTY_PRINTED;

	private DOMUtils() {
	}

	// This isn't real pretty printing
	public static boolean brokenUp() {
		return PRETTY_PRINTED;
	}

	// This isn't real pretty printing
	public static void brokenUp(boolean aBool) {
		PRETTY_PRINTED = aBool;
	}

	public static String toXML(Node aNode) {
		return buildXML(new StringBuilder(), aNode, 0).toString();
	}

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
				String nsURI = attribute.getNamespaceURI();

				if ((nsURI != null && !aNode.equals(XML1998_NS_URI))
						|| nsURI == null) {

					if (!XML.equals(attribute.getLocalName())) {
						String attrValue = attribute.getTextContent();

						aBuilder.append(SPACE).append(attribute.getNodeName());
						aBuilder.append(START_ATTR);
						aBuilder.append(escapeEntities(attrValue));
						aBuilder.append(END_ATTR);
					}
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
				}
				else {
					buildXML(aBuilder, node, aLevel + 1);
				}
			}

			aBuilder.append(START_CLOSE_ELEM).append(nodeName);
			aBuilder.append(END_FULL_ELEM);

			if (brokenUp()) {
				aBuilder.append(System.getProperty("line.separator"));
			}
		}
		else {
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
				int offset = (textLength - index) >= 6 ? 6 : textLength - index;
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
