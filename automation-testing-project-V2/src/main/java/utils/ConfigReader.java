package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getValidEmail() {
        return get("valid.email");
    }

    public static String getValidPassword() {
        return get("valid.password");
    }

    public static String getInvalidEmail() {
        return get("invalid.email");
    }

    public static String getInvalidPassword() {
        return get("invalid.password");
    }

    public static String getRegisterEmail() {
        return get("register.email");
    }

    public static String getRegisterPhone() {
        return get("register.phone");
    }

    public static String getRegisterPassword() {
        return get("register.password");
    }
}
