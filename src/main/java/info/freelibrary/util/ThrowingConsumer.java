
package info.freelibrary.util;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.util.function.Consumer;

/**
 * A consumer that captures a checked exception and throws it as a runtime exception.
 *
 * @param <T> The type that the function accepts
 * @param <E> The type of exception that the function wraps
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> extends Consumer<T> {

    /** The logger for the {@code ThrowingConsumer}. */
    Logger LOGGER = LoggerFactory.getLogger(ThrowingConsumer.class, MessageCodes.BUNDLE);

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
     * Returns a standard Consumer that rethrows checked exceptions as unchecked using a sneaky throw.
     *
     * @param <T> The accepted type
     * @param <E> The exception type
     * @param aConsumer The ThrowingConsumer to wrap
     * @return A Consumer that rethrows checked exceptions as unchecked
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    static <T, E extends Exception> Consumer<T> sneaky(final ThrowingConsumer<T, E> aConsumer) {
        return input -> {
            try {
                aConsumer.acceptThrows(input);
            } catch (final RuntimeException | Error details) {
                throw details;
            } catch (final Exception details) {
                sneakyThrow(details);
            }
        };
    }

    /**
     * Returns a ThrowingConsumer that rethrows {@code I18nRuntimeException}'s cause as a checked exception.
     *
     * @param <T> The accepted type
     * @param <E> The exception type
     * @param aConsumer The ThrowingConsumer to unwrap
     * @return A ThrowingConsumer that unwraps the runtime exception
     */
    @SuppressWarnings({ JDK.UNCHECKED, PMD.PRESERVE_STACK_TRACE })
    static <T, E extends Exception> ThrowingConsumer<T, E> unwrap(final ThrowingConsumer<T, E> aConsumer) {
        return input -> {
            try {
                aConsumer.accept(input);
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

    /**
     * Sneakily throws a checked exception as an unchecked one.
     *
     * @param aException The exception to throw
     * @param <E> The exception type
     * @throws E The unchecked exception (will never be caught by the compiler)
     */
    @SuppressWarnings(JDK.UNCHECKED)
    private static <E extends Throwable> void sneakyThrow(final Throwable aException) throws E {
        throw (E) aException;
    }
}
