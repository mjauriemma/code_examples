/**
 * Object Stored in CityNode. Used for Table and adjacency List
 * @author mjauriemma
 *
 */
public class Vertex implements Comparable<Vertex> {
	public final String name;
	public Edge adjacencies = null;;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;

	public Vertex(String argName) {
		name = argName;
		
	}

	public String toString() {
		return name;
	}

	public int compareTo(Vertex other) {
		return Double.compare(minDistance, other.minDistance);
	}
}