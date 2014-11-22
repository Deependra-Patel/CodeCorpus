package profile;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    } 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if(session.getAttribute("handle")==null){
			response.sendRedirect("./index.jsp?err=1"); 
		}
		else {
			int userid = (Integer) session.getAttribute("userid");
			String handle = (String) session.getAttribute("handle");
			
			String toFollow = request.getParameter("follow");
			String toUnfollow = request.getParameter("unfollow");
			if(toFollow!=null){
				System.out.println("following");
				ProfileBackend obj = new ProfileBackend();
				int foll = Integer.parseInt(toFollow);
				obj.follow(userid, true, foll);
			}
			else if(toUnfollow!=null){
				System.out.println("unfollowing");
				ProfileBackend obj = new ProfileBackend();
				int foll = Integer.parseInt(toUnfollow);
				obj.follow(userid, false, foll);		
			}
			String requestedHandle = request.getParameter("handle");
			if(requestedHandle==null){
				requestedHandle = handle;
			}
			System.out.println("hannnnnnnnnnnnnnn"+requestedHandle);
			ProfileBackend details = new ProfileBackend();
			UserDetails pairs = new UserDetails();
			pairs = details.profileDetails(requestedHandle);
			System.out.println(pairs.personalDetail);
			System.out.println(pairs.interests);
			System.out.println(pairs.followers); 		
			request.setAttribute("details", pairs);
			request.setAttribute("personalDetail", pairs.personalDetail);
			request.setAttribute("problemsAttempted", pairs.problemsAttempted);
			request.setAttribute("problemsSolved", pairs.problemsSolved);
			request.setAttribute("interests", pairs.interests);
			request.setAttribute("followers", pairs.followers);		
			request.getRequestDispatcher("/profile.jsp").forward(request, response);	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
