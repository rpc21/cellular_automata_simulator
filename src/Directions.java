public enum Directions {
    NORTH(-1, 0),
    NORTH_EAST(-1, 1),
    EAST(0, 1),
    SOUTH_EAST(1, 1),
    SOUTH(1, 0),
    SOUTH_WEST(1, -1),
    WEST(0, -1),
    NORTH_WEST(-1, -1);

    private int myDeltaY;
    private int myDeltaX;

    Directions(int deltaY, int deltaX){
        myDeltaX = deltaX;
        myDeltaY = deltaY;
    }

    public int getMyDeltaX() {
        return myDeltaX;
    }

    public int getMyDeltaY() {
        return myDeltaY;
    }
}
