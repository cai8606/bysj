package com.cai.item.config;

import com.alibaba.fastjson.JSON;
import com.cai.item.vo.Result;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyUserAuthFilter extends AuthorizationFilter {
    /**
     * 当isAccessAllowed方法返回false时执行
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        System.out.println("=======执行了onAccessDenied");
        Subject subject = this.getSubject(request, response);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        if (subject.getPrincipal() == null) {
            //没有登录的情况
            Result result = new Result(2001,"未登录");
            writer.println(JSON.toJSONString(result));
        } else {
            //没有权限
            Result result = new Result(403,"您没有该资源的访问权限");
            writer.println(JSON.toJSONString(result));
        }
        writer.flush();
        writer.close();
        return false;
    }


    /**
     * 判断是否有该资源的访问权限,如果有返回true,没有返回false
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        System.out.println("=====isAccessAllowed");
        String perms[] = (String [])o;

        boolean isAllowed = true;
        Subject subject = this.getSubject(servletRequest, servletResponse);
        if(perms!=null&&perms.length>0){
            if(perms.length==1){
                if(!subject.isPermitted(perms[0])){
                    isAllowed=false;
                }
            }else{
                if(!subject.isPermittedAll(perms)){
                    isAllowed=false;
                }
            }
        }
        System.out.println("isperms:"+isAllowed);
        return isAllowed;
    }
}
