package uk.davidwei.perfmock;

import org.jmock.internal.InvocationExpectation;

/**
 * A sequence of expectations; invocations can be constrained to occur in a strict 
 * order defined by a sequence.
 * 
 * @author nat
 */
public interface PerfSequence {
    void constrainAsNextInSequence(PerfInvocationExpectation expectation);
}
