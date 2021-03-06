package org.synovite.mvctest.filehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.synovite.mvctest.model.Email;

/**
 * Concrete implementation of FileHandler interface that retrieves Email POJO's from MultipartFile objects.
 * 
 * @author Mihai
 *
 */
@Component
public class EmailHandler implements FileHandler<Email> {

    private Session mailSession;
    private static final Logger logger = LoggerFactory.getLogger(EmailHandler.class);

    public EmailHandler() {
	mailSession = Session.getDefaultInstance(System.getProperties(), null);
    }

    @Override
    public List<Email> getDocumentsFromMultipart(List<MultipartFile> multipartFiles) {
	List<Email> emails = new ArrayList<Email>();
	for (MultipartFile multipartFile : multipartFiles) {
	    emails.add(getDocumentFromMultipart(multipartFile));
	}
	return emails;
    }

    @Override
    public Email getDocumentFromMultipart(MultipartFile multipartFile) {
	InputStream source = null;
	Email email = null;
	try {
	    source = multipartFile.getInputStream();
	    MimeMessage message = new MimeMessage(mailSession, source);
	    String id = message.getMessageID();
	    String subject = message.getSubject();
	    String sender = message.getFrom()[0].toString();
	    Date sentDate = message.getSentDate();
	    Address[] recipients = message.getRecipients(Message.RecipientType.TO);
	    List<String> toAddresses = new ArrayList<String>();
	    for (Address address : recipients) {
		toAddresses.add(address.toString());
	    }
	    String textContent = message.getContent().toString();

	    email = new Email(id, sender, toAddresses, sentDate, subject, textContent);
	} catch (MessagingException e) {
	    logger.error("Error converting message");
	} catch (IOException e) {
	    logger.error("IO Exception");
	} finally {
	    try {
		source.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	logger.debug("Email object created from multipart file");
	return email;
    }

    @Override
    public List<Email> getDocumentsFromFile(List<File> files) {
	List<Email> emails = new ArrayList<Email>();
	for (File file : files) {
	    emails.add(getDocumentFromFile(file));
	}
	return emails;
    }

    @Override
    public Email getDocumentFromFile(File file) {
	Email email = null;
	InputStream source = null;
	try {
	    source = new FileInputStream(file);
	    MimeMessage message = new MimeMessage(mailSession, new FileInputStream(file));
	    String id = message.getMessageID();
	    String subject = message.getSubject();
	    String sender = message.getFrom()[0].toString();
	    Date sentDate = message.getSentDate();
	    Address[] recipients = message.getRecipients(Message.RecipientType.TO);
	    List<String> toAddresses = new ArrayList<String>();
	    for (Address address : recipients) {
		toAddresses.add(address.toString());
	    }
	    String textContent = message.getContent().toString();

	    email = new Email(id, sender, toAddresses, sentDate, subject, textContent);
	} catch (MessagingException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		source.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	logger.debug("Email object created from file");
	return email;
    }

}
