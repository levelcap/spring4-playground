package com.levelcap.spring.playground.service;

public class Metric
{
    private final String metricName;
    private long metricValue;

    public Metric(String metricName, long metricValue)
    {
        this.metricName = metricName;
        this.metricValue = metricValue;
    }

    public String getMetricName()
    {
        return this.metricName;
    }

    public long getMetricValue()
    {
        return this.metricValue;
    }
    
    public void setMetricValue(long metricValue) {
        this.metricValue = metricValue;
    }

    @Override
    public String toString()
    {
        return "Metric [metricName=" + this.metricName + ", this.metricValue=" + this.metricValue + "]";
    }
}
