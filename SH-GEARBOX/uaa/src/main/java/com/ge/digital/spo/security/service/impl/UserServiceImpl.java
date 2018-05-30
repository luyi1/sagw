
package com.ge.digital.spo.security.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ge.digital.spo.chain.infrastructure.user.dao.RoleMapper;
import com.ge.digital.spo.chain.infrastructure.user.dao.UserMapper;
import com.ge.digital.spo.chain.infrastructure.user.dao.UserRoleMapper;
import com.ge.digital.spo.chain.infrastructure.user.model.Role;
import com.ge.digital.spo.chain.infrastructure.user.model.User;
import com.ge.digital.spo.chain.infrastructure.user.model.UserRole;
import com.ge.digital.spo.chain.infrastructure.user.vo.RoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserByRoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserBylistRoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserRoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserVo;
import com.ge.digital.spo.common.util.ValidationUtil;
import com.ge.digital.spo.controller.common.RestResponseCode;
import com.ge.digital.spo.security.exception.BizException;
import com.ge.digital.spo.security.service.AuthValidatorService;
import com.ge.digital.spo.security.service.UserService;
import com.ge.digital.spo.security.util.Encrypt;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	AuthValidatorService authValidatorService;
	@Autowired
	UserMapper userMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	@Override
	public int deleteByPrimaryKey(String id) {
		
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User record) {
		if(record.getPassword()!=null)
		{
			record.setPassword(Encrypt.md5(record.getPassword()));
		}
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(User record) {
		if(record.getPassword()!=null)
		{
			record.setPassword(Encrypt.md5(record.getPassword()));
		}
		return userMapper.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(String id) {
		
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		if(record.getPassword()!=null)
		{
			record.setPassword(Encrypt.md5(record.getPassword()));
		}
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		if(record.getPassword()!=null)
		{
			record.setPassword(Encrypt.md5(record.getPassword()));
		}
		return userMapper.updateByPrimaryKey(record);
	}
	@Override
	public List<UserVo> findByUserVo(UserVo vo) {
		return userMapper.findByUserVo(vo);
	}

	@Override
	public RestResponseCode saveUser(User entity) {
		int returnInt = insert(entity);
		if(returnInt > 0){
			return RestResponseCode.OK;
		}
		return RestResponseCode.SAVE_USER_ERROR;
	}

	@Override
	public RestResponseCode deleteUser(User entity) {
		String userId = entity.getId();
		if(StringUtils.isEmpty(userId)){
			return RestResponseCode.DELETE_USER_ERROR;
		}
		int deleteByUserKeyint =  userRoleMapper.deleteByUserKey(userId);
		int returnInt = deleteByPrimaryKey(userId);
		if(returnInt > 0 && deleteByUserKeyint > 0 ){
			return RestResponseCode.OK;
		}
		return RestResponseCode.DELETE_USER_ERROR;
	}

	@Override
	public RestResponseCode updateUser(User entity) {
		int returnInt = updateByPrimaryKeySelective(entity);
		if(returnInt > 0){
			return RestResponseCode.OK;
		}
		return RestResponseCode.UPDATE_USER_ERROR;
	}

	@Override
	public List<UserVo> findUser(UserVo vo) {
		 List<UserVo> listVo = findByUserVo( vo);
//		int returnInt = insert(entity);
//		if(returnInt > 0){
//			return RestResponseCode.OK;
//		}
		return listVo;
	}

	@Override
	public List<RoleVo> findByRoleVo(RoleVo vo) {
		return roleMapper.findByRoleVo(vo);
	}

	@Override
	public List<UserByRoleVo> findByUserByRoleVo(UserByRoleVo vo) {
		return userMapper.findByUserByRoleVo(vo);
	}

	@Override
	@Transactional
	public RestResponseCode saveByUpdateRelUserRole(UserByRoleVo vo) {
		RestResponseCode restResponseCode = RestResponseCode.OK;
		if (StringUtils.isEmpty(vo.getId())) {
			restResponseCode = saveRelUserRole(vo);
		} else {
			restResponseCode = updateRelUserRole(vo);
		} 
		return restResponseCode;
	}

	private RestResponseCode updateRelUserRole(UserByRoleVo vo) {
		RestResponseCode restResponseCode = RestResponseCode.OK;
		UserVo userVo = new UserVo();
		userVo.setId(vo.getId());
		List<UserVo> listUserVo = userMapper.findByUserVo(userVo);
		if(listUserVo.isEmpty()){
			//异常处理
			throw new BizException(RestResponseCode.NO_USER_INFORMATION_WAS_FOUND);
		}
		UserVo userVoDto = listUserVo.get(0);
		userVoDto.setId(vo.getId());
		userVoDto.setLoginId(vo.getLoginId());
		if(!userVoDto.getPassword().equals(vo.getPassword())){
			userVoDto.setPassword(Encrypt.md5(vo.getPassword()));
		}
		userVoDto.setLastName(vo.getLastName());
		userVoDto.setFirstName(vo.getFirstName());
		userVoDto.setIsactive(vo.getIsactive());
		userVoDto.setDefaultNetGrp(vo.getDefaultNetGrp());
		userVoDto.setLandingPage(vo.getLandingPage());
		userVoDto.setLastUpdateOn(new Date());
		userVoDto.setLocale(vo.getLocale());
		
		String userVoDtoJson = JSON.toJSONString(userVoDto);
		User user = JSON.parseObject(userVoDtoJson,User.class);
		int insertbo = userMapper.updateByPrimaryKeySelective(user);
		if(insertbo < 0){
			//异常处理
			throw new BizException(RestResponseCode.USER_DATA_UPDATE_FAILED);
		}
		String roleIdArrStr = vo.getRoleIdArrStr();
		if(!StringUtils.isEmpty(roleIdArrStr)){
		//清role数据
//		UserRoleVo findUserRoleVo = new UserRoleVo();
//		findUserRoleVo.setUserId(vo.getId());
//		List<UserRoleVo> listUserByRoleVo = userRoleMapper.findByUserRoleVo(findUserRoleVo);

		//加role数据
		UserVo findUserVo = new UserVo();
		findUserVo.setId(vo.getId());
		List<UserVo> findListUserVo = userMapper.findByUserVo(findUserVo);
		if(findListUserVo.isEmpty()){
			//异常处理
			throw new BizException(RestResponseCode.NO_USER_INFORMATION_WAS_FOUND);
		}
		UserVo findUserVoDto = findListUserVo.get(0);
		
//		if(listUserByRoleVo.isEmpty()){
//			//异常处理
//			//throw new BizException(RestResponseCode.USER_ROLE_DATA_IS_NOT_FOUND);
//			//jesse 加角色
//			、。。
//			
//		}
		UserRoleVo userRoleVo = new UserRoleVo();
		userRoleVo.setUserId(vo.getId());
		int deleteByRoleVoCode = userRoleMapper.deleteByRoleVo(userRoleVo);
		if(deleteByRoleVoCode < 0){
			//异常处理
			throw new BizException(RestResponseCode.REFRESH_THE_USER_ROLE_DATA_FAILURE);
		}
		userRoleMapper.deleteByPrimaryKey(findUserVoDto.getId());
		
		updateRoles( roleIdArrStr, findUserVoDto);
		
//			roleIdArrStr.trim();
//			roleIdArrStr.substring(0, roleIdArrStr.length()-1);
//			String[] roleArr = roleIdArrStr.split(",");
//			if(roleArr.length > 0){
//				for (int i = 0; i < roleArr.length; i++) {
//					UserRole userRole = new UserRole();
//					userRole.setUserId(findUserVoDto.getId());
//					
//					RoleVo roleVo = new RoleVo();
//					roleVo.setRoleName(roleArr[i]);
//					List<RoleVo> listRoleVo = roleMapper.findByRoleVo(roleVo);
//					if(listRoleVo.isEmpty()){
//						throw new BizException(RestResponseCode.NO_USER_INFORMATION_WAS_FOUND);
//					}
//					RoleVo pamRoleVo = listRoleVo.get(0);
//					
//					userRole.setRoleId(pamRoleVo.getRoleId());
//					userRole.setCreateOn(new Date());
//					int insertCode = userRoleMapper.insert(userRole);
//					if(insertCode < 0){
//						//异常处理
//						throw new BizException(RestResponseCode.USER_ROLE_RELATIONSHIP_INSERTION_FAILURE);
//					}
//				}
//			}
		}
		return restResponseCode;
	}

	private void updateRoles(String roleIdArrStr, UserVo findUserVoDto) {
		roleIdArrStr.trim();
		roleIdArrStr.substring(0, roleIdArrStr.length()-1);
		String[] roleArr = roleIdArrStr.split(",");
		if(roleArr.length > 0){
			for (int i = 0; i < roleArr.length; i++) {
				UserRole userRole = new UserRole();
				userRole.setUserId(findUserVoDto.getId());
				
				RoleVo roleVo = new RoleVo();
				roleVo.setRoleName(roleArr[i]);
				List<RoleVo> listRoleVo = roleMapper.findByRoleVo(roleVo);
				if(listRoleVo.isEmpty()){
					throw new BizException(RestResponseCode.NO_USER_INFORMATION_WAS_FOUND);
				}
				RoleVo pamRoleVo = listRoleVo.get(0);
				
				userRole.setRoleId(pamRoleVo.getRoleId());
				userRole.setCreateOn(new Date());
				int insertCode = userRoleMapper.insert(userRole);
				if(insertCode < 0){
					//异常处理
					throw new BizException(RestResponseCode.USER_ROLE_RELATIONSHIP_INSERTION_FAILURE);
				}
			}
		}
		
	}

	private RestResponseCode saveRelUserRole(UserByRoleVo vo) {
		RestResponseCode restResponseCode = RestResponseCode.OK;
		Boolean testSameLoginIdBo = authValidatorService.testbUIsSameLoginId(vo);
		if(testSameLoginIdBo){
			//重复验证
			return RestResponseCode.TEST_SAME_LOGINID;
		}
		User user = new User();
		user.setLoginId(vo.getLoginId());
		user.setPassword(Encrypt.md5(vo.getPassword()));
		user.setLastName(vo.getLastName());
		user.setFirstName(vo.getFirstName());
		user.setIsactive(true);
		user.setLandingPage(vo.getLandingPage());
		user.setDefaultNetGrp(vo.getDefaultNetGrp());
		user.setCreateOn(new Date());
		user.setLocale(vo.getLocale());
		int insertbo = userMapper.insert(user);
		if(insertbo < 0){
			//new异常
			return RestResponseCode.USER_INSERT_ERROR;
		}
		
		UserVo findUserVo = new UserVo();
		findUserVo.setLoginId(vo.getLoginId());
		List<UserVo> findListUserVo = userMapper.findByUserVo(findUserVo);
		if(findListUserVo.isEmpty()){
			//异常处理
			throw new BizException(RestResponseCode.NO_USER_INFORMATION_WAS_FOUND);
		}
		UserVo findUserVoDto = findListUserVo.get(0);
		String roleIdArrStr = vo.getRoleIdArrStr().trim();
		roleIdArrStr.substring(0, roleIdArrStr.length()-1);
		String[] roleArr = roleIdArrStr.split(",");
		if(roleArr.length > 0){
			for (int i = 0; i < roleArr.length; i++) {
				UserRole userRole = new UserRole();
				userRole.setUserId(findUserVoDto.getId());
				
				RoleVo roleVo = new RoleVo();
				roleVo.setRoleName(roleArr[i]);
				List<RoleVo> listRoleVo = roleMapper.findByRoleVo(roleVo);
				if(listRoleVo.isEmpty()){
					throw new BizException(RestResponseCode.FIND_THE_ROLE_IS_EMPTY);
				}
				RoleVo pamRoleVo = listRoleVo.get(0);
				
				userRole.setRoleId(pamRoleVo.getRoleId());
				userRole.setCreateOn(new Date());
				int insetInt = userRoleMapper.insert(userRole);
				if(insetInt < 0){
					//异常处理
					throw new BizException(RestResponseCode.USER_ROLE_RELATIONSHIP_INSERTION_FAILURE);
				}
			}
		}
		return restResponseCode;
	}

	@Override
	public List<UserBylistRoleVo> findByCountUserRoleVo(UserByRoleVo vo) {
		Map<String, UserBylistRoleVo> map = new HashMap<>();
		List<UserByRoleVo> listUserByRoleVo = userMapper.findByUserAndRoleVo(vo);
		if(listUserByRoleVo.isEmpty()){
			throw new BizException(RestResponseCode.NO_USER_INFORMATION_WAS_FOUND);
		}
		for (int i = 0; i < listUserByRoleVo.size(); i++) {
			UserByRoleVo userByRoleVo = listUserByRoleVo.get(i);
			String userId = userByRoleVo.getLoginId();
			if(map.containsKey(userId)){
				Role role = new Role();
				UserBylistRoleVo userByluistRoleVo = map.get(userId);
				List<Role> listRole = userByluistRoleVo.getRoles();
				//role.setId(userByRoleVo.getId());
				role.setRoleId(userByRoleVo.getRoleId());
				role.setRoleName(userByRoleVo.getRoleName());
				role.setRoleType(userByRoleVo.getRoleType());
				role.setRoleDescription(userByRoleVo.getRoleDescription());
				role.setIsactive(userByRoleVo.getIsactive());
				listRole.add(role);
				userByluistRoleVo.setRoles(listRole);
				map.put(userId, userByluistRoleVo);
			}else{
				UserBylistRoleVo userBylistRoleVo = new UserBylistRoleVo();
				List<Role> listRole = new ArrayList<>();
				userBylistRoleVo.setId(userByRoleVo.getId());
				userBylistRoleVo.setLoginId(userByRoleVo.getLoginId());
				userBylistRoleVo.setPassword(userByRoleVo.getPassword());
				userBylistRoleVo.setFirstName(userByRoleVo.getFirstName());
				userBylistRoleVo.setLastName(userByRoleVo.getLastName());
				userBylistRoleVo.setIsactive(userByRoleVo.getIsactive());
				userBylistRoleVo.setLandingPage(userByRoleVo.getLandingPage());
				userBylistRoleVo.setDefaultNetGrp(userByRoleVo.getDefaultNetGrp());
				Role role = new Role();
				//role.setId(userByRoleVo.getId());
				role.setRoleId(userByRoleVo.getRoleId());
				role.setRoleName(userByRoleVo.getRoleName());
				role.setRoleType(userByRoleVo.getRoleType());
				role.setRoleDescription(userByRoleVo.getRoleDescription());
				role.setIsactive(userByRoleVo.getIsactive());
				listRole.add(role);
				userBylistRoleVo.setRoles(listRole);
				map.put(userId, userBylistRoleVo);
			}
		}
		List<UserBylistRoleVo> listUserBylistRoleVo = new ArrayList<>();
		for (UserBylistRoleVo value : map.values()) {  
			listUserBylistRoleVo.add(value);
		}  
		return listUserBylistRoleVo;
	}

	@Override
	public List<UserBylistRoleVo> findByCountUserRoleVoByRoleName(UserByRoleVo vo) {
		Map<String, UserBylistRoleVo> map = new HashMap<>();
		List<UserByRoleVo> listUserByRoleVo = userMapper.findByUserAndRoleVo(vo);
//		if(listUserByRoleVo.isEmpty()){
//			throw new BizException(RestResponseCode.NO_USER_INFORMATION_WAS_FOUND);
//		}
		for (int i = 0; i < listUserByRoleVo.size(); i++) {
			UserByRoleVo userByRoleVo = listUserByRoleVo.get(i);
			String userId = userByRoleVo.getId();
			if(map.containsKey(userId)){
				UserBylistRoleVo userByluistRoleVo = map.get(userId);
				List<String> listRoleName = userByluistRoleVo.getRoleName();
				listRoleName.add(userByRoleVo.getRoleName());
				userByluistRoleVo.setRoleName(listRoleName);
				map.put(userId, userByluistRoleVo);
			}else{
				UserBylistRoleVo userBylistRoleVo = new UserBylistRoleVo();
				List<String> listRole = new ArrayList<>();
				userBylistRoleVo.setId(userByRoleVo.getId());
				userBylistRoleVo.setLoginId(userByRoleVo.getLoginId());
				userBylistRoleVo.setPassword(userByRoleVo.getPassword());
				userBylistRoleVo.setFirstName(userByRoleVo.getFirstName());
				userBylistRoleVo.setLastName(userByRoleVo.getLastName());
				userBylistRoleVo.setIsactive(userByRoleVo.getIsactive());
				userBylistRoleVo.setLandingPage(userByRoleVo.getLandingPage());
				userBylistRoleVo.setDefaultNetGrp(userByRoleVo.getDefaultNetGrp());
				userBylistRoleVo.setLocale(userByRoleVo.getLocale());
				listRole.add(userByRoleVo.getRoleName());
				userBylistRoleVo.setRoleName(listRole);
				map.put(userId, userBylistRoleVo);
			}
		}
		List<UserBylistRoleVo> listUserBylistRoleVo = new ArrayList<>();
		for (UserBylistRoleVo value : map.values()) {  
			listUserBylistRoleVo.add(value);
		}  
		return listUserBylistRoleVo;
	}

	@Override
	public RestResponseCode updateUserIsActive(UserVo vo) {
		int intcode = userMapper.updateByPrimaryKeySelective(vo);
		if(intcode < 0){
			return  RestResponseCode.UPDATE_ISACTIVE_FAILURE;
		}
		return  RestResponseCode.OK;
	}

	@Override
	public RestResponseCode reSaveUser(UserVo vo) {
		if(ObjectUtils.isEmpty(vo.getPassword())){
			throw new BizException(RestResponseCode.MANUALRECEIPTBYRECEIPT_CODE19);
		}
		String newPassword = vo.getNewPassword();
		if(!(6 <= newPassword.length() && newPassword.length() <= 16)){
			throw new BizException(RestResponseCode.MANUALRECEIPTBYRECEIPT_CODE20);
		}
		if(!ValidationUtil.matcherTestByAlphanumeric(newPassword)){
			throw new BizException(RestResponseCode.MANUALRECEIPTBYRECEIPT_CODE21);
		}
		//User user = userMapper.selectByPrimaryKey(vo.getId());
		//findByUserVo
		UserVo userVoDto = new UserVo();
		userVoDto.setLoginId(vo.getLoginId());
		List<UserVo> listUserVo = userMapper.findByUserVo(userVoDto);
		UserVo userVo = listUserVo.get(0);
		
		if(!userVo.getPassword().equals(Encrypt.md5(vo.getPassword()))){
			throw new BizException(RestResponseCode.MANUALRECEIPTBYRECEIPT_CODE18);
		}
		userVo.setPassword(Encrypt.md5(vo.getNewPassword()));
		User user = new User();
		BeanUtils.copyProperties(userVo, user);
		userMapper.updateByPrimaryKeySelective(user);
		return RestResponseCode.OK;
	}

	
}
