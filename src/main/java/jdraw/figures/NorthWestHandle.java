package jdraw.figures;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.event.MouseEvent;

public class NorthWestHandle implements FigureHandle {

    private Figure figure;
    private Point corner;

    private final int HANDLE_SIZE = 6;
    private final int HANDLE_x    = 3;
    private final int HANDLE_y    = 3;

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
        g.setColor(Color.GREEN); g.fillRect(loc.x-HANDLE_x, loc.y-HANDLE_y, HANDLE_SIZE, HANDLE_SIZE);
        g.setColor(Color.BLUE);  g.drawRect(loc.x-HANDLE_x, loc.y-HANDLE_y, HANDLE_SIZE, HANDLE_SIZE);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
    }

    @Override
    public boolean contains(int x, int y) {
        boolean withinX = getLocation().x-HANDLE_x<x && getLocation().x+HANDLE_x>x;
        boolean withinY = getLocation().y-HANDLE_y<y && getLocation().y+HANDLE_y>y;
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
