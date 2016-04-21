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
package org.locationtech.udig.project.internal.render.impl;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.udig.catalog.IGeoResource;
import org.locationtech.udig.core.internal.ExtensionPointProcessor;
import org.locationtech.udig.project.internal.Layer;
import org.locationtech.udig.project.internal.render.RenderContext;
import org.locationtech.udig.project.internal.render.RenderManager;
import org.locationtech.udig.project.internal.render.SelectionLayer;
import org.locationtech.udig.project.internal.render.impl.InternalRenderMetricsFactory.InternalRenderMetrics;
import org.locationtech.udig.project.render.IMultiLayerRenderer;
import org.locationtech.udig.project.render.IRenderMetricsFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;

/**
 * Processes the org.locationtech.udig.project.renderer extension point adding the RendererMetricsFactories that apply to the layer to 
 * a cache of legal RendererMetricsFactories.
 * 
 * @author Jesse
 * @since 1.0.0
 */
public class RendererExtensionProcessor implements ExtensionPointProcessor {
    Layer layer;

    List<InternalRenderMetrics> rFactories = new ArrayList<InternalRenderMetrics>();

    private org.locationtech.udig.project.internal.Map map;

    private RenderManager rm;


    /**
     * Creates an new instance of Processor
     * 
     * @param layer The layer which needs to be rendered
     */
    public RendererExtensionProcessor( Layer layer, org.locationtech.udig.project.internal.Map map, RenderManager rm ) {
        this.layer = layer;
        this.rm=rm;
        this.map=map;
    }

    /**
     * @see org.locationtech.udig.core.internal.ExtensionPointProcessor#process(org.eclipse.core.runtime.IExtension,
     *      org.eclipse.core.runtime.IConfigurationElement)
     */
    public void process( IExtension extension, IConfigurationElement element ) {

        try {
            IRenderMetricsFactory createExecutableExtension = (IRenderMetricsFactory) element.createExecutableExtension("class"); //$NON-NLS-1$
            InternalRenderMetricsFactory metricsFactory = new InternalRenderMetricsFactory(
                    createExecutableExtension, element); 

            
            List<IGeoResource> data = layer.getGeoResources();
            for( IGeoResource resource : data ) {

                RenderContext context;

                try{
                    if (IMultiLayerRenderer.class
                            .isAssignableFrom(metricsFactory.getRendererType())) {
                        context = new CompositeRenderContextImpl();
                    } else {
                        context = new RenderContextImpl(layer instanceof SelectionLayer);
                    }
                }catch(Throwable e){
                    context = new RenderContextImpl(layer instanceof SelectionLayer);                    
                }
                
                context.setMapInternal(map);
                context.setRenderManagerInternal(rm);
                context.setLayerInternal(layer);
                context.setGeoResourceInternal(resource);
                                
                InternalRenderMetrics metrics = metricsFactory.createMetrics(context);
                String ns = element.getDeclaringExtension().getNamespaceIdentifier();
                metrics.delegate.setId(ns + "." + element.getAttribute("id")); //$NON-NLS-1$ //$NON-NLS-2$)
                rFactories.add(metrics);
            }
        } catch (CoreException e) {
            throw (RuntimeException)new RuntimeException().initCause(e);
        } 
    }

    public List<InternalRenderMetrics> getRFactories() {
        return rFactories;
    }
}
