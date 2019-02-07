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
    HEXAGON(new int[] {-1, -1, 0, 0, 1, 1}, new int[] {-1, 0, -1, 1, -1, 0});

    private final int[] deltaRow;
    private final int[] deltaCol;

    NeighborsDefinitions(int[] deltaRow, int[] deltaCol) {
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    public int[] getDeltaCol() {
        return deltaCol;
    }

    public int[] getDeltaRow() {
        return deltaRow;
    }

}
