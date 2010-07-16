package info.freelibrary.xq;

import info.freelibrary.util.DOMUtils;
import info.freelibrary.util.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.activation.MimetypesFileTypeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * An XQuery extension wrapper of some HTTP utilities that throw exceptions.
 * Throwing Java exceptions isn't acceptable for an XQuery extension.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class Put {

	private static final String XML_CONTENT_TYPE = "application/xml; charset=utf-8";
	private static final String XQ_MIME_TYPE = "application/xquery xq xql xquery";
	private static final String CHARSET = "UTF-8";

	public static final Element put(String aFileName, String aURL) throws IOException {
		return put(new File(aFileName), new URL(aURL));
	}
	
	public static final Element put(String aFileName, URL aURL) throws IOException {
		return put(new File(aFileName), aURL);
	}
	
	public static final Element put(File aFile, URL aURL) throws IOException {
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		fileTypeMap.addMimeTypes(XQ_MIME_TYPE);
		
		String contentType = fileTypeMap.getContentType(aFile);
		HttpURLConnection http = connect(aURL, contentType);
		OutputStream outStream = http.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(outStream, CHARSET);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		StringBuilder response = new StringBuilder();

		// Serialize the element as XML
		bufferedWriter.write(StringUtils.readAsUTF8(aFile));
		bufferedWriter.close();

		int responseCode = http.getResponseCode();
		String responseMessage = http.getResponseMessage();

		if (responseCode == 200) {
			InputStream inStream = http.getInputStream();
			InputStreamReader reader = new InputStreamReader(inStream);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				response.append(line + System.getProperty("line.separator"));
			}

			reader.close();
		}

		if (response.length() == 0) {
			response.append("<empty/>");
		}

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			StringReader reader = new StringReader(response.toString());
			Document doc = docBuilder.parse(new InputSource(reader));
			String codeAsText = Integer.toString(responseCode);
			Element root = doc.getDocumentElement();

			root.setAttribute("responseCode", codeAsText);
			root.setAttribute("responseMessage", responseMessage);

			return root;
		}
		catch (ParserConfigurationException details) {
			throw new RuntimeException(details);
		}
		catch (SAXException details) {
			throw new RuntimeException(details);
		}
	}

	public static final Element put(URL aURL, Element aNode) throws IOException {
		HttpURLConnection http = connect(aURL, XML_CONTENT_TYPE);
		StringBuilder response = new StringBuilder();
		int responseCode = writeXML(aNode, http);

		if (responseCode == 200) {
			InputStream inStream = http.getInputStream();
			InputStreamReader reader = new InputStreamReader(inStream);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				response.append(line + System.getProperty("line.separator"));
			}

			reader.close();
		}

		if (response.length() == 0) {
			response.append("<empty/>");
		}

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			StringReader reader = new StringReader(response.toString());
			Document doc = docBuilder.parse(new InputSource(reader));
			String codeAsText = Integer.toString(responseCode);
			Element root = doc.getDocumentElement();

			root.setAttribute("responseCode", codeAsText);

			return root;
		}
		catch (ParserConfigurationException details) {
			throw new RuntimeException(details);
		}
		catch (SAXException details) {
			throw new RuntimeException(details);
		}
	}

	/**
	 * Connects to a remote resource represented by a URL and sets the
	 * connection method to POST.
	 * 
	 * @param aURL The URL representation of a remote resource
	 * @return An active <code>HttpURLConnection</code>
	 * @throws IOException If there was a problem making the connection
	 */
	private static final HttpURLConnection connect(URL aURL, String aContentType)
			throws IOException {
		HttpURLConnection http = (HttpURLConnection) aURL.openConnection();

		// Set this to a PUT
		http.setDoOutput(true);
		http.setRequestMethod("PUT");

		// Properly set content type so receiving applications knows what
		// to expect (some won't process it otherwise)
		http.setRequestProperty("content-type", aContentType);

		http.connect();

		return http;
	}

	/**
	 * Writes an XML element to an HTTP POST connection.
	 * 
	 * @param aNode An XML element to write
	 * @param aHTTPConn The HTTP connection to which we write the element
	 * @return The status code of the write (whether it was successful)
	 * @throws IOException If there is a problem writing to the remote resource
	 */
	private static final int writeXML(Element aNode, HttpURLConnection aHTTPConn)
			throws IOException {
		OutputStream outStream = aHTTPConn.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(outStream, CHARSET);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);

		// Serialize the element as XML
		bufferedWriter.write(DOMUtils.toXML(aNode));
		bufferedWriter.close();

		return aHTTPConn.getResponseCode();
	}
}
