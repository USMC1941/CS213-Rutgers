# CS213 - Library Kiosk

## Submission

One of the team members is responsible to submit the zipped project folder. The zipped file MUST include the following subfolders.

1.  Source folder `src`, including all Java files [**30 points**]

    -   MUST include both team members' names with `@author` in the comment block on top of EVERY Java class.
    -   In the `Date` class, you MUST include a testbed `main`, which implements the test cases in the test design document.

2.  Javadoc folder `doc`, including all the files generated. [**5 points**]
3.  Test design document, which is typed with a document editor, including all test cases you used to test the `isValid()` method in the `Date` class. [**10 points**]

## Project Description

In this project, you will implement an array-based container class `Library` and use it to hold the information of a list of books owned by a library. An instance of the `Library` class is a growable bag data structure with an initial capacity of `4`, and automatically grows (increases) the capacity by `4` whenever it is full. You are NOT allowed to use the `ArrayList` class, or you will get **0 points** for this project. A `Kiosk` class must be included in the project to provide library services and process the command lines read from the console. The library services shall include **adding**, **removing** books to/from the library catalog, **checking out** and **returning** books, as well as **displaying** the list of books by the date published or by the books' serial numbers.

`Scanner` class and `StringTokener` class should be used to read the command lines from the console. When your project starts running, it shall display

```
Library Kiosk running.
```

Next, it will read and process the command lines continuously until the user enters the `Q` command to quit. Before the project stops running, display

```
Kiosk session ended.
```

A command line always begins with a command and followed by a comma and some data tokens. Each data token is also delimited by a comma. Some examples of valid command lines are demonstrated below. All commands are case-sensitive, which means the commands with lowercase letters are invalid. You are required to deal with bad commands that are not supported.

-   Adding a book

    ```
    A,Programming in Java,11/20/2019
    ```

    `A` is a command for adding a book to the library, followed by the name of the book, and the date published. If the bag is full, the bag automatically grows the capacity by `4`.

    A serial number will be automatically generated to create the book instance with the name and the date published. The serial number is a five-digit number starting `10001`, which will be increased by 1 for each subsequent instance of `Book`.

    The date will always be in `mm/dd/yyyy` format. However, you must check if the date is valid with the `isValid()` method in the `Date` class (see **3. `Date` class** below.) Display

    ```
    Invalid Date!
    ```

    if the date is invalid; otherwise, display

    ```
    Programming in Java added to the bag.
    ```

    on the console when the book is added.

-   Removing a book

    ```
    R,10005
    ```

    `R` is a command for removing a book from the library given a book's serial number. If the book doesn't exist, display

    ```
    Unable to remove, the library does not have this book.
    ```

    otherwise, display

    ```
    Book#10005 removed.
    ```

-   Checking out a book

    ```
    O,10005
    ```

    `O` is a command for checking out a book from the library. If the library doesn't own the book, or the book has already been checked out, display

    ```
    Book#1005 is not available.
    ```

    otherwise, display

    ```
    You've checked out Book#10005. Enjoy!
    ```

-   Returning a book

    ```
    I,10005
    ```

    `I` is a command for returning a book to the library. If the book doesn't belong to the library, or the book is not checked out, display

    ```
    Unable to return Book#10005.
    ```

    otherwise, display

    ```
    Book#10005 return has completed. Thanks!
    ```

-   Printing the library catalog

    ```
    PA // Output the list of books to the console with the current sequence
    PD // Output the list of books by the dates published in ascending order
    PN // Output the list of books by the book numbers in ascending order
    ```

-   `Q` command will stop the program and display

    ```
    Kiosk session ended.
    ```

## Program Requirement

1. You MUST follow the **Software Development Ground Rules**. You will lose points if you are not following the rules.
2. There are sample input and output for your reference. The graders will be using the test cases in the order of the sample input to test your project. You will **lose 2 points** for each incorrect output.

    > See [`sample_input.txt`](sample_input.txt) and [`sample_output.txt`](sample_output.txt)

3. Each Java class must go in a separate file. **-2 points** if you put more than one Java class into a file.
4. Your program MUST handle bad commands! **-2 points** for each bad command not handled.
5. You are not allowed to use any Java library classes, except [`Scanner`](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/Scanner.html), [`StringTokenizer`](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/StringTokenizer.html) and [`Calendar`](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/Calendar.html) classes. **-5 points** for each violation.
6. You MUST include the classes below. **-5 points** for each class missing. Add additional classes if necessary.

    1. `Book` class.

        > See [`Book.java`](src/Book.java)

        - This class defines the abstract data type `Book`, which encapsulates the data fields and methods of a book. The book number is a 5-digit serial number automatically generated starting `10001`. You CANNOT add other data members, EXCEPT `static` variables or constants.

        - You CANNOT read from console or use `System.out` in this class. **-2 points** for each violation.

        - `toString()` method returns a textual representation of a book in the following format:

            ```
            Book#10007::Design Patterns::5/30/1996::is available.
            ```

        - `equals()` method returns `true` if the serial numbers for the 2 book objects are the same.

        - You CANNOT change the signatures of the `toString()` and `equals()` methods. You cannot remove the `@Override` tags. **-2 points** for each violation. You CAN add constructors, `private` methods (helper methods) and `public` methods.

    2. `Library` class.

        > See [`Library.java`](src/Library.java)

        - This is the **container class** that defines the abstract data type `Library` to hold library catalog and its operations. You CANNOT add other data members, EXCEPT constants. You CANNOT change the signatures of the methods listed. **-2 points** for each violation. You must implement all the methods listed, **-2 points** for each method not implemented or not used. You can add other methods if necessary.
        - You CANNOT use `System.out` in this class, except the 3 `print()` methods, **-2 points** for each violation.
        - The `remove()` method calls the helper method `find()` and finds the index of the book to be removed. This method maintains the current sequence of books in the array after the removal, **-3 points** if this is not done.
        - You can use any sorting algorithms for sorting.

    3. `Date` class.

        > See [`Date.java`](src/Date.java)

        - This class defines the properties of a `Date` object. You CANNOT add other data members to this class, and you CANNOT use `System.out` in this class. **-2 points** for each violation.
        - The `isValid()` method checks if a date is valid.

            - For a date with the year less than 1900 or a date beyond today's date is invalid.
            - For the month, January, March, May, July, August, October and December, each has 31 days; April, June, September and November, each has 30 days; February has 28 days in a normal year, and 29 days in a leap year. To determine whether a year is a leap year, follow these steps:

                1. If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
                2. If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
                3. If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
                4. The year is a leap year.
                5. The year is not a leap year.

            - You MUST design the test cases to thoroughly test the `isValid()` method. You must write a testbed `main` and implement the test cases. You must follow the instructions in the "Test Specification" section of the **Software Development Ground Rules**. You will **lose 10 points** if you do not submit the test document, or not follow the format. In the testbed `main`, you MUST write code to print out the test results to the console showing the test cases are passed or not passed; **-5 points** if the testbed is missing.

    4. `Kiosk` class for processing the command lines from the console.

        > See [`Kiosk.java`](src/Kiosk.java)

        - This class is the **user interface class** that handles the input and output. You must define a `run()` method, or you will **lose 5 points**.
        - You can define the data members you need and define some `private` methods to handle the commands. Make sure you follow the ground rules and have a good programming style.

    5. `RunProject1` class is a driver class to run your Project. The `main` method will call the `run()` method in the `Kiosk` class.

        > See [`RunProject1.java`](src/RunProject1.java)

7. You must follow the instructions in the **Software Development Ground Rules** and comment your code. You are required to _generate the Javadoc_ after you properly commented your code. Your Javadoc must include the documentations for the constructors, private methods and public methods of all Java classes. Generate the Javadoc in a single folder and include it in your project folder to be submitted. Please double-check your Javadoc after you generated it and make sure the descriptions are not empty. You will lose points if the descriptions in the Javadoc is empty. You will **lose 5 points** for not including the Javadoc.
