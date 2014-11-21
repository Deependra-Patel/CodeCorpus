package search;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hellosldjfhla");
		String tag = request.getParameter("tag");
		SearchBackend sb = new SearchBackend();
		ArrayList<ArrayList<String>> problemDetails = new ArrayList<ArrayList<String>>();
		problemDetails = sb.searchProblemsWithTags(tag);
		
		try
		{
		
		request.setAttribute("problemDetails", problemDetails);
		request.setAttribute("searchWhat", "problems");

		//String destination="/searchResult.jsp"; 
		
		getServletConfig().getServletContext().getRequestDispatcher("/searchResult.jsp").forward(request,response);
		//requestDispatcher.findForward("/your/display/jsp");
		//RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
		//rd.forward(request, response);

		}

		catch (Exception e)
		{
		throw new ServletException(e.toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String keyword = request.getParameter("keywords");
		
		String searchWhat = request.getParameter("options");
		System.out.println("hello123 " + searchWhat);
		SearchBackend sb = new SearchBackend();
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> usersByName = new ArrayList<String>();
		
		ArrayList<ArrayList<String>> problemDetails = new ArrayList<ArrayList<String>>(); 
		if(searchWhat.equals("handle")){
			if(!(keyword.equals(null) || keyword.equals(""))){
				al = sb.searchUserById(keyword);
				System.out.println("yoyoyo" + al.size());
			}
		}
		else if(searchWhat.equals("problems")){
			if(!(keyword.equals(null) || keyword.equals(""))){
				problemDetails = sb.searchProblems(keyword);
				System.out.println("size: ");
				System.out.println("yoyoyoproblsize " + problemDetails.size());
				System.out.println("done");
			}
		}
		else if(searchWhat.equals("nameUser")){
			if(!(keyword.equals(null) || keyword.equals(""))){
				usersByName = sb.searchUserByName(keyword);
				System.out.println("size: ");
				System.out.println("YOYOYOnamesize" + usersByName.size());
				System.out.println("done");
			}
		}
		System.out.println(searchWhat);
		System.out.println(usersByName);
		
		
		try
		{
		
		request.setAttribute("problemDetails", problemDetails);
		request.setAttribute("usersByName", usersByName);
		request.setAttribute("arrayList", al);
		request.setAttribute("searchWhat", searchWhat);

		//String destination="/searchResult.jsp"; 
		
		getServletConfig().getServletContext().getRequestDispatcher("/searchResult.jsp").forward(request,response);
		//requestDispatcher.findForward("/your/display/jsp");
		//RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
		//rd.forward(request, response);

		}

		catch (Exception e)
		{
		throw new ServletException(e.toString());
		} 
		
	}

}
