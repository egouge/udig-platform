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
import java.io.IOException;
import java.net.URL;

import org.locationtech.udig.catalog.IGeoResource;
import org.locationtech.udig.project.StyleContent;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IMemento;

public class LegendStyleContent extends StyleContent {

    private static final String BACKGROUND_COLOUR = "backgroundColour"; //$NON-NLS-1$
	private static final String IMAGE_WIDTH = "imageWidth"; //$NON-NLS-1$
	private static final String IMAGE_HEIGHT = "imageHeight"; //$NON-NLS-1$
	private static final String INDENT_SIZE = "indentSize"; //$NON-NLS-1$
	private static final String HORIZONTAL_SPACING = "horizontalSpacing"; //$NON-NLS-1$
	private static final String VERTICAL_SPACING = "verticalSpacing"; //$NON-NLS-1$
	private static final String HORIZONTAL_MARGIN = "horizontalMargin"; //$NON-NLS-1$
	private static final String VERTICAL_MARGIN = "verticalMargin"; //$NON-NLS-1$
	private static final String NUM_COLUMNS= "numberColumns"; //$NON-NLS-1$
	private static final String DRAW_BORDER = "drawBorder"; //$NON-NLS-1$

    
	public static final String ID = "org.locationtech.udig.legend.legendStyle"; //$NON-NLS-1$
    
    public LegendStyleContent() {
        super(ID);
    }
    
    @Override
    public Class<?> getStyleClass() {
        return LegendStyle.class;
    }

    @Override
    public void save( IMemento momento, Object value ) {
    	if (value instanceof LegendStyle) {
    		LegendStyle ls = (LegendStyle)value;
    		momento.putInteger(VERTICAL_MARGIN, ls.verticalMargin);
    		momento.putInteger(HORIZONTAL_MARGIN, ls.horizontalMargin);
    		momento.putInteger(VERTICAL_SPACING, ls.verticalSpacing);
    		momento.putInteger(HORIZONTAL_SPACING, ls.horizontalSpacing);
    		momento.putInteger(NUM_COLUMNS, ls.numCols);
    		momento.putInteger(INDENT_SIZE, ls.indentSize);
    		momento.putInteger(IMAGE_HEIGHT, ls.imageHeight);
    		momento.putInteger(IMAGE_WIDTH, ls.imageWidth);
    		momento.putBoolean(DRAW_BORDER, ls.drawBorder);
    		
    		momento.putInteger(BACKGROUND_COLOUR, ls.backgroundColour.getRGB());
    	}
    }

    @Override
    public Object load( IMemento momento ) {
    	 LegendStyle style = createDefault();
    	 
    	 Integer v = momento.getInteger(VERTICAL_MARGIN);
    	 if (v != null) style.verticalMargin = v.intValue();
    	 
    	 v = momento.getInteger(HORIZONTAL_MARGIN);
    	 if (v != null) style.horizontalMargin = v.intValue();
    	 
    	 v = momento.getInteger(VERTICAL_SPACING);
    	 if (v != null) style.verticalSpacing = v.intValue();
    	 
    	 v = momento.getInteger(HORIZONTAL_SPACING);
    	 if (v != null) style.horizontalSpacing = v.intValue();
    	 
    	 v = momento.getInteger(INDENT_SIZE);
    	 if (v != null) style.indentSize = v.intValue();
    	 
    	 v = momento.getInteger(IMAGE_HEIGHT);
    	 if (v != null) style.imageHeight = v.intValue();
    	 
    	 v = momento.getInteger(IMAGE_WIDTH);
    	 if (v != null) style.imageWidth = v.intValue();
   
    	 v = momento.getInteger(NUM_COLUMNS);
    	 if (v != null) style.numCols = v.intValue();
    	 
    	 v = momento.getInteger(BACKGROUND_COLOUR);
    	 if (v != null) style.backgroundColour = new Color(v);
    	 
    	 Boolean b = momento.getBoolean(DRAW_BORDER);
    	 if (b != null) style.drawBorder = b;
    	 
        return style;
    }

    @Override
    public Object load( URL url, IProgressMonitor monitor ) throws IOException {
        return null;
    }

    @Override
    public Object createDefaultStyle( IGeoResource resource, Color colour, IProgressMonitor monitor ) throws IOException {
        if( !resource.canResolve(LegendGraphic.class) )
            return null;
        return createDefault();
    }
    
    /**
     * Creates the default legend style
     * @return
     */
    public static LegendStyle createDefault() {
        LegendStyle style = new LegendStyle();
        
        style.numCols = 1;
        style.verticalMargin = 5; 
        style.horizontalMargin = 5; 
        
        style.verticalSpacing = 3; 
        style.horizontalSpacing = 3;
        
        style.indentSize = 10;
        
        style.imageHeight = 16;
        style.imageWidth = 16;
        
        style.imageSpacing = 2;
        
        style.backgroundColour = Color.WHITE;
        style.drawBorder = true;
        return style;
    }
}
