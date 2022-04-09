# CS213 - Software Development Ground Rules

> Note: Not all projects use these rules. Read the README of each project.

## General Rules

-   You will work with a partner throughout the semester for all projects.
-   Meeting the project requirements, producing reliable software, and meeting the deadlines are the most important things for software developers. This class enforces these good software practices.
-   Dealing with software change is very challenging in the real world. This class enforces good programming styles and good software practices to produce better structured software, which enhances maintainability and software reuse.
-   Submitting a working project does not mean you will always get the full credit. All Java programs, test documents and class diagrams must adhere to the style and documentation standards given in this document to get the full credit.
-   Maximum deductions for not following the standards are listed for each category, at the end of this document.
-   One Java class per Java file in all projects.

## Documentation Standards

Files must be documented according to the Javadoc standards. A Javadoc comment is made up of two parts - a description followed by zero or more tags.

```java
/**
 * This is the one sentence, descriptive summary, part of a doc comment.
 * There can be more lines after the first one.
 *
 * @tag1 Comment for the tag1
 * @tag2 Comment for the tag2
 */
```

The first line is indented to line up with the code below the comment and starts with `/**` followed by a return. The last line begins with `*/` followed by a return. The comment for a code entity (class or method) must be immediately before the code entity. The first sentence of each doc comment should be a summary sentence, containing a concise but complete description of the code entity. It is important to write crisp and informative initial sentences that can stand on their own. This sentence ends at the first period that is followed by a blank, tab, or line terminator, or at the first tag. Any tags come at the end. Minimally, we require the following documentation:

-   **Comment block at the top of each class.** Since we have one class per file, this usually serves as the "file" comment block.

    ```java
    /**
    * First, a single, very descriptive sentence describing the class.
    * Then, a couple more sentences of description to elaborate.
    * @author teammemberName1, teammemberName2
    */
    ```

-   **Every method and constructor must start with a comment block** which describes what the method (or constructor) does (points lost otherwise). The first sentence must be a very descriptive summary of the method (or constructor). The following lines, if necessary, elaborate and/or give any extra information the user should know. All parameters must be listed using the `@param` tag. If there is a return value, it is listed with the `@return` tag. For example:

    ```java
    /**
    * Deletes the person with the given name from the list.
    * Does nothing if name doesn't appear in the list.
    *
    * @param name of the person to delete
    * @return true if person was deleted, false otherwise
    */
    public boolean deletePerson(String name) {}
    ```

-   **You can comment sections of code within methods**. Use the `//` comments when you do. But don't overdo it! Excessive comments can be distracting, and comments that add nothing to the understanding of the code are particularly distracting. For example,

    ```java
    int count;
    count = count + 1; // Add one to count
    ```

    is a useless comment. For the most part, you shouldn't need more than one line of comments within methods for every few lines of code. If you feel you need to write a comment to make a section of code clear, then you probably should break that section out into a separate method!

## Names

-   **Use descriptive names.** This makes your programs easier to write and debug. If you're tempted to use a poor name for something, then you probably don't completely understand the problem you're trying to solve yet! Figure that out first before trying to go on.
-   Variable and data members:
    -   These should generally be nouns or noun phrases such as `grade` and `gradeForStudent`. The exception is `for` loop counters; this is the only place where it is *sometimes* acceptable to use a one-letter name such as `i`.
    -   These names must start with a small letter and each subsequent word in a multi-word name must be capitalized. Use lower case for the remaining letters.
-   Method names:
    -   Names for methods with a return type of `void` should generally be **verb phrases** such as `printOrders()`.
    -   Names for methods with other return types should generally be **nouns or noun phrases** such as `monthlySalary()`.
    -   Method names shall start with a small letter and each subsequent word in a multi-word name shall be capitalized. Use lower case for the remaining letters.
-   Class names:
    -   Use meaningful common nouns.
    -   Start each class name with an upper-case letter and capitalize each "word" in a multi-word name. Use lower case for the remaining letters.
-   **NO magic numbers!** If a constant is used in the program, you must properly name the constant. In general, any value other than `0` or `1` should be given a name in a constant declaration.
-   Names for constants (final variables) should use all uppercase letters, with underscores to separate words. Please use meaningful nouns or noun phrases. For example, the name `TEN` below doesn't add to the understanding of the program at all.

    ```java
    public final int TEN = 10; // useless!
    ```

## Formatting

-   **Indent your programs.** This enhances the readability of your programs. You must indent 3 or 4 spaces ([**Intellij**: Indentation](https://www.jetbrains.com/help/idea/indentation.html), **Eclipse**: Preferences/General/Editors/Text Editors)

    -   inside all brace pairs, and
    -   for simple statements following `if`, `while`, `for`, `switch`, and `do`.

    You should be sure your editor is set up to indent each line by 3 or 4 spaces and that it does not insert tab characters in the source code.

-   Statements that are spread over multiple lines must be indented to make it obvious which lines are continuations. For example,

    ```java
    System.out.print("This is a message that's broken into two"
            + " parts for no good reason.");
    ```

-   When a line gets too long (for example, more than 78 columns), break it at a reasonable place. ([**Intellij**: Wrapping and braces](https://www.jetbrains.com/help/idea/code-style-java.html#wrapping_and_braces_tab))
-   Line up the closing brace with the statement that includes the opening brace to make it clear how they are matched. For example,

    ```java
    if (radius > 0) {
        area = Math.PI * radius * radius;
    }

    if (radius > 0)
    {
        area = Math.PI * radius * radius;
    }
    ```

-   Each line must contain at most one statement, though a single statement may be spread over multiple lines.
-   There must be a space before and after each operator. Use one space after a comma.
-   Empty lines between different sections of the program and between different methods.

## Unit Testing

-   For some programs, Unit Testing with [JUnit](https://junit.org/junit5/) or testbed `main`s will be required.
-   Details will be specified in project descriptions.

## Test Specification

-   For some projects, a test specification will be required.
-   The test specification must be typed in a document and turned in by the specified date/time. **Handwritten documents are not acceptable.**
-   The test specification of a project must include the test cases showing that the project is meeting the specified requirements.
-   In the test document, you are required to specify each of the test cases with a description identifying the purpose of the test case, the input data, and the expected output. You must organize the test cases with a table. For example,

    | Test Case # | Purpose of the test case                                                                                                                                                                                                                                                                                        | Input Data                                                                              | Expected output                                            |
    | :---------: | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------- | ---------------------------------------------------------- |
    |      1      | Test the parameterized constructor for instantiating an instant of `Employee` class. The name and employment date are required to instantiate an object of the `Employee` class. The `toString()` method will be used to display the textual representation the object to be compared with the expected output. | "Lily", "11/27/2011"                                                                    | Lily 11/27/2011                                            |
    |      2      | Test the `equals()` method, which determines if two objects are equal. Two objects are equal if they have the same name and same employment date.                                                                                                                                                               | Case 1 <br> - Instance #1: "Lily", "11/27/2011" <br> - Instance #2: "Lily", "11/28/2011 | <br> - Case 1 returns `false` <br> - Case 2 returns `true` |
    |     ...     | ...                                                                                                                                                                                                                                                                                                             | ...                                                                                     | ...                                                        |

## Class Diagram

-   Some projects are required to include a Class Diagram.
-   The Class Diagram must use the UML notations discussed in class.
-   The diagram must show the classes and the relationships between the classes.
-   You must create the class diagram with a CASE tool. **Hand drawing is not acceptable!**

## Project Grading

-   Projects submitted must always compile, run, and produce the correct (required) output to receive the
    credits.
-   You are also expected to apply the object-oriented techniques and good software engineering practices covered in this course. If not, there would be no reason for you take this course!
-   Each project has specific requirements, which may include the test document, Class Diagram, JUnit tests, and "testbed `main`s", in addition to the source code.
-   In addition to producing the correct output, projects submitted to Canvas must meet the specified requirements. You will lose points for not meeting the requirements. The maximum points you will lose for each project is listed in the next section. The lowest grade you can receive for a project is 0.
-   Projects are normally graded within 7 days starting the next day of the project due dates. Given the high enrollment number of this class, I ask that if you have any problems with the grading of a given project, please email the grader within 7 days after the grades are posted. If you are not hearing back from the graders after 2 days, email me.

## Maximum Point Losses

-   **Doesn't Compile**: you lose ALL points, i.e., you get a 0. (this is also the minimum off)
-   **Runtime Error**: you lose ALL points, i.e., you get a 0. (this is also the minimum off)
-   **Incorrect Output**: 80% of the total possible points.
-   **Style & Documentation**: 30% of the total possible points; further broken down below:

    |        Guideline Violated        | Each offense | Max off |
    | :------------------------------: | :----------: | :-----: |
    |          Class Comment           |      1       |    2    |
    | Method/Constructor comment block |     0.5      |    3    |
    |         Braces lined up          |     0.5      |    2    |
    |        Naming Conventions        |     0.5      |    3    |
    |           Indentation            |     0.5      |    2    |
    |          Magic Numbers           |     0.5      |    2    |
    |     Space between Operators      |     0.5      |    1    |
