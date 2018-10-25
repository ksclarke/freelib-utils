
package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A filter that returns only directories from a file system scan.
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
