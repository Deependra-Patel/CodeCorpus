<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="search.autoComplete"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="./css/style.css" rel="stylesheet"/>
    <!-- CSS Global Compulsory -->
    <link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">

    <!-- CSS Implementing Plugins -->
    <link rel="stylesheet" href="assets/plugins/line-icons/line-icons.css">
    <link rel="stylesheet" href="assets/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/plugins/revolution-slider/rs-plugin/css/settings.css" type="text/css" media="screen">

    <!-- CSS Customization -->
    <link rel="stylesheet" href="assets/css/custom.css">
<link href="./css/form.css" rel="stylesheet"/>    
<script src="./js/jquery.js"></script>
<script src="./bootstrap/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" language="javascript">
	$(document).ready(function() {
	   $("#submit").click(function(event){
		   var type = $("#searchOptions").val();
		   var word = document.getElementById("searchBox").value;
	       $("#results").load('./search', {"options":type, "keywords": word} );
	   });
	});
</script>
<style>
	.tt-dropdown-menu{
		background:white;
		width:100%;
		border-color:black;
		border:2px;
	}
	#searchBox{
		width:300px;
		height:35px;
	}
	.form-wrapper{
		width:600px;
	}
</style>
</head>
<body>
		<jsp:include page="./header.jsp" />
		<div class="col-lg-10 col-lg-offset-1">
<div class="form-search">
<div class="input-group">
  
<div class="form-wrapper" style="margin-bottom:300px;">
	<ul>
	    <li style="display:inline;"><input id="searchBox" type="text" placeholder="Search for CSS3, HTML5, jQuery ..." required></li>
	    <li style="display:inline;"><select style="width:100px;" class="form-control" id="searchOptions" name="options" onChange="selectCall()">
		  <option value="handle">Users</option>
		  <option value="problems">Problems</option>
		  <option value="nameUser">Name of the USer</option>
		  <option value="tags">Tags</option>
		 </select>
		 </li>
	    <li style="display:inline;"><input type="submit" value="go" id="submit"> </li>
    </ul>
</div>
	
  </div></div></div>	<!-- 
	<input id = "searchBox"  placeholder="Type your query" name = "keywords" type = "text"> -->

<div style="margin-top:120px;" id="results"></div>
</body>
	<script src="./js/jquery.js"></script>
		<script type="text/javascript" src="./js/typeahead.js"></script>
       	<script type="text/javascript">
       	<% 
       	
       	ArrayList<String> handles = new ArrayList<String>();
       	autoComplete auto = new autoComplete();
       	handles = auto.autoCompleteFunc("handle");
       	
       	%>
		
       	
       	var states = <%= auto.toJavascriptArray(handles) %>;
        //var states="<%=handles%>";
        ////alert(states);
        
        
        function selectCall(){
       		var e = document.getElementById("searchOptions");
            var temp = e.options[e.selectedIndex].value;
            //alert(temp);
            if(temp == "handle"){
		
       	
				//alert(temp);
				//var e = document.getElementById("searchOptions");
	            //strUser1 = e.options[e.selectedIndex].value;
				
				<% 
		       	String strUser1 = "handle";
		       	//if(request.getParameter("option") != null) strUser = request.getParameter("option");
		       	System.out.println("yo "+ strUser1);
		       	ArrayList<String> handles1 = new ArrayList<String>();
		       	
		       	
		       	
		       	autoComplete auto1 = new autoComplete();
		       	handles1 = auto1.autoCompleteFunc(strUser1);
		       	System.out.println(handles1);
		       	%>

		       	states = <%= auto1.toJavascriptArray(handles1) %>;
		       	$('#searchBox').typeahead('destroy');
		       	$('#searchBox').typeahead({
		      	  hint: false,
		      	  highlight: true,
		      	  minLength: 1
		      	},
		      	{
		      	  name: 'states',
		      	  displayKey: 'value',
		      	  source: substringMatcher(states)
		      	});
            }
            else if(temp == "problems"){
        		
               	
            	//alert(temp);;
				//var e = document.getElementById("searchOptions");
	            //strUser1 = e.options[e.selectedIndex].value;
				
				<% 
		       	String strUser2 = "problems";
		       	//if(request.getParameter("option") != null) strUser = request.getParameter("option");
		       	System.out.println("yo "+ strUser2);
		       	ArrayList<String> handles2 = new ArrayList<String>();
		       	
		       	
		       	
		       	autoComplete auto2 = new autoComplete();
		       	handles2 = auto2.autoCompleteFunc(strUser2);
		       	System.out.println(handles2);
		       	%>

		       	states = <%= auto2.toJavascriptArray(handles2) %>;

		       	$('#searchBox').typeahead('destroy');
		       	$('#searchBox').typeahead({
		      	  hint: false,
		      	  highlight: true,
		      	  minLength: 1
		      	},
		      	{
		      	  name: 'states',
		      	  displayKey: 'value',
		      	  source: substringMatcher(states)
		      	});
		      	
		       //	var autocomplete = $('#searchBox').typeahead();
		       //	autocomplete.data('typeahead').source = states; //where newSource is your own array
            }
			else if(temp == "nameUser"){
        		
               	
				//alert(temp);
				//var e = document.getElementById("searchOptions");
	            //strUser1 = e.options[e.selectedIndex].value;
				
				<% 
		       	String strUser3 = "nameUser";
		       	//if(request.getParameter("option") != null) strUser = request.getParameter("option");
		       	System.out.println("yo "+ strUser3);
		       	ArrayList<String> handles3 = new ArrayList<String>();
		       	
		       	
		       	
		       	autoComplete auto3 = new autoComplete();
		       	handles3 = auto3.autoCompleteFunc(strUser3);
		       	System.out.println(handles3);
		       	%>

		       	states = <%= auto3.toJavascriptArray(handles3) %>;
		       	$('#searchBox').typeahead('destroy');
		       	$('#searchBox').typeahead({
		      	  hint: false,
		      	  highlight: true,
		      	  minLength: 1
		      	},
		      	{
		      	  name: 'states',
		      	  displayKey: 'value',
		      	  source: substringMatcher(states)
		      	});
		       	
            }
			else if(temp == "tags"){
        		
               	//alert("hi");
				//alert(temp+"yoyo");
				//var e = document.getElementById("searchOptions");
	            //strUser1 = e.options[e.selectedIndex].value;
				
				<% 
		       	String strUser4 = "tags";
		       	//if(request.getParameter("option") != null) strUser = request.getParameter("option");
		       	System.out.println("yo "+ strUser4);
		       	ArrayList<String> handles4 = new ArrayList<String>();
		       	
		       	
		       	
		       	autoComplete auto4 = new autoComplete();
		       	handles4 = auto4.autoCompleteFunc(strUser4);
		       	System.out.println(handles3);
		       	%>

		       	states = <%= auto4.toJavascriptArray(handles4) %>;
		       	$('#searchBox').typeahead('destroy');
		       	$('#searchBox').typeahead({
		      	  hint: false,
		      	  highlight: true,
		      	  minLength: 1
		      	},
		      	{
		      	  name: 'states',
		      	  displayKey: 'value',
		      	  source: substringMatcher(states)
		      	});
		       	
            }
	       		
       		
       		
       		
       		
       		//alert(states);
       	} ;
        
        
        
        
        
		var substringMatcher = function(strs) {
  			return function findMatches(q, cb) {
    			var matches, substrRegex;
 
			    // an array that will be populated with substring matches
			    matches = [];
			 
			    // regex used to determine if a string contains the substring `q`
			    substrRegex = new RegExp(q, 'i');
			 
			    // iterate through the pool of strings and for any string that
			    // contains the substring `q`, add it to the `matches` array
			    var count = 0;
			    var length = 10;
			    $.each(strs, function(i, str) {
			        
			      if (substrRegex.test(str) && count<length) {
			        // the typeahead jQuery plugin expects suggestions to a
			        // JavaScript object, refer to typeahead docs for more info
			        matches.push({ value: str });
			        count++;
			      }
			    });
 
    			cb(matches);
  			};
		};
		 /*
		var states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
		  'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
		  'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
		  'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
		  'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
		  'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
		  'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
		  'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
		  'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
		];*/
		//alert(states);
 
	$('#searchBox').typeahead({
	  hint: true,
	  highlight: true,
	  minLength: 1
	},
	{
	  name: 'states',
	  displayKey: 'value',
	  source: substringMatcher(states)
	});

		</script>
</html>