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
			        <div class="content">
			        	<h1>Sign Up</h1>
			        	<c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
				        <form:form commandName="user" cssClass="form" method="post" action="register">
				        	<div class="form-group">
				            	<label>Username</label>
				            	<form:input cssClass="form-control" path="username"/>
				            	<form:errors cssClass="error" path="username"/>
				            </div>
				            <div class="form-group">
				            	<label>Password </label>
				            	<form:password cssClass="form-control" path="password"/>
				            	<form:errors cssClass="error" path="password"/>
				            </div>
				            <div class="form-group">
				            	<label>Repeat Password </label>
				            	<form:password cssClass="form-control" path="password_repeat"/>
				            	<form:errors cssClass="error" path="password_repeat"/>
				            </div>
				            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
				            <input class="btn btn-primary" type="submit" value="Register" />
			        	</form:form>
			        </div>
			    </div>
			</div>
		</div>
    </body>
</html>
