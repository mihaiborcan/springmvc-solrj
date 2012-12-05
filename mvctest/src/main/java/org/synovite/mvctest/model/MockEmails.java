package org.synovite.mvctest.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Not used
 * 
 * @author Mihai
 *
 */
public class MockEmails {
    
    public static Email getMockEmail(){
	String id = "<54718DFF0B21FB47BD155F7366D32FD711B2F01C4E@drl-ex-01>";
	String sender = "Example User <example@synovite.nl>";
	List<String> recipients = new ArrayList<String>();
	recipients.add("Tomas Salfischberger <t.salfischberger@synovite.nl>");
	recipients.add("Tomas Salfischberger <t.salfischberger@synovite.nl>");
	Date date = new Date();
	String subject = "Example message";
	String textContent = "Hi,\n"+
		"This is an example message for the search-engine assignment.\n"+
		"Best regards,\n"+
		"Example User";
	Email email = new Email(id, sender, recipients, date, subject, textContent);
	return email;
    }
    
    public static List<Email> getMockEmails(){
	List<Email> emails = new ArrayList<Email>();
	List<String> recipients = new ArrayList<String>();
	recipients.add("Tomas Salfischberger <t.salfischberger@synovite.nl>");
	String content = "Hi,"+
			"This is an example message for the search-engine assignment."+
			"Best regards,"+
			"Example User";
	emails.add(new Email("email", "<example@synovite.nl>", recipients, null, "Example message", content));
	
	return emails;
    }
    


}
