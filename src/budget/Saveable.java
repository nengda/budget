package budget;

/*
 * Interface for saveable data object, e.g. save to
 * external persistence layer
 * */
public interface Saveable {
    default void save() { return; }
}
