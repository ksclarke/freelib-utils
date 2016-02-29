/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.File;

/**
 * A representation of an encapsulated object in the Pairtree structure.
 *
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class PairtreeObject extends File {

    /**
     * The <code>serialVersionUID</code> for a <code>PairtreeObject</code>.
     */
    private static final long serialVersionUID = -1952619495973020687L;

    /**
     * Creates a new Pairtree object for the supplied name in the supplied Pairtree structure.
     *
     * @param aRoot An existing Pairtree structure
     * @param aID The name of the Pairtree object
     */
    PairtreeObject(final PairtreeRoot aRoot, final String aID) {
        super(aRoot, PairtreeUtils.mapToPtPath(null, aID, aID));
    }

    /**
     * Creates a new Pairtree object for the supplied name in the supplied Pairtree structure.
     *
     * @param aRoot The root of the Pairtree structure
     * @param aPrefix A Pairtree prefix to use when creating the directory structure
     * @param aID The name of the Pairtree object to create
     */
    PairtreeObject(final PairtreeRoot aRoot, final String aPrefix, final String aID) {
        super(aRoot, PairtreeUtils.mapToPtPath(null, PairtreeUtils.removePrefix(aPrefix, aID), PairtreeUtils
                .removePrefix(aPrefix, aID)));
    }

    /**
     * Returns a string representation of the Pairtree object.
     */
    @Override
    public String toString() {
        return getAbsolutePath();
    }
}
