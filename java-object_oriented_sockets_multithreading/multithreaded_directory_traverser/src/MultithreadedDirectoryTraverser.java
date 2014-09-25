import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

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
		
		TreeSet<Path> temp;
		
			try (DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {
				
				temp = new TreeSet<>();
				
				for (Path f : files)

					if (f.toFile().isDirectory()) {
						workers.execute(new DirectoryWorker(f, ext));
					} else {
						if (f.toString().toLowerCase().endsWith(ext)) {
							temp.add(f);
							}
					}
				lock.lockWrite();
				updatePaths(temp);
				lock.unlockWrite();
				
			} catch (IOException e) {
				System.out.println("Unable to Read directory " + dir);
			}
			
			
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
	 * Updates the number of files and bytes found. Note that we had to make
	 * this method synchronized to work in the multithreaded version.
	 * 
	 * @param files
	 * @param bytes
	 */
	private synchronized void updatePaths(TreeSet<Path> path) {
		lock.lockWrite();
		for(Path p: path){
			paths.add(p);
		}
		lock.unlockWrite();
		logger.debug("Paths added to paths");
	}


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

		
		/**
		 * Handles per-directory parsing. If a subdirectory is encountered, a new
		 * {@link DirectoryWorker} is created to handle that subdirectory.
		 */
		
		@Override
		public void run() {
			TreeSet<Path> temp;
				try (DirectoryStream<Path> files = Files.newDirectoryStream(directory)) {

					temp = new TreeSet<>();
					
					for (Path f : files)

						if (Files.isDirectory(f)) {
							workers.execute(new DirectoryWorker(f, ext));
						} else {
							if (f.toString().toLowerCase().endsWith(ext)) {
								temp.add(f);
								}
						}
					
					lock.lockWrite();
					updatePaths(temp);
					lock.unlockWrite();
					
				} catch (IOException e) {
					System.out.println("Unable to Read directory " + directory);
				}
	
				
				
				decrementPending();
			}
	}

}
