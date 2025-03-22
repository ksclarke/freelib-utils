
package info.freelibrary.util;

import java.util.function.BiConsumer;

import info.freelibrary.util.warnings.PMD;

/**
 * A bi-consumer that throws a runtime exception. This is not an ideal solution (as the PMD suppressions indicate), but
 * it provides a generic way to handle exceptions thrown in lambdas.
 *
 * @param <T> The first input that the function accepts
 * @param <U> The second input that the function accepts
 * @param <E> The type of Exception that can be caught and wrapped
 */
@FunctionalInterface
public interface ThrowingBiConsumer<T, U, E extends Exception> extends BiConsumer<T, U> {

    @Override
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    default void accept(final T a1stInput, final U a2ndInput) {
        try {
            acceptThrows(a1stInput, a2ndInput);
        } catch (final Exception details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * A method that wraps any exceptions through by the bi-consumer with a runtime exception.
     *
     * @param a1stInput A first input accepted by the consumer
     * @param a2ndInput A second input accepted by the consumer
     * @throws E An exception thrown by the consumer
     */
    @SuppressWarnings({ PMD.SIGNATURE_DECLARE_THROWS_EXCEPTION })
    void acceptThrows(T a1stInput, U a2ndInput) throws E;

    /**
     * Converts a ThrowingBiConsumer&lt;T, U, E&gt; into a standard BiConsumer&lt;T, U&gt; by wrapping exceptions in an
     * {@code I18nRuntimeException}.
     *
     * @param <T> The first type passed to the consumer
     * @param <U> The second type passed to the consumer
     * @param <E> The type of exception that's wrapped by the consumer
     * @param aThrowingBiConsumer The ThrowingBiConsumer instance
     * @return A BiConsumer&lt;T, U&gt; that automatically handles exceptions
     */
    static <T, U, E extends Exception> BiConsumer<T, U> wrap(final ThrowingBiConsumer<T, U, E> aThrowingBiConsumer) {
        return aThrowingBiConsumer;
    }
}
