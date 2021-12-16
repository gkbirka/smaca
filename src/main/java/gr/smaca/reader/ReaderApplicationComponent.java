package gr.smaca.reader;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.props.PropsState;

public class ReaderApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        PropsState propertiesState = context.getStateRegistry().getState(PropsState.class);

        ReaderService service = new ReaderService(context.getEventBus());
        Reader reader = new Reader(service);
        reader.propertiesStateProperty().set(propertiesState);

        context.getEventBus().subscribe(ReaderEvent.class, reader::handle);
    }
}