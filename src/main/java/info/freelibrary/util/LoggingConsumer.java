
package info.freelibrary.util;

import java.util.function.Consumer;

/**
 * A consumer that logs any exceptions. This is not an ideal solution (as the PMD suppressions indicate), but it
 * provides a generic way to handle exceptions thrown in lambdas.
 *
 * @param <T> The type that the function accepts
 */
@FunctionalInterface
public interface LoggingConsumer<T> extends Consumer<T> {

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    default void accept(final T aType) {
        try {
            acceptLogs(aType);
        } catch (final Exception details) {
            LoggerFactory.getLogger(LoggingConsumer.class, MessageCodes.BUNDLE).error(details, details.getMessage());
        }
    }

    /**
     * A method that logs any exceptions thrown by the consumer.
     *
     * @param aType A type being accepted by the consumer
     */
    void acceptLogs(T aType);

}
