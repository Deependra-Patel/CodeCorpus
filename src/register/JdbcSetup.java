package register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;

public class JdbcSetup {
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/STUDENTS";

	   //  Database credentials
//	   static final String USER = "username";
//	   static final String PASS = "password";
		public Connection conn1 =null;
		Statement st =null;
	public JdbcSetup() throws ServletException {
	      //Open the connection here
		
		String dbURL2 = "jdbc:postgresql://localhost/cs387";
        String user = "db120050032";
        String pass = "faltu_passwd";

        try {
			Class.forName("org.postgresql.Driver");
		
			conn1 = DriverManager.getConnection(dbURL2, user, pass);
			st = conn1.createStatement();
			System.out.println("init"+conn1);
        	} catch (Exception e) {
			// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
	    }

	    public void destroy() {
	     //Close the connection here
	    	try{
	    		conn1.close();
	    		System.out.println("close");
	    	}catch(Exception e)
	    	{
	    		System.out.println(e);
	    	}
	    }
}
