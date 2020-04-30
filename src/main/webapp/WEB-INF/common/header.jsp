<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://kit.fontawesome.com/7c4bbe027d.js"
	crossorigin="anonymous"></script>

<style>
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

<!-- TODO Import Bootstrap from local -->
<!-- Bootstrap core CSS -->



<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/gijgo/1.9.13/combined/css/gijgo.min.css"
	rel="stylesheet" type="text/css" />
<title>${param.title}</title>
</head>
<body>

	<header>
		<!-- Fixed navbar -->
		<nav class="navbar navbar-expand-md navbar-dark bg-dark">
			<a class="navbar-brand" href="<%=request.getContextPath()%>/"><strong>Bizcom</strong></a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarCollapse" aria-controls="navbarCollapse"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarCollapse">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle"
						href="<%=request.getContextPath()%>/" id="navbarDropdownMenuLink"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="text-white"><strong>RECEIVING</strong></span>
					</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/pre_alert"> <span
									class="text-black"><strong>PRE-ALERT</strong></span>
							</a></li>
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/searchitem"><span
									class="text-black"><strong>Physical Receiving</strong></span></a></li>
							<li class="dropdown-submenu"><a
								class="dropdown-item dropdown-toggle" href="#"><span
									class="text-black"><strong>Report</strong></span></a>
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
						href="<%=request.getContextPath()%>/mici?page=display"><span
							class="text-white"><strong>MICI</strong></span></a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/repair01"><span
							class="text-white"><strong>REPAIR01</strong></span></a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/qc1"><span
							class="text-white"><strong>QC1</strong></span></a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/eco"><span
							class="text-white"><strong>ECO</strong></span></a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/repair02"><span
							class="text-white"><strong>REPAIR02</strong></span></a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/v1"><span
							class="text-white"><strong>V1</strong></span></a></li>



					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/qc2"><span
							class="text-white"><strong>QC2</strong></span></a></li>

					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/runin2"><span
							class="text-white"><strong>RUNIN-2</strong></span></a></li>

					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/cmb2"><span
							class="text-white"><strong>CMB2</strong></span></a></li>

					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/qc3"><span
							class="text-white"><strong>QC3</strong></span></a></li>

					<%-- 	<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/cosmetic_check"><span
							class="text-white"><strong>Cosmetic Check</strong></span></a></li>

					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/packing"><span
							class="text-white"><strong>Packing</strong></span></a></li>

					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/shipping"><span
							class="text-white"><strong>Shipping</strong></span></a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/obe"><span
							class="text-white"><strong>OBE</strong></span></a></li>
 --%>

					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/pe_analyze"><span
							class="text-white"><strong>PE Analyze</strong></span></a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/pe_clca_identify"><span
							class="text-white"><strong>PE CLCA and Identify</strong></span></a></li>


					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/search"><span
							class="text-white"><strong>SEARCH</strong></span></a></li>




					<%-- <li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="http://example.com"
						id="navbarDropdownMenuLink" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> <span
							class="text-white"><strong>SHOP FLOOR</strong></span>

					</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/shopfloor/create_new_partnumber_step1">NEW
									PROJECT</a></li>
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/shopfloor/station_management">STATION
									MANAGEMENT</a></li>
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/shopfloor_search">SEARCH</a>
							</li>
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/shopfloor_report">REPORT</a>
							</li>
						</ul></li> --%>
				</ul>
				<%
					if (request.getSession().getAttribute("username") == null) {
						request.setAttribute("setHiddenSignOut", "hidden");

					} else {
						request.setAttribute("setHiddenSignOut", "show");
					}
				%>

				<ul class="navbar-nav ml-auto mr-5">
				<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle"
						id="navbarDropdownMenuLink"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="text-white"><strong>Account</strong></span>
					</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/signout"> <span
									class="text-black"><strong>Sign Out</strong></span>
							</a></li>
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/signup"> <span
									class="text-black"><strong>Create Account</strong></span>
							</a></li>
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/manage_account"> <span
									class="text-black"><strong>Manage Account</strong></span>
							</a></li>
						</ul>

				</ul>

				
			</div>
		</nav>
	</header>