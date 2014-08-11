/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.ps.cloudwatch.metrics;

import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.boundless.ps.cloudwatch.aws.MetricDatumEncoder;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom
 */
public class MonitorMetricProvider implements MetricProvider{

    private MetricDatumEncoder encoder;
    
    private static final Logger logger = LoggerFactory.getLogger(MonitorMetricProvider.class);

    
    @Override
    public Collection<MetricDatum> getStatistics() {
        

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
