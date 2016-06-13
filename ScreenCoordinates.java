package com.minobilis.hexagrid;

public class ScreenCoordinates extends Coordinates {
    private int x;
    private int y;

    public ScreenCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ScreenCoordinates() {
        this.x = 0;
        this.y = 0;
    }

    public ScreenCoordinates(ScreenCoordinates c) {
        this.x = c.getX();
        this.y = c.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void positiveOffset(ScreenCoordinates c){
        this.x += c.getX();
        this.y += c.getY();
    }

    public void negativeOffset(ScreenCoordinates c){
        this.x -= c.getX();
        this.y -= c.getY();
    }

    @Override
    public int hashCode() {
        return (x * 31) ^ y;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScreenCoordinates) {
            ScreenCoordinates other = (ScreenCoordinates) obj;
            return (x == other.x && y == other.y);
        }
        return false;
    }
}
