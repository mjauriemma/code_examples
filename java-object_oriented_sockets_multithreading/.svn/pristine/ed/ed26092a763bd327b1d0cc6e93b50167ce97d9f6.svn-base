import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * Driver for the Inverted Index Accepts the arguments for a directory to read
 * from and for a filename to write to.
 * 
 * @author Matthew Auriemma
 * 
 */

public class Driver {
	private static int PORT;

	public static final Logger logger = LogManager.getLogger();
	public static InvertedIndex iIndex;
	public static WorkQueue workers;

	public static void main(String[] args) {

		/* Instantiation of Objects */
		

		ArgumentParser pArg = new ArgumentParser(args);
		iIndex = new InvertedIndex();

		
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

		
		WebCrawler crawler = new WebCrawler(iIndex, workers);
		
		/*
		 * Checks to see if a directory was passed in. If not, Sys outputs an
		 * error message and exits
		 */
		
		if (pArg.hasFlag("-u") && pArg.getValue("-u") == null) {
				logger.debug("Invalid Value for -u");
				workers.shutdown();
				return;
		
		} 
		
		else if (pArg.hasFlag("-u")) {
			crawler.start(pArg.getValue("-u"));
			crawler.finish();
		}
		
		else {
			logger.debug("No -u flag");
			workers.shutdown();
			return;
		}

		

		if (pArg.hasFlag("-p")){
			PORT = Integer.parseInt(pArg.getValue("-p"));
		}else{
			PORT = 8080;
		}
		//Start Server
		
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		
		handler.addServletWithMapping(LoginUserServlet.class,    "/login");
		handler.addServletWithMapping(LoginRegisterServlet.class, "/register");
		handler.addServletWithMapping(HistoryServlet.class,  "/history");
		handler.addServletWithMapping(SearchServlet.class, "/");
		handler.addServletWithMapping(ResultServlet.class, "/results");
		handler.addServletWithMapping(ChangePasswordServlet.class, "/changepw");
		
		// FUNCTIONS (50)
		// TODO History (10)
		// TODO Change PW (5)
		// TODO Clear History (5)
		
		
		//SERVLETS
		//TODO Home Servlet
		//TODO LOGIN Servlets
		//TODO Results Servlet
		
		//Extras (55)
		//TODO page SNIPPET (15)
		//TODO  ADMIN
			// NEW seed (10)
			// Server Shutdown (10)
		//TODO Private Search (5)
		//TODO SEarch Stats (5)
		//TODO RESUlts per page (5)
		//TODO Time Stamps (5)
		
		try {
			server.start();
			server.join();

			
		}
		catch (Exception ex) {
			System.out.println("WTF?");
			System.exit(-1);
		}
		
		workers.shutdown();
		logger.debug("Workers have ShutDown\n");
	}
}
