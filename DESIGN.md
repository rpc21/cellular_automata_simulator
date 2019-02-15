Cell Society - Team 12
===================

# Design Goals
**1. Flexibility**

   * Allow for new simulations, new interfaces for the user to interact with, and new 
   displays of information to be easily implemented. In addition to functional flexibility, 
   allow for graphical flexibility, such as changing the locations and ordering of GUI
   options.
   
**2. Limit Dependency**

   * Refrain from using excessive getter/setter methods, and instead hold relevant
   objects to be responsible for updating their current state in response to events such
   as the user resetting the simulation.
   
# Adding New Features
**1. Add New Simulation**

   * For front-end/config, add necessary .xml files, add new simulation option to simulation 
   factory, add new subclass of simulation-specific panels, potentially extending GUIGridCell
   to handle display if it is more complicated than the current options of having a
   shape and an "agent" in a cell. From a back-end perspective, cell subclasses for the 
   different states involved in the simulation, and a simulation subclass to help handle
   updating the grid according to the rules
   
**2. Add New Control**

   * Create a subclass of GUIControlManager to access different default control layouts before
   adding an instance of that button to whatever panel or window it belongs to
   

# Major Design Choices
**1. Simulation and Cell Hierarchy**

   * Pro: It's relatively easy to add a new level, and any page that only displays text,
   such as a splash page or an "evaluation" page (lose vs win) is quite quick to instantiate.
   * Con: There is a strict format for the files that specify the state and layout of GamePieces, as
   files are read in a particular manner dictated by (for example) a LevelPage. Though this has not 
   caused any immediate issues, it may present future ones for unique configurations.
   
   
**2. GUIPolygon Hierarchy**

   * Pro: GUIGridCell can have an instance of a GUIPolygon for display and then add onto the polygon
   to give cells flexible design options
   * Con: Because of its separation from the design options window for each grid, it may become
   cumbersome to transfer information about display (color, border, whether or not there is an agent)
   if it becomes significantly more complicated. It does little to help with this issue
   
**3. GUIDefaultPanel's Fields of Buttons**

   * Pro: Creating a class for each button encapsulated details of that button from the default panel
   and made the code much easier to manage and extend
   * Con: Being removed one step further from certain needed methods, such as step functions,
   required developing functional interfaces in order for those controls to access the methods 
   they needed to when activated

# Assumptions & Decisions

   * Users can only switch between having one or two simulations and they are both the same type
   * Users must click reset after changing parameters instead of those changes happening 
   in immediate response to being changed.
   * The graph only displays the first 50 iterations of the grid updates.
