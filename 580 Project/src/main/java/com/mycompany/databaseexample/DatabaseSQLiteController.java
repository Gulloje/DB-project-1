package com.mycompany.databaseexample;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class DatabaseSQLiteController implements Initializable {

    @FXML
    private TableView tableView = new TableView<Book>();
    @FXML 
    private TableView memberView = new TableView<Member>();
    @FXML
    private TableView checkedOutView = new TableView<CheckOut>();

    @FXML
    private VBox vBox; //like the outer section of ui

    @FXML
    private TextField nameTextField, authorTextField, yearTextField, memberNumber;
    
    @FXML
    private TextField memNameText, libNumText;

    @FXML
    Label footerLabel;
    @FXML
    TableColumn id = new TableColumn("ID");

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        try {
            loadData();
        } catch (SQLException ex) {
            
            System.out.println(ex.toString());
        }
        intializeColumns();
        CreateSQLiteTable();
    }

    String databaseURL = "jdbc:sqlite:src/main/resources/com/mycompany/databaseexample/BooksDB.db";
   
    /* Connect to a sample database
     */
    private ObservableList<Book> data; //object that holds data
    private ObservableList<Member> memData;
    private ObservableList<CheckOut> checkOutData;

    /*
       ArrayList: Resizable-array implementation of the List interface. 
       Implements all optional list operations, and permits all elements, including null.
       ObservableList: A list that allows listeners to track changes when they occur
    */
    
    
    public DatabaseSQLiteController() throws SQLException {
        this.data = FXCollections.observableArrayList();
        this.memData = FXCollections.observableArrayList();
        this.checkOutData = FXCollections.observableArrayList();
    }

    private void intializeColumns() {

        id = new TableColumn("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));

        TableColumn name = new TableColumn("Name");
        name.setMinWidth(300);
        name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));

        TableColumn author = new TableColumn("Author");
        author.setMinWidth(200);
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));

        TableColumn year = new TableColumn("Year");
        year.setMinWidth(100);
        year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        tableView.setItems(data);
        tableView.getColumns().addAll(id, name, author, year);

        
        //initalize member table
        id = new TableColumn("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<Member, Integer>("id"));
        
      
        
        TableColumn memName = new TableColumn("Name");
        memName.setMinWidth(300);
        memName.setCellValueFactory(new PropertyValueFactory<Member, String>("Name"));
        
        TableColumn libNum = new TableColumn("LibraryNumber");
        libNum.setMinWidth(100);
        libNum.setCellValueFactory(new PropertyValueFactory<Member, Integer>("LibraryNumber"));
        
        memberView.setItems(memData);
        memberView.getColumns().addAll(memName, libNum);
        
        //checkedout Table
        id = new TableColumn("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<CheckOut, Integer>("id"));
        
      
        
        TableColumn checkedLibNum = new TableColumn("Library Number");
        checkedLibNum.setMinWidth(100);
        checkedLibNum.setCellValueFactory(new PropertyValueFactory<CheckOut, Integer>("LibraryNumber"));
        
        TableColumn checkedBookNum = new TableColumn("BookID");
        checkedBookNum.setMinWidth(100);
        checkedBookNum.setCellValueFactory(new PropertyValueFactory<CheckOut, Integer>("BookID"));
        
        TableColumn checkedBookName = new TableColumn("Book Name");
        checkedBookName.setMinWidth(300);
        checkedBookName.setCellValueFactory(new PropertyValueFactory<CheckOut, String>("name"));
        
        checkedOutView.setItems(checkOutData);
        checkedOutView.getColumns().addAll(checkedLibNum, checkedBookNum, checkedBookName);
        
        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }

    public void loadData() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
 
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");
            System.out.println(databaseURL);
            String sql = "SELECT * FROM Books;";
            String sql2 = "SELECT * FROM Members;";
            String sql3 = "SELECT * FROM CheckOut";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs.getInt("id") + rs.getString("name"));

            while (rs.next()) {
                //Movie movie;
                //movie = new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("rating"));
                //System.out.println(movie.getId() + " - " + movie.getTitle() + " - " + movie.getRating() + " - " + movie.getYear());
                //data.add(movie);
                Book book;
                book = new Book(rs.getInt("id"), rs.getString("name"), rs.getString("author"), rs.getInt("year"));
                System.out.println( book.getId() + book.getName() + book.getAuthor() + book.getYear());
                data.add(book);
            }
            
            stmt = conn.createStatement();
            ResultSet rsMem = stmt.executeQuery(sql2);
            //System.out.println( rsMem.getString("Name"));
            
            while (rsMem.next()) {
                Member member;
                member = new Member(rsMem.getInt("LibraryNumber"), rsMem.getString("Name"));
                System.out.println( member.getLibraryNumber() + member.getName());
                memData.add(member);
            }
            
            stmt = conn.createStatement();
            ResultSet rsCheck = stmt.executeQuery(sql3);
            //System.out.println( rsMem.getString("Name"));
            
            while (rsCheck.next()) {
                CheckOut checkedOut;
                checkedOut = new CheckOut(rsCheck.getInt("LibraryNumber"), rsCheck.getInt("BookID"), rsCheck.getString("name"));
                System.out.println( checkedOut.getLibraryNumber() + checkedOut.getBookID());
                checkOutData.add(checkedOut);
            }

            rs.close();
            rsMem.close();
            rsCheck.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void drawText() {
        //Drawing a text 
        Text text = new Text("The Book Database");

        //Setting the font of the text 
        text.setFont(Font.font("Edwardian Script ITC", 55));

        //Setting the position of the text 
//        text.setX(600);
//        text.setY(100);
        //Setting the linear gradient 
        Stop[] stops = new Stop[]{
            new Stop(0, Color.DARKSLATEBLUE),
            new Stop(1, Color.RED)
        };
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        //Setting the linear gradient to the text 
        text.setFill(linearGradient);
        // Add the child to the grid
        vBox.getChildren().add(text);

    }

    /**
     * Insert a new row into the movies table
     *
     * @param name
     * @param author
     * @param year
     * @throws java.sql.SQLException
     */
    
    public void insert(String name, String author, int year) throws SQLException {
        int last_inserted_id = 0;
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Books(name, author, year) VALUES(?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                last_inserted_id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("last_inserted_id " + last_inserted_id);
  
        //data.add(new Movie(last_inserted_id, title, year, rating));
        data.add(new Book(last_inserted_id, name, author, year));
    }

    @FXML
    public void handleAddMovie(ActionEvent actionEvent) {

        System.out.println("Name: " + nameTextField.getText() + "\nAuthor: " + authorTextField.getText() + "\nYear: " + yearTextField.getText());

        try {
            // insert a new rows
            insert(nameTextField.getText(), authorTextField.getText(),Integer.parseInt(yearTextField.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        nameTextField.setText("");
        authorTextField.setText("");
        yearTextField.setText("");

        footerLabel.setText("Record inserted into table successfully!");
    }

    private void CreateSQLiteTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Books (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	author text NOT NULL,\n"
                + "	year int NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(databaseURL);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            
            System.out.println("Table Created Successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRecord(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            String sql = "DELETE FROM Books WHERE id=" + Integer.toString(id);
            String sql2 = "DELETE FROM CheckOut WHERE BookID=" + Integer.toString(id);
            //String sql = "INSERT INTO CheckOut (id, name, author, year) SELECT id, name, author, year FROM Books WHERE id=" + Integer.toString(id);

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            
            tableView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            checkedOutView.getItems().remove(selectedIndex);
            System.out.println("Record Deleted Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @FXML
    private void handleDeleteAction(ActionEvent event) throws IOException {
        System.out.println("Delete Movie");
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                Book book = (Book) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + book.getId());
                System.out.println("Name: " + book.getName());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("year: " + book.getYear());
                deleteRecord(book.getId(), selectedIndex);
            }

        }
        if (checkedOutView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = checkedOutView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
               
                CheckOut checkOut = (CheckOut) checkedOutView.getSelectionModel().getSelectedItem();
                
                deleteRecord(checkOut.getBookID(), selectedIndex);
            }

        }
    }
    
        public void checkOut(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            int memberToInsert =  Integer.valueOf(memberNumber.getText());
            //String sql = "INSERT INTO CheckOut (id, name, author, year) SELECT id, name, author, year FROM Books WHERE id=" + Integer.toString(id);
            String sql = "INSERT INTO CheckOut (BookID, LibraryNumber, Name, Author, Year) SELECT BOOKS.id, Members.LibraryNumber, BOOKS.name, Books.author, Books.year FROM Books, Members "
                    +"WHERE Books.id = " +Integer.toString(id) + " AND Members.LibraryNumber= " +memberToInsert;
            String deleteSQL = "DELETE FROM Books WHERE id=" +Integer.toString(id);       
                    //System.out.println(sql);
                    
                    /*INSERT INTO CheckOut (BookID, MemberID)
                    SELECT Books.id, Members.LibraryNumber
                    FROM Books, Members
                    WHERE Books.id = 100
                    AND Members.LibraryNumber = 222;*/
                    
            Statement stmt = conn.createStatement();
            if (memberNumber.getText()!= null) {
                stmt.executeUpdate(sql);
                stmt.executeUpdate(deleteSQL);
            }
            //stmt.executeUpdate(sql);
            //stmt.executeUpdate(deleteSQL);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (memberNumber.getText()!= null) {
                tableView.getItems().remove(selectedIndex);
            }
            //tableView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            System.out.println("Record Checked Out Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    @FXML
    private void handleCheckOutAction(ActionEvent event) throws IOException {
        System.out.println("Check Out Book");
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle checkOut Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                Book book = (Book) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + book.getId());
                System.out.println("Name: " + book.getName());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("year: " + book.getYear());
                checkOut(book.getId(), selectedIndex);
            }

        }
    }
    
    public void checkIn(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            //String sql = "INSERT INTO CheckOut (id, name, author, year) SELECT id, name, author, year FROM Books WHERE id=" + Integer.toString(id);
            String sql = "INSERT INTO Books (id, name, author, year) SELECT BookID, name, author, year FROM CheckOut WHERE BookID =" + Integer.toString(id);
            String sql2 = "DELETE FROM CheckOut WHERE BookID =" +Integer.toString(id);
            
            
                    /*INSERT INTO CheckOut (BookID, MemberID)
                    SELECT Books.id, Members.LibraryNumber
                    FROM Books, Members
                    WHERE Books.id = 100
                    AND Members.LibraryNumber = 222;*/
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            checkedOutView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            System.out.println("Record Checked in Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    @FXML
    private void handleCheckInAction(ActionEvent event) throws IOException {
        System.out.println("Check In Book");
        //Check whether item is selected and set value of selected item to Label
        if (checkedOutView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = checkedOutView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle checkIn Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                CheckOut checkOut = (CheckOut) checkedOutView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + checkOut.getBookID());
                System.out.println("Name: " + checkOut.getName());
                System.out.println("Author: " + checkOut.getAuthor());
                System.out.println("year: " + checkOut.getYear());
                checkIn(checkOut.getBookID(), selectedIndex);
            }

        }
    }

    Integer index = -1;

    @FXML
    private void showRowData() {

        index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        System.out.println("showRowData");
        System.out.println(index);
        Book book = (Book) tableView.getSelectionModel().getSelectedItem();
        //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
        System.out.println("ID: " + book.getId());
        System.out.println("Name: " + book.getName());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Year: " + book.getYear());

        nameTextField.setText(book.getName());
        authorTextField.setText(book.getAuthor());
        yearTextField.setText(Integer.toString(book.getYear()));

        String content = "Id= " + book.getId() + "\nName= " + book.getName() + "\nAuthor=  " + book.getAuthor() + "\nYear= " + Integer.toString(book.getYear());

    }


    @SuppressWarnings("empty-statement")
    public ObservableList<Book> search(String _name, String _author, String _year) throws SQLException {
        ObservableList<Book> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        CreateSQLiteTable();
        String sql = "Select * from Books where true"; //querey

        if (!_name.isEmpty()) {
            sql += " and name like '%" + _name + "%'";
        }
        if (!_year.isEmpty()) {
            sql += " and year ='" + _year + "'";
        }

        if (!_author.isEmpty()) {
            sql += " and author like '%" + _author + "%'";
        }

        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(databaseURL);
                Statement stmt = conn.createStatement()) {
            // create a ResultSet

            ResultSet rs = stmt.executeQuery(sql); //
            // checking if ResultSet is empty
            if (rs.next() == false) {
                System.out.println("ResultSet in empty");
            } else {
                // loop through the result set
                do {

                    int recordId = rs.getInt("id");
                    String name = rs.getString("name");
                    String author = rs.getString("author");
                    int year = Integer.parseInt(rs.getString("year"));
                 

                    //Movie movie = new Movie(recordId, title, year, rating);
                    Book book = new Book(recordId, name, author, year);
                    searchResult.add(book);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return searchResult;
    }

    @FXML
    private void handleSearchAction(ActionEvent event) throws IOException, SQLException {
        String _name = nameTextField.getText().trim();
        String _author = authorTextField.getText().trim();
        String _year = yearTextField.getText().trim();
        ObservableList<Book> tableItems = search(_name, _author, _year);
        tableView.setItems(tableItems);

    }
    
    

    @FXML
    private void handleShowAllRecords(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(data);

    }

    /**
     * Update a record in the movies table
     *
     * @param name
     * @param year
     * @param author
     * @throws java.sql.SQLException
     */
    public void update(String name,  String author, int year, int selectedIndex, int id) throws SQLException {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);
            String sql = "UPDATE Books SET name = ?, author =?, year =? Where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, author);
            pstmt.setString(3, Integer.toString(year));;
            pstmt.setString(4, Integer.toString(id));

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @FXML
    private void handleUpdateRecord(ActionEvent event) throws IOException, SQLException {

        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                Book book = (Book) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + book.getId());

                try {
                    // insert a new rows
                    //update(nameTextField.getText(), Integer.parseInt(authorTextField.getText()), yearTextField.getText(), selectedIndex, movie.getId());
                    update(nameTextField.getText(), authorTextField.getText(), Integer.parseInt(yearTextField.getText()), selectedIndex, book.getId());
                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                nameTextField.setText("");
                authorTextField.setText("");
                yearTextField.setText("");

                footerLabel.setText("Record updated successfully!");
                data.clear();
                loadData();
                tableView.refresh();
            }

        }

    }

    @FXML
    private void sidebarShowAllRecords() {
        tableView.setItems(data);
    }

    @FXML
    private void sidebarAddNewRecord() {
        System.out.println("Title: " + nameTextField.getText() + "\nYear: " + authorTextField.getText() + "\nRating: " + yearTextField.getText());

        try {
            // insert a new rows
            //insert(nameTextField.getText(), Integer.parseInt(authorTextField.getText()), yearTextField.getText());
            insert(nameTextField.getText(), authorTextField.getText(), Integer.parseInt(yearTextField.getText()));
            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        nameTextField.setText("");
        authorTextField.setText("");
        yearTextField.setText("");

        footerLabel.setText("Record inserted into table successfully!");

    }

    @FXML
    private void sidebarDeleteRecord() {
        System.out.println("Delete Movie");
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                Book book = (Book) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + book.getId());
                System.out.println("Name: " + book.getName());
                System.out.println("Auhtor: " + book.getAuthor());
                System.out.println("Year: " + book.getYear());
                deleteRecord(book.getId(), selectedIndex);
            }

        }
    }

    @FXML
    private void sidebarSearch() throws SQLException {
        String _name = nameTextField.getText().trim();
        String _author = authorTextField.getText().trim();
        String _year = yearTextField.getText().trim();
        ObservableList<Book> tableItems = search(_name, _author, _year);
        tableView.setItems(tableItems);
    }

    
     @FXML
    private void sidebarUpdateRecord() throws SQLException {
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                Book book = (Book) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + book.getId());

                try {
                    // insert a new rows
                    //update(nameTextField.getText(), Integer.parseInt(authorTextField.getText()), yearTextField.getText(), selectedIndex, movie.getId());
                    update(nameTextField.getText(), authorTextField.getText(), Integer.parseInt(yearTextField.getText()), selectedIndex, book.getId());
                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                nameTextField.setText("");
                authorTextField.setText("");
                yearTextField.setText("");

                footerLabel.setText("Record updated successfully!");
                data.clear();
                loadData();
                tableView.refresh();
            }
        }
    }   
    
}
