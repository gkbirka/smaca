package gr.smaca.reader;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

class Reader implements TagReportListener {
    private final ReaderService service;

    Reader(ReaderService service) {
        this.service = service;
        initialize();
    }

    private void initialize() {

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

    }

    private void stopReading() {

    }

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        service.report(report.getTags());
    }
}