package org.synovite.mvctest.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.synovite.mvctest.model.SearchBean;
import org.synovite.mvctest.solr.SolrDataDAO;

/**
 * Concrete implementation of SearchManager interface
 * that takes a SolrDataDAO object as autowired bean 
 * to communicate with a server
 * 
 * @author Mihai
 *
 */
@Component
public class SimpleSearchManager implements SearchManager {

    @Autowired
    SolrDataDAO solrDataDAO;

    /* (non-Javadoc)
     * @see org.synovite.mvctest.search.SearchManager#searchForBean(org.synovite.mvctest.search.SearchBean)
     */
    @Override
    public List<SearchBean> searchForBean(SearchBean searchBean){
	return solrDataDAO.queryBySearchBean(searchBean);
    }
    
    /* (non-Javadoc)
     * @see org.synovite.mvctest.search.SearchManager#getQuery(org.synovite.mvctest.search.SearchBean)
     */
    @Override
    public String getQuery(SearchBean searchBean){
	return solrDataDAO.getQueryFromSearchBean(searchBean);
    }

    @Override
    public SearchBean trimSearch(SearchBean searchBean) {
	searchBean.setSender(searchBean.getSender().trim());
	searchBean.setRecipient(searchBean.getRecipient().trim());
	searchBean.setSubject(searchBean.getSubject().trim());
	searchBean.setTextContent(searchBean.getTextContent().trim());
	return searchBean;
    }

    public SolrDataDAO getSolrDataDAO() {
        return solrDataDAO;
    }

    public void setSolrDataDAO(SolrDataDAO solrDataDAO) {
        this.solrDataDAO = solrDataDAO;
    }
    
    
}
