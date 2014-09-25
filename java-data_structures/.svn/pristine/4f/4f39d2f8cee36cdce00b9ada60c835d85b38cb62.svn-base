public class FlightNode {

	private FlightData FlightData;
	private FlightKey FlightKey;
	private FlightNode up; // up link
	private FlightNode down; // down link
	private FlightNode left; // left link
	private FlightNode right; // right link

	public FlightNode(FlightKey FlightKey, FlightData flightData2) {

		this.FlightData = flightData2;
		this.FlightKey = FlightKey;
		this.up = null; // up link
		this.down = null; // down link
		this.left = null; // left link
		this.right = null; // right link
	}

	/**
	 * Getters and Setters
	 * 
	 */
	public FlightData getFlightData() {
		return this.FlightData;
	}

	public void setFlightData(FlightData flightData) {
		this.FlightData = flightData;
	}

	public FlightKey getKey() {
		return this.FlightKey;
	}

	public void setFlightKey(FlightKey flightKey) {
		this.FlightKey = flightKey;
	}

	public FlightNode getUp() {
		return this.up;
	}

	public void setUp(FlightNode up) {
		this.up = up;
	}

	public FlightNode getDown() {
		return this.down;
	}

	public void setDown(FlightNode down) {
		this.down = down;
	}

	public FlightNode getLeft() {
		return this.left;
	}

	public void setLeft(FlightNode left) {
		this.left = left;
	}

	public FlightNode getRight() {
		return this.right;
	}

	public void setRight(FlightNode right) {
		this.right = right;
	}

	/**
	 * toString for Nodes, calls FlightKey and FlightData's toString
	 * Overrides Object's toString
	 */
	@Override
	public String toString() {
		return this.FlightKey.toString() + " " + this.FlightData.toString();
	}
	
	
}