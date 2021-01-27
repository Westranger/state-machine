package state.machine.action;

import state.machine.State;

public final class ActionSequence<T> implements Action<T> {

    private final Action<T>[] actions;

    public ActionSequence(Action<T>... actions) {
        this.actions = actions;
    }

    @Override
    public void execute(final State<T> fromState, final State<T> toState, final T data) {
        for (Action<T> action : this.actions) {
            action.execute(fromState, toState, data);
        }
    }

    @Override
    public String getDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Action sequence: [");
        for (Action action : this.actions) {
            sb.append(action.getDescription());
            sb.append(',');
            sb.append(' ');
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(']');
        return sb.toString();
    }
}
