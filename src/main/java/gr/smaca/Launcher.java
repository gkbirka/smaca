package gr.smaca;

import com.sun.javafx.util.Logging;

class Launcher {
    public static void main(String[] args) {
        Logging.getJavaFXLogger().disableLogging();
        Smaca.main(args);
    }
}