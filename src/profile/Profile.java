package profile;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    } 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String handle = request.getParameter("handle");
		ProfileBackend details = new ProfileBackend();
		UserDetails pairs = new UserDetails();
		pairs = details.profileDetails(handle);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
