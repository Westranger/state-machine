package state.machine;

import state.machine.viz.GraphViz;

public abstract class Event implements GraphViz {

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

}
