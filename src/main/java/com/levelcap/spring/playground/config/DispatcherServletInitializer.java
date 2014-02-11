package com.levelcap.spring.playground.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Here we gather up all of our various @Configurations and apply them to their
 * appropriate places in our context.
 * 
 * @author Dave Cohen
 * 
 */
public class DispatcherServletInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer
{

    /**
     * Use our security configuration as our root context.
     */
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[] { WebSecurityConfig.class };
    }

    /**
     * Our servlet contexts are defined by WebConfig and WebSocketConfig
     */
    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class<?>[] { WebConfig.class, WebSocketConfig.class };
    }

    /**
     * Servlets map to the root of the deployment.
     */
    @Override
    protected String[] getServletMappings()
    {
        return new String[] { "/" };
    }

    /**
     * Update the DispatcherServlet section so that it dispatches HTTP OPTIONS requests to controllers
     * instead of pushing back the default response that does not include the
     * relevant Access-Control-Allow headers.
     * 
     * Also enables asynchronous operations on the Servlet.
     * 
     */
    @Override
    protected void customizeRegistration(Dynamic registration)
    {
        registration.setInitParameter("dispatchOptionsRequest", "true");
        registration.setAsyncSupported(true);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        super.onStartup(servletContext);
    }

}
