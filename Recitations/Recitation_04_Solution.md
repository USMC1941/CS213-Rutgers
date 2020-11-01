# Problem Set 4 - Lambda Expressions Solution

Questions 3.3 and 4 refer to the `Student` class. (`Code/2017-02-16/Student.java`)

## Question 1

For each of the following expressions, tell whether it is valid or not. If valid, explain the reasoning. If not valid, explain why.

1. ```java
   () -> { }
   ```

   **Answer**: Valid. Corresponds to a method that takes no arguments, returns `void`, and has an empty body, e.g.

   ```java
   public void stuff() { }
   ```

2. ```java
   () -> "Hello"
   ```

   **Answer**: Valid. Similar to previous, except it's written as an explicit statement inside curly braces.

   ```java
   public String stuff() {
      return "Hello";
   }
   ```

3. ```java
   () -> { return "Goodbye"; }
   ```

   **Answer**: Valid. Similar to previous, except it's written as an explicit statement inside curly braces.

   ```java
   public String stuff() {
      return "Goodbye";
   }
   ```

4. ```java
   (Integer i) -> { return i + 10; }
   ```

   **Answer**: Invalid. Since `return` is a control flow statement, it has to be enclosed within braces.

5. ```java
   (String s) -> { "Bourne Ultimatum"; }
   ```

   **Answer**: Invalid. `"Bourne Ultimatum"` is an expression, not a statement. You can do either of the following to get a correct lambda expression:

   -  Move expression out of the braces:
      ```java
      (String s) -> "Bourne Ultimatum"
      ```
   -  Do a return statement:

      ```java
      (String s) -> { return "Bourne Ultimatum"; }
      ```

## Question 2

Which of the following are functional interfaces?

1. ```java
   public interface Sum1 {
      int sum(int i, int j);
   }
   ```

   **Answer**: Yes.

2. ```java
   public interface Sum2 extends Sum1 {
      double sum(double i, double j);
   }
   ```

   **Answer**: No. `Sum2` has two methods.

3. ```java
   public interface Rectangle {
      double getWidth();

      double getHeight();

      default double area() {
         return getWidth() * getHeight();
      }
   }
   ```

   **Answer**: No. There are two abstract methods.

## Question 3

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

   **Answer**: Yes. The lambda takes no args and returns nothing, which matches the execute method of the `Executor` interface.

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

   **Answer**: Valid. The lambda in the return takes no args and a `String`, which matches the process method of the `Proc` interface, with the binding of `String` to the generic type `T`.

3. ```java
   Predicate<Student> p = (Student s) -> s.getMajor();
   ```

   **Answer**: Invalid. The lambda should return a `boolean`.

4. ```java
   BiFunction<Integer, Integer, String> bif = (int i, int j) -> "" + i + j;
   ```

   **Answer**: Invalid. The args for the lambda must be `Integers`. Auto conversion to `int` will not be done. (If you omit the data type for the arguments, it will work just fine.)

## Question 4

1. Write a NAMED lambda expression using a method reference to check if a student is a senior.

   **Answer**:

   ```java
   Predicate<Student> is_senior = Student::isSenior;
   ```

2. Write a NAMED lambda expression using a method reference to get the major of a student.

   **Answer**:

   ```java
   Function<Student, String> major = Student::getMajor;
   ```

3. Given the following `filter` method:

   ```java
   public List<T> filter(List<T> list, Predicate<T> p) {
      List<T> res = new ArrayList<T>();
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

      **Answer**:

      ```java
      Predicate<Student> cs_major     = s -> s.getMajor().equals("CS");
      Predicate<Student> non_cs_major = cs_major.negate();
      ```

   2. All CS and Physics majors who are commuters.

      **Answer**:

      ```java
      Predicate<Student> physics_major = s -> s.getMajor().equals("Physics");
      Predicate<Student> commuter = Student::getCommuter;
      Predicate<Student> pred = (cs_major.or(physics_major)).and(commuter);
      Predicate<Student> is_senior = Student::isSenior;
      ```

   3. Math seniors who are not commuters

      **Answer**:

      ```java
      Predicate<Student> math_major = s -> s.getMajor().equals("Math");
      Predicate<Student> pred       = (math_major.and(is_senior)).and(commuter.negate());
      ```

   4. Resident non-Math non-freshman students

      **Answer**:

      ```java
      Predicate<Student> is_freshman = Student::isFreshman;
      Predicate<Student> pred = commuter.negate().and(math_major.negate()).and(is_freshman.negate());
      ```
