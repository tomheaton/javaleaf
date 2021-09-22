package dev.tomheaton;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NanoleafClient {

    final static int DEFAULT_PORT = 16021;

    String host;
    int port;
    String accessToken;
    String baseUrl;

    NanoleafClient(String host, int port, String accessToken) {
        this.host = host;
        this.port = port;
        this.accessToken = accessToken;
        this.baseUrl = "http://" + this.host + ":" + this.port + "/api/v1/" + this.accessToken;
    }

    NanoleafClient(String host, String accessToken) {
        this(host, DEFAULT_PORT, accessToken);
    }

    // TODO: this.
    // The result returned will be a 32-character authorization token that you will use in all of your subsequent calls.
    public void addUser() {
    }

    // TODO: this.
    // Requires an authorization token of an existing user.
    public void removeUser() {
    }

    // Get all light controller info
    public void getStatus() {
        this.get("/");
    }

    public void isOn() {
        this.get("/state/on");
    }

    public void turnOn() {
        this.put("/state", "{\"on\": {\"value\": true}}");
    }

    public void turnOff() {
        this.put("/state", "{\"on\": {\"value\": false}}");
    }

    public void setPower(boolean power) {
        this.put("/state", "{\"on\": {\"value\": " + power + "}}");
    }

    public void getBrightness() {
        this.get("/state/brightness");
    }

    public void setBrightness(int brightness) {
        this.put("/state", "{\"brightness\": {\"value\": " + brightness + "}}");
    }

    public void setBrightness(int brightness, int duration) {
        this.put("/state", "{\"brightness\": {\"value\": " + brightness + ", \"duration\": " + duration + "}}");
    }

    public void getHue() {
        this.get("/state/hue");
    }

    public void setHue(int hue) {
        this.put("/state", "{\"hue\": {\"value\": " + hue + "}}");
    }

    public void getSaturation() {
        this.get("/state/sat");
    }

    public void setSaturation(int saturation) {
        this.put("/state", "{\"sat\": {\"value\": " + saturation + "}}");
    }

    // Min and max values returned for ct are wrong. They should be 1200-6500, but are returned 0-100.
    public void getColorTemperature() {
        this.get("/state/ct");
    }

    // Min and max values returned for ct are wrong. They should be 1200-6500, but are returned 0-100.
    public void setColorTemperature(int colorTemperature) {
        this.put("/state", "{\"ct\": {\"value\": " + colorTemperature + "}}");
    }

    public void getColorMode() {
        this.get("/state/colorMode");
    }

    public void getSelectedEffect() {
        this.get("/effects/select");
    }

    public void setSelectedEffect(String effect) {
        this.put("/effects", "{\"select\": \"" + effect + "\"}}");
    }

    public void getEffects() {
        this.get("/effects/effectsList");
    }

    // TODO: this.
    public void setEffects(String effect) {
        this.put("/effects", "{\"write\" : {\"command\" : \"request\", \"animName\" : " + effect + "}}");
    }

    public void getLayout() {
        this.get("/panelLayout/layout");
    }

    public void getGlobalOrientation() {
        this.get("/panelLayout/globalOrientation");
    }

    public void setGlobalOrientation(int globalOrientation) {
        this.put("/panelLayout/globalOrientation", "{\"globalOrientation\": {\"value\": " + globalOrientation + "}}");
    }

    // Causes the panels to flash in unison. This is typically used to help users differentiate between multiple panels.
    public void identify() {
        this.put("/identify", null);
    }

    // Indicates if the Rhythm is connected to the Light Panels or not.
    public void getRhythmConnected() {
        this.get("/rhythm/rhythmConnected");
    }

    // Indicates if the Rhythm's microphone is currently active or not.
    public void getRhythmActive() {
        this.get("/rhythm/rhythmActive");
    }

    // Indicates the Rhythm's Id in the Light Panel system.
    public void getRhythmId() {
        this.get("/rhythm/rhythmId");
    }

    // Indicates the Rhythm's hardware version.
    public void getRhythmHardwareVersion() {
        this.get("/rhythm/hardwareVersion");
    }

    // Indicates the Rhythm's firmware version.
    public void getRhythmFirmwareVersion() {
        this.get("/rhythm/firmwareVersion");
    }

    // Indicates if an aux cable (3.5mm) is currently connected to the Rhythm.
    public void getRhythmAux() {
        this.get("/rhythm/auxAvailable");
    }

    // Allows the user to control the sound source for the Rhythm.
    // Writing 0 to this field sets the Rhythm's sound source to the microphone, and writing 1 to the field sets the sound source to the aux cable.
    public void getRhythmMode(int mode) {
        this.put("/rhythm/rhythmMode", "{\"rhythmMode\": " + mode + "}");
    }

    // Allows the user to control the sound source for the Rhythm.
    // Writing 0 to this field sets the Rhythm's sound source to the microphone, and writing 1 to the field sets the sound source to the aux cable.
    public void setRhythmMode() {
        this.get("/rhythm/rhythmMode");
    }

    // Indicates the position and orientation of the Rhythm in the Light Panels' layout.
    public void setRhythmPosition() {
        this.get("/rhythm/rhythmPos");
    }

    // Requests:
    enum Method { GET, PUT, POST, DELETE }

    private void get(String path) {
        this.request(Method.GET, path, null);
    }

    private void put(String path, String body) {
        this.request(Method.PUT, path, body);
    }

    private void post(String path, String body) {
        this.request(Method.POST, path, body);
    }

    private void delete(String path, String body) {
        this.request(Method.DELETE, path, body);
    }

    private void request(Method method, String path, @Nullable String body) {
        try {
            URL url = new URL(this.baseUrl + path);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod(method.toString());
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setDoOutput(true);
            con.connect();

            if (method.equals(Method.PUT) && body != null) {
                try (OutputStream outputStream = con.getOutputStream()) {
                    byte[] input = body.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }

            int responseCode = con.getResponseCode();

            if (responseCode == 200 || responseCode == 201) {
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
}
