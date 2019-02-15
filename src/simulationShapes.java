/**
 * Defines the valid types of shapes that can be used for cells in a simulation
 *
 * @author Dima Fayayd
 */
public enum simulationShapes {
    square("square"),
    rectangle("rectangle"),
    triangle("triangle"),
    hexagon("hexagon");

    private final String myName;

    simulationShapes(String name) {
        myName = name;
    }
}
