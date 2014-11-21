package updateProblems;

import java.net.SocketTimeoutException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import register.JdbcSetup;

import java.util.*;

/**
 * Servlet implementation class scrapeProblems
 */

public class scrapeProblems extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String[] presentCodes;
	HashMap<String, String> tagmap;
	JdbcSetup setUp;
	ArrayList<String> addedCodes;
	ArrayList<String> addedNames;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public scrapeProblems() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * create connection
     * @throws ServletException 
     */
    public int  createConnection(){
    	try{
    		setUp = new JdbcSetup();
    		if (setUp.conn1 == null){
    			System.out.println("Could not connect");
    			return 0;
    		}
    		addedCodes = new ArrayList<String>();
    		addedNames = new ArrayList<String>();
    		addedCodes.add("Aditya");
    		System.out.println("connection set up done");
    		return 1;
    	}
    	catch(ServletException e){
    		System.out.println("Server not connected. :O");
    		e.printStackTrace();
    		return 0;
    	}
    	catch(Exception e){
    		System.out.println("random error ");
    		e.printStackTrace();
    		return 0;
    	}
    }
    
    /**
     * destroy connection
     */
    public void destroyConnection(){
    	setUp.destroy();
    	System.out.println("Connection closed");
    }
    
    /**
     * To sort the data
     */
    public void getPresentCodes(){
    	int len = 0;
    	try{
    		String query = "select count(*) from problems";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()){
    			len = rs.getInt(1);
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	presentCodes = new String[len];
    	try{
    		String query = "select code from problems";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		int pos = 0;
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
    }
    
    /**
     * To get the tag maps
     */
    public void getTagMap(){
    	tagmap = new HashMap<String, String>();
    	try{
    		String query = "select tagid, name from tags";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()){
    			tagmap.put(rs.getString(2), rs.getString(1));
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * To insert one tuple into problems
     */
    public void insertTupleInProblems(String code, String name, String diff, String solved, String acc, String doa){
    	try{
    		String query = "insert into problems(code, name, link, doa, difficulty, statistic) values(?, ?, ?, ?, ?, ROW(?, ?))";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setString(1, code);
    		pstmt.setString(2, name);
    		pstmt.setString(3, "http://www.codechef.com/problems/" + code);
    		pstmt.setDate(4, java.sql.Date.valueOf(doa));
    		pstmt.setString(5, diff);
    		pstmt.setInt(6, Integer.parseInt(solved));
    		pstmt.setDouble(7, Double.parseDouble(acc));
    		pstmt.executeUpdate();
    		addedCodes.add(code);
    		addedNames.add(name);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * Insert into tags
     */
    public void insertTupleInTags(String code, String tagId){
    	try{
    		String query = "insert into problems_tags(code, tagid) values(?, ?)";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setString(1, code);
    		pstmt.setInt(2, Integer.parseInt(tagId));
    		pstmt.executeUpdate();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * insert a new tag into the table
     */
    public void insertNewTag(String tag, String tagId){
    	try{
    		String query = "insert into tags(tagid, name) values(?, ?)";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setInt(1, Integer.parseInt(tagId));
    		pstmt.setString(2, tag);
    		pstmt.executeUpdate();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * get the tag id
     */
    public String getTagId(String tag){
    	if(tagmap.get(tag) != null){
    		String val = String.valueOf(tagmap.get(tag));
    		return val;
    	}
    	else{
    		int l = tagmap.size();
    		String id = String.valueOf(l);
    		tagmap.put(tag, id);
    		insertNewTag(tag, id);
    		return id;
    	}
    }
    
    /**
     * return correct date format
     * @throws ParseException 
     */
    public String correctDateFormat(String date) throws ParseException{
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
    	Date date1 = formatter.parse(date);
    	return String.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(date1));
    }
    
    /** 
     * To get the date of a given problem
     */
    
    public String problemDate(Document doc){

    	try{
    		Elements rows = doc.select("tr");
    		int l = rows.size();
    		for( int i = 0; i < l; i++){
    			String name = rows.get(i).select("td").get(0).text();
    			if(name.equalsIgnoreCase("Date Added:")){
    				String date = rows.get(i).select("td").get(1).text();
    				//System.out.println(date);
    				return date;
    			}
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * To get tags
     */
    
    public Element getRowOfTags(Document doc){
    	
    	try{
    		Elements rows = doc.select("tr");
    		//System.out.println(row);
    		int l = rows.size();
    		for(int i = 0; i < l; i++){
    			String name = rows.get(i).select("td").get(0).text();
    			if (name.equalsIgnoreCase("Tags")) return rows.get(i);
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
		return null;
    	
    }
    
    public Document getDoc(String code){
    	int tries = 0;
    	while(tries < 3){
	    	String url = "http://www.codechef.com/problems/" + code;
	    	try{
	    		Document doc = Jsoup.connect(url).get();
	    		return doc;
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		System.out.println("try failed " + tries);
	    		tries++;
	    	}
    	}
		return null;
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
     * This function finds the problem page wise
     */
    
    public void insertProblems(String url, String diff){
    	int tries = 0;
    	while(tries < 3){
	    	try{
	    		Document doc = Jsoup.connect(url).get();
	    		tries = 3;
	    		Elements rows = doc.getElementsByClass("problemrow");
	    		int l = rows.size();
	    		for(int i = 0; i < l; i++){
	    			Element row = rows.get(i);
	    			Elements cols = row.select("td");
	    			String code = cols.get(1).select("a").get(0).text();System.out.println("code" + code);
	    			if(!isPresent(code)){
	    				Document doc1 = getDoc(code);
	    				String des = cols.get(0).select("b").get(0).text();
	    				String solved = cols.get(2).text();
	    				String acc = cols.get(3).select("a").get(0).text();
	    				String doa = correctDateFormat(problemDate(doc1));
	    				insertTupleInProblems(code, des, diff, solved, acc, doa);
	    				Element tagRow = getRowOfTags(doc1);
	    				Elements tags = tagRow.select("td").get(1).select("a");
	    				int l1 = tags.size();
	    				for(int j = 0; j < l1; j++){
	    					String tag = tags.get(j).text();
	    					insertTupleInTags(code, getTagId(tag));
	    				}
	    				System.out.println("done insertion");
	    			}
	    		}
	    	}
	    	catch(SocketTimeoutException e){
	    		System.out.println("Fail loading try " + tries);
	    		tries++;
	    		
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		createConnection();
		getPresentCodes();
		getTagMap();
		String url = "http://www.codechef.com/problems/"; 
		String diff = request.getParameter("UpdateType");
		if (diff.equalsIgnoreCase("All")){
			diff = "easy";
			insertProblems(url + diff, diff); System.out.println(diff + "done");
			diff = "medium";
			insertProblems(url + diff, diff); System.out.println(diff + "done");
			diff = "hard";
			insertProblems(url + diff, diff); System.out.println(diff + "done");
			diff = "challenge";
			insertProblems(url + diff, diff); System.out.println(diff + "done");
		}
		else{
			diff = diff.toLowerCase();
			insertProblems(url + diff, diff); System.out.println(diff + "done");
		}
		destroyConnection();
		request.setAttribute("codes", addedCodes);
		request.setAttribute("names", addedNames);
		request.getRequestDispatcher("./admin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int err_code = createConnection();
		if (err_code == 1){
			getPresentCodes();System.out.println("Codes Obtained");
			getTagMap();System.out.println("tags obtained");
			String url = "http://www.codechef.com/problems/"; 
			String diff = request.getParameter("UpdateType");
			if (diff.equalsIgnoreCase("All")){
				diff = "easy";
				insertProblems(url + diff, diff); System.out.println(diff + "done");
				diff = "medium";
				insertProblems(url + diff, diff); System.out.println(diff + "done");
				diff = "hard";
				insertProblems(url + diff, diff); System.out.println(diff + "done");
				diff = "challenge";
				insertProblems(url + diff, diff); System.out.println(diff + "done");
			}
			else{
				diff = diff.toLowerCase();
				insertProblems(url + diff, diff); System.out.println(diff + "done");
			}
			destroyConnection();

			request.setAttribute("codes", addedCodes);
			request.setAttribute("names", addedNames);
			System.out.println("dddddd");
			System.out.println(addedCodes);
			request.getRequestDispatcher("./admin_array.jsp").forward(request, response);
		}
		System.out.println("Server op finished");
	}
}
