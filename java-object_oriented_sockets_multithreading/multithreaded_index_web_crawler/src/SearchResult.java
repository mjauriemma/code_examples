

/**
 * Creates SearchResult objects for the partial search class. Provides a
 * toString and compareTo methods for the results
 * 
 * @author Matthew Auriemma
 * 
 */
public class SearchResult implements Comparable<SearchResult> {

	private final String path;
	private int frequency;
	private int position;

	public SearchResult(String p, int frequency, int position) {
		this.path = p;
		this.frequency = frequency;
		this.position = position;
	}

	/**
	 * Compares two search results by frequency, then position, then path
	 * 
	 * @param o
	 *            SearchResult object to compare to
	 */
	@Override
	public int compareTo(SearchResult o) {
		if (this.frequency == o.frequency) {
			if (this.position == o.position) {
				return this.path.toString().compareTo(o.path.toString());
			}
			return Integer.compare(this.position, o.position);
		} else {
			return Integer.compare(o.frequency, this.frequency);
		}
	}

	/**
	 * Provides a toString format for writing out the SearchResults
	 */
	@Override
	public String toString() {
		return '"' + this.path.toString() + '"' + ", " + this.frequency + ", "
				+ this.position;
	}

	/**
	 * Updates an existing search result's position and frequency
	 * 
	 * @param frequency
	 *            Frequency that the search result is found in a given path
	 * @param position
	 *            Initial position that the word is found in a given path
	 */
	public void update(int frequency, int position) {
		this.frequency += frequency;
		if (position < this.position) {
			this.position = position;
		}
	}
}
