package cn.jeeweb.web.modules.sys.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.modules.sys.entity.UserOrganization;
import cn.jeeweb.web.modules.sys.entity.UserRole;
import cn.jeeweb.web.modules.sys.mapper.UserMapper;
import cn.jeeweb.web.modules.sys.service.IUserOrganizationService;
import cn.jeeweb.web.modules.sys.service.IUserRoleService;
import cn.jeeweb.web.modules.sys.service.IUserService;
import cn.jeeweb.web.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

@Transactional
@Service("userService")
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User> implements IUserService {
	@Autowired
	PasswordService passwordService;
	@Autowired
	private IUserOrganizationService userOrganizationService;
	@Autowired
	private IUserRoleService userRoleService;

	@Override
	public void changePassword(String userid, String newPassword) {
		User user = selectById(userid);
		if (user != null) {
			user.setPassword(newPassword);
			passwordService.encryptPassword(user);
		}
		insertOrUpdate(user);
	}

	@Override
	public User findByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		return selectOne(new EntityWrapper<User>(User.class).eq("username", username));
	}

	@Override
	public User findByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		return selectOne(new EntityWrapper<User>(User.class).eq("email", email));
	}

	@Override
	public User findByPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		return selectOne(new EntityWrapper<User>(User.class).eq("phone", phone));
	}

	@Override
	public boolean insert(User user) {
		passwordService.encryptPassword(user);
		return super.insert(user);
	}

	@Override
	public boolean deleteById(Serializable id) {
		// 删除角色关联
		userRoleService.delete(new EntityWrapper<UserRole>(UserRole.class).eq("userId", id));
		// 删除部门关联
		userOrganizationService.delete(new EntityWrapper<UserOrganization>(UserOrganization.class).eq("userId", id));
		return super.deleteById(id);
	}

	@Override
	public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
		for (Object id : idList) {
			this.deleteById((Serializable) id);
		}
		return true;
	}

	@Override
	public Page<User> selectPage(Page<User> page, Wrapper<User> wrapper) {
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectUserList(page, wrapper));
		return page;
	}

	@Override
	public void updateForChangeFreezeUserStatus(String userId,int status){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId","'"+userId.replaceAll(",","','")+"'");
		paramMap.put("status",status);
		paramMap.put("lastRepair", UserUtils.getUser().getId());
		paramMap.put("lastTime", DateUtils.getDate());
		int num=baseMapper.updateUserFreezeStatus(paramMap);
		try{
			boolean  retBool=num>0?true:false;
			if(!retBool){
				throw  new RuntimeException("操作失败[更新帐号冻结状态失败]");
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void updateForChangeReceiveTaskStatus(String userId,int status){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId","'"+userId.replaceAll(",","','")+"'");
		paramMap.put("status",status);
		paramMap.put("lastRepair", UserUtils.getUser().getId());
		paramMap.put("lastTime", DateUtils.getDate());
		int num=baseMapper.updateUserReceiveTaskStatus(paramMap);
		try{
			boolean  retBool=num>0?true:false;
			if(!retBool){
				throw  new RuntimeException("操作失败[当前帐号任务领取状态不需要更改]");
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

}
