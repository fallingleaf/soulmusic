<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script src="<c:url value='/resources/js/jquery-1.11.3.min.js' />" ></script>
		<link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
		<script src="<c:url value='/resources/js/bootstrap.min.js' />" ></script>
		<link href="<c:url value='/resources/css/font-awesome.min.css' />" rel="stylesheet">
		<link href="<c:url value='/resources/css/style.css' />" rel="stylesheet">
		
	</head>
    <body>
    	<div class="container">
  			<div class="pull-right"><a href="?language=en">English</a>|<a href="?language=vi_VN">Vietnamese</a></div>
    		<div class="row">
    			<div class="col-md-6 col-sm-8 col-md-offset-3 col-sm-offset-2">
			    	<div class="message">
						<c:if test="${not empty message}">
							<div class="alert alert-info">${message}</div>
						</c:if>
			    	</div>
			    	<div class="content">
				        <h1><spring:message code="msg.login"></spring:message></h1>
				        <c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
				        <form class="form" method="post" action="<c:url value='j_spring_security_check' />">
				        	<div class="form-group">
				            	<label><spring:message code="msg.username"/></label>
				            	<input class="form-control" name="username" value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
				            </div>
				            <div class="form-group">
				            	<label><spring:message code="msg.password"/></label>
				            	<input class="form-control" type="password" name='password' />
				            </div>
				            <spring:message code="msg.remember"/>: <input type="checkbox" name="_spring_security_remember_me" /> <br />
				            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
				            <input class="btn btn-primary" type="submit" value="<spring:message code="msg.submit"/>">
				            <a href="<c:url value='/register'/>"><spring:message code="msg.letregister"/></a>
			        	</form>
			        </div>
			     </div>
			  </div>
	     </div>
    </body>
</html>
