package unl.soc;

import java.time.LocalDateTime;
import java.io.*;
import java.util.List;
import java.util.Map;

public class DataManager {

	DataManager() {

	}

	public static void saveCurrentData(Map<String, Account> accounts, List<Post> postList) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\unl\\soc\\save\\savefile.data"))) {

			writer.write("#Accounts\n"); // Save Account informations

			for (Account account : accounts.values()) {
				writer.write(account.getUsername() + "|" + account.getUserPassword() + "|" + account.getUserPhoneNum()
						+ "|" + account.getUserEmail() + "\n");
			}

			writer.write("#Posts\n"); // Save Posts informations

			for (Post post : postList) {
				writer.write(post.postAccount.getUsername() + "|" + post.getPostTime() + "|");
				if (post instanceof TextPost) {
					writer.write("TextPost" + "|" + ((TextPost) post).getTextContent() + "\n");
				} else if (post instanceof TextArtPost) {
					writer.write("TextArtPost" + "|" + ((TextArtPost) post).getChoiceOfArt() + "\n");
				}
			}

			writer.close();
			System.out.println("All accounts and posts saved succesfully!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadLastSavedData(Map<String, Account> accounts, List<Post> postList) {
		try (BufferedReader reader = new BufferedReader(new FileReader("src\\unl\\soc\\save\\savefile.data"))) {
			String line;
			boolean loadAccount = false;
			boolean loadPost = false; // initialize some variables for while loop later

			// Loop while the line is not empty
			while ((line = reader.readLine()) != null) {
				if (line.equals("#Accounts")) {
					loadAccount = true;
					loadPost = false;
					continue;
				} else if (line.equals("#Posts")) {
					loadAccount = false;
					loadPost = true;
					continue;
				}
				
				String token[] = line.split("\\|");
				
				
				if(loadAccount == true) {
					accounts.put(token[0], new Account(token[0], token[1],token[2],token[3]));
					continue;
				} else if (loadPost == true) {
					
					Account postAccount = accounts.get(token[0]);
					
					if (token[2].equals("TextPost")) {
						postList.add(new TextPost(postAccount, LocalDateTime.parse(token[1]), token[3]));
						
					} else if (token[2].equals("TextArtPost"))
						postList.add(new TextArtPost(postAccount, LocalDateTime.parse(token[1]), token[3].charAt(0)));
					
					continue;
				}
				
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
