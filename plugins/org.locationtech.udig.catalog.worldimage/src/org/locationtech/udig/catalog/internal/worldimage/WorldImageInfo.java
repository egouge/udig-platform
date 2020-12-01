/**
 * 
 */
package org.locationtech.udig.catalog.internal.worldimage;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.locationtech.udig.catalog.rasterings.AbstractRasterGeoResource;
import org.locationtech.udig.catalog.rasterings.AbstractRasterGeoResourceInfo;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

final class WorldImageInfo extends
		AbstractRasterGeoResourceInfo {
	private final CoordinateReferenceSystem crs;

	WorldImageInfo(
			AbstractRasterGeoResource resource, CoordinateReferenceSystem crs) {
		super(resource, "WorldImage", "world image", ".gif", ".jpg", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				".jpeg", ".tif", ".tiff", ".png"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		this.crs = crs;
	}

	@Override
	public CoordinateReferenceSystem getCRS() {
		return crs;
	}
	
	@Override
	public synchronized ReferencedEnvelope getBounds() {
		ReferencedEnvelope b = super.getBounds();
		// the bounds gets the projection information from the coverage which defaults to WGS84 if it doesn't know
		// the projection.  So we get the bounds and set the CRS read from the prj file
		return new ReferencedEnvelope(b, crs);
	}
}
