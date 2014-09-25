import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Class for storing the Inverted Index. Contains functions to add words to the
 * index and to write the index to a file
 * 
 * @author Matthew Auriemma
 * 
 */

public class InvertedIndex {

	
	private final MultiReaderLock indexLock;

	// Data Structure for the Inverted Index
	private final TreeMap<String, TreeMap<String, ArrayList<Integer>>> index;

	public InvertedIndex() {
		indexLock = new MultiReaderLock();
		index = new TreeMap<>();
	}

	/**
	 * Adds words to the data structure. Checks if the word already exists in
	 * the data structure and, if so, updates it. Else it just adds a new Word
	 * object
	 * 
	 * @param word
	 *            String word to be updated or added to the data structure
	 * @param count
	 *            Where the word appears in the file
	 * @param file
	 *            Path where the word appears
	 */

	public void addWord(String word, int count, String file) {
		
		indexLock.lockWrite();
		
		if (index.containsKey(word)) {
			if (index.get(word).containsKey(file)){
				index.get(word).get(file).add(count);
			}
			else{
			index.get(word).put(file, new ArrayList<Integer>());
			index.get(word).get(file).add(count);
			}
		} else {
			index.put(word, new TreeMap<String, ArrayList<Integer>>());
			index.get(word).put(file, new ArrayList<Integer>());
			index.get(word).get(file).add(count);
		}
		
		indexLock.unlockWrite();

	}

	/**
	 * Method to write out a Reader dataBase to a file named either
	 * invertedindex.txt or what the user specified. Uses BufferedWriter
	 * 
	 * @param iName
	 *            String with either a filename specified by user, or null
	 */

	public void write(String iName) {

		if (iName == null) {
			iName = "invertedindex.txt";
		}

		indexLock.lockRead();

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(iName),
				Charset.forName("UTF-8"));) {
			for (String w : index.keySet()) {
				writer.write(w + " ");
				writer.newLine();
				for (String p : index.get(w).keySet()) {
					writer.write('"' + p + '"' + ", ");
					for (int i = 0; i < index.get(w).get(p).size(); i++) {
						writer.write(index.get(w).get(p).get(i).toString());
						if (i < index.get(w).get(p).size() - 1) {
							writer.write(", ");
						}
					}
					writer.newLine();
				}

				writer.newLine();
				writer.flush();
			}
		} catch (IOException e) {
			System.out.println("Writing Failed in file " + iName);
		}

		indexLock.unlockRead();

	}

	/**
	 * Searches inverted index for queries found on each line of the query file
	 * and updates the search results or creates a new SearchResult object.
	 * 
	 * @param queries
	 *            Array containing the words from one line of the query file
	 */

	public ArrayList<SearchResult> search(String[] queries) {

		HashMap<String, SearchResult> resultMap = new HashMap<>();
		ArrayList<SearchResult> results = new ArrayList<>();
		indexLock.lockRead();
		for (String q : queries) {
			for (String w : index.tailMap(q).keySet()) {
				if (!w.startsWith(q)) {
					break;
				}
				for (String p : index.get(w).keySet()) {
					if (resultMap.containsKey(p)) {
						resultMap.get(p).update(index.get(w).get(p).size(),
								index.get(w).get(p).get(0));
					} else {
						resultMap.put(p, new SearchResult(p, index.get(w)
								.get(p).size(), index.get(w).get(p).get(0)));
					}
				}
			}
		}
		indexLock.unlockRead();
		results.addAll(resultMap.values());
		Collections.sort(results);
		return results;
	}

	public void addAll(InvertedIndex other) {

		indexLock.lockWrite();
		Driver.logger.debug("Adding to global");
		for (String key : other.index.keySet()) {

			if (!this.index.containsKey(key)) {
				this.index.put(key, other.index.get(key));
			} else {
				for (String p : other.index.get(key).keySet()) {
					if (this.index.get(key).containsKey(p)) {
						this.index.get(key).get(p)
								.addAll(other.index.get(key).get(p));
					} else {
						this.index.get(key).put(p, other.index.get(key).get(p));
					}
				}
			}

		}
		Driver.logger.debug("Done adding to global");
		indexLock.unlockWrite();

	}
	

}
