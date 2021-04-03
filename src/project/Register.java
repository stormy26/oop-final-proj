package project;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.awt.Image.SCALE_SMOOTH;

public class Register {
    // for calling on other classes
    public static void showWindow() {
        Register window = new Register();
        window.registerFrame.setVisible(true);
    }

    // main
    public static void main(String[] args) {
        Register window = new Register();
        window.registerFrame.setVisible(true);
    }

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private JFrame registerFrame;
    private JTextField usernamejtxt;
    private JTextField firstNamejtxt;
    private JTextField lastNamejtxt;
    private JTextField emailtxt;
    private JPasswordField passwordjpw;

    public Register() {
        //declare frame
        registerFrame = new JFrame();
        registerFrame.setTitle("Register");
        registerFrame.setBounds(100, 100, 700, 500);;
        registerFrame.setLayout(null);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setLocationRelativeTo(null);
        registerFrame.setResizable(false);
        registerFrame.getContentPane().setBackground(new Color(255, 255, 255));

        //declare elements
        ImageIcon moviePic = new ImageIcon("people-watching.png");
        Image movieIconimage = moviePic.getImage();
        Image movieIconnewimg = movieIconimage.getScaledInstance(800, 600,  SCALE_SMOOTH);
        moviePic = new ImageIcon(movieIconnewimg);
        JLabel moviePicLabel= new JLabel(moviePic);
        JPanel moviePicPanel = new JPanel();

        JLabel usernamelbl = new JLabel("Username");
        JLabel passwordlbl = new JLabel("Password");
        JLabel firstNamelbl = new JLabel("First Name");
        JLabel lastNamelbl = new JLabel("Last Name");
        JLabel emailbl = new JLabel("Email");

        usernamejtxt = new JTextField();
        passwordjpw = new JPasswordField();
        firstNamejtxt = new JTextField();
        lastNamejtxt = new JTextField();
        emailtxt = new JTextField();

        JLabel reglbl = new JLabel("User Registration");
        reglbl.setFont(new Font("Verdana", Font.BOLD, 17));
        JButton signupBtn = new JButton("Sign Up");
        JButton cancelBtn = new JButton("Cancel");

        signupBtn.setBackground(new Color(165, 194, 242));

        cancelBtn.setBackground(new Color(187, 252, 229));


        //layout elements
        moviePicPanel.setBounds(350,-50,350,530);
        moviePicLabel.setBounds(0,0,350,550);
        reglbl.setBounds(100, 60, 200,20);

        usernamelbl.setBounds(50, 122, 98, 14);
        passwordlbl.setBounds(50, 153, 98, 14);
        firstNamelbl.setBounds(50, 184, 98, 14);
        lastNamelbl.setBounds(50, 215, 98, 14);
        emailbl.setBounds(50, 246, 98, 14);

        usernamejtxt.setBounds(158, 125, 150, 25);
        passwordjpw.setBounds(158, 156, 150, 25);
        firstNamejtxt.setBounds(158, 187, 150, 25);
        lastNamejtxt.setBounds(158, 218, 150, 25);
        emailtxt.setBounds(158, 249, 150, 25);

        signupBtn.setBounds(110, 300, 180, 23);
        cancelBtn.setBounds(110, 340, 180, 23);

        //add elements to the frame
        registerFrame.add(usernamelbl);
        registerFrame.add(usernamejtxt);
        registerFrame.add(passwordlbl);
        registerFrame.add(passwordjpw);
        registerFrame.add(firstNamelbl);
        registerFrame.add(firstNamejtxt);
        registerFrame.add(lastNamelbl);
        registerFrame.add(lastNamejtxt);
        registerFrame.add(emailbl);
        registerFrame.add(emailtxt);

        registerFrame.add(signupBtn);
        registerFrame.add(cancelBtn);

        registerFrame.add(reglbl);

        registerFrame.add(moviePicPanel);
        moviePicPanel.add(moviePicLabel);

        // sign up button
        // registers and goes back to login window
        signupBtn.addActionListener(e -> {
            register();
        });

        // cancel button
        cancelBtn.addActionListener(e -> {
            //close register window
            registerFrame.dispose();

            // Go back to Login page
            Login.showWindow();
        });
    }

    public boolean register(){
        String username = usernamejtxt.getText();
        String password = passwordjpw.getText();
        String first_name = firstNamejtxt.getText();
        String last_name = lastNamejtxt.getText();
        String email = emailtxt.getText();

        if( !Validator.validateRegistration(username, password, first_name, last_name) ) {
            Validator.displayError();
            return false;
        }

        usernamejtxt.setText(username);
        passwordjpw.setText(password);
        firstNamejtxt.setText(first_name);
        lastNamejtxt.setText(last_name);
        emailtxt.setText(email);

        boolean success = false;

        conn = DatabaseConn.getConnection();

        if(conn != null) {
            String sql = "INSERT INTO Users (username, password, first_name, last_name, email) ";
            sql += "VALUES (";
            sql += "'" + username + "',";
            sql += "'" + password + "',";
            sql += "'" + first_name + "',";
            sql += "'" + last_name + "'";
            sql += "'" + email + "'";
            sql += ")";

            System.out.println("register- SQL : " + sql);

            try {
                pst = conn.prepareStatement(sql);
                int count = pst.executeUpdate();

                // check if updated
                if(count > 0) {
                    success = true;
                    Alert.Success("Registered succesfully!");

                    registerFrame.dispose();
                    Login.showWindow();

                } else {
                    Alert.Error("Registration unsuccessful");
                }
                conn.close(); //close connection
            } catch (Exception e) {
                Alert.Error("[Register.java] " + e.getMessage());
            }
        }

        return success;
    };

}
