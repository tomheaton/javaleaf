package dev.tomheaton;

public class App {

    public static void main(String[] args) {
        System.out.print("hello world - javaleaf\n");

        //NanoleafClient client = new NanoleafClient("192.168.x.x", ":accessToken:");
        NanoleafClient client = new NanoleafClient("192.168.x.x", 16021, ":accessToken:");
    }
}
