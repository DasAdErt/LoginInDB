package database;

import java.sql.*;

public class DBConnector {
    public static Connection connect() {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/my_login_db_tkachyov","postgres", "admin");
            //connection.close();
            System.out.println("Connection successed!");
        }
        catch(Exception e) {
            System.out.println(e);
            connection = null;
        }
        return connection;
    }
}