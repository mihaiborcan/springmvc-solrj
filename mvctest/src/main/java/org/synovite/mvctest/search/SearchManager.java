package org.synovite.mvctest.search;

import java.util.List;

import org.synovite.mvctest.model.SearchBean;

/**
 * Generic interface for getting query strings 
 * and retrieving objects from the server
 * 
 * @author Mihai
 *
 */
public interface SearchManager {

    public List<SearchBean> searchForBean(SearchBean searchBean);

    public String getQuery(SearchBean searchBean);
    
    public SearchBean trimSearch(SearchBean searchBean);

}