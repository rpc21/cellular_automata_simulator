Inheritance Review
rt125 Rishi Tripathy
dkf8 Dima Fayyad

### Dima's answers

#### Part 1

###### 1
Different Cell types hidden from other parts of code 

##### 2
Hierarchies: abstract cell -> percolation cell, game of life cell, movable cell (also abstract), segregation cell, based around behaviors such as moving

##### 3
Closed: make many details of situation closed to the visualization-- viz shouldn't have to know any of that in order to display

Open: Events that occur in viz must be open to simulation in order to pause etc

##### 4
Possible errors: XML parser, taking basic XML exceptions presented in class, not much throwing/handling yet,

need to look into checking for correct type of simulation

##### 5

Disentaglement of simulation and visualization

Adding another simulation only requires addition of new code (not alteration of existing codebase)

measure as compared to game, extensibility

##### Part 2

#### 1

XML parsing heavily linked to simulation and cell initialization, needs to set up the grid and all cells in the grid, constructor needs to take in those things

#### 2

Right now, fact that an XML parser returns a full simulation is implementation choice


#### 3

Trying to minimize depenencies, would want to pass a set of params back


##### Part 3

### Rishi's answers

#### Part 1

###### 1

Grid is updating states of the different cells, making it closed to visualization, simulation has no idea how update is achieved (left to the grid).

###### 2

Using an interface to have separate enums for each cell type.
The ruleset executes the logic for a certain simulation, the grid passes relevant cells to the ruleset.

###### 3

Closed: The cell does not need to know who it's neighbors are. The cell is more of a value holder.
Open: Communication between the three major parts is handled with controllers.

###### 4

Possible errors: XML parser will handle errors, exceptions to make sure expected type is what is received.
Most useful for the XML parser to return a new simulation in their design. 

###### 5

Responsibilities designated to each class will make the code extendable and clear.

##### Part 2





