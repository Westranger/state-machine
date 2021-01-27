package state.machine.guard;

import state.machine.State;

public final class And<T> implements Guard<T> {

    private final Guard<T>[] guards;

    public And(final Guard<T>... guards) {
        this.guards = guards;
    }

    @Override
    public boolean check(final State<T> parentState, final T data) {
        boolean result = this.guards[0].check(parentState, data);
        for (int i = 1; i < this.guards.length; i++) {
            result = result && this.guards[i].check(parentState, data);
        }
        return result;
    }

    @Override
    public String getDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Guard action : this.guards) {
            sb.append(action.getDescription());
            sb.append(" and ");
        }
        sb.delete(sb.length() - 5, sb.length());
        sb.append(']');
        return sb.toString();
    }
}
