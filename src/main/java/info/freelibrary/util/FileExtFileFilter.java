/**
 * 
 */
package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class FileExtFileFilter implements FilenameFilter {

	private String[] myExtensions;

	public FileExtFileFilter(String aFileExt) {
		myExtensions = new String[] { "." + aFileExt };
	}

	public FileExtFileFilter(String... aFileExtList) {
		myExtensions = new String[aFileExtList.length];

		for (int index = 0; index < aFileExtList.length; index++) {
			myExtensions[index] = "." + aFileExtList[index];
		}
	}

	public boolean accept(File aDir, String aFileName) {
		for (String extension : myExtensions) {
			if (new File(aDir, aFileName).isFile()
					&& aFileName.endsWith(extension)) {
				return true;
			}
		}

		return false;
	}

}
