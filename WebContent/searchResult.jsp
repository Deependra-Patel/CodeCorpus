<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/bootstrap.min.css" rel="stylesheet"/>
<link href="./css/style.css" rel="stylesheet"/>
<style>
.table-responsive tr > td {
	background-color : white;
	}
.table-responsive th {
	background-color : white;
	}

</style>
</head>
<body>
<% 
ArrayList <String> al=new ArrayList <String>();

if (request.getAttribute("arrayList") != null) {
	al = (ArrayList ) request.getAttribute("arrayList");
}

ArrayList<ArrayList<String>> problemDetails = new ArrayList<ArrayList<String> >();
if (request.getAttribute("problemDetails") != null) {
	problemDetails = (ArrayList ) request.getAttribute("problemDetails");
	System.out.println("problemdetailssize " + problemDetails.size());
}
System.out.println(request.getAttribute("usersByName"));
ArrayList <String> usersByName=new ArrayList <String>();
if (request.getAttribute("usersByName") != null) {
	usersByName = (ArrayList ) request.getAttribute("usersByName");
	System.out.println("sizemaxx" + usersByName.size());
}

ArrayList <String> tagsByName=new ArrayList <String>();
if (request.getAttribute("tagsByName") != null) {
	tagsByName = (ArrayList ) request.getAttribute("tagsByName");
	System.out.println("sizemaxx" + tagsByName.size());
}

String searchWhat = new String();
searchWhat = (String) request.getAttribute("searchWhat");
if(searchWhat.equals("handle") || searchWhat.equals("nameUser")){
	out.println("<div class = 'col-lg-8 col-lg-offset-2'>");
	out.println("<center>");
	if((al.size() == 0 && searchWhat.equals("handle")) || (usersByName.size() == 0 && searchWhat.equals("nameUser"))){
		out.println("<h3>Sorry! No users matched your request</h3>");
		
	}
	else{
		
		out.println("<h3>Users that matched your query</h3><br>");
		out.println("<table class = 'table table-bordered table-responsive'>");
		out.println("<th>Handle</th><th>Name</th>");
		Iterator<String> iterator;
		if(searchWhat.equals("handle")){
			iterator = al.iterator();
		}
		else iterator = usersByName.iterator();
		int i = 1;
		while (iterator.hasNext()){
			out.println("<tr>");
			String word = iterator.next();
			String parts[] = word.split("\\;");
			String handle = parts[0];
			String name = parts[1];
			
			if(name.equals("null")) name = "Not Available";
			out.println("<td><a target='_blank' href = './profile?handle=" + handle + "'>" + handle + "</a></td>");
			out.println("<td>"+name+"</td>");
			i++;
			out.println("</tr>");
		}
		out.println("<br>");
		out.println("</table>");
	}
	out.println("</div>");
}
else if(searchWhat.equals("problems")){
	
	out.println("<div class = 'col-lg-8 col-lg-offset-2'>");
	out.println("<a href='./home'>Home</a>"); 
	if(problemDetails.size() == 0){
		out.println("<center><h3>Sorry! No problems matched your query</h3><br></center>");
	}
	else{
		out.println("<h3>Problems that matched your query</h3><br>");
		
		Iterator<ArrayList<String>> iterator = problemDetails.iterator();
		//ArrayList<ArrayList<String>> tagList = new ArrayList<ArrayList<String>>();
		int i = 1;
		;
		out.println("<table class = 'table table-bordered table-responsive'>");
		out.println("<th>Problem Code</th>");
		out.println("<th>Problem Name</th>");
		out.println("<th>Tags</th>");
		String prevCode = "";
		String prevTags = "";
		String prevName = "";
		String prevLink = "";
		String code = new String();
		String name = new String();
		String link = new String();
		String tag = new String();
		ArrayList<String> tagList = new ArrayList<String>();
		while(iterator.hasNext()){
			System.out.println("hello");
			ArrayList<String> current = iterator.next();
			Iterator<String> iterator1 = current.iterator();
			code = iterator1.next();
			name = iterator1.next();
			link = iterator1.next();
			tag = iterator1.next();
			if(!code.equals(prevCode) && (!(prevCode.equals(null) || prevCode.equals("")))){
				out.println("<tr>");
				out.println("<td>" + prevCode + "</td>");
				out.println("<td><a href = '" + prevLink + "' class = " + "'btn btn-link'>" + prevName + "</a></td>");
				//out.println("<td>" + prevLink + "</td>");
				//out.println("<td>" + prevTags + "</td>");
				out.println("<td>");
				Iterator<String> tagIter = tagList.iterator();
				while(tagIter.hasNext()){
					
					String tempTag = tagIter.next();
					out.println("<a href = './tagProblems?tag=" + tempTag + "' class = 'btn btn-primary btn-xs'>" + tempTag + " </a>" );
				}
				out.println("</td>");
				out.println("</tr>");
				prevTags = "";
				tagList = new ArrayList<String>();
			}
			else{
				prevTags = prevTags + "," + tag;
				
			}
			prevCode = code;
			prevName = name;
			prevLink = link;
			tagList.add(tag);
			i++;
			
		}
		out.println("<tr>");
		out.println("<td>" + prevCode + "</td>");
		out.println("<td><a href = '" + prevLink + "' class = " + "'btn btn-link'>" + prevName + "</a></td>");
		//out.println("<td>" + prevLink + "</td>");
		//out.println("<td>" + prevTags + "</td>");
		out.println("<td>");
		Iterator<String> tagIter = tagList.iterator();
		while(tagIter.hasNext()){
			String tempTag = tagIter.next();
			out.println("<a href = './tagProblems?tag=" + tempTag + "' class = 'btn btn-primary btn-xs'>" + tempTag + " </a>" );
		}
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
	}
	out.println("</center>");
	out.println("</div>");
	
}
else if(searchWhat.equals("tags")){
	out.println("<div class = 'col-lg-8 col-lg-offset-2'>");
	out.println("<center>");
	if((tagsByName.size() == 0 && searchWhat.equals("tags"))){
		out.println("<h3>Sorry! No tags matched your request</h3>");
		
	}
	else{
		
		out.println("<h3>Tags that matched your query</h3><br>");
		out.println("<table class = 'table table-bordered table-responsive'>");
		out.println("<th>Tag Name</th>");
		Iterator<String> iterator;
		iterator = tagsByName.iterator();
		
		int i = 1;
		while (iterator.hasNext()){
			out.println("<tr>");
			String tag = iterator.next();
			
			
			out.println("<td>");
			out.println("<a href='./tagProblems?tag="+tag+"'>"+tag+"</a>");
			out.println("</td>");
			
			i++;
			out.println("</tr>");
		}
		out.println("<br>");
		out.println("</table>");
	}
	out.println("</div>");
}
%>
</body>
</html>