package uk.davidwei.perfmock.distribution;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Exponential implements Distribution {
    private final ExponentialDistribution delegate;

    public Exponential(double rate) {
        delegate = new ExponentialDistribution(rate);
    }

    @Override
    public double sample() {
        return delegate.sample();
    }
}