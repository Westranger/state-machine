package state.machine;

import state.machine.action.Action;
import state.machine.guard.Guard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class StateMachine<T> {

    private final Map<State<T>, Map<State<T>, Guard<T>>> guardList;
    private final Map<State<T>, Map<State<T>, Action<T>>> actionList;
    private final Map<State<T>, Map<State<T>, Class<? extends Event>>> eventTypeList;
    private State<T> currentState;
    private State<T> terminalState;
    private T currentData;

    public StateMachine() {
        this.guardList = new HashMap<>();
        this.actionList = new HashMap<>();
        this.eventTypeList = new HashMap<>();
        this.currentState = null;
        this.currentData = null;
    }

    public boolean addAction(final Class<? extends Event> eventClass, final State<T> fromState, final State<T> toState, final Guard<T> guard,
                             final Action<T> action) {
        if (fromState == null || toState == null || eventClass == null) {
            return false;
        }

        {
            Map<State<T>, Class<? extends Event>> map;
            if (this.eventTypeList.containsKey(fromState)) {
                map = this.eventTypeList.get(fromState);
                map.put(toState, eventClass);
            } else {
                map = new HashMap<>();
                map.put(toState, eventClass);
                this.eventTypeList.put(fromState, map);
            }
        }

        if (guard != null) {
            Map<State<T>, Guard<T>> map;
            if (this.guardList.containsKey(fromState)) {
                map = this.guardList.get(fromState);
                map.put(toState, guard);
            } else {
                map = new HashMap<>();
                map.put(toState, guard);
                this.guardList.put(fromState, map);
            }
        }

        if (action != null) {
            Map<State<T>, Action<T>> map;
            if (this.actionList.containsKey(fromState)) {
                map = this.actionList.get(fromState);
                map.put(toState, action);
            } else {
                map = new HashMap<>();
                map.put(toState, action);
                this.actionList.put(fromState, map);
            }
        }

        return true;
    }

    public State<T> getCurrentState() {
        return this.currentState;
    }

    public void setInitialState(final State<T> initialState, final T initialData) {
        if (!this.eventTypeList.containsKey(initialState)) {
            throw new IllegalStateException("no transitions have been set for initial state (state unknown)");
        }

        this.currentState = initialState;
        this.currentData = initialData;
        initialState.onEntry(initialData);
    }

    public void setTerminalState(final State<T> terminalState) {
        if (!this.eventTypeList.containsKey(terminalState)) {
            boolean contains = false;
            for (Map<State<T>, Class<? extends Event>> map : this.eventTypeList.values()) {
                if (map.containsKey(terminalState)) {
                    contains = true;
                    break;
                }
            }

            if (!contains) {
                throw new IllegalStateException("no transitions have been set for initial state (state unknown)");
            }
        }

        this.terminalState = terminalState;
    }

    public boolean hasTerminated() {
        return this.currentState == this.terminalState;
    }

    public T cycle(final Event evt) {
        if (currentState == null) {
            throw new IllegalStateException("FSM has not been initialized");
        }

        final Map<State<T>, Class<? extends Event>> map = this.eventTypeList.get(this.currentState);
        for (State<T> toState : map.keySet()) {
            final Class<? extends Event> aClass = this.eventTypeList.get(this.currentState).get(toState);
            if (evt.getClass() != aClass) {
                continue;
            }

            final Guard<T> guard;
            boolean guardPassed = true;

            if (this.guardList.containsKey(this.currentState) && this.guardList.get(this.currentState).containsKey(toState)) {
                guard = this.guardList.get(this.currentState).get(toState);
                guardPassed = guard.check(this.currentState, this.currentData);
            }

            if (guardPassed) {
                if (this.actionList.containsKey(this.currentState)) {
                    this.currentState.onExit(this.currentData);


                    if (this.actionList.get(this.currentState).containsKey(toState)) {
                        final Action<T> action = this.actionList.get(this.currentState).get(toState);
                        action.execute(this.currentState, toState, this.currentData);
                    }

                    toState.onEntry(this.currentData);
                    this.currentState = toState;
                    break;
                } else {
                    this.currentState.onExit(this.currentData);
                    toState.onEntry(this.currentData);
                    this.currentState = toState;
                    break;
                }
            }
        }

        return this.currentData;
    }

    // http://magjac.com/graphviz-visual-editor/
    public String toGraphVIZ() {
        final StringBuilder sb = new StringBuilder();
        sb.append("digraph finite_state_machine {\n\trankdir=LR;\n\tsize=\"8,5\"\n\tnode [shape = doublecircle]; \"");
        sb.append(this.terminalState.getDescription());
        sb.append("\";\n\tnode [shape = circle];\n");

        for (Map.Entry<State<T>, Map<State<T>, Class<? extends Event>>> statePair : this.eventTypeList.entrySet()) {
            final Map<State<T>, Class<? extends Event>> map = statePair.getValue();

            for (Map.Entry<State<T>, Class<? extends Event>> stateEventPair : map.entrySet()) {
                sb.append('\t');
                sb.append('"');
                sb.append(statePair.getKey().getDescription());
                sb.append('"');
                sb.append(" -> ");
                sb.append('"');
                sb.append(stateEventPair.getKey().getDescription());
                sb.append('"');
                sb.append(" [ label = ");
                sb.append('"');
                // put guards,events and actions here
                sb.append("Event: ");
                sb.append(stateEventPair.getValue().getSimpleName());
                sb.append("\\n");

                if (this.guardList.containsKey(statePair.getKey()) && this.guardList.get(statePair.getKey()).containsKey(stateEventPair.getKey())) {
                    final Guard<T> guard = this.guardList.get(statePair.getKey()).get(stateEventPair.getKey());
                    if (guard != null) {
                        sb.append("Guard: ");
                        sb.append(guard.getDescription());
                        sb.append("\\n");
                    }
                }

                if (this.actionList.containsKey(statePair.getKey()) && this.actionList.get(statePair.getKey()).containsKey(stateEventPair.getKey())) {
                    final Action<T> action = this.actionList.get(statePair.getKey()).get(stateEventPair.getKey());
                    if (action != null) {
                        sb.append("Action: ");
                        sb.append(action.getDescription());
                        sb.append("\\n");
                    }
                }

                sb.append('"');
                sb.append(" ];\n");
            }
        }

        sb.append('}');
        return sb.toString();
    }
}
