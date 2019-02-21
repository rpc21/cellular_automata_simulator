List of Issues:
* Duplicate Code
    * In GUIWatorPanel, each of the spinners where a user may enter a particular percentage of (for example) fish to be displayed in the grid, the other spinners would automatically adjust their own values to ensure the percentage of fish, sharks, and empty cells all added up to 100%. This could have been refactored into one method where each of the spinner's listener methods could have been set up to be codependent upon one another. Additionally, having learned about the possibility of having property binding between values, we also now realize including this code is repetitive of functionalities already provided to us. These issues are in lines 78-117 in GUIWatorPanel.
    * Another resolution is to create a data structure to represent the controls to loop over and initialize using a resources folder with the specifications for each of the controls (i.e. minium and maximum values of the spinners)


* Magic Values
    * In general, there are many places in our program where we use Strings that are defined as constants at the top of a class rather than reading in the common strings from a properties file.
    * In GUIManager, we hardcoded an error statement (Line 153) instead of creating an error class to manage this or loading the error statement from a resources folder. This makes our code less readable, and makes it difficult to resolve.
    * In the visualization portion of this assignment,
        we did not use the resources folder to load in        constants or control labels, such as the label for     the controls in GUIWatorPanel (Lines 12, 17, 21, 25,     29, and 33). Though this was not specifically flagged     in any other class, this is a design mistake we made     throughout the different control panels. 
    * In the configuration portion of the assignment, we did not define the string values of the tag names in a text file, but rather in an enum. It would definitely make more sense to define these in a text file. Similarly, the error messages were not defined in a text file. This would make changing these messages or the language much easier, which is desirable for reusability and extensibility. 
        

* Switch/Case Statements
    * We used a pseudo-factory design pattern to initialize the various simulations throughout our code.  This caused us to write many switch/case statements to identify which simulation is being run and initialize the simulation appropriately.  Most of our longest methods are simply these "factory methods" that have switch, case break statements for each of our simulations. An example of this is in the GUISimulationFactory on lines 14 and 82.
    * We could fix methods of this nature by reading in a resource file with the simulation names and the associated files.  This would allow us to

* Refactoring Main
    * In CellularAutomataMain, which is the main method, there is a clear need to refactor the code into multiple methods. The file selection, which spans lines 38-41 can be refactored into a method fileChooser that handles the popups and file browsing and returns a file. Since 2 files are selected by the user in the same way, this method will reduce duplicated code and make it easier to allow the user to select multiple files if desired. Lines 44-52 which all fall under the try statement can also be refactored into a method runSimulation, which will ensure that the order of execution of these lines is consistent, and take implementation details out of main. 
* Resolutions
    * Use property binding to ease communication between the front end and back end
    * Read in magic values, text labels and error messages from the file in the beginning so it is easier to manage.
    * We will use CSS Style Sheets to set up things that are hard-coded in the visualization portion of our projects so that we don't have as much duplicated/boilerplate code. This was also make our code more flexible to changes in GUI.
    * We felt as though a lot of code in visualization was complicated and confusing because we made visualization responsible for "control" as well. Had these been more distinctly separated, we would not have had so many difficulities gathering the information needed to do operations such as resetting.
    