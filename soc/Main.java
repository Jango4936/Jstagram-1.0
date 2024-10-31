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
					System.out.println("Invalid input. Please enter 'r', 'l', or 'q'");
					// if user input incorrect character
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
					"Account login successfully!! Welcome " + userName + "!!" + " Press any key to continue...");
			// welcome message

			currentAccount = accounts.get(userName); // set current account to the current user
			scanner.nextLine(); // pause gap :)
			viewPost(); // call view post menu

		} else {
			// incorrect password or invalid username
			System.out.println("Invalid username or password. Please try again bruh!! Press any key to continue...");
			scanner.nextLine(); // pause gap :)
			Views.mainWindow(); // go back to mainmenu page

		}

	}

	// View post menu method
	private static void viewPost() {

		// view all the posts store in memory and menu for user
		Views.postViewWindow(postList, currentAccount);

		// loop as long as user input appropriate options
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
				newPost(); // open new post menu
				Views.postViewWindow(postList, currentAccount);
				break;
			case '*':
				sortOptions(); // open post sort menu
				Views.postViewWindow(postList, currentAccount);
				break;
			case 's':
				settingsMenu(); // open account settings menu

				// check if the user deleted their account
				if (currentAccount == null) {
					Views.mainWindow();
					// user deleted their account,go back to mainmenu page
				} else {
					Views.postViewWindow(postList, currentAccount);
					// user not deleted their account,go back to post view page
				}

				break;
			case 'l':
				currentAccount = null; // logout current user and go back to mainmenu
				Views.mainWindow();
				break;
			case 'q':
				isRunning = false; // terminate the program
				break;
			case '$':
				// check if current account is admin
				if (currentAccount.getUsername().equals("admin")) {
					// is admin, open admin menu
					adminMenu();
				} else {
					// not admin but enter "$"
					System.out.println("Invalid input. Please enter a valid option");
				}
				break;

			default:
				System.out.println("Invalid input. Please enter a valid option");
			}
			if (isRunning == false || currentAccount == null) {
				break; // if user choose to terminate the program or logout, break the loop
			}
		}

	}

	// special admin menu method :)
	private static void adminMenu() {

		Views.displayAdminMenu(currentAccount); // print admin menu out

		// switch for current loop, to prevent user enter invalid options
		boolean status = true;

		while (status == true) {

			do {
				// this inner loop is to make sure user dont leave the input blank or type more
				// than 1 char
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
				// save all the data include user and post
				DataManager.saveCurrentData(accounts, postList);
				Views.postViewWindow(postList, currentAccount);
				System.out.println("All accounts and posts saved succesfully!!");

				status = false; // for breaking the while loop;
				break;
			case '-':
				// load previous saved data include user and post
				DataManager.loadLastSavedData(accounts, postList);
				Views.postViewWindow(postList, currentAccount);
				System.out.println("All accounts and posts loaded succesfully!!");

				status = false; // for breaking the while loop
				break;
			case 'r':
				// admin change his mind? return to post page
				Views.postViewWindow(postList, currentAccount);
				status = false; // for breaking the while loop
				break;
			default:
				System.out.println("Invalid input. Please enter a valid options!!");
			}
			if (status == false) {
				break; // break the loop
			}
		}
	}

	// method to call sort option menu
	private static void sortOptions() {
		Views.displayPostSortOption(currentAccount); // print all the sort options out

		// switch for current loop, to prevent user enter invalid options
		boolean status = true;
		while (status == true) {

			do {
				// this inner loop is to make sure user dont leave the input blank or type more
				// than 1 char
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
			// Sort by Ascending Order of Time
			Collections.sort(postList, Comparator.comparing(Post::getPostTime));
			return false;

		case '-':
			// Sort by Descending Order of Time
			Collections.sort(postList, (p1, p2) -> p2.getPostTime().compareTo(p1.getPostTime()));
			return false;

		case '*':
			// Sort by Ascending Order of Username
			Collections.sort(postList, Comparator.comparing(post -> post.getPostAccount().getUsername()));
			return false;

		case '=':
			// Sort by Descending Order of Username
			Collections.sort(postList,
					Comparator.comparing(post -> ((Post) post).getPostAccount().getUsername()).reversed());
			return false;

		default:
			System.out.println("Invalid input. Please enter a valid options!!!");
			return true;
		}
	}

	// account settings menu method
	// method to let user choose either delete his account or return to main menu
	private static void settingsMenu() {
		// print options to user
		Views.displayAccountSettings(currentAccount);

		// switch for current loop, to prevent user enter invalid options
		boolean status = true;
		while (status == true) {
			do {
				// this inner loop is to make sure user dont leave the input blank or type more
				// than 1 char
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
				// delete current account from the accounts hash map
				accounts.remove(currentAccount.getUsername());

				// delete every post that is posted by current account
				for (int i = 0; i < postList.size(); i++) {
					if (postList.get(i).postAccount.equals(currentAccount)) {
						postList.remove(i);
						i--; // offset, prevent skipping after delete the post
					}
				}
				currentAccount = null; // reset

				status = false; // for breaking the loop
				break;
			case 'r':
				// user change his mind, return to view post menu
				Views.postViewWindow(postList, currentAccount);
				status = false; // for breaking the loop
				break;
			default:
				System.out.println("Invalid input. Please enter a valid options!!");
			}
			if (status == false) {
				break; // breaking the loop
			}
		}
	}

	// new post menu method
	// method to let user choose either post text or art text
	private static void newPost() {
		Views.newPostWindow(currentAccount); // print out the menu

		boolean status = true; // switch for breaking the loop later
		while (status == true) {
			do {
				// this inner loop is to make sure user dont leave the input blank or type more
				// than 1 char
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
				// post text selected
				postText();
				status = false;
				break;
			case 'a':
				// post text art selected
				postTextArt();
				status = false;
				break;
			default:
				System.out.println("Invalid input. Please enter 'T' or 'A'");
			}
			if (status == false) {
				break; // breaking the loop
			}
		}
	}

	// postTextArt menu method
	// method to let user choose which text art to post
	private static void postTextArt() {
		Views.displayTextArtOptions(currentAccount); // print out all the art options

		boolean status = true; // for breaking the loop later
		while (status == true) {
			do {
				// this inner loop is to make sure user dont leave the input blank or type more
				// than 1 char
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

			// switch case to add selected art option to the postList
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
				break; // break the loop
			}
		}
	}

	// Post Text Menu method
	// method to let user type their text for posting later
	private static void postText() {
		System.out.print("Enter your text: ");
		input = scanner.nextLine();
		postList.add(new TextPost(currentAccount, input));
	}
}