package org.synovite.mvctest.fileupload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.synovite.mvctest.filehandler.FileHandler;
import org.synovite.mvctest.model.Email;
import org.synovite.mvctest.solr.SolrDataDAO;

/**
 * Concrete implementation of FileUploadManager interface. Sends a list of Email beans to the Solr server for indexing.
 * 
 * @author Mihai
 *
 */
@Component
public class SimpleFileUploadManager implements FileUploadManager {

    @Autowired
    FileHandler<Email> emailHandler;

    @Autowired
    SolrDataDAO solrDataDAO;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synovite.mvctest.fileupload.FileUploadManager#indexFiles(java.util
     * .List)
     */
    @Override
    public boolean indexFiles(List<MultipartFile> files) {
	List<Email> emails = emailHandler.getDocumentsFromMultipart(files);
	System.out.println(emails.get(0).getTextContent());
	return solrDataDAO.addEmails(emails);
    }

}
