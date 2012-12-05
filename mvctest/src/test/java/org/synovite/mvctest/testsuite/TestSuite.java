package org.synovite.mvctest.testsuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.synovite.mvctest.filehandler.EmailHandlerTests;
import org.synovite.mvctest.fileupload.FileUploadControllerTests;
import org.synovite.mvctest.search.SearchControllerTests;
import org.synovite.mvctest.solr.SolrDataDAO;
import org.synovite.mvctest.solr.SolrDataDAOTests;



@RunWith(Suite.class)
@Suite.SuiteClasses({
  EmailHandlerTests.class,
  FileUploadControllerTests.class,
  SearchControllerTests.class,
  SolrDataDAOTests.class
})

public class TestSuite {
    // the class remains empty, 
    // used only as a holder for the above annotations
}