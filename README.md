cell society
====

This project implements a cellular automata simulator.

Names: Ryan Culhane, Anna Darwish, Dima Fayyad

### Timeline

Start Date: January 27 2019

Finish Date: February 4 2019

Hours Spent: 

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

Data files needed: GOLTest.xml, PercolationTest.xml, SegregationTest.xml, SpreadingFireTest.xml, WatorTest.xml

Interesting data files: WatorTest.xml

Features implemented: 
* Configuration: The ability to read in the simulation from xml file. The ability to read in the initial states for each individual cell or for larger simulations, one can generate the initial states by specifying the percentage of each cell type used in the simulation in the xml file. To have the states be generated based on cell type percentages, set GenerateStatesBy tag in the xml file to "random".
* Simulation: The ability to run one of 5 simulations: Game of Life, Segregation, Predator-Prey, Fire, and Percolation.  
* Visualization: The ability to display the simulations and allow the user to interact with the simulation by changing simulation-specific parameters, simulation speed, pause, reset, and switch simulations using the GUI.  

Assumptions or Simplifications:

* Switching between simulations requires the 5 default files GOLTest.xml, PercolationTest.xml, SegregationTest.xml, SpreadingFireTest.xml, and WatorTest.xml
* Percolation continues running until no changes occur between iterations of the simulation. The number of filled water cells at the start of the configuration is not limited to 1; the user can begin the simulation with as many water filled cells as desired. 
* The Spreading Fire simulation trees only remain on fire for one iteration before burning down
* The Predator Prey simulation assumes predator and prey can only move to immediately adjacent vacant cells (including wrap around adjacent cells)

Known Bugs: 

Extra credit: 


### Notes


### Impressions

