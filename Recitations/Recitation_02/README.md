# Problem Set 2 - GUI Application with FXML

## About

Write an application that can accept an order to make a sub, out of three constituents: meat, cheese, and toppings. (There aren't any choices of bread.)

Use checkboxes to select (or unselect) kinds of meat (e.g. turkey, ham, roast beef, etc.), and kinds of toppings (e.g. lettuce, tomato, olives, etc.)

Use a combo box to select one kind of cheese out of at least 3 options (e.g. swiss, american, provolone, etc.)

When the user confirms the order (via a button), display a message in a textarea (non-editable), listing the ingredients that went into the sub.

Make a package for the view (fxml + controller), and another package for the main app driver code.

## How to Run

This is a JavaFX project managed with [Gradle](https://gradle.org/). If Gradle is installed, run

```sh
gradle run
```

to run the app. If Gradle is not installed, run

```sh
./gradlew run
```

in Bash or

```sh
.\gradlew.bat run
```

in Powershell.
