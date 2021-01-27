package test.state.machine.states;

import state.machine.State;
import test.state.machine.TestData;

public final class TestStateC extends State<TestData> {

    @Override
    public TestData onEntry(TestData data) {
        data.setValue(data.getValue() * -1.0);
        System.out.println("State C onEntry: calc 'x * -1'");
        return data;
    }

    @Override
    public TestData onExit(TestData data) {
        System.out.println("State C onExit");
        return data;
    }


    @Override
    public int hashCode() {
        return 3;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public String getDescription() {
        return "TestStateC";
    }
}
