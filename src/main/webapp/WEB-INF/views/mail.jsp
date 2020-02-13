<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="com.bizcom.mail.Mail"%>
<!DOCTYPE html>
<html>
<head>
<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
</style>
</head>
<body>



	<div class="container my-4 bg-light">
		<h1 class="text-center mb-4 text-primary mt-3">BIZCOM RMA MAILING</h1>

		<p class="h3 " name="time">
			Date:
			<%=(new java.util.Date()).toLocaleString()%></p>
		<hr />
		<br>
		<form action="mail" method="POST">
			<span class="h4 mx-4 mb-4">RMA </span><input type="text" name="rma"
				placeholder=" Enter your RMA Number" style="width: 500px;">
			<table class="table table-lg  my-4 table-bordered">
				<thead>
					<tr class="center">
						<th scope="col" class="text-center bg-dark"><span
							class="text-light">Part Number</span></th>
						<th scope="col" class="text-center bg-dark"><span
							class="text-light">Quality</span></th>

					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row1.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row1.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row2.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row2.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row3.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row3.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row4.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row4.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row5.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row5.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row6.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row6.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row7.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row7.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row8.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row8.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row9.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row9.2"></td>
					</tr>
					<tr>
						<td><input class="form-control input-lg" type="text"
							name="row10.1"></td>
						<td><input class="form-control input-lg" type="text"
							name="row10.2"></td>
					</tr>
				</tbody>
			</table>
			<div class="text-center">
				<input class=" btn btn-primary" type="submit"  value="SEND EMAIL"> <br>
				<p class="${abc}">Message Sent</p>
			</div>

		
		</form>

	</div>


	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	<script type="extensions/editable/bootstrap-table-editable.js"></script>
</body>
</html>