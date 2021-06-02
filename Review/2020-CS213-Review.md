# 2020 CS213 Practice

## Question 1

What is written to the standard output as the result of executing the following code?

-  `MySuper.java`

   ```java
   public class MySuper {
      int a = 4;

      public MySuper() {
         myMethod();
      }

      void myMethod() {
         a++;
         System.out.println("y" + a);
      }
   }
   ```

-  `MySub.java`

   ```java
   public class MySub extends MySuper {
      int b = 3;

      void myMethod() {
         System.out.println("y" + b);
      }

      public static void main(String[] args) {
         MySub mySub = new MySub();
      }
   }
   ```

1. This program writes `"y4"` to the standard output.
2. This program writes `"y3"` to the standard output.
3. This program writes `"y5"` to the standard output.
4. This program writes `"y0"` to the standard output.
5. This program writes `"y5y3"` to the standard output.
6. This program writes `"y3y5"` to the standard output.

<details>
   <summary>Question 1 Solution</summary>
   <br />
   4. This program writes <code>"y0"</code> to the standard output.
</details>

## Question 2

What is written to the standard output as the result of executing the following code?

```java
public class MyException {
   public static void main(String[] args) {
      int x = 0;
      int y = 4;
      try {
         System.out.print("a");
         y = y / x;
         System.out.print("b");
      }
      catch (ArithmeticException arithmeticException) {
         System.out.print("c");
      }
      finally {
         System.out.print("d");
      }
   }
}
```

1. This program writes `"a"` to the standard output.
2. This program writes `"ab"` to the standard output.
3. This program writes `"acd"` to the standard output.
4. This program writes `"ac"` to the standard output.
5. This program writes `"ad"` to the standard output.
6. This program writes `"b"` to the standard output.

<details>
   <summary>Question 2 Solution</summary>
   <br />
   3. This program writes <code>"acd"</code> to the standard output.
</details>

## Question 3

What is written to the standard output as the result of executing the following code?

```java
public class MyException {
   int[] arrayInt = new int[8];
   int   nr       = 3;

   public void myMethod(int i, int i2) {
      try {
         arrayInt[i] = i2;
         nr = i / i2;
         System.out.print("s");
      }
      catch (ArithmeticException arithmeticException) {
         System.out.print("t");
      }
      catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
         System.out.print("x");
      }
   }

   public static void main(String[] args) {
      MyException myException = new MyException();
      myException.myMethod(8, 0);
      myException.myMethod(7, 0);
   }
}
```

1. This program writes `"st"` to the standard output.
2. This program writes `"sx"` to the standard output.
3. This program writes `"stx"` to the standard output.
4. This program writes `"tx"` to the standard output.
5. This program writes `"xs"` to the standard output.
6. This program writes `"xt"` to the standard output.

<details>
   <summary>Question 3 Solution</summary>
   <br />
   6. This program writes <code>"xt"</code> to the standard output.
</details>

## Question 4

What is written to the standard output as the result of executing the following code?

-  `MySuper.java`

   ```java
   public class MySuper {
      MySuper() {
         this(2);
         System.out.print("s");
      }

      MySuper(int x) {
         System.out.print(x);
      }
   }
   ```

-  `MyClass.java`

   ```java
   public class MyClass extends MySuper {
      MyClass() {
         this("t");
         System.out.print("y");
      }

      MyClass(String str) {
         System.out.print(str);
      }

      public static void main(String[] args) {
         MyClass myClass = new MyClass();
      }
   }
   ```

1. This program writes `"xsty"` to the standard output.
2. This program writes `"ty"` to the standard output.
3. This program writes `"y"` to the standard output.
4. This program writes `"t"` to the standard output.
5. This program writes `"2sty"` to the standard output.
6. This program writes `"2s"` to the standard output.

<details>
   <summary>Question 4 Solution</summary>
   <br />
   5. This program writes <code>"2sty"</code> to the standard output.
</details>

## Question 5

What happens when the following program is compiled and run?

-  `InfB.java`

   ```java
   public interface InfB {
      int x = 3;

      int myMethod();
   }
   ```

-  `InfA.java`

   ```java
   public interface InfA extends InfB {
      int x = 7;
   }
   ```

-  `MyClass.java`

   ```java
   public class MyClass implements InfA {
      int x = 5;

      public int myMethod() {
         return x;
      }

      public static void main(String[] args) {
         InfA    infA    = new MyClass();
         InfB    infB    = new MyClass();
         MyClass myClass = new MyClass();
         System.out.print(myClass.x);
         System.out.print(infA.x);
         System.out.print(infB.x);
         System.out.print(myClass.myMethod());
         System.out.print(infA.myMethod());
         System.out.print(infB.myMethod());
      }
   }
   ```

1. This program writes `"555555"` to the standard output.
2. This program writes `"777777"` to the standard output.
3. This program writes `"573555"` to the standard output.
4. This program writes `"333333"` to the standard output.
5. This program writes nothing to the standard output.
6. This program does not compile.

<details>
   <summary>Question 5 Solution</summary>
   <br />
   Solution:
   
   3. This program writes <code>"573555"</code> to the standard
   output.
   
   Explanation:

   <ol>
      <li>
         By converting from a class to an interface, the interface can access the
         overridden methods.
      </li>
      <li>
         The overridden method <code>myMethod</code> returns the variable
         <code>x</code> of the class <code>MyClass</code>. Therefore the methods
         <code>infB.myMethod()</code>, <code>infA.myMethod() </code> and
         <code>infB.myMethod()</code> return <code>5</code>, which is the value
         of the variable <code>x</code> of <code>MyClass</code>.
      </li>
   </ol>
</details>

## Question 6

What is written to the standard output as the result of executing the following code?

-  `Item.java`

   ```java
   public class Item {
      protected double price = 5;

      public double getPrice() {
         return price;
      }
   }
   ```

-  `Book.java`

   ```java
   public class Book extends Item {
      double price = 15;

      public double getPrice() {
         return price += 3;
      }

      public static void main(String[] args) {
         Item item = new Book();
         Book book = (Book) item;
         System.out.print(item.price);
         System.out.print(", ");
         System.out.print(item.getPrice());
         System.out.print(", ");
         System.out.print(book.getPrice());
         System.out.print(", ");
         System.out.print(book.getPrice());
      }
   }
   ```

1. This program writes `"5.0, 5.0, 5.0, 5.0"` to the standard output.
2. This program writes `"5.0, 18.0, 18.0, 21.0"` to the standard output.
3. This program writes `"2.0, 18.0, 15.0, 18.0"` to the standard output.
4. This program writes `"5.0, 5.0, 15.0, 18.0"` to the standard output.
5. This program writes `"5.0, 18.0, 15.0, 8.0"` to the standard output.
6. This program writes `"15.0, 18.0, 15.0, 18.0"` to the standard output.

<details>
   <summary>Question 6 Solution</summary>
   <br />
   Solution:
   
   2. This program writes <code>"5.0, 18.0, 18.0, 21.0"</code> to the
   standard output.
   
   Explanation:

   <ol>
      <li>
         By upcasting objects, the overridden variable depends on the type of the
         object reference item, but the overridden methods depends on the type
         of the object that was created.
      </li>
      <li>
         By downcasting objects, both variables and methods depends on the type
         of the object reference book.
      </li>
   </ol>
</details>

## Question 7

What happens when the following program is compiled and run?

```java
public class MySuper {
   int b = 1;
}

class MySub extends MySuper {
   int c = 5;

   MySub(int c) {
      System.out.print("-c" + this.c);
   }

   void myMethod() {
      System.out.print("-b" + b);
   }
}

class TestMyProgram {
   public static void main(String[] args) {
      MySub mySub = new MySub(4);
      mySub.myMethod();
   }
}
```

1. This program does not compile.
2. This program writes `"-c4-b1"` to the standard output.
3. This program writes `"-c5-b1"` to the standard output.
4. This program writes `"-cs-b0"` to the standard output.
5. This program writes `"c4-b0"` to the standard output.

<details>
   <summary>Question 7 Solution</summary>
   <br />
   Solution:
   
   3. This program writes <code>"-c5-b1"</code> to the
   standard output.
   
   Explanation:

The subclass can access the variable in its super class.
Remember that without using the keyworld <code>this</code>, the answer would be <code>-c4-b1</code>.

</details>

## Question 8

```java
class MySuper {
   int a = 1;

   MySuper() {
      System.out.println("-a" + a);
   }
}

class MySub extends MySuper {
   MySub(int c) {
      System.out.println("-c" + c);
   }
}

class TestInheritance {
   public static void main(String[] args) {
      MySub mySub = new MySub(2);
   }
}
```

1. This program does not compile.
2. This program writes `"-c2"` to the standard output.
3. This program writes nothing to the standard output
4. This program writes `"-c2-a1"` to the standard output.
5. This program writes `"-a0-c2"` to the standard output.
6. This program writes `"-a1-c2"` to the standard output.

<details>
   <summary>Question 8 Solution</summary>
   <br />
   Solution:
   
   6. This program writes <code>"-a1-c2"</code> to the
   standard output.
   
   Explanation:

The subclass' constructor implicitly calls superclass' no-argument constructor.

</details>

## Question 9

Which statements are true about the following code?

```java
public interface MyInterface {
   int SMALL_NUMBER;
   protected double NUMBER = 20.0;
   int MY_NUMBER = 10;

   int getSum(int nr1, int nr2);

   private int getMultiply(int nr1, int nr2);

   int getMultiply(int nr1, int nr2, int nr3);
}

public class MyClass implements MyInterface {
   int getSum(int a, int b) {
      return a + b;
   }

   public int getMultiply(int a, int b, int c) {
      return a * b * c;
   }

   public double getPI() {
      return 3.14;
   }
}
```

1. The constant `int SMALL NUMBER;` should be initialized.
2. The modifier `protected` must be removed from `protected double NUMBER = 20.0;`.
3. The constant `int MY MYNUMBER = 10;` must be `public`.
4. The abstract method `private int getMultiply(int nn, int nr2);` cannot be `private`.
5. `MyClass` must implement all the constants of `MyInterface`.
6. The method `int getSum(int a, int b)` inside `MyClass` must be `public`.
7. `MyClass` should define the method `int getMultiply(int nr1, int nr2);`, which has two parameters.
8. You must remove the method `getPI()` from the class `MyClass`.

<details>
   <summary>Question 8 Solution</summary>
   <br />   
   The correct answers are: 1, 2, 4, 6, and 7.
</details>

## Question 9

What is written to the standard output as the result of executing the following code?

-  `Animal.java`

   ```java
   public class Animal {
      public void sound() {
         System.out.println("Animal is making a sound");
      }
   }
   ```

-  `Horse.java`

   ```java
   class Horse extends Animal {
      @Override
      public void sound() {
         System.out.println("Neigh");
      }

      public static void main(String[] args) {
         Animal obj = new Horse();
         obj.sound();
      }
   }
   ```

<details>
   <summary>Question 9 Solution</summary>
   <br />   
   <code>"Neigh"</code>
</details>

## Question 10

What is written to the standard output as the result of executing the following code?

```java
class Overload {
   void demo(int a) {
      System.out.println("a: " + a);
   }

   void demo(int a, int b) {
      System.out.println("a and b: " + a + "," + b);
   }

   double demo(double a) {
      System.out.println("double a: " + a);
      return a * a;
   }
}

class MethodOverloading {
   public static void main(String[] args) {
      Overload Obj = new Overload();
      double   result;
      Obj.demo(10);
      Obj.demo(10, 20);
      result = Obj.demo(5.5);
      System.out.println("O/P: " + result);
   }
}
```

<details>
   <summary>Question 10 Solution</summary>
   <code>a: 10</code>

<code>a and b: 10,20</code>

<code>double a: 5.5</code>

<code>O/P: 30.25</code>

</details>
