import java.util.HashMap;

/**
 * Class that handles parsing an array of arguments into flag/value pairs. flag
 * is considered to be a non-null String that starts with a "-" dash symbol. A
 * value optionally follows a flag, and must not start with a "-" dash symbol.
 * 
 * @author Matthew Auriemma
 */

public class ArgumentParser {

	/** Stores flag/value pairs of arguments. */
	private final HashMap<String, String> argumentMap;

	public ArgumentParser(String[] args) {
		argumentMap = new HashMap<String, String>();
		parseArgs(args);
	}

	/**
	 * Parses the provided array of arguments into flag/value pairs, storing the
	 * results in an internal map.
	 * 
	 * @param arguments
	 *            to parse
	 */
	private void parseArgs(String[] arguments) {

		if (arguments == null) {
			return;
		}

		String f = null;

		for (int i = 0; i < arguments.length; i++) {

			if (isFlag(arguments[i])) {
				f = arguments[i];
				argumentMap.put(f, null);
			}
			if (isValue(arguments[i]) && f != null) {
				if (hasFlag(f)) {
					argumentMap.remove(f);
				}
				argumentMap.put(f, arguments[i]);
				f = null;
			}
		}

	}

	/**
	 * Tests whether the provided String is a flag, i.e. whether the String is
	 * non-null, starts with a "-" dash, and has at least one character
	 * following the dash.
	 * 
	 * @param text
	 *            to test
	 * @return <code>true</code> if the text is non-null, start with the "-"
	 *         dash symbol, and has a flag name of at least one character
	 */
	public static boolean isFlag(String text) {

		if (text == null || text.trim().length() < 2) {

			return false;
		}

		if (text.indexOf('-') == 0) {

			return true;

		} else {

			return false;
		}
	}

	/**
	 * Tests whether the provided String is a value, i.e. whether the String is
	 * non-null, non-empty, and does NOT start with a "-" dash.
	 * 
	 * @param text
	 *            to test
	 * @return <code>true</code> if the text is non-null, non-empty, and does
	 *         NOT start with the "-" dash symbol
	 */
	public static boolean isValue(String text) {

		if (text == null || text.trim().isEmpty()) {
			return false;
		}
		if (text.startsWith("-")) {
			return false;

		} else {
			return true;
		}
	}

	/**
	 * Returns the number of flags stored in the argument map.
	 * 
	 * @return number of flags stored in the argument map
	 */
	public int numFlags() {

		return argumentMap.size();
	}

	/**
	 * Checks whether the provided flag exists in the argument map.
	 * 
	 * @param flag
	 *            to check for
	 * @return <code>true</code> if the flag exists
	 */
	public boolean hasFlag(String flag) {

		return argumentMap.containsKey(flag);
	}

	/**
	 * Checks whether the provided flag has an associated non-null value.
	 * Returns <code>false</code> if there is no value for the flag, or if the
	 * flag does not exist.
	 * 
	 * @param flag
	 *            to check for value
	 * @return <code>true</code> if the flag has a non-null value, or
	 *         <code>false</code> if the value or flag does not exist
	 */
	public boolean hasValue(String flag) {
		return (argumentMap.get(flag) != null);
	}

	/**
	 * Returns the value associated with a flag, or <code>null</code> if the
	 * flag does not exist, or the flag does not have an associated value.
	 * 
	 * @param flag
	 *            to fetch associated value
	 * @return value of the flag if it exists, or <code>null</code>
	 */
	public String getValue(String flag) {

		return argumentMap.get(flag);
	}
}