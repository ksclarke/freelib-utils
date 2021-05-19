
package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * A {@link FilenameFilter} that allows filtering file names based on regular expressions.
 */
public class RegexFileFilter implements FilenameFilter {

    /**
     * A regex pattern for the file filter.
     */
    private Pattern myPattern;

    /**
     * Constructor for a regular expression {@link FilenameFilter}.
     *
     * @param aPattern The regular expression for the filter
     */
    public RegexFileFilter(final String aPattern) {
        this(aPattern, false);
    }

    /**
     * Constructor for a regular expression {@link FilenameFilter} that creates a case insensitive.
     *
     * @param aPattern The regular expression for the filter
     * @param aCaseInsensitivePattern A case insensitive regular expression pattern
     */
    public RegexFileFilter(final String aPattern, final boolean aCaseInsensitivePattern) {
        if (aCaseInsensitivePattern) {
            myPattern = Pattern.compile(aPattern, Pattern.CASE_INSENSITIVE);
        } else {
            myPattern = Pattern.compile(aPattern);
        }
    }

    /**
     * Determines whether the supplied {@link File} in the supplied directory should be included.
     *
     * @param aDir The directory in which the {@link File} of the file name lives
     * @param aFilename A {@link File} name to compare against the regular expression; it must be a file (not a
     *        directory)
     */
    @Override
    public boolean accept(final File aDir, final String aFilename) {
        return new File(aDir, aFilename).isFile() && myPattern.matcher(aFilename).matches();
    }

    /**
     * Returns a string version of the regular expression used as a filter.
     */
    @Override
    public String toString() {
        return myPattern.pattern();
    }
}
