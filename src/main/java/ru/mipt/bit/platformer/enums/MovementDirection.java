package ru.mipt.bit.platformer.enums;

public enum MovementDirection {
    UP(0, 1, 90f),
    DOWN(0, -1, -90f),
    LEFT(-1, 0, 180f),
    RIGHT(1, 0, 0f);

    private final int coordinateX;
    private final int coordinateY;
    private final float rotation;

    MovementDirection(int x, int y, float rotation) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.rotation = rotation;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public float getRotation() {
        return rotation;
    }
}
