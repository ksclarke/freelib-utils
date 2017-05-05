/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import net.iharder.Base64;

/**
 * Utilities for use with password creation.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public final class PasswordUtils {

    private PasswordUtils() {
    }

    /**
     * Generates a salt for working with passwords.
     *
     * @return A salt
     */
    public static String generateSalt() {
        return new BigInteger(40, new SecureRandom()).toString(32);
    }

    /**
     * Encrypts the supplied text.
     *
     * @param aText The text to be encrypted
     * @return The encrypted password
     * @throws IOException If there is trouble encrypting the supplied text
     */
    public static String encrypt(final String aText) throws IOException {
        return PasswordUtils.encrypt(aText, "");
    }

    /**
     * Encrypts the supplied text using the supplied salt.
     *
     * @param aText The text to be encrypted
     * @param aSalt The salt to use in the encryption
     * @return The encrypted password
     * @throws IOException If there is trouble encrypting the supplied text
     */
    public static String encrypt(final String aText, final String aSalt) throws NullPointerException, IOException {
        if (aText == null) {
            throw new NullPointerException("Text to encrypt is null");
        }

        if (aSalt == null) {
            throw new NullPointerException("Salt to encrypt with is null");
        }

        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA");
            final String saltedText = aText + aSalt;
            digest.update(saltedText.getBytes("UTF-8"));
            return Base64.encodeBytes(digest.digest());
        } catch (final NoSuchAlgorithmException details) {
            throw new RuntimeException(details); // programming error
        } catch (final UnsupportedEncodingException details) {
            throw new RuntimeException(details); // programming error
        }
    }
}
