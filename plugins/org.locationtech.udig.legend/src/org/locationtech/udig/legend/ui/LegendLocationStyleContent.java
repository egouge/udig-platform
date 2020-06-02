/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2004, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 *
 */
package org.locationtech.udig.legend.ui;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IMemento;
import org.locationtech.udig.catalog.IGeoResource;
import org.locationtech.udig.mapgraphic.MapGraphic;
import org.locationtech.udig.project.StyleContent;

/**
 * Legend location style which 
 * has an x,y position options of left, center, right, top bottom
 * but x and y padding offsets.  
 * @author Emily
 */
public class LegendLocationStyleContent extends StyleContent {
	
	/** extension id */
	public static final String ID = "org.locationtech.udig.legend.locationStyle"; //$NON-NLS-1$
    
	//momento keys
    private static final String Y = "y"; //$NON-NLS-1$
    private static final String X = "x";  //$NON-NLS-1$
    private static final String PAD_X = "xpadding"; //$NON-NLS-1$
    private static final String PAD_Y = "ypadding"; //$NON-NLS-1$
    
    
    /**
     * Legend location style.
     */
    public LegendLocationStyleContent(){
        super( ID );
    }
    
    public Class<?> getStyleClass() {
        return Point.class;
    }
    
    public Object load( IMemento memento ) {
        int x = memento.getInteger(X);
        int y = memento.getInteger(Y);
        int xp = memento.getInteger(PAD_X);
        int yp = memento.getInteger(PAD_Y);
        
        LegendLocationStyle style = LegendLocationStyleContent.createDefaultStyle();
        style.xpadding = xp;
        style.ypadding = yp;
        style.xposition = x;
        style.yposition = y;
        
        return style;
    }

    public void save( IMemento memento, Object item ) {
    	LegendLocationStyle style = (LegendLocationStyle) item;
    	
        memento.putInteger(X, style.xposition);
        memento.putInteger(Y, style.yposition);
        memento.putInteger(PAD_X, style.xpadding);
        memento.putInteger(PAD_Y, style.ypadding);
    }
    
    public Object createDefaultStyle(IGeoResource resource, Color colour,  IProgressMonitor monitor) throws IOException {
        if( !resource.canResolve(MapGraphic.class))
            return null;

        if( resource.canResolve(LegendLocationStyle.class) ){
            // lets assume this is the best location for this resource
        	LegendLocationStyle style = resource.resolve(LegendLocationStyle.class, monitor);
            if( style !=null ){
                return style;
            }
        }
        
        return createDefaultStyle();
	}
	
    public Object load( URL url, IProgressMonitor monitor) throws IOException {
        return null;
    }

    public static LegendLocationStyle createDefaultStyle() {
    	LegendLocationStyle style = new LegendLocationStyle();
    	style.xpadding = 5;
    	style.ypadding = 5;
    	style.xposition = LegendLocationStyle.FixedPosition.RIGHT.value;
    	style.yposition = LegendLocationStyle.FixedPosition.BOTTOM.value;
        return style;
    }
    
}
