package test.state.machine.events;

import state.machine.Event;

public final class TestEventB extends Event {
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
        return "TestEventB";
    }
}
