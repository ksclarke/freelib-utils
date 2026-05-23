
package info.freelibrary.util;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

/**
 * A functional interface that can capture {@code Runnable} exceptions and throw them as runtime exceptions.
 *
 * @param <E> The exception that the function wraps
 */
@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {

    /** The logger for the {@code ThrowingRunnable}. */
    Logger LOGGER = LoggerFactory.getLogger(ThrowingRunnable.class, MessageCodes.BUNDLE);

    /**
     * A runnable that throws an exception.
     *
     * @throws E Any exception that a runnable could throw
     */
    void run() throws E;

    /**
     * Sneakily rethrows checked exceptions as unchecked without wrapping.
     *
     * @param <E> The exception type to throw
     * @param aThrowingRunnable A throwing runnable
     * @return A standard Runnable that sneakily throws exceptions
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    static <E extends Exception> Runnable sneaky(final ThrowingRunnable<E> aThrowingRunnable) {
        return () -> {
            try {
                aThrowingRunnable.run();
            } catch (final RuntimeException | Error details) {
                throw details;
            } catch (final Exception details) {
                sneakyThrow(details);
            }
        };
    }

    /**
     * Unwraps a ThrowingRunnable by extracting and rethrowing the original checked exception wrapped in an
     * {@code I18nRuntimeException}.
     *
     * @param <E> The exception type to unwrap
     * @param aRunnable The wrapped runnable
     * @return A ThrowingRunnable that rethrows the original exception
     */
    @SuppressWarnings({ JDK.UNCHECKED, PMD.PRESERVE_STACK_TRACE })
    static <E extends Exception> ThrowingRunnable<E> unwrap(final Runnable aRunnable) {
        return () -> {
            try {
                aRunnable.run();
            } catch (final I18nRuntimeException details) {
                final Throwable cause = details.getCause();

                if (cause instanceof final Exception exception) {
                    throw (E) exception;
                }

                throw new IllegalStateException(LOGGER.getMessage(MessageCodes.UTIL_078), cause);
            }
        };
    }

    /**
     * A utility to wrap ThrowingRunnable(s) and capture their checked exceptions.
     *
     * @param <E> The exception type that is caught and wrapped in an {@code I18nRuntimeException}
     * @param aThrowingRunnable A runnable that throws checked exceptions
     * @return A runnable that turns checked exceptions into unchecked exceptions
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    static <E extends Exception> Runnable wrap(final ThrowingRunnable<E> aThrowingRunnable) {
        return () -> {
            try {
                aThrowingRunnable.run();
            } catch (final Exception details) {
                throw new I18nRuntimeException(details);
            }
        };
    }

    /**
     * Sneakily throws the given checked exception as an unchecked exception.
     *
     * @param aThrowable A checked exception
     * @param <E> The throwable type
     * @throws E Always thrown
     */
    @SuppressWarnings(JDK.UNCHECKED)
    private static <E extends Throwable> void sneakyThrow(final Throwable aThrowable) throws E {
        throw (E) aThrowable;
    }

}
