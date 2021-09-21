package dev.tomheaton;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class NanoleafClient {

    // auth, state, effect, layout, rhythm, identify, info

    final static int BASE_PORT = 16021;

    String host;
    int port;
    String accessToken;
    String baseUrl;

    NanoleafClient(String host, int port, String accessToken) {
        this.host = host;
        this.port = port;
        this.accessToken = accessToken;
        //this.baseUrl = String.format("http://%s:%s/api/v1/", this.host, this.port);
        this.baseUrl = "https://reqres.in/api/"; // testing api url

    }

    NanoleafClient(String host, String accessToken) {
        this(host, BASE_PORT, accessToken);
    }

    private void get(String path) {
        System.out.println("GETTING");
        try {
            URL url = new URL(this.baseUrl + path);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.connect();

            int responseCode = con.getResponseCode();

            if (responseCode == 200) {
                try {
                    StringBuilder data = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());
                    while (scanner.hasNext()) {
                        data.append(scanner.nextLine());
                    }
                    System.out.println(data);
                    JSONObject object = new JSONObject(String.valueOf(data));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void post(String path, String body) {
        System.out.println("POSTING");
        try {
            URL url = new URL(this.baseUrl + path);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setDoOutput(true);
            con.connect();

            try (OutputStream outputStream = con.getOutputStream()) {
                byte[] input = body.getBytes(StandardCharsets.UTF_8);
                System.out.println(Arrays.toString(input));
                outputStream.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();

            if (responseCode == 201) {
                try {
                    StringBuilder data = new StringBuilder();
                    Scanner scanner = new Scanner(con.getInputStream());
                    while (scanner.hasNext()) {
                        data.append(scanner.nextLine());
                    }
                    System.out.println(data);
                    JSONObject object = new JSONObject(String.valueOf(data));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: extract common code from get/post requests
    private void request() {

    }

    // AUTH:

    // STATE:

    // EFFECT:

    // LAYOUT:

    // RHYTHM:

    // IDENTIFY:

    // INFO:

}
