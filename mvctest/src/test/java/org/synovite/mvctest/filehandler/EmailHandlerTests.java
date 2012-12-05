package org.synovite.mvctest.filehandler;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.synovite.mvctest.model.Email;

public class EmailHandlerTests {

    private EmailHandler emailHandler;
    private List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
    private List<File> files = new ArrayList<File>();

    @Before
    public void setUp() throws Exception {
	emailHandler = new EmailHandler();
	File testFile = new File("src/test/resources/email1.eml");
	File testFile2 = new File("src/test/resources/email2.eml");
	File testFile3 = new File("src/test/resources/log4j.xml");
	files.add(testFile);
	files.add(testFile2);
	files.add(testFile3);
	
	MultipartFile multiFile = new MockMultipartFile(testFile.getName(), new FileInputStream(
		testFile));
	MultipartFile multiFile2 = new MockMultipartFile(testFile2.getName(), new FileInputStream(
		testFile2));
	MultipartFile wrongMultiFile = new MockMultipartFile(testFile3.getName(),
		testFile3.getName(), "text/xml", new FileInputStream(testFile3));

	multipartFiles.add(multiFile);
	multipartFiles.add(multiFile2);
	multipartFiles.add(wrongMultiFile);
    }

    @Test(expected = NullPointerException.class)
    public void testWrongDocument() {
	emailHandler.getDocumentFromMultipart(multipartFiles.get(2));
    }

    @Test
    public void getDocumentFromMultipart() {
	Email email = emailHandler.getDocumentFromMultipart(multipartFiles.get(0));
	System.out.println(email);
	assertTrue("242716674".equals(email.getId()));
    }
    
    @Test(expected = NullPointerException.class)
    public void testWrongDocumentFromFile() {
	emailHandler.getDocumentFromFile(files.get(2));
    }

    @Test
    public void getDocumentFromFile() {
	Email email = emailHandler.getDocumentFromFile(files.get(0));
	assertTrue("242716674".equals(email.getId()));
    }

}
