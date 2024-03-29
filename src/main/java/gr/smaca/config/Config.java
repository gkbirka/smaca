package gr.smaca.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static final String FOLDER_PATH = System.getProperty("user.home") + "/.smaca";
    //Database properties
    private final String databaseHost;
    private final String databasePort;
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
    //Reader properties
    private final String readerHost;

    Config() {
        Properties config = load();
        this.databaseHost = config.getProperty("db.host");
        this.databasePort = config.getProperty("db.port");
        this.databaseName = config.getProperty("db.name");
        this.databaseUsername = config.getProperty("db.username");
        this.databasePassword = config.getProperty("db.password");
        this.readerHost = config.getProperty("reader.host");
    }

    private Properties load() {
        try (InputStream input = new FileInputStream(FOLDER_PATH + "/config.properties")) {
            Properties properties = new Properties();

            properties.load(input);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();

            save();
            return defaults();
        }
    }

    private void save() {
        try {
            Files.createDirectories(Paths.get(FOLDER_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream output = new FileOutputStream(FOLDER_PATH + "/config.properties")) {
            Properties properties = defaults();

            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Properties defaults() {
        Properties config = new Properties();

        config.setProperty("db.host", "db_host");
        config.setProperty("db.port", "db_port");
        config.setProperty("db.name", "db_name");
        config.setProperty("db.username", "db_username");
        config.setProperty("db.password", "db_password");
        config.setProperty("reader.host", "reader_host");
        return config;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getReaderHost() {
        return readerHost;
    }
}