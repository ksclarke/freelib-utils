
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
     * <p>
     * Use this method when you want the caller to handle or declare the checked exception:
     * <p>
     * {@snippet lang = java:
     * ThrowingConsumer<Path, IOException> deleter = Files::delete;
     *
     * try {
     *     deleter.acceptThrows(path);
     * } catch (final IOException details) {
     *     // Handle the checked exception here.
     * }
     * }
     *
     * @param aType A type being accepted by the consumer
     * @throws E An exception thrown by the consumer
     */
    void acceptThrows(T aType) throws E;

    /**
     * Returns a standard Consumer that rethrows checked exceptions as unchecked using an unchecked throw.
     * <p>
     * Use this method when an API requires a standard {@link Consumer}, but the lambda or method reference can throw a
     * checked exception, and you do not want that exception wrapped in an {@link I18nRuntimeException}:
     * <p>
     * {@snippet lang = java:
     * paths.forEach(ThrowingConsumer.uncheck(path -> Files.delete(path)));
     * }
     *
     * @param <T> The accepted type
     * @param <E> The exception type
     * @param aConsumer The ThrowingConsumer to wrap
     * @return A Consumer that rethrows checked exceptions as unchecked
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    static <T, E extends Exception> Consumer<T> uncheck(final ThrowingConsumer<T, E> aConsumer) {
        return input -> {
            try {
                aConsumer.acceptThrows(input);
            } catch (final RuntimeException | Error details) {
                throw details;
            } catch (final Exception details) {
                throwUnchecked(details);
            }
        };
    }

    /**
     * Returns a ThrowingConsumer that rethrows {@code I18nRuntimeException}'s cause as a checked exception.
     * <p>
     * Use this method when a throwing consumer may have been invoked through {@link #accept(Object)}, causing checked
     * exceptions to be wrapped in {@link I18nRuntimeException}, and you want to expose the original checked exception
     * again:
     * <p>
     * {@snippet lang = java:
     * ThrowingConsumer<Path, IOException> deleter = Files::delete;
     * ThrowingConsumer<Path, IOException> unwrapped = ThrowingConsumer.unwrap(deleter);
     *
     * try {
     *     unwrapped.acceptThrows(path);
     * } catch (final IOException details) {
     *     // Handle the original checked exception here.
     * }
     * }
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
     * <p>
     * Use this method when an API requires a standard {@link Consumer} and you want checked exceptions to be converted
     * into {@link I18nRuntimeException}:
     * <p>
     * {@snippet lang = java:
     * Consumer<Path> deleter = ThrowingConsumer.wrap(path -> Files.delete(path));
     *
     * try {
     *     paths.forEach(deleter);
     * } catch (final I18nRuntimeException details) {
     *     // The original checked exception is available as details.getCause().
     * }
     * }
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
     * Throws a checked exception as an unchecked one.
     *
     * @param aException The exception to throw
     * @param <E> The exception type
     * @throws E The unchecked exception (will never be caught by the compiler)
     */
    @SuppressWarnings(JDK.UNCHECKED)
    private static <E extends Throwable> void throwUnchecked(final Throwable aException) throws E {
        throw (E) aException;
    }
}
