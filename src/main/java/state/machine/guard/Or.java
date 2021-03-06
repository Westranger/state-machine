package state.machine.guard;

import state.machine.State;

public final class Or<T> implements Guard<T> {

    private final Guard<T>[] guards;

    public Or(final Guard<T>... guards) {
        this.guards = guards;
    }

    @Override
    public boolean check(final State<T> parentState, final T data) {
        boolean result = this.guards[0].check(parentState, data);
        for (int i = 1; i < this.guards.length; i++) {
            result = result || this.guards[i].check(parentState, data);
        }
        return result;
    }

    @Override
    public String getDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Guard<T> action : this.guards) {
            sb.append(action.getDescription());
            sb.append(" or ");
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(']');
        return sb.toString();
    }
}
