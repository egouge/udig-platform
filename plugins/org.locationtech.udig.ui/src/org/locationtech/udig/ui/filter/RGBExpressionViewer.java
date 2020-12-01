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
package org.locationtech.udig.ui.filter;

import java.awt.Color;

import net.miginfocom.swt.MigLayout;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.util.Converters;
import org.locationtech.udig.ui.internal.Messages;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.Literal;

/**
 * ExpressionViewer with Slider controls to allow the user to change the colour attribute of an
 * Expression
 * 
 * @author Scott
 * @since 1.3.0
 */
public class RGBExpressionViewer extends IExpressionViewer {
    
    private final int MIN = 0;
    private final int MAX = 255;

    /**
     * Factory used for the general purpose DefaultExpressionViewer.
     * 
     * @author jody
     * @since 1.2.0
     */
    public static class Factory extends ExpressionViewerFactory {
        @Override
        public int score(ExpressionInput input, Expression expression) {
            if (expression instanceof Literal) {
                Literal literal = (Literal) expression;
                Color color = literal.evaluate(null, Color.class);
                if (color != null) {
                    return Appropriate.APPROPRIATE.getScore();
                }
            }
            return Appropriate.NOT_APPROPRIATE.getScore();
        }

        @Override
        public IExpressionViewer createViewer(Composite parent, int style) {
            return new RGBExpressionViewer(parent, style);
        }
    }

    Composite control;

    protected Scale redScale;
    protected Spinner redSpinner;

    protected Scale blueScale;
    protected Spinner blueSpinner;

    protected Scale greenScale;
    protected Spinner greenSpinner;

    protected Text colorText;
    
    /**
     * Used to {@link #validate()} when any of the Scale controls change.
     */
    private SelectionListener listener = new SelectionListener() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            Expression newExpression = validate(e);
            if( newExpression != null ){
                internalUpdate( newExpression );
            }
        }
        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }
    };

    protected void listen(boolean listen) {
        if (listen) {
            redSpinner.addSelectionListener(listener);
            if( redScale != null ){
                redScale.addSelectionListener(listener);
            }
            greenSpinner.addSelectionListener(listener);
            if( greenScale != null ){
                greenScale.addSelectionListener(listener);
            }
            blueSpinner.addSelectionListener(listener);
            if( blueScale != null ){
                blueScale.addSelectionListener(listener);
            }
        } else {
            redSpinner.removeSelectionListener(listener);
            if( redScale != null ){
                redScale.removeSelectionListener(listener);
            }
            greenSpinner.removeSelectionListener(listener);
            if( greenScale != null ){
                greenScale.removeSelectionListener(listener);
            }
            blueSpinner.removeSelectionListener(listener);
            if( blueScale != null ){
                blueScale.removeSelectionListener(listener);
            }
        }
    }

    public RGBExpressionViewer(Composite parent, int style) {
        boolean multiLine = (SWT.MULTI & style) != 0;
        boolean isReadOnly = (style & SWT.READ_ONLY) != 0;
        
        control = new Composite(parent, style);
        control.setSize(400, 400);
        
        // RED
        if( multiLine ){
            redScale = new Scale(control, SWT.HORIZONTAL);
            redScale.setMaximum(MAX);
            redScale.setMinimum(MIN);
            redScale.setEnabled(true);
        }
        Label redLabel = new Label(control, SWT.SINGLE);
        redLabel.setText(multiLine ? Messages.RGBExpressionViewer_RedLong : Messages.RGBExpressionViewer_RedShort);

        redSpinner = new Spinner(control, SWT.BORDER);
        redSpinner.setMinimum(MIN);
        redSpinner.setMaximum(MAX);
        redSpinner.setEnabled(true);

        // GREEN
        if( multiLine ){
            greenScale = new Scale(control, SWT.HORIZONTAL);
            greenScale.setMaximum(MAX);
            greenScale.setMinimum(MIN);
        }
        Label greenLabel = new Label(control, SWT.SINGLE);
        greenLabel.setText(multiLine ? Messages.RGBExpressionViewer_GreenLong : Messages.RGBExpressionViewer_GreenShot);
        
        greenSpinner = new Spinner(control, SWT.BORDER);
        greenSpinner.setDigits(0);
        greenSpinner.setMinimum(MIN);
        greenSpinner.setMaximum(MAX);
        
        // BLUE
        if( multiLine ){
            blueScale = new Scale(control, SWT.HORIZONTAL);
            blueScale.setMaximum(MAX);
            blueScale.setMinimum(MIN);
        }
        Label blueLabel = new Label(control, SWT.SINGLE);
        blueLabel.setText(multiLine ? Messages.RGBExpressionViewer_BlueLong : Messages.RGBExpressionViewer_BlueShort);
        
        blueSpinner = new Spinner(control, SWT.BORDER);
        blueSpinner.setDigits(0);
        blueSpinner.setMinimum(MIN);
        blueSpinner.setMaximum(MAX);
        
        // HEX
        colorText = new Text(control, SWT.SINGLE|SWT.READ_ONLY|SWT.BORDER);
        Label hexLabel = new Label(control, SWT.SINGLE);
        hexLabel.setText(Messages.RGBExpressionViewer_Color);

        
        if (multiLine) {
            MigLayout layout = new MigLayout("insets 0", "[][][grow]", "[]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            control.setLayout(layout);
            
            redLabel.setLayoutData("cell 0 0, alignx trailing, gapx related"); //$NON-NLS-1$
            redSpinner.setLayoutData("cell 1 0, wmin 60,alignx left, gapx related"); //$NON-NLS-1$
            redScale.setLayoutData("cell 2 0, grow, width 200:100%:100%"); //$NON-NLS-1$
            
            greenLabel.setLayoutData("cell 0 1, alignx trailing, gapx related"); //$NON-NLS-1$
            greenSpinner.setLayoutData("cell 1 1, wmin 60,alignx left, gapx related"); //$NON-NLS-1$
            greenScale.setLayoutData("cell 2 1,grow, width 200:100%:100%"); //$NON-NLS-1$
            
            blueLabel.setLayoutData("cell 0 2, alignx trailing, gapx related"); //$NON-NLS-1$
            blueSpinner.setLayoutData("cell 1 2, wmin 60,alignx left, gapx related"); //$NON-NLS-1$
            blueScale.setLayoutData("cell 2 2,grow, width 200:100%:100%"); //$NON-NLS-1$
            
            hexLabel.setLayoutData("cell 0 3, alignx trailing, gapx related"); //$NON-NLS-1$
            colorText.setLayoutData("cell 2 3 2 1,grow, width 200:100%:100%"); //$NON-NLS-1$

        }
        else {
            control.setLayout( new MigLayout("insets 0, flowx", "", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            
            redLabel.setLayoutData("gap related"); //$NON-NLS-1$
            redSpinner.setLayoutData("gap unrelated"); //$NON-NLS-1$
            
            greenLabel.setLayoutData("gap related"); //$NON-NLS-1$
            greenSpinner.setLayoutData("gap unrelated"); //$NON-NLS-1$
            
            blueLabel.setLayoutData("gap related"); //$NON-NLS-1$
            blueSpinner.setLayoutData("gap unrelated"); //$NON-NLS-1$
            
            hexLabel.setLayoutData("gap related"); //$NON-NLS-1$
            colorText.setLayoutData("gap unrelated"); //$NON-NLS-1$
        }   
        listen(true);
    }

    @Override
    public Control getControl() {
        return control;
    }
    
    /**
     * Sync the two controls based on the selection event.
     * 
     * @param spinner
     * @param scalebar
     * @param e
     */
    private void syncSelection( Spinner spinner, Scale scalebar, SelectionEvent e ){
        if( spinner == null || scalebar == null ){
            // no need to sync
            return;
        }
        if( spinner == e.getSource() ){
            int value = spinner.getSelection();
            scalebar.setSelection( value );
            scalebar.setEnabled(true);
        }
        
        if( scalebar == e.getSource() ){
            int value = scalebar.getSelection();
            spinner.setSelection( value );
            spinner.setEnabled(true);
        }
        
    }
    /**
     * Used to update the expression (if required).
     * 
     * @param e Even causing the change
     * @return Validated expression if avaialble
     */
    public Expression validate(SelectionEvent e) {
        syncSelection( redSpinner, redScale, e );
        
        syncSelection( greenSpinner, greenScale, e );
        
        syncSelection( blueSpinner, blueScale, e );
        
        int r = redSpinner.getSelection();
        int g = greenSpinner.getSelection();
        int b = blueSpinner.getSelection();
        Color c = new Color(r, g, b);
        
        String cql = "#"+Integer.toHexString(c.getRGB() & 0x00ffffff); //$NON-NLS-1$
        colorText.setText(cql);
        colorText.setEnabled(true);
        
        FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
        Expression expression = ff.literal(cql);

        return expression;
    }
    
    /** Used to supply a filter for display or editing */
    @Override
    public void setExpression(Expression expression) {
        if (this.expression == expression) {
            return;
        }
        this.expression = expression;
        refreshExpression();
        fireSelectionChanged(new SelectionChangedEvent(this, getSelection()));
    }
    @Override
    public void refresh() {
        if (input != null) {
            boolean isColor = Color.class.isAssignableFrom( input.getBinding() );
            if( isColor ){
                feedback();
            }
            else {
                feedback(Messages.RGBExpressionViewer_useWithColor);
            }
        }
        refreshExpression();
    }
    
    public void refreshSelection( Spinner spinner, Scale scalebar, int selection ){
        spinner.setValues(selection,  MIN, MAX, 0, 1,5);
        // spinner.setSelection(selection);
        if( scalebar != null ){
            scalebar.setMinimum(MIN);
            scalebar.setMaximum(MAX);
            scalebar.setIncrement(1);
            scalebar.setPageIncrement(5);
            scalebar.setSelection(selection);
        }
    }
    public void refreshExpression() {
        if (colorText != null && !colorText.isDisposed()) {
            colorText.getDisplay().asyncExec(new Runnable() {
                public void run() {
                    if (colorText == null || colorText.isDisposed()) {
                        return; // must of been disposed while we are in the queue
                    }
                    try {
                        listen(false); // don't listen while updating controls
                        Expression expr = getExpression();

                        if (expr instanceof Literal) {
                            Literal literal = (Literal) expr;
                            Color color = literal.evaluate(null, Color.class);
                            if (color != null) {
                                feedback();
                                refreshSelection( redSpinner, redScale, color.getRed() );
                                refreshSelection( greenSpinner, greenScale, color.getGreen() );
                                refreshSelection( blueSpinner, blueScale, color.getBlue() );
                                
                                String text = Converters.convert(color, String.class);
                                if (text != null) {
                                    colorText.setText(text);
                                } else {
                                    colorText.setText("#"+Integer.toHexString(color.getRGB())); //$NON-NLS-1$
                                }
                                colorText.setEnabled(true);
                                return; // literal color displayed!
                            }
                        }
                        
                        String cql = ""; //$NON-NLS-1$
                        if (expr != null) {
                            cql = ECQL.toCQL(expr);
                        }
                        colorText.setText(cql);
                        colorText.setEnabled(false);
                        
                        // fill in other controls with 0 as a starting point
                        refreshSelection( redSpinner, redScale, 0 );
                        refreshSelection( greenSpinner, greenScale, 0 );
                        refreshSelection( blueSpinner, blueScale, 0 );
                        
                        feedbackReplace( expr );
                    } finally {
                        listen(true);
                    }
                }
            });
        }
    }

    @Override
    public void setSelection(ISelection selection, boolean reveal) {
        if (selection instanceof StructuredSelection) {
            StructuredSelection structuredSelection = (StructuredSelection) selection;
            Object value = structuredSelection.getFirstElement();

            if (value instanceof Expression) {
                setInput((Expression) value);
            }
        }
    }

}
