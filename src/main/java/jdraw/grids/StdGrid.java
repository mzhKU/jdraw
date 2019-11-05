package jdraw.grids;

import jdraw.framework.DrawGrid;

import java.awt.*;

public class StdGrid implements DrawGrid {
    @Override
    public Point constrainPoint(Point p) {
        return new Point(
                (int) p.getX() - (int) p.getX()%50,
                (int) p.getY() - (int) p.getY()%50
        );
    }

    @Override
    public int getStepX(boolean right) {
        return 1;
    }

    @Override
    public int getStepY(boolean down) {
        return 1;
    }

    @Override
    public void activate() {
        System.out.println("Standard grid activated.");
    }

    @Override
    public void deactivate() {
        System.out.println("Standard grid deactivated.");
    }

    @Override
    public void mouseDown() {

    }

    @Override
    public void mouseUp() {

    }
}
