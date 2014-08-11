/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.ps.cloudwatch.metrics;

import com.amazonaws.services.cloudwatch.model.MetricDatum;
import java.util.Collection;

/**
 *
 * @author Tom
 */
public interface MetricProvider 
{

    public Collection<MetricDatum> getStatistics() ;
    
}
