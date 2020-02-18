package com.huadong.hdgate.systemManagement.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huadong.hdgate.systemManagement.entity.SysDeptEntity;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.mapper.SysDeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("sysDeptService")
public class SysDeptService extends ServiceImpl<SysDeptMapper,SysDeptEntity> {

	private static final Logger logger = LoggerFactory.getLogger(SysDeptService.class);

	@Resource
	private SysDeptMapper sysDeptMapper;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 根据登录账号获取该用户所在部门及其子部门
	 * @param account 登录账号
	 * @return 部门信息
	 */
	public List<SysDeptEntity> queryDeptsByAccount(String account){
		SysUserEntity user = sysUserService.queryUserByAccount(account);
		List<SysDeptEntity> allDepts = queryAllDepts();
		List<SysDeptEntity> userDeptList = new ArrayList<>();
		userDeptList = queryUserDepts(user.getOrgnId(),allDepts,userDeptList);
		logger.info("用户：" + user + ",获取到的部门:" + userDeptList);
		return userDeptList;
	}

	/**
	 *
	 * @param deptId 部门id
	 * @return 部门信息
	 */
	public SysDeptEntity queryDeptBypDeptId(String deptId){
		Wrapper<SysDeptEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysDeptEntity.DEPT_ID,deptId);
		SysDeptEntity dept = super.selectOne(wrapper);
		return dept;
	}


	/**
	 * 获取用户所在部门及其子部门
	 * @return 部门
	 */
	private List<SysDeptEntity> queryUserDepts(String deptId, List<SysDeptEntity> list, List<SysDeptEntity> finalDeptList){
		if(list.size()<1){
			return finalDeptList;
		}
		List<SysDeptEntity> childList = new ArrayList<>();
		for (SysDeptEntity dept : list){
			if(dept.getDeptId().equals(deptId)){
				// 获取到结果返回
				finalDeptList.add(dept);
				return finalDeptList;
			}
			if(dept.getChildren() != null){
				childList.addAll(dept.getChildren());// 将所有的子级部门加入
			}
		}
		if(finalDeptList.size()==0){ // 没有取到对应的部门信息，且存在子部门，继续深层查找
			queryUserDepts(deptId,childList,finalDeptList);
		}
		return finalDeptList;
	}

	/**
	 * 获取所有部门信息
	 * @return 部门
	 */
	private List<SysDeptEntity> queryAllDepts(){
		Wrapper<SysDeptEntity> wrapper = new EntityWrapper<>();
		List<SysDeptEntity> list = super.selectList(wrapper);
		// sysDeptMapper.queryDeptsByAccount(account);
		//存放根
		List<SysDeptEntity> rootDeptList = new ArrayList<>();
		//存放子
		List<SysDeptEntity> childDeptList = new ArrayList<>();
		for (SysDeptEntity dept : list) {
			String parentDeptId = dept.getParentDeptId();
			if(parentDeptId==null||"".equals(parentDeptId)||"0".equals(parentDeptId)){
				rootDeptList.add(dept);//是根加入list中
			}else{
				childDeptList.add(dept);
			}
		}
		//递归添加菜单
		for (SysDeptEntity dept : rootDeptList) {
			dept.setChildren(getChild(dept.getDeptId()+"",childDeptList));
		}
		return rootDeptList;
	}
	/**
	 * 递归查找子菜单
	 * @param id 当前菜单id
	 * @param menuList 要查找的列表
	 * @return 权限列表
	 */
	private List<SysDeptEntity> getChild(String id, List<SysDeptEntity> menuList) {
		// 子菜单
		List<SysDeptEntity> childList = new ArrayList<>();
		for (SysDeptEntity menu : menuList) {
			String menuId = menu.getParentDeptId();//该子菜单对应的父菜单id
			if(id.equals(menuId)){
				childList.add(menu);
			}
		}
		menuList.removeAll(childList);//去掉子菜单中属于父一级的菜单
		if (childList.size() == 0) {
			return null;
		}
		// 把子菜单的子菜单再循环一遍
		for (SysDeptEntity menu : childList) {
			String hasChild = menu.getHasChild();//值不为空或空字符串说明存在下一级菜单
			if("1".equals(hasChild)){
				menu.setChildren(getChild(menu.getDeptId()+"", menuList));
			}
		}
		return childList;
	}

	/**
	 * 添加或修改部门信息
	 * @param deptEntity 部门
	 */
	public void addOrUpdateDept(SysDeptEntity deptEntity){
		super.insertOrUpdate(deptEntity);
	}

	/**
	 * 根据传入的当前级别部门信息删除部门内容，并删除部门下的所有用户
	 * @param parentDept 部门信息
	 */
	public void deleteDept(SysDeptEntity parentDept){
		Wrapper<SysDeptEntity> deleteWrapper = new EntityWrapper<>();
		deleteWrapper.eq(SysDeptEntity.DEPT_ID,parentDept.getDeptId());
		super.delete(deleteWrapper);// 删除上级部门
		sysUserService.deleteUserByDept(parentDept.getDeptId());//删除上级部门下用户

		// 判断父级部门是否存在子部门了，如果不存在更改父部门的是否有子部门更改为没有
		Wrapper<SysDeptEntity> selectWrapper = new EntityWrapper<>();
		selectWrapper.eq(SysDeptEntity.PARENT_DEPT_ID,parentDept.getParentDeptId());
		List<SysDeptEntity> deptList = super.selectList(selectWrapper);

		if(deptList.size()<1){
			Wrapper<SysDeptEntity> updateWrapper = new EntityWrapper<>();
			updateWrapper.eq(SysDeptEntity.DEPT_ID,parentDept.getParentDeptId());
			SysDeptEntity parentFatherDept = super.selectOne(updateWrapper);
			parentFatherDept.setHasChild("");
			super.updateById(parentFatherDept);
		}
		//根据传值，嵌套删除部门信息
		getDeptChild(parentDept.getChildren());
	}

	/**
	 * 递归查找子菜单，用于删除部门操作,与上个区别是，上面是直接数据库查询的List，这个是递归过后的树状结构的List
	 * @param menuList 树状结构的部门信息
	 */
	private void getDeptChild(List<SysDeptEntity> menuList) {
		for (SysDeptEntity childDept : menuList) {
			//先删除，然后判断是否有子部门，有嵌套删除
			Wrapper<SysDeptEntity> deptWrapper = new EntityWrapper<>();
			deptWrapper.eq(SysDeptEntity.DEPT_ID,childDept.getDeptId());
			super.delete(deptWrapper);// 删除上级部门
			sysUserService.deleteUserByDept(childDept.getDeptId());
			//gateDeptMapper.deleteDept(childDept.getDeptId());
			//gateUserMapper.deleteUserByDept(childDept.getDeptId());
			if(childDept.getChildren() != null){
				getDeptChild(childDept.getChildren());
			}
		}
	}
}
