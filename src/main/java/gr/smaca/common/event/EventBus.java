package gr.smaca.common.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("rawtypes,unchecked")
public class EventBus {
    private final Map<Class, CopyOnWriteArrayList<EventListener>> listeners = new HashMap<>();

    public synchronized void emit(Event event) {
        Class eventClass = event.getClass();
        List<EventListener> eventListeners = listeners.get(eventClass);

        for (EventListener eventListener : eventListeners) {
            eventListener.handle(event);
        }
    }

    public synchronized <T extends Event> void subscribe(Class<T> eventClass, EventListener<T> listener) {
        if (!listeners.containsKey(eventClass)) {
            listeners.put(eventClass, new CopyOnWriteArrayList<>());
        }

        listeners.get(eventClass).add(listener);
    }

    public synchronized <T extends Event> void unsubscribe(Class<T> eventClass, EventListener<T> listener) {
        if (listeners.containsKey(eventClass)) {
            listeners.get(eventClass).remove(listener);
        }
    }
}