package test.state.machine.action;

import state.machine.State;
import state.machine.action.Action;
import test.state.machine.TestData;

public final class TestActionA implements Action<TestData> {
    @Override
    public void execute(final State<TestData> fromState, final State<TestData> toState, final TestData data) {
        data.setValue(data.getValue() / 10.0);
        System.out.println("Transition A: calc 'x / 10.0'");
    }

    @Override
    public String getDescription() {
        return "x / 10.0";
    }
}
