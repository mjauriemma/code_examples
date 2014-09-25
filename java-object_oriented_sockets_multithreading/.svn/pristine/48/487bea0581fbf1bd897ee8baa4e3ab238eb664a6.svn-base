import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

	private static final int PORT = 80;

	private final InvertedIndex index;
	private final WorkQueue workers;
	private int pending;
	private final HashSet<String> links;
	private MultiReaderLock linkLock;

	public WebCrawler(InvertedIndex index, WorkQueue workers) {
		this.index = index;
		this.workers = workers;
		pending = 0;
		links = new HashSet<String>();
		linkLock = new MultiReaderLock();
	}

	/**
	 * Fetches the webpage at the provided URL by opening a socket, sending an
	 * HTTP request, removing the headers, and returning the resulting HTML
	 * code.
	 * 
	 * You can use the HTMLFetcher class, but you must include all required
	 * classes with your homework.
	 * 
	 * @param url
	 *            - webpage to download
	 * @return html code
	 */
	public String fetchText(String url) {
		ArrayList<String> local = new ArrayList<String>();
		StringBuffer htmlBuffer = new StringBuffer();
		boolean head = true;

		URL link = null;
		URL base = null;

		try {
			link = new URL(url);
			
			base = new URL(url);
		} catch (MalformedURLException e) {
			Driver.logger.debug("URL broke url: {} base {}", link, base);
		}

		try (Socket socket = new Socket(link.getHost(), PORT);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				PrintWriter writer = new PrintWriter(socket.getOutputStream())

		) {
			// craftRequest();
			String host = link.getHost();
			String resource = link.getFile().isEmpty() ? "/" : link.getFile();

			StringBuffer output = new StringBuffer();
			output.append("GET " + resource + " HTTP/1.1\n");
			output.append("Host: " + host + "\n");
			output.append("Connection: close\n");
			output.append("\r\n");
			// end craftRequest();

			String request = output.toString();

			writer.println(request);
			writer.flush();
			String line = reader.readLine();

			while (line != null) {

				if (head) {

					if (line.trim().isEmpty()) {
						head = false;
					}
				} else {

					htmlBuffer.append(line + "\n");
				}
				line = reader.readLine();

			}
		} catch (IOException e) {
			Driver.logger.debug("Could not open Socket for {}", url);
		}

		local = listLinks(htmlBuffer.toString());
		linkLock.lockWrite();
		for (String s : local) {

			try {
				s = new URL(base, s).toURI().normalize().toString();

				if (!links.contains(s)) {
					links.add(s);

					if (links.size() <= 50) {
						workers.execute(new WebMinion(s));
					}

				}
			} catch (MalformedURLException | URISyntaxException e) {

				Driver.logger.debug("Bad URL from local {}", s);

			}

			// Driver.logger.debug("Added {} to links", s);
		}
		linkLock.unlockWrite();

		String html;
		html = cleanHTML(htmlBuffer.toString());

		return html;
	}

	public void start(String url) {
		linkLock.lockWrite();
		links.add(url);
		linkLock.unlockWrite();
		workers.execute(new WebMinion(url));
	}

	private class WebMinion implements Runnable {

		private String link;
		private InvertedIndex local;

		public WebMinion(String link) {
			local = new InvertedIndex();
			this.link = link;
			Driver.logger.debug("Worker created for URL {}", link);
			incrementPending();
		}

		@Override
		public void run() {
			Driver.logger.debug("Worker Starting URL {}", this.link);

			Driver.logger.debug("Fetching for {}", link);
			String html = fetchText(this.link);

			Driver.logger.debug("Parsing {}", link);
			ArrayList<String> words = parseWords(html);
			for (int i = 0; i < words.size(); i++) {
				local.addWord(words.get(i), i + 1, this.link);
			}
			Driver.logger.debug("Parsed {}", link);

			index.addAll(local);
			decrementPending();

		}
	}

	/**
	 * Indicates that we now have additional "pending" work to wait for. We need
	 * this since we can no longer call join() on the threads. (The threads keep
	 * running forever in the background.)
	 * 
	 * We made this a synchronized method in the outer class, since locking on
	 * the "this" object within an inner class does not work.
	 */
	private synchronized void incrementPending() {
		pending++;
		Driver.logger.debug("Pending increased to {}", getPending());
	}

	/**
	 * Indicates that we now have one less "pending" work, and will notify any
	 * waiting threads if we no longer have any more pending work left.
	 */
	private synchronized void decrementPending() {
		pending--;
		Driver.logger.debug("Pending decreased to {}", getPending());

		if (pending <= 0) {
			notifyAll();
		}
	}

	/**
	 * Get method for the value of pending
	 * 
	 * @return pending
	 */
	public synchronized int getPending() {
		return pending;
	}

	

	/**
	 * Helper method, that helps a thread wait until all of the current work is
	 * done. This is useful for resetting the counters or shutting down the work
	 * queue.
	 */
	public synchronized void finish() {
		try {
			while (getPending() > 0) {
				Driver.logger.debug("Waiting until finished");
				this.wait();
			}
		} catch (InterruptedException e) {
			Driver.logger.debug("Finish interrupted", e);
		}
	}

	/**
	 * Removes all HTML tags, which is essentially anything between the < and >
	 * symbols. The tag will be replaced by the empty string.
	 * 
	 * @param html
	 *            - html code to parse
	 * @return text without any html tags
	 */
	public String stripTags(String html) {

		return html.replaceAll("(?i)(?s)<[^>]*>", " ");

	}

	/**
	 * Parses the provided plain text (already cleaned of HTML tags) into
	 * individual words.
	 * 
	 * THIS METHOD IS PROVIDED FOR YOU. DO NOT MODIFY.
	 * 
	 * @param text
	 *            - plain text without html tags
	 * @return list of parsed words
	 */
	public ArrayList<String> parseWords(String text) {
		ArrayList<String> words = new ArrayList<String>();

		for (String word : text.split("\\s+")) {
			word = word.toLowerCase().replaceAll("[\\W_]+", "").trim();

			if (!word.isEmpty()) {
				words.add(word);
			}
		}

		return words;
	}

	/**
	 * Removes all style and script tags (and any text in between those tags),
	 * all HTML tags, and all special characters/entities.
	 * 
	 * THIS METHOD IS PROVIDED FOR YOU. DO NOT MODIFY.
	 * 
	 * @param html
	 *            - html code to parse
	 * @return plain text
	 */
	public String cleanHTML(String html) {
		String text = html;
		text = stripElement("script", text);
		text = stripElement("style", text);
		text = stripTags(text);
		text = stripEntities(text);
		return text;
	}

	/**
	 * Replaces all HTML entities in the text with the empty string. For
	 * example, "2010&ndash;2012" will become "20102012".
	 * 
	 * @param html
	 *            - the text with html code being checked
	 * @return text with HTML entities replaced by a space
	 */
	public String stripEntities(String html) {
		return html.replaceAll("(?i)(?s)(?m)&.*?;", " ");
	}

	/**
	 * Removes everything between the element tags, and the element tags
	 * themselves. For example, consider the html code:
	 * 
	 * <pre>
	 * &lt;style type="text/css"&gt;body { font-size: 10pt; }&lt;/style&gt;
	 * </pre>
	 * 
	 * If removing the "style" element, all of the above code will be removed,
	 * and replaced with the empty string.
	 * 
	 * @param name
	 *            - name of the element to strip, like style or script
	 * @param html
	 *            - html code to parse
	 * @return html code without the element specified
	 */
	public String stripElement(String name, String html) {
		return html.replaceAll("(?i)(?s)(?m)<" + name + ".*?[^<]*<.*?" + name
				+ ".*?>", " ");
	}

	/**
	 * Parses the provided text for HTML links. You should not need to modify
	 * this method.
	 * 
	 * @param text
	 *            - valid HTML code, with quoted attributes and URL encoded
	 *            links
	 * @return list of links found in HTML code
	 */
	public ArrayList<String> listLinks(String text) {
		String REGEX = "(?i)(?s)<a.*?href[\\s]*=[\\s]*[\"](.+?\\..+?)\"+";
		int GROUP = 1;
		// list to store links
		ArrayList<String> links = new ArrayList<String>();

		// compile string into regular expression
		Pattern p = Pattern.compile(REGEX);

		// match provided text against regular expression
		Matcher m = p.matcher(text);

		// loop through every match found in text
		while (m.find()) {
			// add the appropriate group from regular expression to list
			links.add(m.group(GROUP));
		}

		return links;
	}
}
