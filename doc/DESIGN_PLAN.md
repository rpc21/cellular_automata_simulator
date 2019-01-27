Cell Society Plan
===================

# Introduction

## Problem

    * We will create an interface that allows user to predict, model, and interact 
    with complex trends in human behavior and natures. These trends are known as Cellular 
    Automata, which can be generally described as ones in which members of a community 
    respond to the states of those around them, leading to a change in the community 
    as a whole over time.
## Design Goals

    * Make the simulation as flexible as possible for extending to other types of Cellular 
    Automata that behave according to different rules.   
    * Make the simulation interactive for the user to be able to set simulation 
    parameters and simulation speed
        
## Primary Architecture

    * Visualization can’t be closed to configuration because some of the user changes 
    will require restarting the user simulation. For example, Schelling’s Segregation 
    model would essentially have to restart if the distribution of races were to change. 
    Visualization and simulation, however, can be closed to one another since simulation 
    should be updating the current state of the Actors, while visualization is simply 
    displaying that information and passing any user updates of parameters to simulation
    * Configuration needs to provide simulation with the information it needs to model 
    and update a specific implementation of Cellular Automata. 
    
# Overview

The above is a map of the classes we intend to write for this project. The two abstract 
classes are the Simulation class and the Cell class. The simulations will be split up by type, 
where each type of simulation has its own class. While we initially considered only using one 
simulation class, we determined that the best way to keep specific information about the 
simulation, such as status (go, pause, end) from the individual cells was to hand this 
responsibility to the simulation class, and doing so requires different subclasses 
corresponding to each simulation. Similarly, the cells will be split into different subclasses, 
as the cells will be responsible for determining their next state and keeping track of their rules. 
Since the cell is primarily responsible for determining its next state based on the rules of the 
simulation which will vary depending on the cell type, we decided that the best way to do 
this was to have different cell subclasses. These design choices are in line with the design 
goal of polymorphism, as our chosen design will allow us to write code that is dependent on 
abstractions rather than types. Since each simulation requires a grid, which we have chosen to implement 
as a 2D array, only one Grid class is specified in our design, and has methods for putting items in the 
grid, and determining other location-specific information. Both the Cell and the Simulation have access to 
the grid, as the Cells need access to their neighbors to determine their next state, 
and the simulation is responsible for taking the information about the new state from the 
cells and updating the grid as necessary. The visualization will take information 
from Simulation and display it. 

# User Interface

## General to All Simulations

    * Pause/Start - Button to freeze simulation and begin again
    * Restart - Begin simulation again from starting state
    * Step - If it’s paused, move one step forward
    * Switch Simulation - Drop down menu
    * Adjust Speed of Simulation - Slider to change how quickly simulation transitions from one state to the next
## Specific to Simulations

    1. Predator-Prey
        * Slider - Predator Breed
        * Slider - Predator Eat
        * Slider - Prey Eat 
    2. Segregation
        * Slider - % Empty
        * Slider - % Group One
        * Slider - % Group Two
        * Slider - % Group One Tolerance
        * Slider - % Group Two Tolerance 

## Design Details

    1. Grid Interface:
        * The grid interface outlines the grid operations that may be useful for different cells.  
        These include finding open spots in the grid if the cell needs to move, finding a cell’s neighbors, 
        and checking if a location is valid.  Grid’s should also be able to add put and get objects from the grid.
        * We chose to make the grid an interface that outlines possible actions a grid may take so we can implement 
        this interface in different types of grids such as an “infinite grid” or a bounded grid later on in the project.
    2. Abstract Cell Class:
        * The abstract cell class will define the behavior that all cells need to have.  These include 
        updating the cell’s state based on its state and the state of its neighbors, getting a cell’s neighbors 
        from the grid and setting the cell’s state.
        * We decided to have a hierarchy of different kinds of cells because as the simulations get more complex,
        the cells have different properties and actions that they can take and we determined that it is better 
        to create cells for each simulation that can implement that simulation’s rules than to hard code all of 
        the different conditionals for how a cell could be updated.
        * The cells for the different type of simulations will extend from this abstract class and will override the
        methods in their own simulation-specific ways.
        * To extend this project to include a new kind of simulation, one has to add a new cell class 
        (or possibly many if the simulation has different types of cells like Wator) that extends this abstract 
        cell class and implements the rules governing that cell.
    3. Abstract Simulation Class
        * The abstract simulation class will define the behavior that all simulations need to have.  
        All simulation classes will be able to update their parameters, run a batch update of the cells 
        in the simulation and check to see if the simulation has reached a final state.  The abstract simulation 
        will also have all the methods that are similar amongst different simulations such as play, pause, setSpeed, 
        step, switchSimulation and resetSimulation.
        * We decided to create a hierarchy of Simulation classes that correspond to the different types of simulations 
        because we realized each different simulation will have different adjustable parameters that need to be displayed 
        and changed and the best way to display these parameters would be if each simulation was defined in its own class.
        * To extend the project to include a new kind of simulation, one simply has to create a new simulation class 
        that extends this abstract simulation class, defining the parameters that can be altered and the 
        parameters to display to the user for configuration.
    4. RaceCell, PercCell, WatorCell, FireCell, GameOfLifeCell all extend the abstract cell and will define their 
    own methods based on the rules of the simulation.
    5. SegregationSim, PercolationSim, WatorSim, GameOfLifeSim, and SpreadingFireSim all extend the 
    abstract simulation class and will define their own methods based on the characteristics of the simulation.
    6. Use Cases:
        * While we are iterating through all of the GameOfLifeCells in myGrid, we would invoke calculateNewState, 
        which uses getNeighbors to determine the correct subset of nieghbors for a middle cell. In 
        calculateNewState, we would update that GameOfLifeCell’s nextState parameter. We would then iterate 
        through the GameOfLifeCells in myGrid again, and set its currentState to its nextState. 
        * The only way this differs from the previous case is that getNeighbors will take a parameter 
        to specify what its potential neighbors are.
        * Described in Use Case 1.
        * When we create our FireCells, we will set the parameter to the probCatch value the XML file dictates in the constructor. 
        * The Wator simulation is chosen from the drop down menu in the GUI, and this causes a 
        new configuration to be set, which in turn sets up the new Wator simulation. This requires 
        the visualization to interact with the configuration. 

## Design Considerations

    1. One General Simulation vs Hierarchy of Simulations
        * On one hand, we began with the mindset that the Simulation of the game itself 
        should be independent of the rules of the specific simulation it was implementing. 
        This would provide the benefit of only having to create a new Cell for every 
        additional simulation we chose to implement. However, we ran into issues 
        when we realized that updating the parameters of rules specific to a simulation
        (such as % tolerance in Segregation) required Visualization to have access to
        Cells. In addition, it would have required Cells to be able to determine whether
        the simulation was complete or not. So, we decided to create a hierarchy of 
        simulations to take responsibility for these two jobs instead.
    2. Whether or not a Cell should know its neighbors
        * We began with the idea that cells shouldn’t know their neighbors, and that 
        simulation should be responsible for temporarily storing the states of the 
        neighbors and then providing that information to a corresponding Cell. We 
        realized that this didn’t make sense with our design because we determined 
        that each Cell should store its own state and decide its new state, which is
        inherently tied to its neighbors. 
    3. How To Update Each Cell’s Grid and Neighbors
        * The issue with our decision from the previous part, however, was that if a cell 
        were to update itself, it would replace its current information and either make it 
        impossible to know what its neighbors’ new state should be, or require overwriting 
        every Cell’s instance of its neighbors. We decided that Simulation should temporarily 
        store the information concerning the cell’s updated state for all cells, and then go 
        through each cell again to pass its new state.

## Team Responsibilities

    1. Visualization - This job will focus on managing the interface between user 
    input and simulation. In addition, it will work with the GUI to make sure the 
    updated grids are displaying properly and adjusting for new input parameters.
        * Primary - Anna Darwish
        * Secondary - Ryan Culhane
    2. Simulation - This job will focus on updating the simulation based on the 
    current state of the grid and enforcing the parameters that dictate the rules 
    of the simulation. It will also determine whether the simulation has reached 
    an equilibrium state, or should continue progressing.
        * Primary - Ryan Culhane
        * Secondary - Dima Fayyad
    3. Configuration - This job will focus on taking in information from the XML 
    file in order to instantiate the necessary materials for the simulation, along with their initial states.
        * Dima Fayyad
        * Anna Darwish




