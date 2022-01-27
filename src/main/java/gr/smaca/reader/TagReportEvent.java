package gr.smaca.reader;

import gr.smaca.common.event.Event;

import java.util.List;

public class TagReportEvent implements Event {
    private final List<Tag> tags;

    public TagReportEvent(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }
}