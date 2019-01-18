package uk.davidwei.perfmock;

public interface Simulation {
    double now();

    void schedule(Event q);
    void doStuff();
}