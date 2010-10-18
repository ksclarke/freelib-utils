package info.freelibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class BufferedFileReader extends BufferedReader {

	public BufferedFileReader(File aFile) throws FileNotFoundException {
		super(getReader(aFile));
	}

	public BufferedFileReader(File aFile, String aEncoding)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(new InputStreamReader(new FileInputStream(aFile), aEncoding));
	}

	private static final Reader getReader(File aFile)
			throws FileNotFoundException {
		try {
			return new InputStreamReader(new FileInputStream(aFile), "UTF-8");
		}
		catch (UnsupportedEncodingException details) {
			throw new RuntimeException(details); // UTF-8 is always supported
		}
	}
}
