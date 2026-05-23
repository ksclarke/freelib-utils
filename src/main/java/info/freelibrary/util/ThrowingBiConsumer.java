package info.freelibrary.util;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.util.function.BiConsumer;

/**
 * A bi-consumer that throws a runtime exception. It provides a generic way to handle exceptions thrown in lambdas.
 *
 * @param <T> The first input that the function accepts
 * @param <U> The second input that the function accepts
 * @param <E> The type of Exception that can be caught and wrapped
 */
@FunctionalInterface
public interface ThrowingBiConsumer<T, U, E extends Exception> extends BiConsumer<T, U> {

    /** The logger for the {@code ThrowingBiConsumer}. */
    Logger LOGGER = LoggerFactory.getLogger(ThrowingBiConsumer.class, MessageCodes.BUNDLE);

    /**
     * Accepts the supplied inputs using {@link #acceptThrows(Object, Object)}.
     * <p>
     * When this method is called through the standard {@link BiConsumer} API, checked exceptions thrown by
     * {@link #acceptThrows(Object, Object)} are wrapped in an {@link I18nRuntimeException}. Runtime exceptions and
     * errors are rethrown unchanged.
     * <p>
     * Use this method indirectly when an API requires a standard {@code BiConsumer<T, U>} and checked exceptions should
     * be converted into runtime exceptions:
     * <p>
     * {@snippet lang = java:
     * BiConsumer<Path, String> writer = ThrowingBiConsumer.wrap(Files::writeString);
     *
     * try {
     *     writer.accept(path, contents);
     * } catch (final I18nRuntimeException details) {
     *     // The original checked exception is available as details.getCause().
     * }
     *}
     *
     * @param a1stInput A first input accepted by the consumer
     * @param a2ndInput A second input accepted by the consumer
     */
    @Override
    @SuppressWarnings({PMD.AVOID_CATCHING_GENERIC_EXCEPTION})
    default void accept(final T a1stInput, final U a2ndInput) {
        try {
            acceptThrows(a1stInput, a2ndInput);
        } catch (final RuntimeException | Error details) {
            throw details;
        } catch (final Exception details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * Accepts the supplied inputs, allowing the operation to throw a checked exception.
     * <p>
     * Use this method when the caller should handle or declare the checked exception directly:
     * <p>
     * {@snippet lang = java:
     * ThrowingBiConsumer<Path, String, IOException> writer = Files::writeString;
     *
     * try {
     *     writer.acceptThrows(path, contents);
     * } catch (final IOException details) {
     *     // Handle the checked exception here.
     * }
     *}
     *
     * @param a1stInput A first input accepted by the consumer
     * @param a2ndInput A second input accepted by the consumer
     * @throws E An exception thrown by the consumer
     */
    void acceptThrows(T a1stInput, U a2ndInput) throws E;

    /**
     * Converts a {@code ThrowingBiConsumer<F, S, E>} into a standard {@link BiConsumer}, allowing checked exceptions to
     * be propagated without requiring them to be declared.
     * <p>
     * Use this method when an API requires a standard {@code BiConsumer<F, S>}, but the lambda or method reference can
     * throw a checked exception and you do not want that exception wrapped in an {@link I18nRuntimeException}:
     * <p>
     * {@snippet lang = java:
     * filesAndContents.forEach(ThrowingBiConsumer.uncheck((path, contents) -> Files.writeString(path, contents)));
     *}
     * <p>
     * The checked exception is not wrapped. It is rethrown without compiler-enforced handling.
     *
     * @param <F> A first input
     * @param <S> A second input
     * @param <E> The thrown exception
     * @param aFunc The supplied {@code ThrowingBiConsumer}
     * @return A standard {@code BiConsumer} that propagates checked exceptions without compiler-enforced handling
     */
    @SuppressWarnings({PMD.AVOID_CATCHING_GENERIC_EXCEPTION})
    static <F, S, E extends Exception> BiConsumer<F, S> uncheck(final ThrowingBiConsumer<F, S, E> aFunc) {
        return (first, second) -> {
            try {
                aFunc.acceptThrows(first, second);
            } catch (final RuntimeException | Error details) {
                throw details;
            } catch (final Exception details) {
                throwUnchecked(details);
            }
        };
    }

    /**
     * Returns a {@code ThrowingBiConsumer} that rethrows an {@link I18nRuntimeException}'s cause as a checked
     * exception.
     * <p>
     * Use this method when a bi-consumer may throw an {@code I18nRuntimeException} that wraps an original checked
     * exception, and the caller should handle that original checked exception again:
     * <p>
     * {@snippet lang = java:
     * ThrowingBiConsumer<Path, String, IOException> wrappedWriter = (path, contents) -> {
     *     try {
     *         Files.writeString(path, contents);
     *     } catch (final IOException details) {
     *         throw new I18nRuntimeException(details);
     *     }
     * };
     *
     * ThrowingBiConsumer<Path, String, IOException> writer =
     *         ThrowingBiConsumer.unwrap(wrappedWriter);
     *
     * try {
     *     writer.acceptThrows(path, contents);
     * } catch (final IOException details) {
     *     // Handle the original checked exception here.
     * }
     *}
     * <p>
     * If the {@code I18nRuntimeException}'s cause is not an {@link Exception}, the returned consumer throws an
     * {@link IllegalStateException}.
     *
     * @param <F> The first input type
     * @param <S> The second input type
     * @param <E> The exception type
     * @param aFunc The {@code ThrowingBiConsumer} to unwrap
     * @return A {@code ThrowingBiConsumer} that rethrows wrapped causes as checked exceptions
     */
    @SuppressWarnings({PMD.PRESERVE_STACK_TRACE})
    static <F, S, E extends Exception> ThrowingBiConsumer<F, S, E> unwrap(final ThrowingBiConsumer<F, S, E> aFunc) {
        return (first, second) -> {
            try {
                aFunc.acceptThrows(first, second);
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
     * Returns a {@code ThrowingBiConsumer} whose standard {@link BiConsumer#accept(Object, Object)} behavior wraps
     * checked exceptions in an {@link I18nRuntimeException}.
     * <p>
     * Use this method when an API requires a standard {@code BiConsumer<F, S>} and checked exceptions should be
     * converted into runtime exceptions:
     * <p>
     * {@snippet lang = java:
     * BiConsumer<Path, String> writer = ThrowingBiConsumer.wrap(Files::writeString);
     *
     * try {
     *     writer.accept(path, contents);
     * } catch (final I18nRuntimeException details) {
     *     // The original checked exception is available as details.getCause().
     * }
     *}
     * <p>
     * Use {@link #uncheck(ThrowingBiConsumer)} instead when checked exceptions should be propagated without wrapping.
     *
     * @param <F> The first input type
     * @param <S> The second input type
     * @param <E> The exception type
     * @param aFunc The {@code ThrowingBiConsumer} to wrap
     * @return A {@code ThrowingBiConsumer} that wraps checked exceptions when used as a standard {@code BiConsumer}
     */
    static <F, S, E extends Exception> ThrowingBiConsumer<F, S, E> wrap(final ThrowingBiConsumer<F, S, E> aFunc) {
        return aFunc;
    }

    /**
     * Throws a checked exception as an unchecked one.
     *
     * @param aException The exception to throw
     * @param <E> The exception type
     * @throws E The unchecked exception
     */
    @SuppressWarnings({JDK.UNCHECKED})
    private static <E extends Throwable> void throwUnchecked(final Throwable aException) throws E {
        throw (E) aException;
    }
}
