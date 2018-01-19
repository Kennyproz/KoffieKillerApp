package models.testing;

import com.fasterxml.jackson.databind.JsonNode;
import models.storage.CoffeeEncryptor;
import play.libs.Json;
import scala.util.parsing.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        try {

            URL url = new URL("http://localhost:9000/message/getmessage/" + 2);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            System.out.println(conn.getContent());

            String output;
            System.out.println("Output from Server .... \n");
            JsonNode node = null;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                node = Json.parse(output);
            }

            System.out.println(node);
            String key = node.get(0).get("encryptKey").asText();
            String message = node.get(0).get("encryptedMessage").asText();

            String decryptedMessage = CoffeeEncryptor.symmetricDecrypt(message, key);
            System.out.println(decryptedMessage);

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
