/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

/**
 * Allows timing of the execution of any block of code.
 */
public final class Stopwatch {

	private boolean myTimerIsRunning;

	private long myStart;

	private long myStop;

	/**
	 * Start the stopwatch.
	 * 
	 * @throws IllegalStateException if the stopwatch is already running.
	 */
	public void start() {
		if (myTimerIsRunning) { throw new IllegalStateException(
				"Must stop before calling start again."); }

		myStart = System.currentTimeMillis();
		myTimerIsRunning = true;
	}

	/**
	 * Stop the stopwatch.
	 * 
	 * @throws IllegalStateException if the stopwatch is not already running.
	 */
	public void stop() {
		if (!myTimerIsRunning) { throw new IllegalStateException(
				"Cannot stop if not currently running."); }

		myStop = System.currentTimeMillis();
		myTimerIsRunning = false;
	}

	/**
	 * Stop the stopwatch (as soon as possible) after a certain number of
	 * seconds.
	 * 
	 * @throws IllegalStateException if the stopwatch is not already running.
	 */
	public void stopAfter(int aSecondsCount) {
		if (!myTimerIsRunning) { throw new IllegalStateException(
				"Cannot stop if not currently running"); }

		while (((System.currentTimeMillis() - myStart) / 1000) < aSecondsCount);

		myStop = System.currentTimeMillis();
		myTimerIsRunning = false;
	}

	/**
	 * Express the "reading" on the stopwatch in seconds.
	 * 
	 * @return Time elapsed in stopwatch in seconds
	 * 
	 * @throws IllegalStateException if the Stopwatch has never been used, or if
	 *         the stopwatch is still running.
	 */
	public String getSeconds() {
		StringBuilder result = new StringBuilder();
		long timeGap = myStop - myStart;

		result.append(timeGap / 1000);
		result.append(" secs, ");

		result.append(timeGap % 1000);
		result.append(" msecs");

		if (myTimerIsRunning) { throw new IllegalStateException(
				"Must stop first."); }

		return result.toString();
	}

	/**
	 * Express the "reading" on the stopwatch in milliseconds.
	 * 
	 * @return Time elapsed in stopwatch in milliseconds
	 * 
	 * @throws IllegalStateException if the Stopwatch has never been used, or if
	 *         the stopwatch is still running.
	 */
	public String getMilliseconds() {
		StringBuilder result = new StringBuilder();
		result.append(myStop - myStart);
		result.append(" msecs");

		if (myTimerIsRunning) { throw new IllegalStateException(
				"Must stop first."); }

		return result.toString();
	}

	/**
	 * Returns a string representation of the time elapsed in milliseconds.
	 * 
	 * @return Number of milliseconds elapsed in stopwatch
	 */
	public String toString() {
		return "Stopwatch milliseconds elapsed: " + getMilliseconds();
	}
}
