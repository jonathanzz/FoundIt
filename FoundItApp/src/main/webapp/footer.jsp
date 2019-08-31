<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Footer</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>

	<div class="navbar navbar-inverse navbar-fixed-bottom">
		<div class="footer2">
			<div class="container">
				<div class="row">
					<form action="user" method="post" id="loglogout">
						<div class="col-md-6 widget">
							<div class="widget-body">
								<p style="color: white;">
									<%
										if (request.getSession().getAttribute("username") == null) {
									%>
									<a href="#" style="color: white;">Home</a> | <a
										href="login.jsp" style="color: white;">Sign In</a>
									<%
										} else {
											String username = (String) request.getSession().getAttribute(
													"username");
									%>
									<a href="#" style="color: white;">Home</a> | <a href="logout"
										style="color: white;">Sign Out</a><input type="hidden"
										name="user" value="logout">
									<%
										}
									%>
								</p>
							</div>
						</div>
					</form>
					<div>
						<div>
							<p class="text-right" style="color: white;">Copyright &copy;
								2016, Designed by Bye Jo</p>
						</div>
					</div>

				</div>
				<!-- /row of widgets -->
			</div>
		</div>
	</div>
</body>
</html>