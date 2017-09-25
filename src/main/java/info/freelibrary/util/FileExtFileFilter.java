/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * A file name filter that checks the file extension to determine whether the filter matches the file name or not. It
 * can have a single or multiple file extensions in the form: 'gif' or 'xml'.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class FileExtFileFilter implements FilenameFilter {

    private static final String DOT = ".";

    // Extensions registered with this filter
    private final String[] myExtensions;

    /**
     * Constructor for a <code>FilenameFilter</code> that checks a single file extension against supplied file names,
     * looking for matches.
     *
     * @param aFileExt A file extensions (minus the '.') against which we want to compare
     */
    public FileExtFileFilter(final String aFileExt) {
        myExtensions = new String[] { normalizeExt(aFileExt) };
    }

    /**
     * Constructor for a <code>FilenameFilter</code> that checks an array of file extensions against supplied file
     * names, looking for matches.
     *
     * @param aFileExtList A list of file extensions (minus the '.') against which we want to compare
     */
    public FileExtFileFilter(final String... aFileExtList) {
        myExtensions = new String[aFileExtList.length];

        for (int index = 0; index < aFileExtList.length; index++) {
            myExtensions[index] = normalizeExt(aFileExtList[index]);
        }
    }

    /**
     * Returns a string representation of the filter which includes the file name extensions being used as filters.
     *
     * @return A string representation of the filter which includes the file name extensions being used as filters.
     */
    @Override
    public String toString() {
        return Arrays.toString(myExtensions);
    }

    /**
     * Returns true if the supplied file name and parent directory are a match for this <code>FilenameFilter</code>.
     *
     * @param aDir A parent directory for the supplied file name
     * @param aFileName The file name we want to check against our filter
     * @return True if the filter matches the supplied parent and file name; else, false
     */
    @Override
    public boolean accept(final File aDir, final String aFileName) {
        for (final String extension : myExtensions) {
            if (new File(aDir, aFileName).isFile() && aFileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns whether or not the supplied file extension is one that this filter matches.
     *
     * @param aFileExt A file extension like: jpg, gif, jp2, txt, xml, etc.
     * @return True if this filter matches files with the supplied extension
     */
    public boolean filters(final String aFileExt) {
        final String normalizedExt = normalizeExt(aFileExt);

        for (final String extension : myExtensions) {
            if (extension.equals(normalizedExt)) {
                return true;
            }
        }

        return false;
    }

    private String normalizeExt(final String aFileExt) {
        return DOT + (aFileExt.startsWith(DOT) ? aFileExt.substring(1) : aFileExt);
    }

}
