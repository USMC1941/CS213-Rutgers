# CS213 - Payroll Processing System GUI

Submission

-   You must include both team members’ names in the comment block on top of EVERY Java file.
-   Your project folder must include the following subfolders/files for grading.
    -   Source folder, including all Java files [35 points]
        -   `*.java` from Payroll Processing System Project
        -   3 JavaFX GUI files – `Main.java`, `Controller.java`, `View.fxml`.
    -   Test design document [10 points]
    -   Javadoc [5 points]

## Project Description

In this project, you will develop a GUI (graphical user interface) with JavaFX for the Payroll Processing System you created. The GUI shall replace the `PayrollProcessing` class (console user interface) in the Payroll Processing System Project. In other words, this project is a GUI version of the Payroll Processing System Project. In addition, you must add the text file import and export functionality to the GUI. The file import will load (read) the employee database from a text file to the array (instance of `Company` class), and the file export will write the employee database from the array to a text file.

## Requirements

1. This is a group assignment. You MUST work in pair in order to get the credit for this project.
2. You MUST follow the **Software Development Ground Rules**, or you will lose points for not having a good programming style.
3. Each Java class must go in a separate file. **-2 points** if you put more than one Java class into a file.
4. You MUST include all the classes from the Payroll Processing System Project and use them in this project, EXCEPT `PayrollProcessing` class and `RunProject2` class. **-2 points** for each class not used.
5. This project uses the Model-View-Controller (MVC) design pattern. You must use ONE JavaFX fxml file for the "view", ONE `Controller` class for the "control", and ALL the entity classes from the Payroll Processing System Project for the "model". The `Main.java`, fxml and controller files constitute the GUI version of Payroll Processing System. You will get **0 points** if you don’t follow the MVC structure.
6. Your GUI must include the following JavaFX components.
    1. Use at least 3 different Layout Panes, such as [`BorderPane`](https://openjfx.io/javadoc/18/javafx.graphics/javafx/scene/layout/BorderPane.html), [`GridPane`](https://openjfx.io/javadoc/18/javafx.graphics/javafx/scene/layout/GridPane.html), [`VBox`](https://openjfx.io/javadoc/18/javafx.graphics/javafx/scene/layout/VBox.html), [`Hbox`](https://openjfx.io/javadoc/18/javafx.graphics/javafx/scene/layout/HBox.html), ... , or **-5 points**.
    2. Use a [`TextArea`](https://openjfx.io/javadoc/18/javafx.controls/javafx/scene/control/TextArea.html) to display output, or **-5 points**. All output MUST be appended to the `TextArea`. You are NOT ALLOWED to use `System.out` in ALL CLASSES, or you will **lose 10 points**. This means you must modify all the `print()` methods in the `Company` class to return a `String`, or define a `toString()` method for the `Company` class.
    3. Use a [`RadioButton`](https://openjfx.io/javadoc/18/javafx.controls/javafx/scene/control/RadioButton.html) group for single-selection items, or **-2 points**; for example, employee types, admin roles, or department, etc.
    4. You must disable not applicable items, **-2 points** for each violation; for example,
        - Only a part-time has hours worked and hourly pay rate.
        - Only a management has the role of either manager, department head, or director.
        - Only a full-time and a management has annual salary.
    5. You MUST set the title of the `primaryStage` (title for the window.) or **-2 points**.
    6. Use the [`FileChooser`](https://openjfx.io/javadoc/18/javafx.graphics/javafx/stage/FileChooser.html) class to select the file you wanted to import/export. A text file [`database.txt`](database.txt) is provided for you to test your program. You must add an `exportDatabase()` method to the `Company` class for the file export. Exporting the database does not remove the employees from the array. You will **lose 10 points** if import/export functionality is not implemented.
    7. You are required to generate the Javadoc after you properly commented your code. Your Javadoc must include the documentations for the constructors, `private` methods and `public` methods of all Java classes (`*.java` files.) You must comment the `Main.java` and `Controller` class and include them in the Javadoc. DO NOT include the `*.fxml` file, which is NOT a java file. Generate the Javadoc in a single folder and include it in your project folder to be submitted. You are responsible to double-check your Javadoc after you generated them. The grader will navigate the Javadoc with the `index.html`. You will **lose 5 points** for not including the Javadoc, OR, the grader cannot navigate your Javadoc through the `index.html`.

## System Testing

1. You MUST create a test document and design the test cases for testing this project. Use the test cases in the Payroll Processing System Project sample input as a reference to design your test cases. For example, adding and removing employees, computing payments, set the hours worked by a part-time, import and export database. In addition, you must design the test cases to test all possible invalid/exception conditions. For example, type mismatch or not entering data on GUI, etc. The test document is worth 10 points.
2. Use your test cases to manually test your GUI. All invalid data should be rejected by the GUI. Proper error messages must be displayed in the `TextArea`. You will **lose 2 points** for each invalid condition not rejected, or error message not properly displayed in the `TextArea`.
3. Your program must always run in a sane state and should not crash in any circumstances. The graders will try to produce Exceptions while running your GUI. You must catch all Java Exceptions. Your program shall continue to run until the user stops the program execution or closes the window. You will **lose 2 points** for each exception not caught.
