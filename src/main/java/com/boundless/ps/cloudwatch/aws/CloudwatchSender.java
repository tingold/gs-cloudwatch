/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.ps.cloudwatch.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClient;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.boundless.ps.cloudwatch.metrics.MetricProvider;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom
 */

public class CloudwatchSender {
    
    
    protected AmazonCloudWatchAsyncClient cloudwatch;
    
    protected boolean enabled = false;
    
    private static final Logger logger = LoggerFactory.getLogger(CloudwatchSender.class);
    
    private List<MetricProvider> providers;
    
    public CloudwatchSender()
    {
        try
        {         
            cloudwatch = new AmazonCloudWatchAsyncClient();      
            enabled = true;
            logger.info("Initialized AWS Client");
        }
        catch(Exception ex)
        {
            logger.error("Error initializing AWS Client!");
            logger.error(ex.getMessage());
        }
    }    
            

    public void sendAllMetrics()
    {
        
        if(!enabled)
        {
            logger.debug("Metrics are disabled...returning");
            return;
        }
        logger.debug("Sending all metrics");
        for(MetricProvider mp: providers)
        {
            
            for(MetricDatum md: mp.getStatistics())
            {
                try{
                    logger.debug("Sending statistic {}", md.getMetricName());
                    PutMetricDataRequest request = new PutMetricDataRequest().withNamespace("geoserver").withMetricData(md);
                    cloudwatch.putMetricDataAsync(request);               
                    logger.debug("Sending statistic {}", md.getMetricName());
                }
                catch(AmazonClientException ex)
                {
                    logger.warn("Erro sending AWS metric {}", md.getMetricName());
                }
            }                            
        }
        
    
    }
    
    protected void sendMetric(MetricDatum metric){
    
    PutMetricDataRequest request = new PutMetricDataRequest()
        .withNamespace("AWS/EC2").withMetricData(metric);
    
    
    cloudwatch.putMetricData(request);
    }

    /**
     * @return the providers
     */
    public List<MetricProvider> getProviders() {
        return providers;
    }

    /**
     * @param providers the providers to set
     */
    public void setProviders(List<MetricProvider> providers) {
        this.providers = providers;
    }

   
}
