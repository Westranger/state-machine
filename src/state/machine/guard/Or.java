package state.machine.guard;

import state.machine.Guard;
import state.machine.State;

public final class Or<T> implements Guard<T> {

	private final Guard<T>[] guards;

	public Or(final Guard<T>... guards) {
		this.guards = guards;
	}

	@Override
	public boolean check(final State<T> parentState) {
		boolean result = this.guards[0].check(parentState);
		for (int i = 1; i < this.guards.length; i++) {
			result = result || this.guards[i].check(parentState);
		}
		return result;
	}

}
