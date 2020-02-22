package state.machine;

public abstract class State<T> {

	public abstract T execute(final T data);

	@Override
	public abstract int hashCode();
}
