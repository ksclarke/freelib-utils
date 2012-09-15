/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;

public class DirFileFilter implements FilenameFilter {

	public boolean accept(File aDir, String aFileName) {
		return new File(aDir, aFileName).isDirectory() ? true : false;
	}

}
