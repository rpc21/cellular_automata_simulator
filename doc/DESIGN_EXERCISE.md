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

#### Choose Weapon

#### Win the round

#### New Choice

#### Game Added

