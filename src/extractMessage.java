import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class extractMessage {

    /* JSON object */
    private JSONObject js;

    /* The constructor */
    public extractMessage(String filename) {
        try {
            js = (JSONObject) new JSONParser().parse(new FileReader(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Get the JSON object and a string type (ex: participants, massages,...) */
    public void getMessages(String value) {
        JSONArray participants = (JSONArray) js.get(value);
        Iterator itr = participants.iterator();

        for (int i = 0; i < participants.size(); i++) {
            Iterator<Map.Entry> iter = ((Map) itr.next()).entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry pair = iter.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
        }
    }

    /* main method */
    public static void main(String[] args) {
        AccessDir newDir = new AccessDir("");
        Path dir = Paths.get(newDir.getDirName(1));
        //go into a specific dir and get the content from "message_1.json"
        extractMessage em = newDir.getContent(dir);
        em.getMessages("messages");
    }
}
