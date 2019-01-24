# RPS Exercise

Completed by Anna Darwish, Dima Fayyad, Ryan Culhane

## Design Description

#### Class1 - Weapons Superclass (abstract class)
```java
    //animates the weapon
    public void updatePosition(double elapsedTime);

```

#### Player Superclass (abstract class)
```java
    //updates the instance variable myWeapon representing weapon id of the weapon
    public void chooseWeapon(int numOfPossibleWeapons);

    // adds 1 to the player's number of wins
    public void addWin();


    //returns the id of the weapon that the player is using
    public int getWeaponId();
    
    public Player(String name);

    // Returns cool version of the given description
    String coolStuff (String description);
```

#### RealPlayer extends Player
```java
    //updates the instance variable myWeapon representing weapon id of the weapon
    public void chooseWeapon(int numOfPossibleWeapons);

    // adds 1 to the player's number of wins
    public void addWin();


    //returns the id of the weapon that the player is using
    public int getWeaponId();
    
    //constructor for Player class
    public Player(String name);

```

#### AutoPlayer extends Player
```java
    //updates the instance variable myWeapon representing weapon id of the weapon
    public void chooseWeapon(int numOfPossibleWeapons);

    // adds 1 to the player's number of wins
    public void addWin();


    //returns the id of the weapon that the player is using
    public int getWeaponId();
    
    public Player(String name);

```

#### Game Class
```java
    //asks user for name and instantiates player with their name
    private void promptUserName();

    //asks user for number of games they want to play
    private void promptUserGames();
    
    //returns in that that indicates which player won
    private int battle(int playerOneWeaponId, int playerTwoWeaponId);
    
    //updates the players based on whether they won or lost
    private void handleWinOrLose(int winner);
    
```

###Cell Society Discussion
1. A cell knows what rules to apply for the simulation based on its current state, its neighbors' current state and 
the rules of the particular simulation. We would have an abstract cell class 
2. A cell only knows about its neighbors when rules is called.  Rules informs the cell about its next state based on 
the current state and the state of its neighbors.
3. Each simulation has a private instance variable that is the Grid for that simulation.  The simulation and the 
visualizer need to be able to access the grid.

