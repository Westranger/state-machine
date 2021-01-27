package test.state.machine.action;

import state.machine.State;
import state.machine.action.Action;
import test.state.machine.TestData;

public final class TestActionB implements Action<TestData> {
    @Override
    public void execute(final State<TestData> fromState, final State<TestData> toState, final TestData data) {
        data.setValue(data.getValue() * 200.0);
        System.out.println("Transition B: calc 'x * 100.0'");
    }

    @Override
    public String getDescription() {
        return "x * 100.0";
    }
}
