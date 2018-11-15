/**
 * 
 * @author Nick DiGennaro
 * 
 * This class was created by me for an assignment which read in words from text files and kept track of which
 * words were read and how many times they were read. The classes purpose is to be an aggregate for the WordDictionary
 * data type.
 *
 */

public class Word implements Comparable<Word>{
	
	private String value;
	private int count;
	
	/**
	 * Constructor for Word, initializing the String, value, and the int, count.
	 * 
	 * @param String value
	 * @param int amount
	 */
	public Word(String value, int amount) {
		this.value = value;
		count = amount;
	}
	
	/**
	 * Increments count by the enter int, amount.
	 * 
	 * @param int amount
	 */
	public void increaseCount(int amount) {
		count += amount;
	}
	
	/**
	 * Getter for the String, value.
	 * 
	 * @return String value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Getter for the int, count
	 * 
	 * @return int count
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Auto generated hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * Auto generated equals method
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * Method that compares two Words based on first, the int, count, from largest to smallest and second,
	 * the String, value, in lexicographical order.
	 */
	@Override
	public int compareTo(Word w) {	
		if(this.count < w.count) {
			return 1;
		} else if(this.count == w.count) {
			if (value.compareTo(w.value) > 0){
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	
	
	
	
}
