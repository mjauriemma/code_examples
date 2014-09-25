public class MyListElem implements ListElem {

	/**
	 * Global Variables
	 */
	private int numKeys;
	protected Comparable[] myKeys;
	protected MyListElem[] myNext;
	protected MyListElem[] myPrev;
	protected Object myData;

	/**
	 * Constructor for making 'dummy' head and tail nodes
	 * 
	 * @param numKeys
	 *            length of the keys array per node
	 */
	public MyListElem(int numKeys) {
		this.numKeys = numKeys;

		myKeys = new Comparable[numKeys];
		myNext = new MyListElem[numKeys];
		myPrev = new MyListElem[numKeys];

	}

	/**
	 * Overloaded constructor for creating nodes of the doubly linked list
	 * 
	 * @param keys
	 *            Comparable array of 'keys' to sort each keyList by
	 * @param data
	 *            Data object stored in each noded (Same for every key)
	 * @param numKeys
	 *            Length of the keys array (must match all other numKeys)
	 */
	public MyListElem(Comparable[] keys, Object data, int numKeys) {
		this.numKeys = numKeys;
		this.myData = data;
		this.myKeys = keys;
		myNext = new MyListElem[numKeys];
		myPrev = new MyListElem[numKeys];

	}


	@Override
	public int numKeys() {
		return numKeys;
	}

	@Override
	public Comparable key(int keyIndex) {
		return myKeys[keyIndex];
	}

	@Override
	public Object data() {
		return myData;
	}

}