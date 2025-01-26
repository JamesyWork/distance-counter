package com.example.distancecounter.common.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger("API");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest httpServletRequest && servletResponse instanceof HttpServletResponse httpServletResponse) {
            val startTime = System.currentTimeMillis();
            CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(httpServletRequest);
            CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(httpServletResponse);

            logger.info("=== START ===");
            logger.info("Request: Method: {}, URI: {}, Payload: {}", requestWrapper.getMethod(), requestWrapper.getRequestURI(), requestWrapper.getBody());

            filterChain.doFilter(requestWrapper, responseWrapper);

            logger.info("Response: Status: {}, Payload: {}", responseWrapper.getStatus(), responseWrapper.getBody());
            logger.info("Time used for execute: {} ms", System.currentTimeMillis() - startTime);
            logger.info("===  END  ===");


            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(responseWrapper.getBody().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

