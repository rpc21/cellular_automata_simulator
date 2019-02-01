Inheritance Review - Simulation
===================

# Part 1

1. We are encapsulating the rules of each game in the game specific cell classes.  The visualization does not need to
 know what kind of cells are actually in the game or what is happening to update the states of the cell.  The 
 visualization only needs to know what color to display and where to display it (relative to the other cells).  The 
 visualization also needs to be able to communicate changes to parameters to the cell.
   
2. The inheritance hierarchies we are using in the cells are that we have an abstract cell class that defines the 
instance variables common to cells of all kinds and the methods common to all cells such as calcNewState, getMyColor, 
and updateState.
   
3. We are trying to keep the specifics of how cells get updated and how they apply their simulation specific rules 
hidden from the visualization and the configuration.  We use polymorphism to allow any cell to be able to calculate 
its next state and to update itself from its current state to its next state.  Another hierarchy we use is a 
CellState interface and simulation specific enums that implement this interface letting cells in general have a 
representation of their state.
    
4. The possible errors involve if the visualization tries to update parameters that are not valid for the type of 
cells currently in the simulation.  Alternatively, if the simulation passes a parameter with the wrong name, it won't
 be updated appropriately in the cells.
    
5. The design is good because it is easily extendable.  To create a new kind of simulation one just has to extend the
 abstract cell class and implement the rules for determining neighbors and updating the cell state.  Furthermore, the
  polymorphism we use in our cell hierarchy allows the specific implementations of the rules in the cells to be 
  hidden from the simulation classes and the visualization.

# Part 2
1. The cells have to get information about the particular parameters and size of the grid from the configuration.  
The cells then need to be able to pass their color and location to the visualization so they can be visually 
represented on the screen.

2. The important dependencies are that the parameters' names are correct, once the configuration, simulation and 
visualization all have consistent naming conventions, there are few dependencies between the branches.

3. We can minimize these dependencies by ensuring that each area of the project is consistent in its naming conventions.

# Part 3
1. Potential Use Cases
    * Simulation tells cells to advance to next state
    * Configuration sets the parameters of the cell
    * Visualization changes the parameters of the cells
    * Visualization asks for location of the cell
    * Visualization asks for the color of the cell
2. I am most excited about making cells as extendable as possible
3. I am most worried about implementing Wator World