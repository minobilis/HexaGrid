package com.minobilis.hexagrid;

import java.util.HashSet;

public class HexagonalGrid {
    public enum Orientation {POINTY_TOPPED, FLAT_TOPPED}
    public enum PredefinedFieldShape {PARALLELOGRAM_UP, PARALLELOGRAM_DOWN, TRIANGLE_UP, TRIANGLE_DOWN, HEXAGON}

    private float hexagonSize; //size = hexagon edge length
    private Orientation orientation;

    public HexagonalGrid(float hexagonSize) {
        this.hexagonSize = hexagonSize;
        this.orientation = Orientation.FLAT_TOPPED;
    }

    public float getHexagonSize() {
        return hexagonSize;
    }

    public void setHexagonSize(float hexagonSize) {
        this.hexagonSize = hexagonSize;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public float getHexagonWidth() {
        switch (this.getOrientation()) {
            case FLAT_TOPPED:
                return this.hexagonSize * 2;
            case POINTY_TOPPED:
                return this.hexagonSize * (float) Math.sqrt(3);
        }
        return -1;
    }

    public float getHexagonHeight() {
        switch (this.getOrientation()) {
            case FLAT_TOPPED:
                return this.hexagonSize * (float) Math.sqrt(3);
            case POINTY_TOPPED:
                return this.hexagonSize * 2;
        }
        return -1;
    }

    public ScreenCoordinates gridToScreenCoordinates(int col, int row) {
        int x = 0;
        int y = 0;
        float size = hexagonSize;

        switch (this.getOrientation()) {
            case FLAT_TOPPED:
                x = Math.round(size * 1.5f * col);
                y = Math.round(size * (float) Math.sqrt(3) * (row + (float) col / 2));
                break;

            case POINTY_TOPPED:
                x = Math.round(size * (float) Math.sqrt(3) * (col + (float) row / 2));
                y = Math.round(size * 1.5f * row);
                break;
        }
        return new ScreenCoordinates(x, y);
    }

    public ScreenCoordinates gridToScreenCoordinates(int col, int row, ScreenCoordinates offset) {
        ScreenCoordinates screenCoordinates = gridToScreenCoordinates(col, row);
        screenCoordinates.positiveOffset(offset);
        return screenCoordinates;
    }

    public GridCoordinates screenToGridCoordinates(int intx, int inty){
        double size = hexagonSize;

        double q = (double) intx * 2/3 / size;
        double r = (-(double) intx / 3 + Math.sqrt(3)/3 * (double) inty) / size;

        double rx = Math.round(q);
        double ry = Math.round(-q-r);
        double rz = Math.round(r);

        double dx = Math.abs((double) intx - rx);
        double dy = Math.abs((double) inty - ry);
        double dz = Math.abs(-(double) intx - (double) inty - rz);

        if (dx > dy && dx > dz) rx = -ry-rz;
        else rz = -rx-ry;

        return new GridCoordinates((int)Math.round(rx),(int)Math.round(rz));
    }

    public static HashSet<GridCoordinates> getHexagonSet(int gridSize, PredefinedFieldShape shape){

        HashSet<GridCoordinates> set = new HashSet<>();

        switch (shape){
            case PARALLELOGRAM_UP:
                for (int i = 0; i < gridSize; i++)
                    for (int j = 0; j < gridSize; j++)
                        set.add(new GridCoordinates(i, j));
                break;
            case HEXAGON:
                for (int i = 0; i > -gridSize; i--) {
                    for (int j = 0; j < gridSize; j++) {
                        set.add(new GridCoordinates(i, j));
                        set.add(new GridCoordinates(j, i));
                    }
                }
                int max = gridSize;
                for (int i = 0; i < gridSize; i++) {
                    for (int j = 0; j < max; j++) {
                        set.add(new GridCoordinates(i, j));
                        set.add(new GridCoordinates(-i, -j));
                    }
                    max--;
                }
                break;
        }

        return set;
    }

    public static boolean isNeighbor (GridCoordinates gridCoordinatesOne, GridCoordinates gridCoordinatesTwo){

        if (gridCoordinatesOne == null || gridCoordinatesTwo == null ) return false;

        if (gridCoordinatesOne.getQ() - gridCoordinatesTwo.getQ() == 0 &&
                gridCoordinatesOne.getR() - gridCoordinatesTwo.getR() == -1) return true;
        if (gridCoordinatesOne.getQ() - gridCoordinatesTwo.getQ() == 1 &&
                gridCoordinatesOne.getR() - gridCoordinatesTwo.getR() == -1) return true;
        if (gridCoordinatesOne.getQ() - gridCoordinatesTwo.getQ() == 1 &&
                gridCoordinatesOne.getR() - gridCoordinatesTwo.getR() == 0) return true;
        if (gridCoordinatesOne.getQ() - gridCoordinatesTwo.getQ() == 0 &&
                gridCoordinatesOne.getR() - gridCoordinatesTwo.getR() == 1) return true;
        if (gridCoordinatesOne.getQ() - gridCoordinatesTwo.getQ() == -1 &&
                gridCoordinatesOne.getR() - gridCoordinatesTwo.getR() == 1) return true;
        if (gridCoordinatesOne.getQ() - gridCoordinatesTwo.getQ() == -1 &&
                gridCoordinatesOne.getR() - gridCoordinatesTwo.getR() == 0) return true;
        return false;
    }


    public static HashSet<GridCoordinates> getRingHexagonSet(int radius){ //radius start from 0 at center
        HashSet<GridCoordinates> set = new HashSet<>();

        if (radius == 0) {
            set.add(new GridCoordinates(0, 0));
            return set;
        }

        for (int i = 0; i < radius; i++) {
            set.add(new GridCoordinates(i - radius, radius));
            set.add(new GridCoordinates(i, radius - i));
            set.add(new GridCoordinates(radius, - i));
            set.add(new GridCoordinates(radius - i, -radius));
            set.add(new GridCoordinates(- i, i - radius));
            set.add(new GridCoordinates(- radius, i));
        }

        return set;
    }
}
