package state.machine.guard;

import state.machine.State;
import state.machine.viz.GraphViz;

public interface Guard<T> extends GraphViz {

    boolean check(final State<T> parentState, final T data);
}
