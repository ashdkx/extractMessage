import java.sql.*;

public class ConnectDB {
    //Login info
    private final String url = "jdbc:mysql://localhost:1306";
    private final String user = "root";
    private final String pword = "PokemonGo123$";

    private String dbName;
    private String tableName;
    Connection connection;

    public ConnectDB() throws SQLException {
        connection = DriverManager.getConnection(url, user, pword);
    }

    public void createDB(String dbName) throws SQLException {
        this.dbName = dbName;
        String createdb = "CREATE DATABASE IF NOT EXISTS " + dbName;
        sqlCmd(connection, createdb);
    }

    public void createTable(String tableName) throws SQLException {
        this.tableName = tableName;
        String createTable = "CREATE TABLE IF NOT EXISTS " + dbName + "." + tableName + "(word varchar(255) not null, frequency int not null)";
        sqlCmd(connection, createTable);
    }

    public void insert(String word, int frq) throws SQLException {
        String insert = "INSERT INTO " + dbName + "." + tableName + "(word, frequency) VALUES " + "('" + word + "', " + frq + ")";
        sqlCmd(connection, insert);
    }

    public void update(Connection connection, String word, int newVal) throws SQLException {
        String cmd = "UPDATE wordbank.wordFreq SET frequency = " + newVal + " WHERE word = '" + word + "'";
        sqlCmd(connection, cmd);
    }

    public void printTable(Connection connection) throws SQLException {
        String print = "SELECT * FROM " + dbName + "." + tableName;
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(print);

        while (results.next()) {
            String key = results.getString("word");
            int value = results.getInt("frequency");
            System.out.println(key + ": " + value);
        }
    }

    public void sqlCmd (Connection connection, String statement) throws SQLException {
        PreparedStatement cmd = connection.prepareStatement(statement);
        cmd.execute();
    }

    public Connection getConnection() {
        return connection;
    }

    public String getDbName() {
        return  dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public static void main(String[] args) {
        String createdb = "CREATE DATABASE IF NOT EXISTS wordBank;";
        String createTable = "CREATE TABLE IF NOT EXISTS wordbank.wordFreq(word varchar(255) not null, frequency int not null)";
        String insert = "INSERT INTO wordbank.wordFreq(word, frequency) VALUES ('Ash', 2)";
        ConnectDB db;
        try {
            db = new ConnectDB();
            db.createDB("wordBank");
            db.createTable("wordFreq");
            db.insert("hungry", 3);
            db.printTable(db.getConnection());
            db.update(db.getConnection(), "ash", 9);
            db.printTable(db.getConnection());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
