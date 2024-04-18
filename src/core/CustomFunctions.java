package core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CustomFunctions {

    CustomConstants myConstant = new CustomConstants();

    String teks = myConstant.teks;

    String server_url = myConstant.server_url;

    public void debugger (){
//        try {
////			sendGET(server_url);
//
//            String postParams = sendContract(myConstant.nodeTx,myConstant.nodeTxPk,myConstant.contract,teks);
////            sendPOST("http://127.0.0.1:5000/set_message", postParams);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
    private static String sendGET(String reqlink) throws IOException {

        String responMsg = "empty";

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

            responMsg = response.toString();
            System.out.println(responMsg);


        } else {
            System.out.println("GET request did not work.");
//            responMsg = ""
        }

        return responMsg;
    }

    public static String sendPOST(String reqlink, String params) throws IOException {

        String responStr = "empty";
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
            responStr = response.toString();
            JSONParser parser = new JSONParser();
            try {
                JSONObject jsonObj = (JSONObject) parser.parse(responStr);
                responStr = jsonObj.get("message_text").toString();


            } catch (ParseException e) {
                responStr = "error json parsing";
                throw new RuntimeException(e);

            }
            System.out.println(responStr);

        } else {
            System.out.println("POST request did not work.");
            responStr = "post request did not work";
        }
        return responStr;
    }

    public static String sendContract(String senderAddress, String senderPk, String contractAddress, String value){
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

    public static String getContract(String senderAddress, String txHash){

        return "sender_address=" +
                senderAddress +
                "&tx_hash=" +
                txHash;
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

    public static String loadKata(){
        JSONParser parser = new JSONParser();
        JSONArray kata;
        String kataTerpilih = null;
        Random rand = new Random();

        try {
            File file = new File("tambahan/kata.json");
            Object obj = parser.parse(new FileReader(file));

            JSONObject jsonObject =  (JSONObject) obj;

            kata = (JSONArray) jsonObject.get("kata");
            int jmlKata = kata.size();
//            System.out.println("RANGE : " + jmlKata);
            int angkaRandom  = rand.nextInt(jmlKata);
            int angkaIterasi = 0;
            Iterator<String> iterator = kata.iterator();
            while (iterator.hasNext()) {
                kataTerpilih = iterator.next();
//                System.out.println(kataTerpilih);

                if(angkaIterasi == angkaRandom){
                 break;
                }
                angkaIterasi++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return kataTerpilih;
    }
}
