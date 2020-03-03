<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />

<section>

	<div class="container">

		<H1>Repair 01</H1>


		<div class="row">
			<form>
				<div class="form-group">
					<label for="errorCode">Error Code</label> <input type="text"
						class="form-control" id="errorCode" aria-describedby="errorCode"
						value="${errorCode}">
				</div>
				<div class="form-group">
					<label for="desciption">Password</label>
					<textarea name="desciption" class="form-control" id="desciption"
						rows="4" cols="100"></textarea>



				</div>

				<div class="form-group">
					<label for="action">Password</label>
					<textarea name="action" class="form-control" id="action" rows="4"
						cols="100"></textarea>


				</div>

				<select class="custom-select" id="duty" name="duty">
					<option value="0" selected>Choose...</option>
					<option value="1">One</option>
					<option value="2">Two</option>
					<option value="3">Three</option>
				</select>

				<div class="form-group">
					<label for="pn">Part Number</label> <input type="text"
						class="form-control" id="pn" aria-describedby="part_number"
						name="pn">
				</div>

				<div class="form-group">
					<label for="old_pn">Old Part Number</label> <input type="text"
						class="form-control" id="old_pn"
						aria-describedby="old_part_number" name="old_pn">
				</div>

				<div class="form-group">
					<label for="location">Location on board</label> <input type="text"
						class="form-control" id="location"
						aria-describedby="localtion_on_board" name="location">
				</div>



				<button type="submit" class="btn btn-primary">Submit</button>
			</form>



		</div>



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