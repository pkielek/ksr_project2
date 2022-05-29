package fuzzy;

public interface SummarySet<T> {
    T And(T set);
    T Or(T set);
    void Not();
}
