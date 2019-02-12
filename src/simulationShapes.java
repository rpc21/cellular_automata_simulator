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
