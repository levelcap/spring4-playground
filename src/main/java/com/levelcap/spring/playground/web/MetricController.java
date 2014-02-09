package com.levelcap.spring.playground.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.levelcap.spring.playground.service.Metric;

@Controller
public class MetricController
{
    private static final Log logger = LogFactory.getLog(MetricController.class);

    public MetricController()
    {
    }

    @SubscribeMapping("/metrics")
    public List<Metric> getMetrics(Principal principal) throws Exception
    {
        logger.debug("Returning a hardcoded list of metrics, logged in as: " + principal.getName());
        List<Metric> metrics = new ArrayList<Metric>();
        metrics.add(new Metric("cache_size", 10000));
        metrics.add(new Metric("page_load", 10000));
        return metrics;
    }
    
    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception)
    {
        return exception.getMessage();
    }

}
