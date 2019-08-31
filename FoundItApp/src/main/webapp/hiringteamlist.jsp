<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
			<h3>Hiring Team</h3>
		</div>
		<div style="margin-top: 20px" align="center">
			<table class="table table-bordered" style="width: 60%">
				<tr>
					<td style="width: 20%">#</td>
					<td><b>Review ID</b></td>
					<td style="width: 40%"><b>Skills</b></td>
				</tr>
				<c:set var="n">1</c:set>
				<c:forEach var="member" items="${requestScope.hteam}">
					<tr>
						<td><c:out value="${n}"></c:out></td>
						<td><c:out value="${member.id}"></c:out></td>
						<td><c:out value="${member.skills}"></c:out></td>

					</tr>
					<c:set var="n" value="${n + 1}"></c:set>
				</c:forEach>
			</table>
			<a
				href="assignapps?teamid=${requestScope.teamId}&jobid=${requestScope.jobId}"
				class="btn btn-primary">Assign Applications</a>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>