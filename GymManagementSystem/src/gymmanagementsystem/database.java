/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author MarcoMan
 * Subscribe our YouTube Channel: https://www.youtube.com/@marcomanchannel
 */
public class database {

    public static Connection connectDb() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/gym", "root", ""); // root is the default username while empty or null to the password
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

        // NOW LETS OPEN OUR XAMPP : ) 
    }

}
