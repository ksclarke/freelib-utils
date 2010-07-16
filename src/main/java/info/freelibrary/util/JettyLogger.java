package info.freelibrary.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class JettyLogger implements org.mortbay.log.Logger {

	private static Logger LOGGER;
	
	public JettyLogger() throws Exception {
		this("org.mortbay.log");
	}
	
	public JettyLogger(String aName) {
		LOGGER = LoggerFactory.getLogger(aName);
	}

	public void debug(String aMessage) {
		LOGGER.debug(aMessage);
	}

	public void debug(String aMessage, Object aDetail) {
		LOGGER.debug(aMessage, aDetail);
	}

	public void debug(String aMessage, Object[] aDetailsList) {
		LOGGER.debug(aMessage, aDetailsList);
	}

	public void debug(String aMessage, Throwable aThrowable) {
		LOGGER.debug(aMessage, aThrowable);
	}

	public void debug(Marker aMarker, String aMessage) {
		LOGGER.debug(aMarker, aMessage);
	}

	public void debug(String aMessage, Object a1stDetail, Object a2ndDetail) {
		LOGGER.debug(aMessage, a1stDetail, a2ndDetail);
	}

	public void debug(Marker aMarker, String aMessage, Object aDetail) {
		LOGGER.debug(aMarker, aMessage, aDetail);
	}

	public void debug(Marker aMarker, String aMessage, Object[] aDetailsList) {
		LOGGER.debug(aMarker, aMessage, aDetailsList);
	}

	public void debug(Marker aMarker, String aMessage, Throwable aThrowable) {
		LOGGER.debug(aMarker, aMessage, aThrowable);
	}

	public void debug(Marker aMarker, String aMessage, Object a1stDetail,
			Object a2ndDetail) {
		LOGGER.debug(aMarker, aMessage, a1stDetail, a2ndDetail);
	}

	public void error(String aMessage) {
		LOGGER.error(aMessage);
	}

	public void error(String aMessage, Object aDetail) {
		LOGGER.error(aMessage, aDetail);
	}

	public void error(String aMessage, Object[] aDetailsList) {
		LOGGER.error(aMessage, aDetailsList);
	}

	public void error(String aMessage, Throwable aThrowable) {
		LOGGER.error(aMessage, aThrowable);
	}

	public void error(Marker aMarker, String aMessage) {
		LOGGER.error(aMarker, aMessage);
	}

	public void error(String aMessage, Object a1stDetail, Object a2ndDetail) {
		LOGGER.error(aMessage, a1stDetail, a2ndDetail);
	}

	public void error(Marker aMarker, String aMessage, Object a1stDetail) {
		LOGGER.error(aMarker, aMessage, a1stDetail);
	}

	public void error(Marker aMarker, String aMessage, Object[] aDetailsList) {
		LOGGER.error(aMarker, aMessage, aDetailsList);
	}

	public void error(Marker aMarker, String aMessage, Throwable aThrowable) {
		LOGGER.error(aMarker, aMessage, aThrowable);
	}

	public void error(Marker aMarker, String aMessage, Object a1stDetail,
			Object a2ndDetail) {
		LOGGER.error(aMarker, aMessage, a1stDetail, a2ndDetail);
	}

	public String getName() {
		return LOGGER.getName();
	}

	public void info(String aMessage) {
		LOGGER.info(aMessage);
	}

	public void info(String aMessage, Object aDetail) {
		LOGGER.info(aMessage, aDetail);
	}

	public void info(String aMessage, Object[] aDetail) {
		LOGGER.info(aMessage, aDetail);
	}

	public void info(String aMessage, Throwable aThrowable) {
		LOGGER.info(aMessage, aThrowable);
	}

	public void info(Marker aMarker, String aMessage) {
		LOGGER.info(aMarker, aMessage);
	}

	public void info(String aMessage, Object a1stDetail, Object a2ndDetail) {
		LOGGER.info(aMessage, a1stDetail, a2ndDetail);
	}

	public void info(Marker aMarker, String aMessage, Object aDetail) {
		LOGGER.info(aMarker, aMessage, aDetail);
	}

	public void info(Marker aMarker, String aMessage, Object[] aDetailsList) {
		LOGGER.info(aMarker, aMessage, aDetailsList);
	}

	public void info(Marker aMarker, String aMessage, Throwable aThrowable) {
		LOGGER.info(aMarker, aMessage, aThrowable);
	}

	public void info(Marker aMarker, String aMessage, Object a1stDetail,
			Object a2ndDetail) {
		LOGGER.info(aMarker, aMessage, a1stDetail, a2ndDetail);
	}

	public boolean isDebugEnabled() {
		return LOGGER.isDebugEnabled();
	}

	public boolean isDebugEnabled(Marker aMarker) {
		return LOGGER.isDebugEnabled(aMarker);
	}

	public boolean isErrorEnabled() {
		return LOGGER.isErrorEnabled();
	}

	public boolean isErrorEnabled(Marker aMarker) {
		return LOGGER.isErrorEnabled(aMarker);
	}

	public boolean isInfoEnabled() {
		return LOGGER.isInfoEnabled();
	}

	public boolean isInfoEnabled(Marker aMarker) {
		return LOGGER.isInfoEnabled(aMarker);
	}

	public boolean isTraceEnabled() {
		return LOGGER.isTraceEnabled();
	}

	public boolean isTraceEnabled(Marker aMarker) {
		return LOGGER.isTraceEnabled(aMarker);
	}

	public boolean isWarnEnabled() {
		return LOGGER.isWarnEnabled();
	}

	public boolean isWarnEnabled(Marker aMarker) {
		return LOGGER.isWarnEnabled(aMarker);
	}

	public void trace(String aMessage) {
		LOGGER.trace(aMessage);
	}

	public void trace(String aMessage, Object aDetail) {
		LOGGER.trace(aMessage, aDetail);
	}

	public void trace(String aMessage, Object[] aDetailsList) {
		LOGGER.trace(aMessage, aDetailsList);
	}

	public void trace(String aMessage, Throwable aThrowable) {
		LOGGER.trace(aMessage, aThrowable);
	}

	public void trace(Marker aMarker, String aMessage) {
		LOGGER.trace(aMarker, aMessage);
	}

	public void trace(String aMessage, Object a1stDetail, Object a2ndDetail) {
		LOGGER.trace(aMessage, a1stDetail, a2ndDetail);
	}

	public void trace(Marker aMarker, String aMessage, Object aDetail) {
		LOGGER.trace(aMarker, aMessage, aDetail);
	}

	public void trace(Marker aMarker, String aMessage, Object[] aDetailsList) {
		LOGGER.trace(aMarker, aMessage, aDetailsList);
	}

	public void trace(Marker aMarker, String aMessage, Throwable aThrowable) {
		LOGGER.trace(aMarker, aMessage, aThrowable);
	}

	public void trace(Marker aMarker, String aMessage, Object a1stDetail,
			Object a2ndDetail) {
		LOGGER.trace(aMarker, aMessage, a1stDetail, a2ndDetail);
	}

	public void warn(String aMessage) {
		LOGGER.warn(aMessage);
	}

	public void warn(String aMessage, Object aDetail) {
		LOGGER.warn(aMessage, aDetail);
	}

	public void warn(String aMessage, Object[] aDetailsList) {
		LOGGER.warn(aMessage, aDetailsList);
	}

	public void warn(String aMessage, Throwable aThrowable) {
		if (aThrowable instanceof RuntimeException
				|| aThrowable instanceof Error) {
			LOGGER.error(aMessage, aThrowable);
		}
		else {
			LOGGER.warn(aMessage, aThrowable);
		}
	}

	public void warn(Marker aMarker, String aMessage) {
		LOGGER.warn(aMarker, aMessage);
	}

	public void warn(String aMessage, Object a1stDetail, Object a2ndDetail) {
		LOGGER.warn(aMessage, a1stDetail, a2ndDetail);
	}

	public void warn(Marker aMarker, String aMessage, Object aDetail) {
		LOGGER.warn(aMarker, aMessage, aDetail);
	}

	public void warn(Marker aMarker, String aMessage, Object[] aDetailsList) {
		LOGGER.warn(aMarker, aMessage, aDetailsList);
	}

	public void warn(Marker aMarker, String aMessage, Throwable aThrowable) {
		LOGGER.warn(aMarker, aMessage, aThrowable);
	}

	public void warn(Marker aMarker, String aMessage, Object a1stDetail,
			Object a2ndDetail) {
		LOGGER.warn(aMarker, aMessage, a1stDetail, a2ndDetail);
	}

	public void setDebugEnabled(boolean aDebugger) {
		warn("setDebugEnabled not implemented", null, null);
	}

	public org.mortbay.log.Logger getLogger(String aName) {
		return new JettyLogger(aName);
	}

}