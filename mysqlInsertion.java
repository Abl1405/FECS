/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fecs;
import java.sql.*;
import java.util.*;
 import java.util.Random; 

public class mysqlInsertion {
  
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  


public static void main(String[] args) {
    Connection conn = null;
    Statement stmt = null;
    
    String username = null;
    
    
    
    try {
        // STEP 2: Register JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        // STEP 3: Open a connection
        System.out.print("\nConnecting to database...");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fecs", "root" , "root");
        System.out.println(" SUCCESS!\n");

      
        // STEP 4: Excute query
        System.out.print("\nInserting records into table...");
        stmt = conn.createStatement();
        for (int i=501;i<=1000;i++){
           Random rand = new Random(); 
         int electricity = rand.nextInt(80) + 1; 
         int petrol=rand.nextInt(50)+0;
         int diesel=rand.nextInt(1)+0;
         int lpg=rand.nextInt(42)+0;
         int cng=rand.nextInt(10)+0;
         double footprint=((electricity*12*0.85)+(petrol*52*2.296)+(diesel*52*2.653)+(lpg*12*2.983)+(cng*12*2.983))/1000;
        String sql = "INSERT INTO footprintdetails VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
ps.setInt(1, i);
ps.setDouble(2, (electricity*12*0.85));
ps.setDouble(3, (petrol*52*2.296));
ps.setDouble(4, (diesel*52*2.653));
ps.setDouble(5, (lpg*12*2.983));
ps.setDouble(6, (cng*12*2.983));
ps.setDouble(7, footprint);
ps.executeUpdate();
ps.close();
        //stmt.executeUpdate(sql);
        System.out.println(i);

        }

    } catch(SQLException se) {
        se.printStackTrace();
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if(stmt != null)
                conn.close();
        } catch(SQLException se) {
        }
        try {
            if(conn != null)
                conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }
    
  }
}



