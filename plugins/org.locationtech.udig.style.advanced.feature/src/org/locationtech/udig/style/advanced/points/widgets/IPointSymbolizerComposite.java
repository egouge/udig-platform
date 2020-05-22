/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2012, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 */
package org.locationtech.udig.style.advanced.points.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Composite;
import org.locationtech.udig.style.advanced.common.styleattributeclasses.PointSymbolizerWrapper;
import org.locationtech.udig.style.advanced.common.styleattributeclasses.RuleWrapper;
import org.locationtech.udig.style.advanced.points.PointPropertiesEditor;

/**
 * This class allows for the addition of alternative options
 * for the point symbolizer.  It adds pages to the drop down
 * where users can pick a mark, image font.
 * 
 * @author Emily
 *
 */
public interface IPointSymbolizerComposite {

	public static final String EXTENSION_ID = "org.locationtech.udig.style.advanced.points.symbolizer"; //$NON-NLS-1$

	/**
	 * The name of the point style
	 * @return
	 */
	public String getName();
	
	/**
	 * The core composite that displays the options
	 * @return
	 */
	public Composite getComposite();
	
	/**
	 * Creates the composite for ui options
	 * @param parent
	 * @param editor
	 * @param ruleWrapper
	 * @param numericAttributesArrays
	 */
	public void createComposite(Composite parent, PointPropertiesEditor editor, RuleWrapper ruleWrapper, String[] numericAttributesArrays);

	/**
	 * Updates the ui elements with the values from the wrapper
	 * @param ruleWrapper
	 */
    public void update( RuleWrapper ruleWrapper );
   	
    /**
    * return true if this option is valid for the given point symbolizer 
    * @param pointWrapper
    * @return
    */
    public boolean canStyle(PointSymbolizerWrapper pointWrapper);
    
    public static List<IPointSymbolizerComposite> getExtensions(){
	    if (Platform.getExtensionRegistry() == null) return Collections.emptyList();
		List<IPointSymbolizerComposite> items = new ArrayList<IPointSymbolizerComposite>();
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(IPointSymbolizerComposite.EXTENSION_ID);
		try {
			for (IConfigurationElement e : config) {
				items.add((IPointSymbolizerComposite)e.createExecutableExtension("class")); //$NON-NLS-1$
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return items;
    }
}
