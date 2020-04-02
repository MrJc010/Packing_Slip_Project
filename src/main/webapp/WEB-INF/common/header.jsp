<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://kit.fontawesome.com/7c4bbe027d.js"
	crossorigin="anonymous"></script>

<style>
<%@include file="/WEB-INF/css/datepicker.min.css"%>

.navbar-nav li:hover>ul.dropdown-menu {
	display: block;
}

.dropdown-submenu {
	position: relative;
}

.dropdown-submenu>.dropdown-menu {
	top: 0;
	left: 100%;
	margin-top: -6px;
}

/* rotate caret on hover */
.dropdown-menu>li>a:hover:after {
	text-decoration: underline;
	transform: rotate(-90deg);
}
</style>

<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">
 <!--<link href="${pageContext.request.contextPath}/WEB-INF/css/style.css" type="text/css" rel="stylesheet" />  -->


<title>${param.title}</title>
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href=""><strong>Bizcom</strong></a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavDropdown">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="http://example.com"
						id="navbarDropdownMenuLink" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> Receiving </a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/pre_alert">Pre-Alert</a></li>
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/searchitem">Physical
									Receiving</a></li>
							<li class="dropdown-submenu"><a
								class="dropdown-item dropdown-toggle" href="#">Reports</a>
								<ul class="dropdown-menu">
									<li><a class="dropdown-item"
										href="<%=request.getContextPath()%>/shortitem">Short Item</a></li>
									<li><a class="dropdown-item"
										href="<%=request.getContextPath()%>/extraitem">Extra Item</a></li>
									<li><a class="dropdown-item"
										href="<%=request.getContextPath()%>/incorrectitem">Incorrect
											Item</a></li>
								</ul></li>
						</ul></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/mici?page=display">MICI</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/repair01">REPAIR01</a></li>
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/search">SEARCH</a></li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="http://example.com"
						id="navbarDropdownMenuLink" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">SHOP FLOOR</a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
								<li>
									<a class="dropdown-item" href="<%=request.getContextPath()%>/new_shopfloor_project">NEW PROJECT</a>
								</li>
								<li>
									<a class="dropdown-item" href="<%=request.getContextPath()%>/exist_project">EXIST PROJECT</a>
								</li>
								<li>
									<a class="dropdown-item" href="<%=request.getContextPath()%>/shopfloor_search">SEARCH</a>
								</li>
								<li>
									<a class="dropdown-item" href="<%=request.getContextPath()%>/shopfloor_report">REPORT</a>
								</li>
							</ul>
					</li>
				</ul>
			</div>
		</nav>
	</header>
