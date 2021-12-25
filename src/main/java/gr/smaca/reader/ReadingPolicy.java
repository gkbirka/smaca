package gr.smaca.reader;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import gr.smaca.common.event.EventBus;

class ReadingPolicy implements TagReportListener {
    private final EventBus eventBus;

    ReadingPolicy(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        eventBus.emit(new TagReportEvent(report.getTags()));
    }
}