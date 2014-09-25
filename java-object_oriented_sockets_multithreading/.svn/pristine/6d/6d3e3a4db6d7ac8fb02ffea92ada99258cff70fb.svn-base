import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

public class PartialSearch {

	// Data Structure for the Search Results
	TreeMap<String, TreeMap<Path, ArrayList<Integer>>> searchIndex;

	/**
	 * Constructor
	 */

	public PartialSearch() {
		searchIndex = new TreeMap<>();
	}

	/**
	 * Reads the file given in program args and searches the index using the
	 * queries found in the file
	 * 
	 * @param pSearch
	 *            PartialSearch object to pass into the InvertedIndex
	 * @param filename
	 *            Path of the file to read the queries from
	 * @param index
	 *            InvertedIndex object to call the Search function within
	 */

	public void readQuery(PartialSearch pSearch, Path filename,
			InvertedIndex index) {
		try (BufferedReader reader = Files.newBufferedReader(filename,
				Charset.forName("UTF-8"))) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				if (line != null) {
					String[] words = line.split("\\s");

					for (String w : words) {
						w = InvertedIndexBuilder.normalizeWord(w);
						if (!w.equals(null)
								&& (!w.equals("") && !w.equals(" "))) {
							index.Search(pSearch, w);
						}
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Unable to read query file " + filename);
		}
	}

	/**
	 * Updates the searchIndex with the new values given, or adds to existing
	 * values in the map.
	 * 
	 * @param q
	 *            String of the Search Query
	 * @param filename
	 *            File where the query was found
	 * @param freq
	 *            Frequency the query appeared in the file
	 * @param pos
	 *            Initial Position of the query
	 */

	public void Update(String q, Path filename, int freq, int pos) {
		if (searchIndex.containsKey(q)) {

			if (searchIndex.get(q).containsKey(filename)) {
				searchIndex.get(q).get(filename)
						.set(0, searchIndex.get(q).get(filename).get(0) + freq);

				if (searchIndex.get(q).get(filename).get(1) > pos) {
					searchIndex.get(q).get(filename).set(1, pos);
				}
			} else {
				searchIndex.get(q).put(filename, new ArrayList<Integer>());
				searchIndex.get(q).get(filename).add(freq);
				searchIndex.get(q).get(filename).add(pos);
			}
		} else {
			searchIndex.put(q, new TreeMap<Path, ArrayList<Integer>>());
			searchIndex.get(q).put(filename, new ArrayList<Integer>());
			searchIndex.get(q).get(filename).add(freq);
			searchIndex.get(q).get(filename).add(pos);
		}

	}

	/**
	 * Sorts the Search results for each query by frequency or, if equal, inital
	 * position
	 * 
	 * @param iName
	 *            Name of file to write to
	 */

	public void writeSearch(String iName) {

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(iName),
				Charset.forName("UTF-8"));) {

			for (String w : searchIndex.keySet()) {

				Path[] files = searchIndex.get(w).keySet()
						.toArray(new Path[searchIndex.get(w).keySet().size()]);

				writer.write(w + " ");
				writer.newLine();

				for (int i = 0; i < files.length; i++) {
					for (int j = i; j > 0; j--) {
						if (searchIndex.get(w).get(files[j - 1]).get(0) > searchIndex
								.get(w).get(files[j]).get(0)) {
							Path swap = files[j];
							files[j] = files[j - 1];
							files[j - 1] = swap;
						}
						else if (searchIndex.get(w).get(files[j - 1]).get(0) == searchIndex
								.get(w).get(files[j]).get(0)) {
							if (searchIndex.get(w).get(files[j - 1]).get(1) > searchIndex
									.get(w).get(files[j]).get(1)) {
								Path swap = files[j];
								files[j] = files[j - 1];
								files[j - 1] = swap;
							}
						}
					}
				}

				for (Path p : files) {
					writer.write('"' + (p).toAbsolutePath().toString() + '"'
							+ ", ");

					writer.write(searchIndex.get(w).get(p).get(0).toString());
					writer.write(", ");
					writer.write(searchIndex.get(w).get(p).get(0).toString());
					writer.newLine();
				}
				writer.newLine();
				writer.flush();
			}

		} catch (IOException e) {
			System.out.println("Writing Failed in file " + iName);

		}
	}

}
