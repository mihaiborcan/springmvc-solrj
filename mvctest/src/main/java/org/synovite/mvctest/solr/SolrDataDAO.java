package org.synovite.mvctest.solr;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.synovite.mvctest.model.Email;
import org.synovite.mvctest.model.SearchBean;
import org.synovite.mvctest.utils.Constants;

/**
 * This class is used to add, delete and query data from Solr
 * 
 * @author Mihai
 * 
 */
@Component
public class SolrDataDAO extends SolrBaseDAO {

    private static SolrServer server = null;

    private static Logger logger = LoggerFactory.getLogger(SolrDataDAO.class);
    protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public SolrDataDAO() {
	if (server == null)
	    server = getHttpSolrConnection();
    }

    /**
     * Method that queries the server using the attributes bound to the searchBean object
     * 
     * @param searchBean
     * @return the list of objects that match the SearchBean
     */
    public List<SearchBean> queryBySearchBean(SearchBean searchBean) {
	ModifiableSolrParams solrParams = new ModifiableSolrParams();
	String query = getQueryFromSearchBean(searchBean);
	logger.info("Query is " + query);
	solrParams.set("q", query);
	solrParams.set("highlight", true);
	solrParams.set("highlightSnippets", 1);
	solrParams.set("start", 0);
	solrParams.set("rows", 20);
	QueryResponse rsp = null;
	try {
	    rsp = server.query(solrParams);
	} catch (SolrServerException e) {
	    logger.error("SolrServer exception: " + e.getMessage());
	    return null;
	}
	if(rsp==null)
	    return null;
	else
	    return extractSearchResults(rsp);
    }

    /**
     * Takes a searchBean object and constructs a solr-compliant query string by attaching the file type first 
     * 
     * @param searchBean
     * @return
     */
    public String getQueryFromSearchBean(SearchBean searchBean) {
	StringBuilder query = new StringBuilder();

	query.append(Constants.FILE_TYPE).append(":").append(searchBean.getFileType());

	if (!"".equals(searchBean.getSender()))
	    query.append(" AND ").append(Constants.SENDER_FIELD).append(":")
		    .append("\"" + searchBean.getSender() + "\"");

	if (searchBean.getDate() != null)
	    query.append(" AND ").append(Constants.DATE_FIELD).append(":")
		    .append("[" + sdf.format(searchBean.getDate()) + " TO NOW]");

	if (!"".equals(searchBean.getRecipient()))
	    query.append(" AND ").append(Constants.RECIPIENTS_FIELD).append(":")
		    .append("\"" + searchBean.getRecipient() + "\"");

	if (!"".equals(searchBean.getSubject()))
	    query.append(" AND ").append(Constants.SUBJECT_FIELD).append(":")
		    .append("\"" + searchBean.getSubject() + "\"");

	if (!"".equals(searchBean.getTextContent()))
	    query.append(" AND ").append(Constants.CONTENT_FIELD).append(":")
		    .append("\"" + searchBean.getTextContent() + "\"");
	return query.toString();
    }

    /**
     * Commit a list of Email beans (which have been annotated with dynamic field names)
     * 
     * @param emails
     * @return true if the commit succeeded, of false otherwise
      */
    public boolean addEmails(List<Email> emails) {
	try {
	    server.addBeans(emails);
	    server.commit();
	} catch (SolrServerException e) {
	    logger.error("Solr Server exception: " + e.getMessage());
	    return false;
	} catch (IOException e) {
	    logger.error("IO Exception encountered");
	    return false;
	}
	logger.info("Data committed Successfully!");
	return true;
    }

    /**
     * Extract objects from query response
     * 
     * @param response
     */
    public static List<SearchBean> extractSearchResults(QueryResponse response) {
	List<SearchBean> results = new ArrayList<SearchBean>();
	SolrDocumentList docs = response.getResults();
	if (docs != null) {
	    for (int i = 0; i < docs.size(); i++) {
		SolrDocument doc = docs.get(i);
		results.add(processSolrDocument(doc));
	    }
	}
	return results;
    }

    /**
     * Utility method for extracting a SearchBean object from a SolrDocument
     * 
     * @param doc
     * @return
     */
    public static SearchBean processSolrDocument(SolrDocument doc) {
	SearchBean responseBean = new SearchBean();
	Collection<String> fieldNames = doc.getFieldNames();

	for (String field : fieldNames) {

	    if (Constants.ID_FIELD.equals(field))
		responseBean.setId((String) doc.getFieldValue(field));

	    if (Constants.SENDER_FIELD.equals(field))
		responseBean.setSender((String) doc.getFieldValue(field));

	    if (Constants.RECIPIENTS_FIELD.equals(field))
		responseBean.setRecipient(((String) doc.getFieldValue(field)));

	    if (Constants.DATE_FIELD.equals(field))
		responseBean.setDate(((Date) doc.getFieldValue(field)));

	    if (Constants.SUBJECT_FIELD.equals(field))
		responseBean.setSubject((String) doc.getFieldValue(field));

	    if (Constants.CONTENT_FIELD.equals(field))
		responseBean.setTextContent((String) doc.getFieldValue(field));

	}
	return responseBean;
    }

    /**
     * Delete records from Solr by id
     * 
     * @param id
     * @throws Exception
     */
    public void deleteDataById(String id) throws Exception {
	server.deleteByQuery("id:" + id);
	server.commit();
	logger.info("Data deleted successfully!");
    }
}
