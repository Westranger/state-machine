package state.machine.guard;

import java.util.Arrays;

import state.machine.Guard;
import state.machine.State;

public final class And<T> implements Guard<T> {

	private final Guard<T>[] guards;

	public And(final Guard<T>... guards) {
		this.guards = guards;
	}

	@Override
	public boolean check(final State<T> parentState) {
		boolean result = this.guards[0].check(parentState);
		for (int i = 1; i < this.guards.length; i++) {
			result = result && this.guards[i].check(parentState);
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(guards);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		And other = (And) obj;
		if (!Arrays.equals(guards, other.guards))
			return false;
		return true;
	}

	
	
}
