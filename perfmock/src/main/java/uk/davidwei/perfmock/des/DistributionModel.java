package uk.davidwei.perfmock.des;

import org.jmock.api.Invocation;
import uk.davidwei.perfmock.PerfModel;
import uk.davidwei.perfmock.Event;
import uk.davidwei.perfmock.Query;
import uk.davidwei.perfmock.Simulation;
import uk.davidwei.perfmock.distribution.Distribution;

public class DistributionModel implements PerfModel {
    private final Distribution distribution;

    public DistributionModel(Distribution distribution) {
        this.distribution = distribution;
    }
    
    @Override
    public void query(Simulation sim, long threadId, Invocation invocation) {
        Query query = new Query(threadId, invocation, sim.now());
        Event event = new DistributionModelEvent(query, sim.now() + distribution.sample());
        sim.schedule(event);
    }

    private class DistributionModelEvent extends Event<Query> {
        DistributionModelEvent(Query query, double invokeTime) {
            super(query, invokeTime);
        }

        @Override
        public boolean invoke() {
            return true;
        }
    }
}