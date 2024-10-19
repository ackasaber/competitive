# Competitive

A number of Java solutions for various competitive programming tasks.

The solutions presented weren't written in the real-time setting. The aim was to practice
solving classic algorithmic problems and reach a higher insight.

## Running the code

The online judges place a number of restrictions on the solution code. I generally don't follow them
in this repository since they vary greatly and are inconvenient. I do keep a single file per
solution, but in some cases various general-purpose classes are extracted into a separate file.
For example, the class `IntStack` that is omnipresent in simple graph problem solutions. Generally
the solutions are grouped by the task origin.

Therefore submitting these solutions requires adaptation: renaming the class, removing the package
declaration, copying any used general-purpose classes into the solution file as static inner classes
and so on. LeetCode solutions in particular don't need I/O code but I still write it for local
testing and debugging.

The solutions were written for JDK 21.

There is supplementary code for task investigation. It's placed in a sub-package next to the
corresponding task solution.

## TODO

- Restructure each competition into a separate POM.
- Restructure the code and add task examples as unit tests.
- Move all involved explanations into TeX.
- Need a more reasonable solution for a certain math-heavy task.