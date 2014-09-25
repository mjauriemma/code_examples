import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class used to read files and normalize the words found within
 * 
 * @author Matthew Auriemma
 * 
 */

public class InvertedIndexBuilder {

	private int pending;
	private InvertedIndex index;
	private WorkQueue workers;

	public InvertedIndexBuilder(InvertedIndex index, WorkQueue workers) {
		this.workers = workers;
		this.index = index;
		pending = 0;
	}

	/**
	 * Reads a file line by line using a BufferedReader, splits the line and
	 * adds the words to the index with their place in the file and the name of
	 * the file they are located
	 * 
	 * @param dir
	 *            Path of the file to be read
	 * @param index
	 *            Object in which the index data structure is stored
	 */
	public void readFile(Path dir) {
		InvertedIndex local = new InvertedIndex();

		int count = 0;
		try (BufferedReader reader = Files.newBufferedReader(dir,
				Charset.forName("UTF-8"))) {
			String line = null;
			String file = dir.toString();

			while ((line = reader.readLine()) != null) {
				if (line != null) {
					String[] wordLine = line.split("\\s");

					for (String w : wordLine) {
						w = normalizeWord(w);
						if (!w.equals(null)
								&& (!w.equals("") && !w.equals(" "))) {
							count++;
							local.addWord(w, count, file);

						}
					}
				}
			}

			index.addAll(local);

		} catch (IOException | NullPointerException e) {
			System.out.println("Invalid path for file " + dir);
		}
	}

	/**
	 * Normalizes the word by replacing all the special characters with spaces
	 * and trims out the spaces and sends it to lower case
	 * 
	 * @param word
	 *            word to be normalized
	 * @return the normalized word or null if its an invalid string
	 */
	public static String normalizeWord(String word) {
		if (word != null && word != "") {
			word = word.replaceAll("_", "");
			return word.replaceAll("\\W", "").toLowerCase().trim();
		} else {
			return null;
		}
	}

	/**
	 * Recursively traverses an array and tests each element to determine
	 * whether or not it is a Directory. If it is, it calls itself on the array
	 * formed from the files in that directory
	 * 
	 * @param index
	 *            InvertedIndex object to pass into the readFile method
	 * 
	 * @param files
	 *            Array of files to traverse and test recursively
	 */

	public void traverse(Path dir) {

		try (DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {

			Driver.logger.debug("Traversing directory {}", dir);

			for (Path f : files) {

				Driver.logger.debug("Creating new Thread");
				workers.execute(new IndexMinion(f));
			}
		} catch (IOException e) {
			System.out.println("Unable to Read directory " + dir);
		}
		Driver.logger.debug("Main Finished Directory");
	}

	
	private class IndexMinion implements Runnable {

		private Path directory;

		public IndexMinion(Path directory) {
			Driver.logger.debug("Worker created for {}", directory);
			this.directory = directory;

			incrementPending();
		}

		/**
		 * Handles per-directory parsing. If a subdirectory is encountered, a
		 * new {@link DirectoryWorker} is created to handle that subdirectory.
		 */

		@Override
		public void run() {
			if (!Files.isDirectory(directory)) {
				if (directory.toString().toLowerCase().endsWith(".txt")) {
					readFile(directory);

					Driver.logger.debug("Read File {}", directory);
				} else {
					Driver.logger.debug("Invalid File {}", directory);

				}
			} else {

				try (DirectoryStream<Path> files = Files
						.newDirectoryStream(directory)) {

					for (Path f : files) {

						if (Files.isDirectory(f)) {
							workers.execute(new IndexMinion(f));
						} else {
							if (f.toString().toLowerCase().endsWith(".txt")) {
								readFile(f);
								Driver.logger.debug("Read File {}", f);
							}
						}
					}
				} catch (IOException e) {
					System.out.println("Unable to Read directory " + directory);
				}
				
			}
			decrementPending();
			
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
		Driver.logger.debug("Pending increased to {}", pending);
	}

	/**
	 * Indicates that we now have one less "pending" work, and will notify any
	 * waiting threads if we no longer have any more pending work left.
	 */
	private synchronized void decrementPending() {
		pending--;
		Driver.logger.debug("Pending decreased to {}", pending);

		if (pending <= 0) {
			notifyAll();
		}
	}

	/**
	 * Get method for the value of pending
	 * 
	 * @return pending
	 */
	public synchronized int getPending() {
		return pending;
	}

	/**
	 * Helper method, that helps a thread wait until all of the current work is
	 * done. This is useful for resetting the counters or shutting down the work
	 * queue.
	 */
	public synchronized void finish() {
		try {
			while (getPending() > 0) {
				Driver.logger.debug("Waiting until finished");
				this.wait();
			}
		} catch (InterruptedException e) {
			Driver.logger.debug("Finish interrupted", e);
		}
	}

}
