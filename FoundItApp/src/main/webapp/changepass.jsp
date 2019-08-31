<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Change Password</title>
<style>
.content {
	width: 400px;
	height: 350px;
	border: 2px solid #CCCCCC;
	padding-left: 110px;
	padding-right: 20px;
	padding-top: 50px;
	padding-bottom: 20px;
	margin-left: 360px;
	margin-right: 300px;
	margin-top: 20px;
	margin-bottom: 100px
}
</style>
</head>
<body>
	<div class="container">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<div style="margin-top: 100px" align="center">
			<h3>Change Password</h3>
		</div>
		<div class="content">
			<c:if test="${requestScope.status != null}">
				<p style="color: #FF0000;">${requestScope.status}</p>
			</c:if>
			<form action="pwdchange" method="post">
				<label>Current password</label><br> <input type="password"
					name="oldpass" style="width: 165px"><br> <label>New
					password </label><br> <input type="password" name="newpass"
					style="width: 165px"><br> <label>Password
					again</label><br> <input type="password" name="confirm"
					style="width: 165px"><br> <br> <input
					type="submit" value="Change" style="width: 80px"
					class="btn btn-default"> <input type="button"
					class="btn btn-default" value="Back" onclick="location='login.jsp'"
					style="width: 80px">
			</form>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>