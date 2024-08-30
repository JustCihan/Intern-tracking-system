package Mainpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
  public class DbHelper {  
 
          private   String url = "jdbc:postgresql://192.168.1.97/StajyerTakipProgrami";
           private String user = "root";  
            private String pass = "postgres";


        public Connection getConnection() throws SQLException { 
            return DriverManager.getConnection(url,user,pass);
            	
        }
        public void showErrorMassage(SQLException exception){
            System.out.println("Error : " + exception.getMessage());
            System.out.println("Error Code : " + exception.getErrorCode());
        }  
 
  }



