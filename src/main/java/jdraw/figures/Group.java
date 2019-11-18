package jdraw.figures;

import jdraw.framework.*;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Group implements Figure, FigureGroup {

    private List<FigureListener> figureListeners;

    private List<Figure> parts;

    public Group(List<Figure> selectedFigures) {
        this.parts = new LinkedList<>();
        for (Figure f : selectedFigures) {
            this.parts.add(f);
        }
        this.figureListeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void draw(Graphics g) {
        for (Figure p : parts) {
            p.draw(g);
        }
    }

    @Override
    public void move(int dx, int dy) {
        for (Figure p : parts) {
            p.move(dx, dy);
        }
        for (FigureListener fl : figureListeners) {
            fl.figureChanged(new FigureEvent(this));
        }
    }

    @Override
    public boolean contains(int x, int y) {
        for (Figure f : parts) {

            if (f.contains(x, y)) {
                // If any of the figures contains the click location.
                return true;
            }
        }
        return false;
    }

    @Override
    public void setBounds(Point origin, Point corner) {

    }

    @Override
    public Rectangle getBounds() {
        // Can I group zero figures?
        Rectangle tmpRect = parts.get(0).getBounds();
        for (Figure p : parts) {
            tmpRect.add(p.getBounds());
        }
        return tmpRect;
    }

    @Override
    public List<FigureHandle> getHandles() {
        List<FigureHandle> handles = new LinkedList<>();
        handles.add(new NorthWestHandle(this));
        return handles;
    }

    @Override
    public void addFigureListener(FigureListener listener) {
        this.figureListeners.add(listener);
    }

    @Override
    public void removeFigureListener(FigureListener listener) {
        this.figureListeners.remove(listener);
    }

    @Override
    public Figure clone() {
        return null;
    }

    @Override
    public Iterable<Figure> getFigureParts() {
        return parts;
    }
}
