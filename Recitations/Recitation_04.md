# Problem Set 4 - Lambda Expressions

Questions 3.3 and 4 refer to the `Student` class. (`Code/2017-02-16/Student.java`)

## Problem 1

For each of the following expressions, tell whether it is valid or not. If valid, explain the reasoning. If not valid, explain why.

1. ```java
   () -> { }
   ```
2. ```java
   () -> "Hello"
   ```
3. ```java
   () -> { return "Goodbye"; }
   ```
4. ```java
   (Integer i) -> { return i + 10; }
   ```
5. ```java
   (String s) -> { "Bourne Ultimatum"; }
   ```

## Problem 2

Which of the following are functional interfaces?

1. ```java
   public interface Sum1 {
      int sum(int i, int j);
   }
   ```
2. ```java
   public interface Sum2 extends Sum1 {
      double sum(double i, double j);
   }
   ```

3. ```java
   public interface Rectangle {
      double getWidth();

      double getHeight();

      default double area() {
         return getWidth() * getHeight();
      }
   }
   ```

## Problem 3

Which of the following are valid uses of lambdas?

1. ```java
   public interface Executor {
      void execute();
   }
   ```
   ```java
   public void do(Executor ex) {
      ex.execute();
   }
   do(() -> { });
   ```
2. ```java
   public interface Proc<T> {
      T process();
   }
   ```
   ```java
   public Proc<String> get() {
      return () -> "I am a go getter!";
   }
   ```
3. ```java
   Predicate<Student> p = (Student s) -> s.getMajor();
   ```
4. ```java
   BiFunction<Integer, Integer, String> bif = (int i, int j) -> "" + i + j;
   ```

## Problem 4

1. Write a NAMED lambda expression using a method reference to check if a student is a senior.
2. Write a NAMED lambda expression using a method reference to get the major of a student.
3. Given the following `filter` method:

   ```java
   public List<T> filter(List<T> list, Predicate<T> p) {
      List<T> res = new ArrayList<>();
      for (T t : list) {
         if (p.test(t)) {
            res.add(t);
         }
      }
      return res;
   }
   ```

   For each of the following, write one or more `Predicate` instances as NAMED lambda expressions that can be passed to the `filter` method to get the requires set of students. (Note: when composing predicates, you want to use named lambda expressions in the composition, otherwise the syntax gets unwieldy/unacceptable.)

   1. All non-CS majors
   2. All CS and Physics majors who are commuters
   3. Math seniors who are not commuters
   4. Resident non-Math non-freshman students
