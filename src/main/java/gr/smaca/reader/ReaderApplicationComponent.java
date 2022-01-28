package gr.smaca.reader;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.config.Config;
import gr.smaca.config.ConfigState;

public class ReaderApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        ConfigState configState = context.getStateRegistry().getState(ConfigState.class);
        Config config = configState.getConfig();

        ReadingPolicy policy = new ReadingPolicy(context.getEventBus());
        Reader reader = new Reader(policy, config);

        context.getEventBus().subscribe(ReaderEvent.class, reader::handle);
    }
}