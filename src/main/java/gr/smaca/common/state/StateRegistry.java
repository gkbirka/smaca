package gr.smaca.common.state;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class StateRegistry {
    private final Map<Class<?>, Object> states = new HashMap<>();

    public <T extends State> void register(Class<T> stateClass, T t) {
        states.put(stateClass, t);
    }

    public <T extends State> void unregister(Class<T> stateClass) {
        states.remove(stateClass);
    }

    public <T extends State> T getState(Class<T> stateClass) {
        return (T) states.get(stateClass);
    }
}
