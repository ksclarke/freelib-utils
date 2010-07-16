/**
 * 
 */
package info.freelibrary.util.bzip2;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class CBZip2InputStreamTest extends TestCase {

	private static final byte[] BZ2_DATA = { 0x42, 0x5a, 0x68, 0x39, 0x31,
			0x41, 0x59, 0x26, 0x53, 0x59, 0x4e, (byte) 0xec, (byte) 0xe8, 0x36,
			0x00, 0x00, (byte) 0x02, 0x51, (byte) 0x80, 0x00, 0x10, 0x40, 0x00,
			0x06, 0x44, (byte) 0x90, (byte) 0x80, 0x20, 0x00, 0x31, 0x06, 0x4c,
			0x41, 0x01, (byte) 0xa7, (byte) 0xa9, (byte) 0xa5, (byte) 0x80,
			(byte) 0xbb, (byte) 0x94, 0x31, (byte) 0xf8, (byte) 0xbb,
			(byte) 0x92, 0x29, (byte) 0xc2, (byte) 0x84, (byte) 0x82, 0x77,
			0x67, 0x41, (byte) 0xb0 };

	public void testCBZip2InputStream() {
		try {
			ByteArrayInputStream inStream = new ByteArrayInputStream(BZ2_DATA);
			CBZip2InputStream bzip2 = new CBZip2InputStream(inStream);
			byte[] bytes = new byte[BZ2_DATA.length];

			bzip2.read(bytes);
			Assert.assertEquals(new String(bytes).trim(), "hello world");
		}
		catch (IOException details) {
			Assert.fail(details.getMessage());
		}
	}
}
