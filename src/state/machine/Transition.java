package state.machine;

public interface Transition<T> {
	public T exetcute(final State<T> fromState, final State<T> toState, final T data);
}
