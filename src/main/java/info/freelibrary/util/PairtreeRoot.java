/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.OutputStreamWriter;

import java.io.FileOutputStream;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The root of the Pairtree structure.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class PairtreeRoot extends File {

    public static final String PT_VERSION_NUM = "0.1";

    private static final String LINE_SEP = System.getProperty("line.separator");

    private static final long serialVersionUID = 148951612898582350L;

    private static final String PAIRTREE_PREFIX = "pairtree_prefix";

    private static final String PAIRTREE_ROOT = "pairtree_root";

    private static final String PAIRTREE_VERSION = "pairtree_version";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final XMLResourceBundle BUNDLE =
            (XMLResourceBundle) ResourceBundle.getBundle(
                    "freelib-utils_messages", new XMLBundleControl());

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
            LOGGER.debug(BUNDLE.get("pt.retrieving_root", aParentDir));

            if (aPairtreePrefix != null) {
                LOGGER.debug(BUNDLE.get("pt.using_prefix", myPairtreePrefix));
            }
        }

        if (!exists() && !mkdirs()) {
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

        if (prefixFile != null && !prefixFile.delete()) {
            result = false;
        }

        if (ptVersion != null && !ptVersion.delete()) {
            result = false;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result =
                prime *
                        result +
                        ((myPairtreePrefix == null) ? 0 : myPairtreePrefix
                                .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object aObject) {
        if (this == aObject) {
            return true;
        }

        if (!super.equals(aObject)) {
            return false;
        }

        if (getClass() != aObject.getClass()) {
            return false;
        }

        PairtreeRoot other = (PairtreeRoot) aObject;

        if (myPairtreePrefix == null) {
            if (other.myPairtreePrefix != null) {
                return false;
            }
        } else if (!myPairtreePrefix.equals(other.myPairtreePrefix)) {
            return false;
        }

        return true;
    }

    /**
     * Returns the Pairtree prefix.
     * 
     * @return The Pairtree prefix
     */
    private String getPairtreePrefix() {
        return myPairtreePrefix;
    }

    /**
     * Gets the name of the Pairtree version file.
     * 
     * @return The name of the Pairtree version file
     */
    private String getVersionFileName() {
        return PAIRTREE_VERSION + PT_VERSION_NUM.replace('.', '_');
    }

    /**
     * Writes the version information to the supplied file.
     * 
     * @param aFile A file to write the version information
     * @throws IOException If there is trouble writing to the file
     */
    private void writeVersionFile(File aFile) throws IOException {
        if (!aFile.exists()) {
            OutputStreamWriter writer = null;

            try {
                FileOutputStream fileOut = new FileOutputStream(aFile);

                writer = new OutputStreamWriter(fileOut, DEFAULT_CHARSET);
                writer.write(BUNDLE.get("pt.verfile.content1", PT_VERSION_NUM));
                writer.write(LINE_SEP);
                writer.write(BUNDLE.get("pt.verfile.content2"));
                writer.write(LINE_SEP);
                writer.close();
            } finally {
                IOUtils.closeQuietly(writer);
            }
        }
    }

    /**
     * Writes the supplied prefix information to the supplied file.
     * 
     * @param aFile A file to write the prefix information
     * @param aPtPrefix The prefix to write to the supplied file
     * @throws IOException If there is trouble writing to the file
     */
    private void writePrefixFile(File aFile, String aPtPrefix)
            throws IOException {
        if (!aFile.exists()) {
            OutputStreamWriter writer = null;

            try {
                FileOutputStream fileOut = new FileOutputStream(aFile);

                writer = new OutputStreamWriter(fileOut, DEFAULT_CHARSET);
                writer.write(aPtPrefix);
                writer.write(LINE_SEP);
                writer.close();
            } finally {
                IOUtils.closeQuietly(writer);
            }
        } else {
            String prefix = StringUtils.read(aFile, DEFAULT_CHARSET);

            if (!prefix.equals(aPtPrefix)) {
                throw new IOException(BUNDLE.get("pt.bad_prefix", prefix,
                        aPtPrefix));
            }
        }
    }
}
