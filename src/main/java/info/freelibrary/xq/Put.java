/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.xq;

import info.freelibrary.util.DOMUtils;
import info.freelibrary.util.IOUtils;
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

    private static final String XML_CONTENT_TYPE =
            "application/xml; charset=utf-8";

    private static final String XQ_MIME_TYPE =
            "application/xquery xq xql xquery";

    private static final String EOL = System.getProperty("line.separator");

    private static final String CHARSET = "UTF-8";

    /**
     * PUTs the file represented from the supplied file name to the supplied URL
     * (in string form).
     * 
     * @param aFileName The file name of the file to PUT to the supplied URL
     * @param aURL The place to PUT the supplied file name
     * @return The XML response from the PUT
     * @throws IOException If there is trouble with the PUT
     */
    public static Element put(String aFileName, String aURL)
        throws IOException {
        return put(new File(aFileName), new URL(aURL));
    }

    /**
     * PUTs the file represented from the supplied file name to the supplied
     * {@link URL}.
     * 
     * @param aFileName The file name of the file to PUT to the supplied URL
     * @param aURL The place to PUT the supplied file name
     * @return The XML response from the PUT
     * @throws IOException If there is trouble with the PUT
     */
    public static Element put(String aFileName, URL aURL) throws IOException {
        return put(new File(aFileName), aURL);
    }

    /**
     * PUTs the supplied {@link File} to the supplied {@link URL}.
     * 
     * @param aFile The file to PUT to the supplied URL
     * @param aURL The place to PUT the supplied file
     * @return The XML response from the PUT
     * @throws IOException If there is trouble with the PUT
     */
    public static Element put(File aFile, URL aURL) throws IOException {
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        fileTypeMap.addMimeTypes(XQ_MIME_TYPE);

        String contentType = fileTypeMap.getContentType(aFile);
        HttpURLConnection http = connect(aURL, contentType);
        StringBuilder response = new StringBuilder();
        OutputStream out = http.getOutputStream();
        BufferedWriter bWriter = null;

        try {
            bWriter = new BufferedWriter(new OutputStreamWriter(out, CHARSET));
            bWriter.write(StringUtils.readAsUTF8(aFile));
        } finally {
            IOUtils.closeQuietly(bWriter);
        }

        int responseCode = http.getResponseCode();
        String responseMessage = http.getResponseMessage();

        if (responseCode == 200) {
            BufferedReader bReader = null;

            InputStream in = http.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, CHARSET);
            String line;

            try {
                bReader = new BufferedReader(reader);

                while ((line = bReader.readLine()) != null) {
                    response.append(line + EOL);
                }
            } finally {
                IOUtils.closeQuietly(bReader);
            }
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
        } catch (ParserConfigurationException details) {
            throw new RuntimeException(details);
        } catch (SAXException details) {
            throw new RuntimeException(details);
        }
    }

    /**
     * PUTs the supplied XML element to the supplied {@link URL}.
     * 
     * @param aElement The XML element to PUT to the supplied URL
     * @param aURL The place to PUT the supplied file name
     * @return The XML response from the PUT
     * @throws IOException If there is trouble with the PUT
     */
    public static Element put(URL aURL, Element aElement) throws IOException {
        HttpURLConnection http = connect(aURL, XML_CONTENT_TYPE);
        StringBuilder response = new StringBuilder();
        int responseCode = writeXML(aElement, http);

        if (responseCode == 200) {
            BufferedReader bReader = null;

            try {
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in, CHARSET);
                String line;

                bReader = new BufferedReader(reader);

                while ((line = bReader.readLine()) != null) {
                    response.append(line).append(EOL);
                }
            } finally {
                IOUtils.closeQuietly(bReader);
            }
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
        } catch (ParserConfigurationException details) {
            throw new RuntimeException(details);
        } catch (SAXException details) {
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
    private static HttpURLConnection connect(URL aURL, String aContentType)
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
    private static final int writeXML(Element aNode, HttpURLConnection aConx)
        throws IOException {
        BufferedWriter bWriter = null;

        try {
            OutputStream out = aConx.getOutputStream();

            bWriter = new BufferedWriter(new OutputStreamWriter(out, CHARSET));
            bWriter.write(DOMUtils.toXML(aNode));
        } finally {
            IOUtils.closeQuietly(bWriter);
        }

        return aConx.getResponseCode();
    }
}
