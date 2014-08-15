package com.boundless.ps.cloudwatch.metrics;

import com.amazonaws.services.cloudwatch.model.MetricDatum;
import java.util.Collection;

/**
 *
 * @author tingold
 * 
 * Simple interface for classes which wish to provide metrics to Cloudwatch
 */
public interface MetricProvider 
{

    public Collection<MetricDatum> getMetrics() ;
    
}
