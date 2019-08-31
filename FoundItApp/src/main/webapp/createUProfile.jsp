<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<div style="margin-top: 100px" align="center">
			<h3>User Profile</h3>
		</div>
		<div style="margin-top: 100px;" align="center">
			<form action="uProfileCreate" method="post">
				<input type="hidden" name="jobid" value="${requestScope.result.id}">
				<table class="table table-bordered" style="width: 40%">
					<tr>
						<td style="width: 30%"><b>Email</b></td>
						<td>${sessionScope.username}</td>
					</tr>
					<tr>
						<td><b>First Name</b></td>
						<td><input type="text" name="firstName" style="width: 100%"></td>
					</tr>
					<tr>
						<td><b>Last Name</b></td>
						<td><input type="text" name="lastName" style="width: 100%"></td>
					</tr>
					<tr>
						<td><b>Address</b></td>
						<td><input type="text" name="address" style="width: 100%"></td>
					</tr>
					<tr>
						<td><b>Driver License</b></td>
						<td><input type="text" name="dlNumber" style="width: 100%"></td>
					</tr>
					<tr>
						<td><b>Skills</b></td>
						<td><input id="skills" type="text" name="skills"
							style="width: 100%"></td>
					</tr>
					<tr>
						<td><b>Experience</b></td>
						<td><input type="text" name="experience" style="width: 100%"></td>
					</tr>
					<tr>
						<td><b>Education</b></td>
						<td><input type="text" name="education" style="width: 100%""></td>
					</tr>
				</table>
				<br> <input type="submit" class="btn btn-primary" value="Save">
			</form>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>