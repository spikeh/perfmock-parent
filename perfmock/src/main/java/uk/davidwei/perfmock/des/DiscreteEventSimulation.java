package uk.davidwei.perfmock.des;

import uk.davidwei.perfmock.Event;
import uk.davidwei.perfmock.Query;
import uk.davidwei.perfmock.Simulation;

import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class DiscreteEventSimulation implements Simulation {
    public DiscreteEventSimulation() {
        System.out.println("New DiscreteEventSimulation");
    }

    private final Queue<Event>eventQueue = new PriorityBlockingQueue<>(21, new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            return Double.compare(e1.getInvokeTime(), e2.getInvokeTime());
        }
    });
    private double time = 0.0;

    @Override
    public double now() {
        return time;
    }

    @Override
    public void schedule(Event event) {
        eventQueue.offer(event);
    }

    @Override
    public void doStuff() {
        while (!eventQueue.isEmpty()) {
            Event e = eventQueue.poll();
            time = e.getInvokeTime();
            boolean end = e.invoke();
            if (end) {
                Query query = e.getQuery();
                System.out.println(time);
            }
        }
    }
}