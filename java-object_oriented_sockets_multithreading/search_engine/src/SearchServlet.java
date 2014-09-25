
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet {

	public static void prepareResponse(String title,
			HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.printf("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"");
		out.printf("\"http://www.w3.org/TR/html4/strict.dtd\">%n%n");
		out.printf("<html>%n%n");
		out.printf("<head>%n");
		out.printf("\t<title>%s</title>%n", title);
		out.printf("\t<meta http-equiv=\"Content-Type\" ");
		out.printf("content=\"text/html;charset=utf-8\">%n");
		out.printf("</head>%n%n");
		out.printf("<body>%n%n");
	}

	/**
	 * Finishes the HTTP response by adding footer HTML code and setting the
	 * response code.
	 * 
	 * @param request
	 *            - HTTP request
	 * @param response
	 *            - HTTP response
	 * @throws IOException
	 * @see #prepareResponse(String, HttpServletResponse)
	 */
	public static void finishResponse(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();

		out.printf("%n");
		out.printf("<p style=\"font-size: 10pt; font-style: italic; text-align: center;");
		out.printf("border-top: 1px solid #eeeeee; margin-bottom: 1ex;\">");

		out.printf("Page <a href=\"%s\">%s</a> generated on %s. ",
				request.getRequestURL(), request.getRequestURL(),
				StringUtilities.getDate("yyyy-MM-dd hh:mm a"));

		out.printf("</p>%n%n");
		out.printf("</body>%n");
		out.printf("</html>%n");

		out.flush();

		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		prepareResponse("Search", response);

		PrintWriter out = response.getWriter();
		String error = request.getParameter("error");

		if (error != null) {
			String errorMessage = StringUtilities.getStatus(error).message();
			out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
		}

		// printForm(out);
		String user = LoginBaseServlet.getUsername(request);

		out.println("<form action=\"/Search\" method=\"post\">");
		if (user != null) {
			out.println("\t\t<td> Logged in As: " + user + "</td>");
		} else {
			out.println("\t\t<td>Register or Login Below</td>");
		}
		out.println("<table border=\"0\">");

		out.println("\t\t<td><input type=\"text\" name=\"Search\" size=\"30\"></td>");
		out.println("\t</tr>");
		out.println("</table>");
		out.println("<p><input type=\"submit\" value=\"Search\"></p>");
		if (user == null) {
			out.print("<p><a href=\"/login\">Login.</a></p>");
			out.print("<p><a href=\"/register\">New User? Register Here.</a></p>");

		} else {
			out.println("<p><a href=\"/history\">View History</a></p>");
			out.println("<p><a href=\"/changepw\">Change Password</a></p>");
			out.println("<p><a href=\"/login?logout\">(logout)</a></p>");
		}
		out.println("</form>");

		finishResponse(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			prepareResponse("Search", response);
		} catch (IOException e) {
			Driver.logger.debug("Prepare response failed");
		}

		String search = request.getParameter("Search");
		Cookie cookie = new Cookie("query", search);
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		try {
			response.sendRedirect(response.encodeRedirectURL("/results"));
		} catch (IOException e) {
			System.out.println("Failed Redirect");
		}

	}

}