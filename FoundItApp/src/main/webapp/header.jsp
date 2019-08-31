<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="au.edu.unsw.soacourse.founditapp.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Header</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/Users/ZQH/Documents/LUNA/workspace/FoundItApp/src/main/webapp/bootstrap.min.css" />
</head>
<body>
	<!-- Fixed navbar -->
	<div class="navbar navbar-inverse navbar-fixed-top headroom">
		<div class="container">
			<div class="navbar-header">
				<div class="navbar-header">
					<a class="navbar-brand" href="index.jsp">FoundIt</a>
				</div>
			</div>

			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav pull-right">
					<li><a href="index.jsp">Home</a></li>
					<c:if test="${sessionScope.username == null}">
						<li><a class="btn" href="login.jsp">SIGN IN</a></li>
					</c:if>
					<c:if test="${sessionScope.username != null}">
						<li class="dropdown"><a href="#" data-toggle="dropdown">Hello,
								<c:out value="${sessionScope.username}"></c:out><b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<c:if test="${sessionScope.userType == 1}">
									<li><a href="uProfile">My Profile</a></li>
									<li><a href="changepass.jsp">Change Password</a></li>
									<li><a href="showapplication">My Applications</a></li>
									<li><a href="showsavedjob">Saved Jobs</a></li>
									<li><a href="jobalert.jsp">Job Alert</a></li>
								</c:if>
								<c:if test="${sessionScope.userType == 2}">
									<li><a href="cProfile">Company Profile</a></li>
									<li><a href="changepass.jsp">Change Password</a></li>
									<li><a href="showjoblist">Job List</a></li>
								</c:if>
								<c:if test="${sessionScope.userType == 3}">
									<li><a href="reviewing">Review</a></li>
								</c:if>
								<li><a href="logout">Sign Out</a></li>
							</ul></li>
					</c:if>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<form action="user" method="post" id="logout">
		<input type="hidden" name="user" value="logout">
	</form>
	<!-- /.navbar -->
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
</body>
</html>