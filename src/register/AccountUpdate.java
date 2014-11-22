package register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import profile.ProfileBackend;
import updateProblems.scrapeUserProblems;

/**
 * Servlet implementation class AccountUpdate
 */
@WebServlet("/AccountUpdate")
public class AccountUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		if(session.getAttribute("handle")==null){
			response.sendRedirect("./?err=1");
		}
		else {
			String handle = (String) session.getAttribute("handle");
			RegisterBackend getUserDetails = new RegisterBackend();
			UserDetails obj = getUserDetails.userDetails(handle);
	
			request.setAttribute("interests", obj.interests);
			request.setAttribute("following", obj.following);	
			request.setAttribute("personalDetail", obj.personalDetail);
	
			request.getRequestDispatcher("./accountUpdate.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		if(session.getAttribute("handle")==null){
			response.sendRedirect("./?err=1");
		}
		else {
			String handle = (String) session.getAttribute("handle");
			int userid = (Integer) session.getAttribute("userid");
			RegisterBackend obj = new RegisterBackend();
			String type = request.getParameter("type");
			String message = "";
			if(type.equals("updateProblems")){
				scrapeUserProblems scraper = new scrapeUserProblems();
				scraper.updateUserInfo(handle);
			}
			else if(type.equals("personal")){
				String name = request.getParameter("name");
				String region = request.getParameter("region");
				String insti = request.getParameter("insti");
				String email = request.getParameter("email");	
				message = obj.updatePersonal(handle, name, region, "",insti, email);
			}
			else if(type.equals("password")){
				String passwd = request.getParameter("passwd");
				if(!passwd.equals("") || passwd==null){
					message = obj.updatePersonal(handle, "", "", passwd,"", "");
				}
			}
			else if(type.equals("interests")){
			ArrayList<String> interests = new ArrayList<String>();
			   Enumeration<String> paramNames = request.getParameterNames();
			   while(paramNames.hasMoreElements()) {
			      String paramName = (String)paramNames.nextElement();
			      System.out.print(paramName + " : ");
			      String paramValue = request.getParameter(paramName);
			      System.out.println(paramValue);
			      if(!(paramName.equals("type") || paramValue.equals("")))
			    	  interests.add(paramValue);
			   }
			   message = obj.updateInterests(userid, interests);
			}	
			if(type.equals("updateProblems")){
				response.sendRedirect("profile");
			}
			else {
				RegisterBackend getUserDetails = new RegisterBackend();
				UserDetails obj2 = getUserDetails.userDetails(handle);
	
				request.setAttribute("interests", obj2.interests);
				request.setAttribute("following", obj2.following);	
				request.setAttribute("personalDetail", obj2.personalDetail);
				request.setAttribute("message", message);
				request.getRequestDispatcher("./accountUpdate.jsp").forward(request, response);	
			}
		}
	}
}
