package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

	public ConnectionUtils() {
		// TODO Auto-generated constructor stub
	}
	public static Connection getConnection() 
            throws ClassNotFoundException, SQLException {
		  Class.forName("com.mysql.jdbc.Driver");

	        // Establish connection
	        String url = "jdbc:mysql://localhost:3306/mywebapp";
	        String username = "root";
	        String password = "";
	        Connection conn = DriverManager.getConnection(url, username, password);

	        return conn;
  }
   
  public static void closeQuietly(Connection conn) {
      try {
          conn.close();
      } catch (Exception e) {
      }
  }

  public static void rollbackQuietly(Connection conn) {
      try {
          conn.rollback();
      } catch (Exception e) {
      }
  }

}
