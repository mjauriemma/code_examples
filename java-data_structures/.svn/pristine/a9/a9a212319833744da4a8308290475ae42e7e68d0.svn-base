public class FlightKey implements Comparable {

	private String origin;
	private String destination;
	private String date;
	private String time;



	public FlightKey(String origin, String destination, String date, String time) {
		this.origin = origin;
		this.destination = destination;
		this.date = date;
		this.time = time;
	}

	/**
	 * Getters and Setters for Instance variables
	 * 
	 */

	public FlightKey getKey() {
		return this;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDest() {
		return destination;
	}

	public void setDest(String destination) {
		this.destination = destination;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * For Printing. Overrides object's toString
	 */
	@Override
	public String toString() {
		return origin + " " + destination + " " + date + " " + time;
	}

	/**
	 * Custom Sorting - Compares in order: origin, destination, date, time
	 */
	@Override
	public int compareTo(Object other) {
		FlightKey key = (FlightKey) other;
		if (this.origin.compareTo(key.origin) == 0) {
			if (this.destination.compareTo(key.destination) == 0) {
				if (this.date.compareTo(key.date) == 0) {
					if (this.time.compareTo(key.time) == 0) {
						return 0;
					} else {
						return this.time.compareTo(key.time);
					}
				} else {
					return this.date.compareTo(key.date);
				}
			} else {
				return this.destination.compareTo(key.destination);
			}
		} else {
			return this.origin.compareTo(key.origin);
		}
	}

}