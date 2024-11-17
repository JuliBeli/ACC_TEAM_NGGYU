package mail;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MailService {

    private static final String API_KEY = "PLACE_HOLDER";

    public JsonNode sendEmail(String receiver, String content) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("URL")
                .basicAuth("api", API_KEY)
                .queryString("from", "NAME <EMAIL_ADDRESS>")
                .queryString("to", receiver)
                .queryString("subject", "SUBJECT")
                .queryString("text", content)
                .asJson();
        return request.getBody();
    }
}
