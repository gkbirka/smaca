package gr.smaca.reader;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import gr.smaca.common.event.EventBus;
import javafx.application.Platform;

import java.util.List;
import java.util.stream.Collectors;

class ReadingPolicy implements TagReportListener {
    private final EventBus eventBus;

    ReadingPolicy(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        List<Tag> tags = report.getTags().stream()
                .map(tag -> new Tag(tag.getEpc().toString()))
                .collect(Collectors.toList());

        Platform.runLater(() -> eventBus.emit(new TagReportEvent(tags)));
    }
}