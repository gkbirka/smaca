package gr.smaca.common.event;

public interface EventListener<T extends Event> {
    void handle(T event);
}
