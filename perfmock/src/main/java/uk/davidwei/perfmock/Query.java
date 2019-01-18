package uk.davidwei.perfmock;

import org.jmock.api.Invocation;

public class Query {
    private final long threadId;
    private final Invocation invocation;
    private final double creationTime;

    public Query(long threadId, Invocation invocation, double creationTime) {
        this.threadId = threadId;
        this.invocation = invocation;
        this.creationTime = creationTime;
    }
}