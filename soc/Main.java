package unl.soc;

import java.util.*;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	private static Map<String, Account> accounts = new HashMap<>();
	private static Account currentAccount = null;
	private static List<Post> postList = new ArrayList<>();
	private static String input = "";
	private static boolean isRunning = true;

	public static void main(String[] args) {

		accounts.put("admin", new Account("admin", "admin"));

		while (isRunning == true) {
			Views.mainWindow();

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
				case 'r':
					register();
					break;
				case 'l':
					login();
					break;
				case 'q':
					isRunning = false;
					break;
				default:
					System.out.println("Invalid input. Please enter 'r', 'l', or 'q'");
				}
				if (isRunning == false) {
					break;
				}
			}

		}
		System.out.println("Program terminated");

//        
//       	Views.newPostWindow(currentAccount);
//       	Views.displayTextArtOptions(currentAccount);
	}

	private static void register() {
		Views.accountRegistrationWindow();
		System.out.print("Enter UserName: ");
		String userName = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		System.out.print("Enter phoneNumber: ");
		String phoneNumber = scanner.nextLine();

		if (accounts.containsKey(userName) == true) {
			System.out.println("The username " + userName + " is already taken!! Press any key to return!!");
		} else {
			accounts.put(userName, new Account(userName, password, phoneNumber));
			System.out.println("Account created successfully!!\nPress any key to return...");
		}
		scanner.nextLine();
		Views.mainWindow();
	}

	private static void login() {
		Views.accountLoginWindow();
		while (true) {
			System.out.print("Enter UserName: ");
			String userName = scanner.nextLine();
			System.out.print("Enter password: ");
			String password = scanner.nextLine();

			if (accounts.containsKey(userName) == true && accounts.get(userName).getUserPassword().equals(password)) {
				System.out.println(
						"Account login successfully!! Welcome " + userName + "!!" + "\nPress any key to continue...");
				currentAccount = accounts.get(userName);
				scanner.nextLine();
				viewPost();
				break;
			} else {
				System.out
						.println("Invalid username or password. Please try again bruh!!\nPress any key to continue...");
				scanner.nextLine();
				Views.mainWindow();
				break;
			}
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
				break;
			case 'l':
				currentAccount = null;
				Views.mainWindow();
				break;
			case 'q':
				isRunning = false;
				break;
			default:
				System.out.println("Invalid input. Please enter '+', 'L', or 'Q'");
			}
			if (isRunning == false || currentAccount == null) {
				break;
			}
		}

	}

	private static void newPost() {
		Views.newPostWindow(currentAccount);
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
			case 't':
				postText();
				break;
			case 'a':
				postTextArt();
				break;
			default:
				System.out.println("Invalid input. Please enter 'T' or 'A'");
			}

		}
	}

	private static void postTextArt() {
		Views.displayTextArtOptions(currentAccount);
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
			case '0':
				
				break;
			case '1':

				break;
			case '2':

				break;
			case '3':

				break;
			default:
				System.out.println("Invalid input. Please enter a number");
			}
		}
	}

	private static void postText() {
		// TODO Auto-generated method stub

	}
}
