<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ page session="true"%>
<c:if test="${!ajaxRequest}">
	<html>
<head>
<title>Search emails</title>
<link href="<c:url value="/resources/form.css" />" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
</head>
<body>
</c:if>
<div id="formsContent">
	<h2>Search form</h2>
	<form:form id="form" method="post" modelAttribute="searchBean"
		cssClass="cleanform">
		<div class="header">
			<c:if test="${not empty message}">
				<div id="message" class="info">${message}</div>
			</c:if>
			<s:bind path="*">
				<c:if test="${not empty error}">
					<div id="message" class="error">Server could not be contacted</div>
				</c:if>
			</s:bind>
		</div>
		<fieldset>
			<legend>Search fields</legend>
			<form:label path="sender">
		  			Sender 
		 		</form:label>
			<form:input path="sender" />

			<form:label path="date">
		  			After date (in form yyyy-mm-dd) <form:errors path="date"
					cssClass="error" />
			</form:label>
			<form:input path="date" />

			<form:label path="recipient">
		  			Recipient 
		 		</form:label>
			<form:input path="recipient" />
			<form:label path="subject">
		  			Subject 
		 		</form:label>
			<form:input path="subject" />
			<form:label path="textContent">
		  			Body 
		 		</form:label>
			<form:textarea path="textContent" />
		</fieldset>

		<p>
			<button type="submit">Submit</button>
		</p>
	</form:form>
	<script type="text/javascript">
		$(document).ready(
				function() {
					$("#form").submit(
							function() {
								$.post($(this).attr("action"), $(this)
										.serialize(), function(html) {
									$("#formsContent").replaceWith(html);
									$('html, body');
								});
								return false;
							});
				});
	</script>

	<div id="resultContent">
		<div class="header">

			<c:if test="${not empty results}">
		We got ${fn:length(results) } results
			<c:forEach var="result" items="${results}">
					<div id="results" class="result">
						From: ${fn:escapeXml(result.sender)} <br /> Date: ${result.date }<br />
						To: ${fn:escapeXml(result.recipient) }<br /> Subject:
						${result.subject }<br /> <br /> ${result.textContent }
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
</div>



<c:if test="${!ajaxRequest}">
	</body>
	</html>
</c:if>