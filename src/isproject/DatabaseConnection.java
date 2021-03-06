/*
    Setting up a database connection
*/
package isproject;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    
    static final String DB_URL = "jdbc:mysql://localhost:3306/casemanagementsystem";
    static final String USER = "root";
    static final String PASS = "";
    public static Connection connectDB(){
        Connection conn = null;
        try{
            
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        }catch(Exception ex){
            
            System.out.print("There was an error connecting to the database!");
            return null;
        }
    }

}


