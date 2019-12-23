import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class extractMessage {
    /* Get the JSON object and a string type (ex: participants, massages,...) */
    public static void getParticipants(JSONObject js, String value) {
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
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject js = (JSONObject) obj;

        getParticipants(js, "messages");
    }
}
