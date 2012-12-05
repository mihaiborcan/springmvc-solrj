package org.synovite.mvctest.filehandler;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


/**
 * Generic interface for retrieving a specific POJO from a MultipartFile or File object or entire lists
 * 
 * @author Mihai
 *
 * @param <T>
 */
public interface FileHandler<T> {
    
    /**
     * @param multipartFiles
     * @return list of <T>
     */
    public List<T> getDocumentsFromMultipart(List<MultipartFile> multipartFiles);
    
    /**
     * @param multipartFile
     * @return <T>
     */
    public T getDocumentFromMultipart(MultipartFile multipartFile);
    
    /**
     * @param files
     * @return list of <T>
     */
    public List<T> getDocumentsFromFile(List<File> files);
    

    /**
     * @param file
     * @return <T>
     */
    public T getDocumentFromFile(File file);

}
