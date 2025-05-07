package org.example.reggie.controller.filters;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.ResultViewEntity;
import org.example.reggie.domain.dto.EmployeeDTO;
import org.example.reggie.utils.BaseContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

/**
 * @author 唐三
 * description: 用户登录过滤器
 */
@Slf4j
public class UserLoginFilter implements Filter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private String[] paths;

    /**
     * 初始化匹配地址库
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        paths = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 开发流程
         *    1,获取路径集合
         *    2,路径匹配
         *    3,判断用户是否登录
         *    4,响应未登录信息
         */
        //地址转换
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /**
         * 可放行路径匹配完以后,最终回判断用户是否登录
         */
        String uri = request.getRequestURI();
        //要放行地址,地址匹配
        if (pathMatch(uri,paths)) {
            log.info("过滤器: {},路径成功匹配","UserLoginFilter");
            filterChain.doFilter(request,response);
            return;
        }
        //3,判断用户是否登录
        Long employeeId = (Long) request.getSession().getAttribute("employee");
        if (ObjectUtil.isNotEmpty(employeeId)) {
            log.info("用户登录成功,过滤器: {}","UserLoginFilter");
            //local线程存储
            BaseContext.setCurrentId(employeeId);
            System.out.println("存储:" + BaseContext.getCurrentId());
            filterChain.doFilter(request,response);
            return;
        }

        //匹配失败发送异常信息
        log.info("用户未登录");
        // 5,如果未登录返回登录界面,通过输出流方式向客户端响应数据
        response.getWriter().write(JSON.toJSONString(ResultViewEntity.error("NOTLOGIN")));
    }

    /**
     * 地址匹配
     * @param path
     * @param paths
     * @return
     */
    private boolean pathMatch(String path,String[] paths) {
        for (String s : paths) {
            //如果这个数组的任意地址匹配,直接放行
            if (PATH_MATCHER.match(s,path)) {
                return true;
            }
        }
        return false;
    }

}
