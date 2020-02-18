package com.huadong.hdgate.systemManagement.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huadong.hdgate.systemManagement.controller.SysController;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.mapper.SysUserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysUserService")
public class SysUserService extends ServiceImpl<SysUserMapper,SysUserEntity> {

	private static final Logger logger = LoggerFactory.getLogger(SysUserService.class);

	/**
	 * 根据登录账号获取该用户信息
	 * @param account 登录账号
	 * @return 用户信息
	 */
	public SysUserEntity queryUserByAccount(String account){
		Wrapper<SysUserEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysUserEntity.ACCOUNT,account);
		return super.selectOne(wrapper);
	}

	/**
	 * 新增或修改用户信息
	 * @param user 用户
	 * @return 是否成功
	 */
	public SysUserEntity saveOrUpdateUser(SysUserEntity user){
		SysUserEntity userEntity = queryUserByAccount(user.getAccount());
		System.out.println("userEnity:"+userEntity);
		if(userEntity == null){
			user.setOrgnId("1");// 默认最上级部门
			addOrUpdateUser(user,"add");
			return user;
		}else{
			userEntity.setUserState(user.getUserState());// 赋值状态，避免后续报错
			userEntity.setUserName(user.getUserName());
			userEntity.setUserPwd(user.getUserPwd());
			addOrUpdateUser(userEntity,"update");
			return userEntity;
		}
	}

	/**
	 * 查询所有用户信息
	 * @return 用户列表
	 */
	public List<SysUserEntity> queryAllUser(){
		Wrapper<SysUserEntity> wrapper = new EntityWrapper<>();
		return super.selectList(wrapper);
	}

	/**
	 * 查询部门所有用户信息
	 * @return 用户列表
	 */
	public List<SysUserEntity> queryUsersByDeptId(String deptId){
		Wrapper<SysUserEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysUserEntity.ORGN_ID,deptId);
		List<SysUserEntity> list = super.selectList(wrapper);
		return list;
	}

	/**
	 * 判断登录是否成功
	 * @param account 账号
	 * @param password 密码
	 * @return 信息
	 */
	public SysUserEntity doLogin(String account, String password) {
		Wrapper<SysUserEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysUserEntity.ACCOUNT,account);
		SysUserEntity user = super.selectOne(wrapper);
		String errMsg = null;
		if (user == null) {
			errMsg = "用户不存在";
		}else{
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), password);
			try {
				currentUser.login(token);
			} catch(UnknownAccountException e){
				System.out.println("UnknownAccountException -->帐号不存在：");
				errMsg = "帐号不存在";
			}catch(IncorrectCredentialsException e){
				System.out.println("IncorrectCredentialsException -- > 密码不正确：");
				errMsg = "密码不正确";
			}catch (Exception e){
				e.printStackTrace();
				errMsg = "其他错误";
			}
		}
		if (errMsg != null) {
			user = new SysUserEntity();
			user.setUserState("error:"+errMsg);
			//throw new RuntimeException(errMsg);// 提示错误，不抛异常，错误显示到前端提示
		}else {
			user.setUserState("success");
			logger.info(user.getUserName()+"用户登录成功");
			SecurityUtils.getSubject().getSession().setAttribute(SysController.SESSION_USER,user);
		}
		return user;
	}

	/**
	 * 从存储过程判断是否登录
	 * @param account 账号
	 * @param password 密码
	 * @return 信息
	 */
	public SysUserEntity doLoginByProcedure(String account, String password) {

		return null;
	}

	/**
	 * 根据部门删除部门下所有用户
	 * @param deptId 部门id
	 */
	public void deleteUserByDept(String deptId){
		Wrapper<SysUserEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysUserEntity.ORGN_ID,deptId);
		super.delete(wrapper);
	}

	/**
	 * 根据账号删除该用户
	 * @param account 账号
	 */
	public void deleteUserByAccount(String account){
		Wrapper<SysUserEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysUserEntity.ACCOUNT,account);
		super.delete(wrapper);
	}

	/**
	 * 添加或修改用户信息
	 * @param userEntity 用户信息
	 * @param flag 标志
	 */
	public void addOrUpdateUser(SysUserEntity userEntity,String flag){
		// 密码进行加密之后在
		ByteSource pwdSalt = ByteSource.Util.bytes(userEntity.getAccount());
		Object obj = new SimpleHash("MD5", userEntity.getUserPwd(), pwdSalt, 1024);
		if("add".equals(flag)){
			userEntity.setUserPwd(obj.toString());
		}else{
			String dbPwd = queryUserByAccount(userEntity.getAccount()).getUserPwd();//数据库中存储的密码
			if(!dbPwd.equals(userEntity.getUserPwd())){ // 密码不一致，修改了密码
				userEntity.setUserPwd(obj.toString());
			}
		}
		super.insertOrUpdate(userEntity);
	}

	/**
	 * 修改用户选择的大机号
	 * @param account 账号
	 */
	public void updateUserSelectedMachines(String account,String lanes){
		SysUserEntity user = queryUserByAccount(account);
		user.setCheckedShowLane(lanes);
		super.insertOrUpdate(user);
	}

}
