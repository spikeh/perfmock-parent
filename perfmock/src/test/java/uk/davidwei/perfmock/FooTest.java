package uk.davidwei.perfmock;

import org.junit.Rule;
import org.junit.Test;
import uk.davidwei.perfmock.des.DistributionModel;
import uk.davidwei.perfmock.distribution.Deterministic;
import uk.davidwei.perfmock.junit4.JUnit4PerfMockery;
import uk.davidwei.perfmock.support.UserService;

public class FooTest {
    @Rule
    public JUnit4PerfMockery context = new JUnit4PerfMockery();

    private UserService userService = context.mock(UserService.class, new DistributionModel(new Deterministic(100.0)));

    @Test
    public void fooTestMethod() {
        context.checking(new Expectations() {{
            oneOf(userService).getUser("Alice");
        }});

        userService.getUser("Alice");

        context.assertIsSatisfied();
    }

    @Test
    public void barTestMethod() {
        context.checking(new Expectations() {{
            oneOf(userService).getUser("Alice");
        }});

        userService.getUser("Alice");

        context.assertIsSatisfied();
    }
}