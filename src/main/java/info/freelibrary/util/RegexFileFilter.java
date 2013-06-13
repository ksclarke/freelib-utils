/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class RegexFileFilter implements FilenameFilter {

	private Pattern myPattern;

	public RegexFileFilter(String aPattern) {
		myPattern = Pattern.compile(aPattern);
	}

	public boolean accept(File aDir, String aFilename) {
		return new File(aDir, aFilename).isFile()
				&& myPattern.matcher(aFilename).matches();
	}

}
