package com.huadong.hdgate.common.filter;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * shiro踢出相同的登录账号
 *
 * @author lit
 *
 */
public class ShiroKickoutSessionFilter extends AccessControlFilter {

	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
		return false;
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {


		return true;
	}

}
