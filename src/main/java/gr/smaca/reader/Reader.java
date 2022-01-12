package gr.smaca.reader;

import com.impinj.octane.*;
import gr.smaca.common.observable.Property;
import gr.smaca.config.Config;

class Reader {
    private final ReadingPolicy policy;
    private final Config config;
    private final Property<ImpinjReader> reader = new Property<>();

    Reader(ReadingPolicy policy, Config config) {
        this.policy = policy;
        this.config = config;
        initialize();
    }

    private void initialize() {
        try {
            String hostname = config.getReaderHost();

            if (hostname == null) {
                throw new Exception("Hostname can't be null");
            }

            reader.set(new ImpinjReader());
            reader.get().connect(hostname);

            Settings settings = reader.get().queryDefaultSettings();
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

            reader.get().setTagReportListener(policy);
            reader.get().applySettings(settings);
        } catch (Exception e) {
            e.printStackTrace();
            reader.set(null);
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
            if (reader.isPresent()) {
                reader.get().start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopReading() {
        try {
            if (reader.isPresent()) {
                reader.get().stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        if (reader.isPresent()) {

            reader.get().disconnect();
            reader.set(null);
        }
    }
}