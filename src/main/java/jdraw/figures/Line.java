/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents lines in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Line implements Figure {
	private static final long serialVersionUID = 9120181044386552132L;

	private List<FigureListener> figureListeners;

	/**
	 * Use the java.awt.Line in order to save/reuse code.
	 */
	private final Line2D.Double line;

	/**
	 * Create a new line of the given dimension.
	 * @param p1 the first endpoint
	 * @param p2 the second endpoint
	 */
	public Line(Point p1, Point p2) {
		line = new Line2D.Double(p1, p2);
		this.figureListeners = new CopyOnWriteArrayList<>();
	}

	/*
	protected void propagateFigureEvent(FigureEvent evt) {
		listeners.forEach(l -> l.figureChanged(evt));
	}
	*/

	/**
	 * Draw the line to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawLine((int)line.x1, (int)line.y1, (int)line.x2, (int)line.y2);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		line.setLine(origin, corner);
		for (FigureListener fl : figureListeners) {
			fl.figureChanged(new FigureEvent(this));
		}
	}

	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) {
			line.setLine((double)dx + line.x1, (double)dy + line.y1, line.x2, line.y2);
            for (FigureListener fl : figureListeners) {
                fl.figureChanged(new FigureEvent(this));
            }
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return line.contains(new Point(x, y));
	}

	@Override
	public Rectangle getBounds() {
		return line.getBounds();
	}

	/**
	 * Returns a list of 8 handles for this Rectangle.
	 * @return all handles that are attached to the targeted figure.
	 * @see Figure#getHandles()
	 */	
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
		figureListeners.remove(listener);
	}

	@Override
	public Figure clone() {
		return null;
	}
}
