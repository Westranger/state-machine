package test.state.machine.guard;

import state.machine.State;
import state.machine.guard.Guard;
import test.state.machine.TestData;

public final class TestGuardA implements Guard<TestData> {

    private final double threshold;

    public TestGuardA(final double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean check(State<TestData> parentState, TestData data) {
        return data.getValue() > this.threshold;
    }

    @Override
    public String getDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append("x > ");
        sb.append(this.threshold);
        return sb.toString();
    }
}
