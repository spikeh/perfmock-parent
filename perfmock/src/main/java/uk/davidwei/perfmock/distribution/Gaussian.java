package uk.davidwei.perfmock.distribution;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Gaussian implements Distribution {
    private final NormalDistribution delegate;

    public Gaussian(double mean, double stdev) {
        delegate = new NormalDistribution(mean, stdev);
    }

    @Override
    public double sample() {
        return delegate.sample();
    }
}