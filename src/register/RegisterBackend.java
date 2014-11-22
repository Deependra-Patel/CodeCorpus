package register;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class UserDetails{
	ArrayList<String> personalDetail = new ArrayList<String>();
	ArrayList<String> interests = new ArrayList<String>();	
	ArrayList<String> following = new ArrayList<String>();		
} 
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
			      jd.destroy();

			} catch (SQLException e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("date");
				message = "Error inserting to user.";
			}						
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			message = "Error connecting to database";
			e.printStackTrace();
		}		
		
		return message;
	}
	String registerUser(String name, String handle, String email, String passwd, String insti, String dob, String region){
		return insertIntoUsers(name, handle, email, passwd, insti, dob, region);
	}
	UserDetails userDetails(String handle){
		try {
			int userid;
			UserDetails ud = new UserDetails();
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT userid, (personal).name, (personal).dob, (personal).region, (personal).email_id, (personal).institution FROM users WHERE handle=?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1, handle);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				userid = rs.getInt(1);
				//ud.personalDetail.add(String.valueOf(rs.getInt(1)));
				//ud.personalDetail.add(rs.getString(2));
				//ud.personalDetail.add(String.valueOf(rs.getInt(3)));
				ud.personalDetail.add(rs.getString(2));
				ud.personalDetail.add(rs.getDate(3).toString());
				ud.personalDetail.add(rs.getString(4));
				ud.personalDetail.add(rs.getString(5));
				ud.personalDetail.add(rs.getString(6));
			}
			else return null;

			selectSql = "SELECT tags.name from users NATURAL JOIN users_interests INNER JOIN tags ON users_interests.tagid=tags.tagid WHERE handle=?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1, handle);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ud.interests.add(rs.getString(1));
			}
			System.out.println("userid"+userid);
			selectSql = "SELECT users.handle from followers INNER JOIN users ON users.userid=followers.followed where followers.follower = ?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setInt(1, userid);
			rs = pstmt.executeQuery();
			jd.destroy();
			while(rs.next()){
				ud.following.add(rs.getString(1));
			}	
			return ud;
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			System.out.println("Error with servlet.");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			System.out.println("Error with sql query");
			return null;
		}
	}
	
	
	//////Update queries
	String updatePersonal(String handle, String name, String region, String passwd, String insti, String email){
		try{
			JdbcSetup jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String updateSql = "";
			String message = "";
			if(passwd.equals("")){
				updateSql = "UPDATE  users set personal.name = ?, personal.region = ?, personal.institution = ?, personal.email_id = ?  WHERE handle=?";
				pstmt = jd.conn1.prepareStatement(updateSql);
				pstmt.setString(1, name);
				pstmt.setString(2, region);
				pstmt.setString(3, insti);
				pstmt.setString(4, email);
				pstmt.setString(5, handle);
				pstmt.executeUpdate();	
				message = "Successfully updated personal info.";
			}else {
				updateSql = "UPDATE  users set passwd = ?  WHERE handle=?";
				pstmt = jd.conn1.prepareStatement(updateSql);
				pstmt.setString(1, passwd);
				pstmt.setString(2, handle);
				pstmt.executeUpdate();	
				message = "Successfully changed password.";				
			}
			return message;
		}
		catch(SQLException e){
			e.printStackTrace();
			return "Error Updating data.";
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error connecting database";
		}
		
	}
	String updateInterests(int userid, ArrayList<String> interests){
		try{
			JdbcSetup jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String removeSql = "DELETE from users_interests where userid = ?";
			try{
				pstmt = jd.conn1.prepareStatement(removeSql);
				pstmt.setInt(1, userid);
				pstmt.executeUpdate();
			}
			catch(SQLException e){
				e.printStackTrace();
				return "Error updating data";
			}
			
			String selectSql = "SELECT tagid  from tags where name = ?";
			boolean allDone = true;
			for(String interest:interests){
				try {
					pstmt = jd.conn1.prepareStatement(selectSql);
					pstmt.setString(1, interest);
					ResultSet rs = pstmt.executeQuery();
					int tagid;
					if(rs.next()){
						tagid = rs.getInt(1);
						String insertSql = "INSERT into users_interests values(?,?)";
						pstmt = jd.conn1.prepareStatement(insertSql);
						pstmt.setInt(1, userid);
						pstmt.setInt(2, tagid);
						try{ 
							pstmt.executeUpdate();
						}
						catch(SQLException e){
							e.printStackTrace();
							allDone = false;
						}
					}
					else {
						allDone = false;
						System.out.println(selectSql+interest);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "Some Error";
				}
			}
			if(allDone){
				return "Successfully updated interests.";
			}
			else return "Some interests might not be updated";
		}
		catch(ServletException e){
			e.printStackTrace();
			return "Error connecting to database";
		}
	}
}
