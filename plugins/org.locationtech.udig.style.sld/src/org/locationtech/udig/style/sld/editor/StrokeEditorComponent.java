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
package org.locationtech.udig.style.sld.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.locationtech.udig.filter.ComboExpressionViewer;
import org.locationtech.udig.style.sld.internal.Messages;

public class StrokeEditorComponent {
    // One of graphic fill, graphic stroke or solid
    private ComboExpressionViewer strokeTypeViewer;
    private ComboExpressionViewer widthViewer;
    private ComboExpressionViewer opacityViewer;
    private ComboExpressionViewer lineJoinViewer;
    private ComboExpressionViewer lineCapViewer;
    private ComboExpressionViewer dashArrayViewer;
    private ComboExpressionViewer dashOffsetViewer;
    
    /**
     * This takes a (hopefully clean) composite and populates it with the widgets necessary to
     * style up a stroke.  Note that it will set the layout of the parent composite, so lets 
     * not give it something half-full or things are likely to go awry.
     *
     * @param parent
     */
    public void createControl( Composite parent ) {
        parent.setLayout(new GridLayout(2, false));
        
        createLabel(parent, Messages.StrokeEditorComponent_StrokeType, Messages.StrokeEditorComponent_StrokeTypeTooltip, 2);
        strokeTypeViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        strokeTypeViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        strokeTypeViewer.setOptions(new String[] {"Solid", "Graphic Fill", "Graphic Stroke"});
        
        createLabel(parent, Messages.StrokeEditorComponent_Width, Messages.StrokeEditorComponent_WidthTooltip);
        widthViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        widthViewer.getControl().setLayoutData(new GridData(SWT.FILL,SWT.FILL, true, false));
        widthViewer.setOptions(new int[] {1, 2, 3, 5, 10});
        
        createLabel(parent, Messages.StrokeEditorComponent_Opacity, Messages.StrokeEditorComponent_OpacityTooltip);
        opacityViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        opacityViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        opacityViewer.setOptions(new double[] { 0.0, 0.25, 0.5, 0.75, 1.0});
        
        createLabel(parent, Messages.StrokeEditorComponent_LineJoin, Messages.StrokeEditorComponent_LineJoinTooltip);
        lineJoinViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        lineJoinViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        lineJoinViewer.setOptions(new String[] {"mitre", "round", "bevel"});
        
        createLabel(parent, Messages.StrokeEditorComponent_LineCap, Messages.StrokeEditorComponent_LineCapTooltip);
        lineCapViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        lineCapViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        lineCapViewer.setOptions(new String[] {"butt", "round", "square"});
        
        createLabel(parent, Messages.StrokeEditorComponent_DashArray, Messages.StrokeEditorComponent_DashArrayTooltip);
        dashArrayViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        dashArrayViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        dashArrayViewer.setOptions(new String[] {"solid", "dot", "dash", "dash-dot", "dash-dot-dot", "dot-dot"});
        
        createLabel(parent, Messages.StrokeEditorComponent_DashOffset, Messages.StrokeEditorComponent_DashOffsetTooltip);
        dashOffsetViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        dashOffsetViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        dashOffsetViewer.setOptions(new double[] {0.0, 0.25, 0.5, 0.75, 1.0});
    }
    
    private Label createLabel(Composite parent, String text, String tooltip, int colspan) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(text);
        label.setToolTipText(tooltip);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, colspan, 1));
        return label;
        
    }
    private Label createLabel(Composite parent, String text, String tooltip) {
        return createLabel(parent, text, tooltip, 1);
    }

}
