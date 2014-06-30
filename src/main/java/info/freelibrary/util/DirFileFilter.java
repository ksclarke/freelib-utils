/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A filter that returns only directories from a file system scan.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class DirFileFilter implements FilenameFilter {

    /**
     * Determines which {@link File}s should be accepted.
     * 
     * @param aDir A directory in which to test the files
     * @param aFileName A file name in the directory
     */
    @Override
    public boolean accept(final File aDir, final String aFileName) {
        return new File(aDir, aFileName).isDirectory() ? true : false;
    }

}
