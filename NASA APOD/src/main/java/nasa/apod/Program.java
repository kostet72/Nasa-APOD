package nasa.apod;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class Program {

    public static String getUrl(String nasaUrl) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(nasaUrl));
            Answer answer = mapper.readValue(response.getEntity().getContent(), Answer.class);
            // Here you can change the bot response (you can look at the options for withdrawal in Answer.java). Now it is returning HD photo
            return answer.hdurl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
