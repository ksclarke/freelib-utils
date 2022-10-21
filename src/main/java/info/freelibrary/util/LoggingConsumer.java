
package info.freelibrary.util;

import static info.freelibrary.util.Constants.EMPTY;

import java.util.function.Consumer;

import info.freelibrary.util.warnings.PMD;

/**
 * A consumer that logs any exceptions. This is not an ideal solution (as the PMD suppressions indicate), but it
 * provides a generic way to handle exceptions thrown in lambdas.
 *
 * @param <T> The type that the function accepts
 */
@FunctionalInterface
public interface LoggingConsumer<T> extends Consumer<T> {

    @Override
    @SuppressWarnings({ "PMD.AvoidCatchingGenericException", PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    default void accept(final T aType) {
        try {
            acceptLogs(aType);
        } catch (final Exception details) {
            final String message = details.getMessage() == null ? EMPTY : details.getMessage();
            LoggerFactory.getLogger(LoggingConsumer.class, MessageCodes.BUNDLE).error(details, message);
        }
    }

    /**
     * A method that logs any exceptions thrown by the consumer.
     *
     * @param aType A type being accepted by the consumer
     * @throws Exception If the function throws an exception
     */
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", PMD.SIGNATURE_DECLARE_THROWS_EXCEPTION })
    void acceptLogs(T aType) throws Exception;

}
