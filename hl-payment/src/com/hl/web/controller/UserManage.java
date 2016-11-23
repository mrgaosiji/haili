package com.hl.web.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hl.common.model.PageData;
import com.hl.common.model.RespInfo;
import com.hl.common.web.BaseController;
import com.hl.common.web.JsonModelAndView;
import com.hl.common.web.security.AuthCfg;
import com.hl.web.entity.MmOperationLog;
import com.hl.web.entity.MmRole;
import com.hl.web.entity.MmUser;
import com.hl.web.form.ModifyPwdForm;
import com.hl.web.form.UserForm;
import com.hl.web.form.UserSearchForm;
import com.hl.web.security.AuthUser;
import com.hl.web.service.UserService;
import com.hl.web.util.MmToolkit;

@Controller
@RequestMapping("/user")
public class UserManage extends BaseController {

	
	
	@Autowired
	private UserService userService;

	@RequestMapping("/list.do")
	public ModelAndView listUser(HttpServletRequest request,
			HttpServletResponse response,@Valid final UserSearchForm form,
			BindingResult result) {
		
		PageData pd = new PageData();
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			pd.setValues(-1, fieldError, null);
		} else {
			pd=userService.queryUser(form.getUserName(), form.getPage(), form.getRows());
		}

		return new JsonModelAndView(pd);
	}
	
	@RequestMapping("/modify-pwd.do")
	@AuthCfg(auth=false)
	public ModelAndView modifyPwd(HttpServletRequest request,
			HttpServletResponse response, final ModifyPwdForm form,
			BindingResult result) {

		RespInfo resp = new RespInfo();
		AuthUser user = (AuthUser) request.getSession().getAttribute(
				AuthUser.AUTH_USER_SESSION_KEY);
		if(user==null){
			return new JsonModelAndView(resp.setValues(-1, "请先登录系统", null));
		}
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			resp.setValues(-1, fieldError, null);
		} else {
			if(!form.getPassword1().equals(form.getPassword2())){
				return new JsonModelAndView(resp.setValues(-1, "新密码两次输入不一致", null));
			}else{
				MmUser mmUser=(MmUser) user.getData();
				MmUser loadedUser=userService.loadUser(mmUser.getUserId());
				String pwd=userService.getMd5Str(form.getPassword());
				if(!pwd.equalsIgnoreCase(loadedUser.getUserPwd())){
					return new JsonModelAndView(resp.setValues(-1, "原密码输入不正确", null));
				}else{
					loadedUser.setUserPwd(userService.getMd5Str(form.getPassword1()));
					userService.updateEntity(loadedUser);
					MmOperationLog opLog=MmToolkit.createOpLog(request);
					opLog.setOpType("U");
					opLog.setRefTable("MM_USER");
					opLog.setOpDesc("修改密码");
					userService.saveEntity(opLog);
					return new JsonModelAndView(resp.setValues(0, "密码修改成功", null));
				}
			}
		}

		return new JsonModelAndView(resp);
	}
	
	@RequestMapping("/create.do")
	public ModelAndView createUser(HttpServletRequest request,
			HttpServletResponse response,@Valid final UserForm form,
			BindingResult result) {
		RespInfo resp = new RespInfo(0,"用户建立成功",null);
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			resp.setValues(-1, fieldError, null);
		} else {
			if(userService.loadEntity(MmUser.class, form.getUserName())!=null){
				return new JsonModelAndView(resp.setValues(-1, "用户名已存在", null));
			}else{
				MmUser user=new MmUser();
				user.setUserId(form.getUserName());
				user.setAddress(form.getAddress());
				user.setUserPwd(userService.getMd5Str(userService.getDefaultPassword()));
				user.setAddTime(new Date());
				user.setCellphone(form.getCellphone());
				user.setEmail(form.getEmail());
				user.setFullName(form.getFullName());
				user.setGender(Long.valueOf(form.getGender()));
				user.setLoginCount(0l);
				user.setNote(form.getNote());
				user.setStatus(0l);
				if(form.getRoles()!=null){
					List<MmRole> roleList=new ArrayList<MmRole>();
					for(String rid:form.getRoles()){
						if(StringUtils.hasText(rid)){
							MmRole mr=new MmRole();
							mr.setId(rid);
							roleList.add(mr);
						}
					}
					user.setRoles(roleList);
				}
				userService.saveEntity(user);
				MmOperationLog opLog=MmToolkit.createOpLog(request);
				opLog.setOpType("C");
				opLog.setNewValue("userId="+user.getUserId());
				opLog.setRefTable("MM_USER");
				opLog.setOpDesc("创建系统用户");
				userService.saveEntity(opLog);
			}
		}

		return new JsonModelAndView(resp);
	}
	
	@RequestMapping("/edit.do")
	public ModelAndView editUser(HttpServletRequest request,
			HttpServletResponse response,@Valid final UserForm form,
			BindingResult result) {
		RespInfo resp = new RespInfo(0,"用户建立成功",null);
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			resp.setValues(-1, fieldError, null);
		} else {
			MmUser user=(MmUser) userService.loadEntity(MmUser.class, form.getUserName());
			if(user==null){
				return new JsonModelAndView(resp.setValues(-1, "用户不已存", null));
			} else if("1".equals(request.getParameter("changeStatusAction"))){
				if(form.getStatus()==0||form.getStatus()==1){
					user.setStatus(Long.valueOf(form.getStatus()));
					userService.updateEntity(user);
					return new JsonModelAndView(resp.setValues(0, "操作成功", null));
				}else{
					return new JsonModelAndView(resp.setValues(-1, "状态值不在规定范围内", null));
				}
			}
			
			else{
				user.setAddress(form.getAddress());
				user.setCellphone(form.getCellphone());
				user.setEmail(form.getEmail());
				user.setFullName(form.getFullName());
				user.setGender(Long.valueOf(form.getGender()));
				user.setNote(form.getNote());
				if(form.getRoles()!=null){
					List<MmRole> roleList=new ArrayList<MmRole>();
					for(String rid:form.getRoles()){
						if(StringUtils.hasText(rid)){
							MmRole mr=new MmRole();
							mr.setId(rid);
							roleList.add(mr);
						}
					}
					user.setRoles(roleList);
				}else{
					user.setRoles(null);
				}
				
				userService.updateEntity(user);
				MmOperationLog opLog=MmToolkit.createOpLog(request);
				opLog.setOpType("U");
				opLog.setNewValue("userId="+user.getUserId());
				opLog.setOldValue("userId="+user.getUserId());
				opLog.setRefTable("MM_USER");
				opLog.setOpDesc("修改系统用户");
				userService.saveEntity(opLog);
			}
		}

		return new JsonModelAndView(resp);
	}
	
	@RequestMapping("/listAllRoles.do")
	@AuthCfg(auth=true,code="pages/user-manage.html")
	public ModelAndView listAllRoles(HttpServletRequest request,
			HttpServletResponse response){
		return new JsonModelAndView(userService.queryAllRoles());
	}

	@RequestMapping("/view.do")
	@AuthCfg(auth=true,code="pages/user-manage.html")
	public ModelAndView getUser(HttpServletRequest request,
			HttpServletResponse response){
		RespInfo resp = new RespInfo(0,"用户建立成功",null);
		String userName=request.getParameter("userName");
		if(StringUtils.hasText(userName)){
			MmUser user=userService.loadUser(userName);
			if(user==null){
				return new JsonModelAndView(resp.setValues(-1, "用户不已存", null));
			} else{
				UserForm form=new UserForm();
				form.setUserName(user.getUserId());
				form.setAddress(user.getAddress());
				form.setStatus(user.getStatus());
				form.setAddTime(user.getAddTime());
				form.setCellphone(user.getCellphone());
				form.setEmail(user.getEmail());
				form.setFullName(user.getFullName());
				form.setGender(user.getGender()!=null?user.getGender().intValue():3);
				form.setNote(user.getNote());
				List<String> roleIds=new ArrayList<String>();
				List<String> roleNames=new ArrayList<String>();
				if(user.getRoles()!=null){
					for(MmRole r:user.getRoles()){
						roleIds.add(r.getId());
						roleNames.add(r.getName());
					}
				}
				form.setRoles(roleIds.toArray(new String[roleIds.size()]));
				form.setRoleNames(roleNames.toArray(new String[roleNames.size()]));
				resp.setData(form);
			}
		}
		return new JsonModelAndView(resp);
	}
}
