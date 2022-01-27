package gr.smaca.reader;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import gr.smaca.common.event.EventBus;

import java.util.ArrayList;
import java.util.List;

class ReadingPolicy implements TagReportListener {
    private final EventBus eventBus;

    ReadingPolicy(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        List<Tag> tags = new ArrayList<>();

        for (com.impinj.octane.Tag tag : report.getTags()) {
            tags.add(new Tag(tag.getEpc().toString()));
        }

        eventBus.emit(new TagReportEvent(tags));
    }
}