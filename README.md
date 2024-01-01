# Competitive

A number of solutions for various competitive programming tasks.
The solutions are in Java.

The solutions presented weren't written in the real-time setting.
The aim was to practice solving classic algorithmic problems
and reach a higher insight, not bragging rights. For that reason
the solutions feature lengthy JavaDocs explaining the solution,
its code structure and algorithms involved.

Additionally, Java core language features were put to test.

## Running the code

The online judges restrict solutions to a single file per solution.
I do not adhere to this strategy completely in this repository. General-purpose
classes are being reused between different solutions, for example, the class
`IntStack` that is omnipresent in simple graph problem solutions.
Generally the solutions are grouped by the task origin. Any particular solution
can be run in Eclipse by pressing Alt+Shift+X, J with the solution Java file
focused.

Note that while the classes are placed in packages, the online judges
don't support it, so sending the solution would require deleting the
package declaration line. If a general-purpose class is being used, copy-paste
it as a static inner class into the solution class.

The solutions were written for JDK 21, but should generally be compatible with
the earlier JDK versions.

There is supplementary code for task investigation.
It's placed in a sub-package near the corresponding task solution.
Supplementary code for one of the tasks requires the SWT project opened
in Eclipse.
See the [official instructions](https://www.eclipse.org/swt/eclipse.php)
on setting up that project.

## License

There is nothing cutting-edge in the code, therefore I consider it public domain.

You're free to use this code any way you want.