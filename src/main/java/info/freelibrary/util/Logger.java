
package info.freelibrary.util;

import org.slf4j.Marker;

/**
 * Create a SLF4J logger that is backed by a {@link java.util.ResourceBundle}.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class Logger extends I18nObject implements org.slf4j.Logger {

    private static org.slf4j.Logger LOGGER;

    /**
     * Creates a logger using the supplied class as the name.
     * 
     * @param aClass A class to use as the name of the logger
     */
    Logger(org.slf4j.Logger aLogger) {
        super();
        LOGGER = aLogger;
    }

    /**
     * Creates a logger using the supplied class as the name.
     * 
     * @param aClass A class to use as the name of the logger
     */
    Logger(org.slf4j.Logger aLogger, String aBundleName) {
        super(aBundleName);
        LOGGER = aLogger;
    }

    @Override
    public void debug(String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(getI18n(aMessage));
        } else {
            LOGGER.debug(aMessage);
        }
    }

    @Override
    public void debug(String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(getI18n(aMessage), aDetail);
        } else {
            LOGGER.debug(aMessage, aDetail);
        }
    }

    @Override
    public void debug(String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(getI18n(aMessage), aVarargs);
        } else {
            LOGGER.debug(aMessage, aVarargs);
        }
    }

    @Override
    public void debug(String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(getI18n(aMessage), aThrowable);
        } else {
            LOGGER.debug(aMessage, aThrowable);
        }
    }

    @Override
    public void debug(Marker aMarker, String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(aMarker, getI18n(aMessage));
        } else {
            LOGGER.debug(aMarker, aMessage);
        }
    }

    @Override
    public void debug(String aMessage, Object a1stDetail, Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.debug(aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public void debug(Marker aMarker, String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(aMarker, getI18n(aMessage), aDetail);
        } else {
            LOGGER.debug(aMarker, aMessage, aDetail);
        }
    }

    @Override
    public void debug(Marker aMarker, String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(aMarker, getI18n(aMessage), aVarargs);
        } else {
            LOGGER.debug(aMarker, aMessage, aVarargs);
        }
    }

    @Override
    public void debug(Marker aMarker, String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(aMarker, getI18n(aMessage), aThrowable);
        } else {
            LOGGER.debug(aMarker, aMessage, aThrowable);
        }
    }

    @Override
    public void debug(Marker aMarker, String aMessage, Object a1stDetail,
            Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.debug(aMarker, getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.debug(aMarker, aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public void error(String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(getI18n(aMessage));
        } else {
            LOGGER.error(aMessage);
        }
    }

    @Override
    public void error(String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(getI18n(aMessage), aDetail);
        } else {
            LOGGER.error(aMessage, aDetail);
        }
    }

    @Override
    public void error(String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(getI18n(aMessage), aVarargs);
        } else {
            LOGGER.error(aMessage, aVarargs);
        }
    }

    @Override
    public void error(String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(getI18n(aMessage), aThrowable);
        } else {
            LOGGER.error(aMessage, aThrowable);
        }
    }

    @Override
    public void error(Marker aMarker, String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(aMarker, getI18n(aMessage));
        } else {
            LOGGER.error(aMarker, aMessage);
        }
    }

    @Override
    public void error(String aMessage, Object a1stDetail, Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.error(aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public void error(Marker aMarker, String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(aMarker, getI18n(aMessage), aDetail);
        } else {
            LOGGER.error(aMarker, aMessage, aDetail);
        }
    }

    @Override
    public void error(Marker aMarker, String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(aMarker, getI18n(aMessage), aVarargs);
        } else {
            LOGGER.error(aMarker, aMessage, aVarargs);
        }
    }

    @Override
    public void error(Marker aMarker, String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(aMarker, getI18n(aMessage), aThrowable);
        } else {
            LOGGER.error(aMarker, aMessage, aThrowable);
        }
    }

    @Override
    public void error(Marker aMarker, String aMessage, Object a1stDetail,
            Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.error(aMarker, getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.error(aMarker, aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public String getName() {
        return LOGGER.getName();
    }

    @Override
    public void info(String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(getI18n(aMessage));
        } else {
            LOGGER.info(aMessage);
        }
    }

    @Override
    public void info(String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(getI18n(aMessage), aDetail);
        } else {
            LOGGER.info(aMessage, aDetail);
        }
    }

    @Override
    public void info(String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(getI18n(aMessage), aVarargs);
        } else {
            LOGGER.info(aMessage, aVarargs);
        }
    }

    @Override
    public void info(String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(getI18n(aMessage), aThrowable);
        } else {
            LOGGER.info(aMessage, aThrowable);
        }
    }

    @Override
    public void info(Marker aMarker, String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(aMarker, getI18n(aMessage));
        } else {
            LOGGER.info(aMarker, aMessage);
        }
    }

    @Override
    public void info(String aMessage, Object a1stDetail, Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.info(aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public void info(Marker aMarker, String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(aMarker, getI18n(aMessage), aDetail);
        } else {
            LOGGER.info(aMarker, aMessage, aDetail);
        }
    }

    @Override
    public void info(Marker aMarker, String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(aMarker, getI18n(aMessage), aVarargs);
        } else {
            LOGGER.info(aMarker, aMessage, aVarargs);
        }
    }

    @Override
    public void info(Marker aMarker, String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(aMarker, getI18n(aMessage), aThrowable);
        } else {
            LOGGER.info(aMarker, aMessage, aThrowable);
        }
    }

    @Override
    public void info(Marker aMarker, String aMessage, Object a1stDetail,
            Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.info(aMarker, getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.info(aMarker, aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return LOGGER.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker aMarker) {
        return LOGGER.isDebugEnabled(aMarker);
    }

    @Override
    public boolean isErrorEnabled() {
        return LOGGER.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(Marker aMarker) {
        return LOGGER.isErrorEnabled(aMarker);
    }

    @Override
    public boolean isInfoEnabled() {
        return LOGGER.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker aMarker) {
        return LOGGER.isInfoEnabled(aMarker);
    }

    @Override
    public boolean isTraceEnabled() {
        return LOGGER.isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(Marker aMarker) {
        return LOGGER.isTraceEnabled(aMarker);
    }

    @Override
    public boolean isWarnEnabled() {
        return LOGGER.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker aMarker) {
        return LOGGER.isWarnEnabled(aMarker);
    }

    @Override
    public void trace(String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(getI18n(aMessage));
        } else {
            LOGGER.trace(aMessage);
        }
    }

    @Override
    public void trace(String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(getI18n(aMessage), aDetail);
        } else {
            LOGGER.trace(aMessage, aDetail);
        }
    }

    @Override
    public void trace(String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(getI18n(aMessage), aVarargs);
        } else {
            LOGGER.trace(aMessage, aVarargs);
        }
    }

    @Override
    public void trace(String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(getI18n(aMessage), aThrowable);
        } else {
            LOGGER.trace(aMessage, aThrowable);
        }
    }

    @Override
    public void trace(Marker aMarker, String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(aMarker, getI18n(aMessage));
        } else {
            LOGGER.trace(aMarker, aMessage);
        }
    }

    @Override
    public void trace(String aMessage, Object a1stDetail, Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.trace(aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public void trace(Marker aMarker, String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(aMarker, getI18n(aMessage), aDetail);
        } else {
            LOGGER.trace(aMarker, aMessage, aDetail);
        }
    }

    @Override
    public void trace(Marker aMarker, String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(aMarker, getI18n(aMessage), aVarargs);
        } else {
            LOGGER.trace(aMarker, aMessage, aVarargs);
        }
    }

    @Override
    public void trace(Marker aMarker, String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(aMarker, getI18n(aMessage), aThrowable);
        } else {
            LOGGER.trace(aMarker, aMessage, aThrowable);
        }
    }

    @Override
    public void trace(Marker aMarker, String aMessage, Object a1stDetail,
            Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.trace(aMarker, getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.trace(aMarker, aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public void warn(String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(getI18n(aMessage));
        } else {
            LOGGER.warn(aMessage);
        }
    }

    @Override
    public void warn(String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(getI18n(aMessage), aDetail);
        } else {
            LOGGER.warn(aMessage, aDetail);
        }
    }

    @Override
    public void warn(String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(getI18n(aMessage), aVarargs);
        } else {
            LOGGER.warn(aMessage, aVarargs);
        }
    }

    @Override
    public void warn(String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(getI18n(aMessage), aThrowable);
        } else {
            LOGGER.warn(aMessage, aThrowable);
        }
    }

    @Override
    public void warn(Marker aMarker, String aMessage) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(aMarker, getI18n(aMessage));
        } else {
            LOGGER.warn(aMarker, aMessage);
        }
    }

    @Override
    public void warn(String aMessage, Object a1stDetail, Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.warn(aMessage, a1stDetail, a2ndDetail);
        }
    }

    @Override
    public void warn(Marker aMarker, String aMessage, Object aDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(aMarker, getI18n(aMessage), aDetail);
        } else {
            LOGGER.warn(aMarker, aMessage, aDetail);
        }
    }

    @Override
    public void warn(Marker aMarker, String aMessage, Object... aVarargs) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(aMarker, getI18n(aMessage), aVarargs);
        } else {
            LOGGER.warn(aMarker, aMessage, aVarargs);
        }
    }

    @Override
    public void warn(Marker aMarker, String aMessage, Throwable aThrowable) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(aMarker, getI18n(aMessage), aThrowable);
        } else {
            LOGGER.warn(aMarker, aMessage, aThrowable);
        }
    }

    @Override
    public void warn(Marker aMarker, String aMessage, Object a1stDetail,
            Object a2ndDetail) {
        if (hasI18nKey(aMessage)) {
            LOGGER.warn(aMarker, getI18n(aMessage), a1stDetail, a2ndDetail);
        } else {
            LOGGER.warn(aMarker, aMessage, a1stDetail, a2ndDetail);
        }
    }

}
