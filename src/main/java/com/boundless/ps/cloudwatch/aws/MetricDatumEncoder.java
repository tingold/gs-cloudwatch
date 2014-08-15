

package com.boundless.ps.cloudwatch.aws;

import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.util.EC2MetadataUtils;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tingold@boundlessgeo.com
 */
public class MetricDatumEncoder 
{

    protected String amiId = "";
    protected String instanceType = "";
    protected String instanceId = "";
    
    protected static final Logger logger = LoggerFactory.getLogger(MetricDatumEncoder.class);
    protected boolean setup = false;
 
  
    private void setUp() 
    {
      try
        {
            amiId = EC2MetadataUtils.getAmiId();            
            logger.info("Detected AMI ID of {}", amiId);
            instanceType = EC2MetadataUtils.getInstanceType();
            logger.info("Detected Instance Type = {}", instanceType);
            instanceId = EC2MetadataUtils.getInstanceId();
            logger.info("Detected Instance Id of {}", instanceId);
            setup = true;
        
        }
        catch(Exception ex)
        {
            logger.error("Unable to determine AMI ID or Instance type...are we on AWS?");
            logger.error(ex.getMessage());
            //only try this once....the strings can just be empty if it fails. 
            //the sender will catch it anyways
            setup = true;
            
        }
        
    }
    
    public synchronized MetricDatum encodeDatum(String name,Double value, UOM uom)
    {
        if(!setup)
        {
            setUp();
        }
    
         MetricDatum memoryMD = new MetricDatum().withDimensions(
                 new Dimension().withName("InstanceType").withValue(instanceType), 
                 new Dimension().withName("InstanceID").withValue(instanceId))
          .withMetricName(name).withTimestamp(new Date()).withUnit(uom.getUnit()).withValue(value);
                
         return memoryMD;        
    }
    
    
    
    
    public enum UOM
    {
        Seconds("Seconds"),
        Microseconds("Microseconds"),
        Milliseconds("Milliseconds"),
        Bytes("Bytes"),
        Kilobytes("Kilobytes"),
        Megabytes("Megabytes"),
        Gigabytes("Gigabytes"),
        Terabytes("Terabytes"),
        Bits("Bits"),
        Kilobits("Kilobits"),
        Megabits("Megabits"),
        Gigabits("Gigabits"),
        Terabits("Terabits"),
        Percent("Percent"),
        Count("Count"),
        Bytes_Second("Bytes/Second"),
        Kilobytes_Second("Kilobytes/Second"),
        Megabytes_Second("Megabytes/Second"),
        Gigabytes_Second("Gigabytes/Second"),
        Terabytes_Second("Terabytes/Second"),
        Bits_Second("Bits/Second"),
        Kilobits_Second("Kilobits/Second"),
        Megabits_Second("Megabits/Second"),
        Gigabits_Second("Gigabits/Second"),
        Terabits_Second("Terabits/Second"),
        Count_Second("Count/Second"),
        None("None");
        
        private final String unit;
        private UOM(String n)
        {
            unit = n;
        }
        public String getUnit(){return unit;}
    }
  
    
    
}
