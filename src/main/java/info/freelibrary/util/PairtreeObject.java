package info.freelibrary.util;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairtreeObject extends File {

	/**
	 * The <code>serialVersionUID</code> for a <code>PairtreeFile</code>.
	 */
	private static final long serialVersionUID = 5022790117870380626L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PairtreeObject.class);

	private static final XMLResourceBundle BUNDLE = (XMLResourceBundle) ResourceBundle
			.getBundle("Messages", new XMLBundleControl());

	PairtreeObject(PairtreeRoot aRoot, String aName) throws IOException {
		super(aRoot, PairtreeUtils.mapToPPath(aName));

		if (!mkdirs()) {
			throw new IOException(BUNDLE.get("pt.cant_mkdirs", this));
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.debug("pt.object_created1", new String[] {
					getAbsolutePath(), aName });
		}
	}

	PairtreeObject(PairtreeRoot aRoot, String aPrefix, String aName)
			throws IOException {
		super(aRoot, PairtreeUtils.mapToPPath(aPrefix, aName));

		if (!mkdirs()) {
			throw new IOException(BUNDLE.get("pt.cant_mkdirs", this));
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.debug("pt.object_created2", new String[] {
					getAbsolutePath(), aPrefix, aName });
		}
	}

	public String toString() {
		return getAbsolutePath();
	}
}
