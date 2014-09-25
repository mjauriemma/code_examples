import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Demonstrates how to create, use, and clear cookies. Vulnerable to attack
 * since cookie values are not sanitized prior to use!
 * 
 * @author Sophie Engle
 * @author CS 212 Software Development
 * @author University of San Francisco
 * 
 * @see BaseServlet
 * @see CookieIndexServlet
 * @see CookieMonster
 */
@SuppressWarnings("serial")
public class CookieMonster extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.info("GET " + request.getRequestURL().toString());

		if (request.getRequestURI().endsWith("favicon.ico")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		prepareResponse("Cookies!", response);

		Map<String, String> cookies = getCookieMap(request);

		PrintWriter out = response.getWriter();
		out.printf("<p>");

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		log.info("MessageServlet ID " + this.hashCode()
				+ " handling GET request.");

		out.printf("<html>%n");
		out.printf("<head><title>%s</title></head>%n", "Cookies");
		out.printf("<body>%n");

		out.printf("<h1>Saved Cookies</h1>%n%n");

		synchronized (cookies) {
			for (String message : cookies.keySet()) {
				out.printf("<p>%s : %s</p>%n", message, cookies.get(message));
			}
			if (cookies.isEmpty()) {
				out.printf("You have no Saved Cookies.");
			}
		}
		out.printf("<h2>Edit Cookies</h2>%n");

		printForm(request, response);

		finishResponse(request, response);
	}

	private static void printForm(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		out.printf("<form method=\"post\" action=\"%s\">%n",
				request.getServletPath());
		out.printf("<table cellspacing=\"0\" cellpadding=\"2\"%n");
		out.printf("<tr>%n");
		out.printf("\t<td nowrap>Name:</td>%n");
		out.printf("\t<td>%n");
		out.printf("\t\t<input type=\"text\" name=\"name\" maxlength=\"50\" size=\"20\">%n");
		out.printf("\t</td>%n");
		out.printf("</tr>%n");
		out.printf("<tr>%n");
		out.printf("\t<td nowrap>Value:</td>%n");
		out.printf("\t<td>%n");
		out.printf("\t\t<input type=\"text\" name=\"value\" maxlength=\"100\" size=\"60\">%n");
		out.printf("\t</td>%n");
		out.printf("</tr>%n");
		out.printf("</table>%n");
		out.printf("<label><input type=\"checkbox\" name=\"checkbox\" value=\"value\"> Delete Cookie</label>");
		out.printf("<p><input type=\"submit\" value=\"Edit Cookie\"></p>\n%n");
		out.printf("</form>\n%n");
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.info("POST " + request.getRequestURL().toString());
		String name, value;

		name = request.getParameter("name");
		value = request.getParameter("value");
		Cookie cookie = new Cookie(name, value);
	
		if (request.getParameter("checkbox") != null) {
			cookie.setMaxAge(0);
		}
		response.addCookie(cookie);

		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(request.getServletPath());

	}
}