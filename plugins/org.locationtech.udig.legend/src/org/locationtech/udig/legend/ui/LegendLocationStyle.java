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

public class LegendLocationStyle {
    
	public enum FixedPosition{
		TOP (-3),
		MIDDLE(-2),
		BOTTOM(-1),
		LEFT(-5),
		RIGHT(-6);
		
		public int value;
		
		FixedPosition(int value){
			this.value = value;
		}
	}
	
	public int xposition;
    public int yposition;
    public int xpadding;
    public int ypadding;
    
    /**
     * Creates a new empty legend style
     */
    public LegendLocationStyle() {
    	
    }
    
    
	/**
	 * Creates a new legend style copying the values
	 * from the old legend style
	 * @param oldStyle
	 */
	public LegendLocationStyle(LegendLocationStyle oldStyle) {
		super();
		this.xposition = oldStyle.xposition;
		this.yposition = oldStyle.yposition;
		this.xpadding = oldStyle.xpadding;
		this.ypadding = oldStyle.ypadding;
	}
    
}
