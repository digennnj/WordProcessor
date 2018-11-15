/**
 * 
 * @author Nick DiGennaro
 * 
 * WordDictionary is a unique data type created by me to store objects of the class Word.
 * There is an Array of Words, words, and an ArrayList of Words, sortedWords.
 * 
 * words: A hash dictionary for easy retrieval of a Word based on its String value
 * sortedWords: An ArrayList that sorts the Words as they are entered based on first, the Word's int, count,
 * and second, the Word's String, value for easy retrieval of a Word based on count compared to other Word's counts.
 * 
 * Based on the assignment, Words can be added but not removed. Also, a Word's int, count, can only be increased. Because
 * of these parameters, it's not necessary to add a removed flag for linear probing, and when a Word's count is increased, the sort can start at
 * the Word's current position and move up the array, avoiding starting from the bottom and potentially working through each element.
 * 
 */

import java.util.ArrayList;

public class WordDictionary {

	private Word[] words;
	private ArrayList<Word> sortedWords;
	private int size;
	
	//Could create an initial really large array, may make it faster
	//Does resize properly though
	private final int DEFAULT_CAPACITY = 600000;

	/**
	 * Empty Constructor initializing the hashed array, its size, and the sortedWords ArrayList
	 * 
	 */
	public WordDictionary() {
		size = 0;
		words = new Word[DEFAULT_CAPACITY];
		sortedWords = new ArrayList<>();
	}

	/**
	 * Adds a Word to the WordDictionary, which adds a Word in the hash array, words, and the sorted ArrayList, sortedWords
	 * 
	 * @param w The Word being add, or increased, in WordDictionary
	 * @param amount The amount the Word is to be increased
	 * @return boolean
	 */
	public boolean add(Word w, int amount) {
		//Creates a hash code that's constrained to the current array size
		int hash = Math.abs( w.getValue().hashCode() % words.length );
		
		//Resize array after it's 70% full
		if(size >= words.length * .7) {
			words = resize();
		}

		//This Hash Dictionary uses linear probing which doesn't seem to hurt performance and if so, the decrease is marginal
		while(true) {

			if(words[hash] == null) {
				words[hash] = w;
				size++;
				//Add word to end of sorted array
				sortedWords.add(w);
				//Because array should be sorted, can call sort as you go even with new words
				sortAsYouGo(sortedWords.indexOf(w));
				return true;
			}

			if(words[hash].getValue().equals(w.getValue())) {
				//If the word already exists in the dictionary then increase the count of # of words found
				words[hash].increaseCount(amount);
				//Because the array is already sorted, sortAsYouGo can be called which
				//runs through the array until the new correct spot is found for the word with its increased count.
				sortAsYouGo(sortedWords.indexOf(w));
				
				//===============================================================
				//I don't need to increase count after the Word has been entered in sortedWords
				//because the Word reference in words is the same reference that's used in sortedWords
				//which is already increased above.
				//===============================================================

				return true;
			}

			//Increases the hash code if the current code isn't null, and the word being entered
			//isn't equal to the word that is found at the code.
			hash++;
			
			//Circles the hash code back to zero if the code reaches the end of the array
			if(hash == words.length) {
				hash = 0;
			}
		}
	}
	
	/**
	 * This is the only sort algorithm that's used. Very simple, in fact not really a common sort function. The 
	 * algorithm receives a Word that's either already in the dictionary or is new. If a word is already in the 
	 * dictionary, the algorithm starts at the Word's position and moves up the array looking for its new position. This can
	 * be done because the program can only increase the count of Words, and Words can't be deleted. If a Word is new,
	 * it is placed at the end of the array and moves up until its position is found.
	 * 
	 * The time complexity of this algorithm is at most N, but a Word should only move a couple of spaces each call.
	 * 
	 * @param i The position of the Word in sortedWords
	 */
	public void sortAsYouGo(int i) {
		
		int j = i;
		while(j>0 && sortedWords.get(j-1).compareTo(sortedWords.get(j)) == 1) {
			Word temp = sortedWords.get(j);
			sortedWords.set(j, sortedWords.get(j-1));
			sortedWords.set(j-1, temp);
			j--;
		}
		
	}		

	/**
	 * Returns the count of the entered String
	 * 
	 * @param s String of the Word to be retrieved
	 * @return int count
	 */
	public int getCount(String s) {
		//Creates a hash code based on the entered String, s
		int hash = Math.abs( s.hashCode() % words.length );
		//int to keep track of how many times a hash code position has been tracked
		int checked = 0;

		//Checks if the int checked is not as large as the size of the words array
		while(checked != words.length - 1) {
			
			//If the hash code doesn't have a word at that position then the Word
			//isn't in the WordDictionary because Words cannot be removed
			if(words[hash] == null) {
				return 0;
			}
			
			//If the hash code has a word at the position, and the Word's value
			//equals s, then return the count of the Word
			if(words[hash].getValue().equals(s)) {
				return words[hash].getCount();
			}

			//Increases the hash code if the current code isn't null, and the word being entered
			//isn't equal to the word that is found at the code.
			hash++;
			
			//Circles the hash code back to zero if the code reaches the end of the array
			if(hash == words.length) {
				hash = 0;
			}

			//If the statement reaches here then a hash code position has been checked so,
			//incremented the checked count.
			checked++;
		}
		return 0;
	}

	/**
	 * Returns the String of the Word, at the given position, based on popularity.
	 * Zero returns the most popular String in sortedWords, and the length of sortedWords returns the least popular String in sortedWords.
	 * 
	 * @param index The position of a Word based on Popularity
	 * @return String value
	 */
	public String lookupPopularity(int index) {	
		//If the the entered index is greater than zero, and less than sortedWords size.
		if(index >= 0 && index < size) {
			//Return the String of the Word associated with the given index.
			return sortedWords.get(index).getValue();
		}
		return "That is not a vaild index";
	}
	
	/**
	 * Resizes words when the array becomes 70% full
	 * 
	 * @return Word newWords
	 */
	private Word[] resize() {
		
		//Creates a temporary array that's double the size of words
		Word[] newWords = new Word[words.length*2];
		//resize is called in add, but before anything is added to words, therefore size will
		//remain the same, and does not need to be reset or accounted for in resize

		//Loops through each Word in words
		for(Word w : words) {
			//if the Word at that position is not null then proceed with adding it to the new dictionary
			if(w != null) {
				//Creates the hash code of the Word with the new array. The hash codes are probably different because
				//newWords is a different size from words
				int hash = Math.abs( w.getValue().hashCode() % newWords.length );

				//Same code from add but using the newWords array
				while(true) {

					if(newWords[hash] == null) {
						newWords[hash] = w;
						break;
					}

					if(newWords[hash].getValue().equals(w.getValue())) {
						newWords[hash].increaseCount(w.getCount());
						break;
					}

					hash++;
					if(hash == newWords.length) {
						hash = 0;
					}
				}
			}

		}

		//Returns the new array with same Words, but is a larger array
		return newWords;

	}
	
	/**
	 * Returns the amount of Words in the words
	 * 
	 * @return int size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Returns an ArrayList of Words, sortedWords, that sort the Words based on the 
	 * Word's int count then String value
	 * 
	 * @return ArrayList<Word> sortedWords
	 */
	public ArrayList<Word> getSorted() {
		return sortedWords;
	}
	

}
