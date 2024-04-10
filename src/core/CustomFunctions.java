package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import core.CustomConstants;

public class CustomFunctions {

    CustomConstants myConstant = new CustomConstants();

    String sender = myConstant.sender;
    String privateKey = myConstant.privateKey;
    String contract = myConstant.contract;
    String teks = myConstant.teks;

    String server_url = myConstant.server_url;

    public void debugger (){
        try {
			sendGET(server_url);

            String postParams = sendContract(sender,privateKey,contract,teks);
//            sendPOST("http://127.0.0.1:5000/set_message", postParams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void sendGET(String reqlink) throws IOException {

        URL obj = new URL(reqlink);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
//		con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request did not work.");
        }

    }

    private static void sendPOST(String reqlink, String params) throws IOException {
        URL obj = new URL(reqlink);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(params.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request did not work.");
        }
    }

    private static String sendContract(String senderAddress, String senderPk, String contractAddress, String value){
        String paramsBuilder = "sender_address=" +
                senderAddress +
                "&sender_pk=" +
                senderPk +
                "&address=" +
                contractAddress +
                "&value=" +
                value;


        return paramsBuilder;
    }

    public static String generateRandomString(int minLength, int maxLength) {
        // Set the range for length of the random string
        int length = minLength + new Random().nextInt(maxLength - minLength + 1);

        // Define characters allowed in the random string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Create a StringBuilder to store the random string
        StringBuilder sb = new StringBuilder();

        // Generate random characters to form the string
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        // Convert StringBuilder to String and return
        return sb.toString();
    }

    public static int calculateByteSize(String text) {
        return text.getBytes(StandardCharsets.UTF_8).length;
    }

}
