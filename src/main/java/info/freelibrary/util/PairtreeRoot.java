package info.freelibrary.util;

import info.freelibrary.util.XMLBundleControl;
import info.freelibrary.util.XMLResourceBundle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairtreeRoot extends File {

	public static final String PAIRTREE_VERSION_NUM = "0.1";

	private static final String LINE_SEP = System.getProperty("line.separator");

	private static final long serialVersionUID = 148951612898582350L;

	private static final String PAIRTREE_PREFIX = "pairtree_prefix";

	private static final String PAIRTREE_ROOT = "pairtree_root";

	private static final String PAIRTREE_VERSION = "pairtree_version";

	private static final XMLResourceBundle BUNDLE = (XMLResourceBundle) ResourceBundle
			.getBundle("Messages", new XMLBundleControl());

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PairtreeRoot.class);

	private String myPairtreePrefix;

	/**
	 * Creates a new Pairtree structure in the current directory. This will
	 * create the related version file too.
	 * 
	 * @throws IOException If there is a problem creating the structure
	 */
	public PairtreeRoot() throws IOException {
		this(new File("."));
	}

	/**
	 * Creates a new Pairtree structure, using a Pairtree prefix, in the current
	 * directory. This will create the related version and Pairtree prefix files
	 * too.
	 * 
	 * @throws IOException If there is a problem creating the structure
	 */
	public PairtreeRoot(String aPairtreePrefix) throws IOException {
		this(new File("."), aPairtreePrefix);
	}

	/**
	 * Creates a new Pairtree structure in the supplied directory. This will
	 * create the related version file too.
	 * 
	 * @throws IOException If there is a problem creating the structure
	 */
	public PairtreeRoot(File aParentDir) throws IOException {
		this(aParentDir, null);
	}

	/**
	 * Creates a new Pairtree structure, using a Pairtree prefix, in the
	 * supplied directory. This will create the related version and Pairtree
	 * prefix files too.
	 * 
	 * @throws IOException If there is a problem creating the structure
	 */
	public PairtreeRoot(File aParentDir, String aPairtreePrefix)
			throws IOException {
		super(aParentDir, PAIRTREE_ROOT);

		if (aPairtreePrefix != null) {
			File prefixFile = new File(aParentDir, PAIRTREE_PREFIX);

			myPairtreePrefix = aPairtreePrefix;
			writePrefixFile(prefixFile, getPairtreePrefix());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(BUNDLE.get("pt.creating_root", aParentDir));

			if (aPairtreePrefix != null) {
				LOGGER.debug(BUNDLE.get("pt.using_prefix", myPairtreePrefix));
			}
		}

		if (!mkdirs()) {
			throw new IOException(BUNDLE.get("pt.cant_mkdirs", this));
		}

		writeVersionFile(new File(aParentDir, getVersionFileName()));
	}

	/**
	 * Returns a directory in the Pairtree directory for the supplied name. File
	 * to be put into the structure can use this as a parent directory.
	 * 
	 * @param aName The name of the Pairtree object (the object's ID)
	 * @return A <code>PairtreeObject</code> (directory in the Pairtree
	 *         structure).
	 * @throws IOException
	 */
	public PairtreeObject getObject(String aName) throws IOException {
		return myPairtreePrefix == null ? new PairtreeObject(this, aName)
				: new PairtreeObject(this, myPairtreePrefix, aName);
	}

	/**
	 * Deletes the Pairtree structure, including all contained and related
	 * files.
	 */
	public boolean delete() {
		File prefixFile = new File(getParentFile(), PAIRTREE_PREFIX);
		File ptVersion = new File(getParentFile(), getVersionFileName());
		boolean result = true;

		if (prefixFile != null) {
			if (!prefixFile.delete()) {
				result = false;
			}
		}

		if (ptVersion != null) {
			if (!ptVersion.delete()) {
				result = false;
			}
		}

		for (File file : listFiles()) {
			if (!FileUtils.delete(file)) {
				result = false;
			}
		}

		if (!super.delete()) {
			result = false;
		}

		return result;
	}

	private String getPairtreePrefix() {
		return myPairtreePrefix;
	}

	private String getVersionFileName() {
		return PAIRTREE_VERSION + PAIRTREE_VERSION_NUM.replace('.', '_');
	}

	private void writeVersionFile(File aFile) throws IOException {
		if (!aFile.exists()) {
			FileWriter writer = new FileWriter(aFile);
			writer.write(BUNDLE.get("pt.verfile.content1", PAIRTREE_VERSION_NUM));
			writer.write(LINE_SEP);
			writer.write(BUNDLE.get("pt.verfile.content2"));
			writer.write(LINE_SEP);
			writer.close();
		}
	}

	private void writePrefixFile(File aFile, String aPrefix) throws IOException {
		if (!aFile.exists()) {
			FileWriter writer = new FileWriter(aFile);
			writer.write(aPrefix);
			writer.write(LINE_SEP);
			writer.close();
		}
	}
}
