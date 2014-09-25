import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Driver for the Inverted Index Accepts the arguments for a directory to read
 * from and for a filename to write to.
 * 
 * @author Matthew Auriemma
 * 
 */

public class Driver {

	public static void main(String[] args) {
		/* Instantiation of Objects */
		ArgumentParser pArg = new ArgumentParser(args);
		InvertedIndex iIndex = new InvertedIndex();
		PartialSearch pSearch = new PartialSearch();
		Path dir = null;

		/*
		 * Checks to see if a directory was passed in. If not, Sys outputs an
		 * error message and exits
		 */
		if (!pArg.hasFlag("-d") || pArg.getValue("-d") == null) {
			System.out.println(pArg.getValue("-d"));
			System.out.println("Invalid Directory");
			return;

		}

		/* Instantiates dir to an Absolute Path of the Input directory */
		dir = Paths.get(pArg.getValue("-d")).toAbsolutePath();

		if (!Files.exists(dir)) {
			System.out.println("Directory does not exist " + dir);
			return;
		}

		InvertedIndexBuilder.traverse(iIndex, dir);

		// Case for when -q is in args
		if (pArg.hasFlag("-q")) {
			pSearch.readQuery(Paths.get(pArg.getValue("-q")), iIndex);
			if (pArg.hasFlag("-i")) {
				if (pArg.getValue("-i") != null) {
					iIndex.write(pArg.getValue("-i"));
				} else {
					iIndex.write(null);
				}
			}
			if (pArg.hasFlag("-r")) {
				if (pArg.getValue("-r") != null) {
					pSearch.write(pArg.getValue("-r"));
				} else {
					pSearch.write(null);
				}
			}
			// No -q in args
		} else {
			if (pArg.hasFlag("-i") && pArg.getValue("-i") != null) {
				iIndex.write(pArg.getValue("-i"));
			} else {
				iIndex.write(null);
			}

		}
	}
}
