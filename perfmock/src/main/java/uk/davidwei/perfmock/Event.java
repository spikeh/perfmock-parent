package uk.davidwei.perfmock;

public abstract class Event<T extends Query> {
    private final T query;
    private final double invokeTime;

    public Event(T query, double invokeTime) {
        this.query = query;
        this.invokeTime = invokeTime;
    }

    public T getQuery() {
        return query;
    }

    public double getInvokeTime() {
        return invokeTime;
    }

    public abstract boolean invoke();
}