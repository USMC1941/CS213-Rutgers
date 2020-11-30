# Problem Set 12 - Multithreading

## Problem 1

Use threads to implement a stop watch that displays, once every five seconds, the minutes and seconds that have passed since it was started. The display should be in the form mm:ss for minutes and seconds. When the clock reaches 15 minutes, it should wrap back and start at 0 minutes and 0 seconds. The user should be able to stop the watch at any time. Write the complete code for the application. (Not the most accurate stop watch, but the model is useful for animations in which slight inaccuracies in time would not be detrimental.)

## Problem 2

Suppose you use a search engine to search for a word or phrase that results in a match with a large number (hundreds) of web pages. However, the browser will only display a list of n (some variable parameter) page links in one screenful. Implement a multi-threaded program with one thread for the browser display, and another for the search engine, and have the search engine deal out hits in batches of the max size the browser can display in one screenful. You may assume that a method called fetch has already been written in the search engine to fetch the next batch of hits, returned as a list of URL strings.

## Problem 3

In one of the lectures on design patterns (Week 10, Design Patterns 3), you saw the `Iterator` design pattern applied to iterate over a linked list. That implementation did not support the `remove` operation. Now suppose you were to update the iterator by supporting `remove`, which deletes the current node from the target linked list.

Explain why this new iterator is not thread-safe. (Thread-safe means multiple threads can be run through all methods of the class via a shared instance of the class, without unsafe interference.) How would you change the implementation to make it safe? Describe your solution, no code needed.
