
package info.freelibrary.util; // NOPMD - ExcessivePublicCount

import static info.freelibrary.util.Constants.DASH_CHAR;
import static info.freelibrary.util.Constants.DOT_CHAR;
import static info.freelibrary.util.Constants.HASH;
import static info.freelibrary.util.Constants.PERIOD;
import static info.freelibrary.util.Constants.PLUS_CHAR;
import static info.freelibrary.util.Constants.SINGLE_INSTANCE;
import static info.freelibrary.util.Constants.ZERO_CHAR;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

import info.freelibrary.util.warnings.PMD;

/**
 * Provides extra functionality for Java Number classes.
 * <p>
 * Class comes from the commons-lang3 library and is licensed under their license.
 * </p>
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength", PMD.EXCESSIVE_CLASS_LENGTH, "PMD.GodClass", PMD.GOD_CLASS,
    "PMD.CyclomaticComplexity", PMD.CYCLOMATIC_COMPLEXITY, "PMD.TooManyMethods", PMD.TOO_MANY_METHODS })
public final class NumberUtils {

    /** Reusable Long constant for zero. */
    public static final Long LONG_ZERO = 0L;

    /** Reusable Long constant for one. */
    public static final Long LONG_ONE = 1L;

    /** Reusable Long constant for minus one. */
    public static final Long LONG_MINUS_ONE = -1L;

    /** Reusable Integer constant for zero. */
    public static final Integer INTEGER_ZERO = 0;

    /** Reusable Integer constant for one. */
    public static final Integer INTEGER_ONE = 1;

    /** Reusable Integer constant for two. */
    public static final Integer INTEGER_TWO = 2;

    /** Reusable Integer constant for minus one. */
    public static final Integer INTEGER_MINUS_ONE = -1;

    /** Reusable Short constant for zero. */
    public static final Short SHORT_ZERO = (short) 0;

    /** Reusable Short constant for one. */
    public static final Short SHORT_ONE = (short) 1;

    /** Reusable Short constant for minus one. */
    public static final Short SHORT_MINUS_ONE = (short) -1;

    /** Reusable Byte constant for zero. */
    public static final Byte BYTE_ZERO = (byte) 0;

    /** Reusable Byte constant for one. */
    public static final Byte BYTE_ONE = (byte) 1;

    /** Reusable Byte constant for minus one. */
    public static final Byte BYTE_MINUS_ONE = (byte) -1;

    /** Reusable Double constant for zero. */
    public static final Double DOUBLE_ZERO = 0.0d;

    /** Reusable Double constant for one. */
    public static final Double DOUBLE_ONE = 1.0d;

    /** Reusable Double constant for minus one. */
    public static final Double DOUBLE_MINUS_ONE = -1.0d;

    /** Reusable Float constant for zero. */
    public static final Float FLOAT_ZERO = 0.0f;

    /** Reusable Float constant for one. */
    public static final Float FLOAT_ONE = 1.0f;

    /** Reusable Float constant for minus one. */
    public static final Float FLOAT_MINUS_ONE = -1.0f;

    /** {@link Integer#MAX_VALUE} as a {@link Long}. */
    public static final Long LONG_INT_MAX_VALUE = (long) Integer.MAX_VALUE;

    /** {@link Integer#MIN_VALUE} as a {@link Long}. */
    public static final Long LONG_INT_MIN_VALUE = (long) Integer.MIN_VALUE;

    /** Logger for the NumberUtils class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtils.class, MessageCodes.BUNDLE);

    /** The upper case form of a hexadecimal prefix. */
    private static final String HEX_PREFIX_UC = "0X";

    /** The lower case form of a hexadecimal prefix. */
    private static final String HEX_PREFIX_LC = "0x";

    /**
     * A private constructor for the {@code NumberUtils} class.
     */
    private NumberUtils() {
        // This is intentionally left empty.
    }

    /**
     * Converts a {@link String} to an {@code int}, returning {@code zero} if the conversion fails.
     * <p>
     * If the string is {@code null}, {@code zero} is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toInt(null) = 0
     *   NumberUtils.toInt("")   = 0
     *   NumberUtils.toInt("1")  = 1
     * </pre>
     *
     * @param aString The string to convert
     * @return The int represented by the string or {@code zero} if conversion fails
     */
    public static int toInt(final String aString) {
        return toInt(aString, 0);
    }

    /**
     * Converts a {@link String} to an {@code int}, returning a default value if the conversion fails.
     * <p>
     * If the string is {@code null}, the default value is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toInt(null, 1) = 1
     *   NumberUtils.toInt("", 1)   = 1
     *   NumberUtils.toInt("1", 0)  = 1
     * </pre>
     *
     * @param aString The string to convert
     * @param aDefaultValue A default value
     * @return The int represented by the string or the default if conversion fails
     */
    public static int toInt(final String aString, final int aDefaultValue) {
        if (aString == null) {
            return aDefaultValue;
        }

        try {
            return Integer.parseInt(aString);
        } catch (final NumberFormatException details) {
            return aDefaultValue;
        }
    }

    /**
     * Converts a {@link String} to a {@code long}, returning {@code zero} if the conversion fails.
     * <p>
     * If the string is {@code null}, {@code zero} is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toLong(null) = 0L
     *   NumberUtils.toLong("")   = 0L
     *   NumberUtils.toLong("1")  = 1L
     * </pre>
     *
     * @param aString The string to convert
     * @return The long represented by the string or {@code 0} if conversion fails
     */
    public static long toLong(final String aString) {
        return toLong(aString, 0L);
    }

    /**
     * Converts a {@link String} to a {@code long}, returning a default value if the conversion fails.
     * <p>
     * If the string is {@code null}, the default value is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toLong(null, 1L) = 1L
     *   NumberUtils.toLong("", 1L)   = 1L
     *   NumberUtils.toLong("1", 0L)  = 1L
     * </pre>
     *
     * @param aString The string to convert
     * @param aDefaultValue A default value
     * @return The long represented by the string or the default if conversion fails
     */
    public static long toLong(final String aString, final long aDefaultValue) {
        if (aString == null) {
            return aDefaultValue;
        }

        try {
            return Long.parseLong(aString);
        } catch (final NumberFormatException details) {
            return aDefaultValue;
        }
    }

    /**
     * Converts a {@link String} to a {@code float}, returning {@code 0.0f} if the conversion fails.
     * <p>
     * If the string is {@code null}, {@code 0.0f} is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toFloat(null)   = 0.0f
     *   NumberUtils.toFloat("")     = 0.0f
     *   NumberUtils.toFloat("1.5")  = 1.5f
     * </pre>
     *
     * @param aString the string to convert
     * @return The float represented by the string or {@code 0.0f} if conversion fails
     */
    public static float toFloat(final String aString) {
        return toFloat(aString, 0.0f);
    }

    /**
     * Converts a {@link String} to a {@code float}, returning a default value if the conversion fails.
     * <p>
     * If the string is {@code null}, the default value is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toFloat(null, 1.1f)   = 1.0f
     *   NumberUtils.toFloat("", 1.1f)     = 1.1f
     *   NumberUtils.toFloat("1.5", 0.0f)  = 1.5f
     * </pre>
     *
     * @param aString The string to convert
     * @param aDefaultValue A default value
     * @return The float represented by the string or default if conversion fails
     */
    public static float toFloat(final String aString, final float aDefaultValue) {
        if (aString == null) {
            return aDefaultValue;
        }

        try {
            return Float.parseFloat(aString);
        } catch (final NumberFormatException details) {
            return aDefaultValue;
        }
    }

    /**
     * Converts a {@link String} to a {@code double}, returning {@code 0.0d} if the conversion fails.
     * <p>
     * If the string is {@code null}, {@code 0.0d} is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toDouble(null)   = 0.0d
     *   NumberUtils.toDouble("")     = 0.0d
     *   NumberUtils.toDouble("1.5")  = 1.5d
     * </pre>
     *
     * @param aString The string to convert
     * @return The double represented by the string or {@code 0.0d} if conversion fails
     */
    public static double toDouble(final String aString) {
        return toDouble(aString, 0.0d);
    }

    /**
     * Converts a {@link String} to a {@code double}, returning a default value if the conversion fails.
     * <p>
     * If the string is {@code null}, the default value is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toDouble(null, 1.1d)   = 1.1d
     *   NumberUtils.toDouble("", 1.1d)     = 1.1d
     *   NumberUtils.toDouble("1.5", 0.0d)  = 1.5d
     * </pre>
     *
     * @param aString The string to convert
     * @param aDefaultValue A default value
     * @return The double represented by the string or default if conversion fails
     */
    public static double toDouble(final String aString, final double aDefaultValue) {
        if (aString == null) {
            return aDefaultValue;
        }

        try {
            return Double.parseDouble(aString);
        } catch (final NumberFormatException details) {
            return aDefaultValue;
        }
    }

    /**
     * Converts a {@link BigDecimal} to a {@code double}.
     * <p>
     * If the {@link BigDecimal} is {@code null}, then the specified default value is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toDouble(null)                     = 0.0d
     *   NumberUtils.toDouble(BigDecimal.valueOf(8.5d)) = 8.5d
     * </pre>
     *
     * @param aValue The {@link BigDecimal} to convert
     * @return The double represented by the {@link BigDecimal} or {@code 0.0d} if the {@link BigDecimal} is
     *         {@code null}
     */
    public static double toDouble(final BigDecimal aValue) {
        return toDouble(aValue, 0.0d);
    }

    /**
     * Converts a {@link BigDecimal} to a {@code double}.
     * <p>
     * If the {@link BigDecimal} is {@code null}, then the specified default value is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toDouble(null, 1.1d)                     = 1.1d
     *   NumberUtils.toDouble(BigDecimal.valueOf(8.5d), 1.1d) = 8.5d
     * </pre>
     *
     * @param aValue The {@link BigDecimal} to convert
     * @param aDefaultValue A default value
     * @return The double represented by the {@link BigDecimal} or the defaultValue if the {@link BigDecimal} is
     *         {@code null}
     */
    public static double toDouble(final BigDecimal aValue, final double aDefaultValue) {
        return aValue == null ? aDefaultValue : aValue.doubleValue();
    }

    /**
     * Converts a {@link String} to a {@code byte}, returning {@code zero} if the conversion fails.
     * <p>
     * If the string is {@code null}, {@code zero} is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toByte(null) = 0
     *   NumberUtils.toByte("")   = 0
     *   NumberUtils.toByte("1")  = 1
     * </pre>
     *
     * @param aString The string to convert
     * @return The byte represented by the string or {@code zero} if conversion fails
     */
    public static byte toByte(final String aString) {
        return toByte(aString, (byte) 0);
    }

    /**
     * Converts a {@link String} to a {@code byte}, returning a default value if the conversion fails.
     * <p>
     * If the string is {@code null}, the default value is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toByte(null, 1) = 1
     *   NumberUtils.toByte("", 1)   = 1
     *   NumberUtils.toByte("1", 0)  = 1
     * </pre>
     *
     * @param aString The string to convert
     * @param aDefaultValue A default value
     * @return The byte represented by the string or the default if conversion fails
     */
    public static byte toByte(final String aString, final byte aDefaultValue) {
        if (aString == null) {
            return aDefaultValue;
        }

        try {
            return Byte.parseByte(aString);
        } catch (final NumberFormatException details) {
            return aDefaultValue;
        }
    }

    /**
     * Converts a {@link String} to a {@code short}, returning {@code zero} if the conversion fails.
     * <p>
     * If the string is {@code null}, {@code zero} is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toShort(null) = 0
     *   NumberUtils.toShort("")   = 0
     *   NumberUtils.toShort("1")  = 1
     * </pre>
     *
     * @param aString The string to convert
     * @return The short represented by the string or {@code zero} if conversion fails
     */
    public static short toShort(final String aString) {
        return toShort(aString, (short) 0);
    }

    /**
     * Converts a {@link String} to an {@code short}, returning a default value if the conversion fails.
     * <p>
     * If the string is {@code null}, the default value is returned.
     * </p>
     *
     * <pre>
     *   NumberUtils.toShort(null, 1) = 1
     *   NumberUtils.toShort("", 1)   = 1
     *   NumberUtils.toShort("1", 0)  = 1
     * </pre>
     *
     * @param aString The string to convert
     * @param aDefaultValue A default value
     * @return The short represented by the string or the default if conversion fails
     */
    public static short toShort(final String aString, final short aDefaultValue) {
        if (aString == null) {
            return aDefaultValue;
        }

        try {
            return Short.parseShort(aString);
        } catch (final NumberFormatException details) {
            return aDefaultValue;
        }
    }

    /**
     * Converts a {@link BigDecimal} to a {@link BigDecimal} with a scale of two that has been rounded using
     * {@code RoundingMode.HALF_EVEN}. If the supplied value is null, then {@code BigDecimal.ZERO} is returned.
     * <p>
     * Note, the scale of a {@link BigDecimal} is the number of digits to the right of the decimal point.
     * </p>
     *
     * @param aValue The {@link BigDecimal} to convert
     * @return The scaled, with appropriate rounding, {@link BigDecimal}
     */
    public static BigDecimal toScaledBigDecimal(final BigDecimal aValue) {
        return toScaledBigDecimal(aValue, INTEGER_TWO, RoundingMode.HALF_EVEN);
    }

    /**
     * Converts a {@link BigDecimal} to a {@link BigDecimal} whose scale is the specified value with a
     * {@link RoundingMode} applied. If the input is {@code null}, we simply return {@code BigDecimal.ZERO}.
     *
     * @param aValue The {@link BigDecimal} to convert
     * @param aScale The number of digits to the right of the decimal point
     * @param aRoundingMode A rounding behavior for numerical operations capable of discarding precision
     * @return The scaled, with appropriate rounding, {@link BigDecimal}
     */
    public static BigDecimal toScaledBigDecimal(final BigDecimal aValue, final int aScale,
            final RoundingMode aRoundingMode) {
        if (aValue == null) {
            return BigDecimal.ZERO;
        }

        return aValue.setScale(aScale, aRoundingMode == null ? RoundingMode.HALF_EVEN : aRoundingMode);
    }

    /**
     * Converts a {@link Float} to a {@link BigDecimal} with a scale of two that has been rounded using
     * {@code RoundingMode.HALF_EVEN}. If the supplied value is null, then {@code BigDecimal.ZERO} is returned.
     * <p>
     * Note, the scale of a {@link BigDecimal} is the number of digits to the right of the decimal point.
     * </p>
     *
     * @param aValue The {@link Float} to convert
     * @return The scaled, with appropriate rounding, {@link BigDecimal}
     */
    public static BigDecimal toScaledBigDecimal(final Float aValue) {
        return toScaledBigDecimal(aValue, INTEGER_TWO, RoundingMode.HALF_EVEN);
    }

    /**
     * Converts a {@link Float} to a {@link BigDecimal} whose scale is the specified value with a {@link RoundingMode}
     * applied. If the input value is {@code null}, we simply return {@code BigDecimal.ZERO}.
     *
     * @param aValue The {@link Float} to convert
     * @param aScale A number of digits to the right of the decimal point
     * @param aRoundingMode A rounding behavior for numerical operations capable of discarding precision
     * @return The scaled, with appropriate rounding, {@link BigDecimal}
     */
    public static BigDecimal toScaledBigDecimal(final Float aValue, final int aScale,
            final RoundingMode aRoundingMode) {
        if (aValue == null) {
            return BigDecimal.ZERO;
        }

        return toScaledBigDecimal(BigDecimal.valueOf(aValue), aScale, aRoundingMode);
    }

    /**
     * Converts a {@link Double} to a {@link BigDecimal} with a scale of two that has been rounded using
     * {@code RoundingMode.HALF_EVEN}. If the supplied value is null, then {@code BigDecimal.ZERO} is returned.
     * <p>
     * Note, the scale of a {@link BigDecimal} is the number of digits to the right of the decimal point.
     * </p>
     *
     * @param aValue The {@link Double} to convert
     * @return The scaled, with appropriate rounding, {@link BigDecimal}
     */
    public static BigDecimal toScaledBigDecimal(final Double aValue) {
        return toScaledBigDecimal(aValue, INTEGER_TWO, RoundingMode.HALF_EVEN);
    }

    /**
     * Converts a {@link Double} to a {@link BigDecimal} whose scale is the specified value with a {@link RoundingMode}
     * applied. If the input value is {@code null}, we simply return {@code BigDecimal.ZERO}.
     *
     * @param aValue A {@link Double} to convert
     * @param aScale The number of digits to the right of the decimal point
     * @param aRoundingMode A rounding behavior for numerical operations capable of discarding precision
     * @return The scaled, with appropriate rounding, {@link BigDecimal}
     */
    public static BigDecimal toScaledBigDecimal(final Double aValue, final int aScale,
            final RoundingMode aRoundingMode) {
        if (aValue == null) {
            return BigDecimal.ZERO;
        }

        return toScaledBigDecimal(BigDecimal.valueOf(aValue), aScale, aRoundingMode);
    }

    /**
     * Converts a {@link String} to a {@link BigDecimal} with a scale of two that has been rounded using
     * {@code RoundingMode.HALF_EVEN}. If the supplied value is null, then {@code BigDecimal.ZERO} is returned.
     * <p>
     * Note, the scale of a {@link BigDecimal} is the number of digits to the right of the decimal point.
     * </p>
     *
     * @param aValue A {@link String} to convert
     * @return The scaled, with appropriate rounding, {@link BigDecimal}
     */
    public static BigDecimal toScaledBigDecimal(final String aValue) {
        return toScaledBigDecimal(aValue, INTEGER_TWO, RoundingMode.HALF_EVEN);
    }

    /**
     * Converts a {@link String} to a {@link BigDecimal} whose scale is the specified value with a {@link RoundingMode}
     * applied. If the input {@code value} is {@code null}, we simply return {@code BigDecimal.ZERO}.
     *
     * @param aValue A {@link String} to convert
     * @param aScale The number of digits to the right of the decimal point
     * @param aRoundingMode A rounding behavior for numerical operations capable of discarding precision
     * @return The scaled, with appropriate rounding, {@link BigDecimal}
     */
    public static BigDecimal toScaledBigDecimal(final String aValue, final int aScale,
            final RoundingMode aRoundingMode) {
        if (aValue == null) {
            return BigDecimal.ZERO;
        }

        return toScaledBigDecimal(createBigDecimal(aValue), aScale, aRoundingMode);
    }

    /**
     * Turns a string value into a {@link java.lang.Number}.
     * <p>
     * If the string starts with {@code 0x} or {@code -0x} (lower or upper case) or {@code #} or {@code -#}, it will be
     * interpreted as a hexadecimal Integer - or Long, if the number of digits after the prefix is more than 8 - or
     * BigInteger if there are more than 16 digits.
     * </p>
     * <p>
     * Then, the value is examined for a type qualifier on the end, i.e. one of {@code 'f', 'F', 'd', 'D', 'l', 'L'}. If
     * it is found, it starts trying to create successively larger types from the type specified until one is found that
     * can represent the value.
     * </p>
     * <p>
     * If a type specifier is not found, it will check for a decimal point and then try successively larger types from
     * {@link Integer} to {@link BigInteger} and from {@link Float} to {@link BigDecimal}.
     * </p>
     * <p>
     * Integral values with a leading {@code 0} will be interpreted as octal; the returned number will be Integer, Long
     * or BigDecimal as appropriate.
     * </p>
     * <p>
     * Returns {@code null} if the string is {@code null}.
     * </p>
     * <p>
     * This method does not trim the input string, i.e., strings with leading or trailing spaces will generate
     * NumberFormatExceptions.
     * </p>
     *
     * @param aString A string containing a number
     * @return Number created from the string (or null if the input is null)
     * @throws NumberFormatException If the value cannot be converted
     */
    @SuppressWarnings({ "PMD.ExcessiveMethodLength", PMD.EXCESSIVE_METHOD_LENGTH, "PMD.CognitiveComplexity",
        PMD.COGNITIVE_COMPLEXITY, "PMD.ExcessiveMethodLength", PMD.EXCESSIVE_METHOD_LENGTH, "PMD.NcssCount",
        PMD.NCSS_COUNT, "PMD.CyclomaticComplexity", PMD.CYCLOMATIC_COMPLEXITY, "PMD.NPathComplexity",
        PMD.N_PATH_COMPLEXITY, "checkstyle:BooleanExpressionComplexity" })
    public static Number createNumber(final String aString) {
        if (aString == null) {
            return null;
        }

        if (StringUtils.isEmpty(aString)) {
            throw new NumberFormatException(LOGGER.getMessage(MessageCodes.UTIL_074));
        }

        // Need to deal with all possible hex prefixes here
        final String[] hexPrefixes = { HEX_PREFIX_LC, HEX_PREFIX_UC, HASH };
        final int length = aString.length();
        final int offset = aString.charAt(0) == '+' || aString.charAt(0) == '-' ? 1 : 0;
        final int hexDigits;

        int prefixLength = 0;

        for (final String prefix : hexPrefixes) {
            if (aString.startsWith(prefix, offset)) {
                prefixLength += prefix.length() + offset;
                break;
            }
        }

        if (prefixLength > 0) { // We have a hex number
            char firstSigDigit = 0; // Strip leading zeroes

            for (int index = prefixLength; index < length; index++) {
                firstSigDigit = aString.charAt(index);

                if (firstSigDigit != ZERO_CHAR) {
                    break;
                }

                prefixLength++;
            }

            hexDigits = length - prefixLength;

            if (hexDigits > 16 || hexDigits == 16 && firstSigDigit > '7') { // Too many for Long
                return createBigInteger(aString);
            }

            if (hexDigits > 8 || hexDigits == 8 && firstSigDigit > '7') { // Too many for an int
                return createLong(aString);
            }

            return createInteger(aString);
        }

        final int expPos = aString.indexOf('e') + aString.indexOf('E') + 1;
        final char lastChar = aString.charAt(length - 1);
        final boolean requestType = !Character.isDigit(lastChar) && lastChar != '.';
        final int decPos = aString.indexOf('.');
        final String mant;
        final String dec;
        final String exp;

        if (decPos > -1) { // There is a decimal point
            if (expPos > -1) { // There is an exponent
                if (expPos < decPos || expPos > length) { // Prevents double exponent causing IndexOutOfBoundsException
                    throw new NumberFormatException(LOGGER.getMessage(MessageCodes.UTIL_075, aString));
                }

                dec = aString.substring(decPos + 1, expPos);
            } else {
                dec = aString.substring(decPos + 1, requestType ? length - 1 : length);
            }

            mant = getMantissa(aString, decPos);
        } else {
            if (expPos > -1) {
                if (expPos > length) { // Prevents double exponent causing IndexOutOfBoundsException
                    throw new NumberFormatException(LOGGER.getMessage(MessageCodes.UTIL_075, aString));
                }

                mant = getMantissa(aString, expPos);
            } else {
                // No decimal, no exponent, but there may be a type character to remove
                mant = getMantissa(aString, requestType ? length - 1 : length);
            }

            dec = null;
        }

        if (requestType) {
            final String numeric;

            if (expPos > -1 && expPos < length - 1) {
                exp = aString.substring(expPos + 1, length - 1);
            } else {
                exp = null;
            }

            // Requesting a specific type
            numeric = aString.substring(0, length - 1);

            switch (lastChar) {
                case 'l':
                case 'L':
                    if (dec == null && exp == null &&
                            (!numeric.isEmpty() && numeric.charAt(0) == '-' && isNumeric(numeric.substring(1)) ||
                                    isNumeric(numeric))) {
                        try {
                            return createLong(numeric);
                        } catch (final NumberFormatException ignored) {
                            // Too big for a long
                        }

                        return createBigInteger(numeric);

                    }

                    throw new NumberFormatException(LOGGER.getMessage(MessageCodes.UTIL_075, aString));
                case 'f':
                case 'F':
                    try {
                        final Float f = createFloat(aString);

                        if (!f.isInfinite() && (f.floatValue() != 0.0F || isZero(mant, dec))) {
                            // If it's too big for a float or the float value = 0, and the string
                            // has non-zeros in it, then float does not have the precision we want
                            return f;
                        }

                    } catch (final NumberFormatException ignored) {
                        // Ignore the bad number
                    }

                    //$FALL-THROUGH$
                case 'd':
                case 'D':
                    try {
                        final Double d = createDouble(aString);

                        if (!d.isInfinite() && (d.doubleValue() != 0.0D || isZero(mant, dec))) {
                            return d;
                        }
                    } catch (final NumberFormatException ignored) {
                        // Ignore the bad number
                    }

                    try {
                        return createBigDecimal(numeric);
                    } catch (final NumberFormatException ignored) {
                        // Ignore the bad number
                    }

                    //$FALL-THROUGH$
                default:
                    throw new NumberFormatException(LOGGER.getMessage(MessageCodes.UTIL_075, aString));
            }
        }

        // User doesn't have a preference on the return type, so let's start small and go from there...
        if (expPos > -1 && expPos < length - 1) {
            exp = aString.substring(expPos + 1);
        } else {
            exp = null;
        }

        // No decimal point and no exponent
        if (dec == null && exp == null) {
            // Must be an Integer, Long, BigInteger
            try {
                return createInteger(aString);
            } catch (final NumberFormatException ignored) {
                // Ignore the bad number
            }

            try {
                return createLong(aString);
            } catch (final NumberFormatException ignored) {
                // Ignore the bad number
            }

            return createBigInteger(aString);
        }

        // Must be a Float, Double, BigDecimal
        try {
            final Float f = createFloat(aString);
            final Double d = createDouble(aString);

            if (!f.isInfinite() && (f.floatValue() != 0.0F || isZero(mant, dec)) && f.toString().equals(d.toString())) {
                return f;
            }

            if (!d.isInfinite() && (d.doubleValue() != 0.0D || isZero(mant, dec))) {
                final BigDecimal b = createBigDecimal(aString);

                if (b.compareTo(BigDecimal.valueOf(d)) == 0) {
                    return d;
                }

                return b;
            }
        } catch (final NumberFormatException ignored) {
            // Ignore the bad number
        }

        return createBigDecimal(aString);
    }

    /**
     * A utility method for {@link #createNumber(java.lang.String)}.
     * <p>
     * Returns mantissa of the given number.
     * </p>
     *
     * @param aString The string representation of the number
     * @param aStopPosition The position of the exponent or decimal point
     * @return The mantissa of the given number
     */
    private static String getMantissa(final String aString, final int aStopPosition) {
        final char firstChar = aString.charAt(0);
        final boolean hasSign = firstChar == '-' || firstChar == '+';

        return hasSign ? aString.substring(1, aStopPosition) : aString.substring(0, aStopPosition);
    }

    /**
     * A utility method for {@link #createNumber(java.lang.String)}.
     * <p>
     * This will check if the magnitude of the number is zero by checking if there are only zeros before and after the
     * decimal place.
     * </p>
     * <p>
     * Note: It is <strong>assumed</strong> that the input string has been converted to either a Float or Double with a
     * value of zero when this method is called. This eliminates invalid input for example {@code ".", ".D", ".e0"}.
     * </p>
     * <p>
     * Thus the method only requires checking if both arguments are null, empty or contain only zeros.
     * </p>
     * <p>
     * Given {@code string = aMantissa + "." + aDecimal}:
     * </p>
     * <ul>
     * <li>{@code true} if string is {@code "0.0"}
     * <li>{@code true} if string is {@code "0."}
     * <li>{@code true} if string is {@code ".0"}
     * <li>{@code false} otherwise (this assumes {@code "."} is not possible)
     * </ul>
     *
     * @param aMantissa The mantissa decimal digits before the decimal point (sign must be removed; never null)
     * @param aDecimal The decimal digits after the decimal point (exponent and type specifier removed; can be null)
     * @return True if the magnitude is zero; else, false
     */
    private static boolean isZero(final String aMantissa, final String aDecimal) {
        return isAllZeros(aMantissa) && isAllZeros(aDecimal);
    }

    /**
     * A utility method for {@link #createNumber(java.lang.String)}.
     * <p>
     * Returns {@code true} if s is {@code null} or empty.
     * </p>
     *
     * @param aString The string to check
     * @return True if it is all zeros or {@code null}; else, false
     */
    private static boolean isAllZeros(final String aString) {
        if (aString == null) {
            return true;
        }

        for (int index = aString.length() - 1; index >= 0; index--) {
            if (aString.charAt(index) != ZERO_CHAR) {
                return false;
            }
        }

        return true;
    }

    /**
     * Converts a {@link String} to a {@link Float}.
     * <p>
     * Returns {@code null} if the string is {@code null}.
     * </p>
     *
     * @param aString a {@link String} to convert
     * @return The converted {@link Float} (or null if the input is null)
     * @throws NumberFormatException If the value cannot be converted
     */
    public static Float createFloat(final String aString) {
        if (aString == null) {
            return null;
        }

        return Float.valueOf(aString);
    }

    /**
     * Converts a {@link String} to a {@link Double}.
     * <p>
     * Returns {@code null} if the string is {@code null}.
     * </p>
     *
     * @param aString A {@link String} to convert
     * @return The converted {@link Double} (or null if the input is null)
     * @throws NumberFormatException If the value cannot be converted
     */
    public static Double createDouble(final String aString) {
        if (aString == null) {
            return null;
        }

        return Double.valueOf(aString);
    }

    /**
     * Converts a {@link String} to a {@link Integer}, handling hex (0xhhhh) and octal (0dddd) notations. Note: a
     * leading zero means octal; spaces are not trimmed.
     * <p>
     * Returns {@code null} if the string is {@code null}.
     * </p>
     *
     * @param aString A {@link String} to convert
     * @return The converted {@link Integer} (or null if the input is null)
     * @throws NumberFormatException If the value cannot be converted
     */
    public static Integer createInteger(final String aString) {
        if (aString == null) {
            return null;
        }

        // Decode() handles 0xAABD and 0777 (hex and octal) as well
        return Integer.decode(aString);
    }

    /**
     * Converts a {@link String} to a {@link Long}; handles hex (0Xhhhh) and octal (0ddd) notations. Note: a leading
     * zero means octal; spaces are not trimmed.
     * <p>
     * Returns {@code null} if the string is {@code null}.
     * </p>
     *
     * @param aString A {@link String} to convert
     * @return The converted {@link Long} (or null if the input is null)
     * @throws NumberFormatException If the value cannot be converted
     */
    public static Long createLong(final String aString) {
        if (aString == null) {
            return null;
        }

        return Long.decode(aString);
    }

    /**
     * Converts a {@link String} to a {@link BigInteger}; handles hex (0x or #) and octal (0) notations.
     * <p>
     * Returns {@code null} if the string is {@code null}.
     * </p>
     *
     * @param aString A {@link String} to convert
     * @return The converted {@link BigInteger} (or null if the input is null)
     * @throws NumberFormatException If the value cannot be converted
     */
    public static BigInteger createBigInteger(final String aString) {
        if (aString == null) {
            return null;
        }

        if (aString.isEmpty()) {
            throw new NumberFormatException(LOGGER.getMessage(MessageCodes.UTIL_076));
        }

        int offset = 0; // Offset within string
        int radix = 10;
        boolean negate = false; // Need to negate later?
        final char char0 = aString.charAt(0);

        if (char0 == DASH_CHAR) {
            negate = true;
            offset = 1;
        } else if (char0 == PLUS_CHAR) {
            offset = 1;
        }

        if (aString.startsWith(HEX_PREFIX_LC, offset) || aString.startsWith(HEX_PREFIX_UC, offset)) { // Hex
            radix = 16;
            offset += 2;
        } else if (aString.startsWith(HASH, offset)) { // Alternative hex (allowed by Long/Integer)
            radix = 16;
            offset++;
        } else if (aString.startsWith("0", offset) && aString.length() > offset + 1) { // Octal if additional digits
            radix = 8;
            offset++;
        } // The default is to treat as decimal

        final BigInteger value = new BigInteger(aString.substring(offset), radix);
        return negate ? value.negate() : value;
    }

    /**
     * Converts a {@link String} to a {@link BigDecimal}.
     * <p>
     * Returns {@code null} if the string is {@code null}.
     * </p>
     *
     * @param aString A {@link String} to convert
     * @return The converted {@link BigDecimal} (or null if the input is null)
     * @throws NumberFormatException If the value cannot be converted
     */
    public static BigDecimal createBigDecimal(final String aString) {
        if (aString == null) {
            return null;
        }

        if (StringUtils.isEmpty(aString)) {
            throw new NumberFormatException(LOGGER.getMessage(MessageCodes.UTIL_074));
        }

        return new BigDecimal(aString);
    }

    /**
     * Returns the minimum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The minimum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static long min(final long... aArray) {
        validateArray(aArray);

        // Finds and returns min
        long min = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (aArray[index] < min) {
                min = aArray[index];
            }
        }

        return min;
    }

    /**
     * Returns the minimum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The minimum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static int min(final int... aArray) {
        validateArray(aArray);

        // Finds and returns min
        int min = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (aArray[index] < min) {
                min = aArray[index];
            }
        }

        return min;
    }

    /**
     * Returns the minimum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The minimum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static short min(final short... aArray) {
        validateArray(aArray);

        // Finds and returns min
        short min = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (aArray[index] < min) {
                min = aArray[index];
            }
        }

        return min;
    }

    /**
     * Returns the minimum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The minimum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static byte min(final byte... aArray) {
        validateArray(aArray);

        // Finds and returns min
        byte min = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (aArray[index] < min) {
                min = aArray[index];
            }
        }

        return min;
    }

    /**
     * Returns the minimum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The minimum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static double min(final double... aArray) {
        validateArray(aArray);

        // Finds and returns min
        double min = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (Double.isNaN(aArray[index])) {
                return Double.NaN;
            }

            if (aArray[index] < min) {
                min = aArray[index];
            }
        }

        return min;
    }

    /**
     * Returns the minimum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The minimum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static float min(final float... aArray) {
        validateArray(aArray);

        // Finds and returns min
        float min = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (Float.isNaN(aArray[index])) {
                return Float.NaN;
            }

            if (aArray[index] < min) {
                min = aArray[index];
            }
        }

        return min;
    }

    /**
     * Returns the maximum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The maximum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static long max(final long... aArray) {
        validateArray(aArray);

        // Finds and returns max
        long max = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (aArray[index] > max) {
                max = aArray[index];
            }
        }

        return max;
    }

    /**
     * Returns the maximum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The maximum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static int max(final int... aArray) {
        validateArray(aArray);

        // Finds and returns max
        int max = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (aArray[index] > max) {
                max = aArray[index];
            }
        }

        return max;
    }

    /**
     * Returns the maximum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The maximum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static short max(final short... aArray) {
        validateArray(aArray);

        // Finds and returns max
        short max = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (aArray[index] > max) {
                max = aArray[index];
            }
        }

        return max;
    }

    /**
     * Returns the maximum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The maximum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static byte max(final byte... aArray) {
        validateArray(aArray);

        // Finds and returns max
        byte max = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (aArray[index] > max) {
                max = aArray[index];
            }
        }

        return max;
    }

    /**
     * Returns the maximum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The maximum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static double max(final double... aArray) {
        validateArray(aArray);

        // Finds and returns max
        double max = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (Double.isNaN(aArray[index])) {
                return Double.NaN;
            }

            if (aArray[index] > max) {
                max = aArray[index];
            }
        }

        return max;
    }

    /**
     * Returns the maximum value in an array.
     *
     * @param aArray An array, must not be null or empty
     * @return The maximum value in the array
     * @throws NullPointerException If {@code array} is {@code null}
     * @throws IllegalArgumentException If {@code array} is empty
     */
    public static float max(final float... aArray) {
        validateArray(aArray);

        // Finds and returns max
        float max = aArray[0];

        for (int index = 1; index < aArray.length; index++) {
            if (Float.isNaN(aArray[index])) {
                return Float.NaN;
            }

            if (aArray[index] > max) {
                max = aArray[index];
            }
        }

        return max;
    }

    /**
     * Checks if the specified array is neither null nor empty.
     *
     * @param aArray The array to check
     * @throws IllegalArgumentException If {@code array} is empty
     * @throws NullPointerException If {@code array} is {@code null}
     */
    private static void validateArray(final Object aArray) {
        Objects.requireNonNull(aArray, "array");

        if (Array.getLength(aArray) != 0) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.UTIL_077));
        }
    }

    /**
     * Gets the minimum of three {@code long} values.
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The smallest of the values
     */
    public static long min(final long a1stValue, final long a2ndValue, final long a3rdValue) {
        long firstValue = a1stValue;

        if (a2ndValue < a1stValue) {
            firstValue = a2ndValue;
        }

        if (a3rdValue < firstValue) {
            firstValue = a3rdValue;
        }

        return firstValue;
    }

    /**
     * Gets the minimum of three {@code int} values.
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The smallest of the values
     */
    public static int min(final int a1stValue, final int a2ndValue, final int a3rdValue) {
        int firstValue = a1stValue;

        if (a2ndValue < a1stValue) {
            firstValue = a2ndValue;
        }

        if (a3rdValue < firstValue) {
            firstValue = a3rdValue;
        }

        return firstValue;
    }

    /**
     * Gets the minimum of three {@code short} values.
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The smallest of the values
     */
    public static short min(final short a1stValue, final short a2ndValue, final short a3rdValue) {
        short firstValue = a1stValue;

        if (a2ndValue < a1stValue) {
            firstValue = a2ndValue;
        }

        if (a3rdValue < firstValue) {
            firstValue = a3rdValue;
        }

        return firstValue;
    }

    /**
     * Gets the minimum of three {@code byte} values.
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The smallest of the values
     */
    public static byte min(final byte a1stValue, final byte a2ndValue, final byte a3rdValue) {
        byte firstValue = a1stValue;

        if (a2ndValue < a1stValue) {
            firstValue = a2ndValue;
        }

        if (a3rdValue < firstValue) {
            firstValue = a3rdValue;
        }

        return firstValue;
    }

    /**
     * Gets the minimum of three {@code double} values.
     * <p>
     * If any value is {@code NaN}, {@code NaN} is returned. Infinity is handled.
     * </p>
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The smallest of the values
     */
    public static double min(final double a1stValue, final double a2ndValue, final double a3rdValue) {
        return Math.min(Math.min(a1stValue, a2ndValue), a3rdValue);
    }

    /**
     * Gets the minimum of three {@code float} values.
     * <p>
     * If any value is {@code NaN}, {@code NaN} is returned. Infinity is handled.
     * </p>
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The smallest of the values
     */
    public static float min(final float a1stValue, final float a2ndValue, final float a3rdValue) {
        return Math.min(Math.min(a1stValue, a2ndValue), a3rdValue);
    }

    /**
     * Gets the maximum of three {@code long} values.
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The largest of the values
     */
    public static long max(final long a1stValue, final long a2ndValue, final long a3rdValue) {
        long firstValue = a1stValue;

        if (a2ndValue > a1stValue) {
            firstValue = a2ndValue;
        }

        if (a3rdValue > firstValue) {
            firstValue = a3rdValue;
        }

        return firstValue;
    }

    /**
     * Gets the maximum of three {@code int} values.
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The largest of the values
     */
    public static int max(final int a1stValue, final int a2ndValue, final int a3rdValue) {
        int firstValue = a1stValue;

        if (a2ndValue > a1stValue) {
            firstValue = a2ndValue;
        }

        if (a3rdValue > firstValue) {
            firstValue = a3rdValue;
        }

        return firstValue;
    }

    /**
     * Gets the maximum of three {@code short} values.
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The largest of the values
     */
    public static short max(final short a1stValue, final short a2ndValue, final short a3rdValue) {
        short firstValue = a1stValue;

        if (a2ndValue > a1stValue) {
            firstValue = a2ndValue;
        }

        if (a3rdValue > firstValue) {
            firstValue = a3rdValue;
        }

        return firstValue;
    }

    /**
     * Gets the maximum of three {@code byte} values.
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The largest of the values
     */
    public static byte max(final byte a1stValue, final byte a2ndValue, final byte a3rdValue) {
        byte firstValue = a1stValue;

        if (a2ndValue > a1stValue) {
            firstValue = a2ndValue;
        }

        if (a3rdValue > firstValue) {
            firstValue = a3rdValue;
        }

        return firstValue;
    }

    /**
     * Gets the maximum of three {@code double} values.
     * <p>
     * If any value is {@code NaN}, {@code NaN} is returned. Infinity is handled.
     * </p>
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The largest of the values
     */
    public static double max(final double a1stValue, final double a2ndValue, final double a3rdValue) {
        return Math.max(Math.max(a1stValue, a2ndValue), a3rdValue);
    }

    /**
     * Gets the maximum of three {@code float} values.
     * <p>
     * If any value is {@code NaN}, {@code NaN} is returned. Infinity is handled.
     * </p>
     *
     * @param a1stValue A first value
     * @param a2ndValue A second value
     * @param a3rdValue A third value
     * @return The largest of the values
     */
    public static float max(final float a1stValue, final float a2ndValue, final float a3rdValue) {
        return Math.max(Math.max(a1stValue, a2ndValue), a3rdValue);
    }

    /**
     * Checks if the CharSequence contains only Unicode digits. A decimal point is not a Unicode digit and returns
     * false.
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     * <p>
     * Note that the method does not allow for a leading sign, either positive or negative. Also, if a String passes the
     * numeric test, it may still generate a NumberFormatException when parsed by Integer.parseInt or Long.parseLong,
     * e.g. if the value is outside the range for int or long respectively.
     * </p>
     *
     * <pre>
     * NumberUtils.isNumeric(null)   = false
     * NumberUtils.isNumeric("")     = false
     * NumberUtils.isNumeric("  ")   = false
     * NumberUtils.isNumeric("123")  = true
     * NumberUtils.isNumeric("\u0967\u0968\u0969")  = true
     * NumberUtils.isNumeric("12 3") = false
     * NumberUtils.isNumeric("ab2c") = false
     * NumberUtils.isNumeric("12-3") = false
     * NumberUtils.isNumeric("12.3") = false
     * NumberUtils.isNumeric("-123") = false
     * NumberUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param aCharSequence The character sequence to check
     * @return True if only contains digits and is non-null; else, false
     */
    public static boolean isNumeric(final CharSequence aCharSequence) {
        if (aCharSequence == null || aCharSequence.length() == 0) {
            return false;
        }

        final int length = aCharSequence.length();

        for (int index = 0; index < length; index++) {
            if (!Character.isDigit(aCharSequence.charAt(index))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether the String a valid Java number.
     * <p>
     * Valid numbers include hexadecimal marked with the {@code 0x} or {@code 0X} qualifier, octal numbers, scientific
     * notation and numbers marked with a type qualifier (e.g. 123L).
     * </p>
     * <p>
     * Non-hexadecimal strings beginning with a leading zero are treated as octal values. Thus the string {@code 09}
     * will return {@code false}, since {@code 9} is not a valid octal value. However, numbers beginning with {@code 0.}
     * are treated as decimal.
     * </p>
     * <p>
     * {@code null} and empty/blank {@link String} will return {@code false}.
     * </p>
     * <p>
     * Note, {@link #createNumber(String)} should return a number for every input resulting in {@code true}.
     * </p>
     *
     * @param aString The {@link String} to check
     * @return True if the string is a correctly formatted number; else, false
     */
    @SuppressWarnings({ "checkstyle:BooleanExpressionComplexity", PMD.EXCESSIVE_METHOD_LENGTH,
        "PMD.ExcessiveMethodLength", PMD.NCSS_COUNT, "PMD.NcssCount", PMD.COGNITIVE_COMPLEXITY,
        "PMD.CognitiveComplexity", PMD.N_PATH_COMPLEXITY, "PMD.NPathComplexity" })
    public static boolean isCreatable(final String aString) {
        if (StringUtils.isEmpty(aString)) {
            return false;
        }

        final char[] chars = aString.toCharArray();

        int length = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;

        // Deal with any possible sign up front
        final int start = chars[0] == '-' || chars[0] == '+' ? 1 : 0;

        // Leading 0, skip if is a decimal number
        if (length > start + 1 && chars[start] == '0' && !aString.contains(PERIOD)) {
            if (chars[start + 1] == 'x' || chars[start + 1] == 'X') { // Leading 0x/0X
                int index = start + 2;

                if (index == length) {
                    return false; // If string == "0x"
                }

                // Checking hex (it can't be anything else)
                for (; index < chars.length; index++) {
                    if ((chars[index] < '0' || chars[index] > '9') && (chars[index] < 'a' || chars[index] > 'f') &&
                            (chars[index] < 'A' || chars[index] > 'F')) {
                        return false;
                    }
                }

                return true;
            }

            if (Character.isDigit(chars[start + 1])) {
                // Leading 0, but not hex, must be octal
                int index = start + 1;

                for (; index < chars.length; index++) {
                    if (chars[index] < '0' || chars[index] > '7') {
                        return false;
                    }
                }

                return true;
            }
        }

        length--; // Don't want to loop to the last char, check it afterwords

        // For type qualifiers
        int index = start;

        // Loop to the next to last char or to the last char if we need another digit to make a valid number (e.g.
        // chars[0..5] = "1234E")
        while (index < length || index < length + 1 && allowSigns && !foundDigit) {
            if (chars[index] >= '0' && chars[index] <= '9') {
                foundDigit = true;
                allowSigns = false;
            } else if (chars[index] == DOT_CHAR) {
                if (hasDecPoint || hasExp) {
                    // Two decimal points or decimal in exponent
                    return false;
                }

                hasDecPoint = true;
            } else if (chars[index] == 'e' || chars[index] == 'E') {
                // We've already taken care of hex.
                if (hasExp || !foundDigit) {
                    return false;
                }

                hasExp = true;
                allowSigns = true;
            } else if (chars[index] == '+' || chars[index] == '-') {
                if (!allowSigns) {
                    return false;
                }

                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }

            index++;
        }

        if (index < chars.length) {
            if (chars[index] >= '0' && chars[index] <= '9') {
                // No type qualifier, OK
                return true;
            }

            if (chars[index] == 'e' || chars[index] == 'E') {
                // Can't have an E at the last byte
                return false;
            }

            if (chars[index] == DOT_CHAR) {
                if (hasDecPoint || hasExp) {
                    // Two decimal points or decimal in exponent
                    return false;
                }

                // Single trailing decimal point after non-exponent is okay
                return foundDigit;
            }

            if (!allowSigns &&
                    (chars[index] == 'd' || chars[index] == 'D' || chars[index] == 'f' || chars[index] == 'F')) {
                return foundDigit;
            }

            if (chars[index] == 'l' || chars[index] == 'L') {
                // Not allowing L with an exponent or decimal point
                return foundDigit && !hasExp && !hasDecPoint;
            }

            // Last character is illegal
            return false;
        }

        // AllowSigns is true if the value ends in 'E'
        // Found digit to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }

    /**
     * Checks whether the given String is a parsable number.
     * <p>
     * Parsable numbers include those Strings understood by {@link Integer#parseInt(String)},
     * {@link Long#parseLong(String)}, {@link Float#parseFloat(String)} or {@link Double#parseDouble(String)}. This
     * method can be used instead of catching {@link java.text.ParseException} when calling one of those methods.
     * </p>
     * <p>
     * Hexadecimal and scientific notations are <strong>not</strong> considered parsable. See
     * {@link #isCreatable(String)} on those cases.
     * </p>
     * <p>
     * {@code null} and empty String will return {@code false}.
     * </p>
     *
     * @param aString The string to check.
     * @return True if the string is a parsable number; else, false
     */
    public static boolean isParsable(final String aString) {
        if (StringUtils.isEmpty(aString) || aString.charAt(aString.length() - 1) == DOT_CHAR) {
            return false;
        }

        if (aString.charAt(0) == DASH_CHAR) {
            if (aString.length() == SINGLE_INSTANCE) {
                return false;
            }

            return isParsableWithDecimalsParsing(aString, 1);
        }

        return isParsableWithDecimalsParsing(aString, 0);
    }

    /**
     * Determines if a string is parsable with decimal parsing.
     *
     * @param aString A string to parse
     * @param aBeginIndex A beginning index position
     * @return True if the supplied string is parsable; else, false
     */
    private static boolean isParsableWithDecimalsParsing(final String aString, final int aBeginIndex) {
        int decimalPoints = 0;

        for (int index = aBeginIndex; index < aString.length(); index++) {
            final boolean isDecimalPoint = aString.charAt(index) == '.';

            if (isDecimalPoint) {
                decimalPoints++;
            }

            if (decimalPoints > 1 || !isDecimalPoint && !Character.isDigit(aString.charAt(index))) {
                return false;
            }
        }

        return true;
    }
}
