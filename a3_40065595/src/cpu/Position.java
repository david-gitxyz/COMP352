package cpu;

public interface Position<E> {
	/**
	 * returns element stored at this position
	 * @return stored element
	 * @throws IllegalStateException if position no longer valid
	 */
	E getElement( ) throws IllegalStateException;

}
