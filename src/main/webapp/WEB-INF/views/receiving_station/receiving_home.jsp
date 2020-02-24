<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">


<title>Insert title here</title>
</head>
<body>
	<div class="container p-5">

		<a type="button" class="btn btn-primary mx-5"
			href="<%=request.getContextPath()%>/upload" role="button">Pre-Alert</a>

		<a type="button" class="btn btn-primary"
			href="<%=request.getContextPath()%>/" role="button">Pre-Alert</a>
	</div>


	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>