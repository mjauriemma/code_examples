import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class FlightList {
	public FlightNode negInf;
	public FlightNode posInf;
	public FlightNode FlightNode;

	/**
	 * Constructor
	 */
	public FlightList() {
		negInf = new FlightNode(new FlightKey("AAA", "AAA", "00/00/0000",
				"00:00"), new FlightData("AA000", 0));
		posInf = new FlightNode(new FlightKey("ZZZ", "ZZZ", "99/99/9999",
				"99:99"), new FlightData("ZZ999", 999));
		negInf.setRight(posInf);
		posInf.setLeft(negInf);
	}

	public FlightList(String filename) {
		
		// Edges
		negInf = new FlightNode(new FlightKey("AAA", "AAA", "00/00/0000",
				"00:00"), new FlightData("AA000", 0));
		posInf = new FlightNode(new FlightKey("ZZZ", "ZZZ", "99/99/9999",
				"99:99"), new FlightData("ZZ999", 999));

		//Attach Edges together
		negInf.setRight(posInf);
		posInf.setLeft(negInf);

		//Add to list from file
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while (br.ready()) {
				String[] linesplit = br.readLine().split(" ");
				lineAdd(linesplit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper Function for the read - Takes the String[] and creates data and
	 * keys and calls insert
	 * 
	 * @param linesplit
	 */
	public void lineAdd(String[] linesplit) {
		FlightKey fKey = new FlightKey(linesplit[0], linesplit[1],
				linesplit[2], linesplit[3]);
		FlightData fData = new FlightData(linesplit[4],
				new Double(linesplit[5]));
		this.insert(fKey, fData);
	}

	/**
	 * Insert a  (key,  value)    pair  to  the  skip list.   The  key is  of 
	 * type  FlightKey and  stores 
	 * origin, destination, date and time as strings. Return
	 * true if it was able to successfully insert  the element. 
	 * 
	 * @param key
	 *            FlightKey being added
	 * @param data
	 *            Data being added
	 * @return true if succesfully added, false otherwise
	 * 
	 */

	public boolean insert(FlightKey key, FlightData data) {
		// Make sure the node has valid values
		if (key.compareTo(negInf.getKey()) < 0
				|| key.compareTo(posInf.getKey()) > 0) {
			return false;
		}
		// Create new node with data and call put
		FlightNode input = new FlightNode(key, data);
		return put(input, negInf, posInf);

	}

	/**
	 * Helper for insert - takes the edges of the list row and the node to be
	 * added to that row. Will add 'up' rows based on random generator.
	 * Recursively calls itself.
	 * 
	 * @param newNode
	 *            Node being added
	 * @param lowBound
	 *            Left edge of skipList row
	 * @param upBound
	 *            Right edge of skipList row
	 * @return true if added, false if not
	 */
	private boolean put(FlightNode newNode, FlightNode lowBound,
			FlightNode upBound) {
		FlightNode curr = lowBound;

		while (curr != null) {

			// If value in node is less than the curr add it before curr
			if (newNode.getKey().compareTo(curr.getKey()) < 0) {

				// Pointer magic
				newNode.setLeft(curr.getLeft());
				newNode.setRight(curr);
				curr.getLeft().setRight(newNode);
				curr.setLeft(newNode);
				break;

				// If nodes are equal return false (No duplicate flights)
			} else if (newNode.getKey().compareTo(curr.getRight().getKey()) == 0) {
				return false;

				// Node values must be > than the curr so go to the next one
			} else {
				curr = curr.getRight();
			}
		}
		// Flip the coin to see if we will add the node to the level above
		if (flipCoin()) {

			// Make duplicate node
			FlightNode towerNode = new FlightNode(newNode.getKey(),
					newNode.getFlightData());

			// Attach top of node we just added to the bottom of the duplicate
			newNode.setUp(towerNode);
			towerNode.setDown(newNode);

			if (lowBound.getUp() != null && upBound.getUp() != null) {
				// Call put with the level above and the node to go into that
				// level
				// Already tethered to the 'tower' of nodes
				put(towerNode, lowBound.getUp(), upBound.getUp());
			} else {

				// Make new edge nodes
				FlightNode newLevelLeft = new FlightNode(new FlightKey("AAA",
						"AAA", "00/00/0000", "00:00"), new FlightData("AA000",
						0));
				FlightNode newLevelRight = new FlightNode(new FlightKey("ZZZ",
						"ZZZ", "99/99/9999", "99:99"), new FlightData("ZZ999",
						999));

				// Attach bottom of new edge nodes to the top of top old ones
				lowBound.setUp(newLevelLeft);
				upBound.setUp(newLevelRight);

				// Attach the old edge nodes to the bottom of new ones
				newLevelRight.setDown(upBound);
				newLevelLeft.setDown(lowBound);

				// Connect the new level together
				newLevelRight.setLeft(newLevelLeft);
				newLevelLeft.setRight(newLevelRight);

				// Call put using the new level and the node on the 'tower'
				put(towerNode, newLevelLeft, newLevelRight);
			}
		}
		return true;
	}

	/**
	 * 'Flips a coin' and finds a random val
	 * 
	 * @return true if > 0 false if <=0 (Can only be >= 0
	 */
	private boolean flipCoin() {
		int level;
		Random rand = new Random();
		for (level = 0; rand.nextInt() % 2 == 0; level++) {
		}
		return level > 0;
	}

	/**
	 * Returns true if it was able to find the entry with a given key in the
	 * skip list. This search has to be efficient.
	 * 
	 * @param key
	 * @return
	 */
	public boolean find(FlightKey key) {
		FlightNode n = search(key, negInf);
		return (!n.getKey().equals(key));

	}

	public FlightNode search(FlightKey key, FlightNode node) {
		if (node.getRight() == null) {
			if (node.getDown() == null)
				return node;
			return search(key, node.getDown());
		}

		if (node.getRight().getKey().compareTo(key) > 0) {
			if (node.getDown() != null)
				return search(key, node.getDown());
			else {
				return node;
			}
		} else {
			return search(key, node.getRight());
		}
	}

	/**
	 * Returns a list of nodes that have the same origin and destination cities
	 * and the same date as the key, with departure times within the
	 * timeDifference of the departure time of the key .
	 * 
	 * @param key
	 *            FlightKey to compare to
	 * @param timeDifference
	 *            Amount of time away from time in key that the flight can be
	 * @return ArrayList of flights within the appropriate time frame
	 */

	public ArrayList<FlightNode> findFlights(FlightKey key, int timeDifference) {
		FlightNode start = negInf;
		boolean found = false;
		
		//Pull the ints out of keys and modify using time difference
		int upBound = Integer.parseInt(key.getTime().substring(0, 2))

		+ timeDifference;
		int lowBound = Integer.parseInt(key.getTime().substring(0, 2))

		- timeDifference;

		//We dont change the date just make the 'new' boundaries flights that day
		if (upBound > 24) {
			upBound = 24;
		}
		if (lowBound < 0) {
			lowBound = 00;
		}

		ArrayList<FlightNode> flights = new ArrayList<FlightNode>();

		//Traverse and find flights with same origin dest and date and add to list
		//If time is between upper and lower boundaries (Only does hours)
		while (start.getRight().getKey() != null) {
			start = start.getRight();
			while ((start.getKey().getOrigin()
					.equalsIgnoreCase(key.getOrigin()))
					&& (start.getKey().getDest()
							.equalsIgnoreCase(key.getDest()))
					&& (start.getKey().getDate()
							.equalsIgnoreCase(key.getDate()))) {
				found = true;

				if (Integer.parseInt(start.getKey().getTime().substring(0, 2))

				>= lowBound
						&& Integer.parseInt(start.getKey().getTime()
								.substring(0, 2))

						<= upBound) {
					flights.add(start);

				}
				start = start.getRight();

			}
			if (found) {
				break;
			}
		}

		return flights;
	}

	/**
	 * Returns a list of nodes that have the same origin and destination cities
	 * and the same date as the key, with departure times in increasing order
	 * from the requested departure time.
	 * 
	 * @param key
	 * @return
	 */
	public ArrayList<FlightNode> successors(FlightKey key) {
		FlightNode start = negInf;
		boolean found = false;
		ArrayList<FlightNode> flights = new ArrayList<FlightNode>();

		//Takes all times less greater than the one in key 
		while (start.getRight().getKey() != null) {
			start = start.getRight();
			while ((start.getKey().getOrigin()
					.equalsIgnoreCase(key.getOrigin()))
					&& (start.getKey().getDest()
							.equalsIgnoreCase(key.getDest()))
					&& (start.getKey().getDate()
							.equalsIgnoreCase(key.getDate()))) {
				found = true;
				if (Integer.parseInt(start.getKey().getTime().substring(0, 2)
						+ start.getKey().getTime().substring(3)) > Integer
						.parseInt(key.getTime().substring(0, 2)
								+ key.getTime().substring(3))) {
					flights.add(start);
				}
				start = start.getRight();

			}
			if (found) {
				break;
			}
		}

		return flights;
	}

	/**
	 * Returns a list of nodes that have the same origin and destination cities
	 * and the same date as the key, with departure times in decreasing order
	 * from the requested departure time.
	 * 
	 * @param key
	 * @return
	 */
	public ArrayList<FlightNode> predecessors(FlightKey key) {
		FlightNode start = negInf;
		boolean found = false;
		ArrayList<FlightNode> flights = new ArrayList<FlightNode>();
		
		//Takes all times less than the ones in key
		while (start.getRight().getKey() != null) {
			start = start.getRight();
			while ((start.getKey().getOrigin()
					.equalsIgnoreCase(key.getOrigin()))
					&& (start.getKey().getDest()
							.equalsIgnoreCase(key.getDest()))
					&& (start.getKey().getDate()
							.equalsIgnoreCase(key.getDate()))) {
				found = true;
				if (Integer.parseInt(start.getKey().getTime().substring(0, 2)
						+ start.getKey().getTime().substring(3)) < Integer
						.parseInt(key.getTime().substring(0, 2)
								+ key.getTime().substring(3))) {
					flights.add(start);
				}
				start = start.getRight();

			}
			if (found) {
				break;
			}
		}

		return flights;
	}

	/**
	 * Prints the structure of the Skip list by separating the levels with a new
	 * line. Keeps a count to say which level it is on
	 */

	public void print() {

		FlightNode start = negInf;
		while (start.getUp() != null) {
			start = start.getUp();
		}
		FlightNode node = start.getRight();
		int count = 1;
		System.out.println("Level: " + count);
		while (true) {
			while (node.getRight() != null) {
				System.out.println(node);
				node = node.getRight();
			}
			if (start.getDown() != null) {
				start = start.getDown();
				node = start.getRight();
				count++;
				System.out.println("\nLevel: " + count);
			} else {
				break;
			}
		}

	}

}