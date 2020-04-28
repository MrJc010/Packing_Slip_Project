<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>CodePen - Sign-Up/Login Form</title>
  <link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
  <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

</head>
<style>
*,
*:before,
*:after {
  box-sizing: border-box;
}

html {
  overflow-y: scroll;
}

body {
  background: #c1bdba;
  font-family: 'Titillium Web', sans-serif;
}

a {
  text-decoration: none;
  color: #1ab188;
  -webkit-transition: 0.5s ease;
  transition: 0.5s ease;
}
a:hover {
  color: #179b77;
}

.form {
  background: rgba(19, 35, 47, 0.9);
  padding: 40px;
  max-width: 600px;
  margin: 40px auto;
  border-radius: 4px;
  box-shadow: 0 4px 10px 4px rgba(19, 35, 47, 0.3);
}

.tab-group {
  list-style: none;
  padding: 0;
  margin: 0 0 40px 0;
}
.tab-group:after {
  content: '';
  display: table;
  clear: both;
}
.tab-group li a {
  display: block;
  text-decoration: none;
  padding: 15px;
  background: rgba(160, 179, 176, 0.25);
  color: #a0b3b0;
  font-size: 20px;
  float: left;
  width: 50%;
  text-align: center;
  cursor: pointer;
  -webkit-transition: 0.5s ease;
  transition: 0.5s ease;
}
.tab-group li a:hover {
  background: #179b77;
  color: #ffffff;
}
.tab-group .active a {
  background: #1ab188;
  color: #ffffff;
}

.tab-content > div:last-child {
  display: none;
}

h1 {
  text-align: center;
  color: #ffffff;
  font-weight: 300;
  margin: 0 0 40px;
}

label {
  position: absolute;
  -webkit-transform: translateY(6px);
  transform: translateY(6px);
  left: 13px;
  color: rgba(255, 255, 255, 0.5);
  -webkit-transition: all 0.25s ease;
  transition: all 0.25s ease;
  -webkit-backface-visibility: hidden;
  pointer-events: none;
  font-size: 22px;
}
label .req {
  margin: 2px;
  color: #1ab188;
}

label.active {
  -webkit-transform: translateY(50px);
  transform: translateY(50px);
  left: 2px;
  font-size: 14px;
}
label.active .req {
  opacity: 0;
}

label.highlight {
  color: #ffffff;
}

input,
textarea {
  font-size: 22px;
  display: block;
  width: 100%;
  height: 100%;
  padding: 5px 10px;
  background: none;
  background-image: none;
  border: 1px solid #a0b3b0;
  color: #ffffff;
  border-radius: 0;
  -webkit-transition: border-color 0.25s ease, box-shadow 0.25s ease;
  transition: border-color 0.25s ease, box-shadow 0.25s ease;
}
input:focus,
textarea:focus {
  outline: 0;
  border-color: #1ab188;
}

textarea {
  border: 2px solid #a0b3b0;
  resize: vertical;
}

.field-wrap {
  position: relative;
  margin-bottom: 40px;
}

.top-row:after {
  content: '';
  display: table;
  clear: both;
}
.top-row > div {
  float: left;
  width: 48%;
  margin-right: 4%;
}
.top-row > div:last-child {
  margin: 0;
}

.button {
  border: 0;
  outline: none;
  border-radius: 0;
  padding: 15px 0;
  font-size: 2rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  background: #1ab188;
  color: #ffffff;
  -webkit-transition: all 0.5s ease;
  transition: all 0.5s ease;
  -webkit-appearance: none;
}
.button:hover,
.button:focus {
  background: #179b77;
}

.button-block {
  display: block;
  width: 100%;
}

.forgot {
  margin-top: -20px;
  text-align: right;
}
.navbar-nav li:hover>ul.dropdown-menu {
	display: block;
}

.dropdown-submenu {
	position: relative;
}

.dropdown-submenu>.dropdown-menu {
	top: 0;
	left: 100%;
	margin-top: -6px;
}

/* rotate caret on hover */
.dropdown-menu>li>a:hover:after {
	text-decoration: underline;
	transform: rotate(-90deg);
}
</style>
<body>
<!-- partial:index.partial.html -->
<header>
		<!-- Fixed navbar -->
		<nav class="navbar navbar-expand-md navbar-dark bg-dark">
			<a class="navbar-brand" href="<%=request.getContextPath()%>/"><strong>Bizcom</strong></a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarCollapse" aria-controls="navbarCollapse"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarCollapse">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="<%=request.getContextPath()%>/"
						id="navbarDropdownMenuLink" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> <span
							class="text-white"><strong>RECEIVING</strong></span>
					</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/pre_alert"> <span
									class="text-black"><strong>PRE-ALERT</strong></span>
							</a></li>
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/searchitem"><span
									class="text-black"><strong>Physical Receiving</strong></span></a></li>
							<li class="dropdown-submenu"><a
								class="dropdown-item dropdown-toggle" href="#"><span
									class="text-black"><strong>Report</strong></span></a>
								<ul class="dropdown-menu">
									<li><a class="dropdown-item"
										href="<%=request.getContextPath()%>/shortitem">Short Item</a></li>
									<li><a class="dropdown-item"
										href="<%=request.getContextPath()%>/extraitem">Extra Item</a></li>
									<li><a class="dropdown-item"
										href="<%=request.getContextPath()%>/incorrectitem">Incorrect
											Item</a></li>
								</ul></li>
						</ul></li>

				



				</ul>
				<%
					if (request.getSession().getAttribute("username") == null) {
						request.setAttribute("setHiddenSignOut", "hidden");
					} else {
						request.setAttribute("setHiddenSignOut", "show");
					}
				%>

				<form class="form-inline mt-2 mt-md-0"
					action="<%=request.getContextPath()%>/signout" method="GET">
					<button class="btn btn-danger my-2 my-sm-0" type="submit"
						${setHiddenSignOut}>
						<strong>SIGN OUT</strong>
					</button>
				</form>
			</div>
		</nav>
	</header>
		<div class="form">		      
      <ul class="tab-group">
        <li class="tab active"><a href="#Manger">Manager</a></li>
        <li class="tab"><a href="#Employee">Employee</a></li>
      </ul>
      
      <div class="tab-content">
        <div id="Manger">   
<!--
  -- ──────────────────────────────────────────────────────────────────────────────  ──────────
  -- *  :::::: S I G N   U P   F O R M   M A N A G E : :  :   :    :     :        :          :
  -- ────────────────────────────────────────────────────────────────────────────────────────
  -->
          <h1>USE FOR MANAGER ONLY!</h1>
          
          <form name="manger" action="<%=request.getContextPath()%>/signup" method="post">
          <input type="hidden" value="manager" name="roles" />
          <div class="top-row">
            <div class="field-wrap">
              <label>
                First Name<span class="req">*</span>
              </label>
              <input type="text" name="firstName1" id="firstName1" required autocomplete="off" />
            </div>
        
            <div class="field-wrap">
              <label>
                Last Name<span class="req">*</span>
              </label>
              <input type="text"name="lastName1" id="lastName1" required autocomplete="off"/>
            </div>
          </div>
		<div class="field-wrap">	
            <label>
              Enter Manager Code<span class="req">*</span>
            </label>
            <input type="password" name="manageCode1"  id="manageCode1" required autocomplete="off"/>
          </div>
          <div class="field-wrap">
            <label>
              Employee ID<span class="req">*</span>
            </label>
            <input type="text"required autocomplete="off" name="employeeID1" id="employeeID1"/>
          </div>
          
          <div class="field-wrap">
            <label>
              Set A Password<span class="req">*</span>
            </label>
            <input type="password"required autocomplete="off" id="password1"  name="password1"/>
          </div>
          
           <div class="field-wrap">
            <label>
              Confirm Password<span class="req">*</span>
            </label>
            <input type="password"required autocomplete="off" id="confirm_password1"  name="confirm_password1"/>
          </div>
          <input type="submit" class="button button-block" id="submit1" value="Sign Up"/>        
          </form>

        </div>
        
        <div id="Employee">   
         <form name="employee" action="<%=request.getContextPath()%>/signup" method="post">
         <input type="hidden" value="employee" name="roles" />
          <div class="top-row">
            <div class="field-wrap">
              <label>
                First Name<span class="req">*</span>
              </label>
              <input type="text" name="firstName2" id="firstName2" required autocomplete="off" />
            </div>
        
            <div class="field-wrap">
              <label>
                Last Name<span class="req">*</span>
              </label>
              <input name="lastName2" id="lastName2" type="text"required autocomplete="off"/>
            </div>
          </div>

          <div class="field-wrap">
            <label>
              Employee ID<span class="req">*</span>
            </label>
            <input type="text"required autocomplete="off" name="employeeID2" id="employeeID2"/>
          </div>
          
            <div class="field-wrap">
            <label>
              Set A Password<span class="req">*</span>
            </label>
            <input type="password"required autocomplete="off" id="password2"  name="password2"/>
          </div>
          
           <div class="field-wrap">
            <label>
              Confirm Password<span class="req">*</span>
            </label>
            <input type="password"required autocomplete="off" id="confirm_password2" name="confirm_password2"/>
          </div>
          
          <input type="submit" class="button button-block" id="submit2" value="Sign Up"/>  
</form>
        </div>
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->
<!-- partial -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script>
var password1 = document.getElementById("password1");
var confirm_password1 = document.getElementById("confirm_password1");
var password2 = document.getElementById("password2");
var confirm_password2 = document.getElementById("confirm_password2");
var submit1 = document.getElementById("submit1");
var submit2 = document.getElementById("submit2");


var manageCode1 = document.getElementById("manageCode1");
var firstName1 = document.getElementById("firstName1");
var lastName1 = document.getElementById("lastName1");
var employeeID1 = document.getElementById("employeeID1");
var password1 =document.getElementById("password1");
var confirm_password1 = document.getElementById("confirm_password1");

var firstName2 = document.getElementById("firstName2");
var lastName2 = document.getElementById("lastName2");
var employeeID2 = document.getElementById("employeeID2");
var password2 = document.getElementById("password2");
var confirm_password2 = document.getElementById("confirm_password2");
console.log("My map" );


var setAtribute = (data = {}) =>{
	
	if (data.hasAttribute("firstName1")) {
		firstName1.value = data.firstName1;
		lastName1.value = data.lastName1;
		employeeID1.value = data.employeeID1;
		password1.value = data.password1;
		confirm_password1.value = data.password1;
	}
	
}
setAtribute(${jsonMap});

submit1.onclick = () => {
	
	if(confirm_password1.value === "" || manageCode1.value ==="" || firstName1.value==="" || lastName1.value==="" || employeeID1.value==="" || password1.value===""){
		alert("All information must be filled out");
		return false;
	}
	validatePassword(password1,confirm_password1);
}

submit2.onclick = () => {
	console.log("sibmit");
	console.log(confirm_password2.value);
	if(confirm_password2.value === "" || firstName2.value==="" || lastName2.value==="" || employeeID2.value==="" || password2.value===""){
	alert("All information must be filled out");
	return false;
	}	
	validatePassword(password2,confirm_password2);
}

function validatePassword(password,confirm_password){
if(password.value != confirm_password.value) {
  confirm_password.setCustomValidity("Passwords Don't Match");
  return false;
} else {
  confirm_password.setCustomValidity('');
  return true;
}
}



$('.form').find('input, textarea').on('keyup blur focus', function (e) {
	  
	  var $this = $(this),
	      label = $this.prev('label');

		  if (e.type === 'keyup') {
				if ($this.val() === '') {
	          label.removeClass('active highlight');
	        } else {
	          label.addClass('active highlight');
	        }
	    } else if (e.type === 'blur') {
	    	if( $this.val() === '' ) {
	    		label.removeClass('active highlight'); 
				} else {
			    label.removeClass('highlight');   
				}   
	    } else if (e.type === 'focus') {
	      
	      if( $this.val() === '' ) {
	    		label.removeClass('highlight'); 
				} 
	      else if( $this.val() !== '' ) {
			    label.addClass('highlight');
				}
	    }

	});

	$('.tab a').on('click', function (e) {
	  
	  e.preventDefault();
	  
	  $(this).parent().addClass('active');
	  $(this).parent().siblings().removeClass('active');
	  
	  target = $(this).attr('href');

	  $('.tab-content > div').not(target).hide();
	  
	  $(target).fadeIn(600);
	  
	});
</script>
</body>
</html>