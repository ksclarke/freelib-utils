
package info.freelibrary.util;

import info.freelibrary.util.warnings.PMD;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.slf4j.Marker;

import static info.freelibrary.util.Constants.COLON;
import static info.freelibrary.util.Constants.SPACE;

/**
 * Creates a SLF4J logger that is backed by a {@link java.util.ResourceBundle}.
 */
@SuppressWarnings({ PMD.TOO_MANY_METHODS, PMD.EXCESSIVE_PUBLIC_COUNT, PMD.CYCLOMATIC_COMPLEXITY })
public class Logger extends I18nObject implements org.slf4j.Logger {

    /**
     * An end of line regular expression pattern in string form.
     */
    private static final String EOL_RE = "(\\n|\\r|\\r\\n)";

    /**
     * A constant representing line number.
     */
    private static final String LINE_NUM = "line";

    /**
     * A wrapped SLF4J logger.
     */
    private final org.slf4j.Logger myLogger;

    /**
     * Creates a logger using the supplied class as the name.
     *
     * @param aLogger A SLF4J logger to wrap
     */
    Logger(final org.slf4j.Logger aLogger) {
        super();
        myLogger = aLogger;
    }

    /**
     * Creates a logger using the supplied class as the name.
     *
     * @param aLogger A SLF4J logger to wrap
     * @param aBundleName A resource bundle name to use with the logger
     */
    Logger(final org.slf4j.Logger aLogger, final String aBundleName) {
        super(aBundleName);
        myLogger = aLogger;
    }

    @Override
    public void debug(final Marker aMarker, final String aMessage) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.debug(aMarker, updateMessage(getI18n(aMessage)));
                } else {
                    myLogger.debug(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void debug(final Marker aMarker, final String aMessage, final Object aDetail) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    final String message = updateMessage(aDetail.toString());
                    myLogger.debug(aMarker, updateMessage(getI18n(aMessage)), message);
                } else {
                    myLogger.debug(aMarker, updateMessage(aMessage), updateMessage(aDetail.toString()));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void debug(final Marker aMarker, final String aMessage, final Object... aDetails) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final Object[] details = new String[aDetails.length];

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                for (int index = 0; index < aDetails.length; index++) {
                    details[index] = updateMessage(aDetails[index].toString());
                }

                if (hasI18nKey(aMessage)) {
                    myLogger.debug(aMarker, updateMessage(getI18n(aMessage)), details);
                } else {
                    myLogger.debug(aMarker, updateMessage(aMessage), details);
                }

                clearMarker();
            }
        }
    }

    @Override
    public void debug(final Marker aMarker, final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail1 = a1stDetail.toString();
                final String detail2 = a2ndDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.debug(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail1),
                            updateMessage(detail2));
                } else {
                    myLogger.debug(aMarker, updateMessage(aMessage), updateMessage(detail1), updateMessage(detail2));
                }

                clearMarker();
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void debug(final Marker aMarker, final String aMessage, final Throwable aThrowable) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.debug(aMarker, updateMessage(getI18n(aMessage)), aThrowable);
                    } else {
                        myLogger.debug(aMarker, updateMessage(getI18n(aMessage)));
                    }
                } else if (aThrowable != null) {
                    myLogger.debug(aMarker, updateMessage(aMessage), aThrowable);
                } else {
                    myLogger.debug(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void debug(final String aMessage) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.debug(getI18n(aMessage));
                } else {
                    myLogger.debug(aMessage);
                }
            }
        }
    }

    @Override
    public void debug(final String aMessage, final Object aDetail) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.debug(getI18n(aMessage), aDetail);
                } else {
                    myLogger.debug(aMessage, aDetail);
                }
            }
        }
    }

    @Override
    public void debug(final String aMessage, final Object... aDetails) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.debug(getI18n(aMessage), aDetails);
                } else {
                    myLogger.debug(aMessage, aDetails);
                }
            }
        }
    }

    @Override
    public void debug(final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.debug(getI18n(aMessage), a1stDetail, a2ndDetail);
                } else {
                    myLogger.debug(aMessage, a1stDetail, a2ndDetail);
                }
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void debug(final String aMessage, final Throwable aThrowable) {
        if (isDebugEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.debug(getI18n(aMessage), aThrowable);
                    } else {
                        myLogger.debug(getI18n(aMessage));
                    }
                } else if (aThrowable != null) {
                    myLogger.debug(aMessage, aThrowable);
                } else {
                    myLogger.debug(aMessage);
                }
            }
        }
    }

    @Override
    public void error(final Marker aMarker, final String aMessage) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.error(aMarker, updateMessage(getI18n(aMessage)));
                } else {
                    myLogger.error(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void error(final Marker aMarker, final String aMessage, final Object aDetail) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail = aDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.error(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail));
                } else {
                    myLogger.error(aMarker, updateMessage(aMessage), updateMessage(detail));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void error(final Marker aMarker, final String aMessage, final Object... aDetails) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final Object[] details = new String[aDetails.length];

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                for (int index = 0; index < details.length; index++) {
                    details[index] = updateMessage(aDetails[index].toString());
                }

                if (hasI18nKey(aMessage)) {
                    myLogger.error(aMarker, updateMessage(getI18n(aMessage)), details);
                } else {
                    myLogger.error(aMarker, updateMessage(aMessage), details);
                }

                clearMarker();
            }
        }
    }

    @Override
    public void error(final Marker aMarker, final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail1 = a1stDetail.toString();
                final String detail2 = a2ndDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.error(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail1),
                            updateMessage(detail2));
                } else {
                    myLogger.error(aMarker, updateMessage(aMessage), updateMessage(detail1), updateMessage(detail2));
                }

                clearMarker();
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void error(final Marker aMarker, final String aMessage, final Throwable aThrowable) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.error(aMarker, updateMessage(getI18n(aMessage)), aThrowable);
                    } else {
                        myLogger.error(aMarker, updateMessage(getI18n(aMessage)));
                    }
                } else if (aThrowable != null) {
                    myLogger.error(aMarker, updateMessage(aMessage), aThrowable);
                } else {
                    myLogger.error(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void error(final String aMessage) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.error(getI18n(aMessage));
                } else {
                    myLogger.error(aMessage);
                }
            }
        }
    }

    @Override
    public void error(final String aMessage, final Object aDetail) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.error(getI18n(aMessage), aDetail);
                } else {
                    myLogger.error(aMessage, aDetail);
                }
            }
        }
    }

    @Override
    public void error(final String aMessage, final Object... aDetails) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.error(getI18n(aMessage), aDetails);
                } else {
                    myLogger.error(aMessage, aDetails);
                }
            }
        }
    }

    @Override
    public void error(final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.error(getI18n(aMessage), a1stDetail, a2ndDetail);
                } else {
                    myLogger.error(aMessage, a1stDetail, a2ndDetail);
                }
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void error(final String aMessage, final Throwable aThrowable) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.error(getI18n(aMessage), aThrowable);
                    } else {
                        myLogger.error(getI18n(aMessage));
                    }
                } else if (aThrowable != null) {
                    myLogger.error(aMessage, aThrowable);
                } else {
                    myLogger.error(aMessage);
                }
            }
        }
    }

    /**
     * A convenience method that uses an argument pattern with Throwable first.
     *
     * @param aThrowable A throwable exception
     * @param aMessage A message with information about the exception
     */
    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    public void error(final Throwable aThrowable, final String aMessage) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.error(getI18n(aMessage), aThrowable);
                    } else {
                        myLogger.error(getI18n(aMessage));
                    }
                } else if (aThrowable != null) {
                    myLogger.error(aMessage, aThrowable);
                } else {
                    myLogger.error(aMessage);
                }
            }
        }
    }

    /**
     * A convenience method that uses an argument pattern with Throwable first.
     *
     * @param aThrowable A throwable exception
     * @param aMessage A message with information about the exception
     * @param aVarargs Additional details about the exception being thrown
     */
    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    public void error(final Throwable aThrowable, final String aMessage, final Object... aVarargs) {
        if (isErrorEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.error(getI18n(aMessage, aVarargs), aThrowable);
                    } else {
                        myLogger.error(getI18n(aMessage, aVarargs));
                    }
                } else if (aThrowable != null) {
                    if (aVarargs.length == 0) {
                        myLogger.error(aMessage, aThrowable);
                    } else {
                        myLogger.error(StringUtils.format(aMessage, aVarargs), aThrowable);
                    }
                } else if (aVarargs.length == 0) {
                    myLogger.error(aMessage);
                } else {
                    myLogger.error(aMessage, aVarargs);
                }
            }
        }
    }

    /**
     * Gets the internal logger that this logger decorates. This allows casting it to the actual logging implementation
     * so that native methods, etc., can be called.
     *
     * @return An underlying logger
     */
    public org.slf4j.Logger getLoggerImpl() {
        return myLogger;
    }

    /**
     * Gets a message from the logger's backing resource bundle if what's passed in is a message key; if it's not then
     * what's passed in is, itself, returned. If what's passed in is the same thing as what's returned, any additional
     * details passed in are ignored.
     *
     * @param aMarker A marker than can affect logging behavior
     * @param aMessage A message to check against the backing resource bundle
     * @param aDetails An array of additional details
     * @return A message value (potentially from the backing resource bundle)
     */
    public String getMessage(final Marker aMarker, final String aMessage, final Object... aDetails) {
        final String[] details = new String[aDetails.length];
        final String message;

        addMarker(aMarker);

        for (int index = 0; index < details.length; index++) {
            details[index] = updateMessage(aDetails[index].toString());
        }

        if (hasI18nKey(aMessage)) {
            message = updateMessage(getI18n(aMessage, details));
        } else if (details.length == 0) {
            message = updateMessage(aMessage);
        } else {
            message = updateMessage(StringUtils.format(aMessage, details));
        }

        clearMarker();

        return message;
    }

    /**
     * Gets a message from the logger's backing resource bundle if what's passed in is a message key; if it's not then
     * what's passed in is, itself, returned. If what's passed in is the same thing as what's returned, any additional
     * details passed in are ignored.
     *
     * @param aMessage A message to check against the backing resource bundle
     * @param aDetails An array of additional details
     * @return A message value (potentially from the backing resource bundle)
     */
    public String getMessage(final String aMessage, final Object... aDetails) {
        if (hasI18nKey(aMessage)) {
            return getI18n(aMessage, aDetails);
        }

        if (aDetails.length == 0) {
            return aMessage;
        }

        return StringUtils.format(aMessage, aDetails);
    }

    @Override
    public String getName() {
        return myLogger.getName();
    }

    @Override
    public void info(final Marker aMarker, final String aMessage) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.info(aMarker, updateMessage(getI18n(aMessage)));
                } else {
                    myLogger.info(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void info(final Marker aMarker, final String aMessage, final Object aDetail) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail = aDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.info(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail));
                } else {
                    myLogger.info(aMarker, updateMessage(aMessage), updateMessage(detail));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void info(final Marker aMarker, final String aMessage, final Object... aDetails) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final Object[] details = new String[aDetails.length];

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                for (int index = 0; index < details.length; index++) {
                    details[index] = updateMessage(aDetails[index].toString());
                }

                if (hasI18nKey(aMessage)) {
                    myLogger.info(aMarker, updateMessage(getI18n(aMessage)), details);
                } else {
                    myLogger.info(aMarker, updateMessage(aMessage), details);
                }

                clearMarker();
            }
        }
    }

    @Override
    public void info(final Marker aMarker, final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail1 = a1stDetail.toString();
                final String detail2 = a2ndDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.info(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail1),
                            updateMessage(detail2));
                } else {
                    myLogger.info(aMarker, updateMessage(aMessage), updateMessage(detail1), updateMessage(detail2));
                }

                clearMarker();
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void info(final Marker aMarker, final String aMessage, final Throwable aThrowable) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.info(aMarker, updateMessage(getI18n(aMessage)), aThrowable);
                    } else {
                        myLogger.info(aMarker, updateMessage(getI18n(aMessage)));
                    }
                } else if (aThrowable != null) {
                    myLogger.info(aMarker, updateMessage(aMessage), aThrowable);
                } else {
                    myLogger.info(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void info(final String aMessage) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.info(getI18n(aMessage));
                } else {
                    myLogger.info(aMessage);
                }
            }
        }
    }

    @Override
    public void info(final String aMessage, final Object aDetail) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.info(getI18n(aMessage), aDetail);
                } else {
                    myLogger.info(aMessage, aDetail);
                }
            }
        }
    }

    @Override
    public void info(final String aMessage, final Object... aDetails) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.info(getI18n(aMessage), aDetails);
                } else {
                    myLogger.info(aMessage, aDetails);
                }
            }
        }
    }

    @Override
    public void info(final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.info(getI18n(aMessage), a1stDetail, a2ndDetail);
                } else {
                    myLogger.info(aMessage, a1stDetail, a2ndDetail);
                }
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void info(final String aMessage, final Throwable aThrowable) {
        if (isInfoEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.info(getI18n(aMessage), aThrowable);
                    } else {
                        myLogger.info(getI18n(aMessage));
                    }
                } else if (aThrowable != null) {
                    myLogger.info(aMessage, aThrowable);
                } else {
                    myLogger.info(aMessage);
                }
            }
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return myLogger.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(final Marker aMarker) {
        return myLogger.isDebugEnabled(aMarker);
    }

    @Override
    public boolean isErrorEnabled() {
        return myLogger.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(final Marker aMarker) {
        return myLogger.isErrorEnabled(aMarker);
    }

    @Override
    public boolean isInfoEnabled() {
        return myLogger.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(final Marker aMarker) {
        return myLogger.isInfoEnabled(aMarker);
    }

    @Override
    public boolean isTraceEnabled() {
        return myLogger.isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(final Marker aMarker) {
        return myLogger.isTraceEnabled(aMarker);
    }

    @Override
    public boolean isWarnEnabled() {
        return myLogger.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(final Marker aMarker) {
        return myLogger.isWarnEnabled(aMarker);
    }

    @Override
    public void trace(final Marker aMarker, final String aMessage) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.trace(aMarker, updateMessage(getI18n(aMessage)));
                } else {
                    myLogger.trace(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void trace(final Marker aMarker, final String aMessage, final Object aDetail) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail = aDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.trace(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail));
                } else {
                    myLogger.trace(aMarker, updateMessage(aMessage), updateMessage(detail));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void trace(final Marker aMarker, final String aMessage, final Object... aDetails) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final Object[] details = new String[aDetails.length];

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                for (int index = 0; index < details.length; index++) {
                    details[index] = updateMessage(aDetails[index].toString());
                }

                if (hasI18nKey(aMessage)) {
                    myLogger.trace(aMarker, updateMessage(getI18n(aMessage)), details);
                } else {
                    myLogger.trace(aMarker, updateMessage(aMessage), details);
                }

                clearMarker();
            }
        }
    }

    @Override
    public void trace(final Marker aMarker, final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail1 = a1stDetail.toString();
                final String detail2 = a2ndDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.trace(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail1),
                            updateMessage(detail2));
                } else {
                    myLogger.trace(aMarker, updateMessage(aMessage), updateMessage(detail1), updateMessage(detail2));
                }

                clearMarker();
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void trace(final Marker aMarker, final String aMessage, final Throwable aThrowable) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.trace(aMarker, updateMessage(getI18n(aMessage)), aThrowable);
                    } else {
                        myLogger.trace(aMarker, updateMessage(getI18n(aMessage)));
                    }
                } else if (aThrowable != null) {
                    myLogger.trace(aMarker, updateMessage(aMessage), aThrowable);
                } else {
                    myLogger.trace(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void trace(final String aMessage) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.trace(getI18n(aMessage));
                } else {
                    myLogger.trace(aMessage);
                }
            }
        }
    }

    @Override
    public void trace(final String aMessage, final Object aDetail) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.trace(getI18n(aMessage), aDetail);
                } else {
                    myLogger.trace(aMessage, aDetail);
                }
            }
        }
    }

    @Override
    public void trace(final String aMessage, final Object... aDetails) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.trace(getI18n(aMessage), aDetails);
                } else {
                    myLogger.trace(aMessage, aDetails);
                }
            }
        }
    }

    @Override
    public void trace(final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.trace(getI18n(aMessage), a1stDetail, a2ndDetail);
                } else {
                    myLogger.trace(aMessage, a1stDetail, a2ndDetail);
                }
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void trace(final String aMessage, final Throwable aThrowable) {
        if (isTraceEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.trace(getI18n(aMessage), aThrowable);
                    } else {
                        myLogger.trace(getI18n(aMessage));
                    }
                } else if (aThrowable != null) {
                    myLogger.trace(aMessage, aThrowable);
                } else {
                    myLogger.trace(aMessage);
                }
            }
        }
    }

    @Override
    public void warn(final Marker aMarker, final String aMessage) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.warn(aMarker, updateMessage(getI18n(aMessage)));
                } else {
                    myLogger.warn(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void warn(final Marker aMarker, final String aMessage, final Object aDetail) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail = aDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.warn(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail));
                } else {
                    myLogger.warn(aMarker, updateMessage(aMessage), updateMessage(detail));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void warn(final Marker aMarker, final String aMessage, final Object... aDetails) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final Object[] details = new String[aDetails.length];

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                for (int index = 0; index < details.length; index++) {
                    details[index] = updateMessage(aDetails[index].toString());
                }

                if (hasI18nKey(aMessage)) {
                    myLogger.warn(aMarker, updateMessage(getI18n(aMessage)), details);
                } else {
                    myLogger.warn(aMarker, updateMessage(aMessage), details);
                }

                clearMarker();
            }
        }
    }

    @Override
    public void warn(final Marker aMarker, final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                final String detail1 = a1stDetail.toString();
                final String detail2 = a2ndDetail.toString();

                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    myLogger.warn(aMarker, updateMessage(getI18n(aMessage)), updateMessage(detail1),
                            updateMessage(detail2));
                } else {
                    myLogger.warn(aMarker, updateMessage(aMessage), updateMessage(detail1), updateMessage(detail2));
                }

                clearMarker();
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void warn(final Marker aMarker, final String aMessage, final Throwable aThrowable) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                // We can output different types of EOL based on marker
                addMarker(aMarker);

                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.warn(aMarker, updateMessage(getI18n(aMessage)), aThrowable);
                    } else {
                        myLogger.warn(aMarker, updateMessage(getI18n(aMessage)));
                    }
                } else if (aThrowable != null) {
                    myLogger.warn(aMarker, updateMessage(aMessage), aThrowable);
                } else {
                    myLogger.warn(aMarker, updateMessage(aMessage));
                }

                clearMarker();
            }
        }
    }

    @Override
    public void warn(final String aMessage) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.warn(getI18n(aMessage));
                } else {
                    myLogger.warn(aMessage);
                }
            }
        }
    }

    @Override
    public void warn(final String aMessage, final Object aDetail) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.warn(getI18n(aMessage), aDetail);
                } else {
                    myLogger.warn(aMessage, aDetail);
                }
            }
        }
    }

    @Override
    public void warn(final String aMessage, final Object... aDetails) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.warn(getI18n(aMessage), aDetails);
                } else {
                    myLogger.warn(aMessage, aDetails);
                }
            }
        }
    }

    @Override
    public void warn(final String aMessage, final Object a1stDetail, final Object a2ndDetail) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    myLogger.warn(getI18n(aMessage), a1stDetail, a2ndDetail);
                } else {
                    myLogger.warn(aMessage, a1stDetail, a2ndDetail);
                }
            }
        }
    }

    @SuppressWarnings({ PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    @Override
    public void warn(final String aMessage, final Throwable aThrowable) {
        if (isWarnEnabled()) {
            try (@SuppressWarnings(PMD.UNUSED_LOCAL_VARIABLE)
            MDCCloseable closeable = setLineNumber()) {
                if (hasI18nKey(aMessage)) {
                    if (aThrowable != null) {
                        myLogger.warn(getI18n(aMessage), aThrowable);
                    } else {
                        myLogger.warn(getI18n(aMessage));
                    }
                } else if (aThrowable != null) {
                    myLogger.warn(aMessage, aThrowable);
                } else {
                    myLogger.warn(aMessage);
                }
            }
        }
    }

    /**
     * Adds a marker that controls how the EOL is handled.
     *
     * @param aMarker A marker possibly containing the desired EOL handling
     */
    private void addMarker(final Marker aMarker) {
        if (aMarker.contains(LoggerMarker.EOL_TO_SPACE)) {
            MDC.put(LoggerMarker.EOL_TO_SPACE, Boolean.TRUE.toString());
        } else if (aMarker.contains(LoggerMarker.EOL_TO_CRLF)) {
            MDC.put(LoggerMarker.EOL_TO_CRLF, Boolean.TRUE.toString());
        } else if (aMarker.contains(LoggerMarker.EOL_TO_CR)) {
            MDC.put(LoggerMarker.EOL_TO_CR, Boolean.TRUE.toString());
        } else if (aMarker.contains(LoggerMarker.EOL_TO_LF)) {
            MDC.put(LoggerMarker.EOL_TO_LF, Boolean.TRUE.toString());
        }
    }

    /**
     * Clears the logger's marker.
     */
    private void clearMarker() {
        if (MDC.get(LoggerMarker.EOL_TO_SPACE) != null) {
            MDC.remove(LoggerMarker.EOL_TO_SPACE);
        } else if (MDC.get(LoggerMarker.EOL_TO_CRLF) != null) {
            MDC.remove(LoggerMarker.EOL_TO_CRLF);
        } else if (MDC.get(LoggerMarker.EOL_TO_LF) != null) {
            MDC.remove(LoggerMarker.EOL_TO_LF);
        } else if (MDC.get(LoggerMarker.EOL_TO_CR) != null) {
            MDC.remove(LoggerMarker.EOL_TO_CR);
        }
    }

    /**
     * Sets the line number if debugging is enabled. This is a legacy option; one could also just configure different
     * appenders/encoders in the Logback configuration file.
     *
     * @return A handle that can remove the line number after it's no longer needed
     */
    private MDCCloseable setLineNumber() {
        if (isDebugEnabled()) {
            final int lineNum = Thread.currentThread().getStackTrace()[3].getLineNumber();
            return MDC.putCloseable(LINE_NUM, COLON + Integer.toString(lineNum));
        }

        return null; // This should not throw a NPE... at least not in the JVMs I tested.
    }

    /**
     * Updates the supplied message.
     *
     * @param aMessage A message to update
     * @return An updated message
     */
    private String updateMessage(final String aMessage) {
        if (MDC.get(LoggerMarker.EOL_TO_SPACE) != null) {
            return aMessage.replaceAll(EOL_RE, SPACE);
        }

        if (MDC.get(LoggerMarker.EOL_TO_CRLF) != null) {
            return aMessage.replaceAll(EOL_RE, "\r\n");
        }

        if (MDC.get(LoggerMarker.EOL_TO_CR) != null) {
            return aMessage.replaceAll(EOL_RE, "\r");
        }

        if (MDC.get(LoggerMarker.EOL_TO_LF) != null) {
            return aMessage.replaceAll(EOL_RE, "\n");
        }

        return aMessage;
    }
}
