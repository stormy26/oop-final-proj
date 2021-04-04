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
                    if ("admin".equals(res.getString("username"))){
                        UserData.usercateg = "2";
                    }
                    else{
                        UserData.usercateg = "1";
                    }
                    // this will fetch user input data
                    UserData.user_id = res.getInt("user_id");
                    UserData.username = res.getString("username");
                    UserData.password = res.getString("password");
                    UserData.firstname = res.getString("first_name");
                    UserData.lastname = res.getString("last_name");
                    UserData.email = res.getString("email");

                    result = 1;
                }

                else {
                    System.out.println("Something wrong with the User Data variables");
                }

            }

            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid Credentials!");
                System.out.println(e.getMessage());
            }

        } else {
            Alert.Error("[AdminData.java] conn is null");
        }

        return result;
    }

}
