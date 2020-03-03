<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section>

	<div class="container">

		<H1>Repair 01</H1>
		

<script>
myFunction(3);
function myFunction(count) {

	while(count > 0){
		var temp = "";
		if(count === 10) temp = "Ten";
		else if(count === 9) temp = "Nine";
		else if(count === 8) temp = "Eight";
		else if(count === 7) temp = "Seven";
		else if(count === 6) temp = "Six";
		else if(count === 5) temp = "Five";
		else if(count === 4) temp = "Four";
		else if(count === 3) temp = "Three";
		else if(count === 2) temp = "Two";
		else if(count === 1) temp = "One";
		document.write( '<div class=\"accordion\" id=\"accordionExample\">\n' );
		document.write( '			<div class=\"card\">\n' );
		document.write( '				<div class=\"card-header\" id=\"heading'+temp+'\">\n' );
		document.write( '					<h5 class=\"mb-0\">\n' );
		document.write( '						<button class=\"btn btn-link\" type=\"button\" data-toggle=\"collapse\"\n' );
		document.write( '							data-target=\"#collapse'+temp+'\"\n' );
		document.write( '							aria-controls=\"collapse'+temp+'\">Error Code '+temp+'</button>\n' );
		document.write( '					</h5>\n' );
		document.write( '				</div>\n' );
		document.write( '\n' );
		document.write( '				<div id=\"collapse'+temp+'\" class=\"collapse show\"\n' );
		document.write( '					aria-labelledby=\"heading'+temp+'\" data-parent=\"#accordionExample\">\n' );
		document.write( '					<div class=\"card-body\">\n' );
		document.write( '						<button name=\"edit'+temp+'\" onclick=\"enableForm()\">Edit</button>\n' );
		document.write( '						<form>\n' );
		
		document.write( '							<fieldset id = \"disable'+temp+'\" disabled>\n' );
		document.write( '								<div class=\"form-row\">\n' );
		document.write( '									<div class=\"form-group col-md-6\">\n' );
		document.write( '										<label for=\"inputEmail'+temp+'\">Email</label> <input type=\"email\"\n' );
		document.write( '											class=\"form-control\" name=\"inputEmail'+temp+'\" id=\"inputEmail'+temp+'\" placeholder=\"Email\">\n' );
		document.write( '									</div>\n' );
		document.write( '									<div class=\"form-group col-md-6\">\n' );
		document.write( '										<label for=\"inputPassword'+temp+'\">Password</label> <input\n' );
		document.write( '											type=\"password\" name=\"inputPassword'+temp+'\" class=\"form-control\" id=\"inputPassword'+temp+'\"\n' );
		document.write( '											placeholder=\"Password\">\n' );
		document.write( '									</div>\n' );
		document.write( '								</div>\n' );
		document.write( '								<button type=\"submit\" name = \"submit'+temp+'\" class=\"btn btn-primary\">Sign in</button>\n' );
		document.write( '							</fieldset>\n' );
		document.write( '						</form>\n' );
		document.write( '					</div>\n' );
		document.write( '				</div>\n' );
		document.write( '			</div>' );
		count--;
	}
}

function enableForm() {
	var res = event.target.name.substring(4);
	console.log(res);
	document.getElementById("disable"+res).disabled = false;	
}
</script>
	</div>


</section>

<script type='text/javascript'>
	var counter = 1;
	var limit = 3;
	function addInput(divName) {
		if (counter == limit) {
			alert("You have reached the limit of adding " + counter + " inputs");
		} else {
			var newdiv = document.createElement('div');
			newdiv.innerHTML = "Entry " + (counter + 1)
					+ " <br><input type='text' name='name"+counter+"'>";
			document.getElementById(divName).appendChild(newdiv);
			counter++;
		}
	}
</script>
<jsp:include page="/WEB-INF/common/footer.jsp" />