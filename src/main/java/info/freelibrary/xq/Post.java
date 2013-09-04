/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.xq;

import info.freelibrary.util.DOMUtils;
import info.freelibrary.util.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
public class Post {

    private static final String CONTENT_TYPE = "application/xml; charset=utf-8";

    private static final String EOL = System.getProperty("line.separator");

    private static final String CHARSET = "UTF-8";

    /**
     * POSTs the supplied XML element to the supplied URL.
     * 
     * @param aURL A URL to which to POST the supplied XML element
     * @param aNode An XML element to POST to the supplied URL
     * @return An XML result of the POST
     * @throws IOException If there is trouble POSTing to the URL
     */
    public static final Element post(URL aURL, Element aNode)
        throws IOException {
        StringBuilder response = new StringBuilder();
        HttpURLConnection http = connect(aURL);
        int responseCode = write(aNode, http);

        if (responseCode == 200) {
            BufferedReader bReader = null;

            try {
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in, CHARSET);
                String line;

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
    private static final HttpURLConnection connect(URL aHttpURL)
        throws IOException {
        HttpURLConnection http = (HttpURLConnection) aHttpURL.openConnection();

        // Set this to a POST
        http.setDoInput(true);
        http.setDoOutput(true);

        // Properly set content type so receiving applications knows what
        // to expect (some won't process it otherwise)
        http.setRequestProperty("content-type", CONTENT_TYPE);

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
    private static final int write(Element aNode, HttpURLConnection aHttpConn)
        throws IOException {
        BufferedWriter bWriter = null;

        try {
            OutputStream out = aHttpConn.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(out, CHARSET);

            bWriter = new BufferedWriter(writer);
            bWriter.write(DOMUtils.toXML(aNode));
        } finally {
            IOUtils.closeQuietly(bWriter);
        }

        return aHttpConn.getResponseCode();
    }
}
