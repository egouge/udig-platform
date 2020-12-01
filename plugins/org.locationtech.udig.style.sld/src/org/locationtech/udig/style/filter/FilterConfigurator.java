/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2008, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 *
 */
package org.locationtech.udig.style.filter;

import net.miginfocom.swt.MigLayout;
import org.locationtech.udig.project.ProjectBlackboardConstants;
import org.locationtech.udig.project.internal.Layer;
import org.locationtech.udig.style.IStyleConfigurator;
import org.locationtech.udig.style.sld.internal.Messages;
import org.locationtech.udig.ui.filter.FilterInput;
import org.locationtech.udig.ui.filter.FilterViewer;
import org.locationtech.udig.ui.filter.IFilterViewer;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.FeatureSource;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

/**
 * Style page responsible for allowing user to configure filter information
 * used to preprocess data prior to display.
 */
public class FilterConfigurator extends IStyleConfigurator {

    /** Viewer used to store the current filter; it will only be changed by the user */
    protected IFilterViewer filterViewer;
    
    /** Constant used to store FilterStyle on the layer blackboard */
    public static String STYLE_ID = ProjectBlackboardConstants.LAYER__STYLE_FILTER;
    
    /** Toggle to indicate interest in the current area of interest */
    protected Button aoiButton;
    
    /**
     * AOI we are watching you
     */
    private SelectionListener aoiListener = new SelectionListener(){
        @Override
        public void widgetSelected( SelectionEvent e ) {
            if( aoiButton == null || aoiButton.isDisposed() ){
                return; // ignore me!
            }
            valueChanged();
        }
        @Override
        public void widgetDefaultSelected( SelectionEvent e ) {
        }
    };

    /** Will write filter to blackboard on focus lost */
    private ISelectionChangedListener listener = new ISelectionChangedListener(){
        public void selectionChanged( SelectionChangedEvent event ) {
            if( filterViewer == null || filterViewer.getControl() == null ||  filterViewer.getControl().isDisposed() ){
                return; // nothing to see
            }
            Filter oldValue = getFilterStyle().getFilter();
            Filter filter = filterViewer.getFilter();
            if( filter == null ){
                return; // invalid
            }
            if( !IFilterViewer.same( oldValue, filter ) ){
                valueChanged();
            }
            else {
                // ignore 
            }
        }
    };


    public FilterConfigurator() {
    }
    
    public void valueChanged() {
        if( aoiButton == null || aoiButton.isDisposed() ){
            return; // nothing to see
        }
        getApplyAction().setEnabled(true);
        
        FilterStyle style = getFilterStyle();
        Filter filter = style.getFilter();
        boolean isAoiFilter = style.isAoiFilter();
        
        if( (filterViewer.getFilter() == null || !filterViewer.getFilter().equals(filter)) ||
                aoiButton.getSelection() != isAoiFilter ){
            FilterStyle newFilterStyle = new FilterStyle( style );
            newFilterStyle.setFilter( filterViewer.getFilter() );
            newFilterStyle.setAoiFilter( aoiButton.getSelection());

            // this will cause FilterContent to rewrite our memento
            // the actual change won't go out until "apply" or "okay" is pressed
            getStyleBlackboard().put(STYLE_ID, newFilterStyle ); 
        }
    }

    @Override
    public boolean canStyle( Layer aLayer ) {
        if (aLayer.hasResource(FeatureSource.class)) {
            return true;
        }
        return false;
    }
    /**
     * Grab the FilterStyle from the style blackboard (may be empty).
     * 
     * @return FilterStyle from style blackboard (may be empty)
     */
    protected FilterStyle getFilterStyle() {
        Layer layer = getLayer();
        if( !canStyle(layer)){
            throw new IllegalStateException("Layer "+layer.getName()+" cannot be filtered" ); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        FilterStyle current = (FilterStyle) getStyleBlackboard().get(STYLE_ID);
        if (current == null) {
            return new FilterStyle(); // not available
        }
        return current;
    }

    @Override
    public void createControl( Composite parent ) {
        MigLayout layout = new MigLayout("insets panel", "[][fill]", "[fill][]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        parent.setLayout(layout);
        Label label;
        
        label = new Label(parent, SWT.SINGLE );
        label.setText(Messages.FilterConfigurator_AutoFilter);
        label.setLayoutData("cell 0 0,aligny top, gapx 0 unrelated"); // unrelated spacing after to leave room for label decoration //$NON-NLS-1$

        
        // Area of Interest filter button
        aoiButton = new Button(parent, SWT.CHECK);
        aoiButton.setText(Messages.FilterConfigurator_AOI);
        aoiButton.setLayoutData("cell 1 0 2 1, left, grow x" ); //$NON-NLS-1$
        
        label = new Label(parent, SWT.SINGLE );
        label.setText(Messages.FilterConfigurator_MnaulFilter);
        label.setLayoutData("cell 0 1,aligny top, gapx 0 unrelated"); // unrelated spacing after to leave room for label decoration //$NON-NLS-1$
        
        ControlDecoration decoration = new ControlDecoration(label, SWT.RIGHT | SWT.TOP );
        filterViewer = new FilterViewer(parent, SWT.MULTI );
        filterViewer.getControl().setLayoutData("cell 1 1,grow,width 200:100%:100%,height 60:100%:100%"); //$NON-NLS-1$
        
        FilterInput input = new FilterInput();
        input.setFeedback( decoration );
        filterViewer.setInput(input);
        filterViewer.refresh();

        listen(true);
    }

    public void listen( boolean listen ) {
        if (listen) {
            filterViewer.addSelectionChangedListener(listener);
            aoiButton.addSelectionListener(aoiListener);
        } else {
            filterViewer.removeSelectionChangedListener(listener);
            aoiButton.removeSelectionListener(aoiListener);
        }
    }
        
    @Override
    protected void refresh() {
        if (filterViewer == null || filterViewer.getControl() == null || filterViewer.getControl().isDisposed()) {
            return;
        }
        getApplyAction().setEnabled(false);
        if( this.aoiButton == null || this.aoiButton.isDisposed()){
            return; // we are shut down and thus ignoring this request to update the ui
        }
        SimpleFeatureType type = getLayer().getSchema();
        FilterInput filterInput = filterViewer.getInput();
        filterInput.setSchema( type );
        
        final FilterStyle style = getFilterStyle();

        filterViewer.getControl().getDisplay().asyncExec(new Runnable(){
            public void run() {
                if (filterViewer == null || filterViewer.getControl() == null || filterViewer.getControl().isDisposed()) {
                    return; // we are shut down and thus ignoring this request to update the ui
                }
                try {
                    listen(false);
                    
                    Filter filter = style != null ? style.getFilter() : Filter.INCLUDE;
                    
                    filterViewer.setFilter( filter );
                    filterViewer.refresh();
                    
                    aoiButton.setSelection( style.isAoiFilter() );
                } finally {
                    listen(true);
                }

            }
        });
    }

    @Override
    public void dispose() {
        if (filterViewer != null) {
            listen(false);
            filterViewer = null;
            aoiButton = null;
        }
        super.dispose();
    }
}
