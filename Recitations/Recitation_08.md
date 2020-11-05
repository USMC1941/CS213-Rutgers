# Problem Set 8 - Interface Default Methods, OO Design

## Problem 1 - Interface Default Methods.

For each of the following, tell whether the code will compile. If so, tell what will be printed, with reasoning. If not, explain why.

### Problem 1

```java
public interface I {
   private default void m() {
      System.out.println("I:m");
   }
}
```

```java
public class X implements I {
   public static void main(String[] args) {
      new X().m();
   }
}
```

### Problem 2

```java
public interface I {
   default void m1() {
      System.out.println("I:m1");
   }
}
```

```java
public class X implements I {
   public void m1() {
      System.out.println("X:m1");
   }
   public static void main(String[] args) {
      new XI().m1();
   }
}
```

### Problem 3

```java
public class X {
   public void m1() {
      System.out.println("X:m1");
   }
}
```

```java
public interface I {
   default void m1() {
      System.out.println("I:m1");
   }
}
```

```java
public class XI extends X implements I {
   public static void main(String[] args) {
      new XI().m1();
   }
}
```

### Problem 4

```java
public interface A {
   default void hello() {
      System.out.println("A!");
   }
}
```

```java
public interface B extends A {
   default void hello() {
      System.out.println("B!");
   }
}
```

```java
public class C implements A, B {
   public static void main(String[] args) {
      new C().hello();
   }
}
```

### Problem 5

```java
public interface I {
   default double getNumber() {
      return 3.5;
   }
}
```

```java
public interface J {
   default int getNumber() {
      return 3;
   }
}
```

```java
public class X implements I, J {
   public static void main(String[] args) {
      System.out.println(new X().getNumber());
   }
}
```

### Problem 6

```java
public interface I {
   default void name() {
      System.out.println("I");
   }
}
```

```java
public interface J extends I {}
```

```java
public interface K extends I {}
```

```java
public class X implements J, K {
   public static void main(String[] args) {
      new X().name();
   }
}
```

## Problem 2

Suppose you design a `class`, `Set`, whose members behave like finite, unordered mathematical sets of integers, and can support the operations of membership query, union of two sets, intersection of two sets, and difference of two sets.

Consider the intersection operation. There are at least two ways of declaring such an operation in the `class Set`:

```java
public Set intersect(Set otherSet)
```

or

```java
public static Set intersect(Set firstSet, Set secondSet)
```

Give one pro and one con for the `static` version.

## Problem 3

A game developer asks you to make a set of classes to represent the monsters in a game. There are at least two different types of monsters, those that walk and those that bounce, but the code you write needs to be easily expandable to different types. Specifically, the code to keep track of a monster's appearance and to draw the monster needs to be in only one place. You are given an `interface` to start out:

```java
public interface Monster {
   void drawMonster();

   void setMonsterImage(Image i);

   void updatePosition();
}
```

Create an `abstract` base class `MovingMonster` for all monsters, and one subclass for each of two types discussed, `WalkingMonster` and `BouncingMonster`. Each monster will need to keep track of its own position and update it when the `updatePosition()` method is invoked. Assumethat the `Image` class has a method `draw(int x, int y)`. The contents of the `updatePosition()` method are not important, but it has to change the monster's position and be different for either monster.

## Problem 4

Suppose we need to have the `Point` and `ColoredPoint` classes provide functionality to parse text representations of points and colored points (as would be returned by the `toString()` method), and return `Point` and `ColoredPoint` objects, respectively.

1. Show how you would implement this functionality.
2. How much of the `Point` implementation of this functionality is reused in `ColoredPoint`? (Reuse meaning using code from `Point` by calling on it in `ColoredPoint`.) If yes, indicate which part, else explain why not.
3. Does your implementation give rise to dynamic binding of the parsing functionality? (Recall that dynamic binding means the subclass version of a method is "bound" to the call made via an object reference that is statically typed to the superclass.)
