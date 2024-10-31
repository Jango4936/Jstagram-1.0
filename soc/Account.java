package unl.soc;

public class Account implements ContentFormatter {
	private final String userName;
	private final String userPassword;
	private final String userPhoneNum;
	private final String userEmail;

	Account(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhoneNum = null;
		this.userEmail = null;

	}

	// method overloading
	Account(String userName, String userPassword, String userPhoneNum, String userEmail) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhoneNum = userPhoneNum;
		this.userEmail = userEmail;

	}
	
	// some getter methods
	
	public String getUsername() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getUserPhoneNum() {
		return userPhoneNum;
	}

	public String getUserEmail() {
		return userEmail;
	}

	@Override
	public String getFormattedContent() {
		return String.format("|  Current user : %-23s|\n", userName);
	}
}