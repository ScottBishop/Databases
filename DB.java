import java.sql.*;

public class DB {
    Connection conn;
	Statement stmt;

	public void start() throws SQLException{
	try {
	    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		}
	catch ( Exception e){
	    e.printStackTrace();
		}

	String strConn = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	    String strUsername = "cs174a_scottbishop";
	    String strPassword = "3954237";

	    conn = DriverManager.getConnection(strConn,strUsername,strPassword);

	    stmt = conn.createStatement();
	}

    public int login(String username, String password) throws SQLException {
	String query = "select tax_ID from Customer where username = " + username + " and password = " + password + ";";
	ResultSet rs = stmt.executeQuery (query);

	int id = rs.getInt("tax_ID");
	rs.close();
	return id;
    }
}