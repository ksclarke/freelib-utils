
package info.freelibrary.util;

/**
 * HTTP response code constants.
 */
public final class HTTP {

    /** Accepted HTTP response code. */
    public static final int ACCEPTED = 202;

    /** Already reported HTTP response code. */
    public static final int ALREADY_REPORTED = 208;

    /** Bad gateway error HTTP response code. */
    public static final int BAD_GATEWAY = 502;

    /** Bad request HTTP response code. */
    public static final int BAD_REQUEST = 400;

    /** Client closed connection error HTTP response code. */
    public static final int CLIENT_CLOSED_CONNECTION = 460;

    /** Conflict HTTP response code. */
    public static final int CONFLICT = 409;

    /** Continue HTTP response code. */
    public static final int CONTINUE = 100;

    /** Created HTTP response code. */
    public static final int CREATED = 201;

    /** Early hints HTTP response code. */
    public static final int EARLY_HINTS = 103;

    /** Elastic load balancer unauthorized HTTP response code. */
    public static final int ELB_UNAUTHORIZED = 561;

    /** Expectation failed HTTP response code. */
    public static final int EXPECTATION_FAILED = 417;

    /** Failed dependency HTTP response code. */
    public static final int FAILED_DEPENDENCY = 424;

    /** Forbidden HTTP response code. */
    public static final int FORBIDDEN = 403;

    /** Found HTTP response code. */
    public static final int FOUND = 302;

    /** Gateway timeout error HTTP response code. */
    public static final int GATEWAY_TIMEOUT = 504;

    /** Gone HTTP response code. */
    public static final int GONE = 410;

    /** HTTP version not supported error HTTP response code. */
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;

    /** Instance manipulation used HTTP response code. */
    public static final int IM_USED = 226;

    /** Insufficient storage error HTTP response code. */
    public static final int INSUFFICIENT_STORAGE = 507;

    /** Internal server error HTTP response code. */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /** Length required HTTP response code. */
    public static final int LENGTH_REQUIRED = 411;

    /** Locked HTTP response code. */
    public static final int LOCKED = 423;

    /** Loop detected error HTTP response code. */
    public static final int LOOP_DETECTED = 508;

    /** Method not allowed HTTP response code. */
    public static final int METHOD_NOT_ALLOWED = 405;

    /** Misdirected request HTTP response code. */
    public static final int MISDIRECTED_REQUEST = 421;

    /** Moved permanently HTTP response code. */
    public static final int MOVED_PERMANENTLY = 301;

    /** Multi-status HTTP response code. */
    public static final int MULTI_STATUS = 207;

    /** Multiple choices HTTP response code. */
    public static final int MULTIPLE_CHOICES = 300;

    /** Network authentication required error HTTP response code. */
    public static final int NETWORK_AUTH_REQUIRED = 511;

    /** No content HTTP response code. */
    public static final int NO_CONTENT = 204;

    /** Non-authoritative information HTTP response code. */
    public static final int NONAUTHORITATIVE_INFORMATION = 203;

    /** Not acceptable HTTP response code. */
    public static final int NOT_ACCEPTABLE = 406;

    /** Not extended error HTTP response code. */
    public static final int NOT_EXTENDED = 510;

    /** Not Found HTTP response code. */
    public static final int NOT_FOUND = 404;

    /** Not implemented HTTP response code. */
    public static final int NOT_IMPLEMENTED = 501;

    /** Not modified HTTP response code. */
    public static final int NOT_MODIFIED = 304;

    /** OK HTTP response code. */
    public static final int OK = 200;

    /** Partial content HTTP response code. */
    public static final int PARTIAL_CONTENT = 206;

    /** Payload too large HTTP response code. */
    public static final int PAYLOAD_TOO_LARGE = 413;

    /** Payment required HTTP response code. */
    public static final int PAYMENT_REQUIRED = 402;

    /** Permanent redirect HTTP response code. */
    public static final int PERMANENT_REDIRECT = 308;

    /** Precondition failed HTTP response code. */
    public static final int PRECONDITION_FAILED = 412;

    /** Precondition required HTTP response code. */
    public static final int PRECONDITION_REQUIRED = 428;

    /** Processing HTTP response code. */
    public static final int PROCESSING = 102;

    /** Proxy authentication required HTTP response code. */
    public static final int PROXY_AUTH_REQUIRED = 407;

    /** Range not satisfiable HTTP response code. */
    public static final int RANGE_NOT_SATISFIABLE = 416;

    /** Request header fields too large HTTP response code. */
    public static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;

    /** Request timeout HTTP response code. */
    public static final int REQUEST_TIMEOUT = 408;

    /** Reset content HTTP response code. */
    public static final int RESET_CONTENT = 205;

    /** See other HTTP response code. */
    public static final int SEE_OTHER = 303;

    /** Service unavailable error HTTP response code. */
    public static final int SERVICE_UNAVAILABLE = 503;

    /** Switch proxy HTTP response code. */
    public static final int SWITCH_PROXY = 306;

    /** Switching protocols HTTP response code. */
    public static final int SWITCHING_PROTOCOLS = 101;

    /** I am a teapot HTTP response code. */
    public static final int TEAPOT = 418;

    /** Temporary redirect HTTP response code. */
    public static final int TEMP_REDIRECT = 307;

    /** Too early HTTP response code. */
    public static final int TOO_EARLY = 425;

    /** Too many requests HTTP response code. */
    public static final int TOO_MANY_REQUESTS = 429;

    /** Unauthorized HTTP response code. */
    public static final int UNAUTHORIZED = 401;

    /** Unavailable for legal reasons HTTP response code. */
    public static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451;

    /** Unprocessable entity HTTP response code. */
    public static final int UNPROCESSABLE_ENTITY = 422;

    /** Unsupported media type HTTP response code. */
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    /** Upgrade required HTTP response code. */
    public static final int UPGRADE_REQUIRED = 426;

    /** URI too large HTTP response code. */
    public static final int URI_TOO_LARGE = 414;

    /** Use proxy HTTP response code. */
    public static final int USE_PROXY = 305;

    /** Variant also negotiates error HTTP response code. */
    public static final int VARIANT_ALSO_NEGOTIATES = 506;

    /**
     * Constructor for class of constants.
     */
    private HTTP() {
        // This is intentionally left empty
    }

    /**
     * The Header class provides a collection of constants representing common HTTP header names. These constants can be
     * used to reference standard HTTP headers in a type-safe manner, avoiding hardcoded string literals throughout the
     * codebase.
     * <p>
     * This class includes well-known HTTP headers used in client-server communication such as Content-Type, Accept,
     * Cache-Control, Authorization, and many others. It also includes custom headers often used in specific application
     * configurations or by proxies such as X-Forwarded-* headers.
     * <p>
     * The Header class is immutable and cannot be instantiated.
     */
    public static final class Header {

        /** Content-Type header. */
        public static final String CONTENT_TYPE = "Content-Type";

        /** Content-Length header. */
        public static final String CONTENT_LENGTH = "Content-Length";

        /** Location header. */
        public static final String LOCATION = "Location";

        /** Connection header. */
        public static final String CONNECTION = "Connection";

        /** Accept header. */
        public static final String ACCEPT = "Accept";

        /** Accept-Charset header. */
        public static final String ACCEPT_CHARSET = "Accept-Charset";

        /** Accept-Encoding header. */
        public static final String ACCEPT_ENCODING = "Accept-Encoding";

        /** Accept-Language header. */
        public static final String ACCEPT_LANGUAGE = "Accept-Language";

        /** User-Agent header. */
        public static final String USER_AGENT = "User-Agent";

        /** Host header. */
        public static final String HOST = "Host";

        /** Content-Encoding header. */
        public static final String CONTENT_ENCODING = "Content-Encoding";

        /** Content-Disposition header. */
        public static final String CONTENT_DISPOSITION = "Content-Disposition";

        /** Content-Language header. */
        public static final String CONTENT_LANGUAGE = "Content-Language";

        /** Transfer-Encoding header. */
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";

        /** Date header. */
        public static final String DATE = "Date";

        /** Server header. */
        public static final String SERVER = "Server";

        /** ETag header. */
        public static final String ETAG = "ETag";

        /** Last-Modified header. */
        public static final String LAST_MODIFIED = "Last-Modified";

        /** Cache-Control header. */
        public static final String CACHE_CONTROL = "Cache-Control";

        /** Authorization header. */
        public static final String AUTHORIZATION = "Authorization";

        /** Cookie header. */
        public static final String COOKIE = "Cookie";

        /** Set-Cookie header. */
        public static final String SET_COOKIE = "Set-Cookie";

        /** Referer header. */
        public static final String REFERER = "Referer";

        /** Allow header. */
        public static final String ALLOW = "Allow";

        /** Origin header. */
        public static final String ORIGIN = "Origin";

        /** Access-Control-Allow-Origin header. */
        public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

        /** Access-Control-Allow-Credentials header. */
        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

        /** Access-Control-Allow-Headers header. */
        public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

        /** Access-Control-Allow-Methods header. */
        public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

        /** Access-Control-Expose-Headers header. */
        public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

        /** Access-Control-Max-Age header. */
        public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

        /** Access-Control-Request-Headers header. */
        public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

        /** Access-Control-Request-Method header. */
        public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

        /** Strict-Transport-Security header. */
        public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";

        /** X-Forwarded-For header. */
        public static final String X_FORWARDED_FOR = "X-Forwarded-For";

        /** X-Forwarded-Proto header. */
        public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";

        /** X-Forwarded-Host header. */
        public static final String X_FORWARDED_HOST = "X-Forwarded-Host";

        /** X-Forwarded-Port header. */
        public static final String X_FORWARDED_PORT = "X-Forwarded-Port";

        /** X-Requested-With header. */
        public static final String X_REQUESTED_WITH = "X-Requested-With";

        /** X-Real-IP header. */
        public static final String X_REAL_IP = "X-Real-IP";

        /** X-Forwarded-Server header. */
        public static final String X_FORWARDED_SERVER = "X-Forwarded-Server";

        /** X-Forwarded-SSL header. */
        public static final String X_FORWARDED_SSL = "X-Forwarded-SSL";

        /** X-Forwarded-Prefix header. */
        public static final String X_FORWARDED_PREFIX = "X-Forwarded-Prefix";

        /** X-Forwarded-Context-Path header. */
        public static final String X_FORWARDED_CONTEXT_PATH = "X-Forwarded-Context-Path";

        /** X-Forwarded-Scheme header. */
        public static final String X_FORWARDED_SCHEME = "X-Forwarded-Scheme";

    }

    /** An HTTP method constants class. */
    public static final class Method {

        /** A constant for the CONNECT method. */
        public static final String CONNECT = "Connect";

        /** A constant for the DELETE method. */
        public static final String DELETE = "DELETE";

        /** A constant for the GET method. */
        public static final String GET = "GET";

        /** A constant for the HEAD method. */
        public static final String HEAD = "HEAD";

        /** A constant for the OPTIONS method. */
        public static final String OPTIONS = "OPTIONS";

        /** A constant for the PATCH method. */
        public static final String PATCH = "PATCH";

        /** A constant for the POST method. */
        public static final String POST = "POST";

        /** A constant for the PUT method. */
        public static final String PUT = "PUT";

        /**
         * A private constructor for HTTP method constants.
         */
        private Method() {
            // This is intentionally left blank.
        }
    }
}
