import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class DB {

	Connection conn;
	Statement stmt;
	Random random;


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
		random = new Random();
	}

	public Calendar getDate() throws SQLException{
		String query = "select * from theDate";
		ResultSet rs = stmt.executeQuery(query);
		Calendar cal = Calendar.getInstance();
		while (rs.next()){
			cal.set(rs.getInt("year"), rs.getInt("month"), rs.getInt("day"));
		}
		rs.close();
		return cal;
	}

	public String dateString(Calendar cal){
		String result = cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.YEAR);
		return result;
	}

	public void setDate(String date) throws SQLException{
		SimpleDateFormat ft = new SimpleDateFormat ("MM-dd-yy");
		Calendar cal = Calendar.getInstance();
		java.util.Date d = new java.util.Date();
		try {
			d = ft.parse(date);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(d);
		updateDailyBalance(cal);
		int currentMonth = 0; int newMonth = (cal.get(Calendar.MONTH) + 1);
		String query = "select month from theDate";
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()){
			currentMonth = rs.getInt("month");
		}
		if(currentMonth != newMonth){
			query = "update market set startingMonthBalance = balance";
			rs = stmt.executeQuery(query);
		}
		query = "delete from theDate";
		rs = stmt.executeQuery(query);
		query = "insert into theDate values (" + (cal.get(Calendar.MONTH) + 1) + "," +  cal.get(Calendar.DAY_OF_MONTH) + "," + cal.get(Calendar.YEAR) + ")";
		rs = stmt.executeQuery(query);
		rs.close();
	}

	public void updateDailyBalance(Calendar calNew) throws SQLException {
		Calendar calOld = getDate(); double balance = 0; int taxID = 0; int maxDay = 0; ArrayList<Integer> ids = new ArrayList<Integer>();
		if(calOld.get(Calendar.DAY_OF_MONTH) != calNew.get(Calendar.DAY_OF_MONTH)){
			String query = "select unique tax_ID from dailyBalance";
			ResultSet rs = stmt.executeQuery (query);
			while (rs.next()){
				ids.add(rs.getInt("tax_ID"));
			}
			for(int x = 0; x < ids.size(); x++){
				query = "select MAX(day) from dailyBalance where tax_ID = '" + ids.get(x) + "'";
				ResultSet xs = stmt.executeQuery(query);
				while(xs.next()){
					maxDay = xs.getInt(1);
				}
				query = "update dailyBalance set daysAtBalance = " + (calNew.get(Calendar.DAY_OF_MONTH) - maxDay) + " where day = '" + maxDay + "' and tax_ID = '" + ids.get(x) + "'";
				xs = stmt.executeQuery(query);

				query = "select balance from market where tax_ID = '" + ids.get(x) + "'";
				xs = stmt.executeQuery(query);
				while(xs.next()){
					balance = xs.getDouble("balance");
				}
				query = "insert into dailyBalance values(" + ids.get(x) + "," + (calNew.get(Calendar.MONTH) + 1) + "," +  calNew.get(Calendar.DAY_OF_MONTH) + "," + calNew.get(Calendar.YEAR) + ","
					+ balance + "," + (calNew.getActualMaximum(Calendar.DAY_OF_MONTH) - calNew.get(Calendar.DAY_OF_MONTH) + 1) + ")";
				xs = stmt.executeQuery(query);
				xs.close();
			}
			rs.close();
		}
	}

	public int login(String username, String password) throws SQLException {
		String query = "select tax_ID from Customer where username = '" + username + "' and password = '" + password + "'";
		ResultSet rs = stmt.executeQuery (query);
		int id =0;
		while (rs.next()){
			id = rs.getInt("tax_ID");
		}
		rs.close();
		return id;
	}

	public int loginAdmin(String username, String password) throws SQLException {
		String query = "select tax_ID from Customer where username = '" + username + "' and password = '" + password + "' and isAdmin = 'y'";
		ResultSet rs = stmt.executeQuery (query);
		int id =0;
		while (rs.next()){
			id = rs.getInt("tax_ID");
		}
		rs.close();
		return id;
	}

	public int newCustomer(String name, String username, String password, String address, String state, String phone, String email, String ssn) throws SQLException{
		int account_ID = 0; int tax_ID = 0; Boolean notFound = true; ResultSet rs; String foundUsername = "";
		String query = "";
		while(notFound){
			foundUsername = "";
			tax_ID = random.nextInt(9000) + 1000;
			query = "select username from Customer where tax_ID = '" + tax_ID + "'";
			rs = stmt.executeQuery (query);
			while (rs.next()){
				foundUsername = rs.getString("username");
			}
			if(foundUsername.isEmpty()){
				notFound = false;
			}
		}
		query = "select max(account_ID) from Customer";
		rs = stmt.executeQuery (query);
		while (rs.next()){
			account_ID = rs.getInt(1);
		}
		account_ID++;
		query = "insert into Customer values('" + username + "','" + password + "','" + name + "','" + address + "','" + state + "'," 
			+ phone + ",'" + email + "'," + tax_ID + "," + ssn + "," + account_ID + ",'n')";
rs = stmt.executeQuery (query);
query = "insert into Market values(" + tax_ID + "," + account_ID + ",1000, 0)";
rs = stmt.executeQuery (query);
rs.close();
return tax_ID;
}

public double getMarketBalance(int id) throws SQLException {
	String query = "select balance from Market where tax_ID = '" + id + "'";
	ResultSet rs = stmt.executeQuery(query);
	double balance=0;
	while (rs.next()){
		balance = rs.getDouble("balance");
	}
	rs.close();
	return balance;
}

public double getCurrentStockPrice(String actorID) throws SQLException {
	String query = "select current_price from Actors where actor_id = '" + actorID + "'";
	ResultSet rs = stmt.executeQuery(query);
	double price=0;
	while (rs.next()){
		price = rs.getDouble("current_price");
	}
	rs.close();
	return price;
}

public void deposit(double amount, int id) throws SQLException {
	double startingAmount = amount;
	String query = "select Balance from Market where tax_ID = '" + id + "'";
	ResultSet rs = stmt.executeQuery(query);
	while (rs.next()){
		amount += rs.getInt("Balance");
	}
	query = "update Market set Balance = " + amount + " where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	query = "insert into Market_Transaction values(" + id + "," + getDate().get(Calendar.MONTH) + "," 
		+ getDate().get(Calendar.DAY_OF_MONTH) + "," + getDate().get(Calendar.YEAR) + ","
		+ amount + ",'" + startingAmount + " was deposited on " + dateString(getDate()) + "')";
rs = stmt.executeQuery(query);
rs.close();
} 

public void withdraw(double amount, int id) throws SQLException {
	String query = "select Balance from Market where tax_ID = '" + id + "'";
	ResultSet rs = stmt.executeQuery(query);
	int balance=0;
	while (rs.next()){
		balance = rs.getInt("Balance");
	}
	query = "update Market set Balance = " + (balance - amount) + " where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	query = "insert into Market_Transaction values(" + id + "," + getDate().get(Calendar.MONTH) + "," 
		+ getDate().get(Calendar.DAY_OF_MONTH) + "," + getDate().get(Calendar.YEAR) + "," + (amount * -1)
		+ ",'" + amount + " was withdrawn on " + dateString(getDate()) + "')";
rs = stmt.executeQuery(query);
rs.close();
}

public void addStock(String stockID, int amount, int id, double price) throws SQLException {
	String query = "select Shares from Stock where tax_ID = '" + id + "' and actor_id = '" + stockID + "'";
	ResultSet rs = stmt.executeQuery(query);
	int currentAmount = 0;
	while (rs.next()){
		currentAmount = rs.getInt("Shares");
	}
	if(currentAmount != 0){
		query = "update Stock set Shares = " + (currentAmount + amount) + " where tax_ID = '" + id + "' and actor_id = '" + stockID + "'";
		rs = stmt.executeQuery(query);
	}
	else{
		query = "insert into Stock values (" + id + "," + amount + ",'" + stockID + "')"; 
		rs = stmt.executeQuery(query);
	}
	query = "insert into Stock_Transaction values(" + id + "," + getDate().get(Calendar.MONTH) + "," 
		+ getDate().get(Calendar.DAY_OF_MONTH) + "," + getDate().get(Calendar.YEAR) + "," + amount
		+ "," + price + ",'" + amount + " shares were bought at " + price + " per share on " + dateString(getDate()) + "')";
rs = stmt.executeQuery(query);
rs.close();
}

public void sellStock(String stockID, int amount, int id, double price, double origPrice) throws SQLException {
	String query = "select Shares from Stock where tax_ID = '" + id + "' and actor_id = '" + stockID + "'";
	ResultSet rs = stmt.executeQuery(query);
	int currentAmount = 0;
	while (rs.next()){
		currentAmount = rs.getInt("Shares");
	}
	if(currentAmount != 0){
		query = "update Stock set Shares = " + (currentAmount - amount) + " where tax_ID = '" + id + "' and actor_id = '" + stockID + "'";
		rs = stmt.executeQuery(query);
	}
	query = "insert into Stock_Transaction values(" + id + "," + getDate().get(Calendar.MONTH) + "," 
		+ getDate().get(Calendar.DAY_OF_MONTH) + "," + getDate().get(Calendar.YEAR) + "," + amount
		+ "," + price + ",'" + amount + " shares were sold at " + price + " per share on " + dateString(getDate()) + "')";
	rs = stmt.executeQuery(query);

	double earnings = 0;
	query = "select earnings from Market where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		earnings = rs.getDouble("earnings");
	}

	earnings += ((price - origPrice) * amount);
	query = "update Market set earnings = " + earnings + " where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	rs.close();
}

public Boolean checkStockExists(String stockID) throws SQLException {
	String query = "select name from Actors where actor_id = '" + stockID + "'";
	ResultSet rs = stmt.executeQuery(query);
	String actorName = "";
	while (rs.next()){
		actorName = rs.getString("name");
	}
	if(actorName.equals("")){
		rs.close();
		return false;
	}
	else{
		rs.close();
		return true;
	}
}

public double getNumShares(String stockID, int id) throws SQLException {
	String query = "select Shares from Stock where tax_ID = '" + id + "' and actor_id = '" + stockID + "'";
	ResultSet rs = stmt.executeQuery(query);
	double shares = 0;
	while (rs.next()){
		shares = rs.getDouble("Shares");
	}
	rs.close();
	return shares;
}

public String getActorProfile(String stockID) throws SQLException {
	String query = "select * from Actors where actor_id = '" + stockID + "'";
	ResultSet rs = stmt.executeQuery(query);
	String actorId = ""; double price = 0; String name = ""; String dob = "";
	String title = ""; String role = ""; int year = 0; double contract = 0;
	while (rs.next()){
		actorId = rs.getString("actor_id");
		price = rs.getDouble("current_price");
		name = rs.getString("name");
		dob = rs.getString("dob");
		title = rs.getString("movie_Title");
		role = rs.getString("role");
		year = rs.getInt("year");
		contract = rs.getDouble("contract");
	}
	rs.close();
	String result = "Actor ID: " + actorId + " Current Stock Price: " + price + " Name: " + name + " Date of Birth: " + dob + 
	"\nMovie Title: " + title + " Role: " + role + " Year: " + year + " Contract Price: " + contract + "\n";
	return result;
}

public Boolean checkTransactionHistory(int id) throws SQLException {
	String market = ""; String stock = "";
	String query = "select description from Market_Transaction where tax_ID = '" + id + "'";
	ResultSet rs = stmt.executeQuery(query);
	while (rs.next()){
		market = rs.getString("description");
	}
	query = "select description from Stock_Transaction where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		stock = rs.getString("description");
	}
	rs.close();
	if(market == null && stock == null){
		return false;
	}
	else{
		return true;
	}
}

public String getTransactionHistory(int id) throws SQLException {
	String market = ""; String stock = ""; String result = "";
	String query = "select description from Market_Transaction where tax_ID = '" + id + "'";
	ResultSet rs = stmt.executeQuery(query);
	while (rs.next()){
		result = result + rs.getString("description") + "\n";
	}
	query = "select description from Stock_Transaction where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		result = result + rs.getString("description") + "\n";
	}
	rs.close();
	return result;
}

public String movieInfo(String title) throws SQLException{
	String query = "select * from CS174A.movies where M_NAME = '" + title + "'";
	ResultSet rs = stmt.executeQuery(query);
	int m_id = 0; String m_name = ""; int m_year = 0; double m_ranking = 0;
	while (rs.next()){
		m_id = rs.getInt("M_ID");
		m_name = rs.getString("M_NAME");
		m_year = rs.getInt("M_YEAR");
		m_ranking = rs.getDouble("M_RANKING");
	}

	rs.close();
	String result = "Movie ID: " + m_id + " Name: " + m_name + " Year: " + m_year + " Ranking: " + m_ranking + "\n";
	return result;
}

public String movieReview(String title) throws SQLException {
	String query = "select M_ID from CS174A.movies where M_NAME = '" + title + "'";
	ResultSet rs = stmt.executeQuery(query);
	int m_id = 0; String review = "";
	while (rs.next()){
		m_id = rs.getInt("M_ID");
	}

	query = "select R_REVIEW from CS174A.reviews where R_ID =  '" + m_id + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		review = rs.getString("R_REVIEW");
	}

	rs.close();
	String result = "Review for the Movie: " + title + " is: " + review + "\n";
	return result;
}

public String topMovies(int start, int end) throws SQLException {
	String query = "select M_NAME from CS174A.movies where M_RANKING > 4.9 and M_YEAR > '" + start + "' and M_YEAR < '" + end + "'";
	ResultSet rs = stmt.executeQuery(query);
	int m_id = 0; String result = "";
	while (rs.next()){
		result = result + rs.getString("M_NAME") + "\n";
	}

	rs.close();
	return result;
}

    //*********************************************************
    //Admin Funtions


public void deleteTransactions() throws SQLException {
	String query = "delete from Market_Transaction";
	ResultSet rs = stmt.executeQuery(query);
	query = "delete from Stock_Transaction";
	rs = stmt.executeQuery(query);
	query = "update market set earnings = 0";
	rs = stmt.executeQuery(query);
	rs.close();
}

public String customerReport(int id) throws SQLException {
	String query = "select account_ID, balance from Market where tax_ID = '" + id + "'";
	ResultSet rs = stmt.executeQuery(query);
	int accID=0; double shares =0; String actID = ""; int tID=0;
	double balance =0;
	while (rs.next()){
		accID = rs.getInt("account_ID");
		balance = rs.getDouble("balance");
	}
	String returnString = "";
	if(accID == 0){
		returnString = "Tax ID does not exist\n";
	}
	else{
		returnString = "Market account ID is " + accID + " with balance: " + balance + "\n";
		query = "select shares, actor_id from Stock where tax_ID = '" + id + "'";
		rs = stmt.executeQuery(query);
		while (rs.next()){
			returnString = returnString + "Stock account: " + rs.getInt("shares") + " shares in actor ID: " + rs.getString("actor_ID") + "\n";
		}
	}
	rs.close();
	return returnString;
}

public String monthlyStatement(int id) throws SQLException {
	int month = getDate().get(Calendar.MONTH); int year = getDate().get(Calendar.YEAR);
	
	String name = ""; String email = ""; String result = "";
	String query = "select name, email from Customer where tax_ID = '" + id + "'";
	ResultSet rs = stmt.executeQuery(query);
	while (rs.next()){
		name = rs.getString("name");
		email = rs.getString("email");
	}
	result = "Monthly Statement for " + name + " " + email + "\n";
	String market = ""; String stock = "";
	query = "select description from Market_Transaction where tax_ID = '" + id + "' and month = '" + month + "' and year = '" + year + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		result = result + rs.getString("description") + "\n";
	}
	query = "select description from Stock_Transaction where tax_ID = '" + id + "' and month = '" + month + "' and year = '" + year + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		result = result + rs.getString("description") + "\n";
	}
	query = "select startingMonthBalance, balance from market where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		result = result + "Initial balance: " + rs.getDouble("startingMonthBalance") +  "   Final balance: " + rs.getDouble("Balance") + "\n";
	}
	query = "select earnings from Market where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		result = result + "Earnings: " + rs.getDouble("earnings") + "\n";
	}
	query = "select COUNT(tax_ID) from Stock_Transaction where tax_ID = '" + id + "'";
	rs = stmt.executeQuery(query);
	while (rs.next()){
		result = result + "Total commissions paid: " + (rs.getInt(1) * 20) + "\n";
	}
	rs.close();
	return result;
}

public String generateDTER() throws SQLException{
	String query = "select tax_ID, earnings from Market where earnings > 10000";
	 ResultSet rs = stmt.executeQuery(query);
	 double earnings = 0;
	 String result = "";
	 while (rs.next()){
		query = "select name, state from Customer where tax_ID = '" + rs.getInt("tax_ID") + "'";
		earnings = rs.getDouble("earnings");
		ResultSet xs = stmt.executeQuery(query);
		while (xs.next()){
			result = result + xs.getString("name") + " " + xs.getString("state") + " " + earnings + "\n";
		}
		xs.close();
	}
	rs.close();	
	return result;
}

public String activeCustomers() throws SQLException{
	String query = "select unique tax_ID from Stock_Transaction";
	 ResultSet rs = stmt.executeQuery(query);
	 double numShares = 0; int taxID = 0;
	 String result = "";
	 while (rs.next()){
	 	taxID = rs.getInt("tax_ID");
		query = "select sum(num_shares) from stock_transaction where tax_ID = '" + taxID + "'";
		ResultSet xs = stmt.executeQuery(query);
		while (xs.next()){
			numShares = xs.getDouble(1);
			if(numShares >= 1000){
				query = "select name from Customer where tax_ID = '" + taxID + "'";
				ResultSet ys = stmt.executeQuery(query);
				while (ys.next()){
					result = result + ys.getString("name") + "\n";
				}
				ys.close();
			}
		}
		xs.close();
	}
	rs.close();	
	return result;
}

public void changeStockPrice(double newPrice, String stockID) throws SQLException{
	String query = "update Actors set current_Price = " + newPrice + " where actor_id = '" + stockID + "'";
	ResultSet rs = stmt.executeQuery(query);
	rs.close();
}

public void insertData() throws SQLException{
	try {
		FileInputStream fstream = new FileInputStream("data.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		ResultSet rs;
		while((strLine = br.readLine()) != null) {
			rs = stmt.executeQuery(strLine);
		}
		in.close();
	}
	catch (Exception e) {
		System.err.println("Error: " + e.getMessage());
	}
}

public void toggleMarketOpenClose(Boolean open) throws SQLException{
	if(open){
		String query = "update marketOpen set open = 'y'";
		ResultSet rs = stmt.executeQuery (query);
	}
	else{
		String query = "update marketOpen set open = 'n'";
		ResultSet rs = stmt.executeQuery (query);
	}
}

public Boolean checkMarketOpen() throws SQLException{
	String result = "";
	String query = "select open from marketOpen";
	ResultSet rs = stmt.executeQuery (query);
	while (rs.next()){
		result = rs.getString("open");
	}
	if(result.equals("y")){
		return true;
	}
	else{
		return false;
	}	
}

public void addInterest() throws SQLException{
	Calendar cal = getDate(); ArrayList<Integer> ids = new ArrayList<Integer>();
	updateDailyBalance(cal);
	String query = "select unique tax_ID from dailyBalance";
	ResultSet rs = stmt.executeQuery (query);
	int taxID = 0;  
	while (rs.next()){
		ids.add(rs.getInt("tax_ID"));
	}
	for(int x = 0; x < ids.size(); x++){
		double total = 0; double total2 =0;
		query = "select balance, daysAtBalance from dailyBalance where tax_ID = '" + ids.get(x) + "'";
		ResultSet xs = stmt.executeQuery(query);
		while(xs.next()){
			total += xs.getDouble("balance") * xs.getInt("daysAtBalance");
		}
		total = (total/(cal.getActualMaximum(Calendar.DAY_OF_MONTH) + 1));
		total = total * 0.0025;
		total2 = total;
		query = "select balance from Market where tax_ID = '" + ids.get(x) + "'";
		xs = stmt.executeQuery(query);
		while(xs.next()){
			total += xs.getDouble("balance");
		}
		query = "update Market set balance = '" + total + "' where tax_ID = '" + ids.get(x) + "'";
		xs = stmt.executeQuery(query);

		query = "select earnings from Market where tax_ID = '" + ids.get(x) + "'";
		xs = stmt.executeQuery(query);
		while(xs.next()){
			total2 += xs.getDouble("earnings");
		}
		query = "update Market set earnings = '" + total2 + "' where tax_ID = '" + ids.get(x) + "'";
		xs = stmt.executeQuery(query);
		xs.close();
	}
	rs.close();
}
}
