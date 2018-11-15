import java.util.Arrays;

/**
 * Your implementation of the LookupInterface.  The only public methods
 * in this class should be the ones that implement the interface.  You
 * should write as many other private methods as needed.  Of course, you
 * should also have a public constructor.
 * 
 * @author // TODO: Nick DiGennaro
 */
  
 
public class StudentLookup implements LookupInterface {
	
	WordDictionary words;
	
	public StudentLookup() {
		words = new WordDictionary();
	}

	@Override
	public void addString(int amount, String s) {
		Word w = new Word(s, amount);
		words.add(w, amount);
	}

	@Override
	public int lookupCount(String s) {
		return words.getCount(s);
	}

	@Override
	public String lookupPopularity(int n) {
		return words.lookupPopularity(n);
	}

	@Override
	public int numEntries() {
		return words.getSize();
	}
	
	public WordDictionary getWords() {
		return words;
	}
    
}
