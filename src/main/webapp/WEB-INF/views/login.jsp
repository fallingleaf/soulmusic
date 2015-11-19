<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
    		<div class="row">
    			<div class="col-md-6 col-sm-8 col-md-offset-3 col-sm-offset-2">
			    	<div class="message">
						<c:if test="${not empty message}">
							<div class="alert alert-info">${message}</div>
						</c:if>
			    	</div>
			    	<div class="content">
				        <h1>Login Page</h1>
				        <c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
				        <form class="form" method="post" action="<c:url value='j_spring_security_check' />">
				        	<div class="form-group">
				            	<label>Username</label>
				            	<input class="form-control" name="username" value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
				            </div>
				            <div class="form-group">
				            	<label>Password </label>
				            	<input class="form-control" type="password" name='password' />
				            </div>
				            Remember me: <input type="checkbox" name="_spring_security_remember_me" /> <br />
				            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
				            <input class="btn btn-primary" type="submit" />
				            <a href="<c:url value='/register'/>">Do not have account ? Register!</a>
			        	</form>
			        </div>
			     </div>
			  </div>
	     </div>
    </body>
</html>
