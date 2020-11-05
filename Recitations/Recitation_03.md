# Problem Set 3 - Interfaces

## Problem 1 - Short Questions

### Problem 1

Will the following code compile?

```java
public class D {}
```

```java
public class C implements Comparable<D> {
   public int compareTo(D o) {
      return 0;
   }
}
```

### Problem 2

Will the following code compile?

```java
public class D {}
```

```java
public class C implements Comparable<C>, Comparable<D> {
   public int compareTo(C o) {
      return 0;
   }

   public int compareTo(D o) {
      return 0;
   }
}
```

### Problem 3

Will the following code compile?

```java
public class A implements Comparable<A> {
   public int compareTo(A o) {
      return 0;
   }
}
```

```java
public class B extends A implements Comparable<B> {
   public int compareTo(B b) {
      return 0;
   }
}
```

### Problem 4

Will the following code compile?

```java
public interface I {
   void stuff();
}
```

```java
public interface J {
   void stuff();
}
```

```java
public class F implements I, J {}
```

### Problem 5

Will the following code compile?

```java
public interface I {
   void stuff();
}
```

```java
public interface J {
   void stuff();
}
```

```java
public class F implements I, J {
   public void stuff() {
   }
}
```

### Problem 6

Will the following code compile?

```java
public interface I {
   void stuff();
}
```

```java
public interface J {
   void stuff();
}
```

```java
public class F implements I, J {
   public int stuff() {
      return 3;
   }
}
```

### Problem 7

Will the following code compile?

```java
class X implements Comparable<X> {
   public int compareTo(X o) {
      return 0;
   }
}

public class Searcher {
   public static <T extends Comparable<T>> boolean binarySearch(T[] list, T item) {
      return false;
   }

   public static void main(String[] args) {
      X[] xs = new X[2];
      xs[0] = new X();
      xs[1] = new X();
      binarySearch(xs, new X());
   }
}
```

### Problem 8

Will the following code compile?

```java
class X implements Comparable<X> {
   public int compareTo(X o) {
      return 0;
   }
}

class Y extends X {}

public class Searcher {
   public static <T extends Comparable<T>> boolean binarySearch(T[] list, T item) {
      return false;
   }

   public static void main(String[] args) {
      Y[] ys = new Y[2];
      ys[0] = new Y();
      ys[1] = new Y();
      binarySearch(ys, new Y());
   }
}
```

### Problem 9

Will the following code compile?

```java
class X implements Comparable<X> {
   public int compareTo(X o) {
      return 0;
   }
}

class Z implements Comparable<X> {}

public class Searcher {
   public static <T extends Comparable<T>> boolean binarySearch(T[] list, T item) {
      return false;
   }

   public static void main(String[] args) {
      Z[] zs = new Z[2];
      zs[0] = new Z();
      zs[1] = new Z();
      binarySearch(zs, new Z());
   }
}
```

### Problem 10

Will the following code compile?

```java
class X implements Comparable<X> {
   public int compareTo(X o) {
      return 0;
   }
}

class Y extends X {}

class Z extends Y {}

public class Searcher {
   public static <T extends Comparable<T>> boolean binarySearch(T[] list, T item) {
      return false;
   }

   public static void main(String[] args) {
      Z[] zs = new Z[2];
      zs[0] = new Z();
      zs[1] = new Z();
      binarySearch(zs, new Z());
   }
}
```

## Problem 2

Suppose you built a Java library of sorting algorithms: insertion sort, quicksort, and heapsort. You want to sell this library. How would you package your library using interfaces, so users could use any of these algorithms in their applications, and switch from using one to another (interface polymorphism), with the least amount of code rewrite?

## Problem 3

Aside from the [`java.lang.Comparable<T>`](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/Comparable.html) `interface` used for comparing objects of a class, the [`java.util`](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/util/package-summary.html) package has an interface, [`Comparator<T>`](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/util/Comparator.html) that may also be used to compare objects. What is the difference between these two interfaces, and how may this difference be usefully employed in applications?
