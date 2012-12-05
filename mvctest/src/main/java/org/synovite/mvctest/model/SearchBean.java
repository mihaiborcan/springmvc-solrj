package org.synovite.mvctest.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.synovite.mvctest.utils.Constants;

/**
 * Bean object attached to the Email search form. It always assigns fileType=email so the form submission will retrieve the right type of files
 * 
 * @author Mihai
 *
 */
public class SearchBean {
    
    private String id;
    private String sender;
    private String recipient;
    @DateTimeFormat(iso=ISO.DATE)
    @Past
    private Date date;
    private String subject;
    private String textContent;
    
    @SuppressWarnings("unused")
    private String fileType;
    
    private static SimpleDateFormat sdf = new SimpleDateFormat( "dd MMM YYYY HH:mm" );
    
    public SearchBean() {
	this.fileType = Constants.EMAIL_TYPE;
	this.sender="";
	this.recipient="";
	this.subject="";
	this.textContent="";
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getTextContent() {
        return textContent;
    }
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
    
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
	StringBuilder email = new StringBuilder();
	email.append("From : " + this.getSender()).append("\n");
	email.append("Date : " + this.getDate()).append("\n");
	email.append("To : " + this.getRecipient()).append("\n");
	email.append("Subject : " + this.getSubject()).append("\n");
	email.append("Content" + this.getTextContent()).append("\n");

	return email.toString();
    }
    
    public String toHTMLString() {
	StringBuilder email = new StringBuilder();
	
	email.append("From : " + this.getSender()).append("<br/>");
	email.append("Date : " + sdf.format(this.getDate())).append("<br/>");
	email.append("To : " + this.getRecipient()).append("<br/>");
	email.append("Subject : " + this.getSubject()).append("<br/><br/>");
	email.append(this.getTextContent()).append("<br/>");

	return email.toString();
    }
}
