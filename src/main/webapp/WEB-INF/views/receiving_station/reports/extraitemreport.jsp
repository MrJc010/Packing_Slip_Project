<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Extra Item Report"></c:param>
</c:import>
<section>

	<div class="container p-5">
		<div class="row justify-content-center">
			<h1>Extra Item Report</h1>
		</div>
		<div>
			<p>
				First,need to have UI to scan in all information of physical items left.
				This UI should be the same with Physical Receiving.
			</p>
			<p>
				After scan in all information, user should click next.
				All scanned in information will be save into List of List of String. 
			</p>
			<p>
				This List of List of String will then pass to function: "getExtraItemReport" in database Handler.
				"getIncorrectItem" return String Json.
				At this step, the UI should displace all information of extra items.
				This UI also allows to export to excel file.
			</p>
		</div>
		
	</div>


</section>


<c:import url="/WEB-INF/common/footer.jsp" />