package com.sf.ssh.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：web服务器生命周期监听器
 * @author 80002888
 * @date   2018年4月27日
 */
public class MyAppListener implements ServletContextListener{

	/**
	 * servletContext中contextPath的key
	 */
	private static final String CONTEXT_PATH = "ctp";
	
	private static Logger logger = LoggerFactory.getLogger(MyAppListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("MyAppListener...contextInitialized 服务器启动。。。");
		ServletContext servletContext = sce.getServletContext();
		String contextPath = servletContext.getContextPath();
		servletContext.setAttribute(CONTEXT_PATH, contextPath);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("MyAppListener...contextInitialized 服务器销毁。。。");
		ServletContext servletContext = sce.getServletContext();
		servletContext.removeAttribute(CONTEXT_PATH);
	}

}
