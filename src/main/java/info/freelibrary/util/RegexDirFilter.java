/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * A {@link FilenameFilter} that allows filtering directory names based on
 * regular expressions.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class RegexDirFilter implements FilenameFilter {

    private Pattern myPattern;

    /**
     * Constructor for a regular expression {@link FilenameFilter}.
     * 
     * @param aPattern The regular expression for the filter
     */
    public RegexDirFilter(String aPattern) {
        myPattern = Pattern.compile(aPattern);
    }

    /**
     * Determines whether the supplied {@link File} in the supplied directory
     * should be included.
     * 
     * @param aDir The directory in which the {@link File} of the file name
     *        lives
     * @param aFilename A {@link File} name to compare against the regular
     *        expression; it must be a directory
     */
    public boolean accept(File aDir, String aFilename) {
        return new File(aDir, aFilename).isDirectory() &&
                myPattern.matcher(aFilename).matches();
    }

    /**
     * Returns a string version of the regex used as a filter.
     */
    public String toString() {
        return myPattern.toString();
    }
}
