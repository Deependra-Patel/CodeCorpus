<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<% 
	ArrayList<String> personalDetail = (ArrayList)request.getAttribute("personalDetail");
	ArrayList<String> interests = (ArrayList)request.getAttribute("interests");
	ArrayList<String> following = (ArrayList)request.getAttribute("following");	
	String message = (String)request.getAttribute("message");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Code Corpus</title>
    <link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">

    <!-- CSS Implementing Plugins -->
    <link rel="stylesheet" href="assets/plugins/line-icons/line-icons.css">
    <link rel="stylesheet" href="assets/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/plugins/sky-forms/version-2.0.1/css/custom-sky-forms.css">

    <!-- CSS Theme -->    
    <link rel="stylesheet" href="assets/css/theme-colors/default.css" id="style_color">
    <link rel="stylesheet" href="assets/css/theme-skins/dark.css">

    <!-- CSS Customization -->
    <link rel="stylesheet" href="assets/css/custom.css">
    <script type="text/javascript" src="js/jquery.js"></script>
<script>
var num_interests = <%=interests.size()%>;
function checkPass()
{
    var pass1 = document.getElementById('pass1');
    var pass2 = document.getElementById('pass2');
    var message = document.getElementById('confirmMessage');
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    if(pass1.value == pass2.value){
        pass2.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        message.innerHTML = "Passwords Match!"
    }else{
        pass2.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Passwords Do Not Match!"
    }
} 
function addBox(){
	num_interests++;
	input = $('<div class = "row"><div class="col col-md-6"><label class="input"><input type="text" name="interest'+num_interests+'" placeholder="Interest"></label></div><div class="col col-md-6"><input type="button" class="btn" onclick="$(this).closest(\'.row\').remove();" value="Remove"></div></div>');
	$("#interests").append(input);
}
</script>
</head>

<body>
<jsp:include page="./header.jsp" />
<div class="col-md-6 col-md-offset-3">
              <!-- Reg-Form -->
              <% if(message!=null && !message.equals("")){ %>
              <div class="alert alert-info fade in">
                   <strong>Hey!</strong> <%=message%>
               </div>
              <%} %>
              <form action="accountUpdate" method="post" class="sky-form">
	              <header>Update Problems Solved</header>
	              <input type="hidden" value="updateProblems" name="type">
                  <footer>
                      <button type="submit" class="btn-u">Update Problems Solved</button>
                  </footer>
              </form>
              <br><hr>
              <form action="accountUpdate" method="post" id="sky-form4" class="sky-form">
                  <header>Update Details</header>
              <input type="hidden" value="personal" name="type">
              <fieldset>
                     <div class = "row">
                          <label class="label col col-3">Name</label>
                          <div class="col col-9">
                              <label class="input">
                                 <input type="text" name="name" placeholder="Name" value="<%out.println(personalDetail.get(0)); %>">
                              </label>
                         	</div>
                     </div>
                          <div class = "row">
                              <label class="label col col-3">Region/Country</label>
                              <div class = "col col-9">
                              <label class="input">
                                  <input type="text" name="region" placeholder="Region/Country"  value="<%out.println(personalDetail.get(2)); %>">
                              </label>
  </div>
                          </div>

                          <div class = "row">
                              <label class="label col col-3">Institution</label>
                              <div class = "col col-9">
                              <label class="input">
                                  <input type="text" name="insti" placeholder="Institution"  value="<%out.println(personalDetail.get(4)); %>">
                              </label>
  </div>
                          </div>
                          <div class = "row">
                          <label class="label col col-3">Email address</label>
                          <div class = "col col-9">
                          <label class="input">
                              <i class="icon-append fa fa-envelope"></i>
                              <input type="email" name="email" placeholder="Email address"  value="<%out.println(personalDetail.get(3)); %>"> 
                              <b class="tooltip tooltip-bottom-right">Needed to verify your account</b>
                          </label>
                          </div>
                      </div>								

                  </fieldset>                                                            
                  <footer>
                      <button type="submit" class="btn-u">Update</button>
                  </footer>
                 </form>
                 <form action="accountUpdate" method="post" id="sky-form4" class="sky-form">
                  <header>Change Password</header>
                  <fieldset>                  
                           <input type="hidden" value="password" name = "type">                        
							<section>
                                <label class="input">
                                    <i class="icon-append fa fa-lock"></i>
                                    <input id="pass1" type="password" name="passwd" placeholder="Password" id="password">
                                    <b class="tooltip tooltip-bottom-right">Don't forget your password</b>
                                </label>
                            </section>
                  
                 			<section>
                                <label class="input">
                                    <i class="icon-append fa fa-lock"></i>
                                    <input id="pass2" type="password" name="passwordConfirm" placeholder="Confirm password" onkeyup="checkPass(); return false;">
                                    <b class="tooltip tooltip-bottom-right">Don't forget your password</b>
                                </label><span id="confirmMessage" class="confirmMessage"></span>
                            </section>
                  </fieldset>
                     
                  <footer>
                      <button type="submit" onclick="" class="btn-u">Update</button>
                  </footer>
              </form>  
              <!-- Reg-Form -->
              <form action="" method="post" id="sky-form4" class="sky-form">
              	<input type="hidden" name="type" value="interests">
                  <header>Update Interests</header>
                 
              <fieldset id="interests">
              	<% for(int i=0; i<interests.size(); i++){ String interest = interests.get(i); %>
                     <div class = "row">
                              <div class="col col-md-6"><label class="input">
                                 <input type="text" name="interest<%=(i+1)%>" placeholder="Name" value="<%out.println(interest); %>">
                              </label></div>
                              <div class="col col-md-6"><input type="button" class="btn" onclick="$(this).closest('.row').remove();" value="Remove"></div>

                      </div>	
                  <% }%> 							
                  </fieldset>      
                  <button onclick="addBox();return false;" class="btn">Add</button>	                          
                  <footer>
                      <button type="submit" class="btn-u">Update</button>
                  </footer>
              </form> 
              <form class="sky-form"><header> Following</header> 
              	<table class="table table-hover"><tbody><tr>


              	<%for(String followingHandle:following){ %>
              		<tr><td><a target="_blank" href="./profile?handle=<%=followingHandle%>" class="btn btn-primary btn-xs"><%=followingHandle%> </a></td></tr>
              	<%} %>
              	</tbody>
              	</table>
              </form>                              
              <!-- End Reg-Form -->
          </div>
			                    
</body>
</html>