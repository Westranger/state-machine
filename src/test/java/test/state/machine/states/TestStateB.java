package test.state.machine.states;

import state.machine.State;
import test.state.machine.TestData;

public final class TestStateB extends State<TestData> {

    @Override
    public TestData onEntry(TestData data) {
        data.setValue(data.getValue() / 3.0);
        System.out.println("State B onEntry: calc 'x / 3'");
        return data;
    }

    @Override
    public TestData onExit(TestData data) {
        System.out.println("State B onExit");
        return data;
    }

    @Override
    public int hashCode() {
        return 2;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public String getDescription() {
        return "TestStateB";
    }
}
