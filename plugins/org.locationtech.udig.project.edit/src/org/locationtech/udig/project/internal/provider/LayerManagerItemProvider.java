/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.locationtech.udig.project.internal.provider;

import java.util.Collection;

import org.locationtech.udig.project.internal.EditManager;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.opengis.feature.simple.SimpleFeature;

/**
 * This is the item provider adpater for a {@link org.locationtech.udig.project.LayerManager} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class LayerManagerItemProvider extends ItemProviderAdapter
        implements
            IEditingDomainItemProvider,
            IStructuredItemContentProvider,
            ITreeItemContentProvider,
            IItemLabelProvider,
            IItemPropertySource {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public static final String copyright = 
            "uDig - User Friendly Desktop Internet GIS client\n" //$NON-NLS-1$
          + "http://udig.refractions.net\n" //$NON-NLS-1$
          + "(C) 2004-2012, Refractions Research Inc.\n" //$NON-NLS-1$
          + "\n\n" //$NON-NLS-1$
          + "All rights reserved. This program and the accompanying materials\n" //$NON-NLS-1$
          + "are made available under the terms of the Eclipse Public License v1.0\n" //$NON-NLS-1$
          + "(http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD\n" //$NON-NLS-1$
          + "License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).\n"; //$NON-NLS-1$
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public LayerManagerItemProvider( AdapterFactory adapterFactory ) {
        super(adapterFactory);
    }

    /**
     * This returns LayerManager.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Object getImage( Object object ) {
        return getResourceLocator().getImage("full/obj16/LayerManager"); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    public String getText( Object object ) {
        SimpleFeature labelValue = ((EditManager) object).getEditFeature();
        String label = labelValue == null ? null : labelValue.toString();
        return label == null || label.length() == 0 ? getString("_UI_LayerManager_type") : //$NON-NLS-1$
                getString("_UI_LayerManager_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing all of the children that can be created under this object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void collectNewChildDescriptors( Collection newChildDescriptors, Object object ) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public ResourceLocator getResourceLocator() {
        return ProjectEditPlugin.INSTANCE;
    }

}
