package gr.smaca.props;

import gr.smaca.common.observable.Property;
import gr.smaca.common.state.State;

public class PropsState implements State {
    private final Property<Props> properties = new Property<>();

    public Property<Props> propertiesProperty() {
        return properties;
    }
}
