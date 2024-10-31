package unl.soc;

import java.time.LocalDateTime;
import java.io.*;
import java.util.List;
import java.util.Map;

public class DataManager {
	private static String fileDirectory = "src\\unl\\soc\\save\\savefile.data";
	
	
	// constructor
	DataManager() {
		
	}
	
	// method to save current data to a file
	public static void saveCurrentData(Map<String, Account> accounts, List<Post> postList) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileDirectory))) {

			writer.write("#Accounts\n"); // Save Account informations
			
			// loop through the account hash map and write its information on the file, information is split by "|"
			for (Account account : accounts.values()) {
				writer.write(account.getUsername() + "|" + account.getUserPassword() + "|" + account.getUserPhoneNum()
						+ "|" + account.getUserEmail() + "\n");
			}

			writer.write("#Posts\n"); // Save Posts informations
			
			// loop through the post list and write its information on the file, information is also split by "|"
			for (Post post : postList) {
				writer.write(post.postAccount.getUsername() + "|" + post.getPostTime() + "|");
				
				if (post instanceof TextPost) {
					// current post is Text Post
					writer.write("TextPost" + "|" + ((TextPost) post).getTextContent() + "\n");
					
				} else if (post instanceof TextArtPost) {
					// current post is Text Art Post
					writer.write("TextArtPost" + "|" + ((TextArtPost) post).getChoiceOfArt() + "\n");
					
				}
			}

			writer.close(); // closed the buffered writer
			System.out.println("All accounts and posts saved succesfully!!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// method to load previously saved data from the file
	public static void loadLastSavedData(Map<String, Account> accounts, List<Post> postList) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileDirectory))) {
			
			String currentLine; // current line that is read by buffered reader
			
			boolean loadAccount = false;
			boolean loadPost = false; // initialize some variables for while loop later

			// Loop while current line is not empty
			while ((currentLine = reader.readLine()) != null) {
				
				if (currentLine.equals("#Accounts")) {
					// it's time to load accounts
					loadAccount = true;
					loadPost = false;
					continue;
				} else if (currentLine.equals("#Posts")) {
					// it's time to load posts
					loadAccount = false;
					loadPost = true;
					continue;
				}
				
				// token array is use to store each and every informations in current line that was splitted by "|"
				String token[] = currentLine.split("\\|");
				
				
				if(loadAccount == true) {
					
					/* token[0] is username
					 * token[1] is password
					 * token[2] is email
					 * token[3] is phone number
					 */
					
					accounts.put(token[0], new Account(token[0], token[1],token[2],token[3]));
					continue;
					
				} else if (loadPost == true) {
					
					Account postAccount = accounts.get(token[0]);
					
					if (token[2].equals("TextPost")) {
						// post is text post
						postList.add(new TextPost(postAccount, LocalDateTime.parse(token[1]), token[3]));
						
					} else if (token[2].equals("TextArtPost"))
						// post is text art post
						postList.add(new TextArtPost(postAccount, LocalDateTime.parse(token[1]), token[3].charAt(0)));
					
					continue;
				}
				
			}
			reader.close(); // close buffered reader
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
