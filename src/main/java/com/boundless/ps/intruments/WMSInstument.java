
package com.boundless.ps.intruments;

import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.boundless.ps.cloudwatch.aws.MetricDatumEncoder;
import com.boundless.ps.cloudwatch.aws.MetricDatumEncoder.UOM;
import com.boundless.ps.cloudwatch.metrics.MetricProvider;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tingold@boundlessgeo.com
 * 
 * This class intercepts and counts WMS requests and errors
 */
public class WMSInstument implements MethodInterceptor, MetricProvider{

    private MetricRegistry metricRegistry;

    private Meter wmsRequestMeter;
    private Meter owsRequestMeter;
    private Meter wmsErrorMeter;
    private Meter owsErrorMeter;
    
        
    private MetricDatumEncoder encoder;
    
    private static final Logger logger = LoggerFactory.getLogger(WMSInstument.class);
    
       
    public void afterPropertiesSet()
    {
        wmsRequestMeter = metricRegistry.meter("geoserver.ows.wms.requests");
        owsRequestMeter = metricRegistry.meter("geoserver.ows.requests");
        owsErrorMeter = metricRegistry.meter("geoserver.ows.errors");
        wmsErrorMeter = metricRegistry.meter("geoserver.ows.wms.errors");
    }
    
    
    
    
    @Override
    public Collection<MetricDatum> getMetrics() 
    {
        List<MetricDatum> metrics = new ArrayList<>();
        
        metrics.add(encoder.encodeDatum("geoserver.ows.wms.requests",wmsRequestMeter.getOneMinuteRate(), UOM.Count_Second));
        metrics.add(encoder.encodeDatum("geoserver.ows.requests",owsRequestMeter.getOneMinuteRate(), UOM.Count_Second));
        metrics.add(encoder.encodeDatum("geoserver.ows.wms.errors",wmsErrorMeter.getOneMinuteRate(), UOM.Count_Second));
        metrics.add(encoder.encodeDatum("geoserver.ows.errors",owsErrorMeter.getOneMinuteRate(), UOM.Count_Second));
                
        return Collections.unmodifiableCollection(metrics);
    }
    
    
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable 
    {        
        logger.debug("Counting WMS request");
        wmsRequestMeter.mark();
        owsRequestMeter.mark();
        try
        {
            return mi.proceed();            
        }
        catch(Exception ex)
        {
            logger.debug("Counting WMS error");
            owsErrorMeter.mark();
            wmsErrorMeter.mark();
            throw ex;
        }
    }

    /**
     * @return the requests
     */
    public MetricRegistry getRequests() {
        return metricRegistry;
    }

    /**
     * @param requests the requests to set
     */
    public void setMetricRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    /**
     * @return the encoder
     */
    public MetricDatumEncoder getEncoder() {
        return encoder;
    }

    /**
     * @param encoder the encoder to set
     */
    public void setEncoder(MetricDatumEncoder encoder) {
        this.encoder = encoder;
    }

}
