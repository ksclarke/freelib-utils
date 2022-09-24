
package info.freelibrary.util;

import java.util.function.Consumer;

/**
 * A consumer that throws a runtime exception. This is not an ideal solution (as the PMD suppressions indicate), but it
 * provides a generic way to handle exceptions thrown in lambdas.
 *
 * @param <T> The type that the function accepts
 */
@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
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
     * @throws Exception An exception thrown by the consumer
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    void acceptThrows(T aType) throws Exception;

}
