package unl.soc;

import java.util.*;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	private static Map<String, Account> accounts = new HashMap<>();
	private static Account currentAccount = null;
	private static List<Post> postList = new ArrayList<>();

	public static void main(String[] args) {

		String input = "";

		boolean isRunning = true;
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

	public static void register() {
		Views.accountRegistrationWindow();
		System.out.print("Enter UserName: ");
		String userName = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		System.out.print("Enter phoneNumber: ");
		String phoneNumber = scanner.nextLine();

		if (accounts.containsKey(userName) == true) {
			System.out.println("The username "+ userName +" is already taken!! Press any key to return!!");
		} else {
			accounts.put(userName, new Account(userName, password, phoneNumber));
			System.out.println("Account created successfully!! Press any key to return!!");
		}
		scanner.nextLine();
		Views.mainWindow();
	}

	public static void login() {
		Views.accountLoginWindow();
		while (true) {
			System.out.print("Enter UserName: ");
			String userName = scanner.nextLine();
			System.out.print("Enter password: ");
			String password = scanner.nextLine();

			if (accounts.containsKey(userName) == true && accounts.get(userName).getUserPassword().equals(password)) {
				System.out.println("Account login successfully!! Press any key to continue!!");
				scanner.nextLine();
				viewPost();
				break;
			} else {
				System.out.println("Invalid username or password. Please try again bruh!! Press any key to continue!!");
				scanner.nextLine();
				Views.mainWindow();
				break;
			}
		}

	}

	public static void viewPost() {
		Views.postViewWindow(postList, currentAccount);
	}
}
