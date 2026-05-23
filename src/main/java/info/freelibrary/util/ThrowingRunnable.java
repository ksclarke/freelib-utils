
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
     * Converts a {@code ThrowingRunnable} into a standard {@link Runnable}, allowing checked exceptions to be
     * propagated without requiring them to be declared.
     * <p>
     * Use this method when an API requires a standard {@code Runnable}, but the operation can throw a checked exception
     * and you do not want that exception wrapped in an {@link I18nRuntimeException}:
     * <p>
     * {@snippet lang = java:
     * executor.execute(ThrowingRunnable.uncheck(() -> Files.delete(path)));
     * }
     * <p>
     * Runtime exceptions and errors are rethrown unchanged.
     *
     * @param <E> The exception type to throw
     * @param aThrowingRunnable A throwing runnable
     * @return A standard {@code Runnable} that propagates checked exceptions without compiler-enforced handling
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    static <E extends Exception> Runnable uncheck(final ThrowingRunnable<E> aThrowingRunnable) {
        return () -> {
            try {
                aThrowingRunnable.run();
            } catch (final RuntimeException | Error details) {
                throw details;
            } catch (final Exception details) {
                throwUnchecked(details);
            }
        };
    }

    /**
     * Unwraps a {@code Runnable} by extracting and rethrowing the original checked exception wrapped in an
     * {@link I18nRuntimeException}.
     * <p>
     * Use this method when a runnable may have wrapped a checked exception in {@code I18nRuntimeException}, and the
     * caller should handle the original checked exception again:
     * <p>
     * {@snippet lang = java:
     * Runnable wrapped = ThrowingRunnable.wrap(() -> Files.delete(path));
     * ThrowingRunnable<IOException> unwrapped = ThrowingRunnable.unwrap(wrapped);
     *
     * try {
     *     unwrapped.run();
     * } catch (final IOException details) {
     *     // Handle the original checked exception here.
     * }
     * }
     *
     * @param <E> The exception type to unwrap
     * @param aRunnable The wrapped runnable
     * @return A {@code ThrowingRunnable} that rethrows the original exception
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
     * Converts a {@code ThrowingRunnable} into a standard {@link Runnable} that wraps checked exceptions in an
     * {@link I18nRuntimeException}.
     * <p>
     * Use this method when an API requires a standard {@code Runnable} and checked exceptions should be converted into
     * runtime exceptions:
     * <p>
     * {@snippet lang = java:
     * Runnable deleter = ThrowingRunnable.wrap(() -> Files.delete(path));
     *
     * try {
     *     deleter.run();
     * } catch (final I18nRuntimeException details) {
     *     // The original checked exception is available as details.getCause().
     * }
     * }
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
     * Throws the given checked exception as an unchecked exception.
     *
     * @param aThrowable A checked exception
     * @param <E> The throwable type
     * @throws E Always thrown
     */
    @SuppressWarnings(JDK.UNCHECKED)
    private static <E extends Throwable> void throwUnchecked(final Throwable aThrowable) throws E {
        throw (E) aThrowable;
    }

}
