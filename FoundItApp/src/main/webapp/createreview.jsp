<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Review</title>
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
		<div style="margin-top: 100px;" align="center">
			<form action="createreview" method="post">
				<input type="hidden" name="appId" value="${param.id}">
				<table class="table table-bordered">
					<tr>
						<td style="width: 50%"><b>Application Id</b></td>
						<td style="width: 50%">${param.id}</td>
					</tr>
					<tr>
						<td><b>Decision</b></td>
						<td><select name="decision" class="form-control">
								<option value="Y">Pass</option>
								<option value="N">Fail</option>
						</select></td>
					</tr>
				</table>
				<p align="left">Comments (no longer than 500 letters)</p>
				<textarea class="form-control" rows="3" name="comments"
					maxlength="150" wrap="soft"
					placeholder="please input comments here">${requestScope.coverletter}</textarea>
				<br>
				<button type="submit" class="btn btn-primary" style="width: 83px">Create</button>
				&nbsp;<a href="reviewing" class="btn btn-primary"
					class="btn btn-primary" style="width: 83px">Back</a>

			</form>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>