package gr.smaca.reader;

import com.impinj.octane.AntennaConfigGroup;
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
            ReportConfig config = settings.getReport();
            config.setIncludeAntennaPortNumber(true);
            config.setMode(ReportMode.Individual);
            settings.setRfMode(1002);

            AntennaConfigGroup antennas = settings.getAntennas();
            antennas.disableAll();
            antennas.enableById(new short[]{1});
            antennas.getAntenna((short) 1).setIsMaxRxSensitivity(false);
            antennas.getAntenna((short) 1).setIsMaxTxPower(false);
            antennas.getAntenna((short) 1).setTxPowerinDbm(20.0);
            antennas.getAntenna((short) 1).setRxSensitivityinDbm(-70);

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