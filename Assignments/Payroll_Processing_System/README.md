# CS213 - Payroll Processing System

## Submission

Submit a copy of the zipped project folder.

1. You must include both team members' names in the comment block on top of EVERY Java file.
2. Your project folder must include the following subfolders/files for grading.
    - Source folder, including all Java files [35 points]
    - [JUnit](https://junit.org/junit5/) Test classes for `Company` and `Management` class. [20 points]
    - Javadoc folder, including all the files generated. [5 points]
    - Class Diagram, drawn with either Visual Paradigm or Draw.io. [5 points]

## Project Description

In this project, you will develop a **Payroll Processing System** to process the payments to the employees in a company. For simplicity, the system will not process the possible deduction items in the paychecks. You are not allowed to use any Java library classes, EXCEPT the [`Scanner`](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/Scanner.html) class, [`StringTokenizer`](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/StringTokenizer.html) class, [`Calendar`](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/Calendar.html) class, [`DecimalFormat`](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/text/DecimalFormat.html) class, and Java [`Exception`](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/Exception.html) class. You will **lose 5 points** for each Java library class imported to the project.

Your project shall process the command lines entered on the console and display the output on the console. The command lines include the instructions for adding/removing an employee, calculating the payments, printing the earning statements, and managing the working hours of the part-time employees.

Let's assume the system maintains an employee database, which may include 3 different types of employees: full time, part-time and management. Note that an employee with a management role is also a full-time employee. You are required to implement an array-based container class to hold the employee database. Every employee in the database has a profile, including the employee's name, department and the date hired, assuming there are 3 departments in the company: "computer science", "electrical and computer engineering" and "information technology". The payroll is processed every other week. Calculation of the payments are different depending on an employee's employment type. The following table has the details.

| Employment Type | Payment Calculation                                                                                                                                                                                                   | Additional Compensation                                                                                                                                                                        |
| --------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Full time       | - There are 26 pay periods per year. <br> - The payment for each pay period equals to annual salary divided by 26.                                                                                                    | Regular full time: NONE. <hr> There are 3 different types of management roles: <br> 1. Manager: $5,000 annually. <br> 2. Department Head: $9,500 annually. <br> 3. Director: $12,000 annually. |
| Part time       | - Pay by the hours worked during the 2-week period. <br> - Each part-time employee has a different hourly rate. <br> - Maximum hours per week is 40, i.e., 80 per pay period, NOT to exceed 100 hours per pay period. | The hours exceed 80 will be paid 1.5 times of the regular hourly rate.                                                                                                                         |

## Requirements

1. This is a **group assignment**. You MUST work in pair in order to get the credit for this project.
2. You MUST follow the **Software Development Ground Rules**, or you will lose points for not having a good programming style.
3. You MUST create a **Class Diagram** for this project to document the software structure. The diagram is worth 5 points. You will **lose the 5 points** if you submit a hand-drawing diagram.
4. Each Java class must go in a separate file. **-2 points** if you put more than one Java class into a file.
5. You are not allowed to have redundant code in this project; **-2 points** for each violation below.

    - Unused code: you write code that was never called or used.
    - Duplicate code segments for the same purpose in more than one places/classes.
    - Define common instance variables in each of the subclasses.
    - Define specific instance variables in the superclass that are not used by all the subclasses.

6. You must define classes with the following inheritance relationships. **-5 points** for each class missing, or incorrect inheritance structure. All instances variables must be `private` or **lose 2 points** for each non-`private` modifier.

    - `Employee` class defines the common data and operations for all employee type; each employee has a profile that uniquely identifies the employee.
    - `Fulltime` class `extends` the `Employee` class and includes specific data and operations to a full-time employee.
    - `Parttime` class `extends` the `Employee` class and includes specific data and operations to a part-time employee.
    - `Management` class `extends` the `Fulltime` class and includes specific data and operations to a full-time employee with a management role.

7. Polymorphism is required. You cannot use the `getClass()` method for checking the class type listed in #6 above, or you will **lose 10 points**. Use `equals()` methods for checking the actual type of instance of `Employee`.

    - You must include `toString()` and `equals()` methods in all classes listed in #6 above with the annotation `@Override` on top of the methods. **-2 points** for each `toString()` or `equals()` method missing. The `toString()` methods in all subclasses must reuse the code in the superclass by calling the `toString()` method in the superclass. **-2 points** for each violation.
    - You must include the following method in `Fulltime`, `Parttime` and `Management` class, and add the `@Override` tag for this method. **-5 points** for each violation. You cannot change the signature of this method, and must reuse the code in the superclass whenever possible. **-2 points** for each violation.

        ```java
        @Override
        public void calculatePayment() {}
        ```

    - **-1 point** for each `@Override` tag missing.

8. In addition to the classes in #6, you must include the 5 classes listed below. **-5 points** for each class missing. You CANNOT do I/O in all classes, EXCEPT the `PayrollProcessing` class, and the `print` methods in the `Company` class. **-2 points** for each violation. The floating-point numbers must be displayed with 2 decimal places. **-1 point** for each violation.

    1. `Date` class. Import this class from the Library Kiosk Project. The class implements the Java Interface `Comparable`. You must implement the overriding `compareTo()` method, or you will **lose 2 points**.

        > See [`Date.java`](src/Date.java)

    2. `Profile` class. Define the profile of an employee with the following. You cannot add additional instance variables and must include `toString()` and `equals()` methods. **-2 points** for each violation.

        > See [`Profile.java`](src/Profile.java)

    3. `Company` class. This class is an array-based container class that implements the employee database. The array will store a list of employees, which may include the instances of full-time, part-time and management. The initial capacity of the container is `4`. It will automatically grow the capacity by `4` if the array is full. You must implement and use the methods listed, and you cannot add additional instances variable or change the signatures of the methods. **-2 points** for each violation. You can add additional methods.

        > See [`Company.java`](src/Company.java)

    4. `PayrollProcessing` class. This is the **user interface** class that reads/writes from/to the console. This class **handles all exceptions and invalid data** before it calls the methods in `Company` class to complete the associated commands. For example, `InputMismatchException`, `NumberFormatException`, invalid dates, invalid department codes, invalid codes for management roles, and negative values. Whenever there is an exception or invalid data, display a message on the console. **-2 points** for each exception not caught or invalid data not checked in this class or messages not displayed.

        A command line always begins with a command in uppercase letters followed by some data tokens delimited by whitespaces. A whitespace could be a single space, multiple spaces, a tab or a new line (`\n`). The commands are case-sensitive, i.e., the commands with lowercase letters are invalid. In addition, you are required to handle the bad commands that are not supported. **-2 points** for each invalid command not handled.

        - `A` command

            ```
            AP Doe,Jane CS 7/1/2020 45.9 // Add a part-time employee with the hourly pay rate
            AF Doe,Jane CS 1/1/2005 85000 // Add a full-time employee with the annual salary
            AM Doe,Jane CS 2/28/2012 85000 1 // Add a full-time with the role "Manager"
            AM Doe,John CS 2/28/2012 85000 2 // Add a full-time with the role "Department Head"
            AM Doe,John ECE 2/28/2012 85000 3 // Add a full-time with the role "Director"
            ```

            Add a new employee to the company database. For management role, use the integer codes `1` for Manager, `2` for Department Head and `3` for Director. Display

            ```
            invalid management code.
            ```

            if the integer code is not supported.

            An employee's name starts with the last name followed by a comma and the first name.

            Department codes are `CS`, `ECE` or `IT` for the 3 different departments, display

            ```
            invalid department code.
            ```

            if the code is not supported.

            If the same employee profile exists in the database, display

            ```
            Employee is already in the list.
            ```

            otherwise, display

            ```
            Employee added.
            ```

            Hourly rate and salary cannot be negative.

        - `R` command

            ```
            R Doe,Jane CS 7/1/2020 // Find the employee with the same profile and remove it
            ```

            Remove an employee from the company database. If there is no such employee profile in the database, display

            ```
            Employee doesn’t exist.
            ```

            otherwise, display

            ```
            Employee removed.
            ```

        - `C` command – Calculate the payments for all employees.
        - `S` command

            ```
            S Doe,Jane 7/1/2020 100 // Set the hours to 100 with the employee profile
            ```

            Set the working hours for a part-time employee; the number of hours cannot be negative.

        - `P` commands – Print the earning statements.

            ```
            PA // Print the earning statements for all employee
            PH // Print the earning statements for all employee in the order of date hired
            PD // Print the earning statements for all employee grouped by department
            ```

        - `Q` command – Display

            ```
            Payroll Processing completed.
            ```

            and stop the program execution.

    5. `RunProject2` class. This is the driver class to run the project.

        > See [`RunProject2.java`](src/RunProject2.java)

9. You must follow the instructions in the **Software Development Ground Rules** and comment your code. You are required to generate the Javadoc after you properly commented your code. Your Javadoc must include the documentations for the constructors, private methods and public methods of all Java classes. Generate the Javadoc in a single folder and include it in your project folder to be submitted. You will **lose 5 points** for not including the Javadoc.
10. You must create a JUnit test class for `Company` class, and write test cases for the `add()`, `remove()` and `setHours()` methods. You are also required to create a JUnit test class for `Management` class, and write test cases for the `calculatePayment()` method. Each test method is worth 5 points.
