package gr.smaca.reader;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;

public class ReaderApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        ReaderService service = new ReaderService(context.getEventBus());
        Reader reader = new Reader(service);

        context.getEventBus().subscribe(ReaderEvent.class, reader::handle);
    }
}