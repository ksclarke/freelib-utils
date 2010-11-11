package info.freelibrary.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class BufferedFileWriter extends BufferedWriter {

	public BufferedFileWriter(File aFile) throws FileNotFoundException {
		super(getWriter(aFile));
	}

	public BufferedFileWriter(File aFile, String aEncoding)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(new OutputStreamWriter(new FileOutputStream(aFile), aEncoding));
	}

	private static final Writer getWriter(File aFile)
			throws FileNotFoundException {
		try {
			return new OutputStreamWriter(new FileOutputStream(aFile), "UTF-8");
		}
		catch (UnsupportedEncodingException details) {
			throw new RuntimeException(details); // UTF-8 is always supported
		}
	}
}
