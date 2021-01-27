package test.state.machine.events;

import state.machine.Event;

public final class TestEventC extends Event {
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
        return "TestEventC";
    }
}
