package jdraw.figures;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.event.MouseEvent;

public class NorthWestHandle implements FigureHandle {

    private Figure figure;
    private Point corner;

    public NorthWestHandle(Figure owner) {
        this.figure = owner;
    }

    @Override
    public Figure getOwner() {
        return this.figure;
    }

    @Override
    public Point getLocation() {
        return this.figure.getBounds().getLocation();
    }

    @Override
    public void draw(Graphics g) {
        Point loc = getLocation();
        // TODO: Constants for the size of then handle
        g.setColor(Color.GREEN); g.fillRect(loc.x-3, loc.y-3, 6, 6);
        g.setColor(Color.BLUE);  g.drawRect(loc.x-3, loc.y-3, 6, 6);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
    }

    @Override
    public boolean contains(int x, int y) {
        // TODO: Constants for the size of then handle
        boolean withinX = getLocation().x-3<x && getLocation().x+3>x;
        boolean withinY = getLocation().y-3<y && getLocation().y+3>y;
        return withinX && withinY;
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = getOwner().getBounds();
        corner  = new Point(r.x + r.width, r.y + r.height);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        getOwner().setBounds(new Point(x, y), corner);
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        corner = null;
    }
}
