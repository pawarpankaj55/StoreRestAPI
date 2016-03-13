package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtil {
 
  private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/storeTest";
  private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  private static final String USER_NAME = "root";
  private static final String PASSWORD = "root";
    
  public DatabaseUtil() throws Exception{	   
 	
   }
   public static Connection getConnection(){
	Connection con = null;
	try {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(DATABASE_URL, USER_NAME,PASSWORD);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return con;
   }
   
   public static void cleanUp(Connection con, PreparedStatement pst){
	  try{
		 if(pst!=null){
			   pst.close();
		 } 
		 if(con!=null){
			   con.close();
		 } 
	   }catch(Exception e){
		   System.out.println("Exception occured while closing connection or statement :"+ e.getMessage());
	   }
   }
}
