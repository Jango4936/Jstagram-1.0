package unl.soc;

import java.time.LocalDateTime;
import java.util.*;

public class TextArtPost extends Post {
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
		// TODO Auto-generated method stub
		return null;
	}

}
