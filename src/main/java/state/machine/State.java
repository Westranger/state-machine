package state.machine;

import state.machine.viz.GraphViz;

public abstract class State<T> implements GraphViz {

    public abstract T onEntry(final T data);

    public abstract T onExit(final T data);

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

}
