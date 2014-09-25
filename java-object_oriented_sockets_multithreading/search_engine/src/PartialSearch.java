import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Class that reads query file and calls search function on the inverted index
 * and stores the results in a data structure. Also contains the function used
 * for writing out the search results
 * 
 * @author Matthew Auriemma
 * 
 */

public class PartialSearch {

	// Data Structure for the Search Results

	private final LinkedHashMap<String, ArrayList<SearchResult>> results;

	private MultiReaderLock searchLock;

	private int pending;

	private WorkQueue workers;

	private InvertedIndex index;

	/**
	 * Constructor
	 */

	public PartialSearch(InvertedIndex index, WorkQueue workers) {
		searchLock = new MultiReaderLock();
		results = new LinkedHashMap<>();
		pending = 0;
		this.workers = workers;
		this.index = index;
	}

	/**
	 * Reads the file given in program args and searches the index using the
	 * queries found in the file
	 * 
	 * @param filename
	 *            Path of the file to read the queries from
	 * @param index
	 *            InvertedIndex object to call the Search function within
	 */

	public void readQuery(Path filename) {

		try (BufferedReader reader = Files.newBufferedReader(filename,
				Charset.forName("UTF-8"))) {
			String line = null;
			searchLock.lockWrite();
			while ((line = reader.readLine()) != null) {
				if (line != null) {

					results.put(line, new ArrayList<SearchResult>());

					workers.execute(new SearchMinion(line));

				}

				Driver.logger.debug("Read Query File {}", filename);
			}
			searchLock.unlockWrite();

		} catch (IOException e) {
			System.out.println("Unable to read query file " + filename);
		}
	}

	/**
	 * Creates a new worker for the provided string to search the index
	 * 
	 * @param query
	 *            String to search for
	 */

	public ArrayList<SearchResult> search(String query) {
		String[] words = query.split("\\s");
		for (String w : words) {
			w = InvertedIndexBuilder.normalizeWord(w);
		}
		ArrayList<SearchResult> temp = index.search(words);
		temp = index.search(words);
		return temp;
	}
	

	/**
	 * Writes the search results to the given or default file name
	 * 
	 * @param iName
	 *            Name of file to write to
	 */

	public void write(String iName) {

		if (iName == null) {
			iName = "searchresults.txt";
		}

		searchLock.lockRead();

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(iName),
				Charset.forName("UTF-8"));) {

			for (String w : results.keySet()) {
				writer.write(w + " ");

				if (results.get(w) == null) {
					writer.newLine();
					writer.newLine();

				} else {
					writer.newLine();
					for (SearchResult s : results.get(w)) {
						writer.write(s.toString());
						writer.newLine();
					}
					writer.newLine();
					writer.flush();
				}
			}

		} catch (IOException e) {
			System.out.println("Writing Failed in file " + iName);
		}

		searchLock.unlockRead();
	}

	private class SearchMinion implements Runnable {

		String query;

		public SearchMinion(String query) {
			Driver.logger.debug("Worker created for {}", query);
			this.query = query;
			incrementPending();
		}

		@Override
		public void run() {

			String[] words = query.split("\\s");
			for (String w : words) {
				w = InvertedIndexBuilder.normalizeWord(w);
			}
			ArrayList<SearchResult> temp = index.search(words);

			searchLock.lockWrite();
			results.get(query).addAll(temp);
			searchLock.unlockWrite();
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
