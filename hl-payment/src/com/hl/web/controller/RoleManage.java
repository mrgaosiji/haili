package com.hl.web.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hl.web.entity.MmFunction;
import com.hl.web.entity.MmOperationLog;
import com.hl.web.entity.MmRole;
import com.hl.web.form.RoleForm;
import com.hl.web.form.UserSearchForm;
import com.hl.web.service.UserService;
import com.hl.web.util.MenuTreeBuilder;
import com.hl.web.util.MmToolkit;

@Controller
@RequestMapping("/role")
public class RoleManage extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping("/list.do")
	@AuthCfg(auth = true, code = "pages/role-manage.html")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, final UserSearchForm form, BindingResult result) {
		PageData pd = new PageData();
		//这句是做什么的
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			pd.setValues(-1, fieldError, null);
		} else {
			List<MmRole> list = this.userService.queryRole();
			pd.setData(list);
		}

		return new JsonModelAndView(pd);
	}

	@RequestMapping("/create.do")
	@AuthCfg(auth = true, code = "pages/role-manage.html")
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response,@Valid final RoleForm form, BindingResult result) {
		RespInfo resp = new RespInfo(0, "角色建立成功", null);
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			resp.setValues(-1, fieldError, null);
		} else {
			MmRole role = this.userService.getMmRoleByName(form.getName());
			if (role != null) {
				return new JsonModelAndView(resp.setValues(-1, "角色名已存在", null));
			} else {
				role = new MmRole();
				role.setAddTime(new Date());
				role.setName(form.getName());
				role.setDescription(form.getDescription());
				/*
				 * 角色资源
				 * */
				List<MmFunction> functions = new ArrayList<MmFunction>();
				if (form.getFuncs()!=null) {
					for(String funcId:form.getFuncs()){
						MmFunction func=new MmFunction();
						func.setId(funcId);
						functions.add(func);
					}
				}
				role.setFunctions(functions);
				userService.saveEntity(role);
				MmOperationLog opLog=MmToolkit.createOpLog(request);
				opLog.setOpType("C");
				opLog.setNewValue("id="+role.getId()+"&name="+role.getName());
				opLog.setRefTable("MM_ROLE");
				opLog.setOpDesc("创建角色");
				userService.saveEntity(opLog);
			}
		}

		return new JsonModelAndView(resp);
	}

	@RequestMapping("/edit.do")
	@AuthCfg(auth = true, code = "pages/role-manage.html")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,@Valid RoleForm form, BindingResult result) {
		RespInfo resp = new RespInfo(0, "角色建立成功", null);
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			resp.setValues(-1, fieldError, null);
		} else {
			String name = form.getName();
			String id = form.getId();
			MmRole role = (MmRole) userService.loadEntity(MmRole.class, id);
			if (role == null) {
				return new JsonModelAndView(resp.setValues(-1, "角色不存在", null));
			} else {
				if (!role.getName().equals(name.trim())) {
					MmRole temp = this.userService.getMmRoleByName(name.trim());
					if (temp != null) {
						return new JsonModelAndView(resp.setValues(-1, "角色名已存在", null));
					}
				}
			}
			role.setName(form.getName());
			role.setDescription(form.getDescription());
			/*
			 * 角色资源
			 * */
			List<MmFunction> functions = new ArrayList<MmFunction>();
			if (form.getFuncs()!=null) {
				for(String funcId:form.getFuncs()){
					MmFunction func=new MmFunction();
					func.setId(funcId);
					functions.add(func);
				}
			}
			role.setFunctions(functions);
			userService.updateEntity(role);
			MmOperationLog opLog=MmToolkit.createOpLog(request);
			opLog.setOpType("U");
			opLog.setNewValue("id="+role.getId()+"&name="+role.getName());
			opLog.setRefTable("MM_ROLE");
			opLog.setOpDesc("更新角色");
			userService.saveEntity(opLog);
		}

		return new JsonModelAndView(resp);
	}

	@RequestMapping("/view.do")
	@AuthCfg(auth = true, code = "pages/role-manage.html")
	public ModelAndView getRole(HttpServletRequest request, HttpServletResponse response) {
		RespInfo resp = new RespInfo(0, "建立成功", null);
		String id = request.getParameter("id");
		if (StringUtils.hasText(id)) {
			MmRole role =  userService.loadMmRole(id);
			if (role == null) {
				return new JsonModelAndView(resp.setValues(-1, "角色不存在", null));
			} else {
				RoleForm form = new RoleForm();
				form.setName(role.getName());
				form.setId(id);
				form.setDescription(role.getDescription());
				List<String> ids=new ArrayList<String>();
				List<String> names=new ArrayList<String>();
				if(role.getFunctions()!=null){
					for(MmFunction f:role.getFunctions()){
						ids.add(f.getId());
						names.add(f.getName());
					}
				}
				form.setFuncs(ids.toArray(new String[ids.size()]));
				form.setFuncNames(names.toArray(new String[names.size()]));
				resp.setData(form);
			}
		}
		System.out.println("opq");
		return new JsonModelAndView(resp);
	}

	@RequestMapping("/funclist.do")
	@AuthCfg(auth = true, code = "pages/role-manage.html")
	public ModelAndView getFuncs(HttpServletRequest request, HttpServletResponse response) {
		Map<String, MmFunction> funcList = new HashMap<String, MmFunction>();
		List<MmFunction> rootChildren = new ArrayList<MmFunction>();
		List<MmFunction> funcs = userService.queryAllFuncs();
		for (MmFunction f : funcs) {
			if (!StringUtils.hasText(f.getParentId())) {
				rootChildren.add(f);
			} else {
				funcList.put(f.getId(), f);
			}
		}

		return new JsonModelAndView(MenuTreeBuilder.buildTreeData(funcList, rootChildren));
	}

	@RequestMapping("/delete.do")
	@AuthCfg(auth = true, code = "pages/role-manage.html")
	public ModelAndView deleteRole(HttpServletRequest request, HttpServletResponse response) {
		RespInfo resp = new RespInfo(0, "建立成功", null);
		String id = request.getParameter("id");
		if (StringUtils.hasText(id)) {
			MmRole role = (MmRole) userService.loadEntity(MmRole.class, id);
			if (role == null) {
				return new JsonModelAndView(resp.setValues(-1, "角色不存在", null));
			} else {
				userService.deleteEntity(role);
				MmOperationLog opLog=MmToolkit.createOpLog(request);
				opLog.setOpType("D");
				opLog.setNewValue("id="+role.getId()+"&name="+role.getName());
				opLog.setRefTable("MM_ROLE");
				opLog.setOpDesc("删除角色");
				userService.saveEntity(opLog);
			}
		} else {
			return new JsonModelAndView(resp.setValues(-1, "ID is null", null));
		}
		return new JsonModelAndView(resp);
	}
}
