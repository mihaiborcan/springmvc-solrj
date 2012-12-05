package org.synovite.mvctest.fileupload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MockFileUploadManager implements FileUploadManager {

    @Override
    public boolean indexFiles(List<MultipartFile> files) {
	// TODO Auto-generated method stub
	return true;
    }

}
