<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">

<title>Add New Todo</title>

<style>
.error {
	font-weight: 400;
	color: red;
	font-size: 0.7em;
	background-color: grey;
}
</style>
</head>
<body>
	<nav class="navbar navbar-default">

		<a href="/" class="navbar-brand">Brand</a>

		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Home</a></li>
			<li><a href="/list-todo.do">Todos</a></li>
			<li><a href="http://www.in28minutes.com">In28Minutes</a></li>
		</ul>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="/logout.do">Logout</a></li>
		</ul>

	</nav>

	<div class="container">
		Your New Action Item:
		<form method="POST" action="/add-todo.do">
			Description : <input name="todo" type="text" /> <BR /> Category : <input
				name="category" type="text" /> <BR /> <input name="add"
				type="submit" />
		</form>
	</div>

	<%@ include file="../common/footer.jspf"%>
	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>