package io.vincent.learning.multi;

import lombok.Getter;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * SimpleDBUtil for test.
 */
public class SimpleDBUtil {
    private static final Properties props = new Properties();
    @Getter
    private static final Connection connection;

    static {
        try {
            props.load(SimpleDBUtil.class.getClassLoader().getResourceAsStream("application.properties"));
            String env = props.getProperty("env");

            if ("test".equalsIgnoreCase(env)) {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection(
                        props.getProperty("h2.url"),
                        props.getProperty("h2.user"),
                        props.getProperty("h2.password")
                );
            } else {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        props.getProperty("postgresql.url"),
                        props.getProperty("postgresql.user"),
                        props.getProperty("postgresql.password")
                );
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException("数据库连接失败", e);
        }
    }

}