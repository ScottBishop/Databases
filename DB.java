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
		String query = "select tax_ID from Customer where username = '" + username + "'' and password = '" + password + "';";
		ResultSet rs = stmt.executeQuery (query);
		int id = rs.getInt("tax_ID");
		rs.close();
		return id;
    }

    public double getMarketBalance(int id) throws SQLException {
		String query = "select Balance from Market where tax_ID = '" + id + "';";
		ResultSet rs = stmt.executeQuery(query);
		double balance = rs.getDouble("Balance");
		rs.close();
		return balance;
    }

    public int getCurrentStockPrice(String actorID) throws SQLException {
		String query = "select current_price from Actors where actor_id = '" + actorID + "';";
		ResultSet rs = stmt.executeQuery(query);
		double price = rs.getDouble("current_price");
		rs.close();
		return price;
    }

   public void deposit(double amount, int id) throws SQLException {
		String query = "select Balance from Market where tax_ID = '" + id + "';";
		ResultSet rs = stmt.executeQuery(query);
		int amount += rs.getInt("Balance");
		query = "update Market set Balance = " + amount + " where tax_ID = '" + id + "';";
		rs = stmt.executeQuery(query);
		rs.close();
    } 

    public void withdraw(double amount, int id) throws SQLException {
		String query = "select Balance from Market where tax_ID = '" + id + "';";
		ResultSet rs = stmt.executeQuery(query);
		int balance = rs.getInt("Balance");
		query = "update Market set Balance = " + (balance - amount) + " where tax_ID = '" + id + "';";
		rs = stmt.executeQuery(query);
		rs.close();
    }

    public void addStock(String stockID, int amount, int id) throws SQLException {
		String query = "select Shares from Stock where tax_ID = '" + id + "' and actor_id = '" + stockID + "';";
		ResultSet rs = stmt.executeQuery(query);
		int currentAmount = rs.getInt("Shares");
		if(currentAmount != 0){
			query = "update Stock set Shares = " + (currentAmount + amount) + " where tax_ID = '" + id + "' and actor_id = '" + stockID + "';";
			rs = stmt.executeQuery(query);
		}
		else{
			query = "insert into Stock values (" + id + "," + amount + ",'" + stockID + "');"; 
			rs = stmt.executeQuery(query);
		}
		rs.close();
    }
}
