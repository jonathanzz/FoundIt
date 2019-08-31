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
<script type="text/javascript">
	function refresh(seconds) {
		setTimeout("self.location.reload()", seconds * 1000);
	}
	refresh(600);
	function hehe() {
		var a = $
		{
			jobId
		}
		;
		alert(a + "hehehehehe");
	}
</script>
<meta http-equiv="refresh" content="600">
</head>
<body>
	<div class="container">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<div style="margin-top: 100px" align="center">
			<h3>Add Reviewer</h3>
		</div>
		<div style="margin-top: 20px" align="center">
			<c:if test="${fn:length(sessionScope.reviewerlist) < 5}">
				<form action="addreviewer" method="post">
					<input type="hidden" name="jobId" value="${param.jobId}">
					<table class="table table-bordered" style="width: 40%">
						<tr>
							<td><b>Reviewer Id</b></td>
							<td><input type="text" name="reviewerid"
								class="form-control"></td>
						</tr>
						<tr>
							<td><b>Password</b></td>
							<td><input type="text" name="password" class="form-control"></td>
						</tr>
						<tr>
							<td><b>Skills</b></td>
							<td><input type="text" name="skills" class="form-control"></td>
						</tr>
					</table>
					<button type="submit" class="btn btn-primary">Add</button>
				</form>
			</c:if>
			<br>
			<c:if test="${fn:length(sessionScope.reviewerlist) > 0}">
				<c:set var="n">1</c:set>
				<table class="table table-bordered" style="width: 40%">
					<tr>
						<td>#</td>
						<td>Reviewer</td>
					</tr>
					<c:forEach var="member" items="${sessionScope.reviewerlist}">
						<tr>
							<td>${n}</td>
							<td>${member.id}</td>
						</tr>
						<c:set var="n" value="${n+1}"></c:set>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(sessionScope.reviewerlist) > 1}">
				<a href="createhiringteam?jobId=${param.jobId}"
					class="btn btn-primary">Build Team</a>
			</c:if>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>