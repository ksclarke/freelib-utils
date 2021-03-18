
package info.freelibrary.util;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;

/**
 * A utility class for working with bytes.
 */
public final class ByteUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteUtils.class, MessageCodes.BUNDLE);

    private ByteUtils() {
    }

    /**
     * Returns a byte array as a list of bytes.
     *
     * @param aByteArray An array of bytes
     * @return A list of bytes
     */
    public static List<Byte> asList(final byte[] aByteArray) {
        if (aByteArray.length == 0) {
            return Collections.emptyList();
        }

        return new ByteArrayAsList(aByteArray);
    }

    /**
     * Returns a list of bytes as an array or bytes.
     *
     * @param aByteList A list of bytes
     * @return An array of bytes
     */
    public static byte[] asArray(final List<Byte> aByteList) {
        final byte[] bytes = new byte[aByteList.size()];

        for (int index = 0; index < aByteList.size(); index++) {
            bytes[index] = aByteList.get(index);
        }

        return bytes;
    }

    private static int indexOf(final byte[] aByteArray, final byte aByte, final int aStart, final int aEnd) {
        for (int index = aStart; index < aEnd; index++) {
            if (aByteArray[index] == aByte) {
                return index;
            }
        }

        return -1;
    }

    private static int lastIndexOf(final byte[] aByteArray, final byte aByte, final int aStart, final int aEnd) {
        for (int index = aEnd - 1; index >= aStart; index--) {
            if (aByteArray[index] == aByte) {
                return index;
            }
        }

        return -1;
    }

    /**
     * Reverses the elements of {@code array}. This is equivalent to {@code
     * Collections.reverse(Bytes.asList(array))}, but is likely to be more efficient.
     *
     * @param aArray An array of bytes
     */
    public static void reverse(final byte[] aArray) {
        reverse(aArray, 0, aArray.length - 1);
    }

    /**
     * Reverses the elements of the array.
     *
     * @param aByteArray An array of bytes
     * @param aStart A start position
     * @param aEnd A end position
     * @throws IndexOutOfBoundsException If the start or end are out of range of the array
     */
    public static void reverse(final byte[] aByteArray, final int aStart, final int aEnd) {
        Objects.requireNonNull(aByteArray);
        checkPositionIndexes(aStart, aEnd, aByteArray.length);

        for (int index = aStart, jndex = aEnd; index < jndex; index++, jndex--) {
            final byte tmpByte = aByteArray[index];

            aByteArray[index] = aByteArray[jndex];
            aByteArray[jndex] = tmpByte;
        }
    }

    /**
     * Check range position indices.
     *
     * @param aStart A start position
     * @param aEnd An end position
     * @param aSize A size
     */
    private static void checkPositionIndexes(final int aStart, final int aEnd, final int aSize) {
        if (aStart < 0 || aStart >= aSize) {
            throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.UTIL_070, aStart));
        }

        if (aEnd < 0 || aEnd >= aSize) {
            throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.UTIL_071, aEnd));
        }

        if (aStart > aEnd) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.UTIL_069, aStart, aEnd));
        }
    }

    private static class ByteArrayAsList extends AbstractList<Byte> implements RandomAccess, Serializable {

        private static final long serialVersionUID = 0;

        final byte[] myArray;

        final int myStart;

        final int myEnd;

        ByteArrayAsList(final byte[] aByteArray) {
            this(aByteArray, 0, aByteArray.length);
        }

        ByteArrayAsList(final byte[] aByteArray, final int aStart, final int aEnd) {
            myArray = aByteArray.clone();
            myStart = aStart;
            myEnd = aEnd;
        }

        @Override
        public int size() {
            return myEnd - myStart;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Byte get(final int aIndex) {
            if (aIndex < 0 || aIndex >= size()) {
                throw new IndexOutOfBoundsException(aIndex);
            }

            return myArray[myStart + aIndex];
        }

        @Override
        public boolean contains(final Object aObject) {
            return aObject instanceof Byte && ByteUtils.indexOf(myArray, (Byte) aObject, myStart, myEnd) != -1;
        }

        @Override
        public int indexOf(final Object aObject) {
            if (aObject instanceof Byte) {
                final int index = ByteUtils.indexOf(myArray, (Byte) aObject, myStart, myEnd);

                if (index >= 0) {
                    return index - myStart;
                }
            }

            return -1;
        }

        @Override
        public int lastIndexOf(final Object aObject) {
            if (aObject instanceof Byte) {
                final int index = ByteUtils.lastIndexOf(myArray, (Byte) aObject, myStart, myEnd);

                if (index >= 0) {
                    return index - myStart;
                }
            }

            return -1;
        }

        @Override
        public Byte set(final int aIndex, final Byte aByte) {
            if (aIndex < 0 || aIndex >= size()) {
                throw new IndexOutOfBoundsException(aIndex);
            }

            final byte oldValue = myArray[myStart + aIndex];

            myArray[myStart + aIndex] = Objects.requireNonNull(aByte);

            return oldValue;
        }

        @Override
        public List<Byte> subList(final int aStart, final int aEnd) {
            checkPositionIndexes(aStart, aEnd, size());

            if (aStart == aEnd) {
                return Collections.emptyList();
            }

            return new ByteArrayAsList(myArray, myStart + aStart, myStart + aEnd);
        }

        @Override
        public boolean equals(final Object aObject) {
            if (aObject == this) {
                return true;
            }

            if (aObject instanceof ByteArrayAsList) {
                final ByteArrayAsList list = (ByteArrayAsList) aObject;
                final int size = size();

                if (list.size() != size) {
                    return false;
                }

                for (int index = 0; index < size; index++) {
                    if (myArray[myStart + index] != list.myArray[list.myStart + index]) {
                        return false;
                    }
                }

                return true;
            }

            return super.equals(aObject);
        }

        @Override
        public int hashCode() {
            int result = 1;

            for (int index = myStart; index < myEnd; index++) {
                result = 31 * result + myArray[index];
            }

            return result;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder(size() * 5);

            builder.append('[').append(myArray[myStart]);

            for (int index = myStart + 1; index < myEnd; index++) {
                builder.append(", ").append(myArray[index]);
            }

            return builder.append(']').toString();
        }

        @SuppressWarnings("unused")
        byte[] toByteArray() {
            return Arrays.copyOfRange(myArray, myStart, myEnd);
        }
    }
}
