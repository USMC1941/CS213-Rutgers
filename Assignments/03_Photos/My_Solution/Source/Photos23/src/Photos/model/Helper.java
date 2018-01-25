package Photos.model;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Helper class for Collections operations.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Helper {

    /**
     * @param tCollection Input Collection
     * @param predicate Predicate
     * @param <T> Type Parameter
     * @return True if predicate is met at least by one in the Collection, false otherwise.
     */
    public static <T> boolean findOne(Collection<T> tCollection, Predicate<T> predicate) {
        return tCollection.stream().anyMatch(predicate);
	    /*
        for (T t : tCollection) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
        */
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
        return tCollection.stream().filter(t -> biPredicate.test(t, u)).collect(Collectors.toList());
        /*
        List<T> list = new ArrayList<>();
        for (T t : tCollection) {
            if (biPredicate.test(t, u)) {
                list.add(t);
            }
        }
        return list;
        */
    }



    public static <T,U> List<U> map(Collection<T> tCollection, Function<T,U> f) {
        return tCollection.stream().map(f).collect(Collectors.toList());
        /*
        List<U> list = new ArrayList<>();
        for (T t : tCollection) {
            U u = f.apply(t);
            list.add(u);
        }
        return list;
        */
    }


    /**
     * @param l Input list
     * @param element Parameter
     * @param <T> Type parameter
     * @return True if inserted into list
     */
    public static <T extends Comparable<T>> boolean addOrRetrieveOrderedList(List<T> l, T element) {
        ListIterator<T> itr = l.listIterator();
        //
        while (true) {
            if (!itr.hasNext()) {
                itr.add(element);
                return true;
            }
            T elementInList = itr.next();
            if (elementInList.compareTo(element) == 0) {
                return false;
            }
            if (elementInList.compareTo(element) > 0) {
                itr.previous();
                itr.add(element);
                return true;
            }
        }
    }


    /**
     * @param l Input list
     * @param k Parameter
     * @param p BiPredicate
     * @param <T> Type parameter
     * @param <K> Type parameter
     * @return Element deleted from list
     */
    public static <T extends Comparable<T>, K> T delete(List<T> l, K k, BiPredicate<T,K> p) {
        ListIterator<T> itr = l.listIterator();
        //
        while (itr.hasNext()) {
            T t = itr.next();
            if (p.test(t, k)) {
                itr.remove();
                return t;
            }
        }
        //
        return null;
    }


    /**
     * @param l Input list
     * @param k Parameter
     * @param p BiPredicate
     * @param <T> Type parameter
     * @param <K> Type parameter
     * @return Element that meets criteria
     */
    public static <T extends Comparable<T>, K> T get(List<T> l, K k, BiPredicate<T,K> p) {
        return l.stream().filter(t -> p.test(t, k)).findFirst().orElse(null);
    }


    /**
     * @param source Input list
     * @param condition Input list condition
     * @param bp BiPredicate
     * @param <T> Type parameter
     * @return True if input list condition contains matches compared to source list
     */
    public static <T extends Comparable<T>> boolean search(List<T> source, List<T> condition, BiPredicate<T,T> bp) {
        boolean isAllMatch = true;
        //
        for (T c : condition) {
            boolean isMatch = source.stream().anyMatch(s -> bp.test(s, c));
            /*
            for (T s : source) {
                if (bp.test(s, c)) {
                    isMatch = true;
                    break;
                }
            }
            */
            //
            if (!isMatch) {
                isAllMatch = false;
                break;
            }
        }
        //
        return isAllMatch;
    }
}