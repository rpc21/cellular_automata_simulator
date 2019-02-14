import java.util.List;

/**
 * Enum defining all the possible neighbors as two parallel arrays of row and column offsets.  This enum also defines
 * the directions in the same way
 */
public enum NeighborsDefinitions {
    ADJACENT(new int[] {0,0,-1,1}, new int[] {-1,1,0,0}),
    BOX_NEIGHBORS(new int[] {0, 0, -1, -1, -1, 1, 1, 1}, new int[] {-1, 1, -1, 0, 1, -1, 0, 1}),
    DIAGONAL(new int[] {1, 1, -1, -1}, new int[] {1, -1, 1, -1}),
    KNIGHT(new int[] {1, 1, 2, 2, -1, -1, -2, -2}, new int[] {2, -2, 1, -1, 2, -2, 1, -1}),
    I_FORMATION(new int[] {-1, -1, -1, 1, 1, 1}, new int[] {-1, 0, 1, -1, 0, 1}),
    TRIANGLE_12_POINT_UP(new int[] {-1, -1, -1, 0, 0, 0, 0, 1, 1, 1, 1, 1}, new int[] {-1, 0, 1, -2, -1, 1, 2, -2, -1
            , 0, 1, 2}),
    TRIANGLE_12_POINT_DOWN(new int[] {-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1}, new int[] {-2, -1,0, 1, 2, -2, -1, 1,
            2, -1, 0, 1}),
    HEXAGON(new int[] {-1, -1, 0, 0, 1, 1}, new int[] {-1, 0, -1, 1, -1, 0}),
    FLIPPED_HEXAGON(new int[] {-1, -1, 0, 0, 1, 1}, new int[] {0, 1, -1, 1, 0, 1}),
    NORTH(new int[] {-1}, new int[] {0}),
    NORTH_EAST(new int[] {-1}, new int[] {1}),
    EAST(new int[] {0}, new int[] {1}),
    SOUTH_EAST(new int[] {1}, new int[] {1}),
    SOUTH(new int[] {1}, new int[] {0}),
    SOUTH_WEST(new int[] {1}, new int[] {-1}),
    WEST(new int[] {0}, new int[] {-1}),
    NORTH_WEST(new int[] {-1}, new int[] {-1});

    public static final NeighborsDefinitions[] CARDINAL_DIRECTIONS_COMPLETE = {NORTH, NORTH_EAST, EAST, SOUTH_EAST,
            SOUTH, SOUTH_WEST, WEST, NORTH_WEST};

    public static final List<Object> allPossibleNeighbors = List.of(ADJACENT,BOX_NEIGHBORS,DIAGONAL,KNIGHT,
            I_FORMATION,TRIANGLE_12_POINT_UP,HEXAGON);
    private final int[] deltaRow;
    private final int[] deltaCol;

    NeighborsDefinitions(int[] deltaRow, int[] deltaCol) {
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    /**
     * @return array of the column offsets associated with the neighbor definition
     */
    public int[] getDeltaCol() {
        return deltaCol;
    }

    /**
     * @return array of the row offsets associated with the neighbor definition
     */
    public int[] getDeltaRow() {
        return deltaRow;
    }

}
