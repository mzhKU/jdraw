/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import jdraw.framework.*;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 * @author Martin Hediger
 *
 */
public class StdDrawModel implements DrawModel {

	private final List<DrawModelListener> listeners = new CopyOnWriteArrayList<>();
	private final List<Figure>              figures = new CopyOnWriteArrayList<>();

	@Override
	public void addFigure(Figure f) {
	    if(!figures.contains(f)) {
	    	figures.add(f);
	    	f.addFigureListener(e -> {
				for (DrawModelListener l : listeners) {
					l.modelChanged(new DrawModelEvent(this, e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED));
				}
			});
			for (DrawModelListener l : listeners) {
				l.modelChanged(new DrawModelEvent(this, f, DrawModelEvent.Type.FIGURE_ADDED));
			}
		}
	}

	@Override
	public Stream<Figure> getFigures() {
		return figures.stream();
	}

	@Override
	public void removeFigure(Figure f) {
		if(figures.contains(f)) {
			figures.remove(f);
			f.removeFigureListener(e -> {
				for (DrawModelListener l : listeners) {
					l.modelChanged(new DrawModelEvent(this, e.getFigure(), DrawModelEvent.Type.FIGURE_REMOVED));
				}
			});
		}
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		listeners.remove(listener);
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * @return the draw command handler.
	 */
	@Override
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) throws IllegalArgumentException, IndexOutOfBoundsException {
		System.out.println(figures);

		List<Figure> tmp = figures;

		tmp.set(index, f);
		for(int i = index +1; i<figures.size(); i++) {
			tmp.set(i, figures.get(i));
		}

		for(int i = 0; i<figures.size(); i++) {
			figures.set(i, tmp.get(i));
		}

		// figures.set(index, f);

		System.out.println(figures);
 		for (DrawModelListener l : listeners) {
 			l.modelChanged(new DrawModelEvent(this, f, DrawModelEvent.Type.DRAWING_CHANGED));
		}
	}

	@Override
	public void removeAllFigures() {
		for (Figure f : figures) {
			figures.remove(f);
			f.removeFigureListener(e -> {
				for (DrawModelListener l : listeners) {
					l.modelChanged(new DrawModelEvent(this, e.getFigure(), DrawModelEvent.Type.DRAWING_CLEARED));
				}
			});
			for (DrawModelListener l : listeners) {
				l.modelChanged(new DrawModelEvent(this, f, DrawModelEvent.Type.DRAWING_CLEARED));
			}
		}
	}
}
