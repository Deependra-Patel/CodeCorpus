<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/bootstrap.min.css" rel="stylesheet"/>
<link href="./css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="./js/jquery.js"></script>
</head>

<body>
	<div style="position:fixed;left:42%;z-index:20;">
		<img alt="" src="./images/logo.jpg">
	</div>
	<div class="col-lg-6 col-lg-offset-3 register_form"> 
		<h3><center>LOGIN</center></h3>
		If not registered then <a href="./register.jsp">Register</a><hr>
	<% String message="";
		if(request.getAttribute("message")!=null)
			message = (String)request.getAttribute("message");
		else if(request.getParameter("message")!=null)
			message = request.getParameter("message");
	if(!message.equals("")){ %>
	    <div class="alert alert-danger alert-error">
	        <a href="#" class="close" data-dismiss="alert">&times;</a>
		    <strong></strong> <%=message %>
		</div>
	<%} %>
	<%String err = request.getParameter("err"); 
		if(err!=null && err.equals("1")){ %>
	    <div class="alert alert-danger alert-error">
	        <a href="#" class="close" data-dismiss="alert">&times;</a>
		    <strong>Error!</strong>Please Signin first!
		</div> 
	<%} else if(err!=null && err.equals("3")){ %>
	    <div class="alert alert-success">
	        <a href="#" class="close" data-dismiss="alert">&times;</a>
		    <strong>Succesfully Logged Out!</strong>
		</div>  
	<%} %>	
	<form method="POST"  id="login" action="login">
	    <table> 
	        <tr>   
	            <td><label for="inputEmail">Codechef handle/username</label></td>
	            <td><input name="handle" class="form-control" id="name" placeholder="Codechef handle"></td>
	        </tr>
	        
	        <tr>
	            <td><label for="inputPassword">Password</label></td>
	            <td><input name="passwd" type="password" class="form-control" id="inputPassword" placeholder="Password"></td>
	        </tr> 	              	        
	        	<td style="align:center;"><button type="submit" id="submit" class="btn btn-primary">Login</button></td>
	        </tr>
	     </table>
	     <div id="div1"></div>
	</form>
	</div>
<script>
</script>
</body>
</html>