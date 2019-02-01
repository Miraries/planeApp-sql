package planeApp.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {
	
	public static Connection getConnection() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("db.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		return DriverManager.getConnection(dburl, user, password);
    }
}
