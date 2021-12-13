package gr.smaca.props;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;

public class PropsApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
        PropsState state = new PropsState();
        context.getStateRegistry().register(PropsState.class, state);
    }

    @Override
    public void initComponent(ApplicationContext context) {
        PropsState state = context.getStateRegistry().getState(PropsState.class);
        Props props = new Props();
        state.propertiesProperty().set(props);
    }
}