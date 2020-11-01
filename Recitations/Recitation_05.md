# Problem Set 5 - Inner Classes, Abstract Classes, Polymorphism

## Problem 1 - Inner Classes

1. Write a class named `Outer` that contains an inner class named `Inner`. Add a method to `Outer` that returns an object of type `Inner`. `Outer` has a private `String` field (initialized by the constructor), and `Inner` has a `toString()` that displays this field. In `main()`, create and initialize a reference to an `Inner` and display it.
2. Write a class named `Outer2` that contains an inner class named `Inner`, and the `Outer2` class itself has a method that returns an instance of the inner class. In a separate class named `InnerApp`, make an instance of the inner class without creating an object of the outer class.

## Problem 2 - Abstract Classes.

For each of the following, tell whether the code will compile. If not, explain why.

1. ```java
   public abstract class X {}
   ```
2. ```java
   public class X {
      public abstract void stuff();
   }
   ```
3. ```java
   public abstract class X {
      public abstract void stuff() {
         System.out.println("abstract");
      }
   }
   ```
4. ```java
   public abstract class X {
      public void stuff() {
         System.out.println("go figure");
      }
   }
   ```

5. ```java
   public abstract class X {
      public abstract void stuff();
   }
   ```
   ```java
   public class Y extends X {}
   ```
6. ```java
   public interface I {
      void stuff();
   }
   ```
   ```java
   public abstract class X {
      public abstract void stuff();
   }
   ```
   ```java
   public class Y extends X implements I {
      public void stuff() {
      }
   }
   ```
7. ```java
   public abstract class X {
      private int i, j;

      public void stuff1() {
      }

      public void stuff2() {
      }
   }
   ```

8. ```java
   public abstract class C {
      public void write() {
         System.out.println("C");
      }

      public static void main(String[] args) {
         C c = new C().write();
      }
   }
   ```

9. ```java
   public abstract class C {
      public abstract void write();
   }
   ```

   ```java
   public class D extends C {
      public void write() {
         System.out.println("D");
      }

      public static void main(String[] args) {
         C c = new D();
         c.write();
      }
   }
   ```

## Problem 3

There is an application that defines a `Person` class and a `Student` class. The `Student` class is defined as a subclass of `Person`. Every person has a home address, while every student has a school address as well.

Consider printing addresses of all people in the application, assuming there is a single array list that stores all `Person` and `Student` objects. How would the address that is printed for students depend on the way the `Student` class address methods are designed/implemented? What alternatives in design can you think of, and what are the pros and cons of these alternatives in printing the addresses?

## Problem 4

This problem gives an example where polymorphism is useful. Consider the class hierarchy given below:

```java
public abstract class Shape implements Comparable<Shape> {
   public void print() {
      System.out.println("Shape");
   }

   public abstract double getArea();

   public static final Shape biggest(Shape[] s) {
      /* TO BE COMPLETED BY YOU */
      return null;
   }

   // OTHER METHODS/FIELDS YOU MAY NEED TO ADD TO ANSWER THE QUESTION
}
```

```java
public class Circle extends Shape {
   double radius;

   public Circle(double r) {
      radius = r;
   }

   public void print() {
      System.out.println("Circle");
   }

   public double getArea() {
      return Math.PI * radius * radius;
   }

   @Override
   public int compareTo(Shape o) {
      /* TO BE COMPLETED BY YOU */
      return 0;
   }
}
```

```java
public class Rectangle extends Shape {
   double height;
   double length;

   public Rectangle(double l, double h) {
      length = l;
      height = h;
   }

   public void print() {
      System.out.println("Rectangle");
   }

   public double getArea() {
      return length * height;
   }

   @Override
   public int compareTo(Shape o) {
      /* TO BE COMPLETED BY YOU */
      return 0;
   }
}
```

```java
public class App {
   public static void main(String[] args) {
      Shape[] s = new Shape[3];

      s[0] = new Circle(7);
      s[1] = new Rectangle(5, 10);
      s[2] = new Circle(4);

      System.out.println("The biggest area of all shapes is : " + Shape.biggest(s));
      return;
   }
}
```

Complete the method

```java
public static final Shape biggest(Shape[] s) {
   /* TO BE COMPLETED BY YOU */
   return null;
}
```

in the `Shape` class. This method should return the shape with the largest area. Note that `Shape` implements the `Comparable` interface. Different `Shape`s should be compared using their area. Now if we extend the `Shape` hierarchy to include more shapes, say rhombus, then will your method run without any problems?
