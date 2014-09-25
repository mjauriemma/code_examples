import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dictionary {

	public Node root;

	/**
	 * create an empty constructor
	 */
	public Dictionary() {
		root = new Node("", false);
	}

	/**
	 * Create a dictionary from a file
	 * 
	 * @param filename
	 *            Name of file to read from
	 */
	public Dictionary(String filename) {
		root = new Node("", false);
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(filename), Charset.forName("UTF-8"))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line != null) {
					String[] words = line.split("\\s");
					for (String w : words) {
						if (!w.equals(null)
								&& (!w.equals("") && !w.equals(" "))) {
							add(w);
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Invalid path for file " + filename);
		}
	}

	/**
	 * Add a word into the dictionary
	 * 
	 * A node whose prefix is not the prefix of the word you are looking for.
	 * This is the hard case.
	 * 
	 * You need to: Create a new node. The prefix stored in this node is the
	 * longest common prefix of the word you are inserting and the prefix stored
	 * at the original root.
	 * 
	 * Thus, if you were inserting "hamster" into a node whose prefix was
	 * "hamburger", then the prefix of this new node would be "ham" Let suffix
	 * and suffixWord be the suffix of the original prefix and the suffix of the
	 * word you are adding, after extracting the common prefix.
	 * 
	 * So, if you are insetring hamster into a node whose prefix was hamburger,
	 * then suffixWord would be "ster" and suffix would be "burger".
	 * 
	 * Set the prefix of the original tree to suffix, and set the child of the
	 * new node corrseponding to the first letter of suffix to the original tree
	 * Recursively insert suffixWord into the approprate child of the new node
	 * Return the new node
	 * 
	 * @param word
	 */

	public void add(String word) {
		if (!check(word))
			root = insert(root, word);
	}
	
	/**
	 * Helper for insert
	 * 
	 * @param word
	 *            word trying to be added (could be truncated)
	 * @param nextword
	 *            Word in the node being looked at
	 * @return String of what they have in common (starting from beginnning)
	 */
	private String prefix(String word, String nextword) {
		String prefix = "";
		int len = Math.min(word.length(), nextword.length());
		for (int i = 0; i <= (len - 1); i++) {
			if (word.substring(0, i + 1).equals(nextword.substring(0, i + 1))) {
				prefix = word.substring(0, i + 1);
			} else {
				return prefix;
			}
		}
		return prefix;
	}

	/**
	 * Helper function for add
	 * 
	 * @param node
	 *            'root' of subtree
	 * @param word
	 *            word to be added to dictionary (with recursively trimmed
	 *            prefixes)
	 * @return New node
	 */

	private Node insert(Node node, String word) {
		word = word.toLowerCase();
		// Base case (if node passed in is null)
		if (node == null) {
			Node newNode = new Node(word, true);
			return newNode;
		}
		// If word is already in dictionary but is not 'true'
		else if (node.prefix.equals(word)) {
			if (!node.valid) {
				node.valid = true;
			}
			return node;
			// Everything else
		} else {
			String commonword = prefix(word, node.prefix);
			if (commonword.equals(node.prefix)) {
				String wordsuffix = word.substring(node.prefix.length());
				int index = wordsuffix.charAt(0) - 'a';
				node.letters[index] = insert(node.letters[index], wordsuffix);
				return node;
			} else {
				Node prefixNode;
				// If word == common word (is new prefix and word)
				if (commonword.equals(word)) {
					prefixNode = new Node(commonword, true);
					// Just putting in a prefix
				} else {
					prefixNode = new Node(commonword, false);
				}
				// Truncating string and recursively calling
				String treesuffix = node.prefix.substring(commonword.length());
				String wordsuffix = word.substring(commonword.length());
				node.prefix = treesuffix;
				prefixNode.letters[node.prefix.charAt(0) - 'a'] = node;
				if (wordsuffix != null) {
					prefixNode.letters[wordsuffix.charAt(0) - 'a'] = insert(
							prefixNode.letters[wordsuffix.charAt(0) - 'a'],
							wordsuffix);
				}
				return prefixNode;
			}
		}
	}



	/**
	 * Checks to see if a word is in the dictionary
	 * 
	 * @param word
	 * @return
	 */
	public boolean check(String word) {
		return exists(root.letters[word.charAt(0) - 'a'], word);
	}

	/**
	 * Helper for check
	 * 
	 * @param n
	 *            'root' of subtree
	 * @param word
	 *            original word with prefixes removed
	 * @return True if word is a word in the dictionary
	 */

	private boolean exists(Node n, String word) {
		if ((n != null) && (word != null) && !word.equals("")) {
			if (word.startsWith(n.prefix)) {
				if (word.equals(n.prefix)) {
					if (n.valid) {
						return true;
					} else {
						return false;
					}
				} else {
					word = word.substring(n.prefix.length());
					return exists(n.letters[word.charAt(0) - 'a'], word);
				}
			}
		}

		return false;
	}

	/**
	 * Checks to see if a prefix matches a word in the dictionary
	 * 
	 * @param prefix
	 * @return
	 */
	public boolean checkPrefix(String prefix) {
		if (prefix.equals("")) {
			return true;
		}
		if (prefix.length() > 0)
			return isPrefix(root.letters[prefix.charAt(0) - 'a'], prefix);
		return false;
	}

	/**
	 * Helper for check prefix
	 * 
	 * @param n
	 *            'root' of the subtree
	 * @param word
	 *            word with past prefixes removed
	 * @return boolean true if prefix is in dictionary
	 */

	private boolean isPrefix(Node n, String word) {

		if (n.prefix.startsWith(word) || n.prefix.equalsIgnoreCase(word)) {
			return true;
		} else if (word.startsWith(n.prefix)) {
			String shortWord = word.substring(n.prefix.length());
			return isPrefix(n.letters[shortWord.charAt(0) - 'a'], shortWord);

		}
		return false;

	}

	/**
	 * Print out the contents of the dictionary, in alphabetical order, one word
	 * per line.
	 */
	public void print() {
		for (Node node : root.letters) {
			if (node != null) {
				print(node, node.prefix);
			}
		}
	}

	/**
	 * Print out the tree structure of the dictionary, in a pre-order fashion.
	 */
	public void printTree() {
		pOPrint(root, 0);

	}

	/**
	 * Helper for print tree (Pre-order print)
	 * 
	 * @param n
	 *            'root' of the subtree
	 * @param indent
	 *            number of tabs to be indented (to show Structure)
	 */
	public void pOPrint(Node n, int indent) {
		// System.out.println("IN pOPrint");
		if (n.letters.length > 0) {
			for (Node node : n.letters) {
				if (node != null) {
					// System.out.println("In Print");
					for (int i = 0; i < indent; i++) {
						System.out.print("\t");
					}
					System.out.print(node.prefix + "\n");
					pOPrint(node, indent + 1);
				}
			}

		}
	}

	/**
	 * Helper function for printing the words Alphabetically
	 * 
	 * @param n
	 *            Node to start at. The 'root' of the subtree
	 * @param word
	 *            String containing all past prefixes
	 */

	public void print(Node n, String word) {
		if (n.valid) {
			System.out.println(word);
		} else {
			for (Node node : n.letters) {
				if (node != null) {
					if (node.valid) {
						System.out.println(word + node.prefix);
					} else {
						print(node, word + node.prefix);
					}
				}
			}
		}
	}

	/**
	 * Return an array of the entries in the dictionary that are as close as
	 * possible to the parameter word.
	 * 
	 * Note: Is broken. Will only return a suggestion if the word exists
	 * 
	 * @param word
	 * @param numSuggestions
	 * @return
	 */
	public String[] suggest(String word, int numSuggestions) {

		if (check(word)) {
			return new String[] { word };
		}

		String[] sugs = new String[numSuggestions];
		//TODO Call helper function to do something

		return sugs;
	}

}