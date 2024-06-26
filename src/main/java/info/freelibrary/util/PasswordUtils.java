
package info.freelibrary.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

/**
 * Utilities for use with password creation.
 */
public final class PasswordUtils {

    /**
     * A logger used by the password utilities.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtils.class, MessageCodes.BUNDLE);

    /**
     * Creates a new password utilities instance.
     */
    private PasswordUtils() {
        // This is intentionally left empty
    }

    /**
     * Encrypts the supplied text.
     *
     * @param aText The text to be encrypted
     * @return The encrypted password
     * @throws IOException If there is trouble encrypting the supplied text
     */
    public static String encrypt(final String aText) throws IOException {
        return encrypt(aText, "");
    }

    /**
     * Encrypts the supplied text using the supplied salt.
     *
     * @param aText The text to be encrypted
     * @param aSalt The salt to use in the encryption
     * @return The encrypted password
     * @throws IOException If there is trouble encrypting the supplied text
     * @throws I18nRuntimeException If an unsupported algorithm or encoded is supplied
     */
    public static String encrypt(final String aText, final String aSalt) throws IOException {
        Objects.requireNonNull(aText, LOGGER.getI18n("Text to encrypt is null"));
        Objects.requireNonNull(aSalt, LOGGER.getI18n("Salt to encrypt with is null"));

        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA");
            final String saltedText = aText + aSalt;

            digest.update(saltedText.getBytes("UTF-8"));

            return Base64.getEncoder().encodeToString(digest.digest());
        } catch (final NoSuchAlgorithmException | UnsupportedEncodingException details) {
            throw new I18nRuntimeException(details); // programming error
        }
    }

    /**
     * Generates a salt for working with passwords.
     *
     * @return A salt
     */
    public static String generateSalt() {
        return new BigInteger(40, new SecureRandom()).toString(32);
    }
}
