package com.uptang.cloud.starter.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@WebListener
public class InitializationListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(InitializationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("系统正以超音速启动中 ....");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("系统正依依不舍的和大家告别 ....");
    }
}