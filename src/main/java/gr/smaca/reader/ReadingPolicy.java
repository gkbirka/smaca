package gr.smaca.reader;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

public class ReadingPolicy implements TagReportListener {
    private final ReaderService service;

    public ReadingPolicy(ReaderService service) {
        this.service = service;
    }

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        //TODO service.report(report.getTags());
        System.out.println(report.getTags());
    }
}