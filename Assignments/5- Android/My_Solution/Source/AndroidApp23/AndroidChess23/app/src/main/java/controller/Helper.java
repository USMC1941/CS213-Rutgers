package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Helper class for Collections operations.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Helper {

	public interface Predicate<T> {
		boolean test(T t);
	}

	public interface BiPredicate<T,U> {
		boolean test(T t, U u);
	}

	public interface TriPredicate<T,U,V> {
		boolean test(T t, U u, V v);
	}

	/**
	 * @param tCollection Input Collection
	 * @param u Parameter
	 * @param v Parameter
	 * @param triPredicate TriPredicate
	 * @param <T> Type Parameter
	 * @param <U> Type Parameter
	 * @param <V> Type Parameter
	 * @return True if predicate is met at least by one in the Collection, false otherwise.
	 */
	public static <T,U,V> boolean findOne(Collection<T> tCollection, U u, V v, TriPredicate<T,U,V> triPredicate) {
        //return tCollection.stream().anyMatch(t -> triPredicate.test(t, u, v));
		for (T t : tCollection) {
			if (triPredicate.test(t, u, v)) {
				return true;
			}
		}
		return false;
    }

	/**
	 * @param tCollection Input Collection
	 * @param predicate Predicate
	 * @param <T> Type Parameter
	 * @return True if predicate is met at least by one in the Collection, false otherwise.
	 */
	public static <T> boolean findOne(Collection<T> tCollection, Predicate<T> predicate) {
        //return tCollection.stream().anyMatch(predicate);
        for (T t : tCollection) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param tCollection Input Collection
     * @param u Parameter
     * @param biPredicate BiPredicate
     * @param <T> Type Parameter
     * @param <U> Type Parameter
     * @return List of elements in the Collection that meet criteria.
     */
    public static <T,U> List<T> filter(Collection<T> tCollection, U u, BiPredicate<T,U> biPredicate) {
        //return tCollection.stream().filter(t -> biPredicate.test(t, u)).collect(Collectors.toList());
        List<T> list = new ArrayList<>();
        for (T t : tCollection) {
            if (biPredicate.test(t, u)) {
                list.add(t);
            }
        }
        return list;
    }
}