package gr.smaca.reader;

import com.impinj.octane.*;
import gr.smaca.config.Config;

class Reader {
    private final ReadingPolicy policy;
    private final Config config;
    private ImpinjReader reader;

    Reader(ReadingPolicy policy, Config config) {
        this.policy = policy;
        this.config = config;
        initialize();
    }

    private void initialize() {
        try {
            String hostname = config.getReaderHost();

            if (hostname == null) {
                throw new Exception("Reader's hostname can't be null.");
            }

            reader = new ImpinjReader();
            reader.connect(hostname);

            Settings settings = reader.queryDefaultSettings();
            ReportConfig report = settings.getReport();
            report.setIncludeAntennaPortNumber(true);
            report.setIncludeLastSeenTime(true);
            report.setIncludeFirstSeenTime(true);
            report.setIncludeSeenCount(true);
            report.setMode(ReportMode.BatchAfterStop);

            settings.getAutoStop().setMode(AutoStopMode.Duration);
            settings.getAutoStop().setDurationInMs(2500);

            reader.setTagReportListener(policy);
            reader.applySettings(settings);
        } catch (Exception e) {
            e.printStackTrace();

            reader = null;
        }
    }

    void handle(ReaderEvent event) {
        switch (event.getType()) {
            case START_READING:
                startReading();
                break;
            case STOP_READING:
                stopReading();
                break;
            case DISCONNECT:
                stopReading();
                disconnect();
                break;
        }
    }

    private void startReading() {
        try {
            if (reader != null) {
                reader.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopReading() {
        try {
            if (reader != null) {
                reader.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        if (reader != null) {

            reader.disconnect();
            reader = null;
        }
    }
}