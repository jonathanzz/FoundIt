<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Review Process</title>
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
			<h3>Reviewing</h3>
		</div>
		<div style="margin-top: 20px" align="center">
			<c:if test="${fn:length(requestScope.result) > 0}">
			<table class="table table-bordered" style="width: 60%">
				<tr>
					<td><b>ApplicationId</b></td>
					<td><b>Candidate Email</b></td>
					<td><b>Review</b></td>
				</tr>
				<c:forEach var="job" items="${requestScope.result}">
					<tr>
						<td><c:out value="${job.id}"></c:out></td>
						<td><c:out value="${job.candidateEmail}"></c:out></td>
						<td><a href="createreview.jsp?id=${job.id}"
								class="btn btn-default">Review</a></td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
			<c:if test="${fn:length(requestScope.result) == 0}">
			<p>There is no application to review.</p>
			</c:if>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>