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
package org.locationtech.udig.project.internal.provider;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Placeholder that will show up in viewers while the contentProvider is loading the data.
 * 
 * @author Jesse
 * @since 1.1.0
 */
public interface LoadingPlaceHolder {
    String getText();
    ImageDescriptor getImage();
}
