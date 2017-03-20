package com.zml.web.controller.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zml.common.page.Page;
import com.zml.common.page.Parameter;
import com.zml.common.web.base.BaseController;
import com.zml.common.web.bind.annotation.CurrentUser;
import com.zml.common.web.bind.annotation.Permission;
import com.zml.common.web.entity.Message;
import com.zml.user.entity.User;
import com.zml.user.exceptions.UserServiceException;
import com.zml.user.service.IUserService;

/**
 * @Permission("user:view")	
 * 规则："资源标识符:操作" - 不支持通配符
 * 上面表示拥有user资源的view操作权限
 * 
 * @author zhaomingliang
 * @date 2017年3月20日
 */
@RestController
public class UserController extends BaseController {
	
	private static final Integer LOCK = 101;
	private static final Integer ACTIVE = 100;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 用户详情
	 * @param id
	 * @return
	 */
	@Permission("user:view")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message getDetail(@PathVariable("id") long id) {
		Message message = new Message();
		User user = this.userService.getUserById(id);
		if(user == null) {
			throw UserServiceException.create("未找到相应用户信息！", UserServiceException.USERINFO_NOT_EXIST);
		} else {
			message.setData(user);
		}
		return message;
		
	}
	
	/**
	 * 获取所有用户列表
	 * @return
	 */
	@Permission("user:view")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public Message listAllUsers() {
		Message message = new Message();
        List<User> users = this.userService.getAllUser();
        if(users.isEmpty()){
        	message.setStatusCode(HttpStatus.NO_CONTENT.value());
        }
        message.setMessage("获取列表成功！");
        message.setData(users);
        return message;
    }
	
	/**
	 * 分页获取用户列表
	 * @param param
	 * @return
	 */
	@Permission("user:view")
	@RequestMapping(value = "/listUser", method = RequestMethod.POST)
	public Message listUserPage(@RequestBody Parameter<User> param) {
		Message message = new Message();
		Page page = this.userService.getUserPage(param);
		message.setMessage("获取列表成功！");
        message.setData(page.getRecordList());
        message.setTotalCount(page.getTotalCount());
        return message;
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @param ucBuilder
	 * @return
	 */
	@Permission("user:create")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public Message createUser(@Valid @RequestBody User user/*, BindingResult result*/) {
		Message message = new Message();
		if(this.userService.isUserExist(user)) {
			this.logSaveErr("用户已经存在！", UserServiceException.USERINFO_IS_EXIST);
			throw UserServiceException.create("用户已经存在！", UserServiceException.USERINFO_IS_EXIST);
		} else {
			this.userService.addUser(user);
			this.logSave("添加用户成功！");
			message.setMessage("添加用户成功！");
		}
		return message;
	}
	
	/**
	 * 更新用户信息
	 * @param id
	 * @param user
	 * @return
	 */
	@Permission("user:update")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Message updateUser(@PathVariable("id") long id, @Valid @RequestBody User user, BindingResult result) {
		Message message = new Message();
		// 相关字段是否验证失败
		if(result.hasErrors()) {
			message.setValidFail(super.loadFieldError(result.getFieldErrors()));
		} else {
			User currentUser = this.userService.getUserById(id);
			if (currentUser == null) {
				throw UserServiceException.create("更新用户失败！", UserServiceException.UPDATE_USER_FAIL);
			} else {
				//currentUser.setUserName(user.getUserName());
				currentUser.setStaffNum(user.getStaffNum());
				//currentUser.setPasswd(user.getPasswd());
				this.userService.updateUser(currentUser);
				message.setSuc();
			}
		}
        return message;
    }
	
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	@Permission("user:delete")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Message deleteUser(@PathVariable("id") long id) {
		Message message = new Message();
		
        System.out.println("Fetching & Deleting User with id " + id);
        User user = this.userService.getUserById(id);
        if (user == null) {
        	throw UserServiceException.create("未找到相应用户信息！", UserServiceException.USERINFO_NOT_EXIST);
        } else {
        	this.userService.deleteUser(id);
        	message.setSuc();
        }
        return message;
    }
	
	/**
	 * 激活、锁定用户
	 * @param id
	 * @param status
	 * @return
	 */
	@Permission("user:update")
	@RequestMapping(value = "/user/{id}/lock/{status}", method = RequestMethod.GET)
	public Message lock(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
		Message message = new Message();
		if(id == null || status == null) {
			throw UserServiceException.create("未找到相应用户信息！", UserServiceException.USERINFO_NOT_EXIST);
		} else {
			User user = this.userService.getUserById(id);
			if (user == null) {
				throw UserServiceException.create("未找到相应用户信息！", UserServiceException.USERINFO_NOT_EXIST);
	        } else {
	        	if(status == 100) { // 锁定
	        		this.userService.updateUserStatus(id, LOCK);
	        	} else {			// 激活
	        		this.userService.updateUserStatus(id, ACTIVE);
	        	}
	        	message.setSuc();
	        }
		}
		return message;
	}
	
	/**
	 * 测试@CurrentUser注解
	 * 从缓存中取出当前登陆用户的信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/insertSomthing", method = RequestMethod.GET)
	public Message insertSomthing(@CurrentUser User user) {
		Message message = new Message();
		message.setSuc();
		message.setData(user);
		return message;
	}
	
}