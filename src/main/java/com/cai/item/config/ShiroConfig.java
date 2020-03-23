package com.cai.item.config;

import com.cai.item.mapper.PermsMapper;
import com.cai.item.pojo.Perms;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Autowired
    private PermsMapper permsMapper;

    //ShiroFilterFactoryBean:第三步
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加Shiro的内置过滤器
        /**
            anon:无需认证就可以访问
            authc:必须认证才能访问
            user:必须拥有 记住我 功能才能用
            perms:拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问
         */
        //拦截
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        //授权，未授权则会跳转到未授权页面
        //filterChainDefinitionMap.put("/user/update", "perms[update]");//意思为只有带着update的用户才可以访问
        filterChainDefinitionMap.put("/login", "anon");
//        filterChainDefinitionMap.put("/**","authc");//登录认证权限

        //动态查询所有资源需要的权限
        List<Perms> permsList = permsMapper.selectAll();
        if (!CollectionUtils.isEmpty(permsList)){
            for (Perms perms : permsList) {
                filterChainDefinitionMap.put(perms.getUrl(),"perms["+perms.getFlagName()+"]");
            }
        }
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        //设置自定义权限认证 filter
        Map<String, Filter> filterMap = new HashMap<>();
        //filterMap.put("authc",new MyHttpAuthenticationFilter());
        filterMap.put("perms",new MyUserAuthFilter());
        bean.setFilters(filterMap);

        //设置登陆的请求，如果没有认证的话
        //bean.setLoginUrl("/toLogin");
        //设置未授权页面
        //bean.setUnauthorizedUrl("/noAuth");


        return bean;
    }

    //DafaultWebSecurityManager:第二步
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建 realm对象，需要自定义:第一步
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }


}