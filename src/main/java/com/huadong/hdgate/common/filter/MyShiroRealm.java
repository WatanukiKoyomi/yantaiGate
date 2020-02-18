package com.huadong.hdgate.common.filter;

import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * shiro配置
 * @author lit
 *
 */
public class MyShiroRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	@Resource
	private SysUserService sysUserService;

	public MyShiroRealm(){
		super();
	}
	/**
	 * 用户授权认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("======用户授权认证======");
		System.out.println("======用户授权认证======");
		SysUserEntity wtUser = (SysUserEntity)principals.getPrimaryPrincipal();
		System.out.println("----------"+wtUser);

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//authorizationInfo.setRoles(WtUser.getRoles());
		//authorizationInfo.addStringPermissions(WtUser.getUrlSet());
		return authorizationInfo;
	}

	/**
     * 用户登陆认证
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		logger.info("======Shiro开始登录认证======");
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		//获取登录用户名
		String username = token.getUsername();
		String password = String.valueOf(token.getPassword());
		SysUserEntity wtUser = sysUserService.queryUserByAccount(username);
		if (wtUser==null) {
			throw new UnknownAccountException();// 没找到账户
		}
		// 盐值：取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
		ByteSource pwdSalt = ByteSource.Util.bytes(username);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(wtUser, // 用户对象
				wtUser.getUserPwd(), // 密码
				pwdSalt,
				getName() // realm name
		);
		return authenticationInfo;
	}


	@Override
	public void onLogout(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
		System.out.println("shiro onLogout!!!");
		SysUserEntity shiroUser = (SysUserEntity) principals.getPrimaryPrincipal();
		removeUserCache(shiroUser.getAccount());
	}

	/**
	 * 清除用户缓存
	 * @param loginName
	 */
	public void removeUserCache(String loginName){
		SimplePrincipalCollection principals = new SimplePrincipalCollection();
		principals.add(loginName, super.getName());
		super.clearCachedAuthenticationInfo(principals);
	}

}
