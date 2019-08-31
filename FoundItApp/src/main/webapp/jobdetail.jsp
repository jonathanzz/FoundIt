<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="au.edu.unsw.soacourse.founditapp.model.JobList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Job Detail</title>
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
			<h3>Job Detail</h3>
		</div>
		<div style="margin-top: 20px;" align="center">
			<c:if
				test="${requestScope.result.status != 'open' || sessionScope.userType != '2' 
				|| sessionScope.company.id != requestScope.result.companyid}">
				<form action="createapplication" method="post">
					<input type="hidden" name="jobid" value="${requestScope.result.id}">
					<table class="table table-bordered" style="width: 60%">
						<tr>
							<td style="width: 30%"><b>JobID</b></td>
							<td>${requestScope.result.id}</td>
						</tr>
						<tr>
							<td><b>Company</b></td>
							<td>${requestScope.result.companyid}</td>
						</tr>
						<tr>
							<td><b>Title</b></td>
							<td>${requestScope.result.title}</td>
						</tr>
						<tr>
							<td><b>Position Type</b></td>
							<td>${requestScope.result.positionType}</td>
						</tr>
						<tr>
							<td><b>Salary</b></td>
							<td>${requestScope.result.salary}</td>
						</tr>
						<tr>
							<td><b>Description</b></td>
							<td>${requestScope.result.description}</td>
						</tr>
						<tr>
							<td><b>Skills</b></td>
							<td>${requestScope.result.skills}</td>
						</tr>
						<tr>
							<td><b>Link</b></td>
							<td>${requestScope.result.link}</td>
						</tr>
						<tr>
							<td><b>Location</b></td>
							<td>${requestScope.result.location}</td>
						</tr>
						<tr>
							<td><b>Status</b></td>
							<td>${requestScope.result.status}</td>
						</tr>
					</table>
					<c:if
						test="${requestScope.result.status == 'open' && sessionScope.userType == '1'}">
						<p style="margin-left: 230px;" align="left">Cover Letter (no
							longer than 150 letters)</p>
						<textarea class="form-control" rows="2" name="coverletter"
							maxlength="150" wrap="soft" style="width: 60%"
							placeholder="please input your cover letter here">${requestScope.coverletter}</textarea>
						<br>
						<a href="addsavedjob?jobid=${requestScope.result.id}"
							style="width: 80px" class="btn btn-primary">Save</a>&nbsp;<input
							type="submit" style="width: 80px" class="btn btn-primary"
							value="Apply">
					</c:if>
					<c:if
						test="${sessionScope.userType == '2' && requestScope.result.status == 'checked'}">
						<a href="addreviewer.jsp?jobId=${requestScope.result.id}"
							class="btn btn-primary">Run Review</a>
					</c:if>
					<c:if
						test="${sessionScope.userType == '2' && requestScope.result.status == 'reviewing'}">
						<p style="color: #FF0000">
							<c:out value="${requestScope.status}"></c:out>
						</p>
						<a href="startinterview?jobId=${requestScope.result.id}"
							class="btn btn-primary">Run Interview</a>
					</c:if>
					<c:if
						test="${sessionScope.userType == '2' && requestScope.result.status == 'interviewing'}">
						<a href="showshortlist?jobId=${requestScope.result.id}"
							class="btn btn-primary">Show All Applications</a>
					</c:if>
				</form>
			</c:if>
			<c:if
				test="${requestScope.result.status == 'open' && sessionScope.userType == '2' 
				&& sessionScope.company.id == requestScope.result.companyid}">
				<form action="editjob" method="post">
					<input type="hidden" name="id" value="${requestScope.result.id}">
					<table class="table table-bordered" style="width: 60%">
						<tr>
							<td style="width: 30%"><b>JobID</b></td>
							<td>${requestScope.result.id}</td>
						</tr>
						<tr>
							<td><b>Company</b></td>
							<td>${requestScope.result.companyid}</td>
						</tr>
						<tr>
							<td><b>Title</b></td>
							<td><input type="text" style="width: 100%" name="title"
								value="${requestScope.result.title}"></td>
						</tr>
						<tr>
							<td><b>Position Type</b></td>
							<td><input type="text" style="width: 100%"
								name="positionType" value="${requestScope.result.positionType}"></td>
						</tr>
						<tr>
							<td><b>Salary</b></td>
							<td><input type="text" style="width: 100%" name="salary"
								value="${requestScope.result.salary}"></td>
						</tr>
						<tr>
							<td><b>Description</b></td>
							<td><input type="text" style="width: 100%"
								name="description" value="${requestScope.result.description}"></td>
						</tr>
						<tr>
							<td><b>Skills</b></td>
							<td><input type="text" style="width: 100%" name="skills"
								value="${requestScope.result.skills}"></td>
						</tr>
						<tr>
							<td><b>Link</b></td>
							<td><input type="text" style="width: 100%" name="link"
								value="${requestScope.result.link}"></td>
						</tr>
						<tr>
							<td><b>Location</b></td>
							<td><input type="text" style="width: 100%" name="location"
								value="${requestScope.result.location}"></td>
						</tr>
						<tr>
							<td><b>Status</b></td>
							<td>${requestScope.result.status}</td>
						</tr>
						<tr>
							<td><b>Total Applications</b></td>
							<td>${requestScope.appcount}</td>
						</tr>
					</table>
					<input type="submit" style="width: 100px" class="btn btn-primary"
						value="Edit"> <input type="reset" style="width: 100px"
						class="btn btn-primary" value="Reset"> <a
						href="deletejob?id=${requestScope.result.id}" style="width: 100px"
						class="btn btn-primary">Delete</a>
					<c:if test="${requestScope.appcount > 0}">
						<a href="runautocheck?jobid=${requestScope.result.id}"
							style="width: 100px" class="btn btn-primary">Auto Check</a>
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