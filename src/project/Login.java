package project;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.awt.Image.SCALE_SMOOTH;

public class Login {
    private JFrame loginFrame;
    private JTextField usernameJtxt;
    private JPasswordField passwordJpw;

    //used to call this class from other classes
    public static void showWindow() {
        Login window = new Login();
        window.loginFrame.setVisible(true);
    }

    //main
    public static void main(String[] args) {
        //if run directly through this code, display frame
        Login window = new Login();
        window.loginFrame.setVisible(true);

        //close program when "X" is clicked
        window.loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //database necessities
    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    //login constructor
    public Login(){

        //always refresh table data
//        reset();

        //frame settings
        loginFrame = new JFrame();
        loginFrame.setTitle("Movie Rental System");
        loginFrame.setBounds(100, 100, 700, 500);
        loginFrame.getContentPane().setBackground(new Color(255, 255, 255));

        //removing layout default settings
        loginFrame.setLayout(null);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //element declaration
        JPanel moviePicPanel = new JPanel();
        JLabel titlelbl = new JLabel("BlockNet Flixluster");
        titlelbl.setFont(new Font("Verdana", Font.BOLD, 17));

        JLabel lblCreators = new JLabel("Created by KG");
        lblCreators.setFont(new Font("Verdana", Font.PLAIN, 9));

        JLabel usernamelbl = new JLabel("Username");
        JLabel passwordlbl = new JLabel("Password");

        usernameJtxt = new JTextField();
        passwordJpw = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        loginBtn.setBackground(new Color(165, 194, 242));

        registerBtn.setBackground(new Color(187, 252, 229));

        ImageIcon moviePic = new ImageIcon("people-watching.png");
        Image movieIconimage = moviePic.getImage();
        Image movieIconnewimg = movieIconimage.getScaledInstance(800, 600,  SCALE_SMOOTH);
        moviePic = new ImageIcon(movieIconnewimg);
        JLabel moviePicLabel= new JLabel(moviePic);

        ImageIcon movieLogoIcon = new ImageIcon("movies-icon.png");
        Image movieLogoIconImage = movieLogoIcon.getImage();
        Image movieLogoIconNewimg = movieLogoIconImage.getScaledInstance(100, 100,  SCALE_SMOOTH);
        movieLogoIcon = new ImageIcon(movieLogoIconNewimg);
        JLabel movieIconLabel = new JLabel(movieLogoIcon);

        // positioning of elements
        moviePicPanel.setBounds(0,-50,350,530);

        titlelbl.setBounds(430, 170, 200,20);

        lblCreators.setBounds(470, 430, 150,20);

        usernamelbl.setBounds(390, 225, 81, 14);
        passwordlbl.setBounds(390, 265, 81, 14);

        usernameJtxt.setBounds(470, 224, 150, 25);
        passwordJpw.setBounds(470, 260, 150, 25);

        loginBtn.setBounds(430, 327, 180, 23);
        registerBtn.setBounds(430, 367, 180, 23);

        moviePicLabel.setBounds(0,0,350,550);

        movieIconLabel.setBounds(470, 45, 100,100);

        // displaying of elements
        loginFrame.add(moviePicPanel);

        loginFrame.add(titlelbl);

        loginFrame.add(lblCreators);

        loginFrame.add(usernamelbl);
        loginFrame.add(usernameJtxt);
        loginFrame.add(passwordlbl);
        loginFrame.add(passwordJpw);

        loginFrame.add(loginBtn);
        loginFrame.add(registerBtn);

        loginFrame.add(movieIconLabel);

        moviePicPanel.add(moviePicLabel);

        //button functions
        loginBtn.addActionListener(e -> { // login button

            String username = usernameJtxt.getText();
            @SuppressWarnings("deprecation")
            String password = passwordJpw.getText();

            if(!Validator.validateCredentials(username, password)) {
                Validator.displayError();
                return;
            }

            if(CheckData.verify(username, password) == 1){
                Alert.Success("Login successful");
                loginFrame.dispose(); // to close the login system
                MoviesWindow mw = new MoviesWindow();
                mw.openMoviesWindow();
            }

            else if (CheckData.verify(username, password) == 2){
                Alert.Success("Login successful");
                loginFrame.dispose();
                MoviesWindow mw = new MoviesWindow();
                mw.openMoviesWindow();
            }
//
            //close to login and go to movies window
            loginFrame.dispose();
            MoviesWindow mw = new MoviesWindow();
            mw.openMoviesWindow();
        });

        registerBtn.addActionListener(e -> {
            loginFrame.dispose();
            Register.showWindow();
        });
    }

    public void refreshTable() {

        if(conn != null) {

            String sql = "SELECT username, password, First_name, last_name FROM Users";
            System.out.println("refreshTable- SQL : " + sql);

            try {
                prep = conn.prepareStatement(sql);
                res = prep.executeQuery();
            }

            catch (Exception e) {
                Alert.Warning("[refreshTable] " + e.getMessage());
            }
        }

    }


}
