package io.vincent.learning.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Vincent on 2020/6/3.
 *
 * @author Vincent
 * @since 1.0, 2020/6/3
 */
public class SimpleDBUtil {

	/**
	 * Get connection.
	 *
	 * @return {@link Connection}.
	 */
	public static Connection getConnection() {
		Connection connection;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://172.16.0.46:3306/tower?useAffectedRows=true", "root", "biosan#17");
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
