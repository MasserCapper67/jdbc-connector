package org.example;

import java.sql.*;

public class Connector {
    private final String DB_USER;
    private Connection connection;

    public Connector(String dbServer, String dbPort, String dbUser, String dbPassword, String dbName) throws Exception {
        DB_USER = dbUser;

        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        String url = "jdbc:mysql://" + dbServer + ":" + dbPort + "/" + dbName;
        connection = DriverManager.getConnection(url, DB_USER, dbPassword);

    }

    public String getDB_USER() {
        return DB_USER;
    }

    public PreparedStatement createStatement(String sqlStatement) throws SQLException {
        return connection.prepareStatement(sqlStatement);
    }

    public ResultSet executeQuery(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }


}
