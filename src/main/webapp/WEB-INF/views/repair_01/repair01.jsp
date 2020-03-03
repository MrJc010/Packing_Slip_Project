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
		document.write( '						<button class=\"btn btn-link\ collapsed" type=\"button\" data-toggle=\"collapse\"\n' );
		document.write( '							data-target=\"#collapse'+temp+'\" aria-expanded=\"false\"\n' );
		document.write( '							aria-controls=\"collapse'+temp+'\">Error Code '+temp+'</button>\n' );
		document.write( '					</h5>\n' );
		document.write( '				</div>\n' );
		document.write( '\n' );
		document.write( '				<div id=\"collapse'+temp+'\" class=\"collapse\"\n' );
		document.write( '					aria-labelledby=\"heading'+temp+'\" data-parent=\"#accordionExample\">\n' );
		document.write( '					<div class=\"card-body\">\n' );
		document.write( '						<button class="my-5 btn btn-primary"name=\"edit'+temp+'\" onclick=\"enableForm()\">Edit</button>\n' );
		document.write( '						<form>\n' );
		
		document.write( '							<fieldset id = \"disable'+temp+'\" disabled>\n' );
		
		document.write( '   <div class=\"input-group mb-3\">\n' );
		document.write( '            <div class=\"input-group-prepend\">\n' );
		document.write( '              <label class=\"input-group-text\" for=\"duty\">Select\n' );
		document.write( '                Duty: </label>\n' );
		document.write( '            </div>\n' );
		document.write( '            <select class=\"custom-select\" id=\"duty\" name=\"duty'+temp+'\">\n' );
		document.write( '              <option value=\"0\" selected>Choose a duty</option>\n' );
		document.write( '              <option value=\"1\">duty 01</option>\n' );
		document.write( '              <option value=\"2\">duty 02</option>\n' );
		document.write( '              <option value=\"3\">duty 03</option>\n' );
		document.write( '            </select>\n' );
		document.write( '          </div>\n' );
		document.write( '\n' );
		document.write( '          <br />' );

		document.write( '\n' );
		document.write( '          <div class=\"form-group\">\n' );
		document.write( '            <label for=\"oldPN\">OLD PN</label>\n' );
		document.write( '            <input type=\"text\" class=\"form-control\" name=\"oldPN'+temp+'\" placeholder=\"Enter Old PN\">\n' );
		document.write( '          </div>\n' );
		document.write( '\n' );
		document.write( '\n' );
		document.write( '          <br />\n' );
		document.write( '\n' );
		document.write( '          <div class=\"form-group\">\n' );
		document.write( '            <label for=\"oldPN\">NEW PN</label>\n' );
		document.write( '            <input type=\"text\" class=\"form-control\" name=\"newPN'+temp+'\" placeholder=\"Enter new PN\">\n' );
		document.write( '          </div>\n' );
		document.write( '\n' );
		document.write( '          <br />\n' );
		document.write( '\n' );
		document.write( '          <div class=\"form-group\">\n' );
		document.write( '            <label for=\"oldPN\">AREA</label>\n' );
		document.write( '            <input type=\"text\" class=\"form-control\" name=\"area'+temp+'\" placeholder=\"Enter area\">\n' );
		document.write( '          </div>\n' );
		document.write( '\n' );
		document.write( '\n' );
		document.write( '          <br />\n' );
		document.write( '          <div class=\"form-group\">\n' );
		document.write( '            <label for=\"actionInput\">Action</label>\n' );
		document.write( '            <textarea type=\"text\" class=\"form-control\" name=\"action'+temp+'\" rows=\"4\" id=\"actionInput\" aria-describedby=\"emailHelp\"\n' );
		document.write( '              placeholder=\"Enter action\">');
		
		
		document.write( '</textarea>	');
		document.write( '								<button type=\"submit\" value=\"submit'+temp+'\"  name = \"submit'+temp+'\" class=\"btn btn-primary\ my-5 text-center mx-auto">Submit</button>\n' );
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