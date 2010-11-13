/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

	public static final void closeQuietly(Reader aReader) {
		if (aReader != null) {
			try {
				aReader.close();
			}
			catch (IOException details) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(details.getMessage(), details);
				}
			}
		}
	}
	
	public static final void closeQuietly(Writer aWriter) {
		if (aWriter != null) {
			try {
				aWriter.close();
			}
			catch (IOException details) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(details.getMessage(), details);
				}
			}
		}
	}
	
	public static final void closeQuietly(InputStream aInputStream) {
		if (aInputStream != null) {
			try {
				aInputStream.close();
			}
			catch (IOException details) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(details.getMessage(), details);
				}
			}
		}
	}

	public static final void closeQuietly(OutputStream aOutputStream) {
		if (aOutputStream != null) {
			try {
				aOutputStream.close();
			}
			catch (IOException details) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(details.getMessage(), details);
				}
			}
		}
	}

	public static final void copyStream(InputStream aInStream,
			OutputStream aOutStream) throws IOException {
		BufferedOutputStream outStream = new BufferedOutputStream(aOutStream);
		BufferedInputStream inStream = new BufferedInputStream(aInStream);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead = 0;

		while (true) {
			bytesRead = inStream.read(buffer);
			if (bytesRead == -1) break;
			byteStream.write(buffer, 0, bytesRead);
		};

		outStream.write(byteStream.toByteArray());
		outStream.flush();
	}

}
