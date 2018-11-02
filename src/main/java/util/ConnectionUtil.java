package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionUtil {

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@revature.cvmfh0fqyy8w.us-east-2.rds.amazonaws.com:1521:ORCL";
		String user = "project_1";
		String pass= "pass";
		
		return DriverManager.getConnection(url, user, pass);
	}
}
