package uk.davidwei.perfmock;

import org.jmock.api.Invocation;

public interface PerfModel {
    void query(Simulation sim, long threadId, Invocation invocation);
}