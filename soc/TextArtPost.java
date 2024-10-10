package unl.soc;

import java.time.LocalDateTime;
import java.util.*;

public class TextArtPost extends Post {
	protected char choiceOfArt;
	
	
	TextArtPost(Account postAccount, char choiceOfArt) {
		super(postAccount);
		this.choiceOfArt = choiceOfArt;
	}
	
	public char getChoiceOfArt() {
		return choiceOfArt;
	}

	public final static List<String[]> OPTIONS;
    
	static { // initialize when the class is loaded
		OPTIONS = initTextArtOptions();
    	}
    
    	public static List<String[]> initTextArtOptions(){
    		List<String[]> textArts = new ArrayList<>();
    		String[] house = {
        		"  ____||____   ",
        		" ///////////\\ ",
        		"///////////  \\",
        		"|    _    |  | ",
        		"|[] | | []|[]| ",
        		"|   | |   |  | ",
    		};
    		textArts.add(house);
    		String[] dog = {
    			" /^ ^\\ ",
    			"/ 0 0 \\",
    			"V\\ Y /V",
    			" / - \\ ", 
    			"/    | ",
    			"V__) ||"
    		};
    		textArts.add(dog);
    		String[] coffee = {
    			"  ( (    ",
    			"   ) )   ",
    			"........ ",
    			"|      |]",
    			"\\      / ",  
    			" `----'   "	
    		};
    		textArts.add(coffee);
    		String[] flower = {
    			"   (\\__         ",
    			"  :=)__)-|  __/) ",
    			"   (/    |-(__(=:",
    			" ______  |  _ \\) ",
    			"/      \\ | / \\   ",
    			"     ___\\|/___\\  ",
    			"    [         ]\\ ",
    			"     \\       /   ",
    			"      \\_____/    "
    		};
    		textArts.add(flower);
    		return textArts;
	}

	@Override
	public String getFormattedContent() {
	    StringBuilder formattedText = new StringBuilder();
	    int index = Character.getNumericValue(choiceOfArt);
	    
	    for (int i = 0; i < OPTIONS.get(index).length; i++) {
	        // Get the current line of art
	        String artLine = OPTIONS.get(index)[i];
	        
	        // Ensure the line is padded to exactly 40 characters (left-aligned)
	        String paddedLine = String.format("%10s%-30s", "",artLine);
	        
	        // Append the formatted line with "|" on both sides
	        formattedText.append("|" + paddedLine + "|\n");
	    }
	    
	    return formattedText.toString();
	}

}
