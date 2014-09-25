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

	/**
	 * Reads a file line by line using a BufferedReader, splits the line and
	 * adds the words to the index with their place in the file and the name of
	 * the file they are located
	 * 
	 * @param file
	 *            Path of the file to be read
	 * @param index
	 *            Object in which the index data structure is stored
	 */
	public static void readFile(Path file, InvertedIndex index) {
		int count = 0;
		try (BufferedReader reader = Files.newBufferedReader(file,
				Charset.forName("UTF-8"))) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				if (line != null) {
					String[] words = line.split("\\s");

					for (String w : words) {
						w = normalizeWord(w);
						if (!w.equals(null)
								&& (!w.equals("") && !w.equals(" "))) {
							count++;
							index.addWord(w, count, file);
						}
					}
				}
			}
		} catch (IOException | NullPointerException e) {
			System.out.println("Invalid path for file " + file);
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

	public static void traverse(InvertedIndex index, Path dir) {

		try (DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {

			for (Path f : files)

				if (Files.isDirectory(f)) {
					traverse(index, f);
				} else {
					if (f.toString().toLowerCase().endsWith(".txt")) {
						InvertedIndexBuilder.readFile(f, index);
					}
				}
		} catch (IOException e) {
			System.out.println("Unable to Read directory " + dir);
		}

	}

}
