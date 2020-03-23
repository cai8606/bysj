package com.cai.item.config;

import com.cai.item.pojo.User;
import com.cai.item.service.UserService;
import com.cai.item.vo.RolePermsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        log.info("principal:"+principal);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加权限
//        info.addStringPermission("user:add");
        List<RolePermsVo> rolePermsVos = userService.queryPermsByUsername(principal.toString());
        if (!CollectionUtils.isEmpty(rolePermsVos)){
            for (RolePermsVo rolePermsVo : rolePermsVos) {
                if (!StringUtils.isEmpty(rolePermsVo.getFlagName())){
                    info.addStringPermission(rolePermsVo.getFlagName());
                }
            }
        }
        //设置当前用户的权限
        //info.addStringPermission(currentUser.getPerms());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userService.queryByName(userToken.getUsername());
        if (user == null) {
            return null; //返回null则会抛出异常 UnknownAccountException
        }
        //登陆成功放到session里
        //Subject currentSubject = SecurityUtils.getSubject();
        //Session session = currentSubject.getSession();
        //session.setAttribute("loginUser",user);

        //密码认证
        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),"");
    }
}