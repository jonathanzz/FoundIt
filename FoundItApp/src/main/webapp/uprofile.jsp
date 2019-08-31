<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Profile</title>
</head>
<body>
	<div class="container" id="paper">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<div style="margin-top: 100px" align="center">
			<h3>User Profile</h3>
		</div>
		<div style="margin-top: 20px" align="center">
			<c:if test="${sessionScope.userType == '1'}">
				<form action="uProfileEdit" method="post">
					<input type="hidden" name="jobid" value="${requestScope.id}">
					<table class="table table-bordered" style="width: 40%">
						<tr>
							<td style="width: 30%"><b>Email</b></td>
							<td>${sessionScope.username}</td>
						</tr>
						<tr>
							<td><b>First Name</b></td>
							<td><input type="text" name="firstName" style="width: 100%"
								value="${requestScope.user.detail.firstName}"></td>
						</tr>
						<tr>
							<td><b>Last Name</b></td>
							<td><input type="text" name="lastName" style="width: 100%"
								value="${requestScope.user.detail.lastName}"></td>
						</tr>
						<tr>
							<td><b>Address</b></td>
							<td><input type="text" name="address" style="width: 100%"
								value="${requestScope.user.detail.address}"></td>
						</tr>
						<tr>
							<td><b>Driver License</b></td>
							<td><input type="text" name="dlNumber" style="width: 100%"
								value="${requestScope.user.detail.dlNumber}"></td>
						</tr>
						<tr>
							<td><b>Skills</b></td>
							<td><input id="skills" type="text" name="skills"
								style="width: 100%" value="${requestScope.user.skills}"></td>
						</tr>
						<tr>
							<td><b>Experience</b></td>
							<td><input type="text" name="experience" style="width: 100%"
								value="${requestScope.user.experience}"></td>
						</tr>
						<tr>
							<td><b>Education</b></td>
							<td><input type="text" name="education" style="width: 100%"
								value="${requestScope.user.education}"></td>
						</tr>
					</table>
					<input type="submit" class="btn btn-primary" value="Save"
						style="width: 100px"> <input type="reset"
						class="btn btn-primary" value="Reset" style="width: 100px">
				</form>

			</c:if>

			<c:if test="${sessionScope.userType == '2'}">
				<form action="interviewresult" method="post">
					<input type="hidden" name="jobId" value="${requestScope.jobId}">
					<table class="table table-bordered">
						<tr>
							<td><b>Email</b></td>
							<td>${requestScope.email}</td>
						</tr>
						<tr>
							<td><b>First Name</b></td>
							<td>${requestScope.user.detail.firstName}</td>
						</tr>
						<tr>
							<td><b>Last Name</b></td>
							<td>${requestScope.user.detail.lastName}</td>
						</tr>
						<tr>
							<td><b>Address</b></td>
							<td>${requestScope.user.detail.address}</td>
						</tr>
						<tr>
							<td><b>Driver License</b></td>
							<td>${requestScope.user.detail.dlNumber}</td>
						</tr>
						<tr>
							<td><b>Skills</b></td>
							<td>${requestScope.user.skills}</td>
						</tr>
						<tr>
							<td><b>Experience</b></td>
							<td>${requestScope.user.experience}</td>
						</tr>
						<tr>
							<td><b>Education</b></td>
							<td>${requestScope.user.education}</td>
						</tr>
						<tr>
							<td><b>Accept Status</b></td>
							<td>${requestScope.status}</td>
						</tr>

					</table>
					<br>
					<c:if test="${requestScope.status == 'acceptinterview'}">
						<a
							href="interviewresult?candidate=${requestScope.email}&result=Y&jobId=${requestScope.jobId}"
							class="btn btn-primary" style="width: 100px">Pass</a>
						<a
							href="interviewresult?candidate=${requestScope.email}&result=N&jobId=${requestScope.jobId}"
							class="btn btn-primary" style="width: 100px">Fail</a>
					</c:if>
					<c:if test="${requestScope.status != 'acceptinterview'}">
						<a href="showshortlist?jobId=${requestScope.jobId}"
							class="btn btn-primary">Back</a>
					</c:if>
				</form>
			</c:if>

		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>