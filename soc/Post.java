package unl.soc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Post implements ContentFormatter {
    protected LocalDateTime postTime;
    protected Account postAccount;
    
    Post(Account postAccount){
    	this.postAccount = postAccount;
    	this.postTime = LocalDateTime.now();
    }

    public String tag() {
        String s = postTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
        return s + " by " + postAccount.getUsername();
    }
}
