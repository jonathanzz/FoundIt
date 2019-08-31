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
			<h3>Company Profile</h3>
		</div>
		<div style="margin-top: 20px;" align="center">
			<form action="cProfileEdit" method="post">
				<table class="table table-bordered" style="width: 40%">
					<tr>
						<td style="width: 30%"><b>Manager Account</b></td>
						<td>${sessionScope.username}</td>
					</tr>
					<tr>
						<td><b>Company ID</b></td>
						<td><input type="text" name="id" style="width: 100%"
							value="${requestScope.company.id}"></td>
					</tr>
					<tr>
						<td><b>Company Name</b></td>
						<td><input type="text" name="name" style="width: 100%"
							value="${requestScope.company.name}"></td>
					</tr>
					<tr>
						<td><b>Industry</b></td>
						<td><input type="text" name="industry" style="width: 100%"
							value="${requestScope.company.industry}"></td>
					</tr>
					<tr>
						<td><b>Description</b></td>
						<td><input type="text" name="description" style="width: 100%"
							value="${requestScope.company.description}"></td>
					</tr>
					<tr>
						<td><b>Web Site</b></td>
						<td><input type="text" name="website" style="width: 100%"
							value="${requestScope.company.website}"></td>
					</tr>
					<tr>
						<td><b>Location</b></td>
						<td><input type="text" name="location" style="width: 100%"
							value="${requestScope.company.location}"></td>
					</tr>
				</table>
				<br> <input type="submit" class="btn btn-primary" value="Save"
					style="width: 100px"> <input type="reset"
					class="btn btn-primary" value="Reset" style="width: 100px">
			</form>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>