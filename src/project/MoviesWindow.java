package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.awt.Image.SCALE_SMOOTH;

public class MoviesWindow {
    JFrame movieFrame;
//    Object [] userData = new Object[7];

    //for movie editing and adding
    private JTextField movieTitle;
    private JTextField movieDirector;
    private JTextField movieActor;
    private JTextField movieYear;
    private JTextField movieGenre;
    private JTextField username;
    private JPasswordField oldpassword;
    private JPasswordField newpassword;
    private JTextField firstname;
    private JTextField lastname;
    private JTextField email;

    //table for movie viewing
    private JTable moviesTable;
    private JTable moviesrentedTable;

    private static JButton btnEdit;
    private static JButton btnAdd;

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;
    DefaultTableModel tableMoviesModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    DefaultTableModel tableRentedModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };


    public void openMoviesWindow() {
        MoviesWindow window = new MoviesWindow();
        window.movieFrame.setVisible(true);
    }

    // main
    public static void main (String[] args) {
        MoviesWindow window = new MoviesWindow();
        window.movieFrame.setVisible(true);
    }

    public MoviesWindow(){
        //function for starting the whole program and database
        start();

        Object headers[] = {"Movie ID", "Movie Title", "Movie Director","Movie Actor", "Movie Year", "Movie Genre"};
        tableMoviesModel.setColumnIdentifiers(headers);
        moviesTable.setModel(tableMoviesModel);

        tableRentedModel.setColumnIdentifiers(headers);
        moviesrentedTable.setModel(tableRentedModel);

        sort();
        refreshMoviesTable();
        refreshRentedTable();
    }

    //default table model comes with table sorter based on column
    private void sort() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableMoviesModel);
        moviesTable.setRowSorter(sorter);
    }

    private void start(){
//        getUserData();
        // elements declaration
        movieFrame = new JFrame();
        movieFrame.setTitle("Movie Rental System");
        movieFrame.setBounds(100, 100, 900, 570);
        movieFrame.setLayout(null);
        movieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // automatic close when different frame
        movieFrame.getContentPane().setBackground(new Color(255, 255, 255));
        movieFrame.setLocationRelativeTo(null);
        movieFrame.setResizable(false);

        JTextArea datajtxt = new JTextArea();
        JTextArea receiptxt = new JTextArea();

        JLabel titlelbl = new JLabel("Title");
        JLabel directorlbl = new JLabel("Director");
        JLabel actorlbl = new JLabel("Actor");
        JLabel yearlbl = new JLabel("Year");
        JLabel genrelbl = new JLabel("Genre");

        //display account settings
        JLabel detailslbl = new JLabel("Your Details");
        detailslbl.setFont(new Font("Roboto", Font.BOLD, 20));
        JLabel usernamelbl = new JLabel(UserData.username);
        usernamelbl.setFont(new Font("Roboto", Font.BOLD, 14));
        JLabel firstnamelbl = new JLabel(UserData.firstname);
        firstnamelbl.setFont(new Font("Roboto", Font.PLAIN, 12));
        JLabel lastnamelbl = new JLabel(UserData.lastname);
        lastnamelbl.setFont(new Font("Roboto", Font.PLAIN, 12));
        JLabel emaillbl = new JLabel(UserData.email);
        emaillbl.setFont(new Font("Roboto", Font.PLAIN, 12));

        //for testing purposes only
//        JLabel usernamelbl = new JLabel("Nielsen");
//        usernamelbl.setFont(new Font("Roboto", Font.BOLD, 14));
//        JLabel firstnamelbl = new JLabel("Nielsen John");
//        firstnamelbl.setFont(new Font("Roboto", Font.PLAIN, 12));
//        JLabel lastnamelbl = new JLabel("Bernardo");
//        lastnamelbl.setFont(new Font("Roboto", Font.PLAIN, 12));
//        JLabel emaillbl = new JLabel("nielsenbernardo26@gmail.com");
//        emaillbl.setFont(new Font("Roboto", Font.PLAIN, 12));


        //display account data for editing
        JLabel editdetailslbl = new JLabel("Edit your Details");
        editdetailslbl.setFont(new Font("Roboto", Font.BOLD, 20));
        JLabel usernametxtlbl = new JLabel("Username");
        JLabel oldpasswordtxtlbl = new JLabel("Old Password");
        JLabel newpasswordtxtlbl = new JLabel("New Password");
        JLabel firstnametxtlbl = new JLabel("First Name");
        JLabel lastnametxtlbl = new JLabel("Last Name");
        JLabel emailtxtlbl = new JLabel("Email");

        ImageIcon profilePic = new ImageIcon("blankprofile.png");
        Image profileImage = profilePic.getImage();
        Image newprofileImage = profileImage.getScaledInstance(100, 100,  SCALE_SMOOTH);
        profilePic = new ImageIcon(newprofileImage);
        JLabel profilePiclbl= new JLabel(profilePic);

        //for editing and adding movies
        movieTitle = new JTextField();
        movieDirector = new JTextField();
        movieActor = new JTextField();
        movieYear = new JTextField();
        movieGenre = new JTextField();

        //for editing accoutn details
        username = new JTextField(UserData.username);
        oldpassword = new JPasswordField("");
        newpassword = new JPasswordField("");
        firstname = new JTextField(UserData.firstname);
        lastname = new JTextField(UserData.lastname);
        email = new JTextField(UserData.email);


        moviesTable = new JTable();
//        moviesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        moviesTable.setModel(new DefaultTableModel(
//                new Object[][] {
//                },
//                new String[] {
//                        "ID", "Title", "Director", "Actor", "Year", "Genre"
//                }
//        ));

        moviesrentedTable = new JTable();
//        moviesrentedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        moviesrentedTable.setModel(new DefaultTableModel(
//                new Object[][] {
//                },
//                new String[] {
//                        "ID", "Title", "Director", "Actor", "Year", "Genre"
//                }
//        ));

        JPanel navBar = new JPanel();
        JPanel navVerBar = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel movieListPanel = new JPanel();
        JPanel rentedMoviesPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel accountPanel = new JPanel();
        JPanel accPanel = new JPanel();
        JPanel updPanel = new JPanel();
        JPanel delPanel = new JPanel();
        JPanel movieForm = new JPanel();

        JButton addMovieBtn = new JButton("Add a Movie");
        addMovieBtn.setBackground(new Color(165, 194, 242));
        JButton removeMovieBtn = new JButton("Remove Movie");
        removeMovieBtn.setBackground(new Color(165, 194, 242));
        JButton rentMovieBtn = new JButton("Rent Movie");
        rentMovieBtn.setBackground(new Color(165, 194, 242));
        JButton editBtn = new JButton("Edit Movie");
        editBtn.setBackground(new Color(165, 194, 242));
        JButton loadDataBtn = new JButton("Load Movie");
        loadDataBtn.setBackground(new Color(165, 194, 242));
        JButton returnBtn = new JButton("Return Movie");
        returnBtn.setBackground(new Color(165, 194, 242));
        JButton infoBtn = new JButton("Information");
        JButton updateBtn = new JButton("Update Account");
        JButton deleteBtn = new JButton("Delete Account");

        JButton moviesBtn = new JButton("Movies");
        JButton yourMoviesBtn = new JButton("My Movies");
        JButton accountBtn = new JButton("Account");
        JButton logoutBtn = new JButton("Log out");

        JButton updateAccBtn = new JButton("Update Details");

        btnEdit = new JButton("Edit");
        btnAdd = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");

        navBar.setBackground(new Color(2, 9, 61));
        navVerBar.setBackground(new Color(2, 9, 61));



        contentPanel.setBackground(new Color(255, 255, 255));

        moviesBtn.setBackground(new Color(2, 9, 61));
        moviesBtn.setForeground(new Color(255,255,255));
        moviesBtn.setBorderPainted(false);

        yourMoviesBtn.setBackground(new Color(2, 9, 61));
        yourMoviesBtn.setForeground(new Color(255,255,255));
        yourMoviesBtn.setBorderPainted(false);

        accountBtn.setBackground(new Color(2, 9, 61));
        accountBtn.setForeground(new Color(255,255,255));
        accountBtn.setBorderPainted(false);

        infoBtn.setBackground(new Color(2, 9, 61));
        infoBtn.setForeground(new Color(255,255,255));
        infoBtn.setBorderPainted(false);

        updateBtn.setBackground(new Color(2, 9, 61));
        updateBtn.setForeground(new Color(255,255,255));
        updateBtn.setBorderPainted(false);

        deleteBtn.setBackground(new Color(2, 9, 61));
        deleteBtn.setForeground(new Color(255,255,255));
        deleteBtn.setBorderPainted(false);

        logoutBtn.setBackground(new Color(2, 9, 61));
        logoutBtn.setForeground(new Color(255,255,255));
        logoutBtn.setBorderPainted(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,navBar, contentPanel);
        contentPanel.setLayout(new CardLayout());

        JSplitPane settingsSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,navVerBar, infoPanel);
        infoPanel.setLayout(new CardLayout());

        contentPanel.add("card1", movieListPanel);
        contentPanel.add("card2", rentedMoviesPanel);
        contentPanel.add("card3", accountPanel);

        infoPanel.add("card1", accPanel);
        infoPanel.add("card2", updPanel);
        infoPanel.add("card3", delPanel);

        CardLayout cl, cl1;
        cl = (CardLayout)(contentPanel.getLayout());
        cl1 = (CardLayout)(infoPanel.getLayout());

        //elements layout
        navBar.setBounds(0,0, 900,35);
        navVerBar.setBounds(0,0,180,600);
        contentPanel.setBounds(0,35,900,600);
        infoPanel.setBounds(180,0,720,600);

        //for table
        movieFrame.setLayout(null);
        movieListPanel.setLayout(null);
        rentedMoviesPanel.setLayout(null);
        accountPanel.setLayout(null);
        navVerBar.setLayout(null);
        accPanel.setLayout(null);
        updPanel.setLayout(null);

        //movie list panel
        rentMovieBtn.setBounds(17, 320, 244,23);
        loadDataBtn.setBounds(17, 350, 244,23);
        addMovieBtn.setBounds(17, 380, 244,23);
        editBtn.setBounds(17, 410, 244,23);
        removeMovieBtn.setBounds(17, 440, 244,23);
        datajtxt.setBounds(275, 320, 280, 145);
        datajtxt.setBackground(new Color(250, 236, 235));


        //rented movies list
        returnBtn.setBounds(17, 320, 244,23);
        receiptxt.setBounds(275, 320, 280, 145);
        receiptxt.setBackground(new Color(250, 236, 235));

        //settings
        infoBtn.setBounds(0,10,180,35);
        updateBtn.setBounds(0,55,180,35);
        deleteBtn.setBounds(0,100,180,35);

        //acc panel
        detailslbl.setBounds(100, 5, 150,100);
        profilePiclbl.setBounds(110,100,100,100);
        usernamelbl.setBounds(110,200,200,80);
        firstnamelbl.setBounds(110,250,200,80);
        lastnamelbl.setBounds(110,280,200,80);
        emaillbl.setBounds(110,310,200,80);

        //edit acc
        editdetailslbl.setBounds(100, 5, 250,100);
        usernametxtlbl.setBounds(100,100,100,35);
        username.setBounds(200,100,300,35);
        oldpasswordtxtlbl.setBounds(100,150,100,35);
        oldpassword.setBounds(200,150,300,35);
        newpasswordtxtlbl.setBounds(100, 200,100,35);
        newpassword.setBounds(200, 200, 300, 35);
        firstnametxtlbl.setBounds(100,250,100,35);
        firstname.setBounds(200,250,300,35);
        lastnametxtlbl.setBounds(100, 300, 100, 35);
        lastname.setBounds(200, 300, 300,35);
        emailtxtlbl.setBounds(100, 350,100,35);
        email.setBounds(200, 350, 300, 35);
        updateAccBtn.setBounds(100,430, 250, 35 );

        JScrollPane scrollPane = new JScrollPane(moviesTable);
        scrollPane.setBounds(17, 30, 850, 270);

        JScrollPane scrollRentedPane = new JScrollPane(moviesrentedTable);
        scrollRentedPane.setBounds(17, 30, 850, 270);

        moviesBtn.setBounds(0,0,60,50);
        yourMoviesBtn.setBounds(60,0,60,50);
        accountBtn.setBounds(120,0,60,50);
        logoutBtn.setBounds(180,0,60,50);

        movieListPanel.setBackground(Color.white);
        rentedMoviesPanel.setBackground(Color.white);

        accPanel.setBackground(Color.white);
        updPanel.setBackground(Color.white);

        //movie form
        movieForm.setBounds(600, 350, 244, 149);
        movieForm.setLayout(null);
        //movie form contents
            titlelbl.setBounds(0, 0, 46, 14);
            directorlbl.setBounds(0, 25, 46, 14);
            actorlbl.setBounds(0, 50, 46, 14);
            yearlbl.setBounds(0, 75, 46, 14);
            genrelbl.setBounds(0, 100, 46, 14);

            movieTitle.setBounds(119,0, 125,20);
            movieDirector.setBounds(119,25, 125,20);
            movieActor.setBounds(119,50, 125,20);
            movieYear.setBounds(119,75, 125,20);
            movieGenre.setBounds(119,100, 125,20);

            btnEdit.setBounds(0, 125, 116, 23);
            btnAdd.setBounds(0, 125, 116, 23);
            cancelBtn.setBounds(128, 125, 116, 23);



        //add elements
        movieFrame.add(movieForm);
        movieFrame.add(splitPane);
        movieFrame.add(settingsSplit);
        movieFrame.add(navBar);
        movieFrame.add(contentPanel);

        movieForm.add(titlelbl);
        movieForm.add(movieTitle);
        movieForm.add(directorlbl);
        movieForm.add(movieDirector);
        movieForm.add(actorlbl);
        movieForm.add(movieActor);
        movieForm.add(yearlbl);
        movieForm.add(movieYear);
        movieForm.add(genrelbl);
        movieForm.add(movieGenre);
        movieForm.add(cancelBtn);
        movieForm.setVisible(false);

        navBar.add(moviesBtn);
        navBar.add(yourMoviesBtn);
        navBar.add(accountBtn);
        navBar.add(logoutBtn);

        movieListPanel.add(scrollPane);
        movieListPanel.add(rentMovieBtn);
        movieListPanel.add(loadDataBtn);
        movieListPanel.add(editBtn);
        movieListPanel.add(removeMovieBtn);
        movieListPanel.add(addMovieBtn);
        movieListPanel.add(datajtxt);

        rentedMoviesPanel.add(scrollRentedPane);
        rentedMoviesPanel.add(returnBtn);
        rentedMoviesPanel.add(receiptxt);

        navVerBar.add(infoBtn);
        navVerBar.add(updateBtn);
        navVerBar.add(deleteBtn);

        accountPanel.add(navVerBar);
        accountPanel.add(infoPanel);

        accPanel.add(detailslbl);
        accPanel.add(profilePiclbl);
        accPanel.add(usernamelbl);
        accPanel.add(firstnamelbl);
        accPanel.add(lastnamelbl);
        accPanel.add(emaillbl);

        updPanel.add(editdetailslbl);
        updPanel.add(usernametxtlbl);
        updPanel.add(username);
        updPanel.add(oldpasswordtxtlbl);
        updPanel.add(oldpassword);
        updPanel.add(newpasswordtxtlbl);
        updPanel.add(newpassword);
        updPanel.add(firstnametxtlbl);
        updPanel.add(firstname);
        updPanel.add(lastnametxtlbl);
        updPanel.add(lastname);
        updPanel.add(emailtxtlbl);
        updPanel.add(email);
        updPanel.add(updateAccBtn);

        moviesBtn.addActionListener(e -> {
            cl.show(contentPanel, "card1");
        });

        yourMoviesBtn.addActionListener(e -> {
            cl.show(contentPanel, "card2");
        });

        accountBtn.addActionListener(e -> {
            cl.show(contentPanel, "card3");
        });

        logoutBtn.addActionListener(e -> {
            movieFrame.dispose();
            Login.showWindow();
        });

        infoBtn.addActionListener(e -> {
            cl1.show(infoPanel, "card1");
        });

        updateBtn.addActionListener(e -> {
            cl1.show(infoPanel, "card2");
        });

        addMovieBtn.addActionListener(e -> {
            movieForm.setVisible(true);

            btnAdd.setEnabled(true);
            btnAdd.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e) {

                    if (movieTitle.getText().isEmpty() || movieDirector.getText().isEmpty()  || movieGenre.getText().isEmpty()){
                        Alert.Message("Alert", "Please fill out all fields.");
                    }

                    else {
                        Movies movie = createMovie();
                        boolean success = false;
                        success = addData(movie);

                        //display success or error message depending on boolean returned by addTable()
                        if(!success){
                            Alert.Error("Error occurred in the database process. Please try again.");

                        } else {
                            Alert.Success("Music was added successfully");

                            movieTitle.setText("");
                            movieDirector.setText("");
                            movieActor.setText("");
                            movieYear.setText("");
                            movieGenre.setText("");

                            movieForm.setVisible(false);

                        }
                    }
                }
            });

            try	{
                movieForm.remove(btnEdit);
            }
            catch(NullPointerException ex) {
                System.out.println("Safe"); // will throw exception if
            }

            movieForm.add(btnAdd);
            movieForm.revalidate(); // update changes
            movieForm.repaint(); // update changes

        });

        deleteBtn.addActionListener(e -> {
            //
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?",
                    "Alert",JOptionPane.YES_OPTION);

            if(response == 0) {
                deleteAccount();

                Alert.Success("Account deleted!");
            }

            movieFrame.dispose();
            Login lw = new Login();
            lw.showWindow();
        });

        updateAccBtn.addActionListener(e -> {
            infoPanel.revalidate();
            String newUsername = username.getText();
            String newPassword = newpassword.getText();
            String newFirstname = firstname.getText();
            String newLastname = lastname.getText();
            String oldPassword = oldpassword.getText();
            String newEmail = email.getText();


            if(newPassword.isEmpty() && oldPassword.isEmpty()){
                newPassword = UserData.password;
            }

            if(updateAccount(newUsername, newPassword, newFirstname, newLastname, newEmail)) {
                // update fields
                oldpassword.setText(newPassword);
                newpassword.setText("");
            }
        });

        loadDataBtn.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) moviesTable.getModel();

            if(moviesTable.getSelectedRow() < 0) {
                Alert.Error("Select a row to display");
            } else {

                conn = DatabaseConn.getConnection();

                int i = moviesTable.getSelectedRow();

                datajtxt.setText("");
                datajtxt.append("Movie Information: \n"
                        + "\nTitle:\t" + (String)model.getValueAt(i, 1).toString()
                        + "\nDirector:\t" + (String)model.getValueAt(i, 2).toString()
                        + "\nActor:\t" + (String)model.getValueAt(i, 3).toString()
                        + "\nYear:\t" + (String)model.getValueAt(i, 4).toString()
                        + "\nGenre:\t" + (String)model.getValueAt(i, 5).toString());

            }
        });

        removeMovieBtn.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) moviesTable.getModel();
            if(moviesTable.getSelectedRow() < 0) {
                Alert.Warning("Select a row to delete");
            }
            else {

                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this from your library?",
                        "Alert",
                        JOptionPane.YES_OPTION);
                if(response == 0) {
                    String id= (String)model.getValueAt(moviesTable.getSelectedRow(), 0);
                    removeData(id, true);
                    model.removeRow(moviesTable.getSelectedRow());
                }
            }
        });

        rentMovieBtn.addActionListener(e -> {
            conn = DatabaseConn.getConnection();

            if(moviesTable.getSelectedRow() < 0) {
                Alert.Warning("Select a row to rent");
            }
            int row_id = moviesTable.getSelectedRow();
            String id = (String) tableMoviesModel.getValueAt(moviesTable.getSelectedRow(), 0);
            int i = Integer.parseInt(id);

            Movies movie = createMoviewithID(i, row_id);
            boolean success = false;
            boolean success1 = false;
            removeData(id, false);
            success = rentMovie(movie);

            if (!success) {
                Alert.Error("Error occurred in the database process. Please try again.");
            } else {
                Alert.Message("Alert", "Movie was successfully rented!");
            }

        });

        returnBtn.addActionListener(e -> {
            conn = DatabaseConn.getConnection();

            if(moviesrentedTable.getSelectedRow() < 0) {
                Alert.Warning("Select a row to return");
            }

            int row_id = moviesrentedTable.getSelectedRow();
            String id = (String) tableRentedModel.getValueAt(moviesrentedTable.getSelectedRow(), 0);
            int i = Integer.parseInt(id);

            Movies movie = returnMoviewithID(i, row_id);
            boolean success = false;
            removeData(id, false);
            success = returnMovie(movie);

            if (!success) {
                Alert.Error("Error occurred in the database process. Please try again.");
            } else {
                Alert.Message("Alert", "Movie was successfully returned!");
            }

        });

        cancelBtn.addActionListener(e -> {
            movieTitle.setText("");
            movieDirector.setText("");
            movieActor.setText("");
            movieYear.setText("");
            movieGenre.setText("");

            movieForm.setVisible(false);
        });

        editBtn.addActionListener(e -> {
            movieForm.setVisible(true);
            conn = DatabaseConn.getConnection();

            int i = moviesTable.getSelectedRow(); // returns -1 if not table selected

            if (i < 0) {
                Alert.Error("Select a row to edit.");
                return;
            }

            movieTitle.setText((String)tableMoviesModel.getValueAt(i, 1).toString());
            movieDirector.setText((String)tableMoviesModel.getValueAt(i, 2).toString());
            movieActor.setText((String)tableMoviesModel.getValueAt(i, 3).toString());
            movieYear.setText((String)tableMoviesModel.getValueAt(i, 4).toString());
            movieGenre.setText((String)tableMoviesModel.getValueAt(i, 5).toString());

            btnEdit.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    Movies movie = createMovie();
                    boolean success = false;
                    success = editData(movie);

                    //display success or error message depending on boolean returned by addTable()
                    if (!success) {
                        Alert.Error("Error occurred in the database process. Please try again.");
                    } else {
                        Alert.Message("Alert", "Movie was successfully edited!");

                        movieTitle.setText("");
                        movieDirector.setText("");
                        movieActor.setText("");
                        movieYear.setText("");
                        movieGenre.setText("");

                        movieForm.setVisible(false);
                    }

                }
            });
            movieForm.add(btnEdit); // button needs to be remove first
            movieForm.revalidate(); // update changes
            movieForm.repaint(); // update changes
            if(moviesTable.getSelectedRow() < 0) {
                Alert.Error("Select a row to edit.");
            }
            else {
                movieForm.setVisible(true);
                btnEdit.setEnabled(true);
            }
        });
    }

    public boolean editData(Movies movie) {

        boolean success = false;

        conn = DatabaseConn.getConnection();

        String id= (String)tableMoviesModel.getValueAt(moviesTable.getSelectedRow(), 0);
        String sql = "UPDATE Movies SET movie_title =\"" + movie.getTitle()
                + "\", movie_director =\"" + movie.getMovieDirector()
                + "\", movie_actor =\"" + movie.getMovieActor()
                + "\", movie_year =\"" + movie.getMovieYear()
                + "\", movie_genre =\"" + movie.getMovieGenre()
                + "\" WHERE movie_id = '" + id + "'";

        System.out.println("modifyTable- SQL : " + sql);

        try {
            prep = conn.prepareStatement(sql);
            int count = prep.executeUpdate();	// count is used to determine if there was a row added in the database

            // check if success
            if(count > 0){
                success= true;	//return success so success message displays
                // then redraw the table
                DefaultTableModel model= (DefaultTableModel)moviesTable.getModel();
                model.setRowCount(0);
                refreshMoviesTable();
            }

        }

        catch (Exception e) {
            Alert.Warning("[modifyTable] " + e.getMessage());
        }

        return success;

    }

    private void removeData(String id, boolean flag) {

        conn= DatabaseConn.getConnection();

        if(conn != null){

            String sql= "DELETE FROM Movies WHERE movie_id = " + id;
            System.out.println("removeTable- SQL : " + sql);

            try {
                prep= conn.prepareStatement(sql);
                prep.executeUpdate();

            } catch (Exception e) {
                Alert.Warning("[removeTable] " + e.getMessage());
            }
            if (flag){
                Alert.Success("Movie deleted successfully");
            }
        }

    }

    public void refreshMoviesTable() {
        conn = DatabaseConn.getConnection();

        if(conn != null) {

            String sql = "SELECT movie_id, movie_title, movie_director, movie_actor, movie_year, movie_genre FROM Movies";
            System.out.println("refreshTable- SQL : " + sql);

            try {
                prep = conn.prepareStatement(sql);
                res = prep.executeQuery();
                Object [] columnData = new Object[6];

                while (res.next()) {
                    columnData[0] = res.getString("movie_id");
                    columnData[1] = res.getString("movie_title");
                    columnData[2] = res.getString("movie_director");
                    columnData[3] = res.getString("movie_actor");
                    columnData[4] = res.getString("movie_year");
                    columnData[5] = res.getString("movie_genre");
                    tableMoviesModel.addRow(columnData);
                }
                moviesTable.setSelectionBackground(new Color(189,124,42));

            }

            catch (Exception e) {
                Alert.Warning("[refreshTable] " + e.getMessage());
            }
        }

    }

    public void refreshRentedTable() {
        conn = DatabaseConn.getConnection();

        if(conn != null) {

            String sql = "SELECT  movie_id, movie_title, movie_director, movie_actor, movie_year, movie_genre FROM Rented" +
                    " WHERE user_id =" + "'" + UserData.user_id + "'";
            System.out.println("refreshTable- SQL : " + sql);

            try {
                prep = conn.prepareStatement(sql);
                res = prep.executeQuery();
                Object [] columnData = new Object[6];

                while (res.next()) {
                    columnData[0] = res.getString("movie_id");
                    columnData[1] = res.getString("movie_title");
                    columnData[2] = res.getString("movie_director");
                    columnData[3] = res.getString("movie_actor");
                    columnData[4] = res.getString("movie_year");
                    columnData[5] = res.getString("movie_genre");
                    tableRentedModel.addRow(columnData);
                }
                moviesTable.setSelectionBackground(new Color(189,124,42));

            }

            catch (Exception e) {
                Alert.Warning("[refreshTable] " + e.getMessage());
            }
        }

    }

    private boolean updateAccount( String newUsername, String newPassword,String newFirstname, String newLastname, String newEmail) {
        boolean result = false;

        if (!Validator.validateCredentials(newUsername, newPassword))
            return false;

        DatabaseConn.closeAllConnection();
        conn = DatabaseConn.getConnection();

        String sql = "UPDATE users SET username ='" + newUsername
                + "', password ='" + newPassword
                + "', first_name ='" + newFirstname
                + "', last_name ='" + newLastname
                + "', email ='" + newEmail
                + "' WHERE user_id = '" + UserData.user_id + "' ;";

        System.out.println("modifyTable- SQL : " + sql);

        try {
            prep = conn.prepareStatement(sql);
            int count = prep.executeUpdate();

            if(count > 0) {
                result = true;
                Alert.Success("Update success!");
            } else {
                Alert.Error("[UpdateAcc.java]Update Unsuccessful");
            }
        } catch (Exception e) {
            Alert.Error("[UpdateAcc.java] " + e.getMessage());
        }

        return result;
    }

    public boolean addData(Movies movie){

        boolean success= false;

        conn = DatabaseConn.getConnection();

        if(conn != null) {

            String sql = "INSERT INTO Movies (movie_title, movie_director, movie_actor, movie_year, movie_genre) ";
            sql += "VALUES (";
            sql += "'" + movie.getTitle() + "',";
            sql += "'" + movie.getMovieDirector() + "',";
            sql += "'" + movie.getMovieActor() + "',";
            sql += "'" + movie.getMovieYear() + "',";
            sql += "'" + movie.getMovieGenre() + "'";
            sql += ")";

            System.out.println("addTable- SQL : " + sql);

            try {
                prep = conn.prepareStatement(sql);
                int count = prep.executeUpdate();	// count is used to determine if there was a row added in the database

                // check if success
                if(count > 0){
                    success= true;	//return success so success message displays
                    // then redraw the table
                    DefaultTableModel model= (DefaultTableModel)moviesTable.getModel();
                    model.setRowCount(0);
                    refreshMoviesTable();
                }

            }

            catch (Exception e) {
                Alert.Warning("[addTable] " + e.getMessage());
            }
        }

        return success;

    }

    private boolean rentMovie(Movies movie){
        boolean success= false;

        conn = DatabaseConn.getConnection();

        if(conn != null) {

            String sql = "INSERT INTO Rented (user_id, movie_id, movie_title, movie_director, movie_actor, movie_year, movie_genre) ";
            sql += "VALUES (";
            sql += "'" + UserData.user_id + "',";
            sql += "'" + movie.getId() + "',";
            sql += "'" + movie.getTitle() + "',";
            sql += "'" + movie.getMovieDirector() + "',";
            sql += "'" + movie.getMovieActor() + "',";
            sql += "'" + movie.getMovieYear() + "',";
            sql += "'" + movie.getMovieGenre() + "'";
            sql += ")";

            System.out.println("addDataRent- SQL : " + sql);

            try {
                prep = conn.prepareStatement(sql);
                int count = prep.executeUpdate();	// count is used to determine if there was a row added in the database

                // check if success
                if(count > 0){
                    success= true;	//return success so success message displays
                    // then redraw the table
                    DefaultTableModel model= (DefaultTableModel)moviesTable.getModel();
                    model.setRowCount(0);
                    refreshMoviesTable();
                }

            }

            catch (Exception e) {
                Alert.Warning("[rentMovie] " + e.getMessage());
            }
        }

        return success;
    }

    private void deleteAccount() {

        Connection conn = null;
        PreparedStatement pst = null;

        conn= DatabaseConn.getConnection();

        if(conn != null){

            String sql= "DELETE FROM Users WHERE user_id = \"" + UserData.user_id + "\"";
            System.out.println("deleteAcc- SQL : " + sql);

            try {
                pst= conn.prepareStatement(sql);
                pst.executeUpdate();

            } catch (Exception e) {
                Alert.Warning("[removeTable] " + e.getMessage());
            }

        }

    }

    private Movies createMovie() {

        Movies movie = new Movies();

        String movieTitleText= movieTitle.getText();
        String movieDirectorText= movieDirector.getText();
        String movieActorText= movieActor.getText();
        String movieYearText= movieYear.getText();
        String movieGenreText= movieGenre.getText();

        movie.setTitle(movieTitleText);
        movie.setMovieDirector(movieDirectorText);
        movie.setMovieActor(movieActorText);
        movie.setMovieYear(movieYearText);
        movie.setMovieGenre(movieGenreText);

        return movie;
    }

    private Movies createMoviewithID(int id, int row_id) {

        Movies movie = new Movies();

        movie.setId(id);
        movie.setTitle((String)tableMoviesModel.getValueAt(row_id, 1).toString());
        movie.setMovieDirector((String)tableMoviesModel.getValueAt(row_id, 2).toString());
        movie.setMovieActor((String)tableMoviesModel.getValueAt(row_id, 3).toString());
        movie.setMovieYear((String)tableMoviesModel.getValueAt(row_id, 4).toString());
        movie.setMovieGenre((String)tableMoviesModel.getValueAt(row_id, 5).toString());

        return movie;
    }

    private Movies returnMoviewithID(int id, int row_id) {

        Movies movie = new Movies();

        movie.setId(id);
        movie.setTitle((String)tableRentedModel.getValueAt(row_id, 1).toString());
        movie.setMovieDirector((String)tableRentedModel.getValueAt(row_id, 2).toString());
        movie.setMovieActor((String)tableRentedModel.getValueAt(row_id, 3).toString());
        movie.setMovieYear((String)tableRentedModel.getValueAt(row_id, 4).toString());
        movie.setMovieGenre((String)tableRentedModel.getValueAt(row_id, 5).toString());

        return movie;
    }

    private boolean returnMovie(Movies movie){
        boolean success= false;

        conn = DatabaseConn.getConnection();

        if(conn != null) {

            String sql = "INSERT INTO Movies (movie_id, movie_title, movie_director, movie_actor, movie_year, movie_genre) ";
            sql += "VALUES (";
            sql += "'" + movie.getId() + "',";
            sql += "'" + movie.getTitle() + "',";
            sql += "'" + movie.getMovieDirector() + "',";
            sql += "'" + movie.getMovieActor() + "',";
            sql += "'" + movie.getMovieYear() + "',";
            sql += "'" + movie.getMovieGenre() + "'";
            sql += ")";

            System.out.println("addDataRent- SQL : " + sql);

            try {
                prep = conn.prepareStatement(sql);
                int count = prep.executeUpdate();	// count is used to determine if there was a row added in the database

                // check if success
                if(count > 0){
                    success= true;	//return success so success message displays
                    // then redraw the table
                    DefaultTableModel model= (DefaultTableModel)moviesTable.getModel();
                    model.setRowCount(0);
                    refreshMoviesTable();
                }

            }

            catch (Exception e) {
                Alert.Warning("[rentMovie] " + e.getMessage());
            }
        }
        return success;
    }
}
