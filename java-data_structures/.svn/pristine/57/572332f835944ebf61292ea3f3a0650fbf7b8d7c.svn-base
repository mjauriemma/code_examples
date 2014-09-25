/**
 * Hash table for storing the values of Cities as integers to be used as keys in
 * the nodes array of MapGraph. Utilizes open Hashing.
 * 
 * @author mjauriemma
 * 
 */
public class HashTable {

	int size;
	HashNode[] hashNodeTable;
	HashNode head;
	HashNode temp;

	public HashTable(int size) {

		hashNodeTable = new HashNode[size];
		this.size = size;
	}

	public void hashTableInsert(String[] nameArray) {

		for (int j = 0; j < nameArray.length; j++) {
			int index = (int) hash(nameArray[j], nameArray.length);
			if (hashNodeTable[index] == null) {
				hashNodeTable[index] = new HashNode(j, nameArray[j]);
			} else {
				HashNode node = hashNodeTable[index];
				while (node.next != null) {
					node = node.next;
				}
				node.next = new HashNode(j, nameArray[j]);
			}
		}
	}

	static long hash(String key, int tablesize) {
		long h = 0;
		int i;

		for (i = 0; i < key.length(); i++) {
			h = (h << 4) + key.charAt(i);
		}

		return h % tablesize;
	}

	public int getID(String city, int length) {
		long h = hash(city, length);

		for (HashNode node = hashNodeTable[(int) h]; node != null; node = node.next) {
			if (city.compareTo(node.city) == 0) {
				return node.id;
			}
		}
		return -1;
	}

	public class HashNode {

		int id;
		String city;
		HashNode next;

		public HashNode(int cityId, String cityName) {

			id = cityId;
			city = cityName;
			next = null;
		}

		public String print() {
			return city;
		}

	}

}