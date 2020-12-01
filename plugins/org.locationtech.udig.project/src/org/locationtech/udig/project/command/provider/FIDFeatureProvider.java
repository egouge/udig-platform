/**
 * 
 */
package org.locationtech.udig.project.command.provider;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.geotools.data.FeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureIterator;
import org.geotools.util.factory.GeoTools;
import org.locationtech.udig.core.IBlockingProvider;
import org.locationtech.udig.core.internal.FeatureUtils;
import org.locationtech.udig.project.ILayer;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.Id;

public class FIDFeatureProvider implements IBlockingProvider<SimpleFeature> {

    private IBlockingProvider<ILayer> layerProvider;
    private String fid;
    private SimpleFeature feature;

    public FIDFeatureProvider( String fid2, IBlockingProvider<ILayer> layer2 ) {
        this.layerProvider = layer2;
        if( fid2 == null ){
            throw new NullPointerException("Fid must not be null"); //$NON-NLS-1$
        }
        this.fid = fid2;
    }

    public synchronized SimpleFeature get( IProgressMonitor monitor, Object... params ) {
        if (feature == null) {
            FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory(GeoTools
                    .getDefaultHints());
            Id fidFilter = filterFactory.id(FeatureUtils.stringToId(filterFactory, fid));
            
            if( monitor == null ) monitor = new NullProgressMonitor();                
            try {
                monitor.beginTask("Get Feature", 100 ); //$NON-NLS-1$
                
                ILayer layer = layerProvider.get( new SubProgressMonitor(monitor, 25) );
                FeatureSource<SimpleFeatureType, SimpleFeature> source = layer
                        .getResource(FeatureSource.class, new SubProgressMonitor(monitor, 25));
                
                monitor.worked(25);
                try(FeatureIterator<SimpleFeature> iter = source.getFeatures(fidFilter).features()){
                    if (iter.hasNext()) {
                        feature = iter.next();
                    }
                    else {
                        // feature not available
                    }
                    monitor.worked(25);
                }
            } catch (IOException e) {
                throw (RuntimeException) new RuntimeException().initCause(e);
            }
            finally {
                monitor.done();
            }
        }
        return feature;
    }

}
