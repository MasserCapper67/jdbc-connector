package masser.jdbc;

public class Main {
    public static void main(String[] args) throws Exception {
        Connector connector = new Connector("localhost", "3306", "root", "", "reformas");

        connector.executeQuery("SELECT * FROM conductores");
    }
}
