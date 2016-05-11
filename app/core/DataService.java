package core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Tram;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mateusz on 2015-06-22.
 */
public class DataService {
    private static final String endPoint = "https://api.um.warszawa.pl/api/action/dbstore_get/?id=daeea0db-0f9a-498d-9c4f-210897daffd2&apikey=b6801572-5c30-4ee5-882a-b5c317565dce";


    public List<Tram> getData() throws IOException, ParseException {
        List<Tram> trams = new ArrayList<Tram>();


        JsonNode node = getJson();
        ArrayNode result = (ArrayNode) node.findPath("result");

        for (JsonNode r : result) {
            ArrayNode values = (ArrayNode) r.findPath("values");
            int line = 0;
            double latitude = 0.0;
            double longitude = 0.0;
            Date lastUpdate = null;

            for (JsonNode v : values) {
                String key = v.get("key").asText();
                JsonNode value = v.get("value");

                switch (key){
                    case "linia":
                        line = value.asInt();
                        break;
                    case "gps_szer":
                        latitude = value.asDouble();
                        break;
                    case "gps_dlug":
                        longitude = value.asDouble();
                        break;
                    case "ostatnia_aktualizacja":
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        Date date = format.parse(value.asText());
                        lastUpdate = date;
                        break;
                }
            }

            if (!contains(trams, line)){
                trams.add(new Tram(line, longitude, latitude, lastUpdate));
            }
        }

        return trams;
    }

    private boolean contains(List<Tram> trams, int line) {
        for (Tram t : trams) {
            if (t.getLine() == line)
                return true;
        }

        return false;
    }

    public JsonNode getJson() {
        F.Promise<WSResponse> data = WS.url(endPoint).get();

        JsonNode node = data.get(5, TimeUnit.SECONDS).asJson();

        return node;
    }
}
