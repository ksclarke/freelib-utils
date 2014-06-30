/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.xq;

import info.freelibrary.util.FileUtils;

import java.io.FileNotFoundException;

import org.w3c.dom.Element;

/**
 * An XQuery extension wrapper of some file utilities that throw exceptions. Throwing Java exceptions isn't acceptable
 * for an XQuery extension.
 *
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class Files {

    private Files() {
    }

    /**
     * Outputs an XML representation of a directory structure using a regular expression to indicate what types of files
     * should be included in the output.
     *
     * @param aDir A directory we want to use as the root in our XML file
     * @param aPattern A regular expression that will match files in the directory
     * @return An element representation of the directory structure
     */
    public static final Element toXML(final String aDir, final String aPattern) {
        return toXML(aDir, aPattern, false);
    };

    /**
     * Outputs an XML representation of a directory structure using a regular expression to indicate what types of files
     * should be included in the output.
     *
     * @param aDir A directory we want to use as the root in our XML file
     * @param aBool Whether we should descend through the directory structure
     * @param aPattern A regular expression that will match files in the directory
     * @return An element representation of the directory structure
     */
    public static final Element toXML(final String aDir, final String aPattern, final boolean aBool) {
        try {
            return FileUtils.toElement(aDir, aPattern, aBool);
        } catch (final FileNotFoundException details) {
            return null; // should be interpreted as an empty sequence
        } catch (final Exception details) {
            throw new RuntimeException(details);
        }
    };

    /**
     * Outputs an XML representation of a directory structure.
     *
     * @param aDir A directory we want to use as the root in our XML file
     * @return An element representation of the directory structure
     */
    public static final Element toXML(final String aDir) {
        return toXML(aDir, false);
    };

    /**
     * Outputs an XML representation of a directory structure.
     *
     * @param aDir A directory we want to use as the root in our XML file
     * @param aBool Whether we should descend through the directory structure
     * @return An element representation of the directory structure
     */
    public static final Element toXML(final String aDir, final boolean aBool) {
        try {
            return FileUtils.toElement(aDir, aBool);
        } catch (final FileNotFoundException details) {
            return null; // should be interpreted as an empty sequence
        } catch (final Exception details) {
            throw new RuntimeException(details);
        }
    };
}
