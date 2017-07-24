package com.reapal.job;

import com.reapal.common.util.SpringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;




public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent event) {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SpringUtils.setDefaultWebApplicationContext(ac);
		
    }

}
