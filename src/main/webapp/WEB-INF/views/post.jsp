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
        <nav class="navbar navbar-inverse navbar-fixed-top">
          <div class="container-fluid">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="<c:url value='/' />">Melody</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav navbar-right">
                <li><a href="<c:url value='/profile' />">Dashboard</a></li>
                <li><a href="#">Settings</a></li>
                <li>
         
                	<c:url var="logoutUrl" value="/j_spring_security_logout"/>
					<form id="logout" action="${logoutUrl}" method="post">
					  <input type="submit" class="" style="display:none;" value="Log out" />
					  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
					<a href="#" onclick="document.getElementById('logout').submit()">Logout</a>
                </li>
              </ul>
              <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
              </form>
            </div>
          </div>
        </nav>
		<div class="container-fluid main-screen">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
		          <ul class="nav nav-sidebar">
		            <li class="active"><a href="<c:url value='/profile'/>">Album<span class="sr-only">(current)</span></a></li>
		            <li><a href="<c:url value='/hotmusic'/>">Timeline</a></li>
		          </ul>
		          
		        </div>
		        <div class="col-sm-9 col-md-10 col-sm-offset-3 col-md-offset-2 main">
		        	<div class="message">
						<c:if test="${not empty message}">
							<div class="alert alert-info">${message}</div>
						</c:if>
			    	</div>
					<div class="post-form">
						<h3>Share your music</h3>
						<div class="row">
							<div class="col-md-6 col-sm-8">
								<c:url var="sharePost" value="/post/music/${music.getId()}"/>
								<form:form commandName="post" cssClass="form" action="${sharePost}" method="post">
									<div class="music-group">
										<p>
											<img src="<c:url value='/resources/images/musicplayer.png' />" alt="music icon"/>
											<span class="title"><c:out value="${music.getTitle()}"/></span>
										</p>
									</div>
									<div class="clearfix"></div>
									<div class="form-group">
										<label>Note</label>
										<form:textarea rows="5" cols="30" cssClass="form-control" path="note"/>
										<form:errors path="note" cssClass="error" />
									</div>
									<div class="form-group radio-group">
										<form:radiobutton path="status" value="public" label="Public"/>
										<form:radiobutton path="status" value="friend" label="Only Friends"/>
									</div>
					            	<input class="btn btn-info" type="submit" value="Share" />
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>