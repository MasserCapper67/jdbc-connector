package org.example;

import java.sql.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        Connector connector = new Connector("localhost", "3306", "root", "", "reformas");

        PreparedStatement st1 = connector.createStatement("SELECT * FROM conductores");
        connector.executeQuery(st1);
    }
}