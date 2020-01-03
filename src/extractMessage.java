import java.io.FileReader;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class extractMessage {
    private static final String MESSAGES = "messages";
    private String aMessage;
    private Map<String, Integer> wordFreq;
    private ConnectDB db;

    /* JSON object */
    private JSONObject js;

    /**
     * The constructor
     * @param filename
     */
    public extractMessage(String filename) {
        wordFreq = new TreeMap<>();
        try {
            js = (JSONObject) new JSONParser().parse(new FileReader(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the JSON object and a string type (ex: participants, massages,...)
     * @param value
     */
    public void getMessages(String value) {
        JSONArray participants = (JSONArray) js.get(value);
        Iterator itr = participants.iterator();

        for (int i = 0; i < participants.size(); i++) {
            Iterator<Entry> iter = ((Map) itr.next()).entrySet().iterator();
            while (iter.hasNext()) {
                Entry pair = iter.next();
                if (pair.getKey().equals("content") ) {
                    aMessage = (String) pair.getValue();
                    getFreq(aMessage);
                }
            }
        }
    }

    /**
     * Assign the words and its frequency into a TreeMap by String : Value
     * @param input
     */
    public void getFreq(String input) {
        String[] brokeUpString = input.split(" ");
        try {
            for (String word : brokeUpString) {
                if (wordFreq.containsKey(word)) {
                    wordFreq.replace(word, wordFreq.get(word), wordFreq.get(word) + 1);
                } else {
                    wordFreq.put(word, 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Query all the data in the tree into a database
     * @throws SQLException
     */
    public void toDb() throws SQLException {
        db = new ConnectDB();
        db.createDB("theWordBank");
        db.deleteTable("theWordFreqTable");
        db.createTable("theWordFreqTable");
        for (Entry<String, Integer> entry: wordFreq.entrySet()) {
            String word = replaceApos(entry.getKey());
            db.insert(word, entry.getValue());
        }
    }

    /**
     * Helper method to ensure the input string doesn't violate the reserved characters in SQL
     * @param word
     * @return
     */
    private String replaceApos(String word) {
        if (word.contains("'")) word = word.replaceAll("'", "''");
        if (word.contains("\\")) word = word.replaceAll("\\\\", "");
        return word;
    }

    /**
     * Print out the map
     */
    public void printMap() {
        for (Entry<String, Integer> entry: wordFreq.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Print from the database
     */
    public void printTable() {
        try {
            db.printTable(db.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {
        AccessDir newDir = new AccessDir("");
        extractMessage em = null;
        for (int i = 0; i < newDir.length(); i++) {
            Path dir = Paths.get(newDir.getDirName(i));
            //go into a specific dir and get the content from "message_1.json"
            em = newDir.getContentFromInbox(dir);
            em.getMessages(MESSAGES);
        }
        try {
            assert em != null;
            em.toDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Print from local table");
        em.printMap();
        System.out.println("Print from database");
        em.printTable();
    }
}
