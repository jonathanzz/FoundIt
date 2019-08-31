<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		<div style="margin-top: 80px" align="center">
			<h3>Show Applications</h3>
		</div>
		<div>
			<table class="table table-bordered" style="width: 60%">
				<tr>
					<td><b>User email</b></td>
					<td><b>Application Status</b></td>
				</tr>
				<c:forEach var="app" items="${requestScope.apps}">
					<tr>
						<td><a
							href="candidatedetail?jobId=${requestScope.jobId}&email=${app.candidateEmail}&status=${app.status}">${app.candidateEmail}</a></td>
						<td>${app.status}</td>
					</tr>
				</c:forEach>
			</table>
			<!-- </form> -->
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>