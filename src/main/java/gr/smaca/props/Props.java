package gr.smaca.props;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Props {
    private static final String CONFIG_PATH = System.getProperty("user.home") + "/.smaca";
    private final String host;
    private final String port;
    private final String name;
    private final String username;
    private final String password;
    private final String readerHost;

    public Props() {
        Properties properties = load();
        this.host = properties.getProperty("db.host");
        this.port = properties.getProperty("db.port");
        this.name = properties.getProperty("db.name");
        this.username = properties.getProperty("db.username");
        this.password = properties.getProperty("db.password");
        this.readerHost = properties.getProperty("reader.host");
    }

    private Properties load() {
        try (InputStream input = new FileInputStream(CONFIG_PATH + "/config.properties")) {
            Properties properties = new Properties();

            properties.load(input);
            return properties;
        } catch (IOException ex) {
            save();
            return defaults();
        }
    }

    private void save() {
        try {
            Files.createDirectories(Paths.get(CONFIG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream output = new FileOutputStream(CONFIG_PATH + "/config.properties")) {
            Properties properties = defaults();

            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Properties defaults() {
        Properties properties = new Properties();

        properties.setProperty("db.host", "localhost");
        properties.setProperty("db.port", "3306");
        properties.setProperty("db.name", "name");
        properties.setProperty("db.username", "username");
        properties.setProperty("db.password", "password");
        properties.setProperty("reader.host", "hostname");
        return properties;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getReaderHost() {
        return readerHost;
    }
}