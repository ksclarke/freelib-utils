
package info.freelibrary.util;

/**
 * An interface for a {@link Process} listener.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public interface ProcessListener {

    /**
     * An event to run once the supplied process has finished.
     *
     * @param aProcess The process to listener to
     */
    void processFinished(Process aProcess);

}
