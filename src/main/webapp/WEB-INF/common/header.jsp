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
<link href="../css/style.css" type="text/css" rel="stylesheet" />


<title>${param.title}</title>
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="______"><strong>Bizcom</strong></a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNavDropdown">
				<ul class="nav ">
					<li class="nav-item dropdown"><span
						class="nav-link dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> <strong>Receiving</strong>
					</span>
						<div class="dropdown-menu dropdown-menu-right"
							aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item"
								href="<%=request.getContextPath()%>/pre_alert">Pre-Alert</a> <a
								class="dropdown-item" href="<%=request.getContextPath()%>/physicalreceiving?page=Search_Item">Physical Receiving</a> <a class="dropdown-item" href="#">Issue RMA</a>
						</div></li>
				</ul>
			</div>
		</nav>
	</header>