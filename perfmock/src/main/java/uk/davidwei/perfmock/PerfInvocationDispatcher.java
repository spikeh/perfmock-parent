package uk.davidwei.perfmock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.jmock.api.Expectation;
import org.jmock.api.ExpectationError;
import org.jmock.api.Invocation;

import org.jmock.internal.ExpectationCollector;
import org.jmock.internal.StateMachine;
import uk.davidwei.perfmock.des.DiscreteEventSimulation;

public class PerfInvocationDispatcher implements ExpectationCollector, SelfDescribing {
    private final Collection<Expectation> expectations;
    private final Collection<StateMachine> stateMachines;
    private final Map<String, PerfModel> models = new HashMap<String, PerfModel>();
    private Simulation sim = new DiscreteEventSimulation();

    public PerfInvocationDispatcher() {
        expectations = new ArrayList<Expectation>();
        stateMachines = new ArrayList<StateMachine>();
    }

    public PerfInvocationDispatcher(Collection<Expectation> theExpectations, Collection<StateMachine> theStateMachines) {
        expectations = theExpectations;
        stateMachines = theStateMachines;
    }

    public StateMachine newStateMachine(String name) {
        StateMachine stateMachine = new StateMachine(name);
        stateMachines.add(stateMachine);
        return stateMachine;
    }

    public void add(Expectation expectation) {
        expectations.add(expectation);
    }

    public void describeTo(Description description) {
        describe(description, expectations);
    }

    public void describeMismatch(Invocation invocation, Description description) {
        describe(description, describedWith(expectations, invocation));
    }

    private Iterable<SelfDescribing> describedWith(Iterable<Expectation> expectations, final Invocation invocation) {
        final Iterator<Expectation> iterator = expectations.iterator();
        return new Iterable<SelfDescribing>() {
            public Iterator<SelfDescribing> iterator() {
                return new Iterator<SelfDescribing>() {
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    public SelfDescribing next() {
                        return new SelfDescribing() {
                            public void describeTo(Description description) {
                                iterator.next().describeMismatch(invocation, description);
                            }
                        };
                    }

                    public void remove() {
                        iterator.remove();
                    }
                };
            }
        };
    }

    private void describe(Description description, Iterable<? extends SelfDescribing> selfDescribingExpectations) {
        if (expectations.isEmpty()) {
            description.appendText("no expectations specified: did you...\n" +
                    " - forget to start an expectation with a cardinality clause?\n" +
                    " - call a mocked method to specify the parameter of an expectation?");
        } else {
            description.appendList("expectations:\n  ", "\n  ", "", selfDescribingExpectations);
            if (!stateMachines.isEmpty()) {
                description.appendList("\nstates:\n  ", "\n  ", "", stateMachines);
            }
        }
    }

    public boolean isSatisfied() {
        for (Expectation expectation : expectations) {
            if (!expectation.isSatisfied()) {
                return false;
            }
        }
        return true;
    }

    public Object dispatch(Invocation invocation) throws Throwable {
        for (Expectation expectation : expectations) {
            if (expectation.matches(invocation)) {
                // Models specified using inTime() overrides class models
                PerfInvocationExpectation perfInvExp = (PerfInvocationExpectation)expectation;
                PerfModel model = perfInvExp.getPerfModel();
                if (model == null) {
                    Object invObj = invocation.getInvokedObject();
                    model = models.get(invObj.toString());
                }
                if (model != null) {
                    long threadId = Thread.currentThread().getId();
                    model.query(sim, threadId, invocation);
                    sim.doStuff();
                }
                return expectation.invoke(invocation);
            }
        }

        throw ExpectationError.unexpected("unexpected invocation", invocation);
    }

    public void setSimulation(Simulation sim) {
        this.sim = sim;
    }

    public void addPerfModel(String name, PerfModel model) {
        models.put(name, model);
    }
}
