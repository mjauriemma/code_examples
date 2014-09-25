import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Driver for the Inverted Index Accepts the arguments for a directory to read
 * from and for a filename to write to.
 * 
 * @author Matthew Auriemma
 * 
 */

public class Driver {

	public static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {

		/* Instantiation of Objects */

		ArgumentParser pArg = new ArgumentParser(args);
		InvertedIndex iIndex = new InvertedIndex();

		WorkQueue workers;
		Integer minions;

		if (pArg.hasFlag("-t") && pArg.getValue("-t") != null) {
			try {
				minions = Integer.parseInt(pArg.getValue("-t"));
				if (minions > 0) {
					workers = new WorkQueue(minions);
				} else {
					workers = new WorkQueue();
				}

			} catch (NumberFormatException e) {
				workers = new WorkQueue();
			}

		} else {
			workers = new WorkQueue();
		}

		Path dir = null;

		InvertedIndexBuilder builder = new InvertedIndexBuilder(iIndex, workers);
		WebCrawler crawler = new WebCrawler(iIndex, workers);
		PartialSearch pSearch = new PartialSearch(iIndex, workers);
		/*
		 * Checks to see if a directory was passed in. If not, Sys outputs an
		 * error message and exits
		 */
		if (pArg.hasFlag("-d") || pArg.hasFlag("-u")) {
			if (pArg.hasFlag("-d") && pArg.getValue("-d") == null) {
				logger.debug("Invalid Value for -d");
				workers.shutdown();
				return;
			}
			if (pArg.hasFlag("-u") && pArg.getValue("-u") == null) {
				logger.debug("Invalid Value for -u");
				workers.shutdown();
				return;
			}
		} else {
			logger.debug("No -d or -u flag");
			workers.shutdown();
			return;
		}

		if (pArg.hasFlag("-d")) {
			dir = Paths.get(pArg.getValue("-d")).toAbsolutePath();

			if (!Files.exists(dir)) {
				System.out.println("Directory does not exist " + dir);
				workers.shutdown();
				return;
			} else {

				builder.traverse(dir);

				builder.finish();
			}
		}

		if (pArg.hasFlag("-u")) {
			crawler.start(pArg.getValue("-u"));
			crawler.finish();
		}

		// Case for when -q is in args
		if (pArg.hasFlag("-q") && pArg.getValue("-q") != null) {
			logger.debug("Starting Search");
			pSearch.readQuery(Paths.get(pArg.getValue("-q")));
			if (pArg.hasFlag("-i")) {
				if (pArg.getValue("-i") != null) {
					iIndex.write(pArg.getValue("-i"));
					logger.debug("Wrote Inverted Index to {}",
							pArg.getValue("-i"));
				} else {
					iIndex.write(null);
					logger.debug("Wrote Inverted Index to {}",
							"invertedindex.txt");
				}
			}

			pSearch.finish();

			if (pArg.hasFlag("-r")) {
				if (pArg.getValue("-r") != null) {
					pSearch.write(pArg.getValue("-r"));
					logger.debug("Wrote Search Index to {}",
							pArg.getValue("-r"));
				} else {
					pSearch.write(null);
					logger.debug("Wrote Search Index to {}",
							"searchresults.txt");
				}
			}
			// No -q in args
		} else {
			if (pArg.hasFlag("-i") && pArg.getValue("-i") != null) {
				iIndex.write(pArg.getValue("-i"));
				logger.debug("Wrote Inverted Index to {}", pArg.getValue("-i"));
			} else {
				iIndex.write(null);
				logger.debug("Wrote Inverted Index to {}", "invertedindex.txt");
			}

		}

		workers.shutdown();
		logger.debug("Workers have ShutDown\n");

	}
}
