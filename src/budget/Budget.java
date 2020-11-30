package budget;

/*
 * Interface for budget management
 * */
public interface Budget extends Saveable, Closeable {
    int get();
    boolean budgetDeduct(int consumption);

}
