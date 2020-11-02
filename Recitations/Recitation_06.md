# Problem Set 6 - Access Levels, UML

## Problem 1

Suppose classes `A` and `B` are in package `ab`, and classes `C` and `D` are in package `cd`. Furthermore, both `C` and `B` extend `A`, and `D` extends `B`. Assume all classes are declared to be `public`.

1. Are `protected` members of `A` accessible in `C`? If yes, explain how. If not, explain why.
2. Are `protected` members of `A` accessible in `D`? If yes, how? If not, why?
3. Answer the same question as in 1. replacing `A` with `B`
4. Answer the same question as in 2. replacing `A` with `B`

## Problem 2

For each of the following pairs/groups of classes, show the most appropriate relationship between them using UML (include multiplicities for associations).

Also show code outlines for the classes involved, including fields that pertain to the associations between them, if any (i.e. connections that are not super-sub or interface implementations). It doesn't matter exactly what data structure you use for fields that are collections - that is something that can be refined at implementation time, and does not change the UML. (Remember, the UML is language-independent, and different languages may offer different options of data structures.)

1. `Document-Keyword `in a search engine
2. `Friend-Friend` on Facebook
3. `Book-Chapter`
4. `Parking Lot-Car`

## Problem 3

You are on a project that is developing software to manage a hospital. In particular, you are working on a sub-system that will model the patient care aspect including doctors, patients, hospital rooms, and services for which patients are billed. Services include medical services such as x-rays, as well as room services such as bed, TV, etc.

Draw a UML class diagram of your model, with just the names of classes (fields and methods not required), and relationships between them. Make sure to show multiplicities on associations.
