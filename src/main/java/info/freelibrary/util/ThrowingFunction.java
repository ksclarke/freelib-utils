package info.freelibrary.util;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.util.function.Function;

/**
 * Represents a functional interface similar to {@code Function}, which allows the function to throw checked
 * exceptions.
 *
 * @param <T> The input type of the function
 * @param <R> The output type of the function
 * @param <E> The type of exception that may be thrown
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    /**
     * Converts a {@link ThrowingFunction} into a standard {@link Function}, allowing checked exceptions to be
     * propagated without requiring them to be declared in the method's {@code throws} clause.
     * <p>
     * Use this method when an API requires a standard {@code Function<T, R>}, but the lambda or method reference can
     * throw a checked exception:
     * <p>
     * {@snippet lang = java:
     * Function<Path, String> reader = ThrowingFunction.uncheck(path -> Files.readString(path));
     *
     * final String contents = reader.apply(path);
     *}
     * <p>
     * The checked exception is not wrapped. It is rethrown without compiler-enforced handling.
     *
     * @param <T> The input type of the function
     * @param <R> The output type of the function
     * @param <E> The type of exception potentially thrown by the provided function
     * @param aFunc The throwing function to be wrapped as a standard function
     * @return A {@link Function} that applies the provided throwing function and rethrows any exceptions as unchecked
     */
    @SuppressWarnings(PMD.AVOID_CATCHING_GENERIC_EXCEPTION)
    static <T, R, E extends Exception> Function<T, R> uncheck(final ThrowingFunction<T, R, E> aFunc) {
        return type -> {
            try {
                return aFunc.apply(type);
            } catch (final Exception details) {
                return ThrowingFunction.<RuntimeException, R>throwUnchecked(details);
            }
        };
    }

    /**
     * Rethrows a given {@link Throwable} without wrapping it in another exception, bypassing checked exception
     * handling.
     * <p>
     * This method is primarily useful inside adapter methods or lambdas that need to return a value while still
     * propagating a checked exception without declaring it:
     * <p>
     * {@snippet lang = java:
     * Function<Path, String> reader = path -> {
     *     try {
     *         return Files.readString(path);
     *     } catch (final IOException details) {
     *         return ThrowingFunction.throwUnchecked(details);
     *     }
     * };
     *}
     * <p>
     * This method never returns normally.
     *
     * @param <E> The type of {@link Throwable} being rethrown
     * @param <R> The return type of the method
     * @param aThrowable The exception to be rethrown
     * @return Nothing; this method never returns normally
     * @throws E The type of exception that will be thrown
     */
    @SuppressWarnings(JDK.UNCHECKED)
    static <E extends Throwable, R> R throwUnchecked(final Throwable aThrowable) throws E {
        throw (E) aThrowable; // Unchecked cast to rethrow without wrapping
    }

    /**
     * Applies the function to the given input and produces a result, allowing the operation to throw a checked
     * exception.
     * <p>
     * Use this method when the caller should handle or declare the checked exception directly:
     * <p>
     * {@snippet lang = java:
     * ThrowingFunction<Path, String, IOException> reader = path -> Files.readString(path);
     *
     * try {
     *     final String contents = reader.apply(path);
     * } catch (final IOException details) {
     *     // Handle the checked exception here.
     * }
     *}
     *
     * @param aType The input to the function
     * @return The result of applying the function to the given input
     * @throws E If an exception occurs during the function execution
     */
    R apply(T aType) throws E;
}
