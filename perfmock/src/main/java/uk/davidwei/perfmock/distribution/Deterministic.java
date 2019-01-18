package uk.davidwei.perfmock.distribution;

public class Deterministic implements Distribution {
    private final double milliseconds;

    public Deterministic(double milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public double sample() {
        return milliseconds;
    }
}