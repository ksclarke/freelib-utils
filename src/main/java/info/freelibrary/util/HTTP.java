
package info.freelibrary.util;

/**
 * HTTP response code constants.
 */
public final class HTTP {

    /** Continue HTTP response code. */
    public static final int CONTINUE = 100;

    /** Switching protocols HTTP response code. */
    public static final int SWITCHING_PROTOCOLS = 101;

    /** Processing HTTP response code. */
    public static final int PROCESSING = 102;

    /** Early hints HTTP response code. */
    public static final int EARLY_HINTS = 103;

    /** OK HTTP response code. */
    public static final int OK = 200;

    /** Created HTTP response code. */
    public static final int CREATED = 201;

    /** Accepted HTTP response code. */
    public static final int ACCEPTED = 202;

    /** Non-authoritative information HTTP response code. */
    @SuppressWarnings("PMD.LongVariable")
    public static final int NONAUTHORITATIVE_INFORMATION = 203;

    /** No content HTTP response code. */
    public static final int NO_CONTENT = 204;

    /** Reset content HTTP response code. */
    public static final int RESET_CONTENT = 205;

    /** Partial content HTTP response code. */
    public static final int PARTIAL_CONTENT = 206;

    /** Multi-status HTTP response code. */
    public static final int MULTI_STATUS = 207;

    /** Already reported HTTP response code. */
    public static final int ALREADY_REPORTED = 208;

    /** Instance manipulation used HTTP response code. */
    public static final int IM_USED = 226;

    /** Multiple choices HTTP response code. */
    public static final int MULTIPLE_CHOICES = 300;

    /** Moved permanently HTTP response code */
    public static final int MOVED_PERMANENTLY = 301;

    /** Found HTTP response code. */
    public static final int FOUND = 302;

    /** See other HTTP response code. */
    public static final int SEE_OTHER = 303;

    /** Not modified HTTP response code. */
    public static final int NOT_MODIFIED = 304;

    /** Use proxy HTTP response code. */
    public static final int USE_PROXY = 305;

    /** Switch proxy HTTP response code. */
    public static final int SWITCH_PROXY = 306;

    /** Temporary redirect HTTP response code. */
    public static final int TEMP_REDIRECT = 307;

    /** Permanent redirect HTTP response code. */
    public static final int PERMANENT_REDIRECT = 308;

    /** Bad request HTTP response code. */
    public static final int BAD_REQUEST = 400;

    /** Unauthorized HTTP response code. */
    public static final int UNAUTHORIZED = 401;

    /** Payment required HTTP response code. */
    public static final int PAYMENT_REQUIRED = 402;

    /** Forbidden HTTP response code. */
    public static final int FORBIDDEN = 403;

    /** Not Found HTTP response code. */
    public static final int NOT_FOUND = 404;

    /** Method not allowed HTTP response code. */
    public static final int METHOD_NOT_ALLOWED = 405;

    /** Not acceptable HTTP response code. */
    public static final int NOT_ACCEPTABLE = 406;

    /** Proxy authentication required HTTP response code. */
    public static final int PROXY_AUTH_REQUIRED = 407;

    /** Request timeout HTTP response code. */
    public static final int REQUEST_TIMEOUT = 408;

    /** Conflict HTTP response code. */
    public static final int CONFLICT = 409;

    /** Gone HTTP response code. */
    public static final int GONE = 410;

    /** Length required HTTP response code. */
    public static final int LENGTH_REQUIRED = 411;

    /** Precondition failed HTTP response code. */
    public static final int PRECONDITION_FAILED = 412;

    /** Payload too large HTTP response code. */
    public static final int PAYLOAD_TOO_LARGE = 413;

    /** URI too large HTTP response code. */
    public static final int URI_TOO_LARGE = 414;

    /** Unsupported media type HTTP response code. */
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    /** Range not satisfiable HTTP response code. */
    public static final int RANGE_NOT_SATISFIABLE = 416;

    /** Expectation failed HTTP response code. */
    public static final int EXPECTATION_FAILED = 417;

    /** I am a teapot HTTP response code. */
    public static final int TEAPOT = 418;

    /** Misdirected request HTTP response code. */
    public static final int MISDIRECTED_REQUEST = 421;

    /** Unprocessable entity HTTP response code. */
    public static final int UNPROCESSABLE_ENTITY = 422;

    /** Locked HTTP response code. */
    public static final int LOCKED = 423;

    /** Failed dependency HTTP response code. */
    public static final int FAILED_DEPENDENCY = 424;

    /** Too early HTTP response code. */
    public static final int TOO_EARLY = 425;

    /** Upgrade required HTTP response code. */
    public static final int UPGRADE_REQUIRED = 426;

    /** Precondition required HTTP response code. */
    public static final int PRECONDITION_REQUIRED = 428;

    /** Too many requests HTTP response code. */
    public static final int TOO_MANY_REQUESTS = 429;

    /** Request header fields too large HTTP response code. */
    @SuppressWarnings("PMD.LongVariable")
    public static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;

    /** Unavailable for legal reasons HTTP response code. */
    @SuppressWarnings("PMD.LongVariable")
    public static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451;

    /** Client closed connection error HTTP response code. */
    public static final int CLIENT_CLOSED_CONNECTION = 460;

    /** Internal server error HTTP response code. */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /** Not implemented HTTP response code. */
    public static final int NOT_IMPLEMENTED = 501;

    /** Bad gateway error HTTP response code. */
    public static final int BAD_GATEWAY = 502;

    /** Service unavailable error HTTP response code. */
    public static final int SERVICE_UNAVAILABLE = 503;

    /** Gateway timeout error HTTP response code. */
    public static final int GATEWAY_TIMEOUT = 504;

    /** HTTP version not supported error HTTP response code. */
    @SuppressWarnings("PMD.LongVariable")
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;

    /** Variant also negotiates error HTTP response code. */
    public static final int VARIANT_ALSO_NEGOTIATES = 506;

    /** Insufficient storage error HTTP response code. */
    public static final int INSUFFICIENT_STORAGE = 507;

    /** Loop detected error HTTP response code. */
    public static final int LOOP_DETECTED = 508;

    /** Not extended error HTTP response code. */
    public static final int NOT_EXTENDED = 510;

    /** Network authentication required error HTTP response code. */
    public static final int NETWORK_AUTH_REQUIRED = 511;

    /** Elastic load balancer unauthorized HTTP response code. */
    public static final int ELB_UNAUTHORIZED = 561;

    /**
     * Constructor for class of constants.
     */
    private HTTP() {
    }
}
