package cc.doublez.platform.shiro.manager;

import cc.doublez.domain.pojo.user.RolePermission;
import cc.doublez.service.user.IPermissionService;
import cc.doublez.service.user.IRoleService;
import cc.doublez.service.user.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.sun.deploy.config.JREInfo.getAll;

@Service
public class ShiroManager{
	
	// 注意/r/n前不能有空格
	private static final String CRLF = "\r\n";

	public static String loadFilterChainDefinitions() {

		StringBuffer sb = new StringBuffer();
			sb.append(getFixedAuthRule());//固定权限，采用读取配置文件
		return sb.toString();
	}

	/**
	 * 从配额文件获取固定权限验证规则串
	 */
	private static  String getFixedAuthRule(){
		String fileName = "shiro_base_auth.ini";
		ClassPathResource cp = new ClassPathResource(fileName);
		INI4j ini = null;
		try {
			ini = new INI4j(cp.getFile());
		} catch (IOException e) {
			//LoggerUtils.fmtError(getClass(), e, "加载文件出错。file:[%s]", fileName);
		}
		String section = "base_auth";
		Set<String> keys = ini.get(section).keySet();
		StringBuffer sb = new StringBuffer();
		for (String key : keys) {
			String value = ini.get(section, key);
			sb.append(key).append(" = ")
			.append(value).append(CRLF);
		}
		return sb.toString();
	}
	@Resource
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	@Resource
	private IRoleService roleService;
	/**
	 * 初始化权限配置（也可以直接在spring-context-shior.xml中设置）
	 */
	public void initFilterChains(){
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
		} catch (Exception e) {
			throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
		}
		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
				.getFilterChainResolver();
		DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) filterChainResolver
				.getFilterChainManager();
		//清空老的权限配置
		filterChainManager.getFilterChains().clear();
		shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
		//更新数据库中的权限配置（可以传入参数）
		List<RolePermission> result = roleService.getAll();
		if (CollectionUtils.isNotEmpty(result)){
			for (RolePermission rp:result) {
				String url = rp.getPermission();
				String chainDefinition = rp.getRole();
				/*System.out.println(url);
				System.out.println(chainDefinition);*/
				filterChainManager.addToChain(url, "roles",chainDefinition);
			}
		}
/*		String url = "/x3i9ze7w/job*//**";
		filterChainManager.addToChain(url, "roles","admin");
		url = "/x3i9ze7w/user*//**";
		filterChainManager.addToChain(url, "roles","guest");*/
		//保证初始的配置权限
		shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
		Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
		for (Map.Entry<String, String> entry : chains.entrySet()) {
			String url = entry.getKey();
			String chainDefinition = entry.getValue().trim().replace(" ", "");//filter name
			filterChainManager.createChain(url, chainDefinition);
		}
	}

	public static String getSavedUrl(ServletRequest request){
		if (null == WebUtils.getSavedRequest(request)){
			return "/x3i9ze7w/success";
		}else{
			return WebUtils.getSavedRequest(request).getRequestUrl();
		}
	}
}
