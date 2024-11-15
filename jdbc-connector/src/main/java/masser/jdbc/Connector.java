package masser.jdbc;

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

    public ResultSet executeQuery(String sqlStatement) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        System.out.println("Executing query: " + getQuery(statement));

        ResultSet resultSet = statement.executeQuery();

        printResultSet(resultSet);

        statement.close();

        return resultSet;
    }

    private String getQuery(PreparedStatement statement) {
        String query = statement.toString();
        int index = query.indexOf(": ");
        if (index > 0) {
            return query.substring(index + 2);
        }
        return query;
    }

    private void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%-20s", metaData.getColumnName(i));
        }
        System.out.println();
        System.out.println("-".repeat(20 * columnCount));

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", resultSet.getString(i));
            }
            System.out.println();
        }
    }
}
