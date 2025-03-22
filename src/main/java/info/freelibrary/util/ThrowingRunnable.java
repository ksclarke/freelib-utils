
package info.freelibrary.util;

import info.freelibrary.util.warnings.PMD;

/**
 * A functional interface that can capture {@code Runnable} exceptions and throw them as runtime exceptions.
 *
 * @param <E> The exception that the function wraps
 */
@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {

    /**
     * A runnable that throws an exception.
     *
     * @throws E Any exception that a runnable could throw
     */
    void run() throws E;

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
}
