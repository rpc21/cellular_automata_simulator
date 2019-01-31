Inheritance Review - Visualization
===================

# Part 1

1. We are encapsulating the simulation updates from the visualization implementation.
   The representation of the Cells is mostly determined in visualization (such as the shape),
   but the Color is part of the Cell's state. Everything pertaining to layout in the 
   GUI is encapsulated from everything, as is handling input.
   
2. For my (Anna) will be an inheritance hierarchies of DefaultControls and SpecificControls
   for controls that are specific to a particular simulation. The GUI class (Irene) will be abstract
   and the formatting would be different entirely (default would have grid, start, 
   stop, etc.). (Harry) Grids of different shapes will also require another hierarchy for display
   
3. We all generally agree that changes strictly relating to aesthetics will
    be completely closed to the simulations (default controls, shape of cells, etc.)
    It should be open to adding a new simulation that requires different parameters
    
4. If there was an input parameter that did not make sense, such as inputting a negative
    number for the number of rows, it should notify the user that this is not possible.
    We are considering adding an Error Class and using the Alert object in JavaFX
    to notify the user of the issue.
    
5. It's closed to other changes in other parts of the project but still open to adding
    new simulations. The functions are all cohesive and focused on visualization.

# Part 2
1. In the beginning, configuration can be invoked to create a new simualtion. After, 
    visualization will be invoked to present this simulation to the user. Depending on user input
    from then on, visualization may invoke configuration to create a new simulation, update the 
    current one, or present the current simulation in a different manner.

2. As long as simulation presents the information in a consistent manner, and configuration is invoked
    in a consistent manner, visualization will be generally closed to these two areas
    of the project.

3. We can minimize these dependencies by ensuring that each area of the project can be invoked
   in a consistent way and does not require much additional information between the different instances
   that they may be called upon to act.


# Part 3
1. Potential Use Cases
    * User inputs new parameters to WaTor World: How do we notify simulation?
    * User switches the current simulation: How do we invoke configuration?
    * User sets a parameter that requires restarting the simulation (changing number of rows)
    * User sets a parameter that requires changing the current display, but not whole
    simulation (changing proportion of races in Segregation)
2. I am most excited to work on developing the simulation-specific control panels
3. I am most worried about invoking configuration
