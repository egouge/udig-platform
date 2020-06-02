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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.locationtech.udig.mapgraphic.MapGraphic;
import org.locationtech.udig.mapgraphic.internal.Messages;
import org.locationtech.udig.project.IBlackboard;
import org.locationtech.udig.project.internal.Layer;
import org.locationtech.udig.project.internal.StyleBlackboard;
import org.locationtech.udig.style.IStyleConfigurator;

/**
 * Configures a "location style", which indicates that a particular layer can
 * be relocated, such as a scalebar or a legend.
 * 
 * @author Richard Gould
 * @since 0.6.0
 * @see LegendLocationStyleContent
 */
public final class LegendLocationStyleConfigurator extends IStyleConfigurator implements SelectionListener, ModifyListener {

    /** vertical alignment constants * */
    private static final String TOP = Messages.ScalebarStyleConfigurator_top; 
    private static final String MIDDLE = Messages.ScalebarStyleConfigurator_middle; 
    private static final String BOTTOM = Messages.ScalebarStyleConfigurator_bottom; 

    /** horizontal alignment constants * */
    private static final String LEFT = Messages.ScalebarStyleConfigurator_left; 
    private static final String CENTER = Messages.ScalebarStyleConfigurator_center; 
    private static final String RIGHT = Messages.ScalebarStyleConfigurator_right; 

    /** ui widgets * */
    private Combo xCombo;
    private Combo yCombo;

    private Combo xPadding;
    private Combo yPadding;

    /**
     * @see org.locationtech.udig.style.StyleConfigurator#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl( Composite parent ) {
        parent.setLayout( new GridLayout( 2, false ));
        
        
        Label xLabel = new Label(parent, SWT.RIGHT);
        xLabel.setText(Messages.ScalebarStyleConfigurator_horizontalAlignment); 

        xCombo = new Combo(parent, SWT.DROP_DOWN| SWT.READ_ONLY);
        xCombo.setItems(new String[]{LEFT, CENTER, RIGHT});
        xCombo.select(0);
        xCombo.addSelectionListener(this);
        xCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        
        Label yLabel = new Label(parent, SWT.RIGHT);
        yLabel.setText(Messages.ScalebarStyleConfigurator_verticalAlignment); 

        yCombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
        yCombo.setItems(new String[]{TOP, MIDDLE, BOTTOM});
        yCombo.select(0);
        yCombo.addSelectionListener(this);
        yCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        
        
        Label xPLabel = new Label(parent, SWT.RIGHT);
        xPLabel.setText(Messages.LegendLocationStyleConfigurator_HorizontalOffset); 

        xPadding = new Combo(parent, SWT.DROP_DOWN);
        xPadding.setItems(new String[]{"0", "1", "3", "5","10","15"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
        xPadding.select(0);
        xPadding.addModifyListener(this);
        xPadding.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        
        Label yPLabel = new Label(parent, SWT.RIGHT);
        yPLabel.setText(Messages.LegendLocationStyleConfigurator_VerticalOffset); 

        yPadding = new Combo(parent, SWT.DROP_DOWN);
        yPadding.setItems(new String[]{"0", "1", "3", "5","10","15"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
        yPadding.select(0);
        yPadding.addModifyListener(this);
        yPadding.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
       
    }
    

	public void init() {
		// do nothing

	}

	public void refresh() {
	    IBlackboard blackboard = getStyleBlackboard();
	    LegendLocationStyle style = (LegendLocationStyle) blackboard.get(LegendLocationStyleContent.ID);

        if (style == null) {
            style = LegendLocationStyleContent.createDefaultStyle();
            setLeft(style);
            setTop(style);

            blackboard.put(LegendLocationStyleContent.ID, style);
            ((StyleBlackboard) blackboard).setSelected(new String[]{LegendLocationStyleContent.ID});
        }

        if (isLeft(style))
            xCombo.select(0);
        else if (isCenter(style))
            xCombo.select(1);
        else if (isRight(style))
            xCombo.select(2);
        
        if (isTop(style)) 
            yCombo.select(0);
        else if (isMiddle(style)) 
            yCombo.select(1);
        else if (isBottom(style)) 
            yCombo.select(2);
        
        yPadding.setText(String.valueOf(style.ypadding));
        xPadding.setText(String.valueOf(style.xpadding));
    }
	
    public boolean canStyle( Layer layer ) {
        return layer.hasResource(MapGraphic.class);
    }

	@Override
	public void modifyText(ModifyEvent e) {
		widgetSelected(null);
		
	}

    /*
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected( SelectionEvent e ) {
        IBlackboard blackboard = getStyleBlackboard();
        LegendLocationStyle style = (LegendLocationStyle) blackboard.get(LegendLocationStyleContent.ID);

        if (style == null) {
            style = LegendLocationStyleContent.createDefaultStyle();
            
            blackboard.put(LegendLocationStyleContent.ID, style);
            ((StyleBlackboard) getStyleBlackboard()).setSelected(new String[]{LegendLocationStyleContent.ID});
        }
        
        //read object state from ui widgets
        switch (xCombo.getSelectionIndex()) {
            case 0:
                setLeft(style);
                break;
            case 1:
                setCenter(style);
                break;
            case 2:
                setRight(style);
                
        }
        
        switch(yCombo.getSelectionIndex()) {
            case 0:
                setTop(style);
                break;
            case 1:
                setMiddle(style);
                break;
            case 2:
                setBottom(style);
        }
        
        try {
        	style.xpadding = Integer.valueOf(xPadding.getText());
        }catch (Exception ex) {}
        
        try {
        	style.ypadding = Integer.valueOf(yPadding.getText());
        }catch (Exception ex) {}
    }
    
    /*
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected( SelectionEvent e ) {
        //do nothing
    }

    
    protected boolean isLeft( LegendLocationStyle rect ) {
    	return rect.xposition == LegendLocationStyle.FixedPosition.LEFT.value;
    }

    protected void setLeft( LegendLocationStyle rect ) {
    	rect.xposition = LegendLocationStyle.FixedPosition.LEFT.value;
    }

    protected boolean isCenter( LegendLocationStyle rect ) {
    	return rect.xposition == LegendLocationStyle.FixedPosition.MIDDLE.value;
    }

    protected void setCenter( LegendLocationStyle rect ) {
    	rect.xposition = LegendLocationStyle.FixedPosition.MIDDLE.value;
    }

    protected boolean isRight( LegendLocationStyle rect ) {
    	return rect.xposition == LegendLocationStyle.FixedPosition.RIGHT.value;
    }

    protected void setRight( LegendLocationStyle rect ) {
        rect.xposition = LegendLocationStyle.FixedPosition.RIGHT.value;
    }

    protected boolean isTop( LegendLocationStyle rect ) {
    	return rect.yposition == LegendLocationStyle.FixedPosition.TOP.value;
    }

    protected void setTop( LegendLocationStyle rect ) {
    	rect.yposition = LegendLocationStyle.FixedPosition.TOP.value;
    }

    protected boolean isMiddle( LegendLocationStyle rect ) {
    	return rect.yposition == LegendLocationStyle.FixedPosition.MIDDLE.value;

    }

    protected void setMiddle( LegendLocationStyle rect ) {
    	rect.yposition = LegendLocationStyle.FixedPosition.MIDDLE.value;
    }

    protected boolean isBottom( LegendLocationStyle rect ) {
    	return rect.yposition == LegendLocationStyle.FixedPosition.BOTTOM.value;
    }

    protected void setBottom( LegendLocationStyle rect ) {
    	rect.yposition = LegendLocationStyle.FixedPosition.BOTTOM.value;
    }

}
