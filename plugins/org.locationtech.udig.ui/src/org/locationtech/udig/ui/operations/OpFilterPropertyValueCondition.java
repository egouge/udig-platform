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
package org.locationtech.udig.ui.operations;

import java.util.Iterator;

import org.locationtech.udig.internal.ui.UiPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Assesses if a tools is valid for a given property value.
 * 
 * @author rgould
 * @since 1.1.0
 */
class OpFilterPropertyValueCondition implements OpFilter {
   
    private static final PropertyValue FAILED_TO_LOAD = new PropertyValue<Object>(){

        public void addListener( IOpFilterListener listener ) {
        }

        public boolean canCacheResult() {
            return true;
        }

        public boolean isBlocking() {
            return false;
        }

        public boolean isTrue( Object object, String value ) {
            return true;
        }

        public void removeListener( IOpFilterListener listener ) {
        }
        
    };
    private final String equalsValue;
    private final IConfigurationElement propertyElement;
    private final String targetObject;
    private volatile PropertyValue propertyValueInstance;
    private Class< ? > targetClass;

    public OpFilterPropertyValueCondition( IConfigurationElement propertyElement2,String targetObject2, String equalsValue2 ) {
        this.equalsValue = equalsValue2;
        this.propertyElement = propertyElement2;
        this.targetObject = targetObject2;
    }

    public boolean accept( Object object ) {
        if( object instanceof IStructuredSelection ){
            IStructuredSelection selection=(IStructuredSelection) object;
            for( Iterator iter = selection.iterator(); iter.hasNext(); ) {
                Object element = iter.next();
                if( !accepted( element ) )
                    return false;
            }
            return true;
        }else{
            return accepted(object);
        }
    }

    @SuppressWarnings("unchecked")
    private boolean accepted( Object object ) {
        if( object==null)
            return false;
        if ( getTargetObject(object)!=null && !getTargetObject(object).isAssignableFrom(object.getClass()) ){
            return false;
        }

        PropertyValue v = getValue();
        if( v==null )
            return false;
        return v.isTrue(object, this.equalsValue);
    }

    
    
    private PropertyValue getValue() {
        if (propertyValueInstance == null) {
            try {
                String classAttribute = propertyElement.getAttribute("class"); //$NON-NLS-1$
                if( classAttribute==null || classAttribute.trim().length()==0  )
                    // fall back case to deprecated tag
                    propertyValueInstance = (PropertyValue) propertyElement.createExecutableExtension("value"); //$NON-NLS-1$
                else{
                    propertyValueInstance = (PropertyValue) propertyElement.createExecutableExtension("class"); //$NON-NLS-1$
                }
            } catch (CoreException e) {
                propertyValueInstance=FAILED_TO_LOAD;
                UiPlugin.log("Error in extension: "+propertyElement.getDeclaringExtension().getUniqueIdentifier(), e); //$NON-NLS-1$
                return null;
            }
        }
        return propertyValueInstance;
    }

    private Class< ? extends Object> getTargetObject( Object object ) {

        if (targetClass == null) {
            try {
                targetClass = object.getClass().getClassLoader().loadClass(targetObject);
            } catch (ClassNotFoundException e) {
                UiPlugin.log("",e); //$NON-NLS-1$
                return null;
            }
        }

        return targetClass;
    }

    public void addListener( IOpFilterListener listener ) {
        getValue().addListener(listener);
    }

    public boolean canCacheResult() {
        return getValue().canCacheResult();
    }

    public boolean isBlocking() {
        return getValue().isBlocking();
    }

    public void removeListener( IOpFilterListener listener ) {
        getValue().removeListener(listener);
    }

    @Override
    public String toString() {
        if( propertyValueInstance == null ){
            return "PropertyValue "+propertyElement.getAttribute("class"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        else {
            return propertyValueInstance.getClass().getSimpleName();
        }
    }
}
