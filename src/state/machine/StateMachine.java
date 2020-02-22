package state.machine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class StateMachine<T> {

	private final Set<State<T>> stateSet;
	private final Map<State<T>, State<T>> stateToStateMapping;
	private final Map<State<T>, Guard<T>> guardList;
	private final Map<State<T>, Transition<T>> transitionList;
	private State<T> currentState;
	private State<T> terminalState;
	private T currentData;

	public StateMachine() {
		this.stateSet = new HashSet<State<T>>();
		this.stateToStateMapping = new HashMap<State<T>, State<T>>();
		this.guardList = new HashMap<State<T>, Guard<T>>();
		this.transitionList = new HashMap<State<T>, Transition<T>>();
		this.currentState = null;
		this.currentData = null;
	}

	public boolean addTransition(final State<T> fromState, final State<T> toState, final Guard<T> guard,
			final Transition<T> transition) {
		this.stateSet.add(fromState);
		this.stateSet.add(toState);
		this.guardList.put(fromState, guard);
		this.transitionList.put(fromState, transition);
		return true;
	};

	public void setInitialState(final State<T> initialState, final T initialData) {
		this.currentState = initialState;
		this.currentData = initialData;
	};

	public void setTerminalState(final State<T> terminalState) {
		this.terminalState = terminalState;
	}

	public boolean hasTerminated() {
		if (this.currentState == this.terminalState) {
			return true;
		}
		return false;
	}

	public T cycle() {
		if (currentState == null) {
			throw new IllegalArgumentException("cuurent state is null");
		}
		if (this.guardList.get(this.currentData).check(this.currentState)) {
			this.currentData = currentState.execute(this.currentData);
			final State<T> tmp = this.stateToStateMapping.get(this.currentState);
			this.transitionList.get(this.currentState).exetcute(this.currentState, tmp, this.currentData);
			this.currentState = tmp;
		}
		return this.currentData;
	};
}
