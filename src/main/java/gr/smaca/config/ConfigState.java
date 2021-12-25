package gr.smaca.config;

import gr.smaca.common.observable.Property;
import gr.smaca.common.state.State;

public class ConfigState implements State {
    private final Property<Config> config = new Property<>();

    public Property<Config> configProperty() {
        return config;
    }
}