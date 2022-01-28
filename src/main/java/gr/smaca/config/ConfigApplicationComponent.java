package gr.smaca.config;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;

public class ConfigApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
        ConfigState state = new ConfigState();
        context.getStateRegistry().register(ConfigState.class, state);
    }

    @Override
    public void initComponent(ApplicationContext context) {
        ConfigState state = context.getStateRegistry().getState(ConfigState.class);
        Config config = new Config();
        state.setConfig(config);
    }
}