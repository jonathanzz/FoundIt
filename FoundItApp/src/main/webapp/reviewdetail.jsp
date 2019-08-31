<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Review Details</title>
<style>
.content {
	width: 400px;
	height: 450px;
	border: 2px solid #CCCCCC;
	padding-left: 110px;
	padding-right: 20px;
	padding-top: 50px;
	padding-bottom: 20px;
	margin-left: 350px;
	margin-right: 300px;
	margin-top: 100px;
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
			<h3>Review Details</h3>
		</div>
		<div style="margin-top: 20px" align="center">
			<c:if test="${requestScope.review != null}">
				<table class="table table-bordered" style="width: 60%">
					<tr>
						<td style="width: 25%"><b>Review Id</b></td>
						<td style="width: 25%"><b>App Id</b></td>
						<td style="width: 25%"><b>Comments</b></td>
						<td style="width: 25%"><b>Decision</b></td>
					</tr>
					<tr>
						<td>${requestScope.review.id }</td>
						<td>${requestScope.review.appId }</td>
						<td>${requestScope.review.comments }</td>
						<td>${requestScope.review.decision }</td>
					</tr>
				</table>
			</c:if>
			<a href="reviewing" class="btn btn-primary">back</a>
		</div>

		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>