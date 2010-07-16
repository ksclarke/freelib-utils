package info.freelibrary.util;

import java.io.File;
import java.io.FilenameFilter;

public class DirFileFilter implements FilenameFilter {

	@Override
	public boolean accept(File aDir, String aFileName) {
		if (new File(aDir, aFileName).isDirectory()) {
			return true;
		}

		return false;
	}

}
