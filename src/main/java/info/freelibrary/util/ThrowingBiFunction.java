
package info.freelibrary.util;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.util.function.BiFunction;

/**
 * A bi-function that throws a checked exception. This allows exception-throwing logic in lambda expressions while still
 * being compatible with standard functional interfaces.
 *
 * @param <F> The first input type
 * @param <S> The second input type
 * @param <R> The return type
 * @param <E> The type of Exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingBiFunction<F, S, R, E extends Exception> extends BiFunction<F, S, R> {

    /** The logger for the {@code ThrowingBiFunction}. */
    Logger LOGGER = LoggerFactory.getLogger(ThrowingBiFunction.class, MessageCodes.BUNDLE);

    /**
     * Applies this function to the supplied arguments using {@link #applyThrows(Object, Object)}.
     * <p>
     * When this method is called through the standard {@link BiFunction} API, checked exceptions thrown by
     * {@link #applyThrows(Object, Object)} are wrapped in an {@link I18nRuntimeException}. Runtime exceptions and
     * errors are rethrown unchanged.
     * <p>
     * Use this method indirectly when an API requires a standard {@code BiFunction<F, S, R>} and checked exceptions
     * should be converted into runtime exceptions:
     * <p>
     * {@snippet lang = java:
     * BiFunction<Path, Charset, String> reader = ThrowingBiFunction.wrap(Files::readString);
     *
     * try {
     *     final String contents = reader.apply(path, StandardCharsets.UTF_8);
     * } catch (final I18nRuntimeException details) {
     *     // The original checked exception is available as details.getCause().
     * }
     * }
     *
     * @param a1stInput The first input argument
     * @param a2ndInput The second input argument
     * @return The function result
     */
    @Override
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    default R apply(final F a1stInput, final S a2ndInput) {
        try {
            return applyThrows(a1stInput, a2ndInput);
        } catch (final RuntimeException | Error error) {
            throw error;
        } catch (final Exception details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * Applies this function to the supplied arguments, allowing the operation to throw a checked exception.
     * <p>
     * Use this method when the caller should handle or declare the checked exception directly:
     * <p>
     * {@snippet lang = java:
     * ThrowingBiFunction<Path, Charset, String, IOException> reader = Files::readString;
     *
     * try {
     *     final String contents = reader.applyThrows(path, StandardCharsets.UTF_8);
     * } catch (final IOException details) {
     *     // Handle the checked exception here.
     * }
     * }
     *
     * @param a1stInput The first input argument
     * @param a2ndInput The second input argument
     * @return The function result
     * @throws E If an error occurs during execution
     */
    R applyThrows(F a1stInput, S a2ndInput) throws E;

    /**
     * Converts a {@code ThrowingBiFunction<F, S, R, E>} into a standard {@link BiFunction}, allowing checked exceptions
     * to be propagated without requiring them to be declared.
     * <p>
     * Use this method when an API requires a standard {@code BiFunction<F, S, R>}, but the lambda or method reference
     * can throw a checked exception and you do not want that exception wrapped in an {@link I18nRuntimeException}:
     * <p>
     * {@snippet lang = java:
     *
     * BiFunction<Path, Charset, String> reader = ThrowingBiFunction.uncheck(Files::readString);
     *
     * final String contents = reader.apply(path, StandardCharsets.UTF_8);
     * }
     * <p>
     * The checked exception is not wrapped. It is rethrown without compiler-enforced handling. Runtime exceptions and
     * errors are rethrown unchanged.
     *
     * @param aFunc The function to adapt
     * @param <F> The first argument type
     * @param <S> The second argument type
     * @param <R> The result type
     * @param <E> The exception type
     * @return A {@code BiFunction} that propagates checked exceptions without compiler-enforced handling
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    static <F, S, R, E extends Exception> BiFunction<F, S, R> uncheck(final ThrowingBiFunction<F, S, R, E> aFunc) {
        return (first, second) -> {
            try {
                return aFunc.applyThrows(first, second);
            } catch (final RuntimeException | Error details) {
                throw details;
            } catch (final Exception details) {
                // Sneaky throw to avoid requiring try/catch or throws declaration
                return throwUnchecked(details);
            }
        };
    }

    /**
     * Returns a {@code ThrowingBiFunction} that rethrows an {@link I18nRuntimeException}'s cause as a checked
     * exception.
     * <p>
     * Use this method when a function may throw an {@code I18nRuntimeException} that wraps an original checked
     * exception, and the caller should handle that original checked exception again:
     * <p>
     * {@snippet lang = java:
     * ThrowingBiFunction<Path, Charset, String, IOException> wrappedReader = (path, charset) -> {
     *     try {
     *         return Files.readString(path, charset);
     *     } catch (final IOException details) {
     *         throw new I18nRuntimeException(details);
     *     }
     * };
     *
     * ThrowingBiFunction<Path, Charset, String, IOException> reader = ThrowingBiFunction.unwrap(wrappedReader);
     *
     * try {
     *     final String contents = reader.applyThrows(path, StandardCharsets.UTF_8);
     * } catch (final IOException details) {
     *     // Handle the original checked exception here.
     * }
     * }
     * <p>
     * If the {@code I18nRuntimeException}'s cause is not an {@link Exception}, the returned function throws an
     * {@link IllegalStateException}.
     *
     * @param <F> The first input type
     * @param <S> The second input type
     * @param <R> The return type
     * @param <E> The exception type
     * @param aFunc The {@code ThrowingBiFunction} to unwrap
     * @return A {@code ThrowingBiFunction} that rethrows wrapped causes as checked exceptions
     */
    @SuppressWarnings({ PMD.PRESERVE_STACK_TRACE, JDK.UNCHECKED, PMD.COMMENT_SIZE })
    static <F, S, R, E extends Exception> ThrowingBiFunction<F, S, R, E>
            unwrap(final ThrowingBiFunction<F, S, R, E> aFunc) {
        return (first, second) -> {
            try {
                return aFunc.applyThrows(first, second);
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
     * Returns a {@code ThrowingBiFunction} whose standard {@link BiFunction#apply(Object, Object)} behavior wraps
     * checked exceptions in an {@link I18nRuntimeException}.
     * <p>
     * Use this method when an API requires a standard {@code BiFunction<F, S, R>} and checked exceptions should be
     * converted into runtime exceptions:
     * <p>
     * {@snippet lang = java:
     * BiFunction<Path, Charset, String> reader = ThrowingBiFunction.wrap(Files::readString);
     *
     * try {
     *     final String contents = reader.apply(path, StandardCharsets.UTF_8);
     * } catch (final I18nRuntimeException details) {
     *     // The original checked exception is available as details.getCause().
     * }
     * }
     * <p>
     * Use {@link #uncheck(ThrowingBiFunction)} instead when checked exceptions should be propagated without wrapping.
     *
     * @param <F> The first input type
     * @param <S> The second input type
     * @param <R> The return type
     * @param <E> The exception type
     * @param aFunc The {@code ThrowingBiFunction} to wrap
     * @return A {@code ThrowingBiFunction} that wraps checked exceptions when used as a standard {@code BiFunction}
     */
    static <F, S, R, E extends Exception> ThrowingBiFunction<F, S, R, E>
            wrap(final ThrowingBiFunction<F, S, R, E> aFunc) {
        return aFunc;
    }

    /**
     * Throws a checked exception as an unchecked one.
     *
     * @param aException The exception to throw
     * @param <E> The exception type
     * @param <R> The return type
     * @return Nothing (this method never returns normally)
     * @throws E The unchecked exception
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    private static <E extends Throwable, R> R throwUnchecked(final Throwable aException) throws E {
        throw (E) aException;
    }

}
