package org.synovite.mvctest.utils;

/**
 * Holds names of dynamic attributes user for indexing in the Solr server
 * 
 * @author Mihai
 *
 */
public class Constants {

    public static final String ID_FIELD = "id";
    public static final String SENDER_FIELD = "sender_t";
    public static final String RECIPIENTS_FIELD = "recipients_txt";
    public static final String DATE_FIELD = "date_dt";
    public static final String SUBJECT_FIELD = "subject_t";
    public static final String CONTENT_FIELD = "content_t";

    public static final String FILE_TYPE = "type_t";
    public static final String EMAIL_TYPE = "email";
    public static final String EMAIL_CONTENT_TYPE = "message/rfc822";
}
