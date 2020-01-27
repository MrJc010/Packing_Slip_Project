<%@page import="java.awt.event.ActionListener"%>
<%@page import="java.util.Timer"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">

<title>Sign in view JSP</title>
</head>
<%
	// This is Scriptlet , DON"T DO IT IN REAL PROJECT
	//	System.out.println("Dummy2");
	//	System.out.println(request.getParameter("name"));
	Date date = new Date();
%>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-dark">
		<a class="navbar-brand" href="#">LOGO</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#">Home
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Features</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#">Pricing</a></li>
				<li class="nav-item"><a class="nav-link disabled" href="#"
					tabindex="-1" aria-disabled="true">Disabled</a></li>
			</ul>

			<span><a href="/signin">LOGIN</a></span>
		</div>
	</nav>

	<div class="container p-5">
		<div>
			<h1>NEW VERSION</h1>
			Current datesssssssssss is
			<%=date%></div>
		<div>Current date REALTIME is ${currentDate}</div>
		<h1>H1 Paragraph</h1>
		<div>
			<form action="/signin.do" method="POST">
				Enter your Name: <input type="text" name="name" /> <br /> Enter
				your Password: <input type="password" name="password" /> <br /> <input
					type="submit" value="SIGN IN" />
			</form>
		</div>

		<div>
			<p>
				<font color="red">${errorMessage}</font>
			</p>
		</div>

	</div>
	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>