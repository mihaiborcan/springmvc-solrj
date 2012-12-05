package org.synovite.mvctest.model;

import java.util.Date;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.synovite.mvctest.utils.Constants;

/**
 * POJO representing an Email object, with just its basic attributes (no binary content, attachments or HTML)
 * 
 * @author Mihai
 * 
 */
public class Email {
    @Field(value = Constants.ID_FIELD)
    private String id;
    @Field(value = Constants.SENDER_FIELD)
    private String sender;
    @Field(value = Constants.RECIPIENTS_FIELD)
    private List<String> recipients;
    @Field(value = Constants.DATE_FIELD)
    @DateTimeFormat(iso = ISO.DATE)
    private Date date;
    @Field(value = Constants.SUBJECT_FIELD)
    private String subject;
    @Field(value = Constants.CONTENT_FIELD)
    private String textContent;

    /**
     * The fileType field identifies the object as an email when indexed on the server
     */
    @Field(value = Constants.FILE_TYPE)
    private String fileType;

    /**
     * Constructor calculates a hashCode from four fields: messageId, sender, subject and date, and assigns it as an unique id for the object
     * 
     * @param id
     * @param sender
     * @param recipients
     * @param date
     * @param subject
     * @param textContent
     */
    public Email(String id, String sender, List<String> recipients, Date date, String subject,
	    String textContent) {
	this.id = id;
	this.sender = sender;
	this.recipients = recipients;
	this.date = date;
	this.subject = subject;
	this.textContent = textContent;
	this.id = String.valueOf(hashCode());
	this.fileType = Constants.EMAIL_TYPE;
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

    public List<String> getRecipients() {
	return recipients;
    }

    public void setRecipients(List<String> recipients) {
	this.recipients = recipients;
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

    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((sender == null) ? 0 : sender.hashCode());
	result = prime * result + ((subject == null) ? 0 : subject.hashCode());
	result = prime * result + ((date == null) ? 0 : date.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Email))
	    return false;
	Email other = (Email) obj;

	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	StringBuilder email = new StringBuilder();
	email.append("ID: " + this.getId());
	email.append("\nFrom : " + this.getSender());
	email.append("\nDate : " + this.getDate());
	email.append("\nTo : " + this.getRecipients());
	email.append("\nSubject : " + this.getSubject());
	email.append("\n----------------------------------");
	email.append("\n" + this.getTextContent());
	email.append("\n\n");

	return email.toString();
    }

}
