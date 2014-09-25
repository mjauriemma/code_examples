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

		/*
		 * Checks to see if an argument was passed in as a filename, and calls
		 * the write function with either that argument or null
		 */
		if (pArg.hasFlag("-i")) {
			iIndex.write(pArg.getValue("-i"));
		}

		
		if(pArg.hasFlag("-q")){
			pSearch.readQuery(pSearch, Paths.get(pArg.getValue("-q")), iIndex);
			if (pArg.hasFlag("-r")){
				pSearch.writeSearch(pArg.getValue("-r"));
			}else{
				pSearch.writeSearch(null);
			}
			
		}else {
			iIndex.write(null);
		}
		
	}

}
