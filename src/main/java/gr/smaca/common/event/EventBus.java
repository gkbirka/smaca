package gr.smaca.common.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes,unchecked")
public class EventBus {
    private final Map<Class, List<EventListener>> listeners = new HashMap<>();

    public void emit(Event event) {
        Class eventClass = event.getClass();
        List<EventListener> eventListeners = listeners.get(eventClass);

        for (EventListener eventListener : eventListeners) {
            eventListener.handle(event);
        }
    }

    public <T extends Event> void subscribe(Class<T> eventClass, EventListener<T> listener) {
        if (!listeners.containsKey(eventClass)) {
            listeners.put(eventClass, new LinkedList<>());
        }

        listeners.get(eventClass).add(listener);
    }

    public <T extends Event> void unsubscribe(Class<T> eventClass, EventListener<T> listener) {
        if (listeners.containsKey(eventClass)) {
            listeners.get(eventClass).remove(listener);
        }
    }
}