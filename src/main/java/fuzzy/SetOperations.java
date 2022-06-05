package fuzzy;

public interface SetOperations<T> {
    T And(T set);
    T Or(T set);
    void Not();
}
