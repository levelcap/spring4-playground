package com.levelcap.spring.playground.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MetricService implements ApplicationListener<BrokerAvailabilityEvent>
{
    private static Log logger = LogFactory.getLog(MetricService.class);
    private final MessageSendingOperations<String> messagingTemplate;
    private final MetricGenerator metricGenerator = new MetricGenerator();
    private AtomicBoolean brokerAvailable = new AtomicBoolean();

    @Autowired
    public MetricService(MessageSendingOperations<String> messagingTemplate)
    {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event)
    {
        this.brokerAvailable.set(event.isBrokerAvailable());
    }

    @Scheduled(fixedDelay = 2000)
    public void sendMetrics()
    {
        for (Metric metric : this.metricGenerator.generateMetrics()) {
            if (logger.isTraceEnabled()) {
                logger.trace("Sending quote " + metric);
            }
            if (this.brokerAvailable.get()) {
                this.messagingTemplate.convertAndSend("/topic/value.metric." + metric.getMetricName(),
                        metric);
            }
        }
    }

    private static class MetricGenerator
    {
        private final Random random = new Random();
        private final Set<Metric> metrics = new HashSet<Metric>();

        public MetricGenerator()
        {
            metrics.add(new Metric("page_load", 10));
            metrics.add(new Metric("concurrent_users", 1));
            metrics.add(new Metric("cache_size", 450));
        }

        public Set<Metric> generateMetrics()
        {
            Iterator<Metric> iterator = metrics.iterator();
            while (iterator.hasNext()) {
                Metric setMetric = iterator.next();
                setMetric.setMetricValue(getNewMetricValue(setMetric.getMetricValue()));
            }
            
            return metrics;
        }

        private long getNewMetricValue(double currentValue)
        {
            return this.random.nextLong();
        }
    }
}
