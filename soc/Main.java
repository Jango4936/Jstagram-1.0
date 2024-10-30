package unl.soc;

import java.util.*;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	private static Account currentAccount = null;
	private static String input = "";
	private static boolean isRunning = true;

	private static Map<String, Account> accounts = new HashMap<>();
	private static List<Post> postList = new ArrayList<>();

	public static void main(String[] args) {

		accounts.put("admin", new Account("admin", "admin")); // pre-registered admin account

		// loop the program as long as it's not terminated by user
		while (isRunning == true) {
			Views.mainWindow(); // call mainmenu page

			while (true) {

				do {
					System.out.print("Choose: ");
					input = scanner.nextLine();

					if (!input.isEmpty() && input.length() == 1) {
						break;
					} else if (input.isBlank()) {
						System.out.println("Invalid input, please don't leave it blank");
					} else if (input.length() > 1) {
						System.out.println("Invalid input, please enter only 1 character!!");
					}

				} while (input.isBlank() || input.length() > 1); // loop and make sure user input is valid

				char option = input.charAt(0); // option variable store user's current choice

				switch (Character.toLowerCase(option)) {
				case 'r':
					register(); // if user choose r, call register method
					break;
				case 'l':
					login(); // if user choose l, call login method
					break;
				case 'q':
					isRunning = false; // if user choose q, terminated the program
					break;
				default:
					System.out.println("Invalid input. Please enter 'r', 'l', or 'q'"); // if user input incorrect
																						// character
				}
				if (isRunning == false) {
					break; // terminated the program and end the while loop
				}
			}

		}
		System.out.println("Program terminated");
	}

	// register method for new account
	private static void register() {

		Views.accountRegistrationWindow(); // call register menu

		// ask user to enter their details
		System.out.print("Enter Username: ");
		String userName = scanner.nextLine();
		System.out.print("Enter Password: ");
		String password = scanner.nextLine();
		System.out.print("Enter Phone Number: ");
		String phoneNumber = scanner.nextLine();
		System.out.print("Enter Email: ");
		String email = scanner.nextLine();

		// check if current username exist in the memory
		if (accounts.containsKey(userName) == true) {
			System.out.println("The username " + userName + " is already taken!! Press any key to return!!");
		} else {
			accounts.put(userName, new Account(userName, password, phoneNumber, email));
			System.out.println("Account created successfully!! Press any key to return...");
		}
		scanner.nextLine();
		Views.mainWindow(); // going back to mainmenu
	}

	// login method for existing account
	private static void login() {
		sortPost('+'); // Set post order to default order every time we login
		Views.accountLoginWindow(); // call login page

		

			// ask user to enter their details
			System.out.print("Enter UserName: ");
			String userName = scanner.nextLine();
			System.out.print("Enter password: ");
			String password = scanner.nextLine();

			// check if current username and password exist/correct in the memory
			if (accounts.containsKey(userName) == true && accounts.get(userName).getUserPassword().equals(password)) {

				System.out.println(
						"Account login successfully!! Welcome " + userName + "!!" + " Press any key to continue..."); // welcome
																														// message

				currentAccount = accounts.get(userName); // set current account to the current user
				scanner.nextLine(); // pause gap :)
				viewPost();	// call view post menu
				
			} else {
				System.out
						.println("Invalid username or password. Please try again bruh!! Press any key to continue...");
				scanner.nextLine();
				Views.mainWindow();
				
			
		}

	}

	private static void viewPost() {
		Views.postViewWindow(postList, currentAccount);
		while (true) {
			do {
				System.out.print("Choose: ");
				input = scanner.nextLine();
				if (!input.isEmpty() && input.length() == 1) {
					break;
				} else if (input.isBlank()) {
					System.out.println("Invalid input, please don't leave it blank");
				} else if (input.length() > 1) {
					System.out.println("Invalid input, please enter only 1 character!!");
				}

			} while (input.isBlank() || input.length() > 1);

			char option = input.charAt(0);
			switch (Character.toLowerCase(option)) {
			case '+':
				newPost();
				Views.postViewWindow(postList, currentAccount);
				break;
			case '*':
				sortOptions();
				Views.postViewWindow(postList, currentAccount);
				break;
			case 's':
				settingsMenu();
				if (currentAccount == null) {
					Views.mainWindow();
				} else {
					Views.postViewWindow(postList, currentAccount);
				}

				break;
			case 'l':
				currentAccount = null;
				Views.mainWindow();
				break;
			case 'q':
				isRunning = false;
				break;
			case '$':
				if (currentAccount.getUsername().equals("admin")) {
					adminMenu();
				} else {
					System.out.println("Invalid input. Please enter a valid option");
				}
				break;

			default:
				System.out.println("Invalid input. Please enter a valid option");
			}
			if (isRunning == false || currentAccount == null) {
				break;
			}
		}

	}

	private static void adminMenu() {
		Views.displayAdminMenu(currentAccount);
		boolean status = true;
		while (status == true) {
			do {
				System.out.print("Choose: ");
				input = scanner.nextLine();
				if (!input.isEmpty() && input.length() == 1) {
					break;
				} else if (input.isBlank()) {
					System.out.println("Invalid input, please don't leave it blank");
				} else if (input.length() > 1) {
					System.out.println("Invalid input, please enter only 1 character!!");
				}

			} while (input.isBlank() || input.length() > 1);

			char option = input.charAt(0);
			switch (Character.toLowerCase(option)) {
			case '+':
				DataManager.saveCurrentData(accounts, postList);
				Views.postViewWindow(postList, currentAccount);
				System.out.println("All accounts and posts saved succesfully!!");
				status = false;
				break;
			case '-':
				DataManager.loadLastSavedData(accounts, postList);
				Views.postViewWindow(postList, currentAccount);
				System.out.println("All accounts and posts loaded succesfully!!");
				status = false;
				break;
			case 'r':
				Views.postViewWindow(postList, currentAccount);
				status = false;
				break;
			default:
				System.out.println("Invalid input. Please enter a valid options!!");
			}
			if (status == false) {
				break;
			}
		}
	}

	private static void sortOptions() {
		Views.displayPostSortOption(currentAccount);
		boolean status = true;
		while (status == true) {
			do {
				System.out.print("Choose: ");
				input = scanner.nextLine();
				if (!input.isEmpty() && input.length() == 1) {
					break;
				} else if (input.isBlank()) {
					System.out.println("Invalid input, please don't leave it blank");
				} else if (input.length() > 1) {
					System.out.println("Invalid input, please enter only 1 character!!");
				}

			} while (input.isBlank() || input.length() > 1);

			char option = input.charAt(0);
			status = sortPost(option);

			if (status == false) {
				break;
			}
		}
	}

	private static boolean sortPost(char option) {
		switch (Character.toLowerCase(option)) {
		case '+':
			Collections.sort(postList, Comparator.comparing(Post::getPostTime)); // Sort by Ascending Order of Time
			return false;
		case '-':
			Collections.sort(postList, (p1, p2) -> p2.getPostTime().compareTo(p1.getPostTime())); // Sort by Descending
																									// Order
			// of Time
			return false;
		case '*':
			Collections.sort(postList, Comparator.comparing(post -> post.getPostAccount().getUsername())); // Sort by
																											// Ascending
																											// Order of
																											// UserName
			return false;
		case '=':
			Collections.sort(postList,
					Comparator.comparing(post -> ((Post) post).getPostAccount().getUsername()).reversed()); // Sort by
																											// Descending
																											// Order of
																											// UserName
			return false;
		default:
			System.out.println("Invalid input. Please enter a valid options!!!");
			return true;
		}
	}

	private static void settingsMenu() {
		Views.displayAccountSettings(currentAccount);
		boolean status = true;
		while (status == true) {
			do {
				System.out.print("Choose: ");
				input = scanner.nextLine();
				if (!input.isEmpty() && input.length() == 1) {
					break;
				} else if (input.isBlank()) {
					System.out.println("Invalid input, please don't leave it blank");
				} else if (input.length() > 1) {
					System.out.println("Invalid input, please enter only 1 character!!");
				}

			} while (input.isBlank() || input.length() > 1);

			char option = input.charAt(0);
			switch (Character.toLowerCase(option)) {
			case '-':
				accounts.remove(currentAccount.getUsername());
				for (int i = 0; i < postList.size(); i++) {
					if (postList.get(i).postAccount.equals(currentAccount)) {
						postList.remove(i);
						i--;
					}
				}
				currentAccount = null;

				status = false;
				break;
			case 'r':
				Views.postViewWindow(postList, currentAccount);
				status = false;
				break;
			default:
				System.out.println("Invalid input. Please enter a valid options!!");
			}
			if (status == false) {
				break;
			}
		}
	}

	private static void newPost() {
		Views.newPostWindow(currentAccount);
		boolean status = true;
		while (status == true) {
			do {
				System.out.print("Choose: ");
				input = scanner.nextLine();
				if (!input.isEmpty() && input.length() == 1) {
					break;
				} else if (input.isBlank()) {
					System.out.println("Invalid input, please don't leave it blank");
				} else if (input.length() > 1) {
					System.out.println("Invalid input, please enter only 1 character!!");
				}

			} while (input.isBlank() || input.length() > 1);

			char option = input.charAt(0);
			switch (Character.toLowerCase(option)) {
			case 't':
				postText();
				status = false;
				break;
			case 'a':
				postTextArt();
				status = false;
				break;
			default:
				System.out.println("Invalid input. Please enter 'T' or 'A'");
			}
			if (status == false) {
				break;
			}
		}
	}

	private static void postTextArt() {
		Views.displayTextArtOptions(currentAccount);
		boolean status = true;
		while (status == true) {
			do {
				System.out.print("Choose: ");
				input = scanner.nextLine();
				if (!input.isEmpty() && input.length() == 1) {
					break;
				} else if (input.isBlank()) {
					System.out.println("Invalid input, please don't leave it blank");
				} else if (input.length() > 1) {
					System.out.println("Invalid input, please enter only 1 character!!");
				}

			} while (input.isBlank() || input.length() > 1);

			char option = input.charAt(0);
			switch (Character.toLowerCase(option)) {
			case '0':
				postList.add(new TextArtPost(currentAccount, '0'));
				status = false;
				break;
			case '1':
				postList.add(new TextArtPost(currentAccount, '1'));
				status = false;
				break;
			case '2':
				postList.add(new TextArtPost(currentAccount, '2'));
				status = false;
				break;
			case '3':
				postList.add(new TextArtPost(currentAccount, '3'));
				status = false;
				break;
			default:
				System.out.println("Invalid input. Please enter a number");
			}
			if (status == false) {
				break;
			}
		}
	}

	private static void postText() {
		System.out.print("Enter your text: ");
		input = scanner.nextLine();
		postList.add(new TextPost(currentAccount, input));
	}
}