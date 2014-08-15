package com.boundless.ps.cloudwatch.metrics;

import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.boundless.ps.cloudwatch.aws.MetricDatumEncoder;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tingold@boundlessgeo.com
 */
public class MonitorMetricProvider implements MetricProvider{

    private MetricDatumEncoder encoder;
    
    private static final Logger logger = LoggerFactory.getLogger(MonitorMetricProvider.class);

    
    @Override
    public Collection<MetricDatum> getMetrics() {
        

        return new ArrayList<MetricDatum>();
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
