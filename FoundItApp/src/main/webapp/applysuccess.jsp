<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Apply Success</title>
</head>
<body>
	<div class="container">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<div style="margin-top: 200px;" align="center">
			<form action="jobdetail" method="post">
				<input type="hidden" name="id" value="${requestScope.jobid}">
				<input type="hidden" name="coverletter"
					value="${requestScope.coverletter}">
				<p>Your application has been sent, HR will check it as soon as
					possible.</p>
				<br> <input type="submit" class="btn btn-primary"
					value="Check job detail" style="width: 200px">&nbsp;<a
					href="index.jsp" class="btn btn-primary" style="width: 200px">Search
					another job</a>
			</form>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>