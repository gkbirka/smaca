package gr.smaca.common.observable;

public interface ChangeListener<V> {
    void onChanged(V value);
}