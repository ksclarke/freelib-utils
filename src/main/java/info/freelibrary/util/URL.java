
package info.freelibrary.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.Optional;

/**
 * A URL class intended for non-user supplied URL values. Instead of throwing a checked
 * {@code java.net.MalformedURLException}, it throws an unchecked {@code info.freelibrary.util.MalformedUrlException}.
 */
public class URL {

    private java.net.URL myURL;

    /**
     * Creates a URL from the standard Java URL class.
     *
     * @param aURL A standard Java URL
     */
    public URL(final java.net.URL aURL) {
        this(aURL.toExternalForm());
    }

    /**
     * Creates a URL that throws a unchecked malformed URL exception is a bad string value is passed.
     *
     * @param aString A string representation of a URL
     * @throws MalformedUrlException An unchecked malformed URL exception
     */
    public URL(final String aString) throws MalformedUrlException {
        try {
            myURL = new java.net.URL(aString);
        } catch (final MalformedURLException details) {
            throw new MalformedUrlException(details);
        }
    }

    /**
     * Creates a URL from the supplied protocol, host, and file that throws an unchecked malformed URL exception if any
     * of those values are bad.
     *
     * @param aProtocol A URL protocol
     * @param aHost A URL host
     * @param aFile A URL file
     */
    public URL(final String aProtocol, final String aHost, final String aFile) {
        try {
            myURL = new java.net.URL(aProtocol, aHost, aFile);
        } catch (final MalformedURLException details) {
            throw new MalformedUrlException(details);
        }
    }

    /**
     * Gets the optional query part of this URL. Unlike {@code java.net.URL#getQuery()}, this returns an
     * {@code java.util.Optional} instead of null if no query exists.
     *
     * @return The optional query part of the URL
     */
    public Optional<String> getQuery() {
        return Optional.ofNullable(myURL.getQuery());
    }

    /**
     * Gets the path part of this URL. This copies {@code java.net.URL#getPath()} in returning an empty string when
     * there is no value.
     *
     * @return The path part of this URL, or an empty string if one does not exist
     */
    public String getPath() {
        return myURL.getPath();
    }

    /**
     * Gets the userInfo part of this URL.
     *
     * @return The optional user info
     */
    public Optional<String> getUserInfo() {
        return Optional.ofNullable(myURL.getUserInfo());
    }

    /**
     * Gets the authority part of this URL.
     *
     * @return The authority part of this URL
     */
    public String getAuthority() {
        return myURL.getAuthority();
    }

    /**
     * Gets the port number of this URL.
     *
     * @return The port number, or -1 if the port is not set
     */
    public int getPort() {
        return myURL.getPort();
    }

    /**
     * Gets the default port number of the protocol associated with this URL. If the URL scheme or the URLStreamHandler
     * for the URL do not define a default port number, then -1 is returned.
     *
     * @return The default port number of the protocol associated with this URL, or -1 if there is no default value
     */
    public int getDefaultPort() {
        return myURL.getDefaultPort();
    }

    /**
     * Gets the protocol name of this URL.
     *
     * @return The protocol name of this URL
     */
    public String getProtocol() {
        return myURL.getProtocol();
    }

    /**
     * Gets the host name of this URL, if applicable. The format of the host conforms to RFC 2732, i.e. for a literal
     * IPv6 address, this method will return the IPv6 address enclosed in square brackets ('[' and ']').
     *
     * @return The host name of this URL
     */
    public String getHost() {
        return myURL.getHost();
    }

    /**
     * Gets the file name of this URL. The returned file portion will be the same as getPath(), plus the concatenation
     * of the value of getQuery(), if any. If there is no query portion, this method and getPath() will return identical
     * results.
     *
     * @return The file from this URL or an empty string if the URL doesn't have a file
     */
    public String getFile() {
        return myURL.getFile();
    }

    /**
     * Gets the anchor (also known as the "reference") of this URL.
     *
     * @return The optional anchor (also known as the "reference") of this URL
     */
    public Optional<String> getRef() {
        return Optional.ofNullable(myURL.getRef());
    }

    @Override
    public boolean equals(final Object aObject) {
        if (aObject != null && getClass().getName().equals(aObject.getClass().getName())) {
            return toExternalForm().equals(((URL) aObject).toExternalForm());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return myURL.toExternalForm().hashCode();
    }

    /**
     * Compares two URLs, excluding the fragment component.
     * <p>
     * Returns true if this URL and the other argument are equal without taking the fragment component into
     * consideration.
     * </p>
     *
     * @param aURL A URL to compare
     * @return True if the supplied URL is equal without taking the fragment component into account
     */
    public boolean sameFile​(final URL aURL) {
        return myURL.sameFile(aURL.toURL());
    }

    /**
     * Converts this URL into a standard Java URL. This throws an unchecked
     * {@code info.freelibrary.util.MalformedUrlException} if this URL cannot be turned into a standard Java URL, but
     * this shouldn't be able to happen since this class is just a wrapper for the standard Java URL class.
     *
     * @return A standard Java URL
     * @throws MalformedUrlException If this URL can't be turned into a standard Java URL
     */
    public java.net.URL toURL() throws MalformedUrlException {
        try {
            return new java.net.URL(myURL.toExternalForm());
        } catch (final MalformedURLException details) {
            throw new MalformedUrlException(details);
        }
    }

    /**
     * Constructs a string representation of this URL. The string is created by calling the toExternalForm method of the
     * stream protocol handler for this object.
     *
     * @return A string representation of this URL
     */
    @Override
    public String toString() {
        return myURL.toString();
    }

    /**
     * Constructs a string representation of this URL. The string is created by calling the toExternalForm method of the
     * stream protocol handler for this object.
     *
     * @return A string representation of this URL
     */
    public String toExternalForm() {
        return myURL.toExternalForm();
    }

    /**
     * Returns a URI equivalent to this URL. This method functions in the same way as new URI (this.toString()).
     * <p>
     * Note, any URL instance that complies with RFC 2396 can be converted to a URI. However, some URLs that are not
     * strictly in compliance can not be converted to a URI.
     * </p>
     *
     * @return The URI form of this URL
     * @throws IllegalArgumentException If the URI isn't a valid URI
     */
    public URI toURI() throws IllegalArgumentException {
        try {
            return myURL.toURI();
        } catch (final URISyntaxException details) {
            throw new IllegalArgumentException(details);
        }
    }

    /**
     * Returns a URLConnection instance that represents a connection to the remote object referred to by the URL.
     * <p>
     * A new instance of URLConnection is created every time when invoking the URLStreamHandler.openConnection(URL)
     * method of the protocol handler for this URL.
     * </p>
     * <p>
     * It should be noted that a URLConnection instance does not establish the actual network connection on creation.
     * This will happen only when calling URLConnection.connect().
     * </p>
     * <p>
     * If for the URL's protocol (such as HTTP or JAR), there exists a public, specialized URLConnection subclass
     * belonging to one of the following packages or one of their subpackages: java.lang, java.io, java.util, java.net,
     * the connection returned will be of that subclass. For example, for HTTP an HttpURLConnection will be returned,
     * and for JAR a JarURLConnection will be returned.
     * </p>
     *
     * @return A URL connection from this URL
     * @throws IOException If the connection cannot be opened
     */
    public URLConnection openConnection() throws IOException {
        return myURL.openConnection();
    }

    /**
     * Same as openConnection(), except that the connection will be made through the specified proxy; Protocol handlers
     * that do not support proxing will ignore the proxy parameter and make a normal connection. Invoking this method
     * preempts the system's default ProxySelector settings.
     *
     * @param aProxy A proxy through which to open the connection
     * @return A URL connection
     * @throws IOException If the connection cannot be opened
     * @throws SecurityException If a security manager is present and the caller doesn't have permission to connect to
     *         the proxy.
     * @throws IllegalArgumentException If proxy is null, or the proxy has the wrong type
     * @throws UnsupportedOperationException If the subclass that implements the protocol handler doesn't support this
     *         method.
     */
    public URLConnection openConnection​(final Proxy aProxy)
            throws IOException, SecurityException, IllegalArgumentException, UnsupportedOperationException {
        return myURL.openConnection(aProxy);
    }

    /**
     * Opens a connection to this URL and returns an InputStream for reading from that connection. This method is a
     * shorthand for: <code>openConnection().getInputStream()</code>.
     *
     * @return An open stream
     * @throws IOException If the input stream can not be opened
     */
    public final InputStream openStream() throws IOException {
        return myURL.openStream();
    }
}
