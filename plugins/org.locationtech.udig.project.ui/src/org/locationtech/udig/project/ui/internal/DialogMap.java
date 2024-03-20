/**
 * 
 */
package org.locationtech.udig.project.ui.internal;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.locationtech.udig.project.ui.ApplicationGIS;

/**
 * Abstract class for a dialog with a map that requires use of tools.
 * The Dialog should call init() to configure dialog correctly.
 * @author Emily
 *
 */
public interface DialogMap {

	/**
	 * 
	 * @return dialog map part
	 */
	public MapPart getMapPart();
	
	/**
	 * 
	 * @return list of dispose listeners
	 */
	public List<Listener> getDisposeListeners();
	
	/**
	 * Runs dispose listeners. Implementors should call this when the map part is disposed.
	 * 
	 * @param event
	 */
	public default void disposeMap(Event event) {
		for (Listener l : getDisposeListeners()) {
			l.handleEvent(event);
		}
	}
	
	/**
	 * Add dispose listener
	 * @param listener
	 */
	public default void addDisposeListener(Listener listener) {
		this.getDisposeListeners().add(listener);
	}
	
	public default void init(Control parent) {
		ApplicationGIS.mapDialogOpen(this);
		parent.addListener(SWT.Dispose, e->disposeMap(e));
	}
}
