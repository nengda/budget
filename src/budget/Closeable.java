package budget;

/*
 * Interface for closeable data stream, e.g. database
 * connection
 * */
public interface Closeable {
    default void close() { return; }
}
