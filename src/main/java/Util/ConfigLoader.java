package Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader{
    private static final String PATH = "/config.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = ConfigLoader.class.getResourceAsStream(PATH)) {
               properties.load(input); 
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static String getClientId() {
        return properties.getProperty("api.spotify.client.id");
    }

    public static String getClientSecret() {
        return properties.getProperty("api.spotify.client.secret");
    }
}