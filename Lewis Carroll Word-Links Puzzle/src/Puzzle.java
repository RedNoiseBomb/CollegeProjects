import java.io.*;  
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


public class Puzzle {

	public static void main(String[] args) throws IOException  {
		// TODO Auto-generated method stub
		int j = 0;
		while(j != 1) {
			System.out.print("Enter a comma separated list of words (or an empty list to quit): ");
			Scanner input = new Scanner(System.in);
			String inputLine = input.nextLine();
			if (inputLine.equalsIgnoreCase("")) {
				break;
			}
			
			String[] readUserWords = readWordList(inputLine);
			isWordChain (readUserWords);
		}
	}
	
	public static String[] readDictionary () {
		FileReader fr = null;
		try {
			fr = new FileReader("C:\\WRDS\\words.txt");
			// you can choose whatever directory you have your "words" file in
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}    
	    BufferedReader br = new BufferedReader(fr);
	    ArrayList <String> dictListWords = new ArrayList <String>();
	    String nextLine;
	    try {
			while((nextLine = br.readLine()) != null) {
				dictListWords.add(nextLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String[] dictWords = dictListWords.toArray(new String [0]);
	    return dictWords;
	}
	
	public static String[] readWordList (String inputLine) {
		String[] readUserWords = inputLine.split(", ");
		return readUserWords;
	}
	
	public static boolean isUniqueList (String[] userWords) {
		int k = 0;
		for (int i = 1; i <= userWords.length; i++) {
			if (i == userWords.length) {
				k++;
				i = 0;
			}
			if (k == i) {
				i++;
				if (i >= userWords.length) {
					break;
				}
			}
			if (userWords[k] == userWords[i]) {
				return false;
			}
			if (k == userWords.length) {
				break;
			}
		}
		return true;
	}
	
	public static boolean isEnglishWord (String userWord) {
		String[] dictWords = readDictionary();
		
		if(Arrays.binarySearch(dictWords, userWord) < 0) {
			return false;
		}
		else return true;
	}
	
	public static boolean isDifferentByOne (String wordOne, String wordTwo) {
		if (wordOne.length() != wordTwo.length()) {
			return false;
		}
		int diffCounter = 0;
		for (int i = 0; i < wordOne.length() && diffCounter < 2; i++) {
			if (wordOne.charAt(i) != wordTwo.charAt(i)) {
				diffCounter++;
			}
		}
		if (diffCounter < 2) {
			return true;
		}
		else return false;
 	}
	
	public static boolean isWordChain (String[] words) {
		boolean isUnique = isUniqueList (words);
		boolean isEnglish = true;
		for (int i = 0; i < words.length; i++) {
			isEnglish = isEnglishWord (words[i]);
			if (!isEnglish) {
				break;
			}
		}
		boolean isDifferent = true;
		String wordOne; String wordTwo;
		for (int i = 0; i < words.length-1; i++) {
			wordOne = words[i];
			wordTwo = words[i+1];
			isDifferent = isDifferentByOne (wordOne, wordTwo);
			if (isDifferent == false) {
				break;
			}
		}
		if (isUnique && isEnglish && isDifferent) {
			System.out.println("Valid chain of words from Lewis Carroll's word-links game.");
			return true;
		}
		else {
			System.out.println("Not a valid chain of words from Lewis Carroll's word-links game.");
			return false;
		}
	}

}
