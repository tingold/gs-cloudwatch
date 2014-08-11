/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.ps.cloudwatch.metrics;

import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.boundless.ps.cloudwatch.aws.MetricDatumEncoder;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom
 */
public class JREMetrics implements MetricProvider{

    private final String JRE_HEAP_PCT = "geoserver-jre-heap-percent-used-memory";
    
    private static final Logger logger = LoggerFactory.getLogger("JREStats");
    
    private MetricDatumEncoder encoder;
    
    public JREMetrics()
    {
    
        
        
    }    
    
    
    @Override
    public Collection<MetricDatum> getStatistics() 
    {
    
        List<MetricDatum> jreStats = new ArrayList<>();

        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        
        double pctUsed = memBean.getHeapMemoryUsage().getUsed()/memBean.getHeapMemoryUsage().getMax();
        
       
        jreStats.add(encoder.encodeDatum(JRE_HEAP_PCT, pctUsed, MetricDatumEncoder.UOM.Percent));
                
        
        return Collections.synchronizedList(jreStats);
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
