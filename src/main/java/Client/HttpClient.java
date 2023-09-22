package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class HttpClient{
    public String sendGetRequest(String url, String accessToken){
        try{
            URL obj = new URI(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            String responseCode = Integer.toString(connection.getResponseCode());
            logger.info("HTTP GET-Request Statuscode: " + responseCode);

            if(responseCode.equals("200")){
                InputStream inputStream = connection.getInputStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer response = new StringBuffer();
                String currentLineOfInput;

                while ((currentLineOfInput = input.readLine()) != null){
                    response.append(currentLineOfInput);
                }

                return response.toString();
            }
            return null;

        } catch(IOException | URISyntaxException e){
            e.printStackTrace();
            return null;
        }
    }

    public String sendPostRequest(String url, String requestBody){
        try{
            URL obj = new URI(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setDoOutput(true);

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (IOException | URISyntaxException e){
            e.printStackTrace();
            return null;
        }
    }
}