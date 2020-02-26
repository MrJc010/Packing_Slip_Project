<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section style="height: 59vh;">

	<div class="container">

		<h1 class="display-3 text-primary text-center">
			<strong>Searching Item</strong>
		</h1>
		<div class="row justify-content-center my-5">
			
			<form action="<%=request.getContextPath()%>/searchitem" method="POST">
				<div class="form-group row">
				    <label for="input-2" class="col-sm-2 col-form-label col-form-label-lg"><strong>PPID</strong></label>
					
					<div class="col-sm-10">
						<input id="input-2" class="form-control form-control-lg"
							name="ppid" placeholder="Enter PPID number" type="text" required />
					</div>
				</div>

				<div class="form-group row">
					<label for="input-3" class="col-sm-2 col-form-label col-form-label-lg"><strong>DPS</strong></label>
					<div class="col-sm-10">
						<input id="input-3" class="form-control form-control-lg" name="dps" placeholder="Enter DPS number"
							type="text" required />
					</div>
				</div>

				<div class="row justify-content-center">
					<button type="submit" class="btn btn-primary">
						<strong>Search Item</strong>
					</button>
				</div>
			</form>


			<c:set var="salary" scope="session" value="${re}" />
			<c:if test="${re == \"Cannot Find Item With Your Info!\"}">
				<script type="text/javascript">
					var msg = "Cannot Find Item With Your Info!";
					alert(msg);
				</script>
			</c:if>

		</div>

	</div>

</section>


<jsp:include page="/WEB-INF/common/footer.jsp" />