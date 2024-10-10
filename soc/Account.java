package unl.soc;

public class Account implements ContentFormatter{
    private final String userName;
    private final String userPassword;
    private final String userPhoneNum;
    
    Account(String userName, String userPassword){
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhoneNum = null;
    	
    }
    
    Account(String userName, String userPassword, String userPhoneNum){
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhoneNum = userPhoneNum;
    	
    }
    
    public String getUsername() {
        return userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public String getUserPhoneNum() {
        return userPhoneNum;
    }
    

    @Override
    public String getFormattedContent() {
        return String.format("|  Current user : %-23s|\n", userName);
    }
}
