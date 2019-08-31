<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring 4 MVC - HelloWorld Index Page</title>
</head>
<body>
	<div class="container">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<div style="margin-top: 100px;" align="center">
			<h2>Welcome to FoundIt</h2>
			<br>
			<form action="searchjob" method="post">
				<select name="key" >
					<option value="keyword">Keyword</option>
					<option value="skills">Skills</option>
					<option value="status">Status</option>
				</select> <input type="text" name="value" style="width: 500px; height: 25px" /><input
					type="submit" value="Search">
			</form>
		</div>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>