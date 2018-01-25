package controller;

/**
 * Interface for TriPredicate
 *
 * @author William Chen
 * @author Chijun Sha
 */

public interface TriPredicate<T, U, V> {

	/**
	 * @param t Parameter
	 * @param u Parameter
	 * @param v Parameter
	 * @return True if TriPredicate criteria is met, false otherwise.
	 */
	boolean test(T t, U u, V v);
}