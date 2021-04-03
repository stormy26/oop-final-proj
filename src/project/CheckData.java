package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckData {
    //login details validation
    static Connection conn = null;
    static PreparedStatement prep = null;
    static ResultSet res = null;
    static DefaultTableModel model = new DefaultTableModel();

    public static int verify(String userN, String pass) {
        int result = 0;
        conn = DatabaseConn.getConnection(); // establishing of connection to the database

        if(conn != null) {
            String sql = "SELECT * FROM users where username = \"" + userN + "\" AND password = \"" + pass + "\" ;";
            System.out.println("refreshTable- SQL : " + sql);

            try {
                prep = conn.prepareStatement(sql);
                res = prep.executeQuery();
                res.next();

                if(userN.equals(res.getString("username")) && pass.equals(res.getString("password"))) {
                    result = 1;
                    // this will fetch user input data
                    UserData.username = res.getString("username");
                    UserData.password = res.getString("password");
                    UserData.firstname = res.getString("first_name");
                    UserData.lastname = res.getString("last_name");
                    UserData.email = res.getString("email");
                    UserData.usercateg = 1;

                }
                else if(userN.equals(res.getString("admin")) && pass.equals(res.getString("admin"))){
                    result = 2;
                    UserData.username = res.getString("username");
                    UserData.password = res.getString("password");
                    UserData.firstname = res.getString("first_name");
                    UserData.lastname = res.getString("last_name");
                    UserData.email = res.getString("email");
                    UserData.usercateg = 2;
                }
                else {
                    System.out.println("Something wrong with the AdminData variables");
                }

            }

            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid Credentials!");
            }

        } else {
            Alert.Error("[AdminData.java] conn is null");
        }

        return result;
    }

}
