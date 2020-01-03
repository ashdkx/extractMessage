import java.sql.*;

public class ConnectDB {
    //Login info
    private final String url = "jdbc:mysql://localhost:1306";
    private final String user = "root";
    private final String pword = "PokemonGo123$";

    private String dbName;
    private String tableName;
    private Connection connection;

    public ConnectDB() {
        try {
            connection = DriverManager.getConnection(url, user, pword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void deleteTable(String tableName) throws SQLException {
        String drop = "DROP TABLE IF EXISTS " + dbName + "." + tableName;
        sqlCmd(connection, drop);
    }

    public void printTable(Connection connection) throws SQLException {
        String print = "SELECT * FROM " + dbName + "." + tableName;
        PreparedStatement statement = connection.prepareStatement(print);
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

    /*
    public static void main(String[] args) {
        ConnectDB db;
        try {
            db = new ConnectDB();
            db.createDB("wordBank");
            db.deleteTable("wordFreq");
            db.createTable("wordFreq");
            String input = "Chuc not' cuoi' ngay\\ :D";
            String[] test = input.split(" ");
            for (String word: test) {
                if (word.contains("'")) word = word.replaceAll("'", "''");
                if (word.contains("\\")) word = word.replaceAll("\\\\", "");
                db.insert(word, 99);
            }

            //db.insert("Nah, If I don''t having anything to say, I just don''t.", 3);
            db.printTable(db.getConnection());
            //db.update(db.getConnection(), "Nah, If I don''t having anything to say, I just don''t.", 9);
            db.printTable(db.getConnection());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     */

}
