
package info.freelibrary.util;

import java.util.function.Consumer;

import info.freelibrary.util.warnings.PMD;

/**
 * A consumer that captures a checked exception and throws it as a runtime exception.
 *
 * @param <T> The type that the function accepts
 * @param <E> The type of exception that the function wraps
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> extends Consumer<T> {

    @Override
    @SuppressWarnings(PMD.AVOID_CATCHING_GENERIC_EXCEPTION)
    default void accept(final T aType) {
        try {
            acceptThrows(aType);
        } catch (final Exception details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * A method that wraps any exceptions through by the consumer with a runtime exception.
     *
     * @param aType A type being accepted by the consumer
     * @throws E An exception thrown by the consumer
     */
    void acceptThrows(T aType) throws E;

    /**
     * Converts a ThrowingConsumer&lt;T, E&gt; into a standard Consumer&lt;T&gt; by wrapping exceptions in an
     * {@code I18nRuntimeException}.
     *
     * @param <T> A type being accepted by the consumer
     * @param <E> The type of exception that can be caught and wrapped
     * @param aThrowingConsumer The ThrowingConsumer instance
     * @return A Consumer&lt;T&gt; that automatically handles exceptions
     */
    static <T, E extends Exception> Consumer<T> wrap(final ThrowingConsumer<T, E> aThrowingConsumer) {
        return aThrowingConsumer;
    }
}
