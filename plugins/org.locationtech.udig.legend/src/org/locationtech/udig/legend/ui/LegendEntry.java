/* uDig - User Friendly Desktop Internet GIS client
 * http://udig.refractions.net
 * (C) 2005, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 */
package org.locationtech.udig.legend.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.geotools.styling.Rule;

/**
 * Legend row entry.
 * 
 * @author Emily Gouge
 * 
 */
public class LegendEntry {

	private int textPosition = SWT.CENTER;
	private String[] text;
	private ImageDescriptor icon;
	private Rule rule;
	
	private Integer spacingAfter = null;

	private boolean indent;
	private boolean noIcon;
	
	/**
	 * Creates a new legend entry based on a rule.
	 * 
	 * @param rule
	 */
	public LegendEntry(Rule rule) {
		this.rule = rule;
		this.text = new String[]{getText(rule)};
		this.icon = null;
		this.indent = false;
	}

	/**
	 * Creates a new legend entry with the given text
	 * 
	 * @param text
	 */
	public LegendEntry(String text) {
		this(new String[] {text}, null);
	}

	/**
	 * Creates a new legend entry with the given text and icon
	 * 
	 * @param text
	 * @param icon
	 */
	public LegendEntry(String text, ImageDescriptor icon) {
		this(new String[] {text}, icon);
	}

	/**
	 * Creates a new legend entry with the given text and icon
	 * 
	 * @param text
	 * @param icon
	 */
	public LegendEntry(String[] text, ImageDescriptor icon) {
		this.text = text;
		this.icon = icon;
		this.rule = null;
		this.indent = false;
		
		if (this.text == null) this.text = new String[0];
	}
	
	/**
	 * True if the legend entry should be indented, false otherwise
	 * @return
	 */
	public boolean isIndent() {
		return this.indent;
	}
	
	/**
	 * True if the legend entry should be indented, false otherwise
	 * @param indent
	 */
	public void setIndent(boolean indent) {
		this.indent = indent;
	}
	

	/**
	 * If true no image should be drawn
	 * @return
	 */
	public boolean getHideImage() {
		return this.noIcon;
	}
	
	/**
	 * If true to glyph image should be drawn.
	 * 
	 * @param hideImage
	 */
	public void setHideImage(boolean hideImage) {
		this.noIcon = hideImage;
	}
	
	/**
	 * Gets the legend entry text.
	 * <p>
	 * If the text has not been set then it looks for the text associated with
	 * the rule. Otherwise it returns an empty string.
	 * </p>
	 * 
	 * @return
	 */
	public String[] getText() {
		return this.text;
	}
	
	/**
	 * Sets the rule associated with this legend entry
	 * @param r
	 */
	public void setRule(Rule rule){
		this.rule = rule;
	}
	/**
	 * @return the rule associated with the legend entry
	 */
	public Rule getRule(){
		return this.rule;
	}
	
	/**
	 * Position of text in cell
	 * @return
	 */
	public int getTextPosition(){
		return this.textPosition;
	}
	
	/**
	 * 
	 * Position of text in cell.  Only SWT.TOP and
	 * SWT.MIDDLE supported
	 * @param textPosition SWT.TOP or SWT.MIDDLE
	 */
	public void setTextPosition(int textPosition){
		this.textPosition = textPosition;
	}
	
	/**
	 * The icon explicity set by the caller.  Will not
	 * generate an icon from the rule.
	 * @return
	 */
	public ImageDescriptor getIcon(){
		return this.icon;
	}
	
	/**
	 * Spacing after image
	 * @return null if default else number
	 * of pixel to draw between current cell and next
	 * cell
	 */
	public Integer getSpacingAfter(){
		return this.spacingAfter;
	}
	
	/**
	 * Sets the spacing after image.  Set
	 * to null to use default
	 * @param spacing
	 */
	public void setSpacingAfter(Integer spacing){
		this.spacingAfter = spacing;
	}

	/**
	 * Finds the text with the associated rule
	 * @param rule
	 * @return
	 */
	private String getText(Rule rule) {
		String text = ""; //$NON-NLS-1$
		String title = null;
		if (rule.getDescription() != null) {
			if (rule.getDescription().getTitle() != null) {
				title = rule.getDescription().getTitle().toString();
			}
		}
		if (title != null && !"".equals(title)) { //$NON-NLS-1$
			text = title;
		} else if (rule.getName() != null && !"".equals(rule.getName())) { //$NON-NLS-1$
			text = rule.getName();
		} else if (rule.getFilter() != null) {
			text = rule.getFilter().toString();
		}
		return text;
	}
}
