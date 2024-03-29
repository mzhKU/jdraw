/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.*;
import jdraw.std.StdDrawModel;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Rect implements Figure {
	private static final long serialVersionUID = 9120181044386552132L;

	private List<FigureListener> figureListeners;

	/**
	 * Use the java.awt.Rectangle in order to save/reuse code.
	 */
	private final Rectangle rectangle;

	/**
	 * Create a new rectangle of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Rect(int x, int y, int w, int h) {
		rectangle = new Rectangle(x, y, w, h);
		this.figureListeners = new CopyOnWriteArrayList<>();
	}


	/*
	protected void propagateFigureEvent(FigureEvent evt) {
		listeners.forEach(l -> l.figureChanged(evt));
	}
	*/


	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.MAGENTA);
		((Graphics2D) g).setStroke(new BasicStroke(1));
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	@Override
	public void setBounds(Point origin, Point corner) {
		rectangle.setFrameFromDiagonal(origin, corner);
		for (FigureListener fl : figureListeners) {
			fl.figureChanged(new FigureEvent(this));
		}
	}

	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) {
			rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
            for (FigureListener fl : figureListeners) {
                fl.figureChanged(new FigureEvent(this));
            }
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return rectangle.getBounds();
	}

	/**
	 * Returns a list of 8 handles for this Rectangle.
	 * @return all handles that are attached to the targeted figure.
	 * @see jdraw.framework.Figure#getHandles()
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
