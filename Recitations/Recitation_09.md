# Problem Set 9 - Design Patterns

## Problem 1

Draw a state diagram that models a user's shopping session at [Amazon.com](https://www.amazon.com/), starting with a search. Show the UML for an implementation using the state design pattern including key fields and headers for the methods in the states.

## Problem 2

Show how you would enhance the Singleton pattern to allow up to a maximum number of instances of an object. There should be a way for clients to recycle instances, i.e. when a client is finished with an instance, it gives it up, and this instance can be later dealt out in response to a new instance request.

## Problem 3

Say you design a `BinaryTree` class. How will you use the `Iterator` design pattern to implement preorder, inorder, and postorder traversals, each of which just prints the data stored at each node? Sketch your design, and show how a client can call on the different traversals.

## Problem 4

You are asked to implement an application that can plot graphs of mathematical functions, each function being of the form `y = f(x)`. While there may be a choice of functions that can be plotted, only one function is plotted at a time on a given 2-D screen. How would you use the `Template Method` design pattern to build your code? Write the outline of each class in your design, including relevant fields, constructors, and methods - pick any two specific mathematical functions to demonstrate the pattern.
