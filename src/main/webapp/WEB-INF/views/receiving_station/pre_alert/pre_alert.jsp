<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />



<section>

	<div class="container" style="height: 25vh;">
		<h1 class="text-center mb-4">Upload excel file</h1>
		<form method="POST" action="<%=request.getContextPath()%>/pre_alert"
			enctype="multipart/form-data">

			<div class="custom-file">
				<input type="file" name="uploadFile" class="custom-file-input"
					id="customFile1"> <label class="custom-file-label"
					for="customFile">Choose file</label>
			</div>
			<div class="input-group-append">
				<input type="submit" value="UPLOAD" />
			</div>

			<script>
				$('#customFile1').on('change', function() {
					//get the file name
					var fileName = $(this).val();
					//replace the "Choose a file" label
					$(this).next('.custom-file-label').html(fileName);
				})
			</script>

		</form>
	</div>

</section>


<jsp:include page="/WEB-INF/common/footer.jsp" />