
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mehemmed Memmedov
 */
public class PostgresConnection {
    public static void main(String[] args) {
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

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
            
            String sql = "select id, first_name, last_name, address " +
                    "from "
                    + "student";

            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            Long id = 0L;
            String name = "";
            String surname = "";
            String address = "";
            
            while(rs.next()){
                id = rs.getLong("id");
                name = rs.getString("name");
                surname = rs.getString("surname");
                address = rs.getString("address");
                System.out.printf("%s %s %s %s\n", id, name, surname, address);
            }

            
            System.out.println(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Postgres Jdbc driver yuklenmedi...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }finally{
            try {
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
                
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (Exception e) {
            }
        }
        
    }
}
