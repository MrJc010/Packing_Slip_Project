<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Search Info"></c:param>
</c:import>
<style>
*, *:before, *:after {
	box-sizing: border-box;
}


</style>


<section>

	<div class="main container-fluid">
		<div class="row mt-3">
			<div class="col-md-4 col-sm-0"></div>
			<div class="col-md-4 col-sm-12 shadow-lg p-3 mb-5 bg-white rounded">
				<div class="text-center">
					<form action="<%=request.getContextPath()%>/signin" method="POST">
						<h1 class="display-5 text-primary mb-5">
							<strong>Bizcom Electronic Inc.</strong>
						</h1>
						<h1 class="h3 mb-3 font-weight-normal">
							<strong>Please sign in</strong>
						</h1>

						<div class="input-group mb-3 text-left">
							<div class="input-group-prepend">
								<div class="input-group-text">
									<i class="fas fa-user text-primary"></i>
								</div>
							</div>
							<input type="text" class="form-control" id="username"
								aria-describedby="username" placeholder="Enter username"
								name="username" value="${username}" required>
						</div>

						<div class="input-group mb-3 text-left">
							<div class="input-group-prepend">
								<div class="input-group-text">
									<i class="fas fa-key text-primary"></i>
								</div>
							</div>
							<input type="password" class="form-control" id="password"
								aria-describedby="password" placeholder="Enter Password"
								name="password" value="${password}" required>
						</div>

						
						<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
							in</button>
						<p class="mt-5 mb-3 text-muted">&copy; Bizcom Electronic Inc. 2020-2021</p>
					</form>
				</div>

			</div>
			<div class="col-md-4 col-sm-0"></div>

		</div>

	</div>
</section>
<c:import url="/WEB-INF/common/footer.jsp" />