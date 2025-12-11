package info.freelibrary.util;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.util.function.BiConsumer;

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

    /** The logger for the {@code ThrowingBiConsumer}. */
    Logger LOGGER = LoggerFactory.getLogger(ThrowingBiConsumer.class, MessageCodes.BUNDLE);

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
     * A method that wraps any exceptions through by the bi-consumer with a runtime exception.
     *
     * @param a1stInput A first input accepted by the consumer
     * @param a2ndInput A second input accepted by the consumer
     * @throws E An exception thrown by the consumer
     */
    void acceptThrows(T a1stInput, U a2ndInput) throws E;

    /**
     * Returns a BiConsumer that sneaks any thrown checked exception.
     *
     * @param <F> A first input
     * @param <S> A second input
     * @param <E> The thrown exception
     * @param aFunction The supplied ThrowingBiConsumer
     * @return A standard BiConsumer
     */
    @SuppressWarnings({PMD.AVOID_CATCHING_GENERIC_EXCEPTION})
    static <F, S, E extends Exception> BiConsumer<F, S> sneaky(final ThrowingBiConsumer<F, S, E> aFunction) {
        return (first, second) -> {
            try {
                aFunction.acceptThrows(first, second);
            } catch (final Exception details) {
                sneakyThrow(details);
            }
        };
    }

    /**
     * Unwraps a ThrowingBiConsumer, converting an unchecked exceptions into a checked exception.
     *
     * @param <F> The first input type
     * @param <S> The second input type
     * @param <E> The exception type
     * @param aFunc The ThrowingBiConsumer to wrap
     * @return A ThrowingBiConsumer that handles exceptions
     */
    @SuppressWarnings({PMD.PRESERVE_STACK_TRACE, JDK.UNCHECKED})
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
     * Wraps a ThrowingBiFunction, converting checked exceptions into runtime exceptions.
     *
     * @param <F> The first input type
     * @param <S> The second input type
     * @param <E> The exception type
     * @param aFunc The ThrowingBiFunction to wrap
     * @return A ThrowingBiConsumer that handles exceptions
     */
    static <F, S, E extends Exception> ThrowingBiConsumer<F, S, E> wrap(final ThrowingBiConsumer<F, S, E> aFunc) {
        return aFunc::acceptThrows;
    }

    /**
     * Sneakily throws a checked exception as an unchecked one.
     *
     * @param aException The exception to throw
     * @param <E> The exception type
     * @throws E The sneaky exception
     */
    @SuppressWarnings({JDK.UNCHECKED})
    private static <E extends Throwable> void sneakyThrow(final Throwable aException) throws E {
        throw (E) aException;
    }
}
