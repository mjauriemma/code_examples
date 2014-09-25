
public class Temp {

	
	import java.io.IOException;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.util.Collections;
	import java.util.HashSet;
	import java.util.Set;
	import java.util.TreeSet;

	import org.apache.logging.log4j.Level;
	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;

	/**
	 
	 * 
	 * 
	 */

	public class MultithreadedDirectoryTraverser {

		private static final Logger logger = LogManager.getLogger();
		private final TreeSet<Path> paths;
		private final WorkQueue workers;
		private int pending;
		private MultiReaderLock lock;

		/**
		 * Initializes the number of files found and total bytes found to 0. Also
		 * initializes a work queue with {@link WorkQueue#DEFAULT} threads.
		 */
		public MultithreadedDirectoryTraverser() {
			workers = new WorkQueue();
			pending = 0;
			lock = new MultiReaderLock();
			paths = new TreeSet<Path>();
		}

		public void traverseDirectory(Path dir, String ext) {
			if (dir.toFile().isDirectory()) {
				workers.execute(new DirectoryWorker(dir, ext));

			}
			if (dir.toFile().isFile()) {
				lock.lockWrite();
				paths.add(dir);
				lock.unlockWrite();
			}

		}

		/**
		 * Resets the counters, allowing this object to be easily reused if desired.
		 * Note that we had to make this method synchronized in the multithreaded
		 * version.
		 */
		public synchronized void reset() {
			finish();
			lock.lockWrite();
			paths.clear();
			lock.unlockWrite();
			pending = 0;
			logger.debug("Set Cleared");
		}

		/**
		 * Returns the number of files found since the last reset. Note that we had
		 * to make this method synchronized in the multithreaded version.
		 * 
		 * @return number of files
		 */
		public synchronized Set<Path> getPaths() {
			logger.debug("Getting files");
			finish();
			lock.lockRead();
			Set<Path> unModPaths = Collections.unmodifiableSet(paths);
			lock.unlockRead();
			return unModPaths;
		}

		/**
		 * Helper method, that helps a thread wait until all of the current work is
		 * done. This is useful for resetting the counters or shutting down the work
		 * queue.
		 */
		public synchronized void finish() {
			try {
				while (pending > 0) {
					logger.debug("Waiting until finished");
					this.wait();
				}
			} catch (InterruptedException e) {
				logger.debug("Finish interrupted", e);
			}
		}

		/**
		 * Will shutdown the work queue after all the current pending work is
		 * finished. Necessary to prevent our code from running forever in the
		 * background.
		 */
		public synchronized void shutdown() {
			logger.debug("Shutting down");
			finish();
			workers.shutdown();
		}

		/**
		 * Handles per-directory parsing. If a subdirectory is encountered, a new
		 * {@link DirectoryWorker} is created to handle that subdirectory.
		 */
		private class DirectoryWorker implements Runnable {

			private Path directory;
			private String ext;

			public DirectoryWorker(Path directory, String ext) {
				logger.debug("Worker created for {}", directory);
				this.directory = directory;
				this.ext = ext;

				// Indicate we now have "pending" work to do. This is necessary
				// so we know when our threads are "done", since we can no longer
				// call the join() method on them.
				incrementPending();
			}

			@Override
			public void run() {
				try {
					HashSet<Path> paths = new HashSet<Path>();

					for (Path path : Files.newDirectoryStream(directory)) {
						if (Files.isDirectory(path)) {
							// Note that we now create a new runnable object and add
							// it
							// to the work queue.
							workers.execute(new DirectoryWorker(path, ext));
						} else {
							// Note that we are adding to LOCAL variables, so we
							// only lock ONCE when we are done.
							if (path.toFile().toString().toLowerCase()
									.endsWith(ext)) {
								paths.add(path);
							}
						}
					}

					// Now that we are done, go ahead and lock to update the
					// counter values.
					lock.lockWrite();
					updateCounters(paths);
					lock.unlockWrite();
					// Indicate that we no longer have "pending" work to do.
					decrementPending();
				} catch (IOException e) {
					logger.warn("Unable to parse {}", directory);
					logger.catching(Level.DEBUG, e);
				}

				logger.debug("Minion finished {}", directory);
			}
		}

		/**
		 * Updates the number of files and bytes found. Note that we had to make
		 * this method synchronized to work in the multithreaded version.
		 * 
		 * @param files
		 * @param bytes
		 */
		private synchronized void updateCounters(HashSet<Path> path) {
			lock.lockWrite();
			for(Path p: path){
				paths.add(p);
			}
			lock.unlockWrite();
			logger.debug("Paths added to paths");
		}

		/**
		 * Indicates that we now have additional "pending" work to wait for. We need
		 * this since we can no longer call join() on the threads. (The threads keep
		 * running forever in the background.)
		 * 
		 * We made this a synchronized method in the outer class, since locking on
		 * the "this" object within an inner class does not work.
		 */
		private synchronized void incrementPending() {
			pending++;
			logger.debug("Pending is now {}", pending);
		}

		/**
		 * Indicates that we now have one less "pending" work, and will notify any
		 * waiting threads if we no longer have any more pending work left.
		 */
		private synchronized void decrementPending() {
			pending--;
			logger.debug("Pending is now {}", pending);

			if (pending <= 0) {
				this.notifyAll();
			}
		}

		/**
		 * Runs a simple example to demonstrate this class. Try changing the path to
		 * your root directory, and see how long it takes!
		 * 
		 * @param args
		 *            unused
		 */
		public static void main(String[] args) {
			MultithreadedDirectoryTraverser demo = new MultithreadedDirectoryTraverser();
			String s = ".";
			demo.traverseDirectory(Paths.get(s), ".txt");
			
			System.out.println(demo.getPaths() + " files");

			demo.shutdown();
		}
	}
	
	
}
