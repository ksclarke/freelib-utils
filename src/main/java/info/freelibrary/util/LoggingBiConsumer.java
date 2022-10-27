
package info.freelibrary.util;

import static info.freelibrary.util.Constants.EMPTY;

import java.util.function.BiConsumer;

import info.freelibrary.util.warnings.PMD;

/**
 * A bi-consumer that logs any exceptions. This is not an ideal solution (as the PMD suppressions indicate), but it
 * provides a generic way to handle exceptions thrown in lambdas.
 *
 * @param <T> The first input that the function accepts
 * @param <U> The second input that the function accepts
 */
@FunctionalInterface
public interface LoggingBiConsumer<T, U> extends BiConsumer<T, U> {

    @Override
    @SuppressWarnings({ "PMD.AvoidCatchingGenericException", PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    default void accept(final T a1stInput, final U a2ndInput) {
        try {
            acceptLogs(a1stInput, a2ndInput);
        } catch (final Exception details) {
            final String message = details.getMessage() == null ? EMPTY : details.getMessage();
            LoggerFactory.getLogger(LoggingBiConsumer.class, MessageCodes.BUNDLE).error(details, message);
        }
    }

    /**
     * A method that logs any exceptions thrown by the bi-consumer.
     *
     * @param a1stInput A first input accepted by the consumer
     * @param a2ndInput A second input accepted by the consumer
     * @throws Exception If the function throws an exception
     */
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", PMD.SIGNATURE_DECLARE_THROWS_EXCEPTION })
    void acceptLogs(T a1stInput, U a2ndInput) throws Exception;

}
