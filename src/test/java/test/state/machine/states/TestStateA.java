package test.state.machine.states;

import state.machine.State;
import test.state.machine.TestData;

public final class TestStateA extends State<TestData> {

    @Override
    public TestData onEntry(TestData data) {
        data.setValue(data.getValue() * 2.0);
        System.out.println("State A onEntry: calc 'x * 2'");
        return data;
    }

    @Override
    public TestData onExit(TestData data) {
        System.out.println("State A onExit");
        return data;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public String getDescription() {
        return "TestStateA";
    }
}
