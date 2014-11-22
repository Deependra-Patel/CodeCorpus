package suggestions;

import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
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


public class Suggest_problems extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	/**
	 * the variables are declared
	 */
	JdbcSetup setUp;
	int usrId;
	
	double winterest, wsolved, wfollow, wfree, wfollower;   // the weights
	HashMap<Integer, Double> tagWeight;
	HashMap<String, Double> problemWeight;
	
	HashMap<String, Integer> solvedProblems; 
	
	ArrayList<Integer> followed;
	
	ArrayList<String> code_list;
	ArrayList<Integer> interest;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Suggest_problems() {
        super();
        // TODO Auto-generated constructor stub
        winterest = 100;
        wsolved = 10;
        wfollow = 5;
        wfollower = 100;
        wfree = 0;
        tagWeight = new HashMap<Integer, Double>();
        problemWeight = new HashMap<String, Double>();
        solvedProblems = new HashMap<String, Integer>();
        followed = new ArrayList<Integer>();
        code_list = new ArrayList<String>();
        interest = new ArrayList<Integer>();
    }
    
    
    public int  createConnection(){
    	try{
    		setUp = new JdbcSetup();
    		if (setUp.conn1 == null){
    			System.out.println("Could not connect");
    			return 0;
    		}
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
     * This function takes the tags from the interest
     * assumes nothing
     */
    public void getInterestTags(){
    	try{
    		String query = "select tagid from users_interests where userid = ?";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setInt(1, usrId);
    		ResultSet rs = pstmt.executeQuery(); 
    		int temp_id;
    		while(rs.next()){
    			temp_id = rs.getInt(1);
    			if (!tagWeight.containsKey(temp_id)){
    				tagWeight.put(temp_id, winterest);
    				interest.add(temp_id);    				
    			}
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /*clears*/
    public void clear(){
        tagWeight.clear();
        problemWeight.clear();
        solvedProblems.clear();
        followed.clear();
        code_list.clear();
        interest.clear();
    }
    /**
     * this one takes the problems solved - t
     * and the tags associated and adds them with proper weight
     */
    public void getSolvedProblemsTag(){
    	try{
    		String query = "select code	from attempted where userid = ? and solved = 't'";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setInt(1, usrId);
    		ResultSet rs = pstmt.executeQuery();
    		String cde;
    		while(rs.next()){
    			cde= rs.getString(1);
    			if (!solvedProblems.containsKey(cde)){
    				solvedProblems.put(cde, 0);
    			} 
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * update from the tags of 1 problem
     */
    public void updateTagWeightSolvedPerProblem(String key){
    	try{
    		String query = "select tagid from problems_tags where code = ?";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setString(1, key);
    		ResultSet rs = pstmt.executeQuery();
    		int _tagid;
    		while (rs.next()){
    			_tagid = rs.getInt(1);
    			if (tagWeight.containsKey(_tagid)){
    				double _t = (Double)tagWeight.get(_tagid).doubleValue();
    				tagWeight.put(_tagid, _t + wsolved);
    			}
    			else{
    				tagWeight.put(_tagid, wsolved);
    				interest.add(_tagid);
    			}
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * This function uses the tags of the solved problems
     *  and updates them in the tag list + adds the weight
     */
    public void updateTagWeightSolved(){
    	Set set = solvedProblems.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()) {
           Map.Entry me = (Map.Entry)i.next();
           updateTagWeightSolvedPerProblem(String.valueOf(me.getKey()));
        }
    }
    
    /**
     * this function update the follower solved problems
     */
    public void getTagWeightFollowedPerProblem(String key, int present, double weight){
    	try{
    		String query = "select tagid from problems_tags where code = ?";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setString(1, key);
    		ResultSet rs = pstmt.executeQuery();
    		int _tagid;
    		while (rs.next()){
    			_tagid = rs.getInt(1);
    			if (tagWeight.containsKey(_tagid)){
    				double _t = (Double)tagWeight.get(_tagid).doubleValue();
    				tagWeight.put(_tagid, _t + weight);
    			}
    			else{
    				tagWeight.put(_tagid, weight);
    			}
    			
    			double val = 0.0;
    			if(present == 0){
    				val = (Double)tagWeight.get(_tagid).doubleValue();
    			}
    			else{
    				val = weight;
    			}
    			
    			double oldVal = 0.0;
    			oldVal = (Double)problemWeight.get(key).doubleValue();
    			oldVal += val;
    			problemWeight.put(key, oldVal);
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /*
     * update the weight from one user
     */
    public void getTagWeightPerFollowed(int _userid){
    	try{
    		String query = "select code from attempted where userid = ? and solved = 't'";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setInt(1, _userid);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()){
    			String _code = rs.getString(1);
    			if(!solvedProblems.containsKey(_code)){
    				if(!problemWeight.containsKey(_code)){
    					problemWeight.put(_code, wfollower);
    					getTagWeightFollowedPerProblem(_code, 0, wfollow);
    				}
    				else{
    					// this updates per _code info
    					getTagWeightFollowedPerProblem(_code, 1, wfollow);
    				}
    			}
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

    /**
     * get the people whom usrid follows
     */
    public void getFollowedPeople(){
    	try{
    		String query = "select followed from followers where follower = ?";
    		PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
    		pstmt.setInt(1, usrId);
    		ResultSet rs = pstmt.executeQuery();
    		int _userid;
    		while (rs.next()){
    			_userid = rs.getInt(1);
    			followed.add(_userid);
    			getTagWeightPerFollowed(_userid);
    		}
    		rs.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    

    
    /**
     * suppose we have less than 25 problems available in our list
     * this function takes care of this corner case
     */
    public void handleCornerCaseOfDeficiency(){
    	int l = problemWeight.size();
    	if (l < 25){
    		int _l = Math.max(solvedProblems.size(), 25);
    		String query = "select code from problems where difficulty = 'easy' order by (statistic).accuracy desc limit ?";
    		PreparedStatement pstmt;
			try {
				pstmt = setUp.conn1.prepareStatement(query);
				pstmt.setInt(1, 2 * _l);
	    		ResultSet rs = pstmt.executeQuery();
	    		while (rs.next()){
	    			String _code = rs.getString(1);
	    			if(!solvedProblems.containsKey(_code)){
	    				if(!problemWeight.containsKey(_code)){
	    					problemWeight.put(_code, 0.0);
	    					getTagWeightFollowedPerProblem(_code, 0, wfree);
	    				}
	    			}
	    		}
	    		rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else return;
    }
    
    /**
     * sorting the list of problems
     */
    public void sortProblem(){
    	
    	ValueComparator bvc =  new ValueComparator(problemWeight);
    	TreeMap<String, Double> sortedMap= new TreeMap<String, Double>(bvc);
    	sortedMap.putAll(problemWeight); //System.out.println(sortedMap);
        Set set = sortedMap.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        int count = 0;
        int tot = sortedMap.size();
        String[] arr = new String[tot];
        
        while(i.hasNext()) {
           Map.Entry me = (Map.Entry)i.next();
           String _userid = (String)me.getKey();
           arr[count] = _userid;
           count++;
        }
        
        int pos = tot - 1;
        int _pos = 0;
        while (pos >= 0 && _pos < 25){
        	code_list.add(arr[_pos]);
        	pos--;
        	_pos++;
        }
    }
    /**
     * get the interest related codes
     */
    public void getInterestedProblems(){
        try{
            
            String query = "select code, tagid from problems_tags where tagid in (";
            int _l = interest.size();
            for (int i = 0; i < _l - 1; i++) query += "?, ";
            if (_l > 0) query += "?)";
            int pos = 1;
            PreparedStatement pstmt = setUp.conn1.prepareStatement(query);
            for (int i : interest){
                pstmt.setInt(pos, i);
                pos++;
            }
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String code = rs.getString(1);
                int _int = rs.getInt(2);
                if(!solvedProblems.containsKey(code)){
                    if(!problemWeight.containsKey(code)){
                        problemWeight.put(code, 0.0);
                    }
                    
                    if (tagWeight.containsKey(_int)){
                    	//System.out.println("flag 1");
                        double val = (Double)problemWeight.get(code).doubleValue();
                        //System.out.println("flag 2");
                        val += (Double)tagWeight.get(_int).doubleValue();
                        //System.out.println("flag 3");
                        problemWeight.put(code, val);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * execute the steps serially for sugestion
     */
    public void executeSeriallySteps(int _userid){
    	usrId = _userid;
    	int state = createConnection();
    	if (state == 1){
    		System.out.println("connection est.");
    		getInterestTags();// System.out.println("tagw1 " + tagWeight.size());
    		getSolvedProblemsTag();// System.out.println("tagw2 " + tagWeight.size() + " prob " + solvedProblems.size());
    		updateTagWeightSolved();// System.out.println("tagw3 " + tagWeight.size());
    		getFollowedPeople();// System.out.println("followed " + tagWeight.size() + " - followed " + followed.size() + " new " + problemWeight.size());
    		getInterestedProblems();
    		handleCornerCaseOfDeficiency();// System.out.println("followed " + tagWeight.size() + " new " + problemWeight.size());
    		//System.out.println(tagWeight);
    		//System.out.println(problemWeight);
    		//System.out.println(interest);
    		sortProblem();
    		destroyConnection();
    	}
    	else System.out.println("Failed to connect");
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		clear();
		HttpSession session = request.getSession(true);
		if(session.getAttribute("handle")==null){
			response.sendRedirect("./index.jsp?err=1");
		}
		else { 
			
			int userid = (Integer) session.getAttribute("userid");
			String handle = (String) session.getAttribute("handle");
			
			SuggestFollowersBackend sfb = new SuggestFollowersBackend();
			ArrayList<String> folSug = sfb.suggestFollowersHandle(handle);
			System.out.println("Folsug-----------"+folSug);
			
			executeSeriallySteps(userid);
			request.setAttribute("codes", code_list);
			
			
			request.setAttribute("folSug", folSug);
			System.out.println(code_list);
			request.getRequestDispatcher("./suggest_problems_array.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("codes", code_list);
		request.getRequestDispatcher("./suggest_problems_array.jsp").forward(request, response);
	}

}


class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}