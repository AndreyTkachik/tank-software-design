package ru.mipt.bit.platformer.enums;

public enum MovementDirection {
    FORWARD(0, 1),
    BACKWARD(0 ,-1),
    TO_THE_LEFT(-1, 0),
    TO_THE_RIGHT(1, 0);

    private final int coordinateX;
    private final int coordinateY;

    MovementDirection(int x, int y) {
        this.coordinateX = x;
        this.coordinateY = y;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

}
