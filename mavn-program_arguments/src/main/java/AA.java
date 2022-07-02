import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Properties;

public class AA {
    public static void main(String[] args) {
        Properties properties = new Properties();
        for (String arg : args) {
            String[] split = arg.split("=");
            properties.setProperty(split[0].substring(2),split[1]);
        }
        System.out.println(properties);
        System.out.println(properties.getProperty("cc"));
    }
}
