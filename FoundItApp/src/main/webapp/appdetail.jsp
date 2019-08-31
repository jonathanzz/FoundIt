<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="au.edu.unsw.soacourse.founditapp.model.JobList"%>
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
	<div class="container" id="paper">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<div style="margin-top: 100px" align="center">
			<h3>Application Detail</h3>
		</div>
		<div style="margin-top: 20px;" align="center">
			<form action="updateapplication" method="post">
				<input type="hidden" name="appid"
					value="${requestScope.appdetail.id}"> <input type="hidden"
					name="jobid" value="${requestScope.appdetail.applyJobId}">
				<table class="table table-bordered" style="width: 60%">
					<tr>
						<td><b>Application ID</b></td>
						<td>${requestScope.appdetail.id}</td>
					</tr>
					<tr>
						<td><b>Email Address</b></td>
						<td>${requestScope.appdetail.candidateEmail}</td>
					</tr>
					<tr>
						<td><b>Job ID</b></td>
						<td>${requestScope.appdetail.applyJobId}</td>
					</tr>
					<tr>
						<td><b>Status</b></td>
						<td>${requestScope.appdetail.status}</td>
					</tr>
				</table>
				<c:if test="${requestScope.appdetail.status == 'open'}">
					<p align="left">Cover Letter (no longer than 150 letters)</p>
					<textarea class="form-control" rows="3" name="coverletter"
						maxlength="150" wrap="soft"
						placeholder="please input your cover letter here">${requestScope.appdetail.coverLetter}</textarea>
					<br>
					<input type="submit" class="btn btn-default" value="Update">
				</c:if>
				<c:if test="${requestScope.appdetail.status == 'pending'}">
					<a
						href="accpetinverview?appid=${requestScope.appdetail.id}&accept=Y"
						class="btn btn-default">Accept</a>
					<a
						href="accpetinverview?appid=${requestScope.appdetail.id}&accept=N"
						class="btn btn-default">Reject</a>
				</c:if>
			</form>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>