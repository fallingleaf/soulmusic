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
					<div class="album-info">
						<p>Album <c:out value="${album.getName() }"/></p>
						<img src="<c:url value='/resources/images/albumicon.jpeg'/>" alt="album-icon"/>
					</div>
					<hr/>
					<div class="music-container">
						<h2>Musics</h2>
						<c:forEach var="music" items="${musics}">
							<div class="music">
								<p><c:out value="${music.getTitle() }"/></p>
								<p class="control">
									<audio controls>
										<source src="<c:url value='/resources/upload/${music.getTitle() }'/>" type="audio/mp3">
										</source>
									</audio>
									<a role="button" href="<c:url value='/post/music/${music.getId()}' />" class="btn btn-warning btn-share">
										<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
									</a>
									<a role="button" href="<c:url value='/music/delete/${music.getId()}/?${_csrf.parameterName}=${_csrf.token}' />" class="btn btn-danger btn-del">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
									</a>
								</p>
							</div>
						</c:forEach>
					</div>
					<hr/>
					<div class="music-upload">
						<h2>Upload music to album</h2>
						<form class="form form-inline"  enctype="multipart/form-data" 
							action="<c:url value='/upload?${_csrf.parameterName}=${_csrf.token}'/>" method="post">
							<div class="form-group">
								<input type="hidden" name="id" value="${album.getId() }"/>
								<input class="form-control" type="file" name="file"/>
							</div>
							
							<input type="submit" class="btn btn-info" value="Upload"/>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			$(".btn-del").click(function(e) {
				e.preventDefault();
				var url = $(this).attr('href');
				var target = $(this).parent('div.music');
				$.post(url, {}, function(result) {
					console.log(result);
					if(result.success) {
						target.remove();
					}
				}, 'json');
			})
		})
	</script>
</html>
