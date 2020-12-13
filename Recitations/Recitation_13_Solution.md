# Problem Set 13 - Streams Solution

For the stream exercises that follow the objective is to write code to process data using streams and stream operations exclusively.

## Problem 1

Write a method that given an array of integers, prints all odd numbers in the array in sorted order.

```java
public static void sortedOdds(int[] arr) {
   // COMPLETE THIS METHOD
}
```

### Problem 1 Solution

```java
public static void sortedOdds(int[] arr) {
   Arrays.stream(arr)
         .filter(i -> i % 2 != 0)
         .sorted()
         .forEach(System.out::println);
}
```

## Problem 2

Write a method to return the common items in two integer lists:

```java
public static int[] getCommon(List<Integer> l1, List<Integer> l2) {
   // COMPLETE THIS METHOD
}
```

### Problem 2 Solution

```java
public static int[] getCommon(List<Integer> l1, List<Integer> l2) {
   return l1.stream()
            .flatMap(i -> l2.stream()
                            .filter(i::equals))
            .mapToInt(i -> i)
            .toArray();
}
```

## Problem 3

Pythagorean triples are any three positive integers `a`, `b`, `c` for which `a^2 + b^2 = c^2`.

Write a method to print all Pythagorean triples for which `a` and `b` are within a given limit:

```java
/**
 * Prints all pythagorean triples a,b,c where a and b are <= limit
 * each triple should be printed as "a,b,c"
 */
public static void printPythagoreans(int limit) {
   // COMPLETE THIS METHOD
}
```

### Problem 3 Solution

```java
/**
 * Prints all pythagorean triples a,b,c where a and b are <= limit
 * each triple should be printed as "a,b,c"
 */
public static void printPythagoreans(int limit) {
   IntStream.rangeClosed(1, limit)
            .boxed()
            .flatMap(a -> IntStream.rangeClosed(a, limit)
                                   .boxed()
                                   .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                   .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}))
            .forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));
}
```

## Problem 4

Write a method to generate fibonacci numbers upto a limit. The first two fibonacci numbers are `0` and `1`. So if the limit is `10`, the numbers generated will be `[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]` (Hint: use a 2-item array to store a pair of consecutive fibonacci numbers, and iterate on such 2-item arrays to do the progression)

```java
public static int[] fibs(int limit) {
   // COMPLETE THIS METHOD
}
```

### Problem 4 Solution

```java
public static int[] fibs(int limit) {
   return Stream.iterate(new int[]{0, 1}, f -> new int[]{f[1], f[0] + f[1]})
                .limit(limit)
                .mapToInt(f -> f[0])
                .toArray();
}
```

## Problem 5

Write a method to partition the integers from `2` to some parameter `n` into primes and non-primes. You may implement support methods as needed.

```java
public static Map<Boolean, List<Integer>> primesAndNot(int n) {
   // COMPLETE THIS METHOD
}
```

### Problem 5 Solution

```java
public static boolean isPrime(int n) {
   int sqrtN = (int) Math.sqrt(n);
   return IntStream.rangeClosed(2, sqrtN)
                   .noneMatch(i -> n % i == 0);
}

public static Map<Boolean, List<Integer>> primesAndNot(int n) {
   return IntStream.rangeClosed(2, n)
                   .boxed()
                   .collect(Collectors.partitioningBy(i -> isPrime(i)));
}
```

## Problem 6

Write a method to classify words in a file according to length:

```java
public static Map<Integer, List<String>> wordsByLength(String file) throws IOException {
   // COMPLETE THIS METHOD
}
```

### Problem 6 Solution

```java
public static Map<Integer, List<String>> wordsByLength(String file) throws IOException {
   return Files.lines(Paths.get(file))
               .map(l -> l.split(" "))
               .flatMap(Arrays::stream)
               .distinct()
               .collect(Collectors.groupingBy(String::length));
}
```

## Problem 7

There are several parts to this question, which are all about hackathons.

A hackathon tracking site keeps info on all school hackathons as follows:

```java
public class Hackathon {
   private String school;
   private int    year;
   private int    month;         // 1..12
   private int    points;        // awarded by site, out of 1000
   private int    participants;
   private int    sponsorship;   // $$ value

   // assume getter methods for the instance fields
   // i.e. getSchool(), getYear(), etc.
   // ...

   public String toString() {
      return school;
   }
}
```

A school may hold more than one hackathon in a year (Rutgers USACS holds two per year).

Assume that all hackathons are stored in a `List<Hackathon>` instance called `hackathons`.

For each of the following questions, use streams to get the required result. Assign the result to a typed and named variable.

You may assume that all the required classes and methods have been imported so you can use them without qualifiers.

Some of the questions may have alternative answers to the ones given here - with streams there is often more than one way to solve a problem.

1. How many hackathons were held in 2015?
2. What was the highest sponsorship amount for any hackathon?
3. Which school (name any one) had the largest number of participants in any hackathon in 2015?
4. For each year, get the number of schools that hosted hackathons with > 500 participants in that year
5. Modify the result of the previous problem to name all the schools instead of the count, sorted in decreasing order of participants. (A school should appear only once in a year even if it hosted multiple hackathons that year that had > 500 participants.)

### Problem 7 Solution

1. How many hackathons were held in 2015?

   ```java
   int h2015 = (int) hackathons.stream()
                               .filter(h -> h.getYear() == 2015)
                               .count();
   ```

2. What was the highest sponsorship amount for any hackathon?

   ```java
   int maxSponsorship = hackathons.stream()
                                  .map(h -> h.getSponsorship())
                                  .reduce(Integer::max)
                                  .orElse(0);
   ```

3. Which school (name any one) had the largest number of participants in any hackathon in 2015?

   ```java
   String school = hackathons.stream()
                             .filter(h -> h.getYear() == n)
                             .collect(Collectors.maxBy(Comparator.comparing(Hackathon::getParticipants)))
                             .map(Hackathon::getSchool)
                             .orElse("No school");
   ```

4. For each year, get the number of schools that hosted hackathons with > 500 participants in that year

   ```java
   Map<Integer, Long> numSchools = hackathons.stream()
                                             .filter(h -> h.getParticipants() > 500)
                                             .collect(Collectors.groupingBy(Hackathon::getYear, Collectors.counting()));
   ```

5. Modify the result of the previous problem to name all the schools instead of the count, sorted in decreasing order of participants. (A school should appear only once in a year even if it hosted multiple hackathons that year that had > 500 participants.)

   ```java
   Map<Integer, Set<String>> numSchools = hackathons.stream()
                                                    .filter(h -> h.getParticipants() > 500)
                                                    .sorted(Comparator.comparing(Hackathon::getParticipants).reversed())
                                                    .collect(Collectors.groupingBy(Hackathon::getYear,
                                                                                   Collectors.mapping(Hackathon::getSchool,
                                                                                                      Collectors.toSet())));
   ```
