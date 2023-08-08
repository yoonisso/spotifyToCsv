import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader{
    
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
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