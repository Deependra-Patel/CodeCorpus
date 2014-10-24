package register;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RegisterBackend {
	
	String[] makeProblemArray(String handle){
		String url = "http://www.codechef.com/users/"+handle;
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements tables = doc.select("table");
			Element tableP = tables.get(3);
			Element ltd = tableP.select("tr").last().select("td").last();
			String[] arr = ltd.select("a").text().split(" ");
			return arr;			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private String insertIntoUsers(String name, String handle, String email, String passwd, String insti, String dob, String region){
		JdbcSetup jd;
		String message = "";
		try {
			jd = new JdbcSetup();
			//Statement st = jd.st;
			PreparedStatement pstmt = null; 
			try {
				pstmt = jd.conn1.prepareStatement("insert into users( handle, passwd, rank, role, personal) values(?,?,?,?, ROW(?,?,?,?,?))");
			      pstmt.setString(1, handle);
			      pstmt.setString(2, passwd);
			      pstmt.setInt(3,-1);
			      pstmt.setBoolean(4, false);
			      pstmt.setString(5, name);
			      pstmt.setDate(6, java.sql.Date.valueOf(dob));
			      pstmt.setString(7, region);
			      pstmt.setString(8, email);	
			      pstmt.setString(9, insti);			      
			      pstmt.executeUpdate();
			      
			      Statement st = jd.st;
			      ResultSet rs =  st.executeQuery("SELECT lastval()");
			      int userid = -1;
			      if(rs.next()){
			    	 userid= rs.getInt(1);
			      }
			      String insert = "INSERT INTO attempted(userid, code, solved) values ";
			      String arr[] = makeProblemArray(handle);
			      for(String pCode:arr){
			    	  String query=insert + "("+userid+",'"+pCode+"',true)";
				      try{
				    	  st.executeQuery(query);
				      }
				      catch (Exception e) {
						System.out.println("Error inserting in attempted. query: "+query);
				      }
			      }

			} catch (SQLException e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
				message = "Error inserting to user.";
			}						
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			message = "Error connecting to database";
			e.printStackTrace();
		}		
		
		return message;
	}
	void registerUser(String name, String handle, String email, String passwd, String insti, String dob, String region){
		insertIntoUsers(name, handle, email, passwd, insti, dob, region);
	}
}
