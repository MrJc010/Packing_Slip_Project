<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form name="RMA" id="form-1" action="/physicalreceiving" method="POST">
		<label for="input-1">Input:</label> <label for="input-1">RMA#:</label><br>
		<input id="input-1" name="rma" placeholder="Enter RMA number" type="text" /><br>
		<input id="input-2" name="ppid" placeholder="Enter PPID number" type="text" /><br>
		<input id="input-3" name="dps" placeholder="Enter DPS number" type="text" /><br>
		<input type="submit" value="Search" />
	</form>

</body>
</html>