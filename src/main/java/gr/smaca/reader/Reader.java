package gr.smaca.reader;

import com.impinj.octane.*;
import gr.smaca.common.observable.Property;
import gr.smaca.props.Props;
import gr.smaca.props.PropsState;

class Reader {
    private final ReaderService service;
    private final Property<PropsState> propertiesState = new Property<>();
    private final Property<ImpinjReader> reader = new Property<>();

    Reader(ReaderService service) {
        this.service = service;
    }

    private void initialize() throws Exception {
        Props properties = propertiesState.get().propertiesProperty().get();
        String hostname = properties.getReaderHost();

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

        reader.get().setTagReportListener(new ReadingPolicy(service));
        reader.get().applySettings(settings);
    }

    void handle(ReaderEvent event) {
        switch (event.getType()) {
            case START_READING:
                startReading();
                break;
            case STOP_READING:
                stopReading();
                break;
        }
    }

    private void startReading() {
        try {
            initialize();
            reader.get().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopReading() {
        try {
            reader.get().stop();
            reader.get().disconnect();
            reader.set(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Property<PropsState> propertiesStateProperty() {
        return propertiesState;
    }
}