package com.amitnehra.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0]; // No root application context configuration
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DispatcherConfig.class}; // Configuration for the Dispatcher Servlet
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; // Map the Dispatcher Servlet to the root URL
    }
}
