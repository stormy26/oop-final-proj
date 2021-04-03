package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MoviesWindow {
    JFrame movieFrame;

    //for movie editing and adding
    private JTextField movieTitle;
    private JTextField movieDirector;
    private JTextField movieActor;
    private JTextField movieYear;
    private JTextField movieGenre;

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
    }

    //default table model comes with table sorter based on column
    private void sort() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableMoviesModel);
        moviesTable.setRowSorter(sorter);
    }

    private void start(){

        // elements declaration
        movieFrame = new JFrame();
        movieFrame.setTitle("Movie Rental System");
        movieFrame.setBounds(100, 100, 900, 570);
        movieFrame.setLayout(null);
        movieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // automatic close when different frame
        movieFrame.getContentPane().setBackground(new Color(255, 255, 255));
        movieFrame.setLocationRelativeTo(null);
        movieFrame.setResizable(false);

        JPanel movieDetails = new JPanel();
        JPanel moviesPanel = new JPanel();
        JTextArea datajtxt = new JTextArea();
        JTextArea receiptxt = new JTextArea();

        JLabel titlelbl = new JLabel("Title");
        JLabel directorlbl = new JLabel("Director");
        JLabel actorlbl = new JLabel("Actor");
        JLabel yearlbl = new JLabel("Year");
        JLabel genrelbl = new JLabel("Genre");

        //for editing and adding movies
        movieTitle = new JTextField();
        movieDirector = new JTextField();
        movieActor = new JTextField();
        movieYear = new JTextField();
        movieGenre = new JTextField();

        moviesTable = new JTable();
        moviesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moviesTable.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Title", "Director", "Actor", "Year", "Genre"
                }
        ));

        moviesrentedTable = new JTable();
        moviesrentedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moviesrentedTable.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Title", "Director", "Actor", "Year", "Genre"
                }
        ));

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

        JButton addMovieBtn = new JButton("Add a Movie");
        JButton removeMovieBtn = new JButton("Remove Movie");
        JButton rentMovieBtn = new JButton("Rent Movie");
        JButton editBtn = new JButton("Edit Movie");
        JButton loadDataBtn = new JButton("Load Movie");
        JButton returnBtn = new JButton("Return Movie");
        JButton infoBtn = new JButton("Information");
        JButton updateBtn = new JButton("Update Account");
        JButton deleteBtn = new JButton("Delete Account");

        JButton moviesBtn = new JButton("Movies");
        JButton yourMoviesBtn = new JButton("My Movies");
        JButton accountBtn = new JButton("Account");
        JButton logoutBtn = new JButton("Log out");

        navBar.setBackground(new Color(2, 9, 61));

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
        movieListPanel.setLayout(null);
        rentedMoviesPanel.setLayout(null);
        accountPanel.setLayout(null);
        navVerBar.setLayout(null);

        //movie list panel
        rentMovieBtn.setBounds(17, 320, 244,23);
        loadDataBtn.setBounds(17, 350, 244,23);
        addMovieBtn.setBounds(17, 380, 244,23);
        editBtn.setBounds(17, 410, 244,23);
        removeMovieBtn.setBounds(17, 440, 244,23);
        datajtxt.setBounds(275, 320, 400, 145);
        receiptxt.setBounds(275, 320, 400, 145);

        //rented movies list
        returnBtn.setBounds(17, 320, 244,23);
        datajtxt.setBounds(275, 320, 400, 145);

        //settings
        infoBtn.setBounds(0,10,180,35);
        updateBtn.setBounds(0,55,180,35);
        deleteBtn.setBounds(0,100,180,35);

        JScrollPane scrollPane = new JScrollPane(moviesTable);
        scrollPane.setBounds(17, 30, 850, 270);

        JScrollPane scrollRentedPane = new JScrollPane(moviesrentedTable);
        scrollRentedPane.setBounds(17, 30, 850, 270);

        moviesBtn.setBounds(0,0,60,50);
        yourMoviesBtn.setBounds(60,0,60,50);
        accountBtn.setBounds(120,0,60,50);
        logoutBtn.setBounds(180,0,60,50);

        movieListPanel.setBackground(Color.black);
        rentedMoviesPanel.setBackground(Color.green);
        accountPanel.setBackground(Color.blue);

        accPanel.setBackground(Color.black);
        updPanel.setBackground(Color.green);
        delPanel.setBackground(Color.blue);

        //add elements
        movieFrame.add(splitPane);
        movieFrame.add(settingsSplit);
        movieFrame.add(navBar);
        movieFrame.add(contentPanel);

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

        deleteBtn.addActionListener(e -> {
            cl1.show(infoPanel, "card3");
        });
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
}
