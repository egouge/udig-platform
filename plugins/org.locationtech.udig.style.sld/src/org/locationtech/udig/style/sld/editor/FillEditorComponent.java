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

public class FillEditorComponent {
    private ComboExpressionViewer fillTypeViewer;
    private ComboExpressionViewer colourViewer;
    private ComboExpressionViewer opacityViewer;
    
    public void createControl(Composite parent) {
        parent.setLayout(new GridLayout(2, false));
        
        Label label = new Label(parent, SWT.NONE);
        label.setText(Messages.FillEditorComponent_FillType);
        label.setToolTipText(Messages.FillEditorComponent_FillTypeTooltip);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
        fillTypeViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        fillTypeViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        fillTypeViewer.getControl().setToolTipText(Messages.FillEditorComponent_FillTypeTooltip);
        
        label = new Label(parent, SWT.NONE);
        label.setText(Messages.FillEditorComponent_Color);
        label.setToolTipText(Messages.FillEditorComponent_ColorTooltip);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
        colourViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        colourViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        label = new Label(parent, SWT.NONE);
        label.setText(Messages.FillEditorComponent_Opacity);
        label.setToolTipText(Messages.FillEditorComponent_OpacityTooltip);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
        opacityViewer = new ComboExpressionViewer(parent, SWT.SINGLE);
        opacityViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            
        
        
    }

}
