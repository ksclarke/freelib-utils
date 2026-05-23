
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
     * Applies this function to the given arguments, potentially throwing an exception.
     *
     * @param a1stInput The first input argument
     * @param a2ndInput The second input argument
     * @return The function result
     * @throws E If an error occurs during execution
     */
    R applyThrows(F a1stInput, S a2ndInput) throws E;

    /**
     * Returns a standard BiFunction that rethrows checked exceptions as unchecked using a sneaky throw.
     *
     * @param aFunc The function to adapt
     * @param <F> The first argument type
     * @param <S> The second argument type
     * @param <R> The result type
     * @param <E> The exception type
     * @return A BiFunction that rethrows checked exceptions as unchecked
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    static <F, S, R, E extends Exception> BiFunction<F, S, R> sneaky(final ThrowingBiFunction<F, S, R, E> aFunc) {
        return (first, second) -> {
            try {
                return aFunc.applyThrows(first, second);
            } catch (final RuntimeException | Error details) {
                throw details;
            } catch (final Exception details) {
                // Sneaky throw to avoid requiring try/catch or throws declaration
                return sneakyThrow(details);
            }
        };
    }

    /**
     * Unwraps a ThrowingBiFunction, converting the unchecked exception into a checked exception.
     *
     * @param <F> The first input type
     * @param <S> The second input type
     * @param <R> The return type
     * @param <E> The exception type
     * @param aFunc The ThrowingBiFunction to wrap
     * @return A standard BiFunction that handles exceptions
     */
    @SuppressWarnings({ PMD.PRESERVE_STACK_TRACE, JDK.UNCHECKED })
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
     * Wraps a ThrowingBiFunction, converting checked exceptions into runtime exceptions.
     *
     * @param <F> The first input type
     * @param <S> The second input type
     * @param <R> The return type
     * @param <E> The exception type
     * @param aFunc The ThrowingBiFunction to wrap
     * @return A BiFunction that handles exceptions
     */
    static <F, S, R, E extends Exception> ThrowingBiFunction<F, S, R, E>
            wrap(final ThrowingBiFunction<F, S, R, E> aFunc) {
        return aFunc::applyThrows;
    }

    /**
     * Sneakily throws a checked exception as an unchecked one.
     *
     * @param aException The exception to throw
     * @param <E> The exception type
     * @param <R> The return type
     * @return Nothing – this method never returns normally
     * @throws E The sneaky exception
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    private static <E extends Throwable, R> R sneakyThrow(final Throwable aException) throws E {
        throw (E) aException;
    }

}
