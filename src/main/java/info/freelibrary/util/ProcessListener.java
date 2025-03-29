
package info.freelibrary.util;

import info.freelibrary.util.warnings.PMD;

/**
 * An interface for a {@link Process} listener.
 */
@SuppressWarnings({ PMD.IMPLICIT_FUNCTIONAL_INTERFACE })
public interface ProcessListener {

    /**
     * An event to run once the supplied process has finished.
     *
     * @param aProcess The process to listener to
     */
    void processFinished(Process aProcess);

}
