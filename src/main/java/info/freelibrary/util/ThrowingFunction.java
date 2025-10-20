
package info.freelibrary.util;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.util.function.Function;

/**
 * Represents a functional interface similar to {@code Function}, which allows the function to throw checked exceptions.
 *
 * @param <T> the input type of the function
 * @param <R> the output type of the function
 * @param <E> the type of exception that may be thrown
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    /**
     * Converts a {@link ThrowingFunction} into a standard {@link Function}, allowing checked exceptions to be thrown
     * without requiring them to be declared in the method's {@code throws} clause. This utility bypasses checked
     * exception handling via rethrowing.
     *
     * @param <T> the input type of the function
     * @param <R> the output type of the function
     * @param <E> the type of exception potentially thrown by the provided function
     * @param aFunc the throwing function to be wrapped as a standard function
     * @return A {@link Function} that applies the provided throwing function and rethrows any exceptions as unchecked
     */
    @SuppressWarnings(PMD.AVOID_CATCHING_GENERIC_EXCEPTION)
    static <T, R, E extends Exception> Function<T, R> sneaky(final ThrowingFunction<T, R, E> aFunc) {
        return type -> {
            try {
                return aFunc.apply(type);
            } catch (final Exception details) {
                return ThrowingFunction.<RuntimeException, R>sneaky(details);
            }
        };
    }

    /**
     * Rethrows a given {@link Throwable} without wrapping it in another exception, bypassing checked exception
     * handling. This allows throwing checked exceptions without declaring them in the method's {@code throws} clause.
     *
     * @param <E> the type of {@link Throwable} being rethrown
     * @param <R> the return type of the method
     * @param aThrowable the exception to be rethrown
     * @return this method does not return a value as it always throws the provided exception
     * @throws E the type of exception that will be thrown
     */
    @SuppressWarnings(JDK.UNCHECKED)
    static <E extends Throwable, R> R sneaky(final Throwable aThrowable) throws E {
        throw (E) aThrowable; // Unchecked cast to rethrow without wrapping
    }

    /**
     * Applies the function to the given input and produces a result.
     *
     * @param aType the input to the function
     * @return the result of applying the function to the given input
     * @throws E if an exception occurs during the function execution
     */
    R apply(T aType) throws E;
}
