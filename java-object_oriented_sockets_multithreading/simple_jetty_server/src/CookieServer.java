import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;


public class CookieServer {

	public static void main(String[] args) {
		Server server = new Server(8080);

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(CookieMonster.class, "/");
        
        server.setHandler(handler);
        server.setHandler(handler);
        try {
			server.start();
		} catch (Exception e) {
			System.out.println("Server failed to Start.");
		}
        try {
			server.join();
		} catch (InterruptedException e) {
			System.out.println("Server was Interrupted.");
		}

	}
	
}
