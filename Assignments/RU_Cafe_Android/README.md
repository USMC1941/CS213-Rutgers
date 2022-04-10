# CS213 - RU Café Android

## Submission

-   Submit the zipped project folder.
    -   Write a comment identifying the API level and the [AVD](https://developer.android.com/studio/run/managing-avds) you used to run your app.
    -   It is your responsibility to make sure the submitted zipped file includes all the files required to run your Android app. You will **get 0 points** if your app does not run.

## Project Description

Develop an Android version of your RU Café. There is no restriction on how you design the GUIs, however, you must include the same functionalities described in RU Café, or **-2 points** for each improper implementation. You can remove the donut types and just have a list of donuts for selection, with $1.39 for each donut. You can modify the classes from RU Café as needed. The export of store orders is not required.

## Project Requirement

1. This is a group assignment. You MUST work in pair in order to get the credit for this program.
2. You MUST follow the **Software Development Ground Rules**, or you will lose points for not having a good programming style.
3. The comment block on top of each Java file must include the names of all team members.
4. You are NOT required to generate the Javadoc, however, you MUST comment ALL Java classes and methods, except the `onCreate()` methods.
5. You must include the following Android "activities" (`*Activity.java`) and their associated layout files (`*.xml` files), or you will **lose 5 points** for each activity missing.
    - Main menu – this is the `MainActivity`.
    - Ordering donuts – add/remove donuts with quantity and running subtotal.
    - Ordering coffee – add/remove add-ins and select sizes with running subtotal.
    - Order details – show the list of menu items added to the order, including the details for each item, and the subtotal, sales tax and total for the order; user can remove a selected item and place the order.
    - Store orders – show the list of orders with the details of every order; user can cancel an order.
6. You must use [`Toast`](https://developer.android.com/reference/android/widget/Toast) to show any messages displayed on the device, or **-2 points**.
7. Your app must NOT have any "hardcoded text" warnings. In other words, you must define all texts in the `strings.xml` resource file. **-1 point** for each violation, with a **maximum of 5 points off**.
8. You MUST define the launcher icon for your app, or **-2 points**.
9. You MUST define the activity titles displayed on top of the activities, **-1 point** for each violation.
10. You MUST define the parent activity for each activity in the manifest file, or **-1 point** for each violation.

## Program Testing

Your program must meet all the requirements and always run in a sane state. Your program must not crash under any circumstances. You are responsible to thoroughly test your Android app. You will **lose 2 points** for each exception not caught or malfunction, with a **maximum of 10 points off**.
