package com.masser.jdbc;

import java.sql.*;
import java.util.Map;

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

    public void executeQuery(String sqlStatement) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        System.out.println("Executing query: " + getQuery(statement));

        ResultSet resultSet = statement.executeQuery();

        printResultSet(resultSet);

        statement.close();
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

    public void createTable(String tableName, String primaryKey,
                            String primaryKeyType, Map<String, String> additionalColumns,
                            Map<String, String[]> foreignKeys) throws SQLException {
        if ((!tableName.matches("[a-zA-Z0-9_]+")) || (!primaryKey.matches("[a-zA-Z0-9_]+"))) {
            throw new SQLException("Invalid table name: " + tableName);
        }

        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" (");
        sql.append(primaryKey).append(" ").append(primaryKeyType).append(" PRIMARY KEY");

        if (additionalColumns != null) {
            for (Map.Entry<String, String> column : additionalColumns.entrySet()) {
                if (!column.getKey().matches("[a-zA-Z0-9_]+")) {
                    throw new SQLException("Invalid column name: " + column.getKey());
                }
                sql.append(", ").append(column.getKey()).append(" ").append(column.getValue());
            }
        }

        if (foreignKeys != null) {
            for (Map.Entry<String, String[]> foreignKey : foreignKeys.entrySet()) {
                String columnName = foreignKey.getKey();
                String[] references = foreignKey.getValue();
                if (references.length != 2 || !references[0].matches("[a-zA-Z0-9_]+") || !references[1].matches("[a-zA-Z0-9_]+")) {
                    throw new SQLException("Invalid foreign key reference for column: " + columnName);
                }
                sql.append(", FOREIGN KEY (").append(columnName).append(") REFERENCES ");
                sql.append(references[0]).append("(").append(references[1]).append(")");
            }
        }
        sql.append(")");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            statement.executeUpdate();
        }
    }
}
