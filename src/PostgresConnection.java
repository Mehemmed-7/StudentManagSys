import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.sql.Connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mehemmed Memmedov
 */
public class PostgresConnection {
    
    public static Connection getConnection() {
        
        Connection connection = null;
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\connection.properties"));
            String user = properties.getProperty("jdbc.user");
            String password = properties.getProperty("jdbc.password");
            String url = properties.getProperty("jdbc.url");
            String driver = properties.getProperty("jdbc.driver");
            
            // Load postgres driver 
            Class.forName(driver);
            
            connection =  DriverManager.getConnection(url, user, password);

            System.out.println("Database'e qosulduq..");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Postgres Jdbc driver yuklenmedi...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
        
    }
}
