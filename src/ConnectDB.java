import java.sql.*;

public class ConnectDB {
    private final String url = "jdbc:mysql://localhost:1306";
    private final String user = "root";
    private final String pword = "PokemonGo123$";
    Connection connection;

    public ConnectDB() throws SQLException {
        connection = DriverManager.getConnection(url, user, pword);
    }

    public static void main(String[] args) {
        String createdb = "CREATE DATABASE IF NOT EXISTS wordBank;";
        String createTable = "CREATE TABLE IF NOT EXISTS wordbank.wordFreq(word varchar(255) not null, frequency int not null)";
        String insert = "INSERT INTO wordbank.wordFreq(word, frequency) VALUES ('Ash', 2)";
        ConnectDB db;
        try {
            db = new ConnectDB();
            db.sqlCmd(db.getConnection() ,createdb);
            db.sqlCmd(db.getConnection(), createTable);
            db.printTable(db.getConnection());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printTable(Connection connection) throws SQLException {
        String print = "SELECT * FROM wordbank.wordFreq";
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

    private Connection getConnection() {
        return connection;
    }
}
