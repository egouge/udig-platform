/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.locationtech.udig.project.internal.provider;

import java.util.Collection;

/**
 * This is the original lazy map item provided gerneated from EMF with the super class replaced.
 * <p>
 * This is being preserved for the use of LayersView; but it is our intention to replace it with
 * LazyMapItemProvider (configured with a ChildFetcher for listing layers).
 */
public class LazyMapItemProvider extends MapItemProviderDecorator {
    @Override
    public Collection<?> getChildren(Object object) {
        // MAKE THIS LAZY
        return super.getChildren(object);
    }

}
