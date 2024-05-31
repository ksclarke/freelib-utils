
package info.freelibrary.util;

import info.freelibrary.util.warnings.PMD;

/**
 * HTTP response code constants.
 */
@SuppressWarnings({ PMD.AVOID_DUPLICATE_LITERALS, PMD.LONG_VARIABLE })
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
}
