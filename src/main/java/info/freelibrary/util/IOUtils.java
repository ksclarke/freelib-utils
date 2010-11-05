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

public class IOUtils {

	public static final void copyStream(InputStream aInputStream,
			OutputStream aOutputStream) throws IOException {
		IOUtils.copyStream(aInputStream, aOutputStream, true);
	}

	public static final void copyStream(InputStream aInputStream,
			OutputStream aOutputStream, boolean aCompleteCopy) throws IOException {
		BufferedInputStream iStream = new BufferedInputStream(aInputStream);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		BufferedOutputStream oStream = new BufferedOutputStream(aOutputStream);
		byte[] buffer = new byte[1024];
		int bytesRead = 0;

		while (true) {
			bytesRead = iStream.read(buffer);
			if (bytesRead == -1) break;
			byteStream.write(buffer, 0, bytesRead);
		};

		oStream.write(byteStream.toByteArray());
		oStream.flush();
		
		if (aCompleteCopy) {
			oStream.close();
			iStream.close();
		}
	}

}
