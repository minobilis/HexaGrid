package com.minobilis.hexagrid;

public class GridCoordinates extends Coordinates {
    private int q;
    private int r;

    public GridCoordinates(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public GridCoordinates() {
        this.q = 0;
        this.r = 0;
    }

    public GridCoordinates(GridCoordinates c) {
        this.q = c.getQ();
        this.r = c.getR();
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    @Override
    public int hashCode() {
        return (q * 31) ^ r;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GridCoordinates) {
            GridCoordinates other = (GridCoordinates) obj;
            return (q == other.q && r == other.r);
        }
        return false;
    }
}
