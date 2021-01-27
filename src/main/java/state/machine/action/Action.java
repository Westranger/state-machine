package state.machine.action;

import state.machine.State;
import state.machine.viz.GraphViz;

public interface Action<T> extends GraphViz {
    void execute(final State<T> fromState, final State<T> toState, final T data);

    @Override
    String toString();
}
