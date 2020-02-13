<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>MAIL</h1>


	<div class="container">
		<p name="time">Date: <%= (new java.util.Date()).toLocaleString()%></p>

		<br>
		<form action="sendmail" method="POST">
			<span>RMA </span><input type="text" name="rma">
			<table class="table">
				<thead>
					<tr>
						<th scope="col">PN</th>
						<th scope="col">QTY</th>

					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="text" name="row1.1"></td>
						<td><input type="text" name="row1.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row2.1"></td>
						<td><input type="text" name="row2.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row3.1"></td>
						<td><input type="text" name="row3.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row4.1"></td>
						<td><input type="text" name="row4.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row5.1"></td>
						<td><input type="text" name="row5.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row6.1"></td>
						<td><input type="text" name="row6.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row7.1"></td>
						<td><input type="text" name="row7.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row8.1"></td>
						<td><input type="text" name="row8.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row9.1"></td>
						<td><input type="text" name="row9.2"></td>
					</tr>
					<tr>
						<td><input type="text" name="row10.1"></td>
						<td><input type="text" name="row10.2"></td>
					</tr>



				</tbody>
			</table>

			<input type="submit" value="SEND">
		</form>

	</div>

	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	<script type="extensions/editable/bootstrap-table-editable.js"></script>
</body>
</html>