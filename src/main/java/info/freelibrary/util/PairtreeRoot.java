/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import static info.freelibrary.util.MessageCodes.PT_MESSAGE_002;
import static info.freelibrary.util.MessageCodes.PT_MESSAGE_006;
import static info.freelibrary.util.MessageCodes.PT_MESSAGE_007;
import static info.freelibrary.util.MessageCodes.PT_MESSAGE_008;
import static info.freelibrary.util.MessageCodes.PT_MESSAGE_009;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * The root of the Pairtree structure.
 *
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class PairtreeRoot extends File {

    public static final String PT_VERSION_NUM = "0.1";

    private static final long serialVersionUID = 148951612898582350L;

    private static final String PAIRTREE_PREFIX = "pairtree_prefix";

    private static final String PAIRTREE_ROOT = "pairtree_root";

    private static final String PAIRTREE_VERSION = "pairtree_version";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final Logger LOGGER = LoggerFactory.getLogger(PairtreeRoot.class, Constants.FREELIB_UTIL_MESSAGES);

    private String myPairtreePrefix;

    /**
     * Creates a new Pairtree structure in the supplied directory. This will create the related version and prefix files
     * too.
     *
     * @param aParentDirPath The parent directory into which to put the Pairtree structure
     * @throws IOException If there is a problem creating the structure
     */
    public PairtreeRoot(final String aParentDirPath) throws IOException {
        this(new File(aParentDirPath));
    }

    /**
     * Creates a new Pairtree structure, using a Pairtree prefix, in the supplied directory. This will create the
     * related version and prefix files too.
     *
     * @param aParentDirPath The parent directory into which to put the Pairtree structure
     * @param aPairtreePrefix The prefix for the Pairtree structure
     * @throws IOException If there is a problem creating the structure
     */
    public PairtreeRoot(final String aParentDirPath, final String aPairtreePrefix) throws IOException {
        this(new File(aParentDirPath), aPairtreePrefix);
    }

    /**
     * Creates a new Pairtree structure in the supplied directory. This will create the related version file too.
     *
     * @throws IOException If there is a problem creating the structure
     */
    public PairtreeRoot(final File aParentDir) throws IOException {
        this(aParentDir, null);
    }

    /**
     * Creates a new Pairtree structure, using a Pairtree prefix, in the supplied directory. This will create the
     * related version and Pairtree prefix files too.
     *
     * @throws IOException If there is a problem creating the structure
     */
    public PairtreeRoot(final File aParentDir, final String aPairtreePrefix) throws IOException {
        super(aParentDir, PAIRTREE_ROOT);

        if (aPairtreePrefix != null) {
            final File prefixFile = new File(aParentDir, PAIRTREE_PREFIX);

            myPairtreePrefix = aPairtreePrefix;
            writePrefixFile(prefixFile, getPairtreePrefix());
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(MessageCodes.PT_MESSAGE_001, aParentDir);

            if (aPairtreePrefix != null) {
                LOGGER.debug(MessageCodes.PT_MESSAGE_003, myPairtreePrefix);
            }
        }

        if (exists() && !canRead()) {
            throw new IOException(LOGGER.getMessage(PT_MESSAGE_009, this));
        } else if (!exists() && !mkdirs()) {
            throw new IOException(LOGGER.getMessage(PT_MESSAGE_002, this));
        }

        writeVersionFile(new File(aParentDir, getVersionFileName()));
    }

    /**
     * Returns a directory in the Pairtree directory for the supplied name. File to be put into the structure can use
     * this as a parent directory.
     *
     * @param aName The name of the Pairtree object (the object's ID)
     * @return A <code>PairtreeObject</code> (directory in the Pairtree structure).
     */
    public PairtreeObject getObject(final String aName) {
        return myPairtreePrefix == null ? new PairtreeObject(this, aName) : new PairtreeObject(this, myPairtreePrefix,
                aName);
    }

    /**
     * Deletes the Pairtree structure, including all contained and related files.
     */
    @Override
    public boolean delete() {
        final File prefixFile = new File(getParentFile(), PAIRTREE_PREFIX);
        final File ptVersion = new File(getParentFile(), getVersionFileName());
        boolean result = true;

        if (prefixFile.exists() && !prefixFile.delete()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Unable to delete pairtree prefix file");
            }

            result = false;
        }

        if (ptVersion.exists() && !ptVersion.delete()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Unable to delete pairtree version file");
            }

            result = false;
        }

        for (final File file : listFiles()) {
            if (!FileUtils.delete(file)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Unable to delete " + file);
                }

                result = false;
            }
        }

        if (!super.delete()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Unable to delete pairtree directory");
            }

            result = false;
        }

        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((myPairtreePrefix == null) ? 0 : myPairtreePrefix.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object aObject) {
        if (this == aObject) {
            return true;
        }

        if (!super.equals(aObject)) {
            return false;
        }

        if (getClass() != aObject.getClass()) {
            return false;
        }

        final PairtreeRoot other = (PairtreeRoot) aObject;

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
    public String getPairtreePrefix() {
        return myPairtreePrefix;
    }

    /**
     * Gets the name of the Pairtree version file.
     *
     * @return The name of the Pairtree version file
     */
    public String getVersionFileName() {
        return PAIRTREE_VERSION + PT_VERSION_NUM.replace('.', '_');
    }

    /**
     * Writes the version information to the supplied file.
     *
     * @param aFile A file to write the version information
     * @throws IOException If there is trouble writing to the file
     */
    private void writeVersionFile(final File aFile) throws IOException {
        if (!aFile.exists()) {
            BufferedWriter writer = null;

            try {
                final FileOutputStream fileOut = new FileOutputStream(aFile);
                final CharsetEncoder encoder = Charset.forName(DEFAULT_CHARSET).newEncoder();

                writer = new BufferedWriter(new OutputStreamWriter(fileOut, encoder));
                writer.write(LOGGER.getMessage(PT_MESSAGE_007, PT_VERSION_NUM));
                writer.newLine();
                writer.write(LOGGER.getMessage(PT_MESSAGE_008));
                writer.newLine();
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
    private void writePrefixFile(final File aFile, final String aPtPrefix) throws IOException {
        if (!aFile.exists()) {
            BufferedWriter writer = null;

            try {
                final FileOutputStream fileOut = new FileOutputStream(aFile);
                final CharsetEncoder encoder = Charset.forName(DEFAULT_CHARSET).newEncoder();

                writer = new BufferedWriter(new OutputStreamWriter(fileOut, encoder));
                writer.write(aPtPrefix);
                writer.newLine();
                writer.close();
            } finally {
                IOUtils.closeQuietly(writer);
            }
        } else {
            final String prefix = StringUtils.read(aFile, DEFAULT_CHARSET);

            if (!prefix.equals(aPtPrefix)) {
                throw new IOException(LOGGER.getMessage(PT_MESSAGE_006, prefix, aPtPrefix));
            }
        }
    }
}
