package state.machine;

public interface Guard<T> {

	boolean check(final State<T> parentState);

}
