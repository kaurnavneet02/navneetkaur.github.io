<%@ page import="java.sql.*" %>
<%

	
final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
final String JDBC_URL = "jdbc:mysql://localhost/test";

final String USERNAME = "root";
final String PASSWORD = "";

try
{
	Class.forName(JDBC_DRIVER);
	Connection connection = DriverManager.getConnection(JDBC_URL,USERNAME, PASSWORD );
	String query = "CREATE TABLE IF NOT EXISTS `restaurant` ( "+
			"`restaurant` INT(10) NOT NULL AUTO_INCREMENT, " +
			"`name` VARCHAR(50), " +
			"`email` VARCHAR(255)," +
			"`password` VARCHAR(25)," +
		"	UNIQUE KEY `email_unique` (`restaurant`,`email`) USING HASH " +
	"	) ENGINE=InnoDB;" ;
	
	Statement stmt = connection.createStatement();
	
	stmt.executeUpdate(query);
	
		
}
catch(Exception e)
{
	out.print(e.getMessage());
}
	

%>