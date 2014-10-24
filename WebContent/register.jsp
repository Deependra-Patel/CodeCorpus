<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
<link href="./css/bootstrap.min.css" rel="stylesheet"/>
<link href="./css/style.css" rel="stylesheet"/>
</head>
<body> 
	<div style="position:fixed;left:42%;z-index:20;">
		<img alt="" src="./images/logo.jpg">
	</div>
	<div class="col-lg-6 col-lg-offset-3 register_form"> 
		<h3><center>REGISTER</center></h3>
		If already registered then <a href="./index.jsp">Login</a><hr>
	    <form method="POST" action="register">
	    <table>
	    	<tr>
	            <td><label for="inputEmail">Name</label></td>
	           	<td><input name="name" class="form-control" id="name" placeholder="name"></td> 
	        </tr>
	        <tr>   
	            <td><label for="inputEmail">Codechef handle</label></td>
	            <td><input name="handle" class="form-control" id="name" placeholder="Codechef handle"></td>
	        </tr>
	        <tr>		        
	            <td><label for="inputEmail">Email</label></td>
	            <td><input name="email" type="email" class="form-control" id="inputEmail" placeholder="Email"></td>
	        </tr>
	        
	        <tr>
	            <td><label for="inputPassword">Password</label></td>
	            <td><input name="passwd" type="password" class="form-control" id="inputPassword" placeholder="Password"></td>
	        </tr>

	        <tr>
	            <td><label for="inputInstitution">Institution</label></td>
	            <td><input name="insti" type="text" class="form-control" id="inputInstitution" placeholder="Institution"></td>
			</tr>

	        <tr>
	            <td><label for="Region(Country)">Region/Country</label></td>
	            <td><input name="region" type="text" class="form-control"placeholder="Region/Country"></td>
	        </tr>

	        <tr>	  
	            <td><label for="Date of Birth">Date of Birth</label></td>
	            <td><input name="dob" type="text" class="form-control"placeholder="YYYY-MM-DD"></td>
	        <tr>	 	              	        
	        	<td style="align:center;"><button type="submit" class="btn btn-primary">Register</button></td>
	        </tr>
	       	</table>
	    </form>
	</div>
</body>
</html>