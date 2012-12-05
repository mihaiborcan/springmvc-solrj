package org.synovite.mvctest.search;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.synovite.mvctest.model.SearchBean;
import org.synovite.mvctest.utils.AjaxUtils;

/**
 * Controller that handles the search form and results
 * 
 * @author Mihai
 *
 */
@Controller
@RequestMapping("/search")
@SessionAttributes("searchBean")
public class SearchController {

    private static Logger logger = LoggerFactory.getLogger(SearchController.class);
    SearchManager searchManager;

    @Autowired
    public SearchController(SearchManager searchManager){
	this.searchManager = searchManager;
    }
    
    @ModelAttribute
    public void ajaxAttribute(WebRequest request, Model model) {
	model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
    }

    @ModelAttribute("searchBean")
    public SearchBean createSearchBean() {
	return new SearchBean();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public void searchForm() {
    }

    /**
     * Method that processes the submit takes a SearchBean object and a BindingResult
     * and performs validation (in this case, just the Date field). 
     * 
     * Retrieves submitted query from the form (for debugging)
     * 
     * Gets result objects from the server
     * 
     * @param searchBean
     * @param bindingResult
     * @param model
     * @param ajaxRequest
     * @param redirectAttrs
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@Valid SearchBean searchBean, BindingResult bindingResult, Model model,
	    @ModelAttribute("ajaxRequest") boolean ajaxRequest, RedirectAttributes redirectAttrs) {
	
	if (bindingResult.hasErrors()) {
	    return null;
	}
	
	String message = "Submitted query is: <br/>" + searchManager.getQuery(searchManager.trimSearch(searchBean));
	List<SearchBean> resultBeans = searchManager.searchForBean(searchManager.trimSearch(searchBean));
	if(resultBeans == null){
	    logger.error("Server could not be contacted");
	    model.addAttribute("error", "Server returned an error");
	    return null;
	}
	model.addAttribute("searchBean", new SearchBean());
	// Success response handling
	if (ajaxRequest) {
	    // prepare model for rendering success message in this request
	    model.addAttribute("message", message);
	    model.addAttribute("results", resultBeans);
	    return null;
	} else {
	    // store a success message for rendering on the next request after redirect
	    // redirect back to the form to render the success message along with newly bound values
	    redirectAttrs.addFlashAttribute("results", resultBeans);
	    redirectAttrs.addFlashAttribute("message", message);
	    return "redirect:/search";
	}
    }

}
