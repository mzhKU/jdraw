package jdraw.grids;

import jdraw.framework.DrawGrid;

import java.awt.*;

public class StdGrid implements DrawGrid {
    @Override
    public Point constrainPoint(Point p) {
        return null;
    }

    @Override
    public int getStepX(boolean right) {
        return 0;
    }

    @Override
    public int getStepY(boolean down) {
        return 0;
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void mouseDown() {

    }

    @Override
    public void mouseUp() {

    }
}
