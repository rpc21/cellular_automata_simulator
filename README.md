cell society
====

This project implements a cellular automata simulator.

Names: Ryan Culhane, Anna Darwish, Dima Fayyad

### Timeline

Start Date: January 27 2019

Finish Date: February 4 2019

Hours Spent: 300 hours

### Primary Roles

Configuration: Dima Fayyad

Simulation: Ryan Culhane

Visualization: Anna Darwish

### Resources Used

* https://en.wikipedia.org/wiki/Wa-Tor
* https://www2.cs.duke.edu/courses/spring19/compsci308/assign/02_cellsociety/nifty/scott-wator-world/
* Code provided in CS308 from lab spike_cellsociety

### Running the Program

Main class: CellularAutomataMain

Data files needed: GameOfLifeTest.xml, GameOfLifeStyle.xml, PercolationTest.xml, PercolationStyle.xml, SegregationTest.xml, SegregationStyle.xml, 
SpreadingFireTest.xml, SpreadingFireStyle.xml, WatorTest.xml, WatorStyle.xml, SugarTest.xml, SugarStyle.xml, ForageTest.xml, ForageStyle.xml

Interesting data files: WatorTest.xml

Features implemented: 
* Configuration: The ability to read in the simulation from xml file. The ability to read in the initial states for each individual cell or for larger simulations, one can generate the initial states by specifying the percentage of each cell type used in the simulation in the xml file. To have the states be generated based on cell type percentages, set GenerateStatesBy tag in the xml file to "random".
* Simulation: The ability to run one of 7 simulations: Game of Life, Segregation, Predator-Prey, Fire, Percolation, Forage, & Sugar.  
* Visualization: The ability to display the simulations and allow the user to interact with the simulation by changing simulation-specific parameters, simulation speed, pause, reset, and switch simulations using the GUI. From visualization, you can track the current simulation's distribution in the chart above, modify the shape of the cells, change its neighbor type,
run an additional simulation of the same type to compare states, and cycle through new states by clicking on the grid, 

Assumptions or Simplifications:

* Switching between simulations requires the 5 default files GOLTest.xml, PercolationTest.xml, SegregationTest.xml, SpreadingFireTest.xml, and WatorTest.xml
* Percolation continues running until no changes occur between iterations of the simulation. The number of filled water cells at the start of the configuration is not limited to 1; the user can begin the simulation with as many water filled cells as desired. 
* The Spreading Fire simulation trees only remain on fire for one iteration before burning down
* The Predator Prey simulation assumes predator and prey can only move to immediately adjacent vacant cells (including wrap around adjacent cells)
* We assumed that the user would only ever want to run one or two simulations at the same time, and that the user would want to run the same simulation at the same time.
* The graph only displays the original simulation's distribution

Known Bugs:

* Grid changes size when you switch shape
* Sometimes grid options button overlaps grid after many cycles of changes to the current GUI
* Clicking on the grid to change the cell state when the shapes are a triangle is non-intuitive because the triangle is in a stackpane

Extra credit: 

* User can actively change neighbor type during simulation
* User can modify display of two grids during simulation
* Added additional neighbor types - i-formation, knight, diagonal, 


### Notes

We all agree that we want to come up with a better way to communicate information from visualization to simulation. We could
have done a better job making sure each end was able to support the other in terms of extension. For example, on visualization's end,
it was difficult to support a change that required displaying a layered cell, or something other than a polygon. Additionally
from visualization's end, information should have been communicated in a more diverse manner rather than passing back an 
aggregate set of parameters and following through a very generic reset method.


### Impressions

We underestimated how difficult it would be to add in the extensions, and we agree that we should have devoted more time in the 
beginning to considering what extensions would be implemented. If we had done this, it would have been easier to extend our code
without sacrificing some of its structure and encapsulation. One thing we really did learn a lot about how to separate the components of the 
project, however, and do our best to maintain encapsulation so changes in one area did not break other parts of the code.
One thing we want to learn more about is how to better communicate among configuration, simulation, and visualization. Passing around
information and knowing when to update was difficult to ensure, as it had to be logical for both the user and the simulation 
classes.

