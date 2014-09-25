public class Node {

	public String prefix;
	public Node[] letters;
	public boolean valid;
	public int children;

	public Node(String prefix, boolean valid) {
		this.prefix = prefix;
		this.children = 0;
		letters = new Node[26];
		this.valid = valid;
	}
}
