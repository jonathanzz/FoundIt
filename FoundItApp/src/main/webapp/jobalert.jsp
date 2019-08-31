<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<style>
.content {
	width: 400px;
	height: 300px;
	border: 2px solid #CCCCCC;
	padding-left: 110px;
	padding-right: 20px;
	padding-top: 50px;
	padding-bottom: 20px;
	margin-left: 360px;
	margin-right: 360px;
	margin-top: 30px;
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
			<h3>Job Alert</h3>
		</div>
		<div class="content">
			<c:if test="${not empty requestScope.status}">
				<p style="color: #FF0000">
					<c:out value="${requestScope.status}"></c:out>
				</p>
			</c:if>
			<form action="createjobalert" method="post" class="form-inline">
				<div class="form-group">
					<label>Keyword</label> <br> <input type="text" name="keyword"
						class="form-control">
				</div>
				<br>
				<div class="form-group">
					<label>Sort by</label> <br> <input type="text" name="sortby"
						class="form-control">
				</div>
				<br> <br>
				<div>
					<button type="submit" class="btn btn-default" style="width: 170px">Create
						job alert</button>
				</div>
			</form>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>