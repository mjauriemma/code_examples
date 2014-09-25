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

	/**
	 * Constructor
	 */

	public PartialSearch() {
		results = new LinkedHashMap<>();
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

	public void readQuery(Path filename, InvertedIndex index) {

		try (BufferedReader reader = Files.newBufferedReader(filename,
				Charset.forName("UTF-8"))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line != null) {
					String[] words = line.split("\\s");
					for (String w : words) {
						w = InvertedIndexBuilder.normalizeWord(w);
					}

					results.put(line, index.search(words));
				}
			}

		} catch (IOException e) {
			System.out.println("Unable to read query file " + filename);
		}
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
	}

}
