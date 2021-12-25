package gr.smaca.reader;

import com.impinj.octane.Tag;
import gr.smaca.common.event.EventBus;

import java.util.List;

class ReaderService {
    private final EventBus eventBus;

    //TODO Remove
    ReaderService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    void report(List<Tag> tags) {
        eventBus.emit(new TagReportEvent(tags));
    }
}