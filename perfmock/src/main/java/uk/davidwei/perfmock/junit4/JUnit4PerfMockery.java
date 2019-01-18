package uk.davidwei.perfmock.junit4;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import uk.davidwei.perfmock.PerfMockery;

public class JUnit4PerfMockery extends PerfMockery implements MethodRule {
    private final JUnitRuleMockery delegate = new JUnitRuleMockery();

    public Statement apply(final Statement base, FrameworkMethod method, final Object target) {
        return delegate.apply(base, method, target);
    }
}