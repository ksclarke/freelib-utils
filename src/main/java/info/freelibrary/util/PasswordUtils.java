package info.freelibrary.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import net.iharder.Base64;

public final class PasswordUtils {

	private PasswordUtils() {}

	public final static String generateSalt() {
		return new BigInteger(40, new SecureRandom()).toString(32);
	}

	public final static String encrypt(String aText) throws IOException {
		return PasswordUtils.encrypt(aText, "");
	}

	public final static String encrypt(String aText, String aSalt)
			throws IOException {
		if (aText == null) {
			throw new NullPointerException("Text to encrypt is null");
		}

		if (aSalt == null) {
			throw new NullPointerException("Salt to encrypt with is null");
		}

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update((aText + aSalt).getBytes("UTF-8"));
			return Base64.encodeBytes(digest.digest());
		}
		catch (NoSuchAlgorithmException details) {
			throw new RuntimeException(details); // programming error
		}
		catch (UnsupportedEncodingException details) {
			throw new RuntimeException(details); // programming error
		}
	}
}
