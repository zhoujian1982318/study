package initializer;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import listener.SessionAttrListener;

// this is no use put here, 需要放在jar 文件中
//在jar文件中放入实现ServletContainerInitializer接口的初始化器.
//需要在jar包含META-INF/services/javax.servlet.ServletContainerInitializer文件，文件内容为已经实现ServletContainerInitializer接口的类

public class CustomServletContainerInitializer implements ServletContainerInitializer {
	
	private static Logger logger  = LoggerFactory.getLogger(CustomServletContainerInitializer.class);
	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		logger.debug("enter the customer container initializer onStartup method ");
		//add session attr listener
		SessionAttrListener sal = new SessionAttrListener();
		ctx.addListener(sal);
		
	}
}
