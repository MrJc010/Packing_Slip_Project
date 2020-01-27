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

<title>Todo's Page</title>

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
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
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

			<span><a href="/signout.do">LOGOUT</a></span>
		</div>
	</nav>
	<div class="container-lg mt-4">
		<div class="row justify-content-lg-center mb-4">
			<h1>
				<span class="text-success">Welcome ${name}</span>
			</h1>
		</div>
		<div class="row p-2">

			<button type="button" class="btn btn-primary">
				<h2>
					Todo List <span class="badge badge-light"> ${totalTodo} </span>
				</h2>
			</button>

		</div>

		<div class="row p-2">
			<ol>
				<c:forEach items="${todos}" var="todo">
					<li>${todo.name}&nbsp;&nbsp;<a
						href="/delete-todo.do?todo=${todo.name}">Delete</a></li>
				</c:forEach>
			</ol>
		</div>


		<div>
			<a href="/add-todo.do"> Add New Todo</a>

		</div>

		<div class="my-4">
			<p class="error">This is sample of error and css</p>
		</div>

	</div>


	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>