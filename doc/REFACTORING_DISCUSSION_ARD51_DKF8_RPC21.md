####Ryan Culhane, Anna Darwish, Dima Fayyad

###Refactoring Changes
* Reduced the complexity of the XMLParer method parseGrid by breaking it up into four different methods.  This 
removed the deep nesting of the control flow statements.  These changes make our code more readable and split the 
functionality of the larger method into small methods with specific functionality.
* Deleted unused import statements in the cell classes.
* Changed design to allow for communication of changed cell states from the Visualization to the Simulation to 
account for changes manually entered by the user.
* Made data types more general.  Replaced specific classes such as HashMap and ArrayList with more general interfaces
 such as Map and List.
* Added curly braces to if statements that were just one line


###Intended Changes
* Remove magic numbers and strings from the Visualization.  Visualization text is going to be read in from a file 
instead of being hardcoded in the Visualization classes.
* 