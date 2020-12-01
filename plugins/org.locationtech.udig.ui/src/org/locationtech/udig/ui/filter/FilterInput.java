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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.AttributeType;

/**
 * Used with {@link IFilterViewer#setInput(Object)} offer the user context to help construct a
 * Filter.
 * <p>
 * Example:
 * 
 * <pre>
 * // Rule filter is used to turn a rule on or off on a feature by feature basis
 * FilterInput ruleInput = new FilterInput();
 * ruleInput.setRequired(false);
 * ruleInput.setSchema(featureType);
 * 
 * Label label = new Label( composite, SWT.DEFAULT );
 * IExpressionViewer ruleFilterViewer = new CQLFilterViewer(composite, SWT.MULTI);
 * ruleFilterViewer.setInput(ruleInput);
 * 
 * // Acts as a normal viewer with direct access to Filter
 * ruleFilterViewer.setFilter(filter);
 * 
 * // The selection being the edited Filter (you can listen for selection changes)
 * ruleFilterViewer.setSelection(new StructuredSelection(filter));
 * </pre>
 * 
 * @author Jody Garnett
 */
public class FilterInput {

    protected boolean required = false;

    protected SimpleFeatureType schema;

    protected ControlDecoration feedback;

    protected List<String> propertyList;

    protected List<String> numericPropertyList;

    protected List<String> stringPropertyList;

    /** Requested viewer (for example stored in property list). Use null to auto select */
    protected String viewerId;
    
    public FilterInput() {
        this(null);
    }

    public FilterInput(SimpleFeatureType schema) {
        this(schema, false);
    }

    public FilterInput(SimpleFeatureType schema, boolean required) {
        this.schema = schema;
        this.required = required;
    }

    /**
     * @return true if this is a required field
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * The isRequired flag will be used to determine the default decoration to show (if there is no
     * warning or error to take precedence).
     * <p>
     * Please note that if this is a required field Filter.EXLCUDE is not considered to be a valid
     * state.
     * </p>
     * 
     * @param isRequired true if this is a required field
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Feature Type to use as a reference point for defining this filter.
     * <p>
     * This is often used to suggest attribute names to the user; for example comparable attributes
     * when defining PropertyIsGreater filter.
     * 
     * @param schema
     */
    public void setSchema(SimpleFeatureType schema) {
        this.schema = schema;
    }

    /**
     * Feature Type used by the FilterViewer
     * 
     * @param schema
     */
    public SimpleFeatureType getSchema() {
        return schema;
    }
    
    /**
     * Overide for {@link #toPropertyList()}.
     * 
     * @return list of supplied property names.
     */
    public List<String> getPropertyList(){
        return propertyList;
    }
    /**
     * Override for {@link #toPropertyList()}.
     * 
     * @param propertyList
     */
    public void setPropertyList(List<String> propertyList) {
        this.propertyList = propertyList;
    }
    
    /**
     * List of property names displayed to the user; generated from {@link #getSchema()} with the
     * option to override with a provided {@link #getPropertyList()}.
     * 
     * @return List of available property names, often generated from {@link #getSchema()}.
     */
    public List<String> toPropertyList(){
        if( propertyList != null ){
            return propertyList;
        }
        if( schema != null ){
            List<String> names = new ArrayList<String>();
            for( AttributeDescriptor descriptor : schema.getAttributeDescriptors() ){
                names.add( descriptor.getLocalName() );
            }
            propertyList = names;
            return propertyList;
        }
        return Collections.emptyList();
    }
    /**
     * Supplied list of String properties (suitable for use where text is expected).
     * 
     * @return list of provided string properties
     */
    public List<String> getStringPropertyList(){
        return stringPropertyList;
    }
    /**
     * Supplied list of String properties (suitable for use where text is expected)
     * @param numericPropertyList list of provided string properties
     */
    public void setStringPropertyList(List<String> stringPropertyList) {
        this.stringPropertyList = stringPropertyList;
    }
    /**
     * List of string properties generated from {@link #getSchema()} with the
     * option to override with a provided {@link #getStringPropertyList()}.
     * 
     * @return List of available property names, often generated from {@link #getSchema()}.
     */
    public List<String> toStringPropertyList(){
        if( stringPropertyList != null ){
            return stringPropertyList;
        }
        if( schema != null ){
            stringPropertyList = toPropertyList( String.class );
            return stringPropertyList;
        }
        return Collections.emptyList();
    }
    
    /**
     * Supplied list of numeric properties (suitable for use where a number is expected).
     * 
     * @return list of provided numeric properties
     */
    public List<String> getNumericPropertyList(){
        return numericPropertyList;
    }
    /**
     * Supplied list of numeric properties (suitable for use where a number is expected)
     * @param numericPropertyList list of provided numeric properties
     */
    public void setNumericPropertyList(List<String> numericPropertyList) {
        this.numericPropertyList = numericPropertyList;
    }
    /**
     * List of numeric properties generated from {@link #getSchema()} with the
     * option to override with a provided {@link #getNumericPropertyList()}.
     * 
     * @return List of available property names, often generated from {@link #getSchema()}.
     */
    public List<String> toNumericPropertyList(){
        if( numericPropertyList != null ){
            return numericPropertyList;
        }
        if( schema != null ){
            numericPropertyList = toPropertyList( Number.class );
            return numericPropertyList;
        }
        return Collections.emptyList();
    }
    
    /**
     * List of attributes from {@link #getSchema()} matching the requested java class.
     * 
     * @param binding Java class binding
     * @return List of available property names, often generated from {@link #getSchema()}.
     */
    public List<String> toPropertyList(Class<?> binding){
        if( schema != null && binding != null ){
            List<String> names = new ArrayList<String>();
            for( AttributeDescriptor descriptor : schema.getAttributeDescriptors() ){
                AttributeType type = descriptor.getType();
                if( binding.isAssignableFrom( type.getBinding() ) ){
                    names.add( descriptor.getLocalName() );
                }
            }
            return names;
        }
        return Collections.emptyList();
    }

    /**
     * Optional ControlDecoration used to show warnings or errors.
     * <p>
     * Control decorators are often applied to the label associated with a FilterInput, or wrapped
     * around the {@link IFilterViewer#getControl()}.
     * 
     * @return ControlDecoration used for feedback (if supplied).
     */
    public ControlDecoration getFeedback() {
        return feedback;
    }

    /**
     * Optional ControlDecoration used to show warnings or errors.
     * <p>
     * Control decorators are often applied to the label associated with a FilterInput, or wrapped
     * around the {@link IFilterViewer#getControl()}.
     * 
     * @param feedback ControlDecoration used for feedback (if supplied).
     */
    public void setFeedback(ControlDecoration feedback) {
        this.feedback = feedback;
    }
    /**
     * Viewer requested for initial display (for example saved in dialog settings).
     * @return viewerId requested for initial display
     */
    public String getViewerId() {
        return viewerId;
    }
    /**
     * Viewer requested for initial display (for example saved in dialog settings).
     * @param viewerId Viewer requested for initial display
     */
    public void setViewerId(String viewerId) {
        this.viewerId = viewerId;
    }
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append( getClass().getSimpleName() );
        build.append(" "); //$NON-NLS-1$
        if( required ){
            build.append("required "); //$NON-NLS-1$
        }
        if( viewerId != null ){
            build.append(" viewerId:"); //$NON-NLS-1$
            build.append( viewerId );
            build.append(" "); //$NON-NLS-1$
        }
        if( feedback != null ){
            build.append( " feedback:" ); //$NON-NLS-1$
            build.append(feedback.getControl().getClass().getSimpleName() );
            build.append(" "); //$NON-NLS-1$
        }
        if( schema != null ){
            build.append(" schema:"); //$NON-NLS-1$
            build.append( schema.getTypeName() );
            build.append(" "); //$NON-NLS-1$
        }
        return build.toString();
    }
}
