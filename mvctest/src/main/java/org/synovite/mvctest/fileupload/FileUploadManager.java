package org.synovite.mvctest.fileupload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface to handle upload of a list of files
 * 
 * @author Mihai
 *
 */
public interface FileUploadManager {

    /**
     * Takes a list of uploaded MultipartFiles and passes them to the server for upload/index.
     * 
     * @param files
     * @return
     */
    public abstract boolean indexFiles(List<MultipartFile> files);

}