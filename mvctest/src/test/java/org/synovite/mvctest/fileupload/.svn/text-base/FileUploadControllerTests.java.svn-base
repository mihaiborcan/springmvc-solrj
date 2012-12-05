package org.synovite.mvctest.fileupload;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadControllerTests {

    private FileUploadController fileUploadController;
    private FileUploadManager fileUploadManager;
    private List<MultipartFile> fileList = new ArrayList<MultipartFile>();
    private List<MultipartFile> emptyFileList = new ArrayList<MultipartFile>();
    
    private List<MultipartFile> wrongFileList = new ArrayList<MultipartFile>();

    @Before
    public void setUp() throws Exception {

	fileUploadManager = new MockFileUploadManager();
	File testFile = new File("src/test/resources/email1.eml");
	File testFile2 = new File("src/test/resources/email2.eml");
	File testFile3 = new File("src/test/resources/log4j.xml");
	
	MultipartFile multiFile = new MockMultipartFile(testFile.getName(), testFile.getName(),
		"message/rfc822", new FileInputStream(testFile));
	MultipartFile multiFile2 = new MockMultipartFile("email2.eml", testFile2.getName(),
		"message/rfc822", new FileInputStream(testFile2));
	fileList.add(multiFile);
	fileList.add(multiFile2);
	
	MultipartFile emptyMultiFile = new MockMultipartFile("empty.eml", new byte[0]);
	emptyFileList.add(emptyMultiFile);
	
	MultipartFile wrongMultiFile = new MockMultipartFile(testFile3.getName(), testFile3.getName(),
		"text/xml", new FileInputStream(testFile3));
	wrongFileList.add(wrongMultiFile);
	
	fileUploadController = new FileUploadController(fileUploadManager);
    }

    @Test
    public void testEmptyUpload() {
	ExtendedModelMap model = new ExtendedModelMap();
	fileUploadController.processUpload(emptyFileList, model);
	assertTrue(model.get("error").equals("No files selected"));
    }
    
    @Test
    public void testWrongFileUpload() {
	ExtendedModelMap model = new ExtendedModelMap();
	fileUploadController.processUpload(wrongFileList, model);
	assertTrue(model.get("error").equals("Files are not the right type"));
    }

    @Test
    public void testProcessUpload() {
	List<String> fileNames = new ArrayList<String>();
	ExtendedModelMap model = new ExtendedModelMap();
	fileUploadController.processUpload(fileList, model);
	System.out.println(model);
	assertTrue(model.containsAttribute("message"));

	for (MultipartFile file : fileList) {
	    fileNames.add(file.getOriginalFilename());
	}
	assertTrue(model.get("fileNames").equals(fileNames));
    }

}
