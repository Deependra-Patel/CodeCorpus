<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/bootstrap.min.css" rel="stylesheet"/>
<link href="./css/style.css" rel="stylesheet"/>
</head>
<body>
	<div style="position:fixed;left:42%;z-index:20;">
		<img alt="" src="./images/logo.jpg">
	</div>
	<div class="col-lg-6 col-lg-offset-3 register_form"> 
		<h3><center>LOGIN</center></h3>
		If not registered then <a href="./register.jsp">Register</a><hr>
	<form method="POST" action="login" >
	    <table>
	        <tr>   
	            <td><label for="inputEmail">Codechef handle/username</label></td>
	            <td><input name="handle" class="form-control" id="name" placeholder="Codechef handle"></td>
	        </tr>
	        
	        <tr>
	            <td><label for="inputPassword">Password</label></td>
	            <td><input name="passwd" type="password" class="form-control" id="inputPassword" placeholder="Password"></td>
	        </tr> 	              	        
	        	<td style="align:center;"><button type="submit" class="btn btn-primary">Login</button></td>
	        </tr>
	     </table>
	</form>
	</div>
</body>
</html>