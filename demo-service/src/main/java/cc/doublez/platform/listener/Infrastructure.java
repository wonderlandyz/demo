package cc.doublez.platform.listener;

import cc.doublez.platform.job.JobInit;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * 服务启动时加载常用资源
 */
public class Infrastructure {

	private static WebApplicationContext wac = null;

	private static void initialApplicationContext(ServletContext context) throws IllegalStateException {
		if (wac != null) {
			return;
		}
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		if (null != ctx) {
			wac = ctx;
		} else {
			throw new IllegalStateException("Not initial webApplicationContext!");
		}
	}

	public static void initialize(ServletContext context) {
		initialApplicationContext(context);
		//启动时初始化任务
		JobInit.initialJob();
	}

	public static Object getBean(String name) {
		return wac.getBean(name);
	}
}
