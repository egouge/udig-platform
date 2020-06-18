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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.locationtech.udig.legend.internal.Messages;
import org.locationtech.udig.mapgraphic.style.FontStyle;
import org.locationtech.udig.mapgraphic.style.FontStyleContent;
import org.locationtech.udig.project.internal.Layer;
import org.locationtech.udig.style.IStyleConfigurator;
import org.locationtech.udig.ui.ColorEditor;

public class LegendGraphicStyleConfigurator extends IStyleConfigurator implements SelectionListener, ModifyListener {

    private Combo verticalMargin;
    private Combo horizontalMargin;
    private Combo verticalSpacing;
    private Combo horizontalSpacing;
    private Combo indentSize;
    private Combo imageSpacing;
    private Combo numCols;
    private Combo imageSize;
    private Spinner backgroundTransparency;
    private Button drawBorder;
    private Text maxEntrySize;
    
    private ColorEditor fontColour;
    private ColorEditor backgroundColour;
    
    private LegendStyle style = null;
    private FontStyle fontStyle = null;	//link color to font style
    
    private boolean fireEvents = true;
    
    
    public void createControl( Composite parent) {
    	parent.setLayout(new GridLayout());
		
    	ScrolledComposite scrollComposite = new ScrolledComposite(parent,SWT.V_SCROLL | SWT.H_SCROLL);
		scrollComposite.setLayoutData(new GridData(GridData.FILL_BOTH
				| GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

        Composite composite = new Composite(scrollComposite, SWT.NONE);
        
        GridLayout layout = new GridLayout(2, true);
        composite.setLayout(layout);
        
        GridData layoutData = null;

        Label numColsLabel = new Label(composite, SWT.NONE);
        numColsLabel.setLayoutData(layoutData);
        numColsLabel.setText(Messages.LegendGraphicStyleConfigurator_NumColsLabel);
        numColsLabel.setToolTipText(Messages.LegendGraphicStyleConfigurator_NumColsTooltip);
        
        numCols = new Combo(composite, SWT.BORDER);
        numCols.setItems("1","2","3","4","5"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        numCols.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        
        new Label(composite, SWT.NONE);
        new Label(composite, SWT.NONE);
        
        Label verticalMarginLabel = new Label(composite, SWT.NONE);
        verticalMarginLabel.setText(Messages.LegendGraphicStyleConfigurator_vertical_margin);
        verticalMarginLabel.setToolTipText(Messages.LegendGraphicStyleConfigurator_VSpacingTooltip);
        verticalMarginLabel.setLayoutData(layoutData);
        verticalMargin = new Combo(composite, SWT.BORDER);
        verticalMargin.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        verticalMargin.setItems("0","2","5","10","15"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        

        Label horizontalMarginLabel = new Label(composite, SWT.NONE);
        horizontalMarginLabel.setLayoutData(layoutData);
        horizontalMarginLabel.setText(Messages.LegendGraphicStyleConfigurator_horizontal_margin);
        horizontalMarginLabel.setToolTipText(Messages.LegendGraphicStyleConfigurator_HSpacingTooltip);
        horizontalMargin = new Combo(composite, SWT.BORDER);     
        horizontalMargin.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        horizontalMargin.setItems("0","2","5","10","15"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        
        Label verticalSpacingLabel = new Label(composite, SWT.NONE);
        verticalSpacingLabel.setLayoutData(layoutData);
        verticalSpacingLabel.setText(Messages.LegendGraphicStyleConfigurator_vertical_spacing);
        verticalSpacingLabel.setToolTipText(Messages.LegendGraphicStyleConfigurator_VEntrySpacingTooltip);
        verticalSpacing = new Combo(composite, SWT.BORDER);
        verticalSpacing.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        verticalSpacing.setItems("0","1","2","3","4","5"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
        
        Label horizontalSpacingLabel = new Label(composite, SWT.NONE);
        horizontalSpacingLabel.setLayoutData(layoutData);
        horizontalSpacingLabel.setText(Messages.LegendGraphicStyleConfigurator_horizontal_spacing);
        horizontalSpacingLabel.setToolTipText(Messages.LegendGraphicStyleConfigurator_ColSpacingTooltip);
        horizontalSpacing = new Combo(composite, SWT.BORDER);
        horizontalSpacing.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        horizontalSpacing.setItems("0","1","2","3","4","5"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
        
        new Label(composite, SWT.NONE);
        new Label(composite, SWT.NONE);
        
        Label imageSizeLabel = new Label(composite, SWT.NONE);
        imageSizeLabel.setLayoutData(layoutData);
        imageSizeLabel.setText(Messages.LegendGraphicStyleConfigurator_ImageSizeLabel);
        imageSizeLabel.setToolTipText(Messages.LegendGraphicStyleConfigurator_ImageSizeTooltip);
        imageSize = new Combo(composite, SWT.BORDER);
        imageSize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        imageSize.setItems("8","16","32","64"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        
        Label imgSpacingLabel = new Label(composite, SWT.NONE);
        imgSpacingLabel.setLayoutData(layoutData);
        imgSpacingLabel.setText(Messages.LegendGraphicStyleConfigurator_ImageTextSpacingLabel);
        imgSpacingLabel.setToolTipText(Messages.LegendGraphicStyleConfigurator_ImageTextSpacingTooltip);
        imageSpacing = new Combo(composite, SWT.BORDER);
        imageSpacing.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        imageSpacing.setItems("0","1","2","3","4","5"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

        Label indentSizeLabel = new Label(composite, SWT.NONE);
        indentSizeLabel.setLayoutData(layoutData);
        indentSizeLabel.setText(Messages.LegendGraphicStyleConfigurator_indent_size);
        indentSizeLabel.setToolTipText(Messages.LegendGraphicStyleConfigurator_IndentTooltip);        
        indentSize = new Combo(composite, SWT.BORDER);
        indentSize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        indentSize.setItems("0","5","10","15"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        
        
        Label maxEntryLabel = new Label(composite, SWT.NONE);
        maxEntryLabel.setLayoutData(layoutData);
        maxEntryLabel.setText("Max Entry Length (chars):");
        maxEntryLabel.setToolTipText("The maximum number of characters in a legend entry. Enter 0 for no limit");
        
        maxEntrySize = new Text(composite, SWT.BORDER);
        maxEntrySize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        maxEntrySize.setText("0");
        
        
        new Label(composite, SWT.NONE);
        new Label(composite, SWT.NONE);
        
        Label fontColourLabel = new Label(composite, SWT.NONE);
        fontColourLabel.setLayoutData(layoutData);
        fontColourLabel.setText(Messages.LegendGraphicStyleConfigurator_font_colour);
        fontColour = new ColorEditor(composite);
       
        Label backgroundColourLabel = new Label(composite, SWT.NONE);
        backgroundColourLabel.setLayoutData(layoutData);
        backgroundColourLabel.setText(Messages.LegendGraphicStyleConfigurator_background_colour);
        
        Composite temp = new Composite(composite, SWT.NONE);
        temp.setLayout(new GridLayout(3, false));
        ((GridLayout)temp.getLayout()).marginWidth = 0;
        ((GridLayout)temp.getLayout()).marginHeight = 0;
        
        backgroundColour = new ColorEditor(temp);
        
        Label translabel = new Label(temp, SWT.NONE);
        translabel.setText(Messages.LegendGraphicStyleConfigurator_opacityLabel);
        
        backgroundTransparency = new Spinner(temp, SWT.BORDER);
        backgroundTransparency.setMinimum(0);
        backgroundTransparency.setMaximum(255);
        backgroundTransparency.setSelection(150);
        
        
        Label borderLabel = new Label(composite, SWT.NONE);
        borderLabel.setLayoutData(layoutData);
        borderLabel.setText(Messages.LegendGraphicStyleConfigurator_BorderOption);
        drawBorder = new Button(composite, SWT.CHECK);
        
        
        composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        scrollComposite.setContent(composite);
        
        
        verticalMargin.addModifyListener(this);
        horizontalMargin.addModifyListener(this);
        verticalSpacing.addModifyListener(this);
        horizontalSpacing.addModifyListener(this);
        indentSize.addModifyListener(this);
        maxEntrySize.addModifyListener(this);
        numCols.addModifyListener(this);
        imageSpacing.addModifyListener(this);
        imageSize.addModifyListener(this);
        backgroundColour.getButton().addSelectionListener(this);
        fontColour.getButton().addSelectionListener(this);
        drawBorder.addSelectionListener(this);
        backgroundTransparency.addModifyListener(this);
    }
    
    @Override
    public boolean canStyle( Layer aLayer ) {
        return aLayer.hasResource(LegendGraphic.class);
    }

    @Override
    protected void refresh() {
        LegendStyle oldstyle = (LegendStyle) getStyleBlackboard().get(LegendStyleContent.ID);
        if (oldstyle == null) {
            oldstyle = LegendStyleContent.createDefault();
        }
        style = new LegendStyle(oldstyle);
        
        fontStyle = (FontStyle) getStyleBlackboard().get(FontStyleContent.ID);
        if (fontStyle == null) {
        	fontStyle = new FontStyle();
        }
        
        //update components
        fireEvents = false;
        verticalMargin.setText(Integer.toString(style.verticalMargin));
        horizontalMargin.setText(Integer.toString(style.horizontalMargin));
        verticalSpacing.setText(Integer.toString(style.verticalSpacing));
        horizontalSpacing.setText(Integer.toString(style.horizontalSpacing));
        indentSize.setText(Integer.toString(style.indentSize));
        numCols.setText(Integer.toString(style.numCols));
        imageSpacing.setText(Integer.toString(style.imageSpacing));
        imageSize.setText(Integer.toString(style.imageWidth));
        fontColour.setColorValue(new RGB(
                fontStyle.getColor().getRed(),
                fontStyle.getColor().getGreen(), 
                fontStyle.getColor().getBlue()));
        backgroundColour.setColorValue(new RGB(
                style.backgroundColour.getRed(),
                style.backgroundColour.getGreen(),
                style.backgroundColour.getBlue()
                ));
        backgroundTransparency.setSelection(style.backgroundColour.getAlpha());
        drawBorder.setSelection(style.drawBorder);
        maxEntrySize.setText(Integer.toString(style.maxEntryLength));
        
        fireEvents = true;
        updateBlackboard();
    }
    
    private void updateBlackboard() {
        
    	RGB bg = backgroundColour.getColorValue();
        style.backgroundColour = new Color(bg.red, bg.green, bg.blue, backgroundTransparency.getSelection());
        
        RGB fg = fontColour.getColorValue();
        fontStyle.setColor(new Color(fg.red, fg.green, fg.blue));
        
        style.horizontalMargin = Integer.parseInt(horizontalMargin.getText());
        style.horizontalSpacing = Integer.parseInt(horizontalSpacing.getText());
        style.numCols = Integer.parseInt(numCols.getText());
        style.indentSize = Integer.parseInt(indentSize.getText());
        style.verticalMargin = Integer.parseInt(verticalMargin.getText());
        style.maxEntryLength = Integer.parseInt(maxEntrySize.getText());
        style.verticalSpacing = Integer.parseInt(verticalSpacing.getText());
        style.imageSpacing = Integer.parseInt(imageSpacing.getText());
        
        style.imageWidth = Integer.parseInt(imageSize.getText());
        style.imageHeight = Integer.parseInt(imageSize.getText());
        style.drawBorder = drawBorder.getSelection();
        
        getStyleBlackboard().put(LegendStyleContent.ID, style);
        getStyleBlackboard().put(FontStyleContent.ID, fontStyle);
    }

    public void widgetSelected( SelectionEvent e ) {
        updateBlackboard();
    }

    public void widgetDefaultSelected( SelectionEvent e ) {
        updateBlackboard();
    }

    public void modifyText( ModifyEvent e ) {
    	if (fireEvents){
    		updateBlackboard();
    	}
    }
}
