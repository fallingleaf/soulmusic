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
		            <li ><a href="<c:url value='/profile'/>">Album<span class="sr-only">(current)</span></a></li>
		            <li class="active"><a href="<c:url value='/hotmusic'/>">Timeline</a></li>
		          </ul>
		          
		        </div>
		        <div class="col-sm-9 col-md-10 col-sm-offset-3 col-md-offset-2 main">
		        	<div class="message">
						<c:if test="${not empty message}">
							<div class="alert alert-info">${message}</div>
						</c:if>
			    	</div>
					<div class="post-container">
						<div class="row">
							<div class="col-md-offset-2 col-md-8 col-sm-10 col-sm-offset-1">
								<c:forEach var="post" items="${posts}">
									<div class="post">
										<div class="info">
											<p class="user">
												<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
												<c:out value="${post.getUser().getUsername()}"/>
											</p>
											<div class="music-group">
												<a href="#<c:url value='${post.getId()}'/>">
													<img src="<c:url value='/resources/images/musicplayer.png' />" alt="music cover"/>
												</a>
												<span class="title"><c:out value="${post.getMusic().getTitle()}"/></span>
												<p class="note"><c:out value="${post.getNote()}"/></p>
											</div>
										</div>
										<div class="clearfix"></div>
										<div class="player" id="${post.getId() }" style="display:none;">
											<audio controls>
												<source src="<c:url value='/resources/upload/${post.getMusic().getTitle() }'/>" type="audio/mp3">
												</source>
											</audio>
										</div>
										<c:set var="comments" value="${post.getComments() }"/>
										
										<a class="indicator down" href="#comment-${post.getId()}">Show all comments</a>
										
										<div class="comment" id="comment-${post.getId()}" style="display:none;">
											<c:forEach var="comment" items="${comments}">
												<p class="comment-text">
													<span class='user'>
														<img src="<c:url value='/resources/images/usericon.png'/>"/>
														<c:out value="${comment.getUser().getUsername()}"/>
													</span>
													<span class='note'></span><c:out value="${comment.getMsg()}"/></span>
												</p>
											</c:forEach>
											<c:url var="commentUrl" value="/addComment/${post.getId()}/?${_csrf.parameterName}=${_csrf.token}"/>
											
											<form class="comment-form" method="post" action="${commentUrl}">
												<p>Add new Comment</p>
												<input class="comment-box" name="note" />
											</form>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			<c:url var="imgUrl" value="/resources/images/usericon.png"/>
			$(function(){
				$(".indicator").click(function(e) {
					
					e.preventDefault();
					var target = $(this).attr('href');
					$(target).toggle();
					var link = $(this);
					if(link.hasClass('down')) {
						link.text("Hide all comments");
						link.removeClass('down').addClass('up');
					} else {
						link.text('Show all comments');
						link.removeClass('up').addClass('down');
					}
				});
				
				$(".comment-form").submit(function(e) {
					e.preventDefault();
					var data = $(this).serialize();
					var action = $(this).attr('action');
					var form = $(this);
					
					$.post(action, data, function(result) {
						
						if(result.success) {
							var html = "<span class='user'>"+
								"<img src='${imgUrl}'/>"+result.username + "</span>" +
								"<span class='note'></span>"+ result.msg +"</span>";
							$('<p>', {
								class: "comment-text",
								html: html
							}).prependTo(form);
						}
					}, 'json');
				});
			});
		</script>
	</body>
</html>
