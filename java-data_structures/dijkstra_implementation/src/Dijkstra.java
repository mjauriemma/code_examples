import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Dijkstra {

	private int sourceVertex = -1; // store the id of the source vertex
	private MapGraph graph;
	HashTable nodeTable;
	private int numCities = 0;

	Dijkstra(String filename) {
		loadGraph(filename);

	}

	public MapGraph getGraph() {
		return graph;
	}

	/**
	 * Compute all the shortest paths from the source vertex to all the other
	 * vertices in the graph; This function is called from GUIApp, when the user
	 * clicks on the city.
	 */
	public void computePaths(CityNode vSource) {
		this.sourceVertex = nodeTable.getID(vSource.getCity(), numCities);

		for (CityNode n : graph.nodes) {
			n.getVertex().minDistance = Double.POSITIVE_INFINITY;
			n.getVertex().previous = null;
		}
		vSource.getVertex().minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue1 = new PriorityQueue<Vertex>();

		MinHeap vertexQueue = new MinHeap(numCities);
		Vertex target = vSource.getVertex();

		vertexQueue.insert(nodeTable.getID(target.name, numCities),
				(int) target.minDistance);
		while (vertexQueue.size > 0) {
			
			Vertex u = graph.nodes[vertexQueue.removemin()].getVertex();
			// Visit each edge exiting u
			Edge e = u.adjacencies;
			while (e != null) {

				Vertex v = e.target;
				double weight = e.weight;
				double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					
					v.minDistance = distanceThroughU;
					if (vertexQueue
							.contains(nodeTable.getID(v.name, numCities))) {
						vertexQueue.reduceKey(
								nodeTable.getID(v.name, numCities),
								(int) distanceThroughU);
					} else {
						vertexQueue.insert(nodeTable.getID(v.name, numCities),
								(int) distanceThroughU);
					}
					v.previous = u;
					
				}
				if (e.next == null) {
					break;
				}
				e = e.next;
			}
		}
	}

	/**
	 * Returns the shortest path between the source vertex and this vertex.
	 * Returns the array of node id-s on the path
	 */
	public ArrayList<Integer> shortestPath(CityNode vTarget) {
		ArrayList<Integer> path = new ArrayList<>();

		for (Vertex vertex = vTarget.getVertex(); vertex != null; vertex = vertex.previous) {
			path.add(nodeTable.getID(vertex.name, numCities));
		}
		Collections.reverse(path);
		return path;
	}

	/**
	 * Loads graph info from the text file into MapGraph graph
	 * 
	 * @param filename
	 */
	public void loadGraph(String filename) {

		CityNode[] nodes = null;
		String[] cities = null;

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while (br.ready()) {
				if (br.readLine().equals("NODES")) {
					numCities = Integer.parseInt(br.readLine());
					nodes = new CityNode[numCities];
					cities = new String[numCities];
					for (int i = 0; i < numCities; i++) {
						String[] CT_ra = br.readLine().split(" ");
						nodes[i] = new CityNode(CT_ra[0],
								Double.parseDouble(CT_ra[1]),
								Double.parseDouble(CT_ra[2]));
						// System.out.println(nodes[i]);
						cities[i] = CT_ra[0];
					}
				}

				nodeTable = new HashTable(numCities);
				nodeTable.hashTableInsert(cities);
				graph = new MapGraph(numCities);
				for (CityNode n : nodes) {
					graph.addNode(n);
				}
				if (br.readLine().contains("ARC")) {
					while (br.ready()) {

						String[] ARC_Line = br.readLine().split("\\s");
						int id = nodeTable.getID(ARC_Line[0], numCities);

						graph.addEdge(
								id,
								new Edge(graph.nodes[nodeTable.getID(
										ARC_Line[1], numCities)].getVertex(),
										Integer.parseInt(ARC_Line[2])));

						graph.addEdge(
								nodeTable.getID(ARC_Line[1], numCities),
								new Edge(graph.nodes[id].getVertex(), Integer
										.parseInt(ARC_Line[2])));

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// Create an instance of the Dijkstra class
		// The parameter is the name of the file
		Dijkstra dijkstra = new Dijkstra(args[0]);

		// create the GUI window with the panel
		GUIApp app = new GUIApp(dijkstra);
	}

}
