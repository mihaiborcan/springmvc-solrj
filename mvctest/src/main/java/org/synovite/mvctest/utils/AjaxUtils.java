package org.synovite.mvctest.utils;

import org.springframework.web.context.request.WebRequest;

/**
 * @author Mihai
 *
 */
public class AjaxUtils {

	public static boolean isAjaxRequest(WebRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}

	public static boolean isAjaxUploadRequest(WebRequest webRequest) {
		return webRequest.getParameter("ajaxUpload") != null;
	}
	
	private AjaxUtils() {}

}
