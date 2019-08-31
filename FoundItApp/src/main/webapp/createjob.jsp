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
	<div class="container">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<div style="margin-top: 100px" align="center">
			<h3>Create Job</h3>
		</div>
		<div style="margin-top: 20px;" align="center">
			<form action="addjob" method="post">
				<input type="hidden" name="companyid"
					value="${sessionScope.company.id}">
				<table class="table table-bordered" style="width: 40%">
					<tr>
						<td style="width: 30%">Job Title</td>
						<td><input type="text" name="title" style="width: 100%"></td>
					</tr>
					<tr>
						<td>Position Type</td>
						<td><input type="text" name="positionType"
							style="width: 100%"></td>
					</tr>
					<tr>
						<td>Salary</td>
						<td><input type="text" name="salary" style="width: 100%"></td>
					</tr>
					<tr>
						<td>Description</td>
						<td><input type="text" name="description" style="width: 100%"></td>
					</tr>
					<tr>
						<td>Skills</td>
						<td><input type="text" name="skills" style="width: 100%"></td>
					</tr>
					<tr>
						<td>Link</td>
						<td><input type="text" name="link" style="width: 100%"></td>
					</tr>
					<tr>
						<td>Location</td>
						<td><input type="text" name="location" style="width: 100%"></td>
					</tr>
				</table>
				<br> <input type="submit" class="btn btn-primary" value="Submit"
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