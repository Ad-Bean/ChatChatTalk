package Client.Service;

import java.io.IOException;
import java.util.Properties;

public class DataBuffer {
    public static Properties properties = new Properties();

    static {
        try {
            // read database setting
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
