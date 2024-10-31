package unl.soc;

import java.time.LocalDateTime;

public class TextPost extends Post {
	protected String textContent;
	
	TextPost(Account postAccount, String textContent){
		super(postAccount); // call parent class, Post
		this.textContent = textContent;
	}
	
	// method overloading
	TextPost(Account postAccount, LocalDateTime postTime, String textContent){
		super(postAccount, postTime); // call parent class, Post
		this.textContent = textContent;
	}
	
	public String getTextContent() {
		return textContent;
	}
	
	
	@Override
	public String getFormattedContent() {
		
		StringBuilder formattedText = new StringBuilder();
	    String[] words = textContent.split(" ");  // split text into words
	    String currentLine = "";

	    for (String word : words) {
	        
	    	// while the word itself is longer than 40 characters, split it into chunks
	        while (word.length() > 40) {
	            // Add the first 40 characters of the word to the current line
	            formattedText.append(String.format("|%-40s|\n", word.substring(0, 40)));
	            // Update the word to the remaining part
	            word = word.substring(40);
	        }

	        // if adding this word exceeds the 40-character limit, finalize the current line
	        if (currentLine.length() + word.length() + 1 > 40) {
	            formattedText.append(String.format("|%-40s|\n", currentLine.trim()));
	            currentLine = "";  // reset the current line
	        }

	        // add the word to the current line, with a space if needed
	        currentLine += word + " ";
	    }

	    // append the last line if there is any remaining content
	    if (!currentLine.isEmpty()) {
	        formattedText.append(String.format("|%-40s|\n", currentLine.trim()));
	    }

	    return formattedText.toString();
	}

}