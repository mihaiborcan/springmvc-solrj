package org.synovite.mvctest.solr;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.core.CoreContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Base class for connecting to Solr
 * 
 * @author Mihai
 * 
 */
public class SolrBaseDAO {
    private static Logger logger = LoggerFactory.getLogger(SolrBaseDAO.class);
    final static String serverURL = "http://localhost:8983/solr";
    final static String solrHome = "c:/apache-solr-4.0.0/example/solr";

    /**
     * 
     * Creates SOLR connection from Web deployed Solr using HttpClient
     * internally
     * 
     * @return will return the SolrServer connection instance.
     */
    public HttpSolrServer getHttpSolrConnection() {
	HttpSolrServer solrServer = null;
	try {
	    // configure a server object with actual solr values.
	    solrServer = new HttpSolrServer(serverURL);
	    solrServer.setParser(new XMLResponseParser());
	    solrServer.setSoTimeout(5000);
	    solrServer.setConnectionTimeout(5000);
	    // Other commonly used properties
	    solrServer.setDefaultMaxConnectionsPerHost(100);
	    solrServer.setMaxTotalConnections(100);
	    solrServer.setMaxRetries(1); // defaults to 0. > 1 not recommended.
	} catch (Exception exc) {
	    logger.error(" Exception in getting Solr Connection: " + exc.getMessage());
	    exc.printStackTrace();
	}
	return solrServer;
    }

    /**
     * Creates Solr connection without requiring an HTTP connection. <b>Method doesn't work.</b>
     * 
     * @return EmbeddedSolrServer
     */
    public EmbeddedSolrServer getEmbeddedSolrConnection() {
	EmbeddedSolrServer server = null;
	try {
	    if (server == null) {
		// If not already set in Tomcat Catalina.sh or eclipse JVM
		// arguments, explicitly set this property
		System.setProperty("solr.solr.home", solrHome);
		CoreContainer.Initializer initializer = new CoreContainer.Initializer();
		CoreContainer coreContainer = initializer.initialize();
		server = new EmbeddedSolrServer(coreContainer, "");
	    }
	} catch (Exception ex) {
	    logger.error(" Exception in getting EmbeddedSolrServer Connection: " + ex.getMessage());
	    ex.printStackTrace();
	}
	return server;
    }

    /**
     * Creates Embedded Solr connection with MultiCore capabilities. <b>Method doesn't work.</b>
     * 
     * @return EmbeddedSolrServer
     */
    @SuppressWarnings("deprecation")
    public EmbeddedSolrServer getEmbeddedSolrConnectionMultiCore() {
	EmbeddedSolrServer server = null;
	File home = new File(solrHome);
	File f = new File(home, "solr.xml");
	CoreContainer container = new CoreContainer();
	try {
	    container.load(solrHome, f);
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SAXException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	server = new EmbeddedSolrServer(container, "collection1");
	return server;
    }
}
