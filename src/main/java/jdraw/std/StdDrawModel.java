/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.sql.SQLOutput;
import java.util.*;
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

	private final List<DrawModelListener> listeners = new CopyOnWriteArrayList<>(); // fixes concurrent modifications
	private final List<Figure>              figures = new CopyOnWriteArrayList<>();
	private final Map<Figure, FigureListener> flMap = new HashMap<>();

	// Version with figure listener pre-instantiated
	/*
	private FigureListener fl = e -> {
		for (DrawModelListener l : listeners) {
			l.modelChanged(new DrawModelEvent(this, e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED));
		}
	};
	 */

	@Override
	public void addFigure(Figure f) {
	    if(!figures.contains(f)) {
	    	figures.add(f);
			// Version with figure listener pre-instantiated
			// f.addFigureListener(fl)
	    	flMap.put(f, e -> {
				for (DrawModelListener l : listeners) {
					l.modelChanged(new DrawModelEvent(this, e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED));
				}
			});
			f.addFigureListener(flMap.get(f));
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

		// if(figures.remove(f)) {...}

		// This will search the list twice
		if(figures.contains(f)) {
			figures.remove(f);
			for(DrawModelListener l : listeners) {
				l.modelChanged(new DrawModelEvent(this, f, DrawModelEvent.Type.FIGURE_REMOVED));
			}
			FigureListener flToRemove = flMap.get(f);
			f.removeFigureListener(flToRemove);
			// Version with pre-instantiated figure listener:
			// f.removeFigureListener()
			flMap.remove(f);
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

		int pos = figures.indexOf(f);

		if (index < 0 || index >= figures.size()) {
			throw new IndexOutOfBoundsException();
		}

		if (pos < 0) {
			throw new IllegalArgumentException("Figure f not contained in model.");
		}

		System.out.println(figures);
		if(pos != index) {
			figures.remove(f);
			figures.add(index, f);
		}
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
