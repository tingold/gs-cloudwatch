package com.boundless.ps.cloudwatch.metrics;
/**
 * stolen from MapMeter extension
 */
public interface SystemMonitor {

    SystemStatSnapshot pollSystemStatSnapshot();

}
