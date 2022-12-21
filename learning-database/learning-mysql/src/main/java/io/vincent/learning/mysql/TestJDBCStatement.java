package io.vincent.learning.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestJDBCStatement {

	public static void testDDL() {
		int id = 1;
		Connection connection = SimpleDBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		PreparedStatement statement = null;
		try {
			if (connection != null) {
				connection.setAutoCommit(false);
				String sql1 = "DROP TABLE IF EXISTS `update_return_value`;";
				String sql2 = " CREATE TABLE `update_return_value`"
				              + "(`id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT, `name` varchar(255))"
				              + "ENGINE=InnoDB AUTO_INCREMENT=1;";
				preparedStatement = connection.prepareStatement(sql1);
				int i = preparedStatement.executeUpdate();
				System.out.println("DDl Return Value: " + i);
				statement = connection.prepareStatement(sql2);
				int rows = statement.executeUpdate();
				System.out.println("DDL Return Value: " + rows);
				connection.commit();
			} else {
				System.out.println("Get Connection error.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void testInsert() {
		Connection connection = SimpleDBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			if (connection != null) {
				connection.setAutoCommit(false);
				String sql = "INSERT INTO update_return_value(name) VALUES (?)";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, "2222");
				int rows = pstmt.executeUpdate();
				System.out.println("Insert Return Value: " + rows);
				connection.commit();
			} else {
				System.out.println("Get Connection error.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void testUpdate() {
		Connection connection = SimpleDBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			if (connection != null) {
				connection.setAutoCommit(false);
				String sql = "UPDATE update_return_value SET name = ? WHERE id = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, "2222");
				pstmt.setLong(2, 1L);
				int rows = pstmt.executeUpdate();
				System.out.println("Update1 Return Value: " + rows);
				pstmt.setString(1, "3333");
				int rows2 = pstmt.executeUpdate();
				System.out.println("Update2 Return Value: " + rows2);
				connection.commit();
			} else {
				System.out.println("Get Connection error.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void testUpdateError() {
		Connection connection = SimpleDBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			if (connection != null) {
				connection.setAutoCommit(false);
				String sql = "UPDATE update_return_value SET name = ? WHERE id = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, "2222");
				pstmt.setLong(2, 2L);
				int rows = pstmt.executeUpdate();
				System.out.println("Update error Return Value: " + rows);
				connection.commit();
			} else {
				System.out.println("Get Connection error.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
