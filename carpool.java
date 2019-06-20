/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fecs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


/**
 *
 * @author Nandana and Yash Raj
 */
public class carpool extends javax.swing.JFrame {

    public static String currentUser;
    public static String nameUser;
    public static ArrayList matchingUsers;
    public static ArrayList matchingUsersName;
    /**
     * Creates new form carpool
     * @param user
     * @param name
     */
    public carpool(String user, String name) {
        initComponents();
        currentUser = user;
        nameUser = name;
        loginStatus.setText("Logged in as, "+nameUser);
        startLat.setEditable(false);
        endLat.setEditable(false);
        startLon.setEditable(false);
        endLon.setEditable(false);
    }
    
    
    public class Geolocation {

	public String begin(String latitude, String longitude) {
            String formatted_address = null;
            try {
                    URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=true");
                    // making connection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");
                    if (conn.getResponseCode() != 200) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                            + conn.getResponseCode());
                    }

                    // Reading data from url
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                    String output;
                    String out="";
                    System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {
                        //System.out.println(output);
                        out+=output;
                    }
                    // Converting Json formatted string into JSON object
                    JSONObject json = (JSONObject) JSONSerializer.toJSON(out);
                    JSONArray results=json.getJSONArray("results");
                    JSONObject rec = results.getJSONObject(0);
                    JSONArray address_components=rec.getJSONArray("address_components");
                    for(int i=0;i<address_components.size();i++){
                        JSONObject rec1 = address_components.getJSONObject(i);
                        //trace(rec1.getString("long_name"));
                        JSONArray types=rec1.getJSONArray("types");
                        String comp=types.getString(0);

                        if(comp.equals("locality")){
                            System.out.println("city ————-"+rec1.getString("long_name"));
                        }
                        else if(comp.equals("country")){
                            System.out.println("country ———-"+rec1.getString("long_name"));
                        }
                    }
                formatted_address = rec.getString("formatted_address");
                System.out.println("formatted_address————–"+formatted_address);
                conn.disconnect();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return formatted_address;
	}
    }
    
    public static double distance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        // Haversine Formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return distance;
    }
    
    public static int timeDifference(String startTime, String endTime){
        int time = 0;
        try{
            SimpleDateFormat format = new SimpleDateFormat("MM/DD/YYYY hh:mm aa");
            Calendar c = Calendar.getInstance();

            c.setTime(format.parse(startTime));
            long startMillis = c.getTimeInMillis();

            c.setTime(format.parse(endTime));
            long endMillis = c.getTimeInMillis();
            
            time = (int) ((endMillis - startMillis)/60000);
            
            System.out.println("time difference is " + time + " minutes");
        }catch(Exception e){
            System.out.println(e);
        }
        
        return time;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginStatus = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        startLat = new javax.swing.JTextField();
        startLon = new javax.swing.JTextField();
        chooseStart = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        endLat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        endLon = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        chooseEnd = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        startAddress = new javax.swing.JTextField();
        endAddress = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        departureTime = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        loginStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton2.setText("Go Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton7.setText("Logout");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Choose Starting Point");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Latitude");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Longitude");

        startLat.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        startLon.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        chooseStart.setText("Choose on Map");
        chooseStart.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chooseStartStateChanged(evt);
            }
        });
        chooseStart.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                chooseStartComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                chooseStartComponentShown(evt);
            }
        });
        chooseStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseStartActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Choose Ending Point");

        endLat.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Latitude");

        endLon.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Longitude");

        chooseEnd.setText("Choose on Map");
        chooseEnd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chooseEndStateChanged(evt);
            }
        });
        chooseEnd.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                chooseEndComponentHidden(evt);
            }
        });
        chooseEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseEndActionPerformed(evt);
            }
        });

        jButton4.setText("Find People for Carpooling");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        startAddress.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        startAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startAddressActionPerformed(evt);
            }
        });

        endAddress.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Enter approx. departure time");

        departureTime.setForeground(new java.awt.Color(204, 204, 204));
        departureTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        departureTime.setText("DD/MM/YYYY hh:mm aa");
        departureTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                departureTimeFocusGained(evt);
            }
        });

        jButton1.setText("Check Status");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loginStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(startAddress))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(startLat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(startLon))
                        .addGap(18, 18, 18)
                        .addComponent(chooseStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(departureTime))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                        .addComponent(endAddress))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(endLat, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(endLon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chooseEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton7))
                    .addComponent(loginStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startLon, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(startLat)
                            .addComponent(startAddress)))
                    .addComponent(chooseStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endLon)
                            .addComponent(endLat)
                            .addComponent(endAddress)))
                    .addComponent(chooseEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(departureTime, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        postLogin p = new postLogin(currentUser, nameUser);
        p.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        currentUser = null;
        loginPage l = new loginPage();
        this.setVisible(false);
        this.dispose();
        l.setVisible(true);

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/FECS","root","root");
            Statement s = c.createStatement();
            s.executeUpdate("insert into carpool_data values('"+currentUser+"','"+nameUser+"',"+Double.parseDouble(startLat.getText())+","+Double.parseDouble(startLon.getText())+","+Double.parseDouble(endLat.getText())+","+Double.parseDouble(endLon.getText())+",'"+startAddress.getText()+"','"+endAddress.getText()+"','"+departureTime.getText()+"')");
            JOptionPane.showMessageDialog(null, "Check Status to get list of people found.", "Searching Database...", HEIGHT);
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void chooseStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseStartActionPerformed
        // TODO add your handling code here:
        showMap sm = new showMap();
        sm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String startingLat = Double.toString(showMap.startLat);
                String startingLng = Double.toString(showMap.startLon);
                startLat.setText(startingLat);
                startLon.setText(startingLng);
                Geolocation g = new Geolocation();
                startAddress.setText(g.begin(startLat.getText(), startLon.getText()));
            }
        });
        sm.setVisible(true);
        sm.isAlwaysOnTop();
        sm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }//GEN-LAST:event_chooseStartActionPerformed

    private void chooseEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseEndActionPerformed
        // TODO add your handling code here:
        showMap sm1 = new showMap();
        sm1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String endingLat = Double.toString(showMap.startLat);
                String endingLng = Double.toString(showMap.startLon);
                endLat.setText(endingLat);
                endLon.setText(endingLng);
                Geolocation g = new Geolocation();
                endAddress.setText(g.begin(endLat.getText(), endLon.getText()));
            }
        });
        sm1.setVisible(true);
        sm1.isAlwaysOnTop();
        sm1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
    }//GEN-LAST:event_chooseEndActionPerformed

    private void chooseStartStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chooseStartStateChanged
    }//GEN-LAST:event_chooseStartStateChanged

    private void chooseEndStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chooseEndStateChanged
    }//GEN-LAST:event_chooseEndStateChanged

    private void chooseStartComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_chooseStartComponentHidden
    }//GEN-LAST:event_chooseStartComponentHidden

    private void chooseEndComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_chooseEndComponentHidden
    }//GEN-LAST:event_chooseEndComponentHidden

    private void chooseStartComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_chooseStartComponentShown
    }//GEN-LAST:event_chooseStartComponentShown

    private void startAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startAddressActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_startAddressActionPerformed

    private void departureTimeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_departureTimeFocusGained
        // TODO add your handling code here:
        departureTime.setText("");
        departureTime.setForeground(Color.black);
    }//GEN-LAST:event_departureTimeFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/FECS","root","root");
            Statement s = c.createStatement();
            ResultSet rs_user = s.executeQuery("select * from carpool_data where Username = '"+currentUser+"'");
            Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/FECS","root","root");
            Statement s1 = c1.createStatement();           
            ResultSet rs_else = s1.executeQuery("select * from carpool_data where Username != '"+currentUser+"'");
            int usersFound = 0;            
            while(rs_user.next()){                
                while(rs_else.next()){
                    if(rs_user.getString("departureTime").substring(0,7).equals(rs_else.getString("departureTime").substring(0,7))){
                        if(timeDifference(rs_user.getString("departureTime"), rs_else.getString("departureTime"))<40){
                            if(distance(rs_user.getDouble("startLatitude"), rs_else.getDouble("startLatitude"), rs_user.getDouble("startLongitude"), rs_else.getDouble("startLongitude"))<3000){
                                if(distance(rs_user.getDouble("endLatitude"), rs_else.getDouble("endLatitude"), rs_user.getDouble("endLongitude"), rs_else.getDouble("endLongitude"))<3000){
                                    matchingUsers.add(rs_else.getString("Username"));
                                    matchingUsersName.add(rs_else.getString("Name"));
                                    usersFound++;
                                }
                            }
                        }
                    }
                }
            }
            if(usersFound==0){
                JOptionPane.showMessageDialog(null, "No other users found with similar time and location. Please check later for other users.", "Message from program:", HEIGHT);             
            }else{
                showResults q = new showResults(currentUser, nameUser, matchingUsers, matchingUsersName);
                q.setVisible(true);
                this.setVisible(false);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(carpool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(carpool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(carpool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(carpool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new carpool(currentUser, nameUser).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chooseEnd;
    private javax.swing.JButton chooseStart;
    private javax.swing.JTextField departureTime;
    private javax.swing.JTextField endAddress;
    private javax.swing.JTextField endLat;
    private javax.swing.JTextField endLon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel loginStatus;
    private javax.swing.JTextField startAddress;
    private javax.swing.JTextField startLat;
    private javax.swing.JTextField startLon;
    // End of variables declaration//GEN-END:variables
}
