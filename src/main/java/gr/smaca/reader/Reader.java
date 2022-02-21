package gr.smaca.reader;

import com.impinj.octane.AutoStopMode;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.ReportConfig;
import com.impinj.octane.ReportMode;
import com.impinj.octane.Settings;
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
            report.setMode(ReportMode.BatchAfterStop);

            settings.getAutoStop().setMode(AutoStopMode.Duration);
            settings.getAutoStop().setDurationInMs(1000);

            reader.setTagReportListener(policy);
            reader.applySettings(settings);
        } catch (Exception e) {
            e.printStackTrace();

            reader = null;
        }
    }

    void handle(ReaderEvent event) {
        switch (event.getType()) {
            case SCAN:
                scan();
                break;
            case DISCONNECT:
                disconnect();
                break;
        }
    }

    private void scan() {
        try {
            if (reader != null) {
                reader.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            if (reader != null) {

                reader.stop();
                reader.disconnect();
                reader = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}