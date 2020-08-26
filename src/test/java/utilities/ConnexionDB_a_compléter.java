package utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexionDB_a_compléter {
    public static Connection give_connection() {
        Connection myConnex = null;
        try {
            String url = "jdbc:mysql://localhost:3306/refuge?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
            String username = "a compléter";
            String password = "a compléter";

            myConnex = DriverManager.getConnection(url, username, password);
            System.out.println("Connection is created successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Connection failed");
        }
        return myConnex;
    }

}
