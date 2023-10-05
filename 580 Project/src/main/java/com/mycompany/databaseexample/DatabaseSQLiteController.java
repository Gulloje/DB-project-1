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
    private VBox vBox; //like the outer section of ui

    @FXML
    private TextField nameTextField, authorTextField, yearTextField;
    
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

    /*
       ArrayList: Resizable-array implementation of the List interface. 
       Implements all optional list operations, and permits all elements, including null.
       ObservableList: A list that allows listeners to track changes when they occur
    */
    
    
    public DatabaseSQLiteController() throws SQLException {
        this.data = FXCollections.observableArrayList();
        this.memData = FXCollections.observableArrayList();
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
            System.out.println( rsMem.getString("Name"));
            
            while (rsMem.next()) {
                //Movie movie;
                //movie = new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("rating"));
                //System.out.println(movie.getId() + " - " + movie.getTitle() + " - " + movie.getRating() + " - " + movie.getYear());
                //data.add(movie);
                Member member;
                member = new Member(rsMem.getInt("LibraryNumber"), rsMem.getString("Name"));
                System.out.println( member.getLibraryNumber() + member.getName());
                memData.add(member);
            }

            rs.close();
            rsMem.close();
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
            /*==================================================================================
insert("3 Idiots",2009,"PG-13");
insert("8½",1963,"NOT RATED");
insert("A Beautiful Mind",2001,"PG-13");
insert("A Clockwork Orange",1971,"R");
insert("A Separation",2011,"PG-13");
insert("A Wednesday",2008,"NOT RATED");
insert("Alien",1979,"R");
insert("Aliens",1986,"R");
insert("All About Eve",1950,"NOT RATED");
insert("Amadeus",1984,"R");
insert("American Beauty",1999,"R");
insert("American History X",1998,"R");
insert("Amores Perros",2000,"R");
insert("Amélie",2001,"R");
insert("Andrei Rublev",1966,"NOT RATED");
insert("Annie Hall",1977,"PG");
insert("Apocalypse Now",1979,"R");
insert("Avengers: Infinity War",2018,"PG-13");
insert("Back to the Future",1985,"PG");
insert("Barry Lyndon",1975,"PG");
insert("Batman Begins",2005,"PG-13");
insert("Before Sunrise",1995,"R");
insert("Before Sunset",2004,"R");
insert("Ben-Hur",1959,"G");
insert("Bicycle Thieves",1948,"NOT RATED");
insert("Blade Runner",1982,"R");
insert("Blade Runner 2049",2017,"R");
insert("Braveheart",1995,"R");
insert("Butch Cassidy and the Sundance Kid",1969,"PG");
insert("Casablanca",1942,"PG");
insert("Casino",1995,"R");
insert("Castle in the Sky",1986,"PG");
insert("Catch Me If You Can",2002,"PG-13");
insert("Children of Heaven",1997,"PG");
insert("Chinatown",1974,"R");
insert("Cinema Paradiso",1988,"R");
insert("Citizen Kane",1941,"PG");
insert("City Lights",1931,"G");
insert("City of God",2002,"R");
insert("Coco",2017,"PG");
insert("Come and See",1985,"NOT RATED");
insert("Cool Hand Luke",1967,"GP");
insert("Dangal",2016,"NOT RATED");
insert("Das Boot",1981,"R");
insert("Dead Poets Society",1989,"PG");
insert("Diabolique",1955,"NOT RATED");
insert("Dial M for Murder",1954,"PG");
insert("Die Hard",1988,"R");
insert("Django Unchained",2012,"R");
insert("Donnie Darko",2001,"R");
insert("Double Indemnity",1944,"PASSED");
insert("Downfall",2004,"R");
insert("Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb",1964,"PG");
insert("Eternal Sunshine of the Spotless Mind",2004,"R");
insert("Fargo",1996,"R");
insert("Fight Club",1999,"R");
insert("Finding Nemo",2003,"G");
insert("For a Few Dollars More",1965,"R");
insert("Forrest Gump",1994,"PG-13");
insert("Full Metal Jacket",1987,"R");
insert("Gandhi",1982,"PG");
insert("Gangs of Wasseypur",2012,"N/A");
insert("Gladiator",2000,"R");
insert("Gone Girl",2014,"R");
insert("Gone with the Wind",1939,"G");
insert("Good Will Hunting",1997,"R");
insert("Goodfellas",1990,"R");
insert("Gran Torino",2008,"R");
insert("Grave of the Fireflies",1988,"NOT RATED");
insert("Groundhog Day",1993,"PG");
insert("Guardians of the Galaxy",2014,"PG-13");
insert("Hachi: A Dog's Tale",2009,"G");
insert("Hacksaw Ridge",2016,"R");
insert("Harry Potter and the Deathly Hallows: Part 2",2011,"PG-13");
insert("Heat",1995,"R");
insert("Hotel Rwanda",2004,"PG-13");
insert("How to Train Your Dragon",2010,"PG");
insert("Howl's Moving Castle",2004,"PG");
insert("Ikiru",1952,"NOT RATED");
insert("In the Mood for Love",2000,"PG");
insert("In the Name of the Father",1993,"R");
insert("Incendies",2010,"R");
insert("Inception",2010,"PG-13");
insert("Incredibles 2",2018,"PG");
insert("Indiana Jones and the Last Crusade",1989,"PG-13");
insert("Inglourious Basterds",2009,"R");
insert("Inside Out",2015,"PG");
insert("Interstellar",2014,"PG-13");
insert("Into the Wild",2007,"R");
insert("It Happened One Night",1934,"NOT RATED");
insert("It's a Wonderful Life",1946,"PG");
insert("Jaws",1975,"PG");
insert("Judgment at Nuremberg",1961,"NOT RATED");
insert("Jurassic Park",1993,"PG-13");
insert("Kill Bill: Vol. 1",2003,"R");
insert("L.A. Confidential",1997,"R");
insert("La Haine",1995,"NOT RATED");
insert("La La Land",2016,"PG-13");
insert("Lagaan: Once Upon a Time in India",2001,"PG");
insert("Lawrence of Arabia",1962,"PG");
insert("Life Is Beautiful",1997,"PG-13");
insert("Life of Brian",1979,"R");
insert("Like Stars on Earth",2007,"PG");
insert("Lock, Stock and Two Smoking Barrels",1998,"R");
insert("Logan",2017,"R");
insert("Léon: The Professional",1994,"R");
insert("M",1931,"NOT RATED");
insert("Mad Max: Fury Road",2015,"R");
insert("Mary and Max",2009,"NOT RATED");
insert("Memento",2000,"R");
insert("Memories of Murder",2003,"NOT RATED");
insert("Metropolis",1927,"NOT RATED");
insert("Million Dollar Baby",2004,"PG-13");
insert("Mission: Impossible - Fallout",2018,"PG-13");
insert("Modern Times",1936,"G");
insert("Monsters, Inc.",2001,"G");
insert("Monty Python and the Holy Grail",1975,"PG");
insert("Mr. Smith Goes to Washington",1939,"NOT RATED");
insert("My Father and My Son",2005,"N/A");
insert("My Neighbor Totoro",1988,"G");
insert("Nausicaä of the Valley of the Wind",1984,"PG");
insert("Network",1976,"R");
insert("No Country for Old Men",2007,"R");
insert("North by Northwest",1959,"NOT RATED");
insert("Oldboy",2003,"R");
insert("On the Waterfront",1954,"NOT RATED");
insert("Once Upon a Time in America",1984,"R");
insert("Once Upon a Time in the West",1968,"PG-13");
insert("One Flew Over the Cuckoo's Nest",1975,"R");
insert("Pan's Labyrinth",2006,"R");
insert("Paper Moon",1973,"PG");
insert("Paris, Texas",1984,"R");
insert("Paths of Glory",1957,"NOT RATED");
insert("Persona",1966,"NOT RATED");
insert("Pirates of the Caribbean: The Curse of the Black Pearl",2003,"PG-13");
insert("Platoon",1986,"R");
insert("Princess Mononoke",1997,"PG-13");
insert("Prisoners",2013,"R");
insert("Psycho",1960,"R");
insert("Pulp Fiction",1994,"R");
insert("Raging Bull",1980,"R");
insert("Raiders of the Lost Ark",1981,"PG");
insert("Ran",1985,"R");
insert("Rang De Basanti",2006,"NOT RATED");
insert("Rashomon",1950,"NOT RATED");
insert("Rear Window",1954,"PG");
insert("Rebecca",1940,"NOT RATED");
insert("Requiem for a Dream",2000,"R");
insert("Reservoir Dogs",1992,"R");
insert("Rocky",1976,"PG");
insert("Room",2015,"R");
insert("Rush",2013,"R");
insert("Saving Private Ryan",1998,"R");
insert("Scarface",1983,"R");
insert("Schindler's List",1993,"R");
insert("Se7en",1995,"R");
insert("Seven Samurai",1954,"NOT RATED");
insert("Sherlock Jr.",1924,"NOT RATED");
insert("Shutter Island",2010,"R");
insert("Singin'in the Rain",1952,"G");
insert("Snatch",2000,"R");
insert("Some Like It Hot",1959,"NOT RATED");
insert("Spirited Away",2001,"PG");
insert("Spotlight",2015,"R");
insert("Stalker",1979,"NOT RATED");
insert("Stand by Me",1986,"R");
insert("Star Wars: Episode IV - A New Hope",1977,"PG");
insert("Star Wars: Episode V - The Empire Strikes Back",1980,"PG");
insert("Star Wars: Episode VI - Return of the Jedi",1983,"PG");
insert("Sunrise",1927,"NOT RATED");
insert("Sunset Boulevard",1950,"NOT RATED");
insert("Taxi Driver",1976,"R");
insert("Terminator 2",1991,"R");
insert("The 400 Blows",1959,"NOT RATED");
insert("The Apartment",1960,"NOT RATED");
insert("The Bandit",1996,"N/A");
insert("The Big Lebowski",1998,"R");
insert("The Bourne Ultimatum",2007,"PG-13");
insert("The Bridge on the River Kwai",1957,"PG");
insert("The Dark Knight",2008,"PG-13");
insert("The Dark Knight Rises",2012,"PG-13");
insert("The Deer Hunter",1978,"R");
insert("The Departed",2006,"R");
insert("The Elephant Man",1980,"PG");
insert("The General",1926,"NOT RATED");
insert("The Godfather",1972,"R");
insert("The Godfather: Part II",1974,"R");
insert("The Gold Rush",1925,"NOT RATED");
insert("The Good, the Bad and the Ugly",1966,"R");
insert("The Grand Budapest Hotel",2014,"R");
insert("The Grapes of Wrath",1940,"NOT RATED");
insert("The Great Dictator",1940,"PASSED");
insert("The Great Escape",1963,"APPROVED");
insert("The Green Mile",1999,"R");
insert("The Handmaiden",2016,"NOT RATED");
insert("The Help",2011,"PG-13");
insert("The Hunt",2012,"R");
insert("The Intouchables",2011,"R");
insert("The Kid",1921,"NOT RATED");
insert("The Lion King",1994,"G");
insert("The Lives of Others",2006,"R");
insert("The Lord of the Rings: The Fellowship of the Ring",2001,"PG-13");
insert("The Lord of the Rings: The Return of the King",2003,"PG-13");
insert("The Lord of the Rings: The Two Towers",2002,"PG-13");
insert("The Maltese Falcon",1941,"NOT RATED");
insert("The Matrix",1999,"R");
insert("The Nights of Cabiria",1957,"NOT RATED");
insert("The Passion of Joan of Arc",1928,"NOT RATED");
insert("The Pianist",2002,"R");
insert("The Prestige",2006,"PG-13");
insert("The Princess Bride",1987,"PG");
insert("The Secret in Their Eyes",2009,"R");
insert("The Seventh Seal",1957,"NOT RATED");
insert("The Shawshank Redemption",1994,"R");
insert("The Shining",1980,"R");
insert("The Silence of the Lambs",1991,"R");
insert("The Sixth Sense",1999,"PG-13");
insert("The Sting",1973,"PG");
insert("The Terminator",1984,"R");
insert("The Thing",1982,"R");
insert("The Third Man",1949,"NOT RATED");
insert("The Treasure of the Sierra Madre",1948,"NOT RATED");
insert("The Truman Show",1998,"PG");
insert("The Usual Suspects",1995,"R");
insert("The Wages of Fear",1953,"NOT RATED");
insert("The Wizard of Oz",1939,"PG");
insert("The Wolf of Wall Street",2013,"R");
insert("There Will Be Blood",2007,"R");
insert("Three Billboards Outside Ebbing, Missouri",2017,"R");
insert("To Kill a Mockingbird",1962,"NOT RATED");
insert("Tokyo Story",1953,"NOT RATED");
insert("Touch of Evil",1958,"PG-13");
insert("Toy Story",1995,"G");
insert("Toy Story 3",2010,"G");
insert("Trainspotting",1996,"R");
insert("Unforgiven",1992,"R");
insert("Up",2009,"PG");
insert("V for Vendetta",2005,"R");
insert("Vertigo",1958,"PG");
insert("WALL·E",2008,"G");
insert("Warrior",2011,"PG-13");
insert("Whiplash",2014,"R");
insert("Wild Strawberries",1957,"NOT RATED");
insert("Wild Tales",2014,"R");
insert("Witness for the Prosecution",1957,"NOT RATED");
insert("Yojimbo",1961,"NOT RATED");
insert("Your Name.",2016,"PG");

==================================================================================*/
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

            //String sql = "DELETE FROM Books WHERE id=" + Integer.toString(id);
            String sql = "INSERT INTO CheckOut (id, name, author, year) SELECT id, name, author, year FROM Books WHERE id=" + Integer.toString(id);

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
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
