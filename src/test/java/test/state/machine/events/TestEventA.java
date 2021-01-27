package test.state.machine.events;

import state.machine.Event;

public final class TestEventA extends Event {
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
        return "TestEventA";
    }
}
