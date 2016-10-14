
package info.freelibrary.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Watcher that can register listeners that will fire when the process has finished.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class ProcessWatcher extends Thread {

    private final List<ProcessListener> myListeners = new ArrayList<>();

    private final Process myProcess;

    /**
     * Creates a watcher for a {@link Process} started by the supplied {@link ProcessBuilder}.
     *
     * @param aProcessBuilder A builder that will spawn the process to watch
     */
    public ProcessWatcher(final ProcessBuilder aProcessBuilder) throws IOException {
        myProcess = aProcessBuilder.start();
    }

    @Override
    public void run() {
        try {
            myProcess.waitFor();

            for (final ProcessListener listener : myListeners) {
                listener.processFinished(myProcess);
            }
        } catch (final InterruptedException details) {
        } finally {
            IOUtils.closeQuietly(myProcess.getInputStream());
            IOUtils.closeQuietly(myProcess.getOutputStream());
            IOUtils.closeQuietly(myProcess.getErrorStream());
        }
    }

    /**
     * Add a {@link info.freelibrary.util.ProcessListener} to this watcher.
     *
     * @param aListener A process listener to add to this watcher
     */
    public ProcessWatcher addListener(final ProcessListener aListener) {
        myListeners.add(aListener);
        return this;
    }

    /**
     * Remove a {@link info.freelibrary.util.ProcessListener} to this watcher.
     *
     * @param aListener A process listener to remove from this watcher
     */
    public ProcessWatcher removeListener(final ProcessListener aListener) {
        myListeners.remove(aListener);
        return this;
    }

}
