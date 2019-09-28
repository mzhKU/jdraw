/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

	private final List<DrawModelListener> listeners = new LinkedList<>();
	private final List<Figure>              figures = new LinkedList<>();

	@Override
	public void addFigure(Figure f) {
	    if(!figures.contains(f)) {
	    	figures.add(f);
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
	    figures.remove(f);
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		// TODO to be implemented  
		System.out.println("StdDrawModel.removeModelChangeListener has to be implemented");
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
	public void setFigureIndex(Figure f, int index) {
		// TODO to be implemented  
		System.out.println("StdDrawModel.setFigureIndex has to be implemented");
	}

	@Override
	public void removeAllFigures() {
		for (Figure f : figures) {
			figures.remove(f);
			for (DrawModelListener l : listeners) {
				l.modelChanged(new DrawModelEvent(this, f, DrawModelEvent.Type.DRAWING_CLEARED));
			}
		}
	}
}
