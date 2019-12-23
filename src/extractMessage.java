import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class extractMessage {

    private JSONObject js;

    public extractMessage(String filename) {
        try {
            js = (JSONObject) new JSONParser().parse(new FileReader(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Get the JSON object and a string type (ex: participants, massages,...) */
    public void getParticipants(String value) {
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
        extractMessage eM = new extractMessage(args[0]);
        eM.getParticipants("messages");
    }
}
