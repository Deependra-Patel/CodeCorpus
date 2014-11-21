package updateProblems;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.postgresql.util.PSQLException;

import register.JdbcSetup;

import java.util.*;

/**
 * Servlet implementation class scrapeUserProblems
 */

public class scrapeUserProblems extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JdbcSetup setUp;
	String[] presentCodes;
	String handle;
	int userid;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public scrapeUserProblems() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * create connection
     */
    public void createConnection(){
    	try{
    		setUp = new JdbcSetup();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * destroy connection
     */
    public void destroyConnection(){
    	setUp.destroy();
    }
    
    /**
     * To sort the data
     */
    public void getPresentCodes(){
    	int len = 0;
    	try{
    		String query = "select userid from users where handle = ?";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setString(1, handle);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()){
    			userid = rs.getInt(1);
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return;
    	}
    	
    	try{
    		String query = "select count(*) from attempted where userid = ?";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setInt(1, userid);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()){
    			len = rs.getInt(1);
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return;
    	}
    	
    	//System.out.println("length " + len);
    	presentCodes = new String[len];
    	try{
    		String query = "select code from attempted where userid = ?";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		int pos = 0;
    		pstmt.setInt(1, userid);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()){
    			String tmp = rs.getString(1);
    			presentCodes[pos] = tmp;
    			pos++;
    		}
    		rs.close();
    		Arrays.sort(presentCodes);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	System.out.println(userid + " userid");
    }
    
    /**
     * to insert into attempted list
     */
    public void insertTupleInAttempted(String code){
    	try{
    		String query = "insert into attempted(userid, code, solved) values(?, ?, true)";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setInt(1, userid);
    		pstmt.setString(2, code);
    		pstmt.executeUpdate();
    	}
    	catch(PSQLException e){
    		System.out.println(" -- Exception while entering data -- code is " + code + "-- ");
    		//e.printStackTrace();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		//System.out.println("falg 1");
    		return;
    	}
    }
    
    /**
     * update the rank
     */
    public void updateRankOfUser(int rank){
    	try{
    		String query = "update users set rank = ? where handle = ?";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setInt(1, rank);
    		pstmt.setString(2, handle);
    		pstmt.executeUpdate();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * This functions finds if a problem code is present already
     */
    public boolean isPresent(String code){
    	int l = presentCodes.length;
    	int start = 0, end = l - 1, mid;
    	while(start <= end){
    		mid = (start + end) / 2;
    		int cmp = presentCodes[mid].compareTo(code);
    		if (cmp == 0) return true;
    		else if (cmp > 0) end = mid - 1;
    		else start = mid + 1;
    	}
    	return false;
    }

    /**
     * update the user data by scraping
     */
    public void updateSolvedProblems(Document doc){
    	try{
    		Elements rows =  doc.select("tr");
    		int l = rows.size();
    		for(int i = 0; i < l; i++){
    			String name = rows.get(i).select("td").get(0).text();
    			if (name.equalsIgnoreCase("Problems Successfully Solved:")){
    				System.out.println("flag 1");
    				Elements probs = rows.get(i).select("td").get(1).select("a");
    				int l1 = probs.size();
    				System.out.println("Inserting probs count " +  l1);
    				for (int j = 0; j < l1; j++){
    					String code = probs.get(j).text();
    					if (!isPresent(code)){
    						insertTupleInAttempted(code);
    					}
    				}
    				return;
    			}
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * update rank
     */
    public void updateRank(Document doc){
    	try{
    		if(!String.valueOf(doc.getElementsByClass("rating-table")).isEmpty()){
	    		Element table = doc.getElementsByClass("rating-table").get(0);
	    		Element row = table.select("tr").get(3);
	    		Element col = row.select("td").get(1);
	    		String rank = col.select("a").get(0).text();
	    		String na = "NA";
	    		if (na.equals(rank))
	    			updateRankOfUser(-1);
	    		else updateRankOfUser(Integer.parseInt(rank));
    		}
    		else{
    			System.out.println("The rating table does not exist.");
    			updateRankOfUser(-1);
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * initialize and call proper functions
     */
    
    public void updateUserInfo(String hndle){
    	System.out.println("tryng to connect..");
		createConnection(); 
		System.out.println("connected !");
    	handle = hndle;
    	getPresentCodes(); 
    	System.out.println(handle + " " + userid + " " + presentCodes.length);
    	try{
    		String url = "http://www.codechef.com/users/" + handle;
    		Document doc = Jsoup.connect(url).get();
    		updateSolvedProblems(doc);
    		updateRank(doc);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	destroyConnection();
    	System.out.println("scrapeUser done.");
    }
    
    /*new function local helper*/
    public void updateUserInfoHelper(String hndle){
    	handle = hndle;
    	getPresentCodes(); 
    	System.out.println(handle + " " + userid + " " + presentCodes.length);
    	try{
    		String url = "http://www.codechef.com/users/" + handle;
    		Document doc = Jsoup.connect(url).get();
    		updateSolvedProblems(doc);
    		updateRank(doc);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public String updateAll(){
		System.out.println("tryng to connect..");
		createConnection(); 
		System.out.println("connected !");
		try{
			String query = "select handle from users";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()){
    			String tmp = rs.getString(1);
    			updateUserInfoHelper(tmp);
    		}
    		rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return "Error occured.";
		}
		destroyConnection();  
		return "Succesfully updated all users.";
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*System.out.println("tryng to connect..");
		createConnection(); 
		System.out.println("connected !");*/
		updateUserInfo("teamrush");
		/*try{
			String query = "select handle from users";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()){
    			String tmp = rs.getString(1);
    			updateUserInfo(tmp);
    		}
    		rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		destroyConnection();*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
