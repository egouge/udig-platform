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
package org.locationtech.udig.legend.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.locationtech.udig.legend.internal.messages"; //$NON-NLS-1$

	public static String LegendGraphicStyleConfigurator_background_colour;

	public static String LegendGraphicStyleConfigurator_BorderOption;

	public static String LegendGraphicStyleConfigurator_ColSpacingTooltip;

	public static String LegendGraphicStyleConfigurator_font_colour;

	public static String LegendGraphicStyleConfigurator_horizontal_margin;

	public static String LegendGraphicStyleConfigurator_horizontal_spacing;

	public static String LegendGraphicStyleConfigurator_HSpacingTooltip;

	public static String LegendGraphicStyleConfigurator_ImageSizeLabel;

	public static String LegendGraphicStyleConfigurator_ImageSizeTooltip;

	public static String LegendGraphicStyleConfigurator_ImageTextSpacingLabel;

	public static String LegendGraphicStyleConfigurator_ImageTextSpacingTooltip;

	public static String LegendGraphicStyleConfigurator_IndentTooltip;

	public static String LegendGraphicStyleConfigurator_indent_size;

	public static String LegendGraphicStyleConfigurator_NumColsLabel;

	public static String LegendGraphicStyleConfigurator_NumColsTooltip;

	public static String LegendGraphicStyleConfigurator_opacityLabel;

	public static String LegendGraphicStyleConfigurator_vertical_margin;

	public static String LegendGraphicStyleConfigurator_vertical_spacing;
	public static String LegendGraphicStyleConfigurator_VEntrySpacingTooltip;

	public static String LegendGraphicStyleConfigurator_VSpacingTooltip;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
